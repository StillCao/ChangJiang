package back.wang.Servlet;

import com.alibaba.fastjson.JSON;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.wang.Dao.AlgoQuery;
import back.wang.Domain.TypicalAlgo;
import back.wang.Domain.TypicalAlgoTags;
import back.wang.Service.AlgoService;

import static java.lang.Math.max;


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
                    String docRootDirPath = "D:\\ftp\\ChangJiang\\典型数据文档\\";
                    String docRootUrl = "http://101.37.83.223:8025/典型数据文档/";
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
                if (!algoQuery.isTagsExistsByName(tagsName[i])) {
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
            int shijinzhi = 0;
            if (newTagIds != null) {
                for (int i = 0; i < newTagIds.length; i++) {
                    shijinzhi |= (1 << (newTagIds[i] -1));
                }
            }
            typicalAlgo.setTags(Integer.toBinaryString(shijinzhi).getBytes());
            algoId = algoQuery.algoInsert(typicalAlgo);
            if (algoId > 0) {
                isSuccess = true;
            }
        }

        //再遍历tagsName一遍，关联标签的算法ID
        if (tagsName != null) {
            for (int i = 0; i < tagsName.length; i++) {
                TypicalAlgoTags tag = algoQuery.getTagsByName(tagsName[i]);
                if (tag != null) {
                    if (tag.algo == null) {
                        tag.algo = new byte[algoId];
                    }
                    else {
                        tag.algo = Arrays.copyOf(tag.algo,max(tag.algo.length,algoId));
                    }
                    tag.algo[tag.algo.length - algoId] = '1';                  //将algo的从右往左数第algoId位设为'1'即ASCII码59
                    algoQuery.updateTagsAlgo(tag.getId(), tag.algo);
                }
            }

        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(String.valueOf(isSuccess));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
