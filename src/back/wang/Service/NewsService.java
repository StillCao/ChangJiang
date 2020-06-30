package back.wang.Service;

import back.wang.Dao.AdminQuery;
import back.wang.Dao.NewsQuery;
import back.wang.Domain.Admin;
import back.wang.Domain.News;
import back.wang.Domain.Page;
import com.alibaba.fastjson.JSON;
import org.apache.commons.fileupload.FileItem;
import org.springframework.dao.DataAccessException;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 描述:
 * 新闻查询服务
 *
 * @author black-leaves
 * @createTime 2020-06-29  20:10
 */

public class NewsService {

    /**
     * 所有分页查询
     *
     * @param currentPage  当前页面
     * @param currentCount 每页条数
     * @return Page<>转为的JsonString
     */
    public String allNews(int currentPage, int currentCount) {
        NewsQuery newsQuery = new NewsQuery();
        int totalCount = newsQuery.queryNewsCount();
        int totalPage = totalCount % currentCount == 0 ? totalCount / currentCount : totalCount / currentCount + 1;
        int startPosition = (currentPage - 1) * currentCount;
        List<News> newsList = newsQuery.queryAllNewsByPage(startPosition, currentCount);
        Page<News> page = new Page<>(currentPage, currentCount, totalPage, totalCount, newsList);
        return JSON.toJSONString(page);
    }

    /**
     * 根据 key值和 value值分页查询
     *
     * @param key          字段名称
     * @param value        字段值
     * @param currentPage  当前页码
     * @param currentCount 每页条数
     * @return Page<>转为的JsonString
     */
    public String newsByKey(String key, String value, int currentPage, int currentCount) {
        NewsQuery newsQuery = new NewsQuery();
        int totalCount = newsQuery.queryNewsByKeyCount(key, value);
        int totalPage = totalCount % currentCount == 0 ? totalCount / currentCount : totalCount / currentCount + 1;
        int startPosition = (currentPage - 1) * currentCount;

        List<News> newsList = newsQuery.queryNewsByKeyLikeByPage(key, value, startPosition, currentCount);
        Page<News> page = new Page<>(currentPage, currentCount, totalPage, totalCount, newsList);
        return JSON.toJSONString(page);
    }

    /**
     * 新闻添加
     *
     * @param news News 对象
     * @return 返回添加状态
     */
    public boolean newsAdd(News news) {
        NewsQuery newsQuery = new NewsQuery();
        return newsQuery.insertNews(news);
    }

    /**
     * @param id 要删除的News id
     * @return 是否删除成功
     */
    public boolean newsDelete(int id) {
        NewsQuery newsQuery = new NewsQuery();
        if (id > 0) {
            return newsQuery.deleteNews(id);
        }
        return false;
    }

    /**
     * 修改新闻
     *
     * @param news 要修改的News对象
     * @return 是否修改成功
     */
    public boolean newsUpdate(News news) {
        NewsQuery newsQuery = new NewsQuery();
        int id = news.getId();
        if (id > 0) { //再进行数据库修改
            return newsQuery.updateNewsById(news);
        }
        return false;
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
}
