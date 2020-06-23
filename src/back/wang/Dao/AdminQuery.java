package back.wang.Dao;

import back.wang.Domain.Admin;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

/**
 * 描述:
 * 管理员表查询
 *
 * @author black-leaves
 * @createTime 2020-06-23  9:31
 */

public class AdminQuery {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * @return 查询admin 表所有记录
     */
    public List<Admin> queryAllAdmins() {
        String sql = "select * from admin";
        return template.query(sql, new BeanPropertyRowMapper<>(Admin.class));
    }

    /**
     * @return 查询admin表记录条数
     */
    public int queryAdminCount() {
        String sql = "select count(*) from admin";
        return template.queryForObject(sql, Integer.class);
    }

    /**
     * @param startPos 起始位置
     * @param count    每页的数量
     * @return Admin分页查询
     */
    public List<Admin> queryAdminsByPage(int startPos, int count) {
        String sql = "select * from admin limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(Admin.class), startPos, count);
    }

    /**
     * @param id admin id
     * @return 对应id的admin
     */
    public Admin queryAdminById(int id) {
        String sql = "select * from admin where id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Admin.class), id);
    }

    /**
     * @param account admin account
     * @return 对应account 的 admin
     */
    public Admin queryAdminByAccount(String account) {
        String sql = "select * from admin where account = ?";
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(Admin.class), account);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * @param admin admin.account 和 admin.password 都不空
     * @return 返回插入成功与否
     */
    public boolean addAdmin(Admin admin) {
        String sql = "insert into admin values(null,?,?,?)";
        try {
            template.update(sql, admin.getAccount(), admin.getPassword(), admin.getType());
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 修改admin信息
     * @param admin
     * @return 修改成功与否
     */
    public boolean updateAdmin(Admin admin) {
        String sql = "UPDATE admin SET account = ? ,password = ?, type = ? WHERE id = ?";
        int success = template.update(sql, admin.getAccount(), admin.getPassword(),admin.getType(),admin.getId());
        return success >= 1;
    }

    /**
     * 删除admin
     * @param id admin id
     * @return 删除成功与否
     */
    public boolean deleteAdminById(int id){
        int count = queryAdminCount();
        if (count == 1){ return false;} //若数据库里只有一个管理员，不能进行删除
        String sql = "delete from admin where id = ?";
        int success = template.update(sql,id);
        return success >= 1;
    }


}