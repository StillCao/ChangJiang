package back.simba.servlet.RollingServlet;

import back.simba.dao.RollingQuery;
import back.simba.service.Upload;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置轮播图
 * @author Simba
 * @date 2020/10/29 17:02
 */
@WebServlet("/DeployRollingImage")
public class DeployRollingImage extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.设置request和response编码格式
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String url = null;//保存图片服务器存储的地址

        //2.获取post请求中的参数。form表单中包含文件上传，不能使用request.getParameter("roundNewInfo")获取参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<String> pList = new ArrayList<>();
        List<FileItem> list = null;

        try {
            list = upload.parseRequest(request);//得到form表单的所以数据
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        for (FileItem item : list) {
            //如果fileitem中封装的是普通输入项的数据
            if (item.isFormField()){
                String value = item.getString("UTF-8");
                pList.add(value);//将非文件的其他参数放到一个list中，后面可以顺序的去取到
                continue;
            }else {//如果fileitem中封装的是上传文件
                InputStream is = item.getInputStream();//上传文件需要的文件流参数
                String filename = item.getName();//上传文件需要的参数

                //设置临时文件，来缓存上传的文件
                String savePath = "C:\\ftp\\ChangJiang\\rollingData";
                File path = new File(savePath);
                if (!path.exists()) {
                    path.mkdir();
                }

                url = Upload.uploadFile(is, path, filename);//返回一个前端能够访问的URL地址
                continue;
            }
        }

        //开始顺序存取非文件参数



        String roundNewInfo = request.getParameter("data");
        JSONObject jsonObject = JSONObject.parseObject(roundNewInfo);//把得到的json字符串转为json对象

        jsonObject.put("file", url);

        //3.调用方法，在rolling表中添加新增数据
        int flag = 0;//表示添加状态：0表示添加失败，1表示添加成功。
        Map<String,Integer> res = new HashMap<>();
        try {
            boolean b = new RollingQuery().addRollingData(jsonObject);
            if (b == true){
                flag = 1;
            }
            res.put(jsonObject.getString("title"),flag);
            String result = new ObjectMapper().writeValueAsString(res);

            //4.向前端响应json数据
            response.setHeader("Access-Control-Allow-Origin","*"); //解决跨域问题，让返回结果可远程调用
            response.getWriter().append(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
