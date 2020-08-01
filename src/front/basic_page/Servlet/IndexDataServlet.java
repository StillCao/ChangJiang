package front.basic_page.Servlet;

import back.wang.Domain.BasicInfoAll;

import com.fasterxml.jackson.databind.ObjectMapper;
import front.basic_page.Dao.QueryData;
import front.basic_page.Domain.BasicInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

@WebServlet("/IndexDataServlet")
public class IndexDataServlet extends HttpServlet {
    private Map<Integer, Integer> dataCounts = null;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String typeString = req.getParameter("type");
        int type = Integer.parseInt(typeString);

        List<BasicInfo> basicInfoList = null;

        QueryData queryData = new QueryData();
        if (type == 1) {//推荐数据
            return;
        } else if (type == 2) {//最新数据
            basicInfoList = queryData.QueryLatest8BasicInfo();
        } else if (type == 3) {//热门数据
            basicInfoList = new ArrayList<>();
            List<Map.Entry<Integer, Integer>> infoIds = new ArrayList<>(dataCounts.entrySet());
            infoIds.sort((o1, o2) -> (o2.getValue() - o1.getValue()));
            for (Map.Entry<Integer, Integer> hot : infoIds) {
                if (basicInfoList.size() >= 8) {
                    break;
                }
                int id = hot.getKey();
                BasicInfo basicInfo = queryData.queryDataById(id);
                if (basicInfo != null) {
                    basicInfoList.add(basicInfo);
                }
            }
        }


        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(basicInfoList);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    public void init() {
        //从数据库初始化

        dataCounts = (Map<Integer, Integer>) this.getServletContext().getAttribute("dataCounts");
        if (dataCounts == null) {
            dataCounts = new HashMap<>();
            List<BasicInfoAll> basicInfoList = new QueryData().queryDataClickCounts();
            basicInfoList.forEach(basicInfo -> dataCounts.put(basicInfo.getId(), basicInfo.getClick_count()));
        }

        this.getServletContext().setAttribute("dataCounts", dataCounts);

    }

    @Override
    public void destroy() {
        //更新到数据库
        QueryData queryData = new QueryData();
        for (Integer id : dataCounts.keySet()) {
            queryData.updateClickCounts(id, dataCounts.get(id));
        }
        this.getServletContext().removeAttribute("dataCounts");
    }
}
