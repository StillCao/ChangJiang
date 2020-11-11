package front.basic_page.Dao;

import back.wang.Domain.BasicInfoAll;
import front.basic_page.Domain.*;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import utils.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

public class QueryData {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * @return 查询最新8条新闻
     */
    public List<News> QueryLatest8News() {
        String sql = "SELECT * from news Order By Date Desc limit 8";
        return template.query(sql, new BeanPropertyRowMapper<>(News.class));
    }

    /**
     * @return 查询最新8条基础数据
     */
    public List<BasicInfo> QueryLatest8BasicInfo() {
        String sql = "SELECT * from basic_info Order By up_time Desc limit 8";
        return template.query(sql, new BeanPropertyRowMapper<>(BasicInfo.class));
    }

    /**
     * 根据二级标签ID查询对应的基础数据
     *
     * @param tagId 二级标签ID
     * @return 所有属于二级标签名的数据
     */
    public List<BasicData> QueryBasicInfoByTagLevel2(int tagId) {
        String sql = "SELECT * from basic_info where da_type = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(BasicData.class), tagId);
    }

    /**
     * 根据二级标签ID查询对应的基础数据
     *
     * @param tagId 二级标签ID
     * @return 所有属于二级标签名的数据
     */
    public List<BasicData> QueryBasicInfoByTagLevel2Id(int tagId) {
        String sql = "SELECT * from basic_info where da_type = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(BasicData.class), tagId);
    }

    /**
     * 根据二级标签ID查询对应的基础数据条数
     *
     * @param tagId 二级标签ID
     * @return 所有属于二级标签名的数据条数
     */
    public Integer QueryBasicInfoCountByTagLevel2Id(int tagId) {
        String sql = "SELECT count(*) from basic_info where da_type = ?";
        return template.queryForObject(sql, Integer.class, tagId);
    }

    /**
     * 根据二级标签ID查询对应的基础数据,限制n条数据
     *
     * @param tagId 二级标签ID
     * @return 所有属于二级标签名的数据, 限制n条数据
     */
    public List<BasicInfo> QueryBasicByTag2IdLimit(int tagId, int num) {
        String sql = "SELECT * from basic_info where da_type = ? limit ?";

        try {
            return template.query(sql, new BeanPropertyRowMapper<>(BasicInfo.class), tagId, num);
        } catch (DataAccessException e) {
            return null;
        }

    }

    /**
     * 根据一级标签ID查询对应的基础数据
     *
     * @param tagId 一级标签ID
     * @return 所有属于一级标签名的数据
     */
    public List<BasicData> QueryBasicInfoByTagLevel1(int tagId) {
        //先查询一级标签对应的二级标签数组
        List<Integer> integers = QueryTagLevel2IdBy1Id(tagId);

        List<BasicData> basicInfoPos = new ArrayList<>();
        integers.forEach(integer -> {
            basicInfoPos.addAll(QueryBasicInfoByTagLevel2(integer));
        });

        return basicInfoPos;
    }

    /**
     * 根据一级标签ID查询对应的基础数据条数
     *
     * @param tagId 一级标签ID
     * @return 所有属于一级标签名的数据条数
     */
    public Integer QueryBasicInfoCountByTagLevel1(int tagId) {
        //先查询一级标签对应的二级标签数组
        List<Integer> integers = QueryTagLevel2IdBy1Id(tagId);
        return integers.stream().mapToInt(this::QueryBasicInfoCountByTagLevel2Id).sum();
    }

    /**
     * 根据一级标签ID查询对应的基础数据
     *
     * @param tagId 一级标签ID
     * @return 所有属于一级标签名的数据
     */
    public List<BasicData> QueryBasicInfoByTagLevel1Id(int tagId) {
        //先查询一级标签对应的二级标签数组
        List<Integer> integers = QueryTagLevel2IdBy1Id(tagId);

        List<BasicData> basicData = new ArrayList<>();
        integers.forEach(integer -> {
            basicData.addAll(QueryBasicInfoByTagLevel2Id(integer));
        });

        return basicData;
    }

    /**
     * 根据一级标签ID查询对应的二级标签ID
     *
     * @param tagId 一级标签ID
     * @return 二级标签ID数组
     */

    public List<Integer> QueryTagLevel2IdBy1Id(int tagId) {
        String sql = "SELECT id from da_type2 where t1_id = ?";
        return template.queryForList(sql, Integer.class, tagId);
    }

    /**
     * 查询所有的一级标签
     *
     * @return
     */
    public List<TypeLevel1> QueryTagLevel1All() {
        String sql = "SELECT * from da_type1";
        return template.query(sql, new BeanPropertyRowMapper<>(TypeLevel1.class));
    }


    /**
     * 根据一级标签ID查询对应的二级标签
     *
     * @param tagId 一级标签ID
     * @return 二级标签ID数组
     */

    public List<TypeLevel2> QueryTagLevel2By1Id(int tagId) {
        String sql = "SELECT * from da_type2 where t1_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(TypeLevel2.class), tagId);
    }

    /**
     * @param key_id 主题词类型Id
     * @return 对应主题词Id 的主题词值ID和数据ID
     */
    public List<RelateKeyNData> QueryRelateByKeyId(int key_id) {
        String sql = "Select * from rela_chart where at_key_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(RelateKeyNData.class), key_id);
    }

    /**
     * @param val_id 主题词值Id
     * @param num    限制条数
     * @return 对应主题词Id 的主题词值ID和数据ID
     */
    public List<RelateKeyNData> QueryRelateByValueIdLimit(int val_id, int num) {
        String sql = "Select * from rela_chart where at_val_id = ? limit ?";
        return template.query(sql, new BeanPropertyRowMapper<>(RelateKeyNData.class), val_id, num);
    }


    /**
     * @param val_id 主题词值Id
     * @return 对应主题词Id 的主题词值ID和数据ID
     */
    public Integer QueryRelateCountByValueId(int val_id) {
        String sql = "Select count(*) from rela_chart where at_val_id = ? ";
        return template.queryForObject(sql, Integer.class, val_id);
    }


    /**
     * @param value_id 主题词值ID
     * @return 根据主题词值ID 查主题词值
     */
    public Attr_value QueryAttrValueById(int value_id) {
        String sql = "Select * from attr_value where v_id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Attr_value.class), value_id);
    }

    /**
     * @param key_id 主题词类型ID
     * @return 主题词类型ID 查主题词值
     */
    public List<Attr_value> QueryAttrValueByKeyId(int key_id) {
        String sql = "Select * from attr_value where v_id_k = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Attr_value.class), key_id);
    }


    /**
     * @param key_id 主题词类型ID
     * @return 主题词类型ID 查主题词值 限制num条
     */
    public List<Attr_value> QueryAttrValueByKeyId(int key_id, int num) {
        String sql = "Select * from attr_value where v_id_k = ? limit ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Attr_value.class), key_id, num);
    }

    /**
     * @return 查询所有AttrKey
     */
    public List<Attr_key> QueryAttrKeyAll() {
        String sql = "Select * from attr_key";
        return template.query(sql, new BeanPropertyRowMapper<>(Attr_key.class));
    }

    /**
     * @return 查询数据ID和点击量
     */
    public List<BasicInfoAll> queryDataClickCounts() {
        String sql = "Select id, click_count from basic_info";
        return template.query(sql, new BeanPropertyRowMapper<>(BasicInfoAll.class));
    }

    /**
     * @return 查询数据ID和下载量
     */
    public List<BasicInfoAll> queryDataDownloadCounts() {
        String sql = "Select id, download_count from basic_info";
        return template.query(sql, new BeanPropertyRowMapper<>(BasicInfoAll.class));
    }


    /**
     * 根据数据id 查询id,name,image字段
     *
     * @param id 数据id
     * @return 查询成功返回对象，查询失败返回null
     */
    public BasicInfo queryDataById(int id) {
        String sql = "Select id , NAME, image from basic_info where id = ?";
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(BasicInfo.class), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * 根据id更新数据点击量
     *
     * @param id          数据id
     * @param click_count 数据点击量
     * @return 是否修改成功
     */
    public boolean updateClickCounts(int id, int click_count) {
        String sql = "update basic_info set click_count = ? where id = ?";
        return template.update(sql, click_count, id) > 0;
    }

    /**
     * 根据id获取点击量
     *
     * @param id 数据id
     */
    public int getClickCountById(int id) {
        String sql = "select click_count from basic_info where id = ?";
        try {
            return template.queryForObject(sql, Integer.class, id);
        } catch (DataAccessException e) {
            return 0;
        }

    }


    /**
     * 根据id更新数据下载量
     *
     * @param id             数据id
     * @param download_count 数据下载量
     * @return 是否修改成功
     */
    public boolean updateDownloadCounts(int id, int download_count) {
        String sql = "update basic_info set download_count = ? where id = ?";
        return template.update(sql, download_count, id) > 0;
    }


    /**
     * 根据id获取下载量
     *
     * @param id 数据id
     */
    public int getDownloadCountById(int id) {
        String sql = "select download_count from basic_info where id = ?";
        try {
            return template.queryForObject(sql, Integer.class, id);
        } catch (DataAccessException e) {
            return 0;
        }

    }


    /**
     * 获取总下载量
     */
    public int getSumDownloadCount() {
        String sql = "select sum(download_count) from basic_info";
        try {
            return template.queryForObject(sql, Integer.class);
        } catch (DataAccessException e) {
            return 0;
        }

    }

    /**
     * 在统计信息表中查询对应种类信息的 数量
     *
     * @param name 种类如：页面访问量、本月服务量
     * @return
     */
    public int queryStatisticsNumByName(String name) {
        String sql = "select num from statistics where name = ?";
        try {
            return template.queryForObject(sql, Integer.class, name);
        } catch (DataAccessException e) {
            return 0;
        }

    }

    /**
     * 在统计信息表中查询对应种类信息的 数量
     *
     * @param name 种类如：页面访问量、本月服务量
     * @return
     */
    public boolean updateStatisticsNumByName(String name, int value) {
        String sql = "update statistics set num = ? where name = ?";
        return template.update(sql, value, name) > 0;
    }

    /**
     * 查询用户表中的记录条数
     */
    public int queryUserCount() {
        String sql = "select count(*) from user";
        try {
            return template.queryForObject(sql, Integer.class);
        } catch (DataAccessException e) {
            return 0;
        }
    }

    /**
     * 根据数据名称模糊查询数据记录条数
     *
     * @param name 数据名称
     */
    public List<Integer> queryIdByNameLike(String name) {
        name = "%" + name + "%";
        String sql = "select id from basic_info where name like ?";
        try {
            return template.queryForList(sql, Integer.class, name);
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * 根据数据id 修改数据存储路径
     * @param id 数据id
     * @param dataPath 数据存储路径
     * @return 是否修改成功
     */
    public boolean updateBasicDataPathById(int id, String dataPath) {
        String sql = "update basic_info set file_url = ? where id = ?";
        return template.update(sql, dataPath, id) > 0;
    }


}
