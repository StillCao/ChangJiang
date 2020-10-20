package back.wang.Dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.zip.DataFormatException;

import back.wang.Domain.AlgoTagsRelate;
import back.wang.Domain.TypicalAlgo;
import back.wang.Domain.TypicalAlgoTags;
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
     * @param startPos 起始位置
     * @param count    每页的数量
     * @return 典型算法分页查询
     */
    public List<TypicalAlgo> queryAlgoByPage(int startPos, int count) {
        String sql = "select * from typical_algo limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(TypicalAlgo.class), startPos, count);
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
    public TypicalAlgoTags getTagsById(int id) {
        String sql = "SELECT * FROM typical_algo_tags WHERE id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(TypicalAlgoTags.class), id);
    }

    /**
     * 根据标签name 获取对应的标签id
     */
    public int getTagsIdByName(String name) {
        String sql = "SELECT id FROM typical_algo_tags WHERE name = ?";
        try {
            return template.queryForObject(sql, Integer.class, name);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            return -1;
        }
    }

    /**
     * 插入一条数据到 typical_algo 表
     *
     * @param typical_algo typical_algo 对象
     * @return 插入后的主键ID
     */
    public int algoInsert(TypicalAlgo typical_algo) {
        String sql = "Insert into typical_algo (id,name,digest,description,doc_url,up_user,up_unit,up_date) values (null,:name,:digest,:description,:doc_url,:up_user,:up_unit,CURRENT_TIMESTAMP)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        npjTemplate.update(sql, new BeanPropertySqlParameterSource(typical_algo), keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * 插入一条数据到 typical_algo_tags 表
     *
     * @param typicalAlgoTags typicalAlgoTags 对象
     * @return 插入后的主键ID
     */
    public int tagInsert(TypicalAlgoTags typicalAlgoTags) {
        String sql = "Insert into typical_algo_tags (id,name) values (null,:name)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        npjTemplate.update(sql, new BeanPropertySqlParameterSource(typicalAlgoTags), keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * 查询typical_algo表总记录条数
     */
    public int getAlgoCounts() {
        String sql = "SELECT count(*) from typical_algo";
        return template.queryForObject(sql, Integer.class);
    }

    /**
     * 根据id判断标签是否已存在
     *
     * @param id 需要判断的标签id
     */
    public boolean isTagsExistsById(int id) {
        String sql = "SELECT count(*) from typical_algo_tags WHERE id = ?";
        return template.queryForObject(sql, Integer.class, id) > 0;
    }

    /**
     * 根据name判断标签是否已存在
     *
     * @param name 需要判断的标签name
     */
    public boolean isTagsExistsByName(String name) {
        String sql = "SELECT count(*) from typical_algo_tags WHERE name = ?";
        return template.queryForObject(sql, Integer.class, name) > 0;
    }


    /**
     * 根据id 删除 算法数据
     *
     * @param id 算法id
     * @return 是否删除成功
     */
    public boolean deleteAlgo(int id) {
        String sql = "DELETE FROM typical_algo WHERE id = ?";
        return template.update(sql, id) > 0;
    }

    /**
     * @param field    字段名称
     * @param value    字段值
     * @param startPos 起始位置
     * @param count    每页的数量
     * @return 典型算法模糊分页查询
     */
    public List<TypicalAlgo> queryAlgoByFieldLike(String field, String value, int startPos, int count) {
        value = "%" + value + "%";
        String sql = "select * from typical_algo WHERE " + field + " LIKE '" + value + "' limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(TypicalAlgo.class), startPos, count);
    }

    /**
     * 典型算法根据条件模糊查询条数
     *
     * @param field 字段名称
     * @param value 字段值
     * @return
     */
    public int queryAlgoCountByFieldLike(String field, String value) {
        value = "%" + value + "%";
        String sql = "select count(*) from typical_algo WHERE " + field + " LIKE '" + value + "';";
        return template.queryForObject(sql, Integer.class);
    }

    /**
     * 根据算法ID查询对应的关联表记录
     *
     * @param algo_id 算法ID
     * @return
     */
    public List<AlgoTagsRelate> queryRelateByAlgoId(int algo_id) {
        String sql = "SELECT * FROM algo_tag_rela WHERE algo_id = ?";
        try {
            return template.query(sql, new BeanPropertyRowMapper<>(AlgoTagsRelate.class), algo_id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据标签ID查询对应的关联表记录
     *
     * @param tag_id 标签ID
     * @return
     */
    public List<AlgoTagsRelate> queryRelateByTagId(int tag_id) {
        String sql = "SELECT * FROM algo_tag_rela WHERE tag_id = ?";
        try {
            return template.query(sql, new BeanPropertyRowMapper<>(AlgoTagsRelate.class), tag_id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 插入关联表记录
     *
     * @param algo_id 算法ID
     * @param tag_id 标签ID
     * @return
     */
    public boolean insertRelate(int algo_id, int tag_id) {
        String sql = "INSERT INTO algo_tag_rela (id,algo_id,tag_id) values (null ,?, ?)";
        try {
            return template.update(sql, algo_id, tag_id) > 0;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除关联表记录
     *
     * @param id  关联表ID
     * @return 是否删除成功
     */
    public boolean deleteRelate(int id) {
        String sql = "DELETE FROM algo_tag_rela WHERE id = ?";
        try {
            return template.update(sql,id) > 0;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


}
