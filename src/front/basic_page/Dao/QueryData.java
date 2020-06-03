package front.basic_page.Dao;

import front.basic_page.Domain.News;
import utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class QueryData {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *
     * @return 查询最新8条新闻
     */
    public List<News> QueryLatest8News(){
        String sql = "SELECT * from news Order By Date Desc limit 8";
        return template.query(sql, new BeanPropertyRowMapper<>(News.class));
    }


}
