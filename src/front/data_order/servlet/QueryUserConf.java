package front.data_order.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import front.data_order.dao.OrderManage;
import front.data_order.domain.UserConfi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Fun: 获取下载数据的当前用户信息。
 * Created by CW on 2020/6/27 5:14 下午
 */
@WebServlet("/dbShopcar")
public class QueryUserConf extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置编码参数等
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        //2.接收参数
        String uid = req.getParameter("u_id");
        int u_id = Integer.parseInt(uid);
        //3.执行入库操作
        UserConfi userConfi = new OrderManage().queryUser(u_id);
        //4.判断入库是否成功
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(userConfi);
        resp.getWriter().append(result);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
