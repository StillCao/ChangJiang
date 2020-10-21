package front.basic_page.Test;


import front.basic_page.Dao.QueryData;
import front.basic_page.Domain.*;

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
        List<BasicData> basicInfoPos = new QueryData().QueryBasicInfoByTagLevel2(9);
        System.out.println(basicInfoPos);
    }

    @org.junit.Test
    public void QueryBasicInfoByTagLevel1() {
        List<BasicData> basicInfoPos = new QueryData().QueryBasicInfoByTagLevel1(4);
        System.out.println(basicInfoPos);
    }

    @org.junit.Test
    public void QueryTagLevel1All() {
        List<TypeLevel1> typeLevel1s = new QueryData().QueryTagLevel1All();
        System.out.println(typeLevel1s);
    }

    @org.junit.Test
    public void QueryTagLevel2By1Id() {
        List<TypeLevel2> typeLevel2s = new QueryData().QueryTagLevel2By1Id(1);
        System.out.println(typeLevel2s);
    }

    @org.junit.Test
    public void QueryBasicInfoByTagLevel2Id() {
        List<BasicData> typeLevel2s = new QueryData().QueryBasicInfoByTagLevel2Id(9);
        System.out.println(typeLevel2s.size());
    }

    @org.junit.Test
    public void QueryBasicByTag2IdLimit() {
        List<BasicInfo> typeLevel2s = new QueryData().QueryBasicByTag2IdLimit(9,5);
        System.out.println(typeLevel2s.size());
    }


    @org.junit.Test
    public void QueryRelateByKeyId() {
        List<RelateKeyNData> relateKeyNData = new QueryData().QueryRelateByKeyId(1);
        System.out.println(relateKeyNData);
    }

    @org.junit.Test
    public void QueryRelateByValueIdLimit() {
        List<RelateKeyNData> relateKeyNData = new QueryData().QueryRelateByValueIdLimit(1,10);
        System.out.println(relateKeyNData.size());
    }

    @org.junit.Test
    public void QueryAttrValueById() {
        Attr_value attr_value = new QueryData().QueryAttrValueById(1);
        System.out.println(attr_value);
    }

    @org.junit.Test
    public void QueryAttrValueByKeyId() {
        List<Attr_value> attr_value = new QueryData().QueryAttrValueByKeyId(1);
        System.out.println(attr_value);
    }

    @org.junit.Test
    public void QueryAttrValueByKeyIdLimit() {
        List<Attr_value> attr_value = new QueryData().QueryAttrValueByKeyId(1,10);
        System.out.println(attr_value);
    }

    @org.junit.Test
    public void QueryBasicInfoCountByTagLevel1() {
        System.out.println(new QueryData().QueryBasicInfoCountByTagLevel1(6));
    }

    @org.junit.Test
    public void QueryAttrKeyAll() {
        System.out.println(new QueryData().QueryAttrKeyAll().toString());
    }

    @org.junit.Test
    public void UpdateClickCounts() {
        System.out.println(new QueryData().updateClickCounts(1,0));
    }

    @org.junit.Test
    public void GetClickCountById() {
        System.out.println(new QueryData().getClickCountById(1));
    }

    @org.junit.Test
    public void QueryStatisticsNumByName() {
        System.out.println(new QueryData().queryStatisticsNumByName("visitCount"));
    }

    @org.junit.Test
    public void UpdateStatisticsNumByName() {
        System.out.println(new QueryData().updateStatisticsNumByName("visitCount",2));
    }



}