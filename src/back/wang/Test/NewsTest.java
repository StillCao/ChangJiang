package back.wang.Test;

import back.wang.Dao.NewsQuery;
import back.wang.Domain.News;
import back.wang.Service.NewsService;

import java.io.File;
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
        List<News> news = new NewsQuery().queryNewsByKeyLikeByPage("title", "国家", 0, 10);
        System.out.println(news);
    }

    @org.junit.Test
    public void queryNewsCount() {
        System.out.println(new NewsQuery().queryNewsCount());
    }

    @org.junit.Test
    public void queryAllNewsByPage() {
        System.out.println(new NewsQuery().queryAllNewsByPage(0, 10));
    }

    @org.junit.Test
    public void queryNewsByKeyCount() {
        System.out.println(new NewsQuery().queryNewsByKeyCount("title", "地球"));
    }

    @org.junit.Test
    public void insertNews() {
        News news = new News();
        Date date = new Date();
        news.setTYPE(1);
        news.setLocaladdr("asdqwd");
        news.setNews_cont("dwqdwqdwqdwqdqwdqwd");
        news.setTitle("qwd");
        news.setSource("asdqd");
//        news.setDATE(date);
//        System.out.println(new NewsQuery().insertNews(news));
        System.out.println(date.getTime());

    }

    @org.junit.Test
    public void allNews() {
        System.out.println(new NewsService().allNews(1, 10));
    }

    @org.junit.Test
    public void newsByKey() {
        System.out.println(new NewsService().newsByKey("title", "地球", 1, 10));
    }

    @org.junit.Test
    public void deleteNews() {
        System.out.println(new NewsQuery().deleteNews(19));
    }

    @org.junit.Test
    public void queryNewsAddr() {
        System.out.println(new NewsQuery().queryNewsAddr(18));
    }

    @org.junit.Test
    public void updateNewsById() {
        News news = new News();
        news.setId(18);
        news.setTYPE(1);
        news.setLocaladdr("asdqwd");
        news.setNews_cont("dwqdwqdwqdwqdqwdqwd");
        news.setTitle("qwd");
        news.setSource("asdqd");
        news.setDATE(new Date());
        System.out.println(new NewsQuery().updateNewsById(news));
    }

    @org.junit.Test
    public void changFileFolderName() {
        File filefolder = new File("D://长江中游地学数据集");
        filefolder.renameTo(new File(filefolder.getParent() + "ahah"));
    }

    @org.junit.Test
    public void deleteFile() {
        File file = new File("D://长江中游地学数据集//test.txt");
        file.delete();
    }

    @org.junit.Test
    public void queryNewsById() {
        System.out.println(new NewsQuery().queryNewsById(10));
    }

}
