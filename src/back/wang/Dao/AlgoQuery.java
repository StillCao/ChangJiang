package back.wang.Dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Arrays;
import java.util.List;

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
     * 根据标签name 获取对应的标签
     */
    public TypicalAlgoTags getTagsByName(String name) {
        String sql = "SELECT * FROM typical_algo_tags WHERE name = ?";
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(TypicalAlgoTags.class), name);
        } catch (DataAccessException exception) {
            return null;
        }

    }

    /**
     * 插入一条数据到 typical_algo 表
     *
     * @param typical_algo typical_algo 对象
     * @return 插入后的主键ID
     */
    public int algoInsert(TypicalAlgo typical_algo) {
//        String sql = "Insert into typical_algo (id,name,tags,digest,description,doc_url,up_user,up_unit,up_date) values (null,:name,:tags,:digest,:description,:doc_url,:up_user,:up_unit,CURRENT_TIMESTAMP)";
        //用上面sql语句插入tags时会自动调用tags的get方法，由于我们重写了tags 的get方法，使得它不是原本的值，因此sql使用字符串拼接tags
        String sql = "";
        if (typical_algo.tags !=null) {
            sql = "Insert into typical_algo (id,name,digest,description,doc_url,up_user,up_unit,up_date,tags) values (null,:name,:digest,:description,:doc_url,:up_user,:up_unit,CURRENT_TIMESTAMP," + new String(typical_algo.tags) + ")";
        }
        else {
            sql = "Insert into typical_algo (id,name,digest,description,doc_url,up_user,up_unit,up_date,tags) values (null,:name,:digest,:description,:doc_url,:up_user,:up_unit,CURRENT_TIMESTAMP,null)";
        }
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
//        String sql = "Insert into typical_algo_tags (id,name,algo) values (null,:name,:algo)";
        //原理同上
        String sql = "";
        if (typicalAlgoTags.algo !=null) {
           sql  = "Insert into typical_algo_tags (id,name,algo) values (null,:name," + new String(typicalAlgoTags.algo) + ")";
        }
        else {
            sql  = "Insert into typical_algo_tags (id,name,algo) values (null,:name,null)";
        }
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
     * 更新tags表中的 algo 字段
     *
     * @param tagId 需要更改algo的tagId
     * @param algo  修改后的algo
     */
    public boolean updateTagsAlgo(int tagId, byte[] algo) {
        String sql = "UPDATE typical_algo_tags SET algo = ? WHERE id = ?";
        return template.update(sql, algo, tagId) > 0;
    }

    /**
     * 更新algo表中的 tags 字段
     *
     * @param id   需要更改的algoId
     * @param tags 修改后的tags
     */
    public boolean updateAlgoTags(int id, byte[] tags) {
        String sql = "UPDATE typical_algo SET tags = ? WHERE id = ?";
        return template.update(sql, tags, id) > 0;
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


}
