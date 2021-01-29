package back.wang.Servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.wang.Dao.SpecialQuery;
import back.wang.Domain.SpecialData;
import back.wang.Service.NewsService;
import back.wang.Service.SpecialService;
import front.basic_page.Servlet.BaseServlet;

/**
 * @author wwx-sys
 * @time 2020-10-27-19:18
 * @description 特色数据接口
 */

@WebServlet("/SpecialServlet")
public class SpecialServlet extends BaseServlet {


    //String rootDirPath = "C:\\ftp\\ChangJiang\\特色数据图片"; 特色数据图片根目录

    /**
     * 专题图片上传
     */
    public void uploadPictures(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
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

        NewsService service = new NewsService();

        List<String> picturePaths = new ArrayList<>();
        AtomicInteger error_count = new AtomicInteger();    //错误数

        //item分类处理
        try {
            //解析请求，或取到所有的item
            items = upload.parseRequest(req);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        //遍历item
        if (items != null) {
            items.forEach(item -> {
                if (!item.isFormField()) { //若item为文件表单项目
//                    String rootDirPath = "C:\\ftp\\ChangJiang\\特色数据图片";
//                    String rootUrl = "http://202.114.194.19:8181/特色数据图片";
                    String rootDirPath = "C:\\ChangJiang\\apache-tomcat-9.0.41\\webapps\\CJData\\特色数据图片";
                    String rootUrl = "http://202.114.194.19:8080/CJData/特色数据图片";
                    String fileName = item.getName();

                    File projDir = new File(rootDirPath);
                    if (!projDir.exists()) {
                        projDir.mkdir();
                        System.out.println("数据不存在，正在上传文件");
                    } else {
                        System.out.println("数据存在，添加文件");
                    }
                    File file = new File(rootDirPath, fileName);
                    //判断文件是否重名或者存在
                    try {
                        if (file.exists()) {
                            error_count.addAndGet(1);
                            resp.getWriter().append("errno:").append(String.valueOf(error_count.get()));
                            resp.getWriter().append("caused by:文件已经存在或者重名！");
                            return;
                        }
                        if (service.SaveFile(item, rootDirPath)) {
                            String picHttpUrl = rootUrl + "/" + file.getName();
                            picturePaths.add(picHttpUrl);
                        } else {
                            error_count.addAndGet(1);
                            resp.getWriter().append("errno:").append(String.valueOf(error_count.get()));
                            resp.getWriter().append("caused by:文件上传失败！");
                        }
                    } catch (IOException e) {
                        error_count.addAndGet(1);
                        e.printStackTrace();
                    }
                }
            });
        }

        JSONObject object = new JSONObject();
        object.put("errno", error_count.get());
        object.put("data", picturePaths);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(JSON.toJSONString(object));
    }

    /**
     * 专题图片删除
     */
    public void deletePictures(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String picNamesString = req.getParameter("picNames");
        List<String> picNames = new ArrayList<>();
        NewsService service = new NewsService();
        String rootPath = "C:\\ChangJiang\\apache-tomcat-9.0.41\\webapps\\CJData\\特色数据图片";
//        String rootPath = "D://ftp";
        List<String> result = new ArrayList<>();
        if (picNamesString.contains(",")) {
            String[] picNamesSplits = picNamesString.split(",");
            picNames.addAll(Arrays.asList(picNamesSplits));
        } else {
            picNames.add(picNamesString);
        }
        picNames.forEach(picName -> {
            String picPath = rootPath + File.separator + picName;
            String res = picName;
            if (service.deletePictures(picPath)) {
                res += "删除成功";
            } else res += "删除失败";
            result.add(res);
        });

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(JSON.toJSONString(result));


    }

    /**
     * 特色数据增加
     */
    public void addData(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        Map<String, String[]> map = req.getParameterMap();
        String result = "0";
        SpecialData specialData = new SpecialData();

        //为BeanUtils登记String转换Date的Pattern，否则populate方法无法自行转换
        ConvertUtils.register((clazz, value) -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = null;
            try {
                parse = format.parse(value.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return parse;
        }, Date.class);

        BeanUtils.populate(specialData, map);

        if (specialData.getPublishTime() == null) {
            specialData.setPublishTime(new Date());
        }

        SpecialService specialService = new SpecialService();
        if (specialService.dataAdd(specialData)) {
            result = "1";
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    /**
     * 特色数据列表
     */
    public void listDataByKey(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");

        Map<String, String[]> map = req.getParameterMap();
        int currentPage = Integer.parseInt(map.getOrDefault("currentPage", new String[]{"1"})[0]);
        int currentCount = Integer.parseInt(map.getOrDefault("currentCount", new String[]{"10"})[0]);

        SpecialService specialService = new SpecialService();
        String result = "";
        if (!map.containsKey("queryInfo")) {//全部查询
            result = specialService.allData(currentPage, currentCount);
        } else {
            String key = map.get("queryInfo")[0];
            if (key.isEmpty()) {
                result = specialService.allData(currentPage, currentCount);
            } else {  //带key值的模糊查询
                if (!map.containsKey("value")) {
                    resp.getWriter().append("value参数未输入");
                    return;
                }

                String value = map.get("value")[0];
                result = specialService.dataByKey(key, value, currentPage, currentCount);
            }
        }

        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    /**
     * 根据ID查询特色数据
     */
    public void getDataById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);

        SpecialData data = new SpecialQuery().queryDataById(id);
        if (data != null) {
            resp.getWriter().append(JSON.toJSONString(data));
        }

        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
    }

    /**
     * 特色数据修改
     */
    public void updateData(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        Map<String, String[]> map = req.getParameterMap();
        String result = "0";
        SpecialData specialData = new SpecialData();

        //为BeanUtils登记String转换Date的Pattern，否则populate方法无法自行转换
        DateConverter converter = new DateConverter();
        converter.setPattern("yyyy-MM-dd");
        ConvertUtils.register(converter, Date.class);

        BeanUtils.populate(specialData, map);
        SpecialService specialService = new SpecialService();
        if (specialService.dataUpdate(specialData)) {
            result = "1";
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    /**
     * 特色数据删除
     */
    public void deleteData(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String result = "0";
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);

        SpecialService specialService = new SpecialService();
        if (specialService.dataDelete(id)) {
            result = "1";
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }
}
