package back.wang.Test;

import back.wang.Dao.NewsQuery;
import back.wang.Domain.News;
import back.wang.Service.NewsService;

import java.util.Date;
import java.util.List;

/**
 * 描述:
 * 新闻测试
 *
 * @author black-leaves
 * @createTime 2020-06-29  16:15
 */

public class NewsTest {
    @org.junit.Test
    public void queryAllAdmins() {
        List<News> news = new NewsQuery().queryNewsByKeyLikeByPage("title","国家",0,10);
        System.out.println(news.size());
    }

    @org.junit.Test
    public void queryNewsCount() {
        System.out.println(new NewsQuery().queryNewsCount());
    }

    @org.junit.Test
    public void queryAllNewsByPage() {
        System.out.println(new NewsQuery().queryAllNewsByPage(0,10));
    }

    @org.junit.Test
    public void queryNewsByKeyCount() {
        System.out.println(new NewsQuery().queryNewsByKeyCount("title","地球"));
    }

    @org.junit.Test
    public void insertNews() {
        News news = new News();
        Date date = new Date();
        news.setType(1);
        news.setLocaladdr("asdqwd");
        news.setNews_cont("dwqdwqdwqdwqdqwdqwd");
        news.setTitle("qwd");
        news.setSource("asdqd");
        news.setDate(date);
        System.out.println(new NewsQuery().insertNews(news));
    }

    @org.junit.Test
    public void allNews() {
        System.out.println(new NewsService().allNews(1,10));
    }

    @org.junit.Test
    public void newsByKey() {
        System.out.println(new NewsService().newsByKey("title","地球",1,10));
    }


}
