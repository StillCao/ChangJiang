package front.data_query.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import front.data_query.dao.Query;
import front.data_query.domain.Basic_info;

import java.util.*;

/**
 * @author Simba
 * @date 2020/11/5 20:32
 */
public class CommonQuery {

    private Query query = new Query();

    /**
     * 根据json对象和上一步的数据id集合，得到二次查询后的标签集合和数据列表。
     * 注：二次查询时，标签也会随之变化。数据总数较多时，变化比较明显
     * @param jsonObj
     * @param idList
     * @return
     */
    public List<Map> SearchSec(JSONObject jsonObj, List<Integer> idList){

        List<Map> resMapList = new ArrayList<>();//存放二次查询结果

        //准备二次查询的参数
        //（1）对排序后的数据集合进行关键字、开始时间和结束时间的二次筛选
        String searchWord = null;
        if (jsonObj.getString("searchWord") != null) searchWord = jsonObj.getString("searchWord");

        //（2） 准备开始时间和结束时间参数
        int startTime = 0;
        int endTime = 0;
        if (jsonObj.get("startTime") != null) startTime = jsonObj.getInteger("startTime");
        if (jsonObj.get("endTime") != null) endTime = jsonObj.getInteger("endTime");

        //2.判断排序条件是时间排序还是访问量排序
        if (jsonObj.get("updated") != null){
            //2.1按时间排序进行二次筛选
            boolean updated = true;//true为时间降序，false为时间升序
            if (jsonObj.getInteger("updated") == 1) updated = false;
            List<Map> mapList = new ArrayList<>();
            try {
                mapList = query.doclist_dyn(updated,0,0,idList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            List<Basic_info> infoList = new ArrayList<>();
            for (Map map : mapList) {
                if (map.containsKey("features")) infoList = (List<Basic_info>) map.get("features");
            }

            resMapList = query.search_sec(searchWord, searchWord, startTime, endTime, infoList);

        }else if (jsonObj.get("clickCounts") != null){

            int clickCounts = jsonObj.getInteger("clickCounts");
            //2.1获取文件列表，默认降序排列
            List<Map> mapList = new ArrayList<>();
            try {
                mapList = query.doclist_dyn(true,0,0,idList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            List<Basic_info> infoList = new ArrayList<>();
            for (Map map : mapList) {
                if (map.containsKey("features")) infoList = (List<Basic_info>) map.get("features");
            }

            //2.2 传入参数，进行二次查询
            resMapList = query.search_sec(searchWord, searchWord, startTime, endTime, infoList);
            //取出2.2中得到数据列表
            List<Basic_info> resList = new ArrayList<>();
            for (Map map : resMapList) {
                if (map.containsKey("features")) resList = (List<Basic_info>) map.get("features");
            }

            //2.3 对resList结果集合按照点击量clickCounts进行排序
            Collections.sort(resList, new Comparator<Basic_info>() {
                @Override
                public int compare(Basic_info o1, Basic_info o2) {
                    int result = 0;
                    if (clickCounts == 1) result = o1.getClick_count() - o2.getClick_count();//点击量降序
                    if (clickCounts == 0) result = o2.getClick_count() - o1.getClick_count();//点击量升序

                    if (result == 0) {
                        result = o2.getId() - o1.getId();//id升序
                    }
                    return result;
                }
            });

            //2.4 将排序后的结果列表放入resMapList中的map集合里的"features"标签下
            for (Map map : resMapList) {
                if (map.containsKey("features")) map.put("features",resList);
            }

        }else {//两个排序条件均为空，只根据开始时间startTime和endTime筛选
            //2.1 获取文件列表，默认降序排列
            List<Map> mapList = new ArrayList<>();
            try {
                query.doclist_dyn(true,0,0,idList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            List<Basic_info> infoList = new ArrayList<>();
            for (Map map : mapList) {//取出features键对应的数据列表
                if (map.containsKey("features")) infoList = (List<Basic_info>) map.get("features");
            }

            //2.2 对数据列表进行二次查询
            resMapList = query.search_sec(searchWord, searchWord, startTime, endTime, infoList);
        }

        //3. 根据二次查询结果，获取结果中数据列表的id集合
        // 取出数据集合，得到对应的id集合
        List<Integer> ids = new ArrayList<>();
        for (Map map : resMapList) {
            if (map.containsKey("features")) {
                List<Basic_info> infos = (List<Basic_info>) map.get("features");
                for (Basic_info info : infos) {
                    ids.add(info.getId());
                }
            }
        }

        //通过id集合确定发生联系的标签
        List<Map> result = query.query_by_id(ids);
        //将最终的数据总数和数据列表作为一个map的值，其键为"docList"。添加到result中
        Map map = new HashMap();
        map.put("docList",resMapList);
        result.add(map);
        return result;
    }

    /**
     * 根据标签集合，得到相关的id集合
     * @param linkQueryItem
     * @return
     */
    public List<Integer> LabelQuery(JSONObject linkQueryItem){
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
        return ids;
    }

    /**
     * 获取上一步操作得到的数据列表。根据上一部数据的json结构，该结果是键"docList"下的键"features"的value值
     * @param result
     * @return
     */
    public List<Basic_info> infoFromPreviousResult(List<Map> result){

        List<Map> mapList = new ArrayList<>();
        List<Basic_info> infos = new ArrayList<>();
        for (Map maps : result) {
            if (maps.containsKey("docList")) {
                mapList = (List<Map>) maps.get("docList"); //得到"docList"
                for (Map map : mapList) {
                    if (map.containsKey("features")) infos = (List<Basic_info>) map.get("features");
                }
            }
        }
        return infos;
    }

    /**
     * 对最终结果分页，然后返回分页后的结果
     *  1. 获取最终结果的数据列表。该列表是键"docList"下的键"features"的value值
     *  2. 对数据列表进行分页。
     *  3. 将分页后的结果放回"features"下
     * @param result
     * @return
     */
    public List<Map> devideInfoListByPage(List<Map> result, int currentPage, int pageSize){

        //1.获取最终结果的数据列表。该列表是键"docList"下的键"features"的value值
        List<Map> mapList = new ArrayList<>();
        List<Basic_info> infos = new ArrayList<>();
        for (Map maps : result) {
            if (maps.containsKey("docList")) {
                mapList = (List<Map>) maps.get("docList"); //得到"docList"
                for (Map map : mapList) {
                    if (map.containsKey("features")) infos = (List<Basic_info>) map.get("features");
                }
            }
        }

        //2.对数据列表进行分页
        List<Basic_info> infoList = new ArrayList<>();//存放分页后的数据列表
        int startIndex = (currentPage - 1) * pageSize;//分页后的数据列表首数据索引
        if (startIndex <= infos.size()) {//当分页条件能够获取到数据时，才进行分页。否则分页结果为空
            for (int i = startIndex; i < infos.size() && i < pageSize * currentPage; i++) {
                infoList.add(infos.get(i));
            }
        }

        //3.将分页后的结果放回"features"下
        for (Map maps : result) {
            if (maps.containsKey("docList")) {
                mapList = (List<Map>) maps.get("docList"); //得到"docList"
                for (Map map : mapList) {
                    if (map.containsKey("features")) map.put("features",infoList);//将数据列表更新为分页后的数据列表
                }
            }
        }

        return result;
    }
}
