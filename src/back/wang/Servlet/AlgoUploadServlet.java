package back.wang.Servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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

import back.wang.Dao.AlgoQuery;
import back.wang.Dao.BasicDataQuery;
import back.wang.Domain.BasicInfoAll;
import back.wang.Domain.TypicalAlgo;
import back.wang.Domain.TypicalAlgoTags;
import back.wang.Service.AlgoService;


/**
 * @author wwx-sys
 * @time 2020-08-20-20:00
 * @description 算法上传接口
 */

@WebServlet("/AlgoUploadServlet")
public class AlgoUploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isSuccess = false;
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

        AlgoService service = new AlgoService();
        TypicalAlgo typicalAlgo = null;         //准备上传的algo对象
        String[] tagsName = null;               //algo对象关联的 tags 名称
        String doc_url = "";                    //文档url
        JSONObject algoObj = null;


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
                        algoObj = JSON.parseObject(value);
                        typicalAlgo = JSON.parseObject(value, TypicalAlgo.class);
                    } else if (name.equals("tags")) {
                        tagsName = value.split(",");
                    }
                    System.out.println(name + ":" + value);
                }
            }
            for (FileItem item : items) {   //再循环一次，保存文件
                if (!item.isFormField()) {  //若item为文件表单项目
//                    String docRootDirPath = "C:\\ftp\\ChangJiang\\典型数据文档\\";
//                    String docRootUrl = "http://101.37.83.223:8025/典型数据文档/";
                    String docRootDirPath = "C:\\ChangJiang\\apache-tomcat-9.0.41\\webapps\\CJData\\典型数据文档\\";
                    String docRootUrl = "https://cjgeodata.cug.edu.cn/CJData/典型数据文档/";
                    String fileName = item.getName();
                    String projName = "这是一个默认的项目目录";
                    if (typicalAlgo != null && typicalAlgo.getName() != null && !typicalAlgo.getName().isEmpty()) {
                        projName = typicalAlgo.getName();
                    }

                    String projDirPath = docRootDirPath + projName;

                    File projDir = new File(projDirPath);
                    if (!projDir.exists()) {
                        projDir.mkdirs();
                        System.out.println("数据不存在，正在上传文件");
                    } else {
                        System.out.println("数据存在，添加文件");
                    }

                    File file = new File(projDir, fileName);
                    //判断文件是否重名或者存在
                    if (file.exists()) {
                        resp.getWriter().append("文件已经存在或者重名！");
                        return;
                    }

                    if (service.SaveFile(item, projDirPath)) {
                        resp.getWriter().append(fileName).append("文件上传成功！");
                        doc_url = docRootUrl + projName + "/" + fileName;
                        if (typicalAlgo != null) {
                            typicalAlgo.setDoc_url(doc_url);
                        }
                    } else {
                        resp.getWriter().append("文件上传失败！");
                        return;
                    }
                }
            }
        }

        //先遍历一遍，看有没有需要新建的标签
        int[] newTagIds = null;
        AlgoQuery algoQuery = new AlgoQuery();
        if (tagsName != null) {
            newTagIds = new int[tagsName.length];            //所有标签ID
            for (int i = 0; i < tagsName.length; i++) {
                if (!algoQuery.isTagsExistsByName(tagsName[i])) {   //不存在则添加
                    TypicalAlgoTags tag = new TypicalAlgoTags();
                    tag.setName(tagsName[i]);
                    newTagIds[i] = algoQuery.tagInsert(tag);
                } else {
                    int tagId = algoQuery.getTagsIdByName(tagsName[i]);
                    if (tagId != -1) {
                        newTagIds[i] = tagId;
                    }
                }
            }
        }

        //插入算法数据
        int algoId = 0;         //插入后的算法ID，
        if (typicalAlgo != null) {
            if (typicalAlgo.getId() != 0) {      //修改数据
                algoId = typicalAlgo.getId();
                String sql = sqlBuilder(algoObj, typicalAlgo);
                if (sql != null) {
                    System.out.println(sql);
                    if (new BasicDataQuery().updateBasicData(sql)) {
                        isSuccess = true;
                    }
                }
            } else {                            //插入数据
                algoId = algoQuery.algoInsert(typicalAlgo);
                if (algoId > 0) {
                    isSuccess = true;
                }
            }
        }

        //再遍历tagsName一遍，关联标签的算法ID
        if (newTagIds != null) {
            if (typicalAlgo != null && typicalAlgo.getId() != 0) { //修改数据的话，先删除关联表中该算法id的关联记录,然后再插入
                algoQuery.deleteRelateByAlgoId(typicalAlgo.getId());
            }
            for (int newTagId : newTagIds) {
                if (algoId != 0 && newTagId != 0) {
                    algoQuery.insertRelate(algoId, newTagId);
                }
            }
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(String.valueOf(isSuccess));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        this.doPost(req, resp);
    }

    //sql字符串拼接函数
    public String sqlBuilder(JSONObject algo_obj, TypicalAlgo typicalAlgo) {
        StringBuilder sql = new StringBuilder("update typical_algo set ");
        if (algo_obj.containsKey("id")) {
            algo_obj.keySet().forEach(key -> {
                if (!key.equals("id") && !key.equals("tags")) {     //tags在表单数据中传过来了，但是数据库中没有对应的字段
                    if (key.equals("doc_url")) {
                        sql.append(key).append("=").append("'").append(transferSlash(typicalAlgo.getDoc_url())).append("'").append(",");
                    } else {
                        sql.append(key).append("=").append("'").append(algo_obj.get(key)).append("'").append(",");
                    }

                }
            });
            if (sql.toString().endsWith(",") && sql.length() > 1) {
                sql.deleteCharAt(sql.length() - 1);
            }

            sql.append(" where id = ").append(algo_obj.get("id")).append(";");
            return sql.toString();
        } else return null;
    }

    //将str中的斜杠转换为反斜杠
    public String transferSlash(String str) {

        if (str.contains("\\")) {
            return str.replace("\\", "\\\\");
        } else return str;
    }
}
