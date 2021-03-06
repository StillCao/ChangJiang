package back.wang.Dao;

import back.wang.Domain.Admin;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import back.wang.Domain.DataConnector;
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
     * account 模糊查询
     *
     * @param account admin account
     * @return 对应account 的 admin
     */
    public List<Admin> queryAdminByAccountLikeByPage(String account, int startPos, int count) {
        account = "%" + account + "%";
        String sql = "select * from admin where account like '" + account + "' limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(Admin.class), startPos, count);
    }

    /**
     * account 精确查询
     *
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
        String sql = "insert into admin values(null,?,?,?,?,?,?,?)";
        try {
            template.update(sql, admin.getAccount(), admin.getPassword(), admin.getType(), admin.getEmail(), admin.getPhone(), admin.getWorkUnit(), admin.getAddr());
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 修改admin信息
     *
     * @param admin Admin对象
     * @return 修改成功与否
     */
    public boolean updateAdmin(Admin admin) {
        String sql = "UPDATE admin SET account = ? ,password = ?, type = ?, email = ? , phone = ?, workUnit =?, addr = ? WHERE id = ?";
        int success = template.update(sql, admin.getAccount(), admin.getPassword(), admin.getType(), admin.getEmail(), admin.getPhone(), admin.getWorkUnit(), admin.getAddr(), admin.getId());
        return success >= 1;
    }

    /**
     * 删除admin
     *
     * @param id admin id
     * @return 删除成功与否
     */
    public boolean deleteAdminById(int id) {
        int count = queryAdminCount();
        if (count == 1) {
            return false;
        } //若数据库里只有一个管理员，不能进行删除
        String sql = "delete from admin where id = ?";
        int success = template.update(sql, id);
        return success >= 1;
    }

    /**
     * admin登录
     *
     * @param admin
     * @return 登录成功返回admin 的 id , 登录失败返回 -1
     */
    public int loginAdmin(Admin admin) {
        String sql = "select id from admin where account = ? and password = ?";

        try {
            return template.queryForObject(sql, Integer.class, admin.getAccount(), admin.getPassword());
        } catch (DataAccessException e) {
            return -1;
        }

    }

    /**
     * 根据数据id查询对应数据联系者的相关信息
     *
     * @param basic_id basic_id
     */
    public List<DataConnector> queryDaConByBasicId(int basic_id) {
        String sql = "select * from da_connnector where basic_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(DataConnector.class),basic_id);
    }

    /**
     * 插入一条联系者数据到表中
     * @param dataConnector DataConnector对象
     * @return 是否插入成功
     */
    public boolean addDaCon(DataConnector dataConnector){
        String sql = "insert into da_connnector values(null,?,?,?,?,?,?)";
         try {
            template.update(sql, dataConnector.getBasic_id(), dataConnector.getBasic_name(), dataConnector.getName(), dataConnector.getPhone(), dataConnector.getUnit(), dataConnector.getMail_address());
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * name 精确查询
     *
     * @param name DataConnector name
     * @return 对应name 的 DataConnector
     */
    public DataConnector queryDaConByName(String name) {
        String sql = "select * from da_connnector where name = ?";
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(DataConnector.class), name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


}
