package front.basic_page.Servlet;

import back.wang.Domain.BasicInfoAll;

import com.fasterxml.jackson.databind.ObjectMapper;

import front.basic_page.Dao.QueryData;
import front.basic_page.Dao.QueryNews;
import front.basic_page.Domain.BasicInfo;
import front.basic_page.Domain.DataDetails;
import front.basic_page.Domain.News_Total;
import front.basic_page.service.DataDetailsContr;
import front.data_query.dao.Query;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/showDataServlet")
public class ShowDataServlet extends HttpServlet {
//    private Map<Integer, Integer> dataCounts = null;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        //1.获取请求参数，type=0，1，2为三种新闻类型
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);
//
//        //增加点击量
//        dataCounts.put(id, dataCounts.getOrDefault(id, 0) + 1);

        //2.根据id,查询数据详情页
        DataDetails dataDetails = new DataDetailsContr().dataDetails(id);


        //3.将记录以json格式返回给前端。
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(dataDetails);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

//    @Override
//    public void init() throws ServletException {
//        //从数据库初始化
//
//        dataCounts = (Map<Integer, Integer>) this.getServletContext().getAttribute("dataCounts");
//        if (dataCounts == null) {
//            dataCounts = new HashMap<>();
//            List<BasicInfoAll> basicInfoList = new QueryData().queryDataClickCounts();
//            basicInfoList.forEach(basicInfo -> dataCounts.put(basicInfo.getId(), basicInfo.getClick_count()));
//        }
//
//        this.getServletContext().setAttribute("dataCounts", dataCounts);
//
//    }
//
//    @Override
//    public void destroy() {
//        //更新到数据库
//        QueryData queryData = new QueryData();
//        for (Integer id : dataCounts.keySet()) {
//            queryData.updateClickCounts(id, dataCounts.get(id));
//        }
//        this.getServletContext().removeAttribute("dataCounts");
//    }

}
