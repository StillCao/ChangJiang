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

@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.设置request和response编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //2.或取请求参数
        String cate_id = req.getParameter("categoryid");
        String type = req.getParameter("type");
        int i = Integer.parseInt(cate_id);
        int j = Integer.parseInt(type);

        HttpSession session = req.getSession();
        //List<Integer> firstSearch = (List<Integer>)session.getAttribute("firstSearch");
        List<Map> mapList = new ArrayList<>();
        //将id集合用session缓存
        List<Integer> cate_idlist = new Query().click_category(i, j);
        session.setAttribute("cate_idlist",cate_idlist);
        session.setAttribute("f_idlist",cate_idlist);
        System.out.println("cate_idlist="+cate_idlist);
        //在进行分类体系查询下做标签查询
        mapList = new Query().query_by_id(cate_idlist);

        //3.将查询结果转换成json;借助工具类ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(mapList);

        //4.向前端响应数据response对象
        resp.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        resp.getWriter().append(result);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

}
