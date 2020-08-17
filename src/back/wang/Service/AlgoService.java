package back.wang.Service;

import com.alibaba.fastjson.JSON;

import java.util.List;

import back.wang.Dao.AdminQuery;
import back.wang.Dao.AlgoQuery;
import back.wang.Domain.Admin;
import back.wang.Domain.Page;
import back.wang.Domain.TypicalAlgo;

/**
 * @author wwx-sys
 * @time 2020-08-17-18:46
 * @description 典型算法服务
 */
public class AlgoService {

    /**
     * @param currentPage  当前页码
     * @param currentCount 每页条数
     * @return Page<TypicalAlgo>转为的JsonString
     */
    public String allAlgoService(int currentPage, int currentCount) {
        AlgoQuery algoQuery = new AlgoQuery();
        int totalCount = algoQuery.getAlgoCounts();
        int totalPage = totalCount % currentCount == 0 ? totalCount / currentCount : totalCount / currentCount + 1;
        int startPosition = (currentPage - 1) * currentCount;
        List<TypicalAlgo> typicalAlgos = algoQuery.queryAlgoByPage(startPosition, currentCount);
        Page<TypicalAlgo> page = new Page<>(currentPage, currentCount, totalPage, totalCount, typicalAlgos);
        return JSON.toJSONString(page);
    }

    /**
     * 插入一条典型算法
     *
     * @param algo TypicalAlgo对象
     * @return 是否插入成功
     */
    public boolean insertAlgo(TypicalAlgo algo) {
        AlgoQuery algoQuery = new AlgoQuery();

        //先判断标签是否需要插入，需要插入则插入
        String tags = algo.getTags();
        if (tags != null && tags.length() >= 1) {
            if (tags.contains(",")) {
                String[] splits = tags.split(",");
                for (String split : splits) {
                    try{
                        int id = Integer.parseInt(split);
                        if (!algoQuery.isTagsExists(id)){       //不存在就添加
//                            if (!algoQuery.tagInsert())
                        }
                    }
                    catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;

    }
}
