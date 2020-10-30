package back.simba.servlet.PlatformLinkServlet;

import back.simba.dao.PlatQuery;
import back.simba.domain.PlatformLink;
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
 * 分页条件查询接口
 * @author Simba
 * @date 2020/10/29 9:55
 */
@WebServlet("/QueryPLByPage")
public class QueryPlatformLinkByPage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1. 设置request和response编码格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //2.获取post请求中的参数
        String currentPage = request.getParameter("currentPage");
        int curpage = Integer.parseInt(currentPage);
        String pageSize = request.getParameter("pageSize");
        int psize  = Integer.parseInt(pageSize);
        String searchWord = request.getParameter("searchWord");

        //3.调用方法，封装参数
        List<PlatformLink> res = new PlatQuery().queryPlatformLink(searchWord, curpage, psize);
        Integer counts = new PlatQuery().queryAllCounts();

        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",counts);
        map.put("data",res);
        //通过jackson工具中的ObjectMapper类的成员方法，转换结果为json
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(map);

        //4.向前端响应json数据
        response.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        response.getWriter().append(result);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
