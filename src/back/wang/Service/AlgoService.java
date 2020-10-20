package back.wang.Service;

import com.alibaba.fastjson.JSON;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import back.wang.Dao.AlgoQuery;
import back.wang.Domain.AlgoTagsRelate;
import back.wang.Domain.Page;
import back.wang.Domain.TypicalAlgo;
import back.wang.Domain.TypicalAlgoTags;

/**
 * @author wwx-sys
 * @time 2020-08-17-18:46
 * @description 典型算法服务
 */
public class AlgoService {

    /**
     * @param currentPage  当前页码
     * @param currentCount 每页条数
     * @return Page<TypicalAlgo>转为的JsonString
     */
    public String allAlgoService(int currentPage, int currentCount) {
        AlgoQuery algoQuery = new AlgoQuery();
        int totalCount = algoQuery.getAlgoCounts();
        int totalPage = totalCount % currentCount == 0 ? totalCount / currentCount : totalCount / currentCount + 1;
        int startPosition = (currentPage - 1) * currentCount;
        List<TypicalAlgo> typicalAlgos = algoQuery.queryAlgoByPage(startPosition, currentCount);

        Map<Integer, String> tagMap = new HashMap<>();           //先把所有标签Id 和 Name 对应关系存起来，避免多次查询
        List<TypicalAlgoTags> tagsList = algoQuery.getAllTags();
        tagsList.forEach(tag -> {
            tagMap.put(tag.getId(), tag.getName());
        });

        typicalAlgos.forEach(algo -> {
            int algo_id = algo.getId();
            List<AlgoTagsRelate> relateList = algoQuery.queryRelateByAlgoId(algo_id);
            StringBuilder tagNames = new StringBuilder();
            relateList.forEach(relate -> {
                tagNames.append(tagMap.get(relate.getTag_id())).append(",");
            });
            if (tagNames.toString().endsWith(",")) {
                tagNames.deleteCharAt(tagNames.length() - 1);
            }
            algo.setTagNames(tagNames.toString());
        });

        Page<TypicalAlgo> page = new Page<>(currentPage, currentCount, totalPage, totalCount, typicalAlgos);
        return JSON.toJSONString(page);
    }


    /**
     * 保存文件流到指定的目录
     *
     * @param item        FileItem 对象
     * @param projDirPath 指定的目录
     * @return 是否保存成功
     */
    public boolean SaveFile(FileItem item, String projDirPath) throws IOException {
        File file = new File(projDirPath, item.getName());
        InputStream is = item.getInputStream();
        //创建文件输出流
        FileOutputStream os = new FileOutputStream(file);
        //将输入流中的数据写出到输出流中
        int len = -1;
        byte[] buf = new byte[1024];
        while ((len = is.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        //关闭流
        os.close();
        is.close();
        //删除临时文件
        item.delete();
        return file.exists();
    }

    /**
     * 删除一条 典型算法 ，同时删除tags表里 对应的算法记录
     *
     * @param algo           算法对象
     * @param docRootDirPath 本地文档存储根目录                              //  C:/ftp/ChangJiang/典型数据文档/
     * @return 是否删除成功
     */
    public boolean deleteAlgo(TypicalAlgo algo, String docRootDirPath) {
        AlgoQuery algoQuery = new AlgoQuery();
        List<AlgoTagsRelate> relateList = algoQuery.queryRelateByAlgoId(algo.getId());                   //先删除关系表里 对应的算法记录
        relateList.forEach(relate -> {
            algoQuery.deleteRelate(relate.getId());
        });

        //再删除文件
        String fileUrlPath = algo.getDoc_url();                     // 类似于   http://101.37.83.223:8025/典型数据文档/爬虫算法/hu2019.pdf
        if (fileUrlPath != null) {
            String[] rootSplits = docRootDirPath.split("/");
            if (rootSplits.length > 0) {
                String rootDirName = rootSplits[rootSplits.length - 1];
                String[] urlSplits = fileUrlPath.split(rootDirName);
                String docSuffixPath = urlSplits[urlSplits.length - 1];
                File doc = new File(docRootDirPath, docSuffixPath);
                doc.delete();                                       //先删除文档
                doc.getParentFile().delete();                       //再删除文档文件夹，若文件夹内还有其他文件，则不会删除
            }
        }

        //再删除algo 表中记录
        return algoQuery.deleteAlgo(algo.getId());
    }

    /**
     * 根据所给的字段和值模糊查询
     *
     * @param field        字段名称
     * @param value        字段值
     * @param currentPage  当前页码
     * @param currentCount 每页条数
     * @return Page<TypicalAlgo>转为的JsonString
     */
    public String getAlgoByFieldLike(String field, String value, int currentPage, int currentCount) {
        AlgoQuery algoQuery = new AlgoQuery();
        int totalCount = algoQuery.queryAlgoCountByFieldLike(field, value);
        int totalPage = totalCount % currentCount == 0 ? totalCount / currentCount : totalCount / currentCount + 1;
        int startPosition = (currentPage - 1) * currentCount;
        List<TypicalAlgo> typicalAlgos = algoQuery.queryAlgoByFieldLike(field, value, startPosition, currentCount);

        Map<Integer, String> tagMap = new HashMap<>();           //先把所有标签Id 和 Name 对应关系存起来，避免多次查询
        List<TypicalAlgoTags> tagsList = algoQuery.getAllTags();
        tagsList.forEach(tag -> {
            tagMap.put(tag.getId(), tag.getName());
        });

        typicalAlgos.forEach(algo -> {
            int algo_id = algo.getId();
            List<AlgoTagsRelate> relateList = algoQuery.queryRelateByAlgoId(algo_id);
            StringBuilder tagNames = new StringBuilder();
            relateList.forEach(relate -> {
                tagNames.append(tagMap.get(relate.getTag_id())).append(",");
            });
            if (tagNames.toString().endsWith(",")) {
                tagNames.deleteCharAt(tagNames.length() - 1);
            }
            algo.setTagNames(tagNames.toString());
        });

        Page<TypicalAlgo> page = new Page<>(currentPage, currentCount, totalPage, totalCount, typicalAlgos);
        return JSON.toJSONString(page);
    }
}
