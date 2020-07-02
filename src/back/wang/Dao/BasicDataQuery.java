package back.wang.Dao;

import back.wang.Domain.BasicInfoAll;
import back.wang.Domain.News;
import front.basic_page.Domain.BasicInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

/**
 * 描述:
 * 基础数据表 查询
 *
 * @author black-leaves
 * @createTime 2020-07-02  9:59
 */

public class BasicDataQuery {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * @return 查询 basic 表条数
     */
    public int queryDataCount() {
        String sql = "select count(*) from basic_info";
        return template.queryForObject(sql, Integer.class);
    }

    /**
     * 查询所有记录
     *
     * @return 分页查询
     */
    public List<BasicInfoAll> queryDataAllByPage(int startPos, int count) {
        String sql = "select * from basic_info limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(BasicInfoAll.class), startPos, count);
    }

    /**
     * @return 根据key 查询 对应的value 在 basic_info表中的条数
     */
    public int queryDataByKeyCount(String key, String value) {
        value = "%" + value + "%";
        String sql = "select count(*) from basic_info where " + key + " like ?";
        return template.queryForObject(sql, Integer.class, value);
    }

    /**
     * key 模糊查询
     *
     * @param key BasicInfoAll 表中的字段值
     * @return 对应的 BasicInfoAll
     */
    public List<BasicInfoAll> queryDataByKeyLikeByPage(String key, String value, int startPos, int count) {
        value = "%" + value + "%";
        String sql = "select * from basic_info where " + key + " like '" + value + "'  Order By up_time Desc limit ?,? ";
        return template.query(sql, new BeanPropertyRowMapper<>(BasicInfoAll.class), startPos, count);
    }
}
