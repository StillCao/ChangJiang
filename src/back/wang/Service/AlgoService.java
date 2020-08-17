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
}
