package front.data_order.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import front.data_order.dao.OrderManage;
import front.data_order.domain.OrderChart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Fun: 获取已经提交（下载目的）的订单信息。
 * Created by CW on 2020/6/27 5:14 下午
 */
@WebServlet("/loadPostOrder")
public class LoadPostOrder extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置编码参数等
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        //2.接收参数
        String uid = req.getParameter("u_id");
        String S_orderStatus = req.getParameter("orderStatus");
        int u_id = Integer.parseInt(uid);
        int orderStatus = Integer.parseInt(S_orderStatus);
        //3.执行入库操作
        List<OrderChart> orderCharts = new OrderManage().queryWaitingOrder(u_id, orderStatus);
        //4.待提交订单入库
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(orderCharts);
        resp.getWriter().append(result);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

}
