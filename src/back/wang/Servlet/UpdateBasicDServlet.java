package back.wang.Servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.wang.Dao.BasicDataQuery;

/**
 * @author wwx-sys
 * @time 2020-10-29-9:56
 * @description 基础数据编辑
 */

@WebServlet("/UpdateBasicDServlet")
public class UpdateBasicDServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> map = req.getParameterMap();
        String result = "0";

        String sql = sqlBuilder(map);
        if (sql != null) {
            System.out.println(sql);
            if (new BasicDataQuery().updateBasicData(sql)) {
                result = "1";
            }
        }


        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    //sql字符串拼接函数
    public String sqlBuilder(Map<String, String[]> stringMap) {
        StringBuilder sql = new StringBuilder("update basic_info set ");
        if (stringMap.containsKey("id")) {
            stringMap.keySet().forEach(key -> {
                if (!key.equals("id")) {
//                    if (key.equals("up_id") || key.equals("da_type")){ //int 型
//                        sql.append(key).append("=").append(stringMap.get(key)[0]).append(",");
//                    }
//                    else {
                    sql.append(key).append("=").append("'").append(stringMap.get(key)[0]).append("'").append(",");
//                    }
                }
            });
            if (sql.toString().endsWith(",") && sql.length() > 1) {
                sql.deleteCharAt(sql.length() - 1);
            }

            sql.append(" where id = ").append(stringMap.get("id")[0]).append(";");
            return sql.toString();
        } else return null;

    }
}
