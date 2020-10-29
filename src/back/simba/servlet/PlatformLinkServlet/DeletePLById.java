package back.simba.servlet.PlatformLinkServlet;

import back.simba.dao.PlatQuery;
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
 * @author Simba
 * @date 2020/10/29 15:07
 */
@WebServlet("/DeletePLById")
public class DeletePLById extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1. 设置request和response编码格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.获取post请求参数
        String id = request.getParameter("id");
        int pl_id = Integer.parseInt(id);

        //3.执行删除操作
        boolean b = new PlatQuery().deletePlatformLinkById(pl_id);
        Map<Integer,Integer> res = new HashMap<>();
        if (b == true){
            res.put(pl_id,1);
        }else {
            res.put(pl_id,0);
        }

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(res);

        //4.向前端响应json数据
        response.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        response.getWriter().append(result);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
