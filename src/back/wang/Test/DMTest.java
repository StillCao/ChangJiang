package back.wang.Test;

import org.junit.Test;

import back.wang.Dao.BasicDataQuery;
import back.wang.Dao.NewsQuery;
import back.wang.Domain.News;
import back.wang.Service.DMService;
import front.basic_page.Dao.QueryData;

import java.util.*;

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
        System.out.println(new BasicDataQuery().queryDataAllByPage(0, 9));
    }

    @org.junit.Test
    public void allData() {
        System.out.println(new DMService().allData(1, 9));
    }

    @org.junit.Test
    public void dataByKey() {
        System.out.println(new DMService().dataByKey("NAME", "长江", 1, 9));
    }

    @org.junit.Test
    public void queryDataByKeyCount() {
        System.out.println(new BasicDataQuery().queryDataByKeyCount("NAME", "长江"));
    }

    @org.junit.Test
    public void queryDataByKeyLikeByPage() {
        System.out.println(new BasicDataQuery().queryDataByKeyLikeByPage("NAME", "长江", 0, 9));
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

    @org.junit.Test
    public void QueryBasicByTag2IdLimit() {
        System.out.println(new QueryData().QueryBasicByTag2IdLimit(39, 8));
    }


    @org.junit.Test
    public void queryDataById() {
        System.out.println(new QueryData().queryDataById(39));
    }

    @org.junit.Test
    public void listSort() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(2, 1);
        map.put(3, 4);
        map.put(4, 3);
        map.put(5, 5);
        map.put(6, 7);

        List<Map.Entry<Integer, Integer>> infoIds = new ArrayList<>(map.entrySet());

        for (Map.Entry<Integer, Integer> infoId : infoIds) {
            String id = infoId.toString();
            System.out.println(id);
        }

        //根据value排序
        infoIds.sort((o1, o2) -> (o2.getValue() - o1.getValue()));

        for (Map.Entry<Integer, Integer> infoId : infoIds) {
            String id = infoId.toString();
            System.out.println(id);
        }

    }

    @Test
    public void deleteShopCarByDataId() {
        System.out.println(new BasicDataQuery().deleteShopCarByDataId(34));
    }


}
