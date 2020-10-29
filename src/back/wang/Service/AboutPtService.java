package back.wang.Service;

import com.alibaba.fastjson.JSON;

import java.util.List;

import back.wang.Dao.AboutPlatformQuery;
import back.wang.Domain.Page;
import back.wang.Domain.AboutPlatform;

/**
 * @author wwx-sys
 * @time 2020-10-29-8:59
 * @description 关于平台服务类
 */
public class AboutPtService {
    AboutPlatformQuery query = new AboutPlatformQuery();

    /**
     * 关于平台数据添加
     *
     * @param data AboutPlatform 对象
     * @return 返回添加状态
     */
    public boolean dataAdd(AboutPlatform data) {
        return query.insertData(data);
    }

    /**
     * 所有分页查询
     *
     * @param currentPage  当前页面
     * @param currentCount 每页条数
     * @return Page<>转为的JsonString
     */
    public String allData(int currentPage, int currentCount) {

        int totalCount = query.queryDataCount();
        int totalPage = totalCount % currentCount == 0 ? totalCount / currentCount : totalCount / currentCount + 1;
        int startPosition = (currentPage - 1) * currentCount;
        List<AboutPlatform> newsList = query.queryAllDataByPage(startPosition, currentCount);
        Page<AboutPlatform> page = new Page<>(currentPage, currentCount, totalPage, totalCount, newsList);
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

        int totalCount = query.queryDataByKeyCount(key, value);
        int totalPage = totalCount % currentCount == 0 ? totalCount / currentCount : totalCount / currentCount + 1;
        int startPosition = (currentPage - 1) * currentCount;

        List<AboutPlatform> newsList = query.queryDataByKeyLikeByPage(key, value, startPosition, currentCount);
        Page<AboutPlatform> page = new Page<>(currentPage, currentCount, totalPage, totalCount, newsList);
        return JSON.toJSONString(page);
    }

    /**
     * 根据ID查询数据
     *
     * @param id id
     */
    public AboutPlatform dataById(int id) {
        return query.queryDataById(id);
    }

    /**
     * 修改关于平台数据
     *
     * @param data 要修改的AboutPlatform对象
     * @return 是否修改成功
     */
    public boolean dataUpdate(AboutPlatform data) {
        int id = data.getId();
        if (id > 0) { //再进行数据库修改
            return query.updateDataById(data);
        }
        return false;
    }

    /**
     * @param id 要删除的AboutPlatform id
     * @return 是否删除成功
     */
    public boolean dataDelete(int id) {
        if (id > 0) {
            return query.deleteData(id);
        }
        return false;
    }
}
