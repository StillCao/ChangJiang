package front.data_order.servlet;

import front.data_order.dao.InsertShopcar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Fun:
 * Created by CW on 2020/6/27 5:14 下午
 */
public class DbShopcar extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置编码参数等
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        //2.接收参数
        String uid = req.getParameter("u_id");
        String dataid = req.getParameter("data_id");
        String S_status = req.getParameter("status");
        int u_id = Integer.parseInt(uid);
        int data_id = Integer.parseInt(dataid);
        int status = Integer.parseInt(S_status);
        //3.执行入库操作
        long time = new Date().getTime();
        int i = new InsertShopcar().insertShopcar(u_id, data_id, status, time);
        //4.判断入库是否成功
        if (i == 1){
            resp.getWriter().append("收藏成功");
        }else {
            resp.getWriter().append("收藏失败");
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
