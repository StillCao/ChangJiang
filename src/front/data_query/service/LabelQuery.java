package front.data_query.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import front.data_query.dao.Query;
import front.data_query.domain.Basic_info;

import java.util.*;

/**
 * @author Simba
 * @date 2020/11/5 19:42
 */
public class LabelQuery {

    private Query query = new Query();
    CommonQuery cQuery = new CommonQuery();

    /**
     * 单纯的标签查询
     *  1.根据标签查询，得到一个数据id集合ids
     *  2.根据ids，查询其他发生变化的标签以及相关的数据条数
     *  3.根据ids，得到对应id的数据列表，默认降序排列。包括allCounts和features两个键
     *  4.将标签列表和数据列表整合后返回
     * @param linkQueryItem
     * @return
     */
    public List<Map> LabelQueryOnly(JSONObject linkQueryItem){

        //1.通过标签单独查询，得到一个id集合，成为ids
        //1.1准备参数：获取json对象中传递的所有标签，并放入一个集合中
        List<String> labelList = new ArrayList<>();
        if (linkQueryItem.get("keyWords") != null) labelList.add(linkQueryItem.getString("keyWords"));
        if (linkQueryItem.get("disciplines") != null) labelList.add(linkQueryItem.getString("disciplines"));
        if (linkQueryItem.get("placeNames") != null) labelList.add(linkQueryItem.getString("placeNames"));
        if (linkQueryItem.get("dataTypes") != null) labelList.add(linkQueryItem.getString("dataTypes"));
        if (linkQueryItem.get("dataProductions") != null) labelList.add(linkQueryItem.getString("dataProductions"));
        if (linkQueryItem.get("spatialScales") != null) labelList.add(linkQueryItem.getString("spatialScales"));
        if (linkQueryItem.get("timeResolutions") != null) labelList.add(linkQueryItem.getString("timeResolutions"));
        if (linkQueryItem.get("spatialResolutions") != null) labelList.add(linkQueryItem.getString("spatialResolutions"));
        if (linkQueryItem.get("scales") != null) labelList.add(linkQueryItem.getString("scales"));
        if (linkQueryItem.get("satelliteSensors") != null) labelList.add(linkQueryItem.getString("satelliteSensors"));
        //1.2 根据标签集合，单独进行标签查询，得到一个id集合，称为id
        List<Integer> ids = query.query_link(labelList);

        //2.通过ids集合，查询其他发生变化的标签以及相关的数据条数
        List<Map> result = query.query_by_id(ids);

        //3.根据ids，得到对应id的数据列表，默认降序排列。包括allCounts和features两个键
        List<Map> mapList = new ArrayList<>();
        try {
            mapList = query.doclist_dyn(true,0,0,ids);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //4.将标签列表和数据列表整合后返回
        Map map = new HashMap();
        map.put("docList",mapList);
        result.add(map);
        return result;
    }

    /**
     * 标签查询+二次检索
     *  1.调用本类中单独的标签查询方法，根据键"features"获取到数据列表。然后遍历数据列表，取出对应的id集合
     *  2.根据参数，判断排序条件是点击量还是时间。
     *  3.根据排序条件和查询条件的约束，对标签查询的结果再次筛选
     * @param linkQueryItem
     * @param secQueryItem
     * @return
     */
    public List<Map> LabelAndSearchSec(JSONObject linkQueryItem,JSONObject secQueryItem){

        LabelQuery labelQuery = new LabelQuery();

        //1.调用本类中单独的标签查询方法，根据键"features"获取到数据列表
        List<Basic_info> list = new ArrayList<>();//暂时存放单纯的标签查询得到的数据列表
        List<Map> tempList = labelQuery.LabelQueryOnly(linkQueryItem);
        for (Map map : tempList) {
            if (map.containsKey("docList")){
                List<Map> mapList = (List<Map>) map.get("docList");
                for (Map map1 : mapList) {
                    if (map1.containsKey("features")) list = (List<Basic_info>) map1.get("features");
                }
            }
        }
        //然后遍历数据列表，取出对应的id集合
        List<Integer> idList = new ArrayList<>();
        for (Basic_info basic_info : list) {
            idList.add(basic_info.getId());
        }

        //2.根据排序条件和查询条件的约束，对标签查询的结果再次筛选
        return  cQuery.SearchSec(secQueryItem,idList);
    }
}
