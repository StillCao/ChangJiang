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

@WebServlet("/LinkServlet")
public class LinkServlet extends HttpServlet{

        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            //1.设置request和response编码格式
            req.setCharacterEncoding("utf-8");
            resp.setContentType("text/html;charset=utf-8");

            //获取session对象，如果在查询之前没有做分类查询，标签查询则在原始数据上进行；
            //如果做了分类体系查询，标签查询则在上述结果的基础上进行查询
            HttpSession ses = req.getSession();
            List<Integer> cate_idlist = (List<Integer>) ses.getAttribute("cate_idlist");
            List<Integer> f_idlist = (List<Integer>) ses.getAttribute("f_idlist");

            int type = 0;
            //2.或取请求参数
            List<String> list = new ArrayList<>();
            if(req.getParameter("keyWords") != null){
                list.add(req.getParameter("keyWords"));
                type = 1;
            }

            if(req.getParameter("disciplines") != null){
                list.add(req.getParameter("disciplines"));
                type = 1;
            }

            if(req.getParameter("placeNames") != null){
                list.add(req.getParameter("placeNames"));
                type = 1;
            }

            if(req.getParameter("dataTypes") != null){
                list.add(req.getParameter("dataTypes"));
                type = 1;
            }

            if(req.getParameter("dataProductions") != null){
                list.add(req.getParameter("dataProductions"));
                type = 1;
            }

            if(req.getParameter("spatialScales") != null){
                list.add(req.getParameter("spatialScales"));
                type = 1;
            }

            if(req.getParameter("timeResolutions") != null){
                list.add(req.getParameter("timeResolutions"));
                type = 1;
            }

            if(req.getParameter("spatialResolutions") != null){
                list.add(req.getParameter("spatialResolutions"));
                type = 1;
            }

            if(req.getParameter("scales") != null){
                list.add(req.getParameter("scales"));
                type = 1;
            }

            if(req.getParameter("satelliteSensors") != null){
                list.add(req.getParameter("satelliteSensors"));
                type = 1;
            }

            //3.将查询结果转换成json;借助工具类ObjectMapper
            List<Integer> label_idlist = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            List<Map> mapList = new ArrayList<>();
            List<Integer> firstSearch = (List<Integer>) ses.getAttribute("firstSearch");

            if(type == 0){
                if(firstSearch != null){
                    mapList = new Query().query_by_id(firstSearch);
                }else {
                    if (cate_idlist != null){
                        mapList = new Query().query_by_id(cate_idlist);
                    }else {
                        mapList = new Query().queryStatic();
                    }
                }
            }else{
                //type不为0，判断是在分类体系的条件下查询，还是全局搜素的条件下查询
                if (f_idlist!= null){
                    //经过了全局查询或分类体系查询
                    label_idlist = new Query().query_link(list);
                    List<Integer> id_union = new Query().id_union(label_idlist, f_idlist);
                    mapList = new Query().query_by_id(id_union);
                    System.out.println("分类体系查询和标签查询的交集"+id_union);
                    //存放分类体系查询和标签查询后的共同集合
                    ses.setAttribute("label_idlist",id_union);
                    ses.setAttribute("f_idlist",id_union);
                }else {
                    //没经过全局查询和分类体系查询
                    label_idlist = new Query().query_link(list);
                    mapList = new Query().query_by_id(label_idlist);
                    System.out.println("标签查询的集合"+label_idlist);
                    //存放分类体系查询和标签查询后的共同集合
                    ses.setAttribute("label_idlist",label_idlist);
                    ses.setAttribute("f_idlist",label_idlist);
                }
            }
            String result = mapper.writeValueAsString(mapList);
            String f_str = mapper.writeValueAsString(f_idlist);
            System.out.println("上一步查询的id集合："+f_str);

            //4.向前端响应数据response对象
            resp.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
            resp.getWriter().append(result);
        }

        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            this.doPost(req,resp);
        }
}
