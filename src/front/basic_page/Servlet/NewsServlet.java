package front.basic_page.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import front.basic_page.Dao.QueryData;
import front.basic_page.Dao.QueryNews;
import front.basic_page.Domain.BasicInfo;
import front.basic_page.Domain.News_Total;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 菜单_新闻动态_查询三大类新闻记录1
 */
@WebServlet("/newsServlet")
public class NewsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        //1.获取请求参数，type=0，1，2为三种新闻类型
        String typeString = req.getParameter("type");
        int type = Integer.parseInt(typeString);
        //2.调用方法，查询数据库，该类新闻的最新5条记录。
        List<News_Total> news_totals = new QueryNews().queryNewsForType(type);
        //3.将记录以json格式返回给前端。
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(news_totals);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
