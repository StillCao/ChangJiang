package back.wang.Test;

import org.junit.Test;

import java.util.Date;

import back.wang.Dao.ThematicQuery;
import back.wang.Domain.ThematicData;

/**
 * @author wwx-sys
 * @time 2020-10-26-19:54
 * @description 专题数据测试
 */
public class ThematicTest {
    ThematicQuery query = new ThematicQuery();
    @Test
    public void insertData(){
        ThematicData data = new ThematicData();
        data.setThematicTitle("test");
        data.setPublishTime(new Date());
        data.setThematicContent("hahahahhahahahaha");
        data.setThematicLink("101.37.83.223:8080/CJCenter/#Index");
        System.out.println(query.insertData(data));
    }

    @Test
    public void queryDataCount(){
        System.out.println(query.queryDataCount());
    }

    @Test
    public void queryAllDataByPage(){
        System.out.println(query.queryAllDataByPage(0,2));
    }

    @Test
    public void queryDataByKeyCount(){
        System.out.println(query.queryDataByKeyCount("thematicTitle","a"));
    }

    @Test
    public void queryDataByKeyLikeByPage(){
        System.out.println(query.queryDataByKeyLikeByPage("thematicTitle","a",0,2));
    }

    @Test
    public void updateDataById(){
        ThematicData data = new ThematicData();
        data.setId(1);
        data.setThematicTitle("test1111");
        data.setPublishTime(new Date());
        data.setThematicContent("hahahahhahahahaha");
        data.setThematicLink("101.37.83.223:8080/CJCenter/#Index");

        System.out.println(query.updateDataById(data));
    }

}
