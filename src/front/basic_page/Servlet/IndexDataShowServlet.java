package front.basic_page.Servlet;

import front.basic_page.service.IndexDataShowService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 描述:
 * 首页数据展示
 *
 * @author black-leaves
 * @createTime 2020-06-18  9:34
 */

@WebServlet("/IndexDataShowServlet")
public class IndexDataShowServlet extends BaseServlet {

    /**
     * 各一级类型目录下的5条数据
     */
    public void level1Data(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int num = 5;
        String url = "http://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        IndexDataShowService showService = new IndexDataShowService();
        String result = showService.getLevel1Data5(num, url);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);

    }

    public void subjectData(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String typeString = req.getParameter("type");
        int type = Integer.parseInt(typeString);
        int num = 10;
        IndexDataShowService showService = new IndexDataShowService();
        String result = showService.getSubjectData(num, type);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }
}
