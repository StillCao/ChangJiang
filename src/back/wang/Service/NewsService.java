package back.wang.Service;

import back.wang.Dao.AdminQuery;
import back.wang.Dao.NewsQuery;
import back.wang.Domain.Admin;
import back.wang.Domain.News;
import back.wang.Domain.Page;
import com.alibaba.fastjson.JSON;


import java.io.File;
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

        //先删除照片
        String pictureFolder = newsQuery.queryNewsAddr(id);
        File folder = new File(pictureFolder);
        if (folder.exists()){
            folder.delete();
        }
        if (id > 0 ){ //再进行数据库删除
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

        //更改图片文件夹名称，保持与title一致
        String addr = news.getLocaladdr();
        String title = news.getTitle();
        File fileFolder = new File(addr);
        if (fileFolder.exists()) {
            fileFolder.renameTo(new File(fileFolder.getParent(),title));
        }

        if (id > 0) { //再进行数据库修改
            return newsQuery.updateNewsById(news);
        }
        return false;
    }
}
