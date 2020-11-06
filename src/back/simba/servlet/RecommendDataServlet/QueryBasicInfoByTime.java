package back.simba.servlet.RecommendDataServlet;

import back.simba.dao.RecommendQuery;
import back.simba.domain.RecomendData;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取按照时间降序的数据列表，包含id，name，status字段
 * @author Simba
 * @date 2020/10/28 10:57
 */
@WebServlet("/QueryBasicInfoByTime")
public class QueryBasicInfoByTime extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 设置request和response编码格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2. 获取请求参数
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        int curPage = Integer.parseInt(currentPage);
        int pSzie = Integer.parseInt(pageSize);

        //3.封装请求结果
        //查询数据总数
        Map<String,Object> map = new HashMap<>();
        Integer counts = new RecommendQuery().queryAllBasicInfo();
        map.put("totalCount",counts);

        List<RecomendData> recomendData = new RecommendQuery().queryBasicInfoByTime(curPage, pSzie);
        map.put("data",recomendData);
        System.out.println(map.toString());
        //4.查询结果转换为json
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(map);

        //5.向前端响应数据response对象
        response.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        response.getWriter().append(result);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
