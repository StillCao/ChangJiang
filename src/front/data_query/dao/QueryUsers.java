package front.data_query.dao;

import front.data_query.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;
import utils.KeyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryUsers {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 返回所有用户信息
     * @return
     */
    public Map q_AllUsersInfo(int currentpage, int pagesize){

        //创建存放Map类型数据的集合，用于封装最终结果
        List<Map> list = new ArrayList<>();

        //1. 创建sql语句，查询所有人员信息，并根据id升序排列
        String sql = "SELECT * FROM user ORDER BY u_id";

        //2. 封装查询结果
        List<User> u_list = template.query(sql, new BeanPropertyRowMapper<>(User.class));


        Map map = new HashMap();
        map.put("currentCount",pagesize);
        map.put("currentPage",currentpage);

        //3. 封装当前页码的数据
        int index = (currentpage - 1) * pagesize;//这是索引变量，指向该页码下的第一条数据
        int count = 0;//计算存储该页码下数据的集合里已经存下的数据条数
        List<User> p_list = new ArrayList<>();//存放当前页码下的数据总数
        for (int i = index; i < u_list.size(); i++) {
            if (count < pagesize){
                //小于计数器，存入集合
                p_list.add(u_list.get(i));
                count++;//存入一条数据
            } else {
                break;//user表到达末尾或者当前页码下的数据存入完毕结束
            }
        }
        map.put("list",p_list);
        //再map集合中存入总条数
        map.put("totalCount",u_list.size());
        //4. 计算总页数
        int totalpage = u_list.size() / pagesize + 1;
        map.put("totalPage",totalpage);
        return map;
    }

    /**
     * 根据用户名称查询
     * @param username
     * @return
     */
    public Map getUserbyuserName(String username){
        //1. 创建sql语句，查询相关结果
        String sql = "SELECT * FROM user WHERE userName = ?";

        //2. 封装结果
        List<User> l = template.query(sql, new BeanPropertyRowMapper<>(User.class), username);

        //3. 用map集合封装结果
        Map map = new HashMap();
        map.put("userName",username);
        map.put("list",l);

        return map;
    }

    /**
     * 点击编辑时根据id获取用户信息
     * @param id
     * @return
     */
    public Map getUserById(int id){

        //1. 创建sql语句，查询结果
        String sql = "SELECT userName,email,phone,workUnit FROM user WHERE u_id = ?";

        //2. 执行sql语句
        List<User> list = template.query(sql, new BeanPropertyRowMapper<>(User.class), id);

        list.forEach( u ->{
            u.setPhone(KeyUtils.encryptPhone(u.getPhone()));
        });

        //3. 用Map集合封装结果
        Map map = new HashMap();
        map.put("id",id);
        map.put("list",list);
        return map;
    }

    /**
     * 修改用户信息
     * @param id
     * @param account
     * @param password
     * @param name
     * @param phone
     * @param email
     * @param unit
     * @return
     */
    public boolean updateUser(int id, String account,String password,String name,String phone,String email,String unit){

        //1. 创建sql语句，完成相关操作
        String sql = "UPDATE user SET userName = ?,password = ?,realName = ?,email = ?,phone = ?,workUnit = ? WHERE u_id = ?" ;

        //2. 执行sql语句
        int flag = template.update(sql,account, password,name, email,phone,unit,id);
        System.out.println("flag="+flag);

        boolean bool = false;
        if(flag != 0){
            bool = true;
        }
        return bool;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    public boolean deleteUser(int id){

        //先删除与user表关联的order_confirm表和data_shopcar表的相关数据，才能删除
        String sql1 = "DELETE FROM order_confirm WHERE userId = ?";
        template.update(sql1,id);
        String sql2 = "DELETE FROM data_shopcar WHERE  u_id = ?";
        template.update(sql2,id);

        //1.创建sql语句，完成相关操作
        String sql = "DELETE FROM user WHERE u_id = ?";

        //2. 执行相关操作
        int flag = template.update(sql,id);
        System.out.println("flag="+flag);

        boolean bool = false;
        if (flag != 0){
            bool = true;
        }
        return bool;
    }
}
