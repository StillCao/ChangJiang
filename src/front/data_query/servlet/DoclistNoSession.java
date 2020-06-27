package front.data_query.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import front.data_query.dao.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet( "/DoclistNoSession")
public class DoclistNoSession extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.设置request和response编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //2.获取请求参数
        String updated = req.getParameter("updated");
        boolean update = Boolean.parseBoolean(updated);
        String pageSize = req.getParameter("PageSize");
        String currentPage = req.getParameter("currentPage");
        int page = Integer.parseInt(pageSize);
        int curpage = Integer.parseInt(currentPage);

        ////3.将查询结果转换成json;借助工具类ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        List<Map> mapList = new ArrayList<>();
        List<Map> map_nopage = new ArrayList<>();//存放未分页的文件动态查询结果
        HttpSession ses = req.getSession();
        //查询静态文件列表
        mapList = new Query().doclist_sta(update,curpage,page);
        //不经过分页和排序
        map_nopage = new Query().doclist_sta(update,0,0);
        ses.setAttribute("doclist_nopage",map_nopage);

        String result = mapper.writeValueAsString(mapList);

        //4.向前端响应数据response对象
        resp.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        resp.getWriter().append(result);

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
