package back.wang.Servlet;

import front.basic_page.Servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 描述:
 * 新闻接口
 *
 * @author black-leaves
 * @createTime 2020-06-29  16:17
 */

@WebServlet("/NewsServlet")
public class NewsServlet extends BaseServlet {
    public void ListNews(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }
}
