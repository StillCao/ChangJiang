package back.wang.Servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.wang.Dao.BasicDataQuery;
import back.wang.Domain.BasicInfoAll;
import front.data_query.dao.Download;

/**
 * @author wwx-sys
 * @time 2020-11-12-15:40
 * @description 下载数据 file_url
 */

@WebServlet("/DownLoadFile")
public class DownLoadFile extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域问题，让返回结果可远程调用

        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);

        BasicInfoAll basicInfoAll = new BasicDataQuery().queryDataById(id);
        String filePath = basicInfoAll.getFile_url();
        if (filePath != null  && new File(filePath).exists() ){
            String[] splits = filePath.split("\\\\");
            String name = filePath;
            if (splits.length != 0) {
                name = splits[splits.length - 1];
            }
            String downloadFile = new Download().downloadfile(resp, name, filePath);

            resp.getWriter().append(downloadFile);
        }
        else {
            resp.getWriter().append("该数据的数据文件不存在！");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
