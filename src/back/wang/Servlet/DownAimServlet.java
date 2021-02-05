package back.wang.Servlet;

import back.wang.Domain.BasicInfoAll;
import back.wang.Domain.Downaim;
import back.wang.Domain.Order_confirm;
import back.wang.Service.DownAimService;
import back.wang.Service.UpLoadService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import front.basic_page.Domain.Attr_value;
import front.user_io.dao.UserQuery;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述:
 * 提交数据申请和填写数据使用用途
 *
 * @author black-leaves
 * @createTime 2020-06-29  15:18
 */

@WebServlet("/DownAimServlet")
public class DownAimServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(req)) {
            throw new RuntimeException("当前请求不支持文件上传！");
        }

        //upload对象初始化
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
        String proofPath = "";    //证明材料绝对路径
        String proofUrl = "";     //证明材料URL
        Downaim downaim = null;    //JSONString转出的Downaim对象
        StringBuilder projName = new StringBuilder(); //项目名
        int user_id = 0;                                   //用户ID
        String user_name = "";                          //用户名称
        List<Integer> dataIds = new ArrayList<>();      //数据id列表

        DownAimService service = new DownAimService();
        UserQuery userQuery = new UserQuery();

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
//                        JSONObject down_aimObj = jsonObject.getJSONObject("downaim");
//                        JSONArray attr_valueArray = jsonObject.getJSONArray("attr_value");
                        downaim = JSON.parseObject(jsonObject.toJSONString(), Downaim.class);
//                        attr_valueList = JSONObject.parseArray(attr_valueArray.toString(), Attr_value.class);
//                        if (basic_info != null && !basic_info.getName().equals("")) {
                    }
                    if (name.equals("user_id")) {
                        try {
                            user_id = Integer.parseInt(value);
                            user_name = userQuery.queryUserById(user_id).getUserName();
                            projName.append(user_name);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    if (name.equals("list_id")) {
                        List<String> ids = Arrays.asList((value.split(",")));
                        dataIds = ids.stream().map(Integer::parseInt).collect(Collectors.toList());
                    }

                }
            }

            for (FileItem item : items) {//先循环一次，找到表单
                if (!item.isFormField()) { //若item为文件表单项目
//                    String rootDirPath = "C:\\ftp\\ChangJiang\\证明材料\\";
//                    String rootUrl = "http://101.37.83.223:8025/证明材料/";
                    String rootDirPath = "C:\\ChangJiang\\apache-tomcat-9.0.41\\webapps\\CJData\\证明材料\\";
                    String rootUrl = "https://cjgeodata.cug.edu.cn/CJData/证明材料/";
                    File rootDir = new File(rootDirPath);
                    if (! rootDir.exists()){
                        rootDir.mkdir();
                    }

                    String fileName = item.getName();
                    String subfix = "";
                    String preName = fileName;

                    if (fileName.contains(".")) {
                        preName = fileName.split("\\.")[0];
                        subfix = fileName.split("\\.")[1];
                    }

                    projName.append("_").append(preName).append("_").append(new Date().getTime());

                    String projDirPath = rootDirPath + projName;
                    File projDir = new File(projDirPath);
                    if (!projDir.exists()) {
                        projDir.mkdir();
                        System.out.println("数据不存在，正在上传文件");
                    } else {
                        System.out.println("数据存在，添加文件");
                    }

                    File file = new File(projDirPath, fileName);
                    //判断文件是否重名或者存在
                    if (file.exists()) {
                        resp.getWriter().append("文件已经存在或者重名！");
                        return;
                    }
                    if (service.SaveFile(item, projDirPath)) {
                        proofUrl = rootUrl + projName + "/" + file.getName();
                        proofPath = file.getAbsolutePath();
                        resp.getWriter().append(fileName).append("文件上传成功！");
                    } else {
                        resp.getWriter().append("文件上传失败！");
                        return;
                    }

                }
            }
        }
        if (downaim == null) {
            return;
        }

        if (new File(proofPath).exists()) {
            downaim.setProofUrl(proofUrl); //插入downaim表
        }
        int id = service.InsertDownAim(downaim);
        StringBuilder result = new StringBuilder();
        if (id > 0 && user_id > 0) {
            int finalUser_id = user_id;
            dataIds.forEach(data_id -> {
                if (data_id > 0) {
                    if (service.UpDateOrderConfirm(finalUser_id, data_id, id)) {
                        result.append("order_confirm表 userId为").append(finalUser_id).append("id").append("更新成功！");
                    } else {
                        result.append("order_confirm表更新失败！");
                    }
                } else {
                    result.append("数据id错误！");
                }
            });
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(String.valueOf(result));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
