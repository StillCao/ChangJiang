package back.simba.servlet.RollingServlet;

import back.simba.dao.RollingQuery;
import back.simba.domain.RollingData;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *根据id获取某一条数据
 * @author Simba
 * @date 2020/10/29 19:47
 */
@WebServlet("/QueryRollingDataById")
public class QueryRollingDataById extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String id = request.getParameter("id");
        int r_id = Integer.parseInt(id);
        RollingData rollingData = new RollingQuery().queryRollingById(r_id);
        String result = new ObjectMapper().writeValueAsString(rollingData);

        response.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        response.getWriter().append(result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
