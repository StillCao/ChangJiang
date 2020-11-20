package front.data_query.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import front.data_query.dao.Query;
import front.data_query.service.CommonQuery;

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
            //2.1 获取文件列表分页查询的请求参数
            String updated = req.getParameter("updated");
            boolean update = Boolean.parseBoolean(updated);
            String pageSize = req.getParameter("PageSize");
            String currentPage = req.getParameter("currentPage");
            int page = Integer.parseInt(pageSize);
            int curpage = Integer.parseInt(currentPage);

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
            List<Integer> label_idlist = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            List<Map> mapList = new ArrayList<>();//存放最终结果

            Map map = new HashMap();//存入文件列表相关信息
            List<Map> doc_list = new ArrayList<>();//存放文件列表分页查询中间结果
            List<Map> map_nopage = new ArrayList<>();//存放未分页的文件动态查询结果

            //4. 采用session存下这次标签查询得到的id集合，以便下个接口使用
            List<Integer> firstSearch = (List<Integer>) ses.getAttribute("firstSearch");

            //type=0，根据原始数据查询标签，不会反向联动分类体系
            //需要判断全局检索后是否点了分类体系，要分两种情况。同理，点击标签反向联动分类体系表也是如此考虑。
            if(type == 0){

                if(firstSearch != null){
                    mapList = new Query().query_by_id(firstSearch);
                    //对结果进行文件列表查询
                    doc_list = new Query().doclist_dyn(update,curpage,page,firstSearch);
                    map.put("docList",doc_list);
                    mapList.add(map);

                    //保存未分页的查询结果
                    map_nopage = new Query().doclist_dyn(update,0,0,firstSearch);
                    ses.setAttribute("doclist_nopage",map_nopage);

                }else {
                    if (cate_idlist != null){
                        mapList = new Query().query_by_id(cate_idlist);
                        //对结果进行文件列表查询
                        doc_list = new Query().doclist_dyn(update,curpage,page,cate_idlist);
                        map.put("docList",doc_list);
                        mapList.add(map);

                        //保存未分页的查询结果
                        map_nopage = new Query().doclist_dyn(update,0,0,cate_idlist);
                        ses.setAttribute("doclist_nopage",map_nopage);
                    }else {
                        mapList = new Query().queryStatic();
                        //对结果进行文件列表查询
                        doc_list = new Query().doclist_sta(update,curpage,page);
                        map.put("docList",doc_list);
                        mapList.add(map);

                        //保存未分页的查询结果
                        map_nopage = new Query().doclist_sta(update,0,0);
                        ses.setAttribute("doclist_nopage",map_nopage);
                    }
                }

            }else{
                //type不为0，判断是在分类体系的条件下查询，还是全局搜素的条件下查询
                if (f_idlist!= null){
                    //经过了全局查询或分类体系查询
                    /*----2020-11-20: 添加v_id参数-----*/
                    label_idlist = new Query().query_link(list, v_ids);
                    /*----2020-11-20: 添加v_id参数-----*/
                    List<Integer> id_union = new Query().id_union(label_idlist, f_idlist);
                    mapList = new Query().query_by_id(id_union);

                    //对结果进行文件列表查询
                    doc_list = new Query().doclist_dyn(update,curpage,page,id_union);
                    map.put("docList",doc_list);
                    mapList.add(map);

                    //保存未分页的查询结果
                    map_nopage = new Query().doclist_dyn(update,0,0,id_union);
                    ses.setAttribute("doclist_nopage",map_nopage);

                    System.out.println("分类体系查询和标签查询的交集"+id_union);
                    //存放分类体系查询和标签查询后的共同集合
                    ses.setAttribute("label_idlist",id_union);
                    ses.setAttribute("f_idlist",id_union);
                }else {
                    //没经过全局查询和分类体系查询
                    /*----2020-11-20: 添加v_id参数-----*/
                    label_idlist = new Query().query_link(list, v_ids);
                    /*----2020-11-20: 添加v_id参数-----*/
                    mapList = new Query().query_by_id(label_idlist);

                    //对结果进行文件列表查询
                    doc_list = new Query().doclist_dyn(update,curpage,page,label_idlist);
                    map.put("docList",doc_list);
                    mapList.add(map);

                    //保存未分页的查询结果
                    map_nopage = new Query().doclist_dyn(update,0,0,label_idlist);
                    ses.setAttribute("doclist_nopage",map_nopage);

                    System.out.println("分类体系查询和标签查询的交集"+label_idlist);
                    //存放分类体系查询和标签查询后的共同集合
                    ses.setAttribute("label_idlist",label_idlist);
                    ses.setAttribute("f_idlist",label_idlist);
                }
            }



            String result = mapper.writeValueAsString(mapList);
            String f_str = mapper.writeValueAsString(f_idlist);
            System.out.println("上一步查询的id集合："+f_str);

            //控制台输出结果，测试用
            Object f_idlist1 = ses.getAttribute("f_idlist");
            System.out.println("LinkServlet?f_idlist="+f_idlist1);

            //5.向前端响应数据response对象
            resp.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
            resp.getWriter().append(result);
        }

        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            this.doPost(req,resp);
        }
}
