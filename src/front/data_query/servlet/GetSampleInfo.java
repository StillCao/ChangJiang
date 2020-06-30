package front.data_query.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import front.data_query.dao.Download;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/GetSampleInfo")
public class GetSampleInfo extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.设置request和response编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //2. 获取请求参数
        String id_str = req.getParameter("id");
        int id = Integer.parseInt(id_str);

        //3. 封装请求结果
        String name = new Download().getSampleName(id);
        Map map = new HashMap();
        if (name != null){
            map.put("name",name);
            StringBuffer currentURL =  req.getRequestURL();
            System.out.println(currentURL);

            String requestURI = req.getRequestURI();
            System.out.println("相对="+requestURI);
            String url_str = currentURL.toString();
            String t_str = url_str.replace(requestURI, "");
            //当前工程名
            String contextPath = req.getContextPath();
            System.out.println("工程名="+contextPath);

            String url= t_str+contextPath+"/DownLoadSample?id="+id;
            System.out.println("download_url="+url);
            map.put("downloadURL",url);
        }else{
            map.put("name",name);
            map.put("downloadURL",null);
        }

        //4.将查询结果转换成json;借助工具类ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(map);

        //5.向前端响应数据response对象
        resp.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        resp.getWriter().append(result);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
