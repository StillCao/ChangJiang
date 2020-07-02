package back.wang.Service;

import back.wang.Dao.BasicDataQuery;
import back.wang.Dao.NewsQuery;
import back.wang.Domain.BasicInfoAll;
import back.wang.Domain.News;
import back.wang.Domain.Page;
import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 描述:
 * 数据管理服务
 *
 * @author black-leaves
 * @createTime 2020-07-02  9:55
 */

public class DMService {

    /**
     * 所有数据记录的分页查询
     *
     * @param currentPage  当前页面
     * @param currentCount 每页条数
     * @return Page<>转为的JsonString
     */
    public String allData(int currentPage, int currentCount) {
        BasicDataQuery basicDataQuery = new BasicDataQuery();
        int totalCount = basicDataQuery.queryDataCount();
        int totalPage = totalCount % currentCount == 0 ? totalCount / currentCount : totalCount / currentCount + 1;
        int startPosition = (currentPage - 1) * currentCount;
        List<BasicInfoAll> newsList = basicDataQuery.queryDataAllByPage(startPosition, currentCount);
        Page<BasicInfoAll> page = new Page<>(currentPage, currentCount, totalPage, totalCount, newsList);
        return JSON.toJSONString(page);
    }

    /**
     * 根据 key值和 value值分页查询
     *
     * @param key          字段名称
     * @param value        字段值
     * @param currentPage  当前页码
     * @param currentCount 每页条数
     * @return Page<>转为的JsonString
     */
    public String dataByKey(String key, String value, int currentPage, int currentCount) {
        BasicDataQuery basicDataQuery = new BasicDataQuery();
        int totalCount = basicDataQuery.queryDataByKeyCount(key, value);
        int totalPage = totalCount % currentCount == 0 ? totalCount / currentCount : totalCount / currentCount + 1;
        int startPosition = (currentPage - 1) * currentCount;

        List<BasicInfoAll> newsList = basicDataQuery.queryDataByKeyLikeByPage(key, value, startPosition, currentCount);
        Page<BasicInfoAll> page = new Page<>(currentPage, currentCount, totalPage, totalCount, newsList);
        return JSON.toJSONString(page);
    }
}
