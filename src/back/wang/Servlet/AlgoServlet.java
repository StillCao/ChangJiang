package back.wang.Servlet;

import com.alibaba.fastjson.JSON;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.wang.Dao.AlgoQuery;
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

    //根据标签id查询对应的算法id
    public void getAlgoIdsByTagId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idString = req.getParameter("id");
        try {
            int id = Integer.parseInt(idString);
            TypicalAlgoTags typicalAlgoTags = algoQuery.getTagsById(id);

            resp.setContentType("text/html;charset=utf-8");
            resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
            resp.getWriter().append(typicalAlgoTags.getAlgo());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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
                isSuccess = algoService.deleteAlgo(algo);
            }
            resp.setContentType("text/html;charset=utf-8");
            resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
            resp.getWriter().append(String.valueOf(isSuccess));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
