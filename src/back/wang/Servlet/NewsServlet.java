package back.wang.Servlet;

import back.wang.Domain.Admin;
import back.wang.Domain.News;
import back.wang.Service.AdminService;
import back.wang.Service.NewsService;
import front.basic_page.Servlet.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 描述:
 * 新闻接口
 *
 * @author black-leaves
 * @createTime 2020-06-29  16:17
 */

@WebServlet("/NewsServlet")
public class NewsServlet extends BaseServlet {

    /**
     * 新闻列表
     */
    public void listNewsByKey(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> map = req.getParameterMap();
        int currentPage = Integer.parseInt(map.getOrDefault("currentPage", new String[]{"1"})[0]);
        int currentCount = Integer.parseInt(map.getOrDefault("currentCount", new String[]{"10"})[0]);

        NewsService newsService = new NewsService();
        String result = "";
        if (!map.containsKey("key")) {//全部查询
            result = newsService.allNews(currentPage, currentCount);
        } else { //带key值的模糊查询
            if (!map.containsKey("value")) {
                resp.getWriter().append("value参数未输入");
                return;
            }
            String key = map.get("key")[0];
            String value = map.get("value")[0];
            result = newsService.newsByKey(key, value, currentPage, currentCount);
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    /**
     * 新闻增加
     */
    public void addNews(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        Map<String, String[]> map = req.getParameterMap();

        News news = new News();
        BeanUtils.populate(news, map);
        NewsService newsService = new NewsService();
        String result = "";
        if (newsService.newsAdd(news)) {
            result = "1";
        } else {
            result = "0";
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }


}
