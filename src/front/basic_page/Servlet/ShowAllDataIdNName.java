package front.basic_page.Servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.wang.Domain.BasicInfoAll;
import front.basic_page.Dao.QueryData;

/**
 * @author wwx-sys
 * @time 2020-11-11-15:59
 * @description 所有数据的id和数据集名称
 */

@WebServlet("/ShowAllDataIdNName")
public class ShowAllDataIdNName extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域问题，让返回结果可远程调用

        List<BasicInfoAll> basicInfoAlls = new QueryData().getAllIdNName();

        JSONArray object = new JSONArray();
        basicInfoAlls.forEach(basicInfoAll -> {
            JSONObject object1 = new JSONObject();
            object1.put("id",basicInfoAll.getId());
            object1.put("NAME",basicInfoAll.getName());
//            object.ad("BasicInfoAll",object1);
            object.add(object1);
        });

        resp.getWriter().append(JSON.toJSONString(object));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
