package back.wang.Dao;

import back.wang.Domain.BasicInfoAll;
import back.wang.Domain.News;
import front.basic_page.Domain.BasicInfo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import utils.JDBCUtils;

import java.util.List;

/**
 * 描述:
 * 基础数据表 查询
 *
 * @author black-leaves
 * @createTime 2020-07-02  9:59
 */

public class BasicDataQuery {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * @return 查询 basic 表条数
     */
    public int queryDataCount() {
        String sql = "select count(*) from basic_info";
        return template.queryForObject(sql, Integer.class);
    }

    /**
     * 查询所有记录
     *
     * @return 分页查询
     */
    public List<BasicInfoAll> queryDataAllByPage(int startPos, int count) {
        String sql = "select * from basic_info limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(BasicInfoAll.class), startPos, count);
    }

    /**
     * @return 根据key 查询 对应的value 在 basic_info表中的条数
     */
    public int queryDataByKeyCount(String key, String value) {
        value = "%" + value + "%";
        String sql = "select count(*) from basic_info where " + key + " like ?";
        return template.queryForObject(sql, Integer.class, value);
    }

    /**
     * key 模糊查询
     *
     * @param key BasicInfoAll 表中的字段值
     * @return 对应的 BasicInfoAll
     */
    public List<BasicInfoAll> queryDataByKeyLikeByPage(String key, String value, int startPos, int count) {
        value = "%" + value + "%";
        String sql = "select * from basic_info where " + key + " like '" + value + "'  Order By up_time Desc limit ?,? ";
        return template.query(sql, new BeanPropertyRowMapper<>(BasicInfoAll.class), startPos, count);
    }

    /**
     * 根据数据id 删除关联表里 与该数据相关记录
     *
     * @param id 数据id
     * @return
     */
    public boolean deleteRelaChartByDataId(int id) {
        String sql = "delete from rela_chart where basi_info_id = ?";
        return template.update(sql, id) > 0;
    }

    /**
     * 根据数据Id查询数据信息
     *
     * @param id 数据Id
     * @return
     */
    public BasicInfoAll queryDataById(int id) {
        String sql = "select * from basic_info where id = ?";
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(BasicInfoAll.class), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * 根据数据id 删除数据记录
     *
     * @param id 数据id
     * @return 是否删除成功
     */
    public boolean deleteBasicData(int id) {
        String sql = "delete from basic_info where id =?";
        return template.update(sql, id) > 0;
    }

    /**
     * 根据数据id 删除订单记录
     *
     * @param id 数据id
     * @return 是否删除成功
     */
    public boolean deleteOrderByDataId(int id) {
        String sql = "delete from order_confirm where dataId = ?";
        return template.update(sql, id) > 0;
    }

    /**
     * 根据数据id 删除购物车记录
     *
     * @param data_id 数据id
     * @return 是否删除成功
     */
    public boolean deleteShopCarByDataId(int data_id) {
        String sql = "delete from data_shopcar where data_id = ?";
        return template.update(sql, data_id) > 0;
    }
}
