package front.basic_page.Servlet;

import front.basic_page.service.CatalogService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 描述:
 * 目录树接口
 *
 * @author black-leaves
 * @createTime 2020-06-17  15:33
 */

@WebServlet("/CatalogServlet")
public class CatalogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CatalogService catalogService = new CatalogService();
        String result = catalogService.getCatalogJsonString();

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
