package back.simba.servlet.PlatformLinkServlet;

import back.simba.dao.PlatQuery;
import back.simba.domain.PlatformLink;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 全表查询接口
 * @author Simba
 * @date 2020/10/29 10:12
 */
@WebServlet("/QueryAllPLs")
public class QueryAllPls extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1. 设置request和response编码格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.无参数，调用方法封装结果
        List<PlatformLink> pls = new PlatQuery().queryAllPLs();//查询所有数据
        String result = new ObjectMapper().writeValueAsString(pls);//结果转换为json字符串

        //3.向前端响应json数据
        response.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        response.getWriter().append(result);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
