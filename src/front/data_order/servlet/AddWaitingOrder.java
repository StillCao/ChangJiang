package front.data_order.servlet;

import com.mysql.cj.x.protobuf.MysqlxCrud;

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
 * Modified by Wwx on 2020/11/06 10:35 上午 加入订单状态为-2即审核未通过订单 的判断
 */
@WebServlet("/addWaitingOrder")
public class AddWaitingOrder extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置编码参数等
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        //2.接收参数
        String uid = req.getParameter("u_id");
        String dataid = req.getParameter("data_id");
        String S_status = req.getParameter("orderStatus");
        int u_id = Integer.parseInt(uid);
        int data_id = Integer.parseInt(dataid);
        int status = Integer.parseInt(S_status);

        //3.执行入库操作
        int rows = new OrderManage().judgeWaitingOrder(u_id, data_id);
        if (rows == 1) {
            int s = new OrderManage().queryOrderStatus(u_id, data_id);
            if (s == 0 || s == 1) {
                //该数据订单已经为待提交状态或者在审核状态，不需要重新提交。
                resp.getWriter().append("OrderExist");
            } else if (s == 2) {
                resp.getWriter().append("OrderDone");
            } else if (s == -1 || s == -2) {
                //订单处于被删除状态 -1 或者 处于审核未通过订单 -2 ，则修改订单状态为0 ，成功加入待提交订单。
                int i = new OrderManage().updateShopcar(u_id, data_id, 0);
                //待提交订单入库
                if (i == 1) {
                    resp.getWriter().append("true");
                } else {
                    resp.getWriter().append("false");
                }
            }

        } else if (rows == 0) {  //订单首次建立
            int i = new OrderManage().insertWaitingOrder(u_id, data_id, status);
            //4.待提交订单入库
            if (i == 1) {
                resp.getWriter().append("true");
            } else {
                resp.getWriter().append("false");
            }
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

}
