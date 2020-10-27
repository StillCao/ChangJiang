package front.basic_page.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.wang.Domain.BasicInfoAll;
import front.basic_page.Dao.QueryData;

/**
 * @author wwx-sys
 * @time 2020-10-22-16:33
 * @description 服务量接口(下载量)
 */

@WebServlet("/ServiceCountServlet")
public class ServiceCountServlet extends HttpServlet {
    private Map<Integer, Integer> serviceCounts = null;

    @Override
    public void init() {
        //从数据库初始化

        serviceCounts = (Map<Integer, Integer>) this.getServletContext().getAttribute("serviceCounts");
        if (serviceCounts == null) {
            serviceCounts = new HashMap<Integer, Integer>();
            List<BasicInfoAll> basicInfoList = new QueryData().queryDataDownloadCounts();
            basicInfoList.forEach(basicInfo -> serviceCounts.put(basicInfo.getId(), basicInfo.getDownload_count()));
        }

        this.getServletContext().setAttribute("serviceCounts", serviceCounts);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);

        //增加下载量
        serviceCounts.put(id, serviceCounts.getOrDefault(id, 0) + 1);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(String.valueOf(serviceCounts.get(id)));


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    public void destroy() {
        //更新到数据库
        QueryData queryData = new QueryData();
        for (Integer id : serviceCounts.keySet()) {
            queryData.updateDownloadCounts(id, serviceCounts.get(id));
        }
        this.getServletContext().removeAttribute("serviceCounts");
    }
}
