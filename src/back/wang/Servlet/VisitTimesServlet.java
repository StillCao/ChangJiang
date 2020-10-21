package back.wang.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import front.basic_page.Dao.QueryData;

/**
 * @author wwx-sys
 * @time 2020-10-21-9:43
 * @description 网站访问量接口
 */

@WebServlet("/VisitTimesServlet")
public class VisitTimesServlet extends HttpServlet {
    private int totalCount = 0;//默认访问量为0

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取网站访问量
        Object count = req.getServletContext().getAttribute("visitCount");
        //判断count是否为null，不为null表示曾经访问过，直接将count赋值给totalCount
        if (count != null) {
            totalCount = (int) count;
        }
        else {
            totalCount =  new QueryData().queryStatisticsNumByName("visitCount");
        }

        totalCount += 1;
        //访问次数累加
        req.getServletContext().setAttribute("visitCount", totalCount);
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append("网站访问量为：").append(String.valueOf(totalCount));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    public void destroy() {
        //更新到数据库
        QueryData queryData = new QueryData();
        queryData.updateStatisticsNumByName("visitCount",totalCount);
        this.getServletContext().removeAttribute("visitCount");
    }


}
