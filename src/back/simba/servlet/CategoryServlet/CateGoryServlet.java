package back.simba.servlet.CategoryServlet;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import front.data_query.service.CategoryQuery;
import front.data_query.service.CommonQuery;

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
 * @date 2020/11/4 21:43
 */
@WebServlet("/CateGoryServlet")
public class CateGoryServlet extends HttpServlet {

    private CategoryQuery cateQuery = new CategoryQuery();
    private CommonQuery comQuery = new CommonQuery();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置request和response请求格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //初始化json转换工具类ObjectMapper,和返回结果，变量名为result
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        //所有请求情况中的共同参数
        String searchWord = request.getParameter("searchWord");
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        int categoryType = Integer.parseInt(request.getParameter("categoryType"));


        //2.获取post请求参数
        String type = request.getParameter("type");//根据type的不同，实现对应的功能
        List<Map> finalList = new ArrayList<>();//存放最终得到的未分页的map集合
        if (type.equals("1")){//type = 1时，单纯的数据分类查询
            finalList = cateQuery.CategoryQueryOnly(categoryId,categoryType);
        }

        if (type.equals("2")){//type = 2 时，数据分类 + 二次检索（关键词、时间、访问量升降序、时间升降序）

            JSONObject secQueryItem = JSONObject.parseObject(request.getParameter("secQueryItem"));
            finalList = cateQuery.CateQueryAndSearchSec(categoryId,categoryType,secQueryItem);
        }

        if (type.equals("3")){//数据分类查询 + 标签

            JSONObject linkQueryItem = JSONObject.parseObject(request.getParameter("linkQueryItem"));
            finalList = cateQuery.CateQueryAndLabel(categoryId,categoryType,linkQueryItem);
        }

        if (type.equals("3")){//数据分类查询+标签查询+二次查询
            JSONObject secQueryItem = JSONObject.parseObject(request.getParameter("secQueryItem"));
            JSONObject linkQueryItem = JSONObject.parseObject(request.getParameter("linkQueryItem"));
            finalList = cateQuery.CateQueryAndLabelAndSearchSec(categoryId,categoryType,linkQueryItem,secQueryItem);
        }

        //3.对结果进行分页
        List<Map> resMapList = comQuery.devideInfoListByPage(finalList, currentPage, pageSize);
        result = mapper.writeValueAsString(resMapList);

        //4.向前端返回数据
        response.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        response.getWriter().append(result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
