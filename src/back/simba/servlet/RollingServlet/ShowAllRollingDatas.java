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
import java.util.List;

/**
 * 展示轮播图信息
 * @author Simba
 * @date 2020/10/29 19:13
 */
@WebServlet("/ShowAllRollingDatas")
public class ShowAllRollingDatas extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.设置request和response编码格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.不用获取参数，调用方法，返回所有轮播图数据
        List<RollingData> rollingDatas = new RollingQuery().queryAllData();
        String result = new ObjectMapper().writeValueAsString(rollingDatas);

        //3.向前端响应json数据
        response.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        response.getWriter().append(result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
