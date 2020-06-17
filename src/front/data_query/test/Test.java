package front.data_query.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import front.data_query.dao.Query;

import java.util.List;
import java.util.Map;

public class Test {

    @org.junit.Test
    public void test1() throws JsonProcessingException {
        /*List<Map> maps = new Query().queryLable(null);
        String s = new ObjectMapper().writeValueAsString(maps);
        System.out.println(s);*/

        List<Map> mapList = new Query().queryLable("湖北省");
        String str = new ObjectMapper().writeValueAsString(mapList);
        System.out.println(str);
    }
}
