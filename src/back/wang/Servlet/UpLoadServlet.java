package back.wang.Servlet;

import back.wang.Domain.BasicInfoAll;
import back.wang.Service.UpLoadService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import front.basic_page.Domain.Attr_value;
import front.basic_page.Domain.RelateKeyNData;
import front.basic_page.Domain.TypeLevel2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 描述:
 * 专题数据上传接口
 *
 * @author black-leaves
 * @createTime 2020-06-24  9:26
 */

@WebServlet("/UpLoadServlet")
public class UpLoadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> map = req.getParameterMap();
        JSONObject jsonObject = JSON.parseObject(map.get("data")[0]);
        JSONObject basic_infoObj = jsonObject.getJSONObject("basic_info");
        JSONObject attr_valueObj = jsonObject.getJSONObject("attr_value");

        BasicInfoAll basic_info = JSON.parseObject(basic_infoObj.toJSONString(), BasicInfoAll.class);
        Attr_value attr_value = JSON.parseObject(attr_valueObj.toJSONString(), Attr_value.class);

        UpLoadService service = new UpLoadService();
        StringBuilder result = new StringBuilder();
        int basicId = service.InsertBasic(basic_info);  //插入基本数据
        if (basicId != 0) {
            result.append("插入基本信息表成功！\n");
            if (service.InsertRelate(attr_value, basicId)) {  //插入数据主题词关系表
                result.append("插入数据主题词关系表成功！\n");
            } else {
                result.append("插入数据主题词关系表失败！\n");
            }
        } else {
            result.append("插入基本信息表失败！\n");
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result.toString());


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
