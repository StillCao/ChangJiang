package front.basic_page.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import front.basic_page.Dao.QueryData;
import front.basic_page.Domain.TypeLevel1;
import front.basic_page.Domain.TypeLevel2;

import java.util.List;

/**
 * 描述:
 * 目录树Json操作
 *
 * @author black-leaves
 * @createTime 2020-06-17  16:05
 */

public class CatalogService {

    //返回处理好的目录树JsonString

    public String getCatalogJsonString() {
        QueryData queryData = new QueryData();

        List<TypeLevel1> typeLevel1s = queryData.QueryTagLevel1All();
        JSONArray Level1sJson = (JSONArray) JSONArray.toJSON(typeLevel1s);
        Level1sJson.forEach(level1 -> {
            int index = Level1sJson.indexOf(level1);
            int id = typeLevel1s.get(index).getId();
            ((JSONObject) level1).put("label", typeLevel1s.get(index).getT1_name());
            ((JSONObject) level1).put("num", queryData.QueryBasicInfoCountByTagLevel1(id));

            List<TypeLevel2> typeLevel2s = queryData.QueryTagLevel2By1Id(id);
            JSONArray Level2sJson = (JSONArray) JSONArray.toJSON(typeLevel2s);
            Level2sJson.forEach(level2 -> {
                int indexx = Level2sJson.indexOf(level2);
                ((JSONObject) level2).put("label", typeLevel2s.get(indexx).getT2_name());
            });
            ((JSONObject) level1).put("children", Level2sJson);
        });
        return Level1sJson.toJSONString();
    }
}
