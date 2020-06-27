package front.data_query.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import front.data_query.dao.Query;
import front.data_query.domain.Basic_info;

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

@WebServlet("/SecondSearch")
public class SecondSearch extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置request和response编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //2.获取请求参数
        String searchWord = req.getParameter("searchWord");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");

        //3. 调用方法，封装结果
        //3.1 先取出上一步得到的未分页的文件列表中的数据list集合
        List<Basic_info> infoList = new ArrayList<>();
        HttpSession ses = req.getSession();
        List<Map> doclist_nopage = (List<Map>) ses.getAttribute("doclist_nopage");
        for (Map map : doclist_nopage) {
            infoList = (List<Basic_info>) map.get("features");
        }

        List<Map> mapList = new ArrayList<>();
        if(startTime != null && endTime != null){
            int start = Integer.parseInt(startTime);
            int end = Integer.parseInt(endTime);
            mapList = new Query().search_sec(searchWord, searchWord, start, end, infoList);
        }else {
            if(startTime == null && endTime == null){
                int start = 0;
                int end = 0;
                mapList = new Query().search_sec(searchWord, searchWord, start, end, infoList);
            }else {
                if (startTime == null && endTime != null){
                    int start = 0;
                    int end = Integer.parseInt(endTime);
                    mapList = new Query().search_sec(searchWord, searchWord, start, end, infoList);
                }else {
                    int start = Integer.parseInt(startTime);
                    int end = 0;
                    mapList = new Query().search_sec(searchWord, searchWord, start, end, infoList);
                }
            }
        }

        //4. 将查询结果转换成json;借助工具类ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(mapList);

        //5. 向前端响应数据response对象
        resp.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        resp.getWriter().append(result);

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
