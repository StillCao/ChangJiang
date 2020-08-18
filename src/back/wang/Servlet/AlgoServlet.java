package back.wang.Servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.wang.Dao.AlgoQuery;
import back.wang.Domain.BasicInfoAll;
import back.wang.Domain.TypicalAlgo;
import back.wang.Domain.TypicalAlgoTags;
import back.wang.Service.AlgoService;
import front.basic_page.Domain.Attr_value;
import front.basic_page.Servlet.BaseServlet;

/**
 * @author wwx-sys
 * @time 2020-08-17-15:22
 * @description 典型算法接口
 */
@WebServlet("/AlgoServlet")
public class AlgoServlet extends BaseServlet {
    private final AlgoQuery algoQuery = new AlgoQuery();

    //返回所有的典型算法记录
    public void showAllAlgo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> map = req.getParameterMap();
        int currentPage = Integer.parseInt(map.getOrDefault("currentPage", new String[]{"1"})[0]);
        int currentCount = Integer.parseInt(map.getOrDefault("currentCount", new String[]{"10"})[0]);
        AlgoService algoService = new AlgoService();
        String result = algoService.allAlgoService(currentPage, currentCount);

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(result);
    }

    //根据算法id查询对应的典型算法
    public void showAlgoById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idString = req.getParameter("id");
        try {
            int id = Integer.parseInt(idString);
            TypicalAlgo typicalAlgo = algoQuery.getAlgoById(id);

            resp.setContentType("text/html;charset=utf-8");
            resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
            resp.getWriter().append(JSON.toJSONString(typicalAlgo));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    //返回所有的标签
    public void showAllTags(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<TypicalAlgoTags> typicalAlgoTagsList = algoQuery.getAllTags();

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(JSON.toJSONString(typicalAlgoTagsList));
    }

    //根据标签id查询对应的算法id
    public void getAlgoIdsByTagId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idString = req.getParameter("id");
        try {
            int id = Integer.parseInt(idString);
            TypicalAlgoTags typicalAlgoTags = algoQuery.getTagsById(id);

            resp.setContentType("text/html;charset=utf-8");
            resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
            resp.getWriter().append(typicalAlgoTags.getAlgo());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    //上传算法
    public void insertAlgo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
                    break;
                }
            }
            for (FileItem item : items) {   //再循环一次，保存文件
                if (!item.isFormField()) {  //若item为文件表单项目
                    String docRootDirPath = "C:\\ftp\\ChangJiang\\典型数据文档\\";
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
                    } else {
                        resp.getWriter().append("文件上传失败！");
                        return;
                    }
                }
            }
        }

        //先遍历一遍，看有没有需要新建的标签
        int[] newTagIds = null;
        if (tagsName != null) {
            newTagIds = new int[tagsName.length];            //所有标签ID
            for (int i = 0; i < tagsName.length; i++) {
                if (!algoQuery.isTagsExistsByName(tagsName[i])) {
                    TypicalAlgoTags tag = new TypicalAlgoTags();
                    tag.setName(tagsName[i]);
                    newTagIds[i] = algoQuery.tagInsert(tag);
                } else {
                    int tagId = algoQuery.getTagsIdByName(tagsName[i]);
                    if (tagId != -1){
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
                    shijinzhi |= (1 << newTagIds[i]);
                }
            }
            typicalAlgo.setTags(Integer.toBinaryString(shijinzhi).getBytes());
            algoId = algoQuery.algoInsert(typicalAlgo);
            if (algoId > 0){
                isSuccess = true;
            }
        }

        //再遍历tagsName一遍，关联标签的算法ID
        if (tagsName != null) {
            for (int i = 0; i < tagsName.length; i++) {
                TypicalAlgoTags tag = algoQuery.getTagsByName(tagsName[i]);
                if (tag != null){
                    tag.algo[algoId] = 1;
                    algoQuery.updateTagsAlgo(tag.getId(), tag.algo);
                }
            }
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
        resp.getWriter().append(String.valueOf(isSuccess));

    }

    //删除算法
    public void deleteAlgo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isSuccess = false;
        String idString = req.getParameter("id");
        try {
            int id = Integer.parseInt(idString);
            TypicalAlgo algo = algoQuery.getAlgoById(id);
            if (algo != null) {
                AlgoService algoService = new AlgoService();
                isSuccess = algoService.deleteAlgo(algo);
            }
            resp.setContentType("text/html;charset=utf-8");
            resp.setHeader("Access-Control-Allow-Origin", "*");//解决跨域问题，开发完毕时应该关闭
            resp.getWriter().append(String.valueOf(isSuccess));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
