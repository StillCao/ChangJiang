package back.simba.servlet.RollingServlet;

import back.simba.dao.RollingQuery;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 删除数据库中对应的记录和对应的轮播图图片
 * @author Simba
 * @date 2020/10/29 19:51
 */
@WebServlet("/DeleteRollingAndImage")
public class DeleteRollingAndImage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String id = request.getParameter("id");
        int r_id = Integer.parseInt(id);

        Integer res = new RollingQuery().deleteRollingById(r_id);
        Map<String,Integer> map = new HashMap<>();
        map.put("id",r_id);
        map.put("isDeleted",res);
        String result = new ObjectMapper().writeValueAsString(map);

        response.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        response.getWriter().append(result);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
