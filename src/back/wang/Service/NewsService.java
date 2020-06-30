package back.wang.Service;

import back.wang.Dao.AdminQuery;
import back.wang.Dao.NewsQuery;
import back.wang.Domain.Admin;
import back.wang.Domain.News;
import back.wang.Domain.Page;
import com.alibaba.fastjson.JSON;


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
     * 管理员添加
     *
     * @param news
     * @return 返回添加状态
     */
    public boolean newsAdd(News news) {
        NewsQuery newsQuery = new NewsQuery();
        return newsQuery.insertNews(news);
    }
}
