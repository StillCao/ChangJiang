package back.simba.servlet.RecommendDataServlet;

import back.simba.dao.RecommendQuery;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author Simba
 * @date 2020/10/28 15:38
 */
@WebServlet("/UpdateBasicInfoById")
public class UpdateBasicInfoById extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 设置request和response编码格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.获取post请求参数
        //2.1获取前端传来的json字符串，有属性名使用getParameter，无属性名使用getInputstream
        StringBuffer sb = new StringBuffer();
        InputStream is = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String s = "";
        while ((s = br.readLine()) != null){
            sb.append(s);
        }
        String str = sb.toString();
        System.out.println(str);

        JSONArray jsonArray = JSONArray.parseArray(str);


        //2.2将json字符串转换为JSON对象，调用ObjectMapper类中的readValue方法
        ObjectMapper mapper = new ObjectMapper();
        //JSONArray jsons = mapper.readValue(line, JSONArray.class);//json字符串转为JSONArray
        Map<Integer, Boolean> res = new RecommendQuery().updateStatusOfBasicInfo(jsonArray);//返回每条数据的更新结果
        String result = mapper.writeValueAsString(res);

        //3.向前端响应json数据
        response.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        response.getWriter().append(result);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
