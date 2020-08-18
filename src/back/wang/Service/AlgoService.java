package back.wang.Service;

import com.alibaba.fastjson.JSON;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import back.wang.Dao.AlgoQuery;
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
        Page<TypicalAlgo> page = new Page<>(currentPage, currentCount, totalPage, totalCount, typicalAlgos);
        return JSON.toJSONString(page);
    }



     /** 保存文件流到指定的目录
     * @param item FileItem 对象
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
     * @param algo 算法对象
     * @return 是否删除成功
     */
    public boolean deleteAlgo(TypicalAlgo algo) {
        AlgoQuery algoQuery = new AlgoQuery();
        int algoId = algo.getId();
        List<Integer> tagsIds = algo.byte2ints();                   //先删除tags表里 对应的算法记录
        tagsIds.forEach(tagsId ->{
            TypicalAlgoTags tag = algoQuery.getTagsById(tagsId);
            tag.algo[algoId] = '0';                                   //删除就令对应 该算法id  位上的数字为 0
            algoQuery.updateTagsAlgo(tagsId, tag.algo);
        });

        //再删除algo 表中记录
        return algoQuery.deleteAlgo(algoId);
    }
}
