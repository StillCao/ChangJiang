package back.simba.servlet.RollingServlet;

import back.simba.dao.RollingQuery;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置轮播图
 * @author Simba
 * @date 2020/10/29 17:02
 */
@WebServlet("/DeployRollingImage")
public class DeployRollingImage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.设置request和response编码格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.获取post请求中的参数
        String roundNewInfo = request.getParameter("roundNewInfo");
        JSONObject jsonObject = JSONObject.parseObject(roundNewInfo);//把得到的json字符串转为json对象

        //3.调用方法，在rolling表中添加新增数据
        int flag = 0;//表示添加状态：0表示添加失败，1表示添加成功。
        Map<String,Integer> res = new HashMap<>();
        try {
            boolean b = new RollingQuery().addRollingData(jsonObject);
            if (b == true){
                flag = 1;
            }
            res.put(jsonObject.getString("title"),flag);
            String result = new ObjectMapper().writeValueAsString(res);

            //4.向前端响应json数据
            response.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
            response.getWriter().append(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
