package back.wang.Servlet;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.wang.Dao.AlgoQuery;
import back.wang.Domain.AlgoTagsRelate;
import back.wang.Domain.TypicalAlgo;
import back.wang.Domain.TypicalAlgoTags;
import back.wang.Service.AlgoService;
import front.basic_page.Servlet.BaseServlet;

/**
 * @author wwx-sys
 * @time 2020-08-17-15:22
 * @description 典型算法接口
 */
@WebServlet("/AlgoServlet")
public class AlgoServlet extends BaseServlet {
    private final AlgoQuery algoQuery = new AlgoQuery();

    //返回所有的典型算法记录
    public void showAllAlgo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> map = req.getParameterMap();
        int currentPage = Integer.parseInt(map.getOrDefault("currentPage", new String[]{"1"})[0]);
        int currentCount = Integer.parseInt(map.getOrDefault("currentCount", new String[]{"10"})[0]);
        AlgoService algoService = new AlgoService();
        String result = algoService.allAlgoService(currentPage, currentCount);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    //根据算法id查询对应的典型算法
    public void showAlgoById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idString = req.getParameter("id");
        try {
            int id = Integer.parseInt(idString);
            TypicalAlgo typicalAlgo = algoQuery.getAlgoById(id);
            List<AlgoTagsRelate> relateList = algoQuery.queryRelateByAlgoId(typicalAlgo.getId());
            StringBuilder tagNames = new StringBuilder();
            relateList.forEach(relate -> {
                tagNames.append(algoQuery.getTagsById(relate.getTag_id()).getName()).append(",");
            });
            if (tagNames.toString().endsWith(",")) {
                tagNames.deleteCharAt(tagNames.length() - 1);
            }
            typicalAlgo.setTagNames(tagNames.toString());

            resp.setContentType("text/html;charset=utf-8");
            resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
            resp.getWriter().append(JSON.toJSONString(typicalAlgo));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    //返回所有的标签
    public void showAllTags(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<TypicalAlgoTags> typicalAlgoTagsList = algoQuery.getAllTags();

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(JSON.toJSONString(typicalAlgoTagsList));
    }

    //删除算法
    public void deleteAlgo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isSuccess = false;
        String idString = req.getParameter("id");
        try {
            int id = Integer.parseInt(idString);
            TypicalAlgo algo = algoQuery.getAlgoById(id);
            if (algo != null) {
                AlgoService algoService = new AlgoService();
                String docRootDirPath = "C:/ftp/ChangJiang/典型数据文档/";
                isSuccess = algoService.deleteAlgo(algo, docRootDirPath);
            }
            resp.setContentType("text/html;charset=utf-8");
            resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
            resp.getWriter().append(String.valueOf(isSuccess));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    //根据给的字段和值和模糊查询
    public void queryAlgoByField2Page(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> map = req.getParameterMap();
        int currentPage = Integer.parseInt(map.getOrDefault("currentPage", new String[]{"1"})[0]);
        int currentCount = Integer.parseInt(map.getOrDefault("currentCount", new String[]{"10"})[0]);
        List<String> fieldList = Arrays.asList("name", "digest", "description", "doc_url", "up_user", "up_unit", "up_date");
        String field = map.getOrDefault("field", new String[]{""})[0];
        String value = map.getOrDefault("value", new String[]{""})[0];
        if (!fieldList.contains(field)) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
            resp.getWriter().append("不存在对应的字段名称 :").append(field);
            return;
        }
        AlgoService service = new AlgoService();
        String result = service.getAlgoByFieldLike(field, value, currentPage, currentCount);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);

    }
}
