package front.basic_page.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import front.basic_page.Dao.QueryData;
import front.basic_page.Domain.BasicData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 数据位置空间展示接口
 *
 * @author black-leaves
 * @createTime 2020-06-16  13:55
 */

@WebServlet("/DataLocationServlet")
public class DataLocationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,String[]> map = req.getParameterMap();
        String type = map.getOrDefault("type", new String[]{"2"})[0];
        int id = Integer.parseInt(map.getOrDefault("tagId", new String[]{"9"})[0]);

//        String type = req.getParameter("type");
//        String idString = req.getParameter("tagId");
//        int id = Integer.parseInt(idString);

        QueryData queryData = new QueryData();
        List<BasicData> basicInfoPos = null;
        if (type.equals("2")){
            basicInfoPos = queryData.QueryBasicInfoByTagLevel2(id);
        }
        else if (type.equals("1")){
            basicInfoPos = queryData.QueryBasicInfoByTagLevel1(id);
        }

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(basicInfoPos);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

}
