package back.wang.Servlet;

import back.wang.Domain.BasicInfoAll;
import back.wang.Service.UpLoadService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import front.basic_page.Domain.Attr_value;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 专题数据上传接口
 *
 * @author black-leaves
 * @createTime 2020-06-24  9:26
 */

@WebServlet("/UpLoadServlet")
public class UpLoadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(req)) {
            throw new RuntimeException("当前请求不支持文件上传！");
        }

        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域问题，让返回结果可远程调用

        //upload对象初始化

        //创建一个fileItem工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置临时文件的边界值，大于该值，上传文件会先保存在临时文件中，否则，上传文件直接写进内存
        //单位：字节，此处边界值为1M
        factory.setSizeThreshold(1024 * 1024 * 1);

        //设置临时文件，来缓存上传的文件
        String tempPath = this.getServletContext().getRealPath("/WEB-INF/tempFile");
        File temp = new File(tempPath);
        if (!temp.exists()) {
            temp.mkdir();
        }
        factory.setRepository(temp);

        //创建文件上传核心组件
        ServletFileUpload upload = new ServletFileUpload(factory);
        //设置每个item的头部字符编码，其可以解决文件名的中文乱码问题
        upload.setHeaderEncoding("utf-8");


        List<FileItem> items = null;
        String imgPath = "";    //缩略图绝对路径
        String proPath = "";    //数据绝对路径
        UpLoadService service = new UpLoadService();
        BasicInfoAll basic_info = null;    //JSONString转出的BasicInfoAll对象
        List<Attr_value> attr_valueList = null;    //JSONString转出的Attr_value列表对象
        String projName = ""; //项目名
        List<String> filePaths = new ArrayList<>();     //非图片文件的存储绝对路径

        //item分类处理
        try {
            //解析请求，或取到所有的item
            items = upload.parseRequest(req);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        //遍历item
        if (items != null) {
            for (FileItem item : items) {//先循环一次，找到表单
                if (item.isFormField()) { //若item为普通表单项
                    String name = item.getFieldName();
                    String value = item.getString("utf-8");
                    if (name.equals("data")) {
                        JSONObject jsonObject = JSON.parseObject(value);
                        JSONObject basic_infoObj = jsonObject.getJSONObject("basic_info");
                        JSONArray attr_valueArray = jsonObject.getJSONArray("attr_value");
                        basic_info = JSON.parseObject(basic_infoObj.toJSONString(), BasicInfoAll.class);
                        attr_valueList = JSONObject.parseArray(attr_valueArray.toString(), Attr_value.class);
                        if (basic_info != null && !basic_info.getName().equals("")) {
                            projName = basic_info.getName();
                        }
                    }
                    System.out.println(name + ":" + value);
                    break;
                }
            }

            for (FileItem item : items) {   //再循环一次，保存文件
                if (!item.isFormField()) {  //若item为文件表单项目
                    String picRootDirPath = "C:\\ftp\\ChangJiang\\基础数据上传图片\\";
                    String rootDirPath = "C:\\Users\\Administrator\\Desktop\\长江中游地学数据集\\";
//                    String rootDirPath = "D:\\长江中游地学数据集\\";
                    String fileName = item.getName();
//                    String projName = item.getName();

                    String subfix = "";
                    if (fileName.contains(".")) {
//                        projName = fileName.split("\\.")[0];
                        subfix = fileName.split("\\.")[1];
                    }
                    String projDirPath = rootDirPath + projName;
                    String picProJDirPath = picRootDirPath + projName;
                    String fileFolderPath = "";         //该文件的父级目录路径
                    File projDir = new File(projDirPath);
                    File picProjDir = new File(picProJDirPath);
                    if (!projDir.exists()) {
                        projDir.mkdirs();
                        System.out.println("数据不存在，正在上传文件");
                    } else {
                        System.out.println("数据存在，添加文件");
                    }

                    if (!picProjDir.exists()) {
                        picProjDir.mkdirs();
                        System.out.println("数据不存在，正在上传图片");
                    } else {
                        System.out.println("数据存在，添加图片");
                    }

                    File file = null;

                    if (subfix.equals("png") || subfix.equals("jpg") || subfix.equals("jpeg")) { //为缩略图文件
                        file  = new File(picProjDir, fileName);
                        imgPath = file.getAbsolutePath();
                        fileFolderPath = picProJDirPath;
                    }
                    else {
                        file = new File(projDir,fileName);
                        fileFolderPath = projDirPath;
                        filePaths.add(file.getAbsolutePath());
                    }

                    //判断文件是否重名或者存在
                    if (file.exists()) {
                        resp.getWriter().append("文件已经存在或者重名！");
                        return;
                    }

                    if (service.SaveFile(item, fileFolderPath)) {
                        resp.getWriter().append(fileName).append("文件上传成功！");
                        proPath = projDirPath;
                    } else {
                        resp.getWriter().append("文件上传失败！");
                        return;
                    }
                }
            }
        }

        if (basic_info == null) {
            return;
        }

        BasicInfoAll finalBasic_info = basic_info;
        if (new File(proPath).exists()) {
            finalBasic_info.setDa_url(proPath);
            if (filePaths.size() > 0){
                filePaths.forEach(filePath ->{
                    if (filePath.contains(".")){
                        String[] splits = filePath.split("\\.");
                        String sub_fix =splits[splits.length - 1];
                        if (sub_fix.equals("doc") || sub_fix.equals("docx")){
                            finalBasic_info.setFile_url(filePath);
                        }else {
                             finalBasic_info.setSample_url(filePath);
                        }
                    }
                });


            }
        }
        if (new File(imgPath).exists()) {
            finalBasic_info.setImage(imgPath);
        }

        StringBuilder result = new StringBuilder();
        int basicId = service.InsertBasic(finalBasic_info);  //插入基本数据
        if (basicId != 0) {
            result.append("插入基本信息表成功！\n");
            if (attr_valueList == null) {
                return;
            }
            attr_valueList.forEach(attr_value -> {
                if (service.InsertRelate(attr_value, basicId)) {  //插入数据主题词关系表
                    result.append(attr_value.getV_name()).append("插入数据主题词关系表成功！\n");
                } else {
                    result.append(attr_value.getV_name()).append("插入数据主题词关系表失败！\n");
                }
            });
        } else {
            result.append("插入基本信息表失败！\n");
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result.toString());

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }


}
