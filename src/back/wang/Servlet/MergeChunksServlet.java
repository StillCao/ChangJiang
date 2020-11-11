package back.wang.Servlet;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import front.basic_page.Dao.QueryData;

/**
 * @author wwx-sys
 * @time 2020-11-11-10:41
 * @description 合并文件切片
 */

@WebServlet("/MergeChunksServlet")
public class MergeChunksServlet extends HttpServlet {
    private final String filePaths = "D:/fileTest";
    private final String filePathTemp = "D:/fileTemp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String fileName = req.getParameter("filename"); //最后合并的文件名
        String guid = req.getParameter("guid"); //临时文件所在的目录名
        String idString = req.getParameter("id");
        String filePath = "";

        File file = new File(filePathTemp + File.separator + guid);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                File partFile = new File(filePaths + File.separator + fileName);
                for (int i = 1; i <= files.length; i++) {
                    File s = new File(filePathTemp + File.separator + guid, i + ".part");
                    FileOutputStream destTempfos = new FileOutputStream(partFile, true);
                    FileUtils.copyFile(s, destTempfos);
                    destTempfos.close();
                }
                if (partFile.exists()){
                    filePath = partFile.getPath();
                }
                FileUtils.deleteDirectory(file);
            }
        }

        int id = Integer.parseInt(idString);
        new QueryData().updateBasicDataPathById(id,filePath);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域问题，让返回结果可远程调用
        resp.getWriter().append(fileName).append("合并成功！").append("存储路径:").append(filePath);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
