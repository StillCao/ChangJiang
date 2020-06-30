package back.wang.Servlet;

import back.wang.Domain.Admin;
import back.wang.Domain.News;
import back.wang.Service.AdminService;
import back.wang.Service.NewsService;
import com.alibaba.fastjson.JSON;
import front.basic_page.Servlet.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 新闻接口
 *
 * @author black-leaves
 * @createTime 2020-06-29  16:17
 */

@WebServlet("/NewsServlet")
public class NewsServlet extends BaseServlet {

    /**
     * 新闻列表
     */
    public void listNewsByKey(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> map = req.getParameterMap();
        int currentPage = Integer.parseInt(map.getOrDefault("currentPage", new String[]{"1"})[0]);
        int currentCount = Integer.parseInt(map.getOrDefault("currentCount", new String[]{"10"})[0]);

        NewsService newsService = new NewsService();
        String result = "";
        if (!map.containsKey("key")) {//全部查询
            result = newsService.allNews(currentPage, currentCount);
        } else { //带key值的模糊查询
            if (!map.containsKey("value")) {
                resp.getWriter().append("value参数未输入");
                return;
            }
            String key = map.get("key")[0];
            String value = map.get("value")[0];
            result = newsService.newsByKey(key, value, currentPage, currentCount);
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    /**
     * 新闻增加
     */
    public void addNews(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        Map<String, String[]> map = req.getParameterMap();
        String result = "0";
        News news = new News();

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

        BeanUtils.populate(news, map);

        //前端不会传图片位置这个字段过来，需要手动与title保持一致
        String title = news.getTitle();
        String folderPath = "C:/ftp/ChangJiang/" + title;
        news.setLocaladdr(folderPath);

        NewsService newsService = new NewsService();
        if (newsService.newsAdd(news)) {
            result = "1";
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    /**
     * 新闻删除
     */
    public void deleteNews(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String result = "0";
        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);

        NewsService newsService = new NewsService();
        if (newsService.newsDelete(id)) {
            result = "1";
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    /**
     * 新闻修改
     */
    public void updateNews(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        Map<String, String[]> map = req.getParameterMap();
        String result = "0";
        News news = new News();

        //为BeanUtils登记String转换Date的Pattern，否则populate方法无法自行转换
        DateConverter converter = new DateConverter();
        converter.setPattern("yyyy-MM-dd");
        ConvertUtils.register(converter, Date.class);

        BeanUtils.populate(news, map);
        NewsService newsService = new NewsService();
        if (newsService.newsUpdate(news)) {
            result = "1";
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    /**
     * 新闻图片上传
     */

    public void uploadPictures(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        List<String> picturePaths  = new ArrayList<>();
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
//                    String rootDirPath = "C:\\ftp\\ChangJiang";
                    String rootDirPath = "D:\\ftp\\ChangJiang";
                    String fileFolderName = item.getFieldName();
                    String fileName = item.getName();
                    String projDirPath = rootDirPath + File.separator + fileFolderName;
                    File projDir = new File(projDirPath);
                    if (!projDir.exists()) {
                        projDir.mkdir();
                        System.out.println("数据不存在，正在上传文件");
                    } else {
                        System.out.println("数据存在，添加文件");
                    }
                    File file = new File(projDirPath, fileName);
                    //判断文件是否重名或者存在
                    try {
                        if (file.exists()) {
                            resp.getWriter().append("文件已经存在或者重名！");
                            return;
                        }
                        if (service.SaveFile(item, projDirPath)) {
                            picturePaths.add(file.getAbsolutePath());
                        } else {
                            resp.getWriter().append("文件上传失败！");
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(JSON.toJSONString(picturePaths));
    }
}
