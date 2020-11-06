package back.simba.servlet.LabelServlet;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import front.data_query.service.CommonQuery;
import front.data_query.service.LabelQuery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Simba
 * @date 2020/11/6 10:57
 */
@WebServlet("/LabelServlet")
public class LabelServlet extends HttpServlet {

    private LabelQuery lQuery = new LabelQuery();
    private CommonQuery cQuery = new CommonQuery();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.设置request和response请求格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //初始化json转换工具类ObjectMapper,和返回结果，变量名为result
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        //所有请求情况中的共同参数
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        JSONObject linkQueryItem = JSONObject.parseObject(request.getParameter("linkQueryItem"));


        //2.获取post请求参数
        String type = request.getParameter("type");//根据type的不同，实现对应的功能

        List<Map> finalList = new ArrayList<>();//存放最终得到的未分页的map集合

        //3. 分析type，根据不同的情况执行不同的操作
        if (type.equals("1")){//单纯的标签查询
            finalList = lQuery.LabelQueryOnly(linkQueryItem);
        }

        if (type.equals("2")){//标签查询+二次查询

            JSONObject secQueryItem = JSONObject.parseObject(request.getParameter("secQueryItem"));
            finalList = lQuery.LabelAndSearchSec(linkQueryItem,secQueryItem);
        }

        //4.对结果进行分页
        List<Map> resMapList = cQuery.devideInfoListByPage(finalList, currentPage, pageSize);
        result = mapper.writeValueAsString(resMapList);

        //5.向前端返回数据
        response.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        response.getWriter().append(result);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
