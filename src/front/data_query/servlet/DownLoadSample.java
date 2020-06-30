package front.data_query.servlet;

import front.data_query.dao.Download;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DownLoadSample")
public class DownLoadSample extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置request和response编码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //2. 获取请求参数
        String id_str = req.getParameter("id");
        int id = Integer.parseInt(id_str);

        //3. 封装请求结果
        String downloadfile = new Download().downloadfile(req, resp, id);

        //resp.setContentType("text/html;charset=utf-8");
        //4.向前端响应数据response对象
        resp.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
        resp.getWriter().append(downloadfile);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
