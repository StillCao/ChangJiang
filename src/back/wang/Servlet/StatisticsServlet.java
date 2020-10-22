package back.wang.Servlet;

import java.io.File;
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
 * @time 2020-10-21-9:43
 * @description 网站统计信息接口 包括(平台用户人数，页面访问量，服务量，数据资源量)
 */

@WebServlet("/StatisticsServlet")
public class StatisticsServlet extends HttpServlet {
    private int totalCount = 0;//默认访问量为0

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        //平台用户人数
        QueryData queryData = new QueryData();
        int userCount = queryData.queryUserCount();
        resp.getWriter().append("平台用户人数：").append(String.valueOf(userCount)).append("人<br/>");

        //获取页面访问量
        Object count = req.getServletContext().getAttribute("visitCount");
        //判断count是否为null，不为null表示曾经访问过，直接将count赋值给totalCount
        if (count != null) {
            totalCount = (int) count;
        } else {
            totalCount = queryData.queryStatisticsNumByName("visitCount");
        }

        totalCount += 1;
        //访问次数累加
        req.getServletContext().setAttribute("visitCount", totalCount);
        resp.getWriter().append("页面访问量：").append(String.valueOf(totalCount)).append("人次<br/>");

        //服务量
        int serviceCountSum = 0;
        Object serviceCounts = this.getServletContext().getAttribute("serviceCounts");
        if (serviceCounts == null) {
            serviceCountSum = queryData.getSumDownloadCount();
        } else {
            Map<Integer, Integer> serviceCountMap = (Map<Integer, Integer>) serviceCounts;
            for (int serviceCount : serviceCountMap.values()) {
                serviceCountSum += serviceCount;
            }
        }

        resp.getWriter().append("服务量：").append(String.valueOf(serviceCountSum)).append("人次<br/>");

        //数据资源量
//        File fileDir = new File("C:\\Users\\Administrator\\Desktop\\长江中游地学数据集\\");
        File fileDir = new File("D:\\doc");
        float size = getDirSize(fileDir);
        int level = 0;
        while (size >= 1024 && level <= 4) {
            size = size / 1024;
            level += 1;
        }
        String unit = "";
        switch (level) {
            case 0:
                unit = "bytes";
                break;
            case 1:
                unit = "KB";
                break;
            case 2:
                unit = "MB";
                break;
            case 3:
                unit = "GB";
                break;
            case 4:
                unit = "TB";
                break;
        }
        ;


        resp.getWriter().append("数据资源量：").append(String.valueOf(size)).append(unit).append("<br/>");

        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
    }

    public float getDirSize(File fileDir) {
        File[] files = fileDir.listFiles();
        float fileSize = 0;
        if (files != null) {
            for (File file : files) {

                if (file.isFile()) {
//                    System.out.println(file.getName() + "  :  " + file.length());
                    fileSize += file.length();
                } else {
                    fileSize += getDirSize(file);
                }
            }
        }
        return fileSize;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    public void destroy() {
        //更新到数据库
        QueryData queryData = new QueryData();
        queryData.updateStatisticsNumByName("visitCount", totalCount);
        this.getServletContext().removeAttribute("visitCount");
    }


}
