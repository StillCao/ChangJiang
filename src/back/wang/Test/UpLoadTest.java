package back.wang.Test;

import back.wang.Dao.UpLoadInsert;
import back.wang.Domain.BasicInfoAll;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import front.basic_page.Domain.Attr_value;
import front.basic_page.Domain.BasicData;
import front.basic_page.Domain.TypeLevel2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 上传相关功能测试
 *
 * @author black-leaves
 * @createTime 2020-06-24  10:10
 */

public class UpLoadTest {

    @org.junit.Test
    public void testJson2Obj() {
        Map<String,String[]> map = new HashMap<>();
        map.put("data",new String[]{"{\"basic_info\":{\"NAME\":\"\",\"aploc\":\"\",\"docname\":\"\",\"up_time\":\"\",\"point1_lat\":-1,\"point1_lon\":-1,\"point2_lat\":-1,\"point2_lon\":-1,\"topic_w1\":\"\",\"topic_w2\":\"\",\"topic_w3\":\"\",\"topic_cfi\":\"\"},\"da_type2\":{\"t2_code\":\"1\",\"t2_name\":\"dad\",\"t1_id\":\"3\"},\"attr_value\":{\"v_name\":\"1\",\"v_id_k\":\"-1\"}}"});
        JSONObject jsonObject = JSON.parseObject(map.get("data")[0]);
        JSONObject basic_infoObj = jsonObject.getJSONObject("basic_info");
        JSONObject da_type2Obj  = jsonObject.getJSONObject("da_type2");
        JSONObject attr_valueObj  = jsonObject.getJSONObject("attr_value");

        System.out.println(da_type2Obj.getString("t2_name"));
        TypeLevel2 typeLevel2 = JSON.parseObject(da_type2Obj.toJSONString(), TypeLevel2.class);
        BasicInfoAll basic_info =  JSON.parseObject(basic_infoObj.toJSONString(), BasicInfoAll.class);
        System.out.println(typeLevel2.toString());
        System.out.println(basic_info.toString());
        basic_info.setUp_id(1);
        basic_info.setDa_type(1);
        System.out.println(new UpLoadInsert().basicDataInsert(basic_info));
    }

    @org.junit.Test
    public void testJsonArray2List(){
        String JsonString = "{\"basic_info\":{\"NAME\":\"\",\"aploc\":\"\",\"docname\":\"\",\"up_time\":\"\",\"point1_lat\":-1,\"point1_lon\":-1,\"point2_lat\":-1,\"point2_lon\":-1,\"topic_w1\":\"\",\"topic_w2\":\"\",\"topic_w3\":\"\",\"topic_cfi\":\"\",\"da_type\":2,\"up_id\":1},\"attr_value\":[{\"v_id\":-1,\"v_name\":\"asdwqd\",\"v_id_k\":4},{\"v_id\":1,\"v_name\":\"asdwqd\",\"v_id_k\":4},{\"v_id\":2,\"v_name\":\"asdwqd\",\"v_id_k\":4}]}";

        JSONObject jsonObject = JSON.parseObject(JsonString);
        JSONObject basic_infoObj = jsonObject.getJSONObject("basic_info");
        JSONArray attr_valueArray  = jsonObject.getJSONArray("attr_value");

//        JSONArray jsonArray = JSONArray.parseArray(attr_valueObj.toJSONString());
		List<Attr_value> attr_valueList = JSONObject.parseArray(attr_valueArray.toString(), Attr_value.class);
		attr_valueList.forEach(attr_value -> {
		    System.out.println(attr_value.toString());
        });
    }

    @org.junit.Test
    public void printBasicInfoFields(){
        BasicInfoAll basicInfo = new BasicInfoAll();
        String data = basicInfo.toString();
        data = data.replaceAll("(=)(.*?)(,)","");
//        data = data.replaceAll(" ",",");
        data = data.replaceAll(" ",",:");
        System.out.println(data);
    }

    @org.junit.Test
    public void attrValueInsert(){
        Attr_value attr_value = new Attr_value();
        attr_value.setV_id_k(4);
        attr_value.setV_name("sadwqdqwd");
        System.out.println(new UpLoadInsert().attrValueInsert(attr_value));
    }
}
