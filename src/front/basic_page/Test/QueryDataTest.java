package front.basic_page.Test;


import front.basic_page.Dao.QueryData;
import front.basic_page.Domain.BasicInfo;
import front.basic_page.Domain.News;

import java.util.List;

public class QueryDataTest {

    @org.junit.Test
    public void queryLatest8News() {
        List<News> newsList = new QueryData().QueryLatest8News();
        System.out.println(newsList);
    }

    @org.junit.Test
    public void queryLatest8BasicInfo() {
        List<BasicInfo> basicInfos = new QueryData().QueryLatest8BasicInfo();
        System.out.println(basicInfos);
    }
}