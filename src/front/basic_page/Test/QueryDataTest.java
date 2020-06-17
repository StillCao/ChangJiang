package front.basic_page.Test;


import front.basic_page.Dao.QueryData;
import front.basic_page.Domain.BasicInfo;
import front.basic_page.Domain.BasicInfoPos;
import front.basic_page.Domain.News;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
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

    @org.junit.Test
    public void queryTagLevel2IdBy1Id() {
        List<Integer> integers = new QueryData().QueryTagLevel2IdBy1Id(1);
        System.out.println(integers);
    }

    @org.junit.Test
    public void QueryBasicInfoByTagLevel2() {
        List<BasicInfoPos> basicInfoPos = new QueryData().QueryBasicInfoByTagLevel2(9);
        System.out.println(basicInfoPos);
    }

    @org.junit.Test
    public void QueryBasicInfoByTagLevel1() {
        List<BasicInfoPos> basicInfoPos = new QueryData().QueryBasicInfoByTagLevel1(4);
        System.out.println(basicInfoPos);
    }


}