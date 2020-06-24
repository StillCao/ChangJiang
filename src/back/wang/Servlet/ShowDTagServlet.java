package back.wang.Servlet;

import com.alibaba.fastjson.JSON;
import front.basic_page.Dao.QueryData;
import front.basic_page.Domain.Attr_key;
import front.basic_page.Domain.Attr_value;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 描述:
 * 展示数据标签
 *
 * @author black-leaves
 * @createTime 2020-06-24  11:20
 */

@WebServlet("/ShowDTagServlet")
public class ShowDTagServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String typeString = req.getParameter("type");
        int type = Integer.parseInt(typeString);
        QueryData queryData = new QueryData();
        String result = "";
        if (type == 1) { // 标签key
            List<Attr_key> keys = queryData.QueryAttrKeyAll();
            result = JSON.toJSONString(keys);
        } else { //标签值
            String v_id_kString = req.getParameter("v_id_k");
            int v_id_k = Integer.parseInt(v_id_kString);
            List<Attr_value> values = queryData.QueryAttrValueByKeyId(v_id_k);
            result = JSON.toJSONString(values);
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
