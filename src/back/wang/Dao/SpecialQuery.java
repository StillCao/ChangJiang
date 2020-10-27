package back.wang.Dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

import back.wang.Domain.SpecialData;
import utils.JDBCUtils;

/**
 * @author wwx-sys
 * @time 2020-10-27-19:12
 * @description 特色数据查询
 */
public class SpecialQuery {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    NamedParameterJdbcTemplate npjTemplate = new NamedParameterJdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 插入一条data
     *
     * @param data
     * @return
     */
    public boolean insertData(SpecialData data) {
        String sql = "insert into special_data (id,specialTitle,publishTime,specialContent,specialLink) Values(null,:specialTitle,:publishTime,:specialContent,:specialLink)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        npjTemplate.update(sql, new BeanPropertySqlParameterSource(data), keyHolder);
        return keyHolder.getKey().intValue() > 0;
    }

    /**
     * @return 查询special_data表条数
     */
    public int queryDataCount() {
        String sql = "select count(*) from special_data";
        return template.queryForObject(sql, Integer.class);
    }

    /**
     * @param startPos 起始位置
     * @param count    查询条数
     * @return SpecialData
     */
    public List<SpecialData> queryAllDataByPage(int startPos, int count) {
        String sql = "select * from special_data limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(SpecialData.class), startPos, count);
    }


    /**
     * @return 查询special_data表条数
     */
    public int queryDataByKeyCount(String key, String value) {
        value = "%" + value + "%";
        String sql = "select count(*) from special_data where " + key + " like ?";
        return template.queryForObject(sql, Integer.class, value);
    }

    /**
     * key 模糊查询
     *
     * @param key thematicTitle aaaaaaa
     */
    public List<SpecialData> queryDataByKeyLikeByPage(String key, String value, int startPos, int count) {
        value = "%" + value + "%";
        String sql = "select * from special_data where " + key + " like '" + value + "'  Order By publishTime Desc limit ?,? ";
        return template.query(sql, new BeanPropertyRowMapper<>(SpecialData.class), startPos, count);
    }

    /**
     * 根据ID查询对应的专题数据
     *
     * @param id id
     */
    public SpecialData queryDataById(int id) {
        String sql = "select * from special_data where id = ?";
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(SpecialData.class), id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据news 的id，更新special_data
     *
     * @param data id大于0的SpecialData对象
     * @return 是否修改成功
     */
    public boolean updateDataById(SpecialData data) {
        String sql = "UPDATE special_data SET specialTitle =  ?, publishTime = ?, specialContent = ?, specialLink = ? WHERE id = ?";
        return template.update(sql, data.getSpecialTitle(), data.getPublishTime(), data.getSpecialContent(), data.getSpecialLink(), data.getId()) > 0;
    }

    /**
     * 根据id 删除special_data
     *
     * @param id special_data id
     * @return 是否删除成功
     */
    public boolean deleteData(int id) {
        String sql = "delete from special_data where id = ?";
        return template.update(sql, id) > 0;
    }
}
