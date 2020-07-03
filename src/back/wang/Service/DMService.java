package back.wang.Service;

import back.wang.Dao.BasicDataQuery;
import back.wang.Dao.NewsQuery;
import back.wang.Domain.BasicInfoAll;
import back.wang.Domain.News;
import back.wang.Domain.Page;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.util.List;
import java.util.Objects;

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

    /**
     * 根据 数据id 删除相关表中 属于该数据Id的记录
     *
     * @param id 数据id
     * @return 是否删除成功
     */
    public boolean deleteRelaChart(int id) {
        BasicDataQuery basicDataQuery = new BasicDataQuery();
        return basicDataQuery.deleteRelaChartByDataId(id);
    }

    /**
     * 删除文件
     *
     * @param basicInfo 基础数据对象
     * @return 结果数组长度为4，分别代表 图片、数据附件、数据文档、样例数据 的删除成功与否
     */
    public boolean[] deleteFiles(BasicInfoAll basicInfo) {
        boolean[] results = new boolean[4];

        //删除图片
        String imgUrl = basicInfo.getImage();
        String rootUrl = "http://101.37.83.223:8025";
        if (imgUrl != null) {
            if (!imgUrl.startsWith(rootUrl)) { //url路径
                results[0] = false;
            } else {
                String rootPath = "C:\\ftp\\ChangJiang";
                String imgPath = imgUrl.replace(rootUrl, rootPath);
                results[0] = deleteFileNParentFile(imgPath);
            }
        } else {
            results[0] = true;
        }


        //删除文件
        String daPath = basicInfo.getDa_url();
        String samplePath = basicInfo.getSample_url();
        String filePath = basicInfo.getFile_url();
        results[1] = deleteFileNParentFile(daPath);
        results[2] = deleteFileNParentFile(samplePath);
        results[3] = deleteFileNParentFile(filePath);

        return results;
    }

    // 删除文件，删除后如父文件夹为空，删除父文件夹
    private static boolean deleteFileNParentFile(String filePath) {
        if (filePath == null) {
            return true;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return true;
        } else {
            if (!file.delete()) {
                return false;
            } else {
                File parent = file.getParentFile();
                if (Objects.requireNonNull(parent.list()).length == 0) {
                    parent.delete();
                }
                return true;
            }
        }
    }


    /**
     * 根据数据id 删除数据记录
     *
     * @param id 数据id
     * @return 是否删除成功
     */
    public boolean deleteBasicData(int id) {
        BasicDataQuery basicDataQuery = new BasicDataQuery();
        return basicDataQuery.deleteBasicData(id);
    }


    /**
     * 根据数据id 删除订单记录
     *
     * @param id 数据id
     * @return 是否删除成功
     */
    public boolean deleteOrderConfirm(int id) {
        BasicDataQuery basicDataQuery = new BasicDataQuery();
        return basicDataQuery.deleteOrderByDataId(id);
    }
}
