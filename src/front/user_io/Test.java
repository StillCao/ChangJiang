package front.user_io;

import utils.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Fun:
 * Created by CW on 2020/6/3 6:53 下午
 */
public class Test {
    @org.junit.Test
    public void test1(){
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "select userName from user where u_id = 1;";
        String name = template.queryForObject(sql, String.class);
        System.out.println(name);
    }
}
