package front.data_order.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import front.data_order.dao.InsertShopcar;
import front.data_order.domain.Shopcar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Fun:
 * Created by CW on 2020/6/27 5:14 下午
 */
@WebServlet("/deleteShopcar")
public class DeleteShopcar extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置编码参数等
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        //2.接收参数
        String uid = req.getParameter("u_id");
        String dataId = req.getParameter("data_id");
        int u_id = Integer.parseInt(uid);
        int data_id = Integer.parseInt(dataId);
        //3.执行查询操作
        int rows = new InsertShopcar().deleteShopcar(u_id, data_id);

        //4.判断删除是否成功
        if (rows == 1){
            resp.getWriter().append("true");
        }else{
            resp.getWriter().append("false");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
