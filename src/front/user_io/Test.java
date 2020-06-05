package front.user_io;

import front.user_io.dao.UserQuery;
import front.user_io.domain.User;
import utils.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @org.junit.Test
    public void test2(){
        int i = new UserQuery().userConfi("username", "admin");
        System.out.println(i);
    }

    @org.junit.Test
    public void test3(){
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = ft.format(dNow);
        User user = new User(1, "a", "a", "a", "a", "a", "a", "a", format, "");
        int i = new UserQuery().insertUser(user);
        System.out.println(i);
    }

}
