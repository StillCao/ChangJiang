package back.wang.Servlet;

import com.alibaba.fastjson.JSON;

import org.junit.Test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.wang.Dao.BasicDataQuery;
import back.wang.Domain.BasicInfoAll;

/**
 * @author wwx-sys
 * @time 2020-10-29-15:17
 * @description 基础数据全字段接口
 */

@WebServlet("/BasicInfoServlet")
public class BasicInfoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");
        String result = "";

        int id = Integer.parseInt(idString);
        if (id != 0){
            BasicDataQuery basicDataQuery = new BasicDataQuery();
            BasicInfoAll basicInfoAll = basicDataQuery.queryDataById(id);
            result = JSON.toJSONString(basicInfoAll);
        }


        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
