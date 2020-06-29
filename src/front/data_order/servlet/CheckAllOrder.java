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
@WebServlet("/checkAllOrder")
public class CheckAllOrder extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置编码参数等
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        //2.查询所有用户的待审核订单
        List<OrderChart> orderCharts = new OrderManage().checkAllOrder(1);
        //3.返回结果
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(orderCharts);
        resp.getWriter().append(result);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

}
