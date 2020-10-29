package back.wang.Dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

import back.wang.Domain.AboutPlatform
        ;
import utils.JDBCUtils;

/**
 * @author wwx-sys
 * @time 2020-10-29-8:55
 * @description 关于平台数据查询
 */
public class AboutPlatformQuery {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    NamedParameterJdbcTemplate npjTemplate = new NamedParameterJdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 插入一条data
     *
     * @param data
     * @return
     */
    public boolean insertData(AboutPlatform data) {
        String sql = "insert into about_platform (id,title,publishTime,htmlContent) Values(null,:title,:publishTime,:htmlContent)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        npjTemplate.update(sql, new BeanPropertySqlParameterSource(data), keyHolder);
        return keyHolder.getKey().intValue() > 0;
    }

    /**
     * @return 查询about_platform表条数
     */
    public int queryDataCount() {
        String sql = "select count(*) from about_platform";
        return template.queryForObject(sql, Integer.class);
    }

    /**
     * @param startPos 起始位置
     * @param count    查询条数
     * @return AboutPlatform
     * 集合
     */
    public List<AboutPlatform
            > queryAllDataByPage(int startPos, int count) {
        String sql = "select * from about_platform limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(AboutPlatform
                .class), startPos, count);
    }


    /**
     * @return 查询about_platform表条数
     */
    public int queryDataByKeyCount(String key, String value) {
        value = "%" + value + "%";
        String sql = "select count(*) from about_platform where " + key + " like ?";
        return template.queryForObject(sql, Integer.class, value);
    }

    /**
     * key 模糊查询
     *
     * @param key title aaaaaaa
     */
    public List<AboutPlatform> queryDataByKeyLikeByPage(String key, String value, int startPos, int count) {
        value = "%" + value + "%";
        String sql = "select * from about_platform where " + key + " like '" + value + "'  Order By publishTime Desc limit ?,? ";
        return template.query(sql, new BeanPropertyRowMapper<>(AboutPlatform
                .class), startPos, count);
    }

    /**
     * 根据ID查询对应的专题数据
     *
     * @param id id
     */
    public AboutPlatform queryDataById(int id) {
        String sql = "select * from about_platform where id = ?";
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(AboutPlatform
                    .class), id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据news 的id，更新about_platform
     *
     * @param data id大于0的AboutPlatform
     *             对象
     * @return 是否修改成功
     */
    public boolean updateDataById(AboutPlatform data) {
        String sql = "UPDATE about_platform SET title =  ?, publishTime = ?, htmlContent = ? WHERE id = ?";
        return template.update(sql, data.getTitle(), data.getPublishTime(), data.getHtmlContent(), data.getId()) > 0;
    }

    /**
     * 根据id 删除about_platform
     *
     * @param id about_platform id
     * @return 是否删除成功
     */
    public boolean deleteData(int id) {
        String sql = "delete from about_platform where id = ?";
        return template.update(sql, id) > 0;
    }
}
