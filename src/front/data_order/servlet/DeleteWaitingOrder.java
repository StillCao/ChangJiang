package front.data_order.servlet;

import front.data_order.dao.OrderManage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Fun:
 * Created by CW on 2020/6/27 5:14 下午
 */
@WebServlet("/delWaitingOrder")
public class DeleteWaitingOrder extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置编码参数等
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        //2.接收参数
        String uid = req.getParameter("u_id");
        String dataid = req.getParameter("data_id");
        int u_id = Integer.parseInt(uid);
        int data_id = Integer.parseInt(dataid);
        //3.删除待提交订单，状态置为-1
        int rows = new OrderManage().updateShopcar(u_id, data_id, -1);

        //4.返回结果
        if (rows == 1){
            resp.getWriter().append("true");
        }else {
            resp.getWriter().append("false");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

}
