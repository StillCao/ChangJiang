package back.wang.Dao;

import back.wang.Domain.Admin;
import back.wang.Domain.BasicInfoAll;
import back.wang.Domain.Downaim;
import back.wang.Domain.Order_confirm;
import front.user_io.domain.User;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import utils.JDBCUtils;

import java.util.List;

/**
 * 描述:
 * 下载目的表插入
 *
 * @author black-leaves
 * @createTime 2020-06-29  15:53
 */

public class DownAimInsert {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    NamedParameterJdbcTemplate npjTemplate = new NamedParameterJdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据id查询下载目的表
     *
     * @param id
     * @return Downaim 对象全字段
     */
    public Downaim QueryDownAimById(int id) {
        String sql = "Select * from downaim WHERE id = ?";
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(Downaim.class), id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 插入一条数据到 DownAim表
     *
     * @param downaim
     * @return
     */
    public int InsertDownAim(Downaim downaim) {
        String sql = "Insert into downaim (id,location,north,south,east,west,timeRange,useType,proofUrl,projLevel,projName,projCode,projAdmin,projWorkUnit,projEndTime,title,schoolName,teacherName,teacherPhone,endTime,note) Values (null,:location,:north,:south,:east,:west,:timeRange,:useType,:proofUrl,:projLevel,:projName,:projCode,:projAdmin,:projWorkUnit,:projEndTime,:title,:schoolName,:teacherName,:teacherPhone,:endTime,:note)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        npjTemplate.update(sql, new BeanPropertySqlParameterSource(downaim), keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * @return 根据条件模糊查询order_confirm表条数
     */
    public int QueryOrderCountByKeyNValuesLike(String key, String value, int orderStatus) {
        value = "%" + value + "%";
        String sql = "Select count(*) from order_confirm where " + key + " like '" + value + "' and orderStatus = ?;";
        return template.queryForObject(sql, Integer.class, orderStatus);
    }

    /**
     * @return 根据订单状态查询order_confirm表条数
     */
    public int QueryOrderCountByStatus(int orderStatus) {
        String sql = "Select count(*) from order_confirm where orderStatus = ?;";
        return template.queryForObject(sql, Integer.class, orderStatus);
    }

    /**
     * @return 根据条件模糊分页查询order_confirm表
     */
    public List<Order_confirm> QueryOrderByKeyNValueNPage(int orderStatus, String key, String value, int startPos, int count) {
        value = "%" + value + "%";
        String sql = "SELECT * FROM order_confirm WHERE " + key + " like '" + value + "' and " + "orderStatus = ? limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(Order_confirm.class), orderStatus, startPos, count);
    }

    /**
     * 更新 order_confirm 表
     *
     * @param order_confirm order_confirm对象
     * @return 是否更新成功
     */
    public boolean UpDateOrderConfirm(Order_confirm order_confirm) {
        String sql = "Update order_confirm set down_aim = ? , orderCode = ?,orderStatus = 1 where id = ? ";
        return template.update(sql, order_confirm.getDown_aim(), order_confirm.getOrderCode(), order_confirm.getId()) >= 1;
    }

    /**
     * 根据 userID 和 dataID 查询 order_confirm 记录,查询对应记录的ID
     *
     * @param userId 用户ID
     * @param dataId 数据ID
     * @return 对应的记录id
     */
    public int QueryOrderConfirmByIds(int userId, int dataId) {
        String sql = "Select id from order_confirm where userId = ? and dataId = ?";
        return template.queryForObject(sql, Integer.class, userId, dataId);
    }

    /**
     * 根据ID查询对应记录
     *
     * @param id order_confirm id
     * @return 对应的 Order_confirm 记录
     */
    public Order_confirm QueryOrderConfirmAllById(int id) {
        String sql = "Select * from order_confirm where id = ?";

        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(Order_confirm.class), id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 订单状态分类查询
     *
     * @param status
     * @return
     */
    public List<Order_confirm> queryOrderByStatus(int status) {
        String sql = "SELECT * FROM order_confirm WHERE orderStatus = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Order_confirm.class), status);
    }

    /**
     * 订单状态分类分页查询
     *
     * @param status
     * @return
     */
    public List<Order_confirm> queryOrderByStatusNPage(int status, int startPos, int count) {
        String sql = "SELECT * FROM order_confirm WHERE orderStatus = ? limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(Order_confirm.class), status, startPos, count);
    }

    /**
     * 根据用户ID查询
     *
     * @param u_id 用户ID
     * @return 全字段
     */
    public User queryUserById(int u_id) {
        String sql = "SELECT u_id,userName,realName,email,phone,workUnit,addr from user WHERE u_id = ?";

        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), u_id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据Id 查询部分字段
     *
     * @return
     */
    public BasicInfoAll queryBasicInfoById(int id) {
        String sql = "SELECT id,Name,da_size,datm_range from basic_info where id =?";
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(BasicInfoAll.class), id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询对应userId的记录条数
     *
     * @param userId
     * @return
     */
    public int queryCountByUidNStatus(int userId, int status) {
        String sql = "Select count(*) from order_confirm where userId = ? and orderStatus = ?";
        try {
            return template.queryForObject(sql, Integer.class, userId, status);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 查询对应userId的记录
     *
     * @param userId
     * @return
     */
    public List<Order_confirm> queryOrderByUidNStatus(int userId, int status, int startPos, int count) {
        String sql = "Select * from order_confirm where userId = ? and orderStatus = ? limit ?,?";
        try {
            return template.query(sql, new BeanPropertyRowMapper<>(Order_confirm.class), userId, status, startPos, count);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询对应dataId的记录条数
     *
     * @param dataId
     * @return
     */
    public int queryCountByDataIdNStatus(int dataId, int status) {
        String sql = "Select count(*) from order_confirm where dataId = ? and orderStatus = ?";
        try {
            return template.queryForObject(sql, Integer.class, dataId, status);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 查询对应dataId的记录
     *
     * @param dataId
     * @return
     */
    public List<Order_confirm> queryOrderByDataIdNStatus(int dataId, int status, int startPos, int count) {
        String sql = "Select * from order_confirm where dataId = ? and orderStatus = ? limit ?,?";
        try {
            return template.query(sql, new BeanPropertyRowMapper<>(Order_confirm.class), dataId, status, startPos, count);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


}
