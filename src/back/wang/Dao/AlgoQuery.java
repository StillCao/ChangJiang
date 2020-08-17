package back.wang.Dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

import back.wang.Domain.TypicalAlgo;
import back.wang.Domain.TypicalAlgoTags;
import front.basic_page.Domain.Attr_value;
import utils.JDBCUtils;

/**
 * @author wwx-sys
 * @time 2020-08-17-14:21
 * @description 典型算法 增删查改
 */
public class AlgoQuery {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    NamedParameterJdbcTemplate npjTemplate = new NamedParameterJdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 获取所有的典型算法
     */
    public List<TypicalAlgo> getAllAlgo() {
        String sql = "SELECT * FROM typical_algo";
        return template.query(sql, new BeanPropertyRowMapper<>(TypicalAlgo.class));
    }

    /**
     * 根据算法id 获取对应的典型算法
     */
    public TypicalAlgo getAlgoById(int id) {
        String sql = "SELECT * FROM typical_algo WHERE id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(TypicalAlgo.class), id);
    }

    /**
     * 获取所有的典型算法标签
     */
    public List<TypicalAlgoTags> getAllTags() {
        String sql = "SELECT * FROM typical_algo_tags";
        return template.query(sql, new BeanPropertyRowMapper<>(TypicalAlgoTags.class));
    }

    /**
     * 根据标签id 获取对应的标签
     */
    public TypicalAlgoTags getTagsById(int id){
        String sql = "SELECT * FROM typical_algo_tags WHERE id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(TypicalAlgoTags.class), id);
    }

    /**
     * 插入一条数据到 typical_algo 表
     * @param typical_algo typical_algo 对象
     * @return 插入后的主键ID
     */
    public int algoInsert(TypicalAlgo typical_algo) {
        String sql = "Insert into typical_algo (id,name,tags,digest,description,doc_url,up_user,up_unit) values (null,:name,:tags,:digest,:description,:doc_url,:up_user,:up_unit)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        npjTemplate.update(sql, new BeanPropertySqlParameterSource(typical_algo), keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * 插入一条数据到 typical_algo_tags 表
     * @param typicalAlgoTags typicalAlgoTags 对象
     * @return 插入后的主键ID
     */
    public int tagInsert(TypicalAlgoTags typicalAlgoTags){
        String sql = "Insert into typical_algo_tags (id,name,algo) values (null,:name,:algo)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        npjTemplate.update(sql, new BeanPropertySqlParameterSource(typicalAlgoTags), keyHolder);
        return keyHolder.getKey().intValue();
    }


}
