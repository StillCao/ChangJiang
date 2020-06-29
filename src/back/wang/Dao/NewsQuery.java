package back.wang.Dao;

import back.wang.Domain.Admin;
import front.basic_page.Domain.News;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

/**
 * 描述:
 * 新闻查询
 *
 * @author black-leaves
 * @createTime 2020-06-29  16:09
 */

public class NewsQuery {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * key 模糊查询
     *
     * @param key admin account
     * @return 对应account 的 admin
     */
    public List<News> queryNewsByKeyLikeByPage(String key, String value, int startPos, int count) {
        value = "%" + value + "%";
        String sql = "select * from news where " + key + " like '" + value + "' limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(News.class), startPos, count);
    }


}
