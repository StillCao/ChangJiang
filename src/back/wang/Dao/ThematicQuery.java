package back.wang.Dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

import back.wang.Domain.News;
import back.wang.Domain.ThematicData;
import utils.JDBCUtils;

/**
 * @author wwx-sys
 * @time 2020-10-26-18:08
 * @description 专题数据表查询
 */
public class ThematicQuery {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    NamedParameterJdbcTemplate npjTemplate = new NamedParameterJdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 插入一条data
     *
     * @param data
     * @return
     */
    public boolean insertData(ThematicData data) {
        String sql = "insert into thematic_data (id,thematicTitle,publishTime,thematicContent,thematicLink) Values(null,:thematicTitle,:publishTime,:thematicContent,:thematicLink)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        npjTemplate.update(sql, new BeanPropertySqlParameterSource(data), keyHolder);
        return keyHolder.getKey().intValue() > 0;
    }

    /**
     * @return 查询thematic_data表条数
     */
    public int queryDataCount() {
        String sql = "select count(*) from thematic_data";
        return template.queryForObject(sql, Integer.class);
    }

    /**
     * @param startPos 起始位置
     * @param count    查询条数
     * @return ThematicData集合
     */
    public List<ThematicData> queryAllDataByPage(int startPos, int count) {
        String sql = "select * from thematic_data limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(ThematicData.class), startPos, count);
    }


    /**
     * @return 查询thematic_data表条数
     */
    public int queryDataByKeyCount(String key, String value) {
        value = "%" + value + "%";
        String sql = "select count(*) from thematic_data where " + key + " like ?";
        return template.queryForObject(sql, Integer.class, value);
    }

    /**
     * key 模糊查询
     *
     * @param key thematicTitle aaaaaaa
     */
    public List<ThematicData> queryDataByKeyLikeByPage(String key, String value, int startPos, int count) {
        value = "%" + value + "%";
        String sql = "select * from thematic_data where " + key + " like '" + value + "'  Order By publishTime Desc limit ?,? ";
        return template.query(sql, new BeanPropertyRowMapper<>(ThematicData.class), startPos, count);
    }

    /**
     * 根据ID查询对应的专题数据
     *
     * @param id id
     */
    public ThematicData queryDataById(int id) {
        String sql = "select * from thematic_data where id = ?";
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(ThematicData.class), id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据news 的id，更新thematic_data
     *
     * @param data id大于0的ThematicData对象
     * @return 是否修改成功
     */
    public boolean updateDataById(ThematicData data) {
        String sql = "UPDATE thematic_data SET thematicTitle =  ?, publishTime = ?, thematicContent = ?, thematicLink = ? WHERE id = ?";
        return template.update(sql, data.getThematicTitle(), data.getPublishTime(), data.getThematicContent(), data.getThematicLink(), data.getId()) > 0;
    }

    /**
     * 根据id 删除thematic_data
     *
     * @param id thematic_data id
     * @return 是否删除成功
     */
    public boolean deleteData(int id) {
        String sql = "delete from thematic_data where id = ?";
        return template.update(sql, id) > 0;
    }
}
