package front.basic_page.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import front.basic_page.Dao.QueryBasicData;
import front.basic_page.Dao.QueryData;
import front.basic_page.Domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 首页数据展示服务
 *
 * @author black-leaves
 * @createTime 2020-06-18  9:39
 */

public class IndexDataShowService {

    //返回各个一级类型下的 num 条数据, url是当前项目的url地址
    public String getLevel1Data5(int num, String url) {
        QueryData queryData = new QueryData();
        QueryBasicData queryBasicData = new QueryBasicData();

        List<TypeLevel1> level1s = queryData.QueryTagLevel1All();

        JSONObject resultObject = new JSONObject();
        level1s.forEach(level1 -> {
            List<BasicInfo> basicDataSub = new ArrayList<>();

            List<TypeLevel2> typeLevel2List = queryData.QueryTagLevel2By1Id(level1.getId());
            for (TypeLevel2 typeLevel2 : typeLevel2List) {
                if (basicDataSub.size() > num) {            //遍历一遍tag2，如果已经满足num条数，限制为num条，并停止遍历
                    basicDataSub = basicDataSub.subList(0, num);
                    break;
                }
                int newnum = num - basicDataSub.size();     // 下一次查询只需要查 newnum 条
                List<BasicInfo>  basicInfos = queryData.QueryBasicByTag2IdLimit(typeLevel2.getId(), newnum);
                if (basicInfos != null){
                    basicDataSub.addAll(basicInfos);
                }
            }
            JSONArray jsonArray = (JSONArray) JSONArray.toJSON(basicDataSub);
//            List<BasicData> finalBasicDataSub = basicDataSub;
//            jsonArray.forEach(jsonObject -> {         //转化成jsonArray添加处理后的image字段
//                int index = jsonArray.indexOf(jsonObject);
//                BasicData basic = finalBasicDataSub.get(index);
//                String picRealPath = queryBasicData.queryImage(basic.getId());
//                String picContextPath = "";
//                if (picRealPath != null) {
//                    if (picRealPath.contains("长江地学\\")) {
//                        String[] picPathSplits = picRealPath.split("长江地学\\\\");
//                        if (picPathSplits.length == 2) {
//                            String picHalfPath = picPathSplits[1].replace("\\", "/");
//                            picContextPath = url + "/" + picHalfPath;
//                        }
//                        System.out.println(picContextPath);
//                    }
//                }
//                ((JSONObject) jsonObject).put("image", picContextPath);
//            });

            resultObject.put(level1.getT1_name(), jsonArray);
        });

        return resultObject.toJSONString();
    }

    /**
     * @param num  主题词类型下主题词值限制条数
     * @param type 主题词类型 1、2、3
     * @return 主题词值加上属于主题词值的数据记录条数
     */
    public String getSubjectData(int num, int type) {
        QueryData queryData = new QueryData();

        List<Attr_value> attr_valueList = queryData.QueryAttrValueByKeyId(type, num);
        JSONArray attrValueJsonArray = (JSONArray) JSONArray.toJSON(attr_valueList);

        attrValueJsonArray.forEach(attrValueJson -> {
            int index = attrValueJsonArray.indexOf(attrValueJson);
            Attr_value attr_value = attr_valueList.get(index);
            int dataNum = queryData.QueryRelateCountByValueId(attr_value.getV_id());
            ((JSONObject) attrValueJson).put("num", dataNum);
        });

        return attrValueJsonArray.toJSONString();
    }
}
