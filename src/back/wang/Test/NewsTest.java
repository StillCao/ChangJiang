package back.wang.Test;

import back.wang.Dao.AdminQuery;
import back.wang.Dao.NewsQuery;
import back.wang.Domain.Admin;
import front.basic_page.Domain.News;

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
}
