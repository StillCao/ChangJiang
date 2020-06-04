package front.basic_page.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import front.basic_page.Dao.QueryData;
import front.basic_page.Domain.BasicInfo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/IndexDataServlet")
public class IndexDataServlet extends HttpServlet {
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
            return;
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
}
