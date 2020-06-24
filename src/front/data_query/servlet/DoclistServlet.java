package front.data_query.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import front.data_query.dao.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/DoclistServlet")
public class DoclistServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置request和response编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //2.获取请求参数
        String updated = req.getParameter("updated");
        boolean update = Boolean.parseBoolean(updated);
        String pageSize = req.getParameter("PageSize");
        String currentPage = req.getParameter("currentPage");

        List<String> list = new ArrayList<>();
        if(req.getParameter("keyWords") != null){
            list.add(req.getParameter("keyWords"));
        }

        if(req.getParameter("disciplines") != null){
            list.add(req.getParameter("disciplines"));
        }

        if(req.getParameter("placeNames") != null){
            list.add(req.getParameter("placeNames"));
        }

        if(req.getParameter("dataTypes") != null){
            list.add(req.getParameter("dataTypes"));
        }

        if(req.getParameter("dataProductions") != null){
            list.add(req.getParameter("dataProductions"));
        }

        if(req.getParameter("spatialScales") != null){
            list.add(req.getParameter("spatialScales"));
        }

        if(req.getParameter("timeResolutions") != null){
            list.add(req.getParameter("timeResolutions"));
        }

        if(req.getParameter("spatialResolutions") != null){
            list.add(req.getParameter("spatialResolutions"));
        }

        if(req.getParameter("scales") != null){
            list.add(req.getParameter("scales"));
        }

        if(req.getParameter("satelliteSensors") != null){
            list.add(req.getParameter("satelliteSensors"));
        }

        //3.将查询结果转换成json;借助工具类ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        List<Map> mapList = new ArrayList<>();
        if(list.isEmpty()){
            int page = Integer.parseInt(pageSize);
            int current = Integer.parseInt(currentPage);
            mapList = new Query().doclist_sta(update,current,page);
        }else {
            int page = Integer.parseInt(pageSize);
            int current = Integer.parseInt(currentPage);
            Query query = new Query();
            //根据list集合里的参数，得出相关的数据id
            query.query_link(list);//该方法中用集合封装数据id
            mapList = query.doclist_dyn(update, current, page);
        }

        String result = mapper.writeValueAsString(mapList);

        //4.向前端响应数据response对象
        resp.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        resp.getWriter().append(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
