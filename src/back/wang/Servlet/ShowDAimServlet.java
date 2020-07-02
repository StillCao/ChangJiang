package back.wang.Servlet;

import back.wang.Dao.DownAimInsert;
import back.wang.Domain.BasicInfoAll;
import back.wang.Domain.Downaim;
import back.wang.Domain.Order_confirm;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import front.user_io.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 描述:
 * 下载 目的预览
 *
 * @author black-leaves
 * @createTime 2020-07-01  8:51
 */

@WebServlet("/ShowDAimServlet")
public class ShowDAimServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String statusString = req.getParameter("status");
        int status = Integer.parseInt(statusString);

        DownAimInsert insert = new DownAimInsert();

        List<Order_confirm> orders = insert.queryOrderByStatus(status);
        JSONArray array = new JSONArray();
        orders.forEach(order -> {
            //object初始化
            JSONObject object = new JSONObject();
            object.put("status", status);

            int orderId = order.getId();
            String orderCode = order.getOrderCode();
            object.put("id", orderId);
            object.put("orderCode", orderCode);
            Order_confirm order_confirm = insert.QueryOrderConfirmAllById(orderId);
            if (order_confirm == null) {
                object.put("downaim", "");
                object.put("user", "");
            } else {
                int downAimId = order_confirm.getDown_aim();
                int userId = order_confirm.getUserId();
                int dataId = order_confirm.getDataId();
                Downaim downaim = insert.QueryDownAimById(downAimId);
                User user = insert.queryUserById(userId);
                BasicInfoAll basicInfo = insert.queryBasicInfoById(dataId);
                object.put("downaim", downaim);
                object.put("user", user);
                object.put("basicInfo", basicInfo);
            }
            array.add(object);
        });

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(JSON.toJSONString(array));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
