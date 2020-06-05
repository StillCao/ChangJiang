package front.user_io.dao;

import front.user_io.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

/**
 * Fun:
 * Created by CW on 2020/6/4 10:05 上午
 */
public class UserQuery {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 1.用户的昵称、邮箱注册校验
     * @param key
     * @param value
     * @return
     */
    public int userConfi(String key,String value){
        String sql = "select count(*) from user where " + key + " = ? ;";
        int i = template.queryForObject(sql, Integer.class,value);
        return i;
    }


    /**
     * 2.用户注册信息。
     * @param user
     */
    public int insertUser(User user){
        String sql = "insert into user (userName,password,realname,phone,email,workUnit) VALUES (?,?,?,?,?,?);";
        int i = template.update(sql, user.getUserName(), user.getPassword(), user.getRealName(), user.getPhone(), user.getEmail(), user.getWorkUnit());
        return i;
    }

    /**
     * 3.用户登录判断
     * @param userName
     * @param password
     * @return 1，0
     */
    public int userLoginIn(String userName, String password) {
        String sql = "select count(*) from user where userName = ? and password = ? ;";
        int  i = template.queryForObject(sql, Integer.class, userName, password);
        return i;  //用户存在返回1，不存在返回0
    }

    /**
     * 4.用户登录成功后，返回用户的基本信息。
     * @return user
     */

    public List<User> queryUser(String userName) {
        String sql = "select u_id,userName,realName,phone,email,workUnit from user where userName = ?";
        List<User> user = template.query(sql, new BeanPropertyRowMapper<>(User.class), userName);
        return user;
    }
}
