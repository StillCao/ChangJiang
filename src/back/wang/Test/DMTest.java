package back.wang.Test;

import back.wang.Dao.BasicDataQuery;
import back.wang.Dao.NewsQuery;
import back.wang.Domain.News;
import back.wang.Service.DMService;

import java.util.List;

/**
 * 描述:
 * 数据管理测试
 *
 * @author black-leaves
 * @createTime 2020-07-02  10:01
 */

public class DMTest {
    @org.junit.Test
    public void queryNewsCount() {
        System.out.println(new BasicDataQuery().queryDataCount());
    }

    @org.junit.Test
    public void queryDataAll() {
        System.out.println(new BasicDataQuery().queryDataAllByPage(0,9));
    }

    @org.junit.Test
    public void allData() {
        System.out.println(new DMService().allData(1,9));
    }

    @org.junit.Test
    public void dataByKey() {
        System.out.println(new DMService().dataByKey("NAME","长江",1,9));
    }

    @org.junit.Test
    public void queryDataByKeyCount() {
        System.out.println(new BasicDataQuery().queryDataByKeyCount("NAME","长江"));
    }

    @org.junit.Test
    public void queryDataByKeyLikeByPage() {
        System.out.println(new BasicDataQuery().queryDataByKeyLikeByPage("NAME","长江",0,9));
    }

    @org.junit.Test
    public void deleteRelaChartByDataId() {
        System.out.println(new BasicDataQuery().deleteRelaChartByDataId(36));
    }

    @org.junit.Test
    public void deleteBasicData() {
        System.out.println(new BasicDataQuery().deleteBasicData(36));
    }

    @org.junit.Test
    public void deleteOrderByDataId() {
        System.out.println(new BasicDataQuery().deleteOrderByDataId(39));
    }
}
