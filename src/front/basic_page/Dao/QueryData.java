package front.basic_page.Dao;

import front.basic_page.Domain.*;
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
    public List<BasicInfoPos> QueryBasicInfoByTagLevel2(int tagId) {
        String sql = "SELECT * from basic_info where da_type = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(BasicInfoPos.class), tagId);
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
     * 根据二级标签ID查询对应的基础数据,限制n条数据
     *
     * @param tagId 二级标签ID
     * @return 所有属于二级标签名的数据, 限制n条数据
     */
    public List<BasicData> QueryBasicByTag2IdLimit(int tagId, int num) {
        String sql = "SELECT * from basic_info where da_type = ? limit ?";
        return template.query(sql, new BeanPropertyRowMapper<>(BasicData.class), tagId, num);
    }

    /**
     * 根据一级标签ID查询对应的基础数据
     *
     * @param tagId 一级标签ID
     * @return 所有属于一级标签名的数据
     */
    public List<BasicInfoPos> QueryBasicInfoByTagLevel1(int tagId) {
        //先查询一级标签对应的二级标签数组
        List<Integer> integers = QueryTagLevel2IdBy1Id(tagId);

        List<BasicInfoPos> basicInfoPos = new ArrayList<>();
        integers.forEach(integer -> {
            basicInfoPos.addAll(QueryBasicInfoByTagLevel2(integer));
        });

        return basicInfoPos;
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


}
