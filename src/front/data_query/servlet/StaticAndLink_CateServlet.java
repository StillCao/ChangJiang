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

@WebServlet("/StaticAndLink_CateServlet")
public class StaticAndLink_CateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置request和response编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //2.获取请求参数
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

        List<Map> mapList = new ArrayList<>();
        if(list.size() == 0){
            //说明没有传入参数，查询的是分类体系静态页面
            mapList = new Query().query_lab_to_cate();
        }else {
            //说明传入了标签参数，查询的是标签查询对于分类体系的联动改变
            Query query = new Query();
            query.query_link(list);
            mapList = query.query_lab_to_cate();
        }

        //3.将查询结果转换成json;借助工具类ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(mapList);

        //4.向前端响应数据response对象
        resp.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        resp.getWriter().append(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}