package back.wang.Servlet;

import com.alibaba.fastjson.JSON;
import front.basic_page.Dao.QueryData;
import front.basic_page.Domain.TypeLevel1;
import front.basic_page.Domain.TypeLevel2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 描述:
 * 数据类型展示接口
 *
 * @author black-leaves
 * @createTime 2020-06-24  10:45
 */

@WebServlet("/ShowDTServlet")
public class ShowDTServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String typeString = req.getParameter("type");
        int type = Integer.parseInt(typeString);

        QueryData queryData = new QueryData();
        String result = "";
        if (type == 1) { //一级数据类型
            List<TypeLevel1> typeLevel1s = queryData.QueryTagLevel1All();
            result = JSON.toJSONString(typeLevel1s);
        } else {  //二级数据类型
            String type1IdString = req.getParameter("t1_id");
            int type1Id = Integer.parseInt(type1IdString);
            List<TypeLevel2> TypeLevel2 = queryData.QueryTagLevel2By1Id(type1Id);
            result = JSON.toJSONString(TypeLevel2);
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
