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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.设置request和response编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //2.或取请求参数
        //2.1 获取文件列表分页查询的请求参数
        String updated = req.getParameter("updated");
        boolean update = Boolean.parseBoolean(updated);
        String pageSize = req.getParameter("PageSize");
        String currentPage = req.getParameter("currentPage");
        int page = Integer.parseInt(pageSize);
        int curpage = Integer.parseInt(currentPage);

        String cate_id = req.getParameter("categoryid");
        String type = req.getParameter("type");
        int i = Integer.parseInt(cate_id);
        int j = Integer.parseInt(type);



        HttpSession session = req.getSession();
        //List<Integer> firstSearch = (List<Integer>)session.getAttribute("firstSearch");
        List<Map> mapList = new ArrayList<>();
        Map map = new HashMap();//存入文件列表相关信息
        List<Map> doc_list = new ArrayList<>();//存放文件列表分页查询中间结果
        List<Map> map_nopage = new ArrayList<>();//存放未分页的文件动态查询结果


        //将id集合用session缓存
        List<Integer> cate_idlist = new Query().click_category(i, j);
        session.setAttribute("cate_idlist",cate_idlist);
        session.setAttribute("f_idlist",cate_idlist);
        System.out.println("cate_idlist="+cate_idlist);
        //在进行分类体系查询下做标签查询
        mapList = new Query().query_by_id(cate_idlist);
        map_nopage = new Query().doclist_dyn(update,0,0,cate_idlist);
        session.setAttribute("doclist_nopage",map_nopage);

        //对结果进行文件列表查询
        doc_list = new Query().doclist_dyn(update,curpage,page,cate_idlist);
        map.put("docList",doc_list);
        mapList.add(map);

        //控制台输出结果，测试用
        System.out.println("CategoryServlet?f_idlist="+cate_idlist);

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
