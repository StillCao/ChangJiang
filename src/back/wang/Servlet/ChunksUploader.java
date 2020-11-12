package back.wang.Servlet;

import com.alibaba.fastjson.JSON;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.wang.Domain.Chunk;
import back.wang.Service.UpLoadService;

/**
 * @author wwx-sys
 * @time 2020-11-10-9:58
 * @description 文件切片上传
 */

@WebServlet("/ChunksUploader")
public class ChunksUploader extends HttpServlet {

    private final String filePath = "D:/fileTest";
    private final String filePathTemp = "D:/fileTemp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(req)) {
            throw new RuntimeException("当前请求不支持文件上传！");
        }

        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域问题，让返回结果可远程调用
//        resp.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,OPTIONS");

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024 * 1);

        String tempPath = this.getServletContext().getRealPath("/WEB-INF/tempFile");
        File temp = new File(tempPath);
        if (!temp.exists()) {
            temp.mkdir();
        }
        factory.setRepository(temp);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("utf-8");

        List<FileItem> items = null;
        Chunk chunk = new Chunk();

        try {
            items = upload.parseRequest(req);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        if (items != null) {
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("utf-8");
                    switch (name) {
                        case "id":
                            long id = Long.parseLong(value);
                            if (id == 0) {
                                throw new RuntimeException("没有选择对应的数据集！");
                            } else {
                                chunk.setId(id);
                            }
                            break;
                        case "chunkNumber":
                            int chuckNumber = Integer.parseInt(value);
                            chunk.setChunkNumber(chuckNumber);
                            break;
                        case "chunkSize":
                            long chunkSize = Long.parseLong(value);
                            chunk.setChunkSize(chunkSize);
                            break;
                        case "currentChunkSize":
                            long currentChunkSize = Long.parseLong(value);
                            chunk.setCurrentChunkSize(currentChunkSize);
                            break;
                        case "totalSize":
                            long totalSize = Long.parseLong(value);
                            chunk.setTotalSize(totalSize);
                            break;
                        case "identifier":
                            chunk.setIdentifier(value);
                            break;
                        case "totalChunks":
                            int totalChunks = Integer.parseInt(value);
                            chunk.setTotalChunks(totalChunks);
                            break;
                        case "filename":
                            chunk.setFilename(value);
                            break;
                    }
                }
            }

            for (FileItem fileItem : items) {
                if (!fileItem.isFormField()) { //是文件类型
                    String fileName = "";
                    if (chunk.getChunkNumber() != 0) {
                        fileName = chunk.getChunkNumber() + ".part";
                    } else {
                        fileName = fileItem.getName();
                    }
                    chunk.setFilename(fileName);
                    File projDir = new File(filePathTemp,chunk.getIdentifier());
                    if (!projDir.exists()){
                        projDir.mkdirs();
                    }
                    if (new UpLoadService().SaveFile(fileItem, projDir.getPath(),fileName)) {
                        resp.getWriter().append(fileName).append("文件上传成功！");
                        chunk.setRelativePath(projDir.getPath() + File.pathSeparator + fileName);
                    } else {
                        resp.getWriter().append("文件上传失败！");
                    }
                }
            }
        }

//        resp.getWriter().append("没有跨越!");
        resp.getWriter().append(JSON.toJSONString(chunk));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        this.doPost(req, resp);
    }
}
