package back.wang.Servlet;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.wang.Dao.AdminQuery;
import back.wang.Domain.DataConnector;

/**
 * @author wwx-sys
 * @time 2021-03-09-20:54
 * @description 根据数据ID获取联系者信息记录
 */
@WebServlet("/GetDaConServlet")
public class GetDaConServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        String idString = req.getParameter("basic_id");
        int id = Integer.parseInt(idString);

        DataConnector dataConnector = new AdminQuery().queryDaConByBasicId(id);
        if (dataConnector != null) {
            resp.getWriter().append(JSON.toJSONString(dataConnector));
        }
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
