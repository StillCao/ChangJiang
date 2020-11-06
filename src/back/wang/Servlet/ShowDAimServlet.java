package back.wang.Servlet;

import back.wang.Dao.DownAimInsert;
import back.wang.Domain.BasicInfoAll;
import back.wang.Domain.Downaim;
import back.wang.Domain.Order_confirm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import back.wang.Service.DownAimService;
import front.user_io.domain.User;

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
 * 下载 目的预览
 *
 * @author black-leaves
 * @createTime 2020-07-01  8:51
 */

@WebServlet("/ShowDAimServlet")
public class ShowDAimServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> map = req.getParameterMap();
        int currentPage = Integer.parseInt(map.getOrDefault("currentPage", new String[]{"1"})[0]);
        int currentCount = Integer.parseInt(map.getOrDefault("currentCount", new String[]{"10"})[0]);
        String statusString = req.getParameter("status");
        int status = Integer.parseInt(statusString);
        String result = "";

        if (!map.containsKey("key")) {//全部查询
            result = new DownAimService().queryOrderByPageLike(currentPage, currentCount, "orderStatus", String.valueOf(status), status);
        } else { //带key值的模糊查询
            if (!map.containsKey("value")) {
                resp.getWriter().append("value参数未输入");
                return;
            }
            String key = map.get("key")[0];
            String value = map.get("value")[0];
            result = new DownAimService().queryOrderByPageLike(currentPage, currentCount, key, value, status);
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
