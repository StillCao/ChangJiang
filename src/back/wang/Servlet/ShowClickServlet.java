package back.wang.Servlet;

import front.basic_page.Dao.QueryData;
import front.basic_page.Servlet.ShowDataServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 描述:
 * 显示数据点击量
 *
 * @author black-leaves
 * @createTime 2020-07-03  19:48
 */

@WebServlet("/ShowClickServlet")
public class ShowClickServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);
        int count = 0;

        Map<Integer,Integer> dataCounts = (Map<Integer, Integer>) this.getServletContext().getAttribute("dataCounts");
        if (dataCounts != null) {
            count = dataCounts.get(id);
        }
        else {
            count = new QueryData().getClickCountById(id);
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(String.valueOf(count));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
