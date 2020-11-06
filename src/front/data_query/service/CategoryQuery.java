package front.data_query.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import front.data_query.dao.Query;
import front.data_query.domain.Basic_info;

import java.util.*;

/**
 * @author Simba
 * @date 2020/11/5 10:05
 */
public class CategoryQuery {

    Query query = new Query();
    CommonQuery cQuery = new CommonQuery();

    /**
     * 单纯的数据分类查询。步骤描述
     *  1.根据categoryId和categoryType得到相应的id集合
     *  2.通过id集合得到对应的标签及其数量
     *  3.将得到的id集合，进行不分页文件列表查询
     * @param categoryId
     * @param categoryType
     * @return
     */
    public List<Map> CategoryQueryOnly(int categoryId, int categoryType){

        //1.根据categoryId和categoryType得到相应的id集合
        List<Integer> idList = query.click_category(categoryId, categoryType);

        //2.通过id集合得到对应的标签及其数量
        List<Map> maps = query.query_by_id(idList);

        //3.将得到的id集合，进行不分页文件列表查询
        List<Map> mapList = new ArrayList<>();
        try {
            mapList = query.doclist_dyn(true, 0, 0, idList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("docList",mapList);
        maps.add(map);
        return maps;
    }

    /**
     * 数据分类+二次检索。步骤如下
     *  1.传入的参数是上一步查询中得到的id集合，在这里是数据分类查询得到的id集合
     *  2.判断排序条件是时间排序还是访问量排序
     *      2.1时间排序：将id集合作为参数，得到对应的按时间排序的数据集合。再把该集合进行关键字、开始时间和结束时间的二次筛选
     *      2.2访问量排序：将id集合作为参数，得到对应的按时间默认降序排序，然后该集合进行访问量排序，然后得到数据集合。再把该集合进行关键字、开始时间和结束时间的二次筛选
     *      2.3没有排序条件，直接对该集合进行关键字、开始时间和结束时间的二次查询
     *  3.抽取出数据列表中的id集合，进行标签查询，得到有关标签及数量
     *
     * @param categoryId
     * @param categoryType
     * @param jsonObj
     * @return
     */
    public List<Map> CateQueryAndSearchSec(int categoryId, int categoryType, JSONObject jsonObj/*, List<Integer> idList*/){

        //1.根据categoryId和categoryType得到相应的id集合
        List<Integer> idList = query.click_category(categoryId, categoryType);

        return cQuery.SearchSec(jsonObj,idList);
    }

    /**
     * 数据分类+标签查询
     *  1.通过数据分类查询，得到一个id集合，成为idS1
     *  2.通过标签单独查询，得到一个id集合，成为idS2
     *  3.idS1和idS2的交集就是最终数据列表的id集合，称为idS3
     *  4.根据idS3就能得到最终的数据结果集合
     * @param categoryId
     * @param categoryType
     * @param linkQueryItem
     * @return
     */
    public List<Map> CateQueryAndLabel(int categoryId,int categoryType,JSONObject linkQueryItem){

        //1.通过数据分类查询，得到一个id集合，成为idS1
        List<Integer> ids1 = query.click_category(categoryId, categoryType);

        //2.通过标签单独查询，得到一个id集合，成为idS2
        List<Integer> ids2 = cQuery.LabelQuery(linkQueryItem);

        //3.获取ids1和ids2的交集
        List<Integer> ids3 = query.id_union(ids1, ids2);
        //根据ids3得到最终的数据结果的集合
        List<Map> resMapList = new ArrayList<>();
        try {
            query.doclist_dyn(true,0,0,ids3);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //根据id3得到相关的标签集合
        List<Map> result = query.query_by_id(ids3);

        //3.整合标签集合和数据列表，然后返回
        Map map = new HashMap();
        map.put("docList",resMapList);
        resMapList.add(map);
        return result;//因为最后一步是标签查询，所以不用再返回标签列表
    }

    /**
     * 数据分类+标签+二次检索
     *  1.数据分类+标签查询得到一个Map集合。遍历该集合，根据键"features"找到特定Map中的键值数据集合。根据数据集合得到id集合，称为ids1；
     *  2.将ids1作为参数，传入到CateQueryAndSearchSec方法中，得到最终结果。包括关键字、数据总数和对应的数据列表
     *
     * @param categoryId
     * @param categoryType
     * @param linkQueryItem
     * @param secQueryItem
     * @return
     */
    public List<Map> CateQueryAndLabelAndSearchSec(int categoryId, int categoryType, JSONObject linkQueryItem, JSONObject secQueryItem){

        CategoryQuery categoryQuery = new CategoryQuery();

        //1.数据分类+标签查询得到一个Map集合
        List<Map> mapList = categoryQuery.CateQueryAndLabel(categoryId, categoryType, linkQueryItem);
        //遍历该集合，根据键"features"找到特定Map中的键值数据集合。
        List<Basic_info> list = new ArrayList<>();//存放数据列表
//        for (Map map : mapList) {
//            if (map.containsKey("features")) list = (List<Basic_info>) map.get("features");
//        }
        list = cQuery.infoFromPreviousResult(mapList);
        //根据数据集合得到id集合，称为ids1
        List<Integer> ids1 = new ArrayList<>();
        for (Basic_info basic_info : list) {
            ids1.add(basic_info.getId());
        }

        //2.将ids1作为参数，传入到CateQueryAndSearchSec方法中，得到最终结果
        List<Map> result = cQuery.SearchSec(secQueryItem,ids1);
        return result;
    }
}
