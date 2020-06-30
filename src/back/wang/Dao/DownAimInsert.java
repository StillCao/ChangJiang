package back.wang.Dao;

import back.wang.Domain.Admin;
import back.wang.Domain.Downaim;
import back.wang.Domain.Order_confirm;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import utils.JDBCUtils;

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
     * 插入一条数据到 DownAim表
     *
     * @param downaim
     * @return
     */
    public int InsertDownAim(Downaim downaim) {
        String sql = "Insert into downaim (id,location,north,south,east,west,timeRange,useType,proofUrl,projLevel,projName,projCode,projAdmin,projWorkUnit,projEndTime,title,schoolName,teacherName,teacherPhone,endTime) Values (null,:location,:north,:south,:east,:west,:timeRange,:useType,:proofUrl,:projLevel,:projName,:projCode,:projAdmin,:projWorkUnit,:projEndTime,:title,:schoolName,:teacherName,:teacherPhone,:endTime)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        npjTemplate.update(sql, new BeanPropertySqlParameterSource(downaim), keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     *  更新 order_confirm 表
     * @param order_confirm order_confirm对象
     * @return 是否更新成功
     */
    public boolean UpDateOrderConfirm(Order_confirm order_confirm) {
        String sql = "Update order_confirm set down_aim = ? , orderStatus = 1 where id = ? ";
        return template.update(sql, order_confirm.getDown_aim(), order_confirm.getId()) >= 1;
    }

    /**
     * 根据 userID 和 dataID 查询 order_confirm 记录,查询对应记录的ID
     * @param userId 用户ID
     * @param dataId 数据ID
     * @return 对应的记录id
     */
    public int QueryOrderConfirmByIds(int userId, int dataId) {
        String sql = "Select id from order_confirm where userId = ? and dataId = ?";
        return template.queryForObject(sql, Integer.class, userId, dataId);
    }
}
