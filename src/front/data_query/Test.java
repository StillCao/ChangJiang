package front.data_query;

import front.data_query.domain.News;

import utils.JDBCUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class Test {

    @org.junit.Test
    public void testdurid() throws JsonProcessingException {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "SELECT * FROM news";
        List<News> query = template.query(sql, new BeanPropertyRowMapper<>(News.class));
        String s = new ObjectMapper().writeValueAsString(query);
        System.out.println(s);
    }

}
