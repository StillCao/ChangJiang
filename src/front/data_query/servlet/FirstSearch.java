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

/**
 * 该接口会与分类体系、标签、文件列表三个模块有正向联动，但不存在反向联动
 */
@WebServlet("/FirstSearch")
public class FirstSearch extends HttpServlet{

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.设置request和response编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //2. 获取请求参数
        String searchWord = req.getParameter("searchWord");
        String name = searchWord;
        String uper_place = searchWord;

        String updated = req.getParameter("updated");
        boolean update = Boolean.parseBoolean(updated);
        String pageSize = req.getParameter("PageSize");
        String currentPage = req.getParameter("currentPage");
        int page = Integer.parseInt(pageSize);
        int curpage = Integer.parseInt(currentPage);

        //3. 查询数据
        List<Integer> list = new Query().search_fir(name, uper_place);
        List<Map> map_nopage = new ArrayList<>();//存放未分页的文件动态查询结果
        //3.1 先用session暂存该数据id集合，方便后续筛选
        HttpSession session = req.getSession();
        session.setAttribute("firstSearch",list);
        map_nopage = new Query().doclist_dyn(update,0,0,list);
        session.setAttribute("doclist_nopage",map_nopage);

        //控制台输出结果，测试用
        System.out.println("FirstSearch?f_idlist="+list);

        //3.2 设置f_idlist，放置最终集合
        session.setAttribute("f_idlist",list);
        session.getAttributeNames();

        //3.2 查询标签结果

        List<Map> mapList = new Query().query_by_id(list);

        //3.3 根据id集合查询数据基本信息
        List<Map> mapList1 = new Query().doclist_dyn(update, curpage, page, list);
        Map map = new HashMap();
        map.put("docList",mapList1);
        mapList.add(map);
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(mapList);

        //4.向前端响应数据response对象
        resp.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        resp.getWriter().append(result);

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
