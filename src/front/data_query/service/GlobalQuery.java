package front.data_query.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import front.data_query.dao.Query;
import front.data_query.domain.Basic_info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Simba
 * @date 2020/11/5 20:51
 *
 * 全局查询模块下的相关方法
 */
public class GlobalQuery {

    Query query = new Query();
    CommonQuery cQuery  = new CommonQuery();

    /**
     * 单纯的全局关键字查询
     *  1.根据全局查询的参数searchWord得到对应基础数据的id集合
     *  2.根据得到的id集合查询每条数据包含的标签
     *  3.根据得到的id集合查询对应的文件列表，默认以更新时间降序
     * @param searchWord
     * @return
     */
    public List<Map> globalQueryOnly(String searchWord){
        List<Integer> list = query.search_fir(searchWord, searchWord);//根据name，uper_plcae字段得到对应基础数据的id集合
        List<Map> maps = query.query_by_id(list);//根据得到的id集合查询每条数据包含的标签
        List<Map> dataMaps = null;//根据得到的id集合查询对应的文件列表，默认以更新时间降序，并将结果分页
        try {
            dataMaps = query.doclist_dyn(true, 0, 0, list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("docList",dataMaps);
        maps.add(map);//将文件列表加入到返回结果中

        return maps;
    }

    /**
     * 全局关键字查询+二次检索
     *  1. 根据全局查询的参数searchWord得到对应基础数据的id集合
     *  2. 根据得到的id集合查询满足二次查询后的标签集合和数据列表
     * @param searchWord
     * @param secQueryItem
     * @return
     */
    public List<Map> globalQueryAndSearchSec(String searchWord, JSONObject secQueryItem){
        //1.根据全局查询的参数searchWord得到对应基础数据的id集合
        List<Integer> list = query.search_fir(searchWord, searchWord);

        //2. 根据得到的id集合查询满足二次查询后的标签集合和数据列表
        List<Map> result = cQuery.SearchSec(secQueryItem, list);
        return result;
    }

    /**
     * 全局关键字查询+标签查询
     * 1.根据全局查询的参数searchWord，得到对应基础数据的id集合,称为ids1
     * 2.根据标签查询参数linkQueryItem，得到对应数据的id集合，称为ids2
     * 3.取ids1和ids2的交集，便是最终的数据列表的id集合，称为ids3
     * 4.查询ids3的数据列表和标签，将两者整合作为返回结果
     * @param searchWord
     * @param linkQueryItem
     * @return
     */
    public List<Map> globalQueryAndLableQuery(String searchWord, JSONObject linkQueryItem){

        //1.根据全局查询的参数searchWord得到对应基础数据的id集合
        List<Integer> ids1 = query.search_fir(searchWord, searchWord);

        //2.根据标签查询参数linkQueryItem，得到对应数据的id集合，称为ids2
        List<Integer> ids2 = cQuery.LabelQuery(linkQueryItem);

        //3.取ids1和ids2的交集，便是最终的数据列表的id集合，称为ids3
        List<Integer> ids3 = query.id_union(ids1, ids2);

        //4.查询ids3的数据列表和标签，将两者整合作为返回结果
        List<Map> result = query.query_by_id(ids3);
        List<Map> resMapList = new ArrayList<>();
        try {
            resMapList = query.doclist_dyn(true,0,0,ids3);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("docList",resMapList);
        result.add(map);
        return result;
    }

    /**
     * 全局关键字+标签+二次查询
     *  1.从全局关键字查询+标签查询的结果中取出数据列表。遍历数据列表，得到对应的id集合ids
     *  2.把ids作为参数，进行二次查询，得到最终结果
     * @param searchWord
     * @param linkQueryItem
     * @param secQueryItem
     * @return
     */
    public List<Map> gQueryAndLabelAndSearchSec(String searchWord,JSONObject linkQueryItem, JSONObject secQueryItem){

        //1.1 先进行全局查询+标签查询
        List<Map> mapList = new GlobalQuery().globalQueryAndLableQuery(searchWord, linkQueryItem);
        //1.2 遍历1.1中的结果列表，取出数据列表
        List<Basic_info> infos = cQuery.infoFromPreviousResult(mapList);
        //1.3 取出1.2中数据列表对应的id，得到id集合ids
        List<Integer> ids = new ArrayList<>();
        for (Basic_info info : infos) {
            ids.add(info.getId());
        }

        //2.把ids作为参数，进行二次查询，得到最终结果
        List<Map> result = cQuery.SearchSec(secQueryItem, ids);
        return result;
    }
}
