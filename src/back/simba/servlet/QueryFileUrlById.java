package back.simba.servlet;

import back.simba.dao.FileQuery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Simba
 * @date 2020/11/12 20:42
 */
@WebServlet("/QueryFileUrlById")
public class QueryFileUrlById extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置request和response请求格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.获取post请求参数
        int id = Integer.parseInt(request.getParameter("id"));

        //3.调用方法，请求数据库
        int flag = new FileQuery().queryFileUrlById(id);
        String result = String.valueOf(flag);

        //4.向前端返回数据
        response.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        response.getWriter().append(result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
