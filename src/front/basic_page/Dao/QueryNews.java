package front.basic_page.Dao;

import front.basic_page.Domain.News_Total;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

/**
 * Fun:
 * Created by CW on 2020/6/15 3:07 下午
 */
public class QueryNews {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 1.新闻动态页面，新闻导航
     * @return 查询三大类新闻的最新的5条记录。
     */
    public List<News_Total> queryNewsForType(int type){
        String sql = "SELECT * FROM news WHERE TYPE = ? ORDER BY DATE DESC LIMIT 5;";
        return template.query(sql, new BeanPropertyRowMapper<>(News_Total.class),type);
    }
}
