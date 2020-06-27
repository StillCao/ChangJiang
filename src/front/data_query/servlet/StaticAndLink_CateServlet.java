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

@WebServlet("/StaticAndLink_CateServlet")
public class StaticAndLink_CateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置request和response编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //2.获取请求参数，前端不传入参数。参数来源于之前的全局查询和分类体系查询
        HttpSession ses = req.getSession();
        List<Map> mapList = new ArrayList<>();

        //3. yongsession对象获取之前的参数
        List<Integer> firstSearch = (List<Integer>)ses.getAttribute("firstSearch");
        List<Integer> label_idlist = (List<Integer>) ses.getAttribute("label_idlist");

        if (firstSearch != null){
            if (label_idlist != null){
                //存在S1C,S1CL,S1L,L，四种种情况因为是互斥的，故label_idlist表示其中一种情形
                mapList = new Query().query_lab_to_cate(label_idlist);
            }else {
                //只存在S1这一种情况，但firstSearch表示该情形的结果与分类体系无关
                mapList = new Query().query_lab_to_cate(null);
            }
        }else {
            if (label_idlist != null){
                //存在CL，L两种情况，两种情况是互斥的，故label_idlist表示其中一种情形
                mapList = new Query().query_lab_to_cate(label_idlist);
            }else {
                //没经过标签查询，直接对所有数据进行分类体系查询
                mapList = new Query().query_lab_to_cate(null);
            }
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