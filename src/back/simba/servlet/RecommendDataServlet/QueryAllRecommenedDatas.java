package back.simba.servlet.RecommendDataServlet;

import back.simba.dao.RecommendQuery;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/QueryAllRecommenedDatas")
public class QueryAllRecommenedDatas extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1. 设置request和response编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        Integer counts = new RecommendQuery().queryAllRecommenedDatas();
        Map<String,Integer> map = new HashMap<>();
        map.put("allCounts",counts);
        String result = new ObjectMapper().writeValueAsString(map);

        resp.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        resp.getWriter().append(result);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
