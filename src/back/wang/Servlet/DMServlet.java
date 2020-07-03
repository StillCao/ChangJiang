package back.wang.Servlet;

import back.wang.Dao.BasicDataQuery;
import back.wang.Domain.BasicInfoAll;
import back.wang.Service.DMService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import front.basic_page.Servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 描述:
 * 基础数据管理接口
 *
 * @author black-leaves
 * @createTime 2020-07-02  9:26
 */

@WebServlet("/DMServlet")
public class DMServlet extends BaseServlet {
    /**
     * 基础数据关键字分页查询
     */
    public void showDataByKey(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭

        Map<String, String[]> map = req.getParameterMap();
        int currentPage = Integer.parseInt(map.getOrDefault("currentPage", new String[]{"1"})[0]);
        int currentCount = Integer.parseInt(map.getOrDefault("currentCount", new String[]{"10"})[0]);

        DMService service = new DMService();
        String result = "";

        if (!map.containsKey("key")) { //全部记录查询
            result = service.allData(currentPage, currentCount);
        } else { //根据关键字查询
            String key = map.get("key")[0];
            if (key.equals("name")) {
                key = "NAME";
            } //与数据库字段匹配
            if (!map.containsKey("value")) {
                result = "请输入value参数 进行查询！";
                resp.getWriter().append(result);
                return;
            }
            String value = map.get("value")[0];
            result = service.dataByKey(key, value, currentPage, currentCount);
        }
        resp.getWriter().append(result);
    }

    /**
     * 基础数据删除
     */
    public void deleteDataById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);
        JSONObject object = new JSONObject();
        DMService service = new DMService();
        BasicDataQuery query = new BasicDataQuery();

        //先删除rela_chart表相关信息
        object.put("删除rela_chart表相关信息:", service.deleteRelaChart(id));

        //再删除order_num表
        object.put("删除order_confirm表相关信息:", service.deleteOrderConfirm(id));

        //再删除基础数据
        BasicInfoAll basicInfo = query.queryDataById(id);
        if (basicInfo != null) {
            //删除基础信息表
            boolean succ = service.deleteBasicData(id);
            object.put("删除基础信息表中记录:", succ);

            if (succ) {
                //再删除基础数据先删除文件(缩略图和文件)
                boolean[] successes = service.deleteFiles(basicInfo);
                for (int i = 0; i < successes.length; i++) {
                    if (i == 0) {
                        object.put("删除基础信息图片:", successes[i]);
                    }
                    if (i == 1) {
                        object.put("删除基础信息数据附件:", successes[i]);
                    }
                    if (i == 2) {
                        object.put("删除基础信息数据文档:", successes[i]);
                    }
                    if (i == 3) {
                        object.put("删除基础信息样例数据:", successes[i]);
                    }
                }
            }
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(JSON.toJSONString(object));
    }

}
