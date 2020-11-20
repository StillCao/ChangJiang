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

@WebServlet("/LinkNoSession")
public class LinkNoSession extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置request和response编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        int type = 0;
        //2.或取请求参数
        List<String> list = new ArrayList<>();
        List<Integer> v_ids = new ArrayList<>();
        if(req.getParameter("keyWords") != null){
            list.add(req.getParameter("keyWords"));
            v_ids.add(1);
            type = 1;
        }

        if(req.getParameter("disciplines") != null){
            list.add(req.getParameter("disciplines"));
            v_ids.add(2);
            type = 1;
        }

        if(req.getParameter("placeNames") != null){
            list.add(req.getParameter("placeNames"));
            v_ids.add(3);
            type = 1;
        }

        if(req.getParameter("dataTypes") != null){
            list.add(req.getParameter("dataTypes"));
            v_ids.add(4);
            type = 1;
        }

        if(req.getParameter("dataProductions") != null){
            list.add(req.getParameter("dataProductions"));
            v_ids.add(5);
            type = 1;
        }

        if(req.getParameter("spatialScales") != null){
            list.add(req.getParameter("spatialScales"));
            v_ids.add(6);
            type = 1;
        }

        if(req.getParameter("timeResolutions") != null){
            list.add(req.getParameter("timeResolutions"));
            v_ids.add(7);
            type = 1;

        }

        if(req.getParameter("scales") != null){
            list.add(req.getParameter("scales"));
            v_ids.add(8);
            type = 1;
        }

        if(req.getParameter("satelliteSensors") != null){
            list.add(req.getParameter("satelliteSensors"));
            v_ids.add(9);
            type = 1;
        }

        if(req.getParameter("spatialResolutions") != null){
            list.add(req.getParameter("spatialResolutions"));
            v_ids.add(10);
            type = 1;
        }

        //3.将查询结果转换成json;借助工具类ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        List<Map> mapList = new ArrayList<>();
        List<Integer> label_idlist = new ArrayList<>();
        HttpSession ses = req.getSession();

        if (type == 0){
            mapList = new Query().queryStatic();
        }else {
            /*----2020-11-20: 添加v_id参数-----*/
            label_idlist = new Query().query_link(list, v_ids);
            /*----2020-11-20: 添加v_id参数-----*/
            mapList = new Query().query_by_id(label_idlist);
            ses.setAttribute("f_idlist",label_idlist);//存入session方便后续查询
            System.out.println("LinkNoSession="+label_idlist);
        }

        String result = mapper.writeValueAsString(mapList);
        //5.向前端响应数据response对象
        resp.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        resp.getWriter().append(result);

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
