package front.data_order.dao;

import back.wang.Domain.News;
import front.data_order.domain.OrderChart;
import front.data_order.domain.UserConfi;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;


/**
 * Fun: 订单管理：待提交订单，提交订单，审核通过订单
 * Created by CW on 2020/6/27 5:06 下午
 */
public class OrderManage {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 1.将待提交订单入库，orderStatus = 0
     * @param u_id
     * @param data_id
     * @param orderStatus
     * @return
     */
    public int insertWaitingOrder(int u_id,int data_id,int orderStatus){
        //此处sql为插入记录，而不是修改
        //String sql = "update order_confirm set orderStatus = ? where userId = ? and dataId = ? ;";
        String sql = "insert into order_confirm  (userId,dataId,orderStatus) values(?,?,?);";
        int rows = template.update(sql,u_id,data_id,orderStatus);
        return rows;
    }

    /**
     * 2.获用户的所有订单记录
     * @param u_id
     * @return
     */
    public List<OrderChart> queryWaitingOrder(int u_id){
        String sql = "SELECT  o.userId,o.dataId,o.orderStatus,b.name,b.da_time,b.sploc,b.da_size from " +
                "order_confirm AS o LEFT JOIN basic_info AS b on o.dataId = b.id where o.userId = ?;";
        List<OrderChart> orderCharts = template.query(sql, new BeanPropertyRowMapper<>(OrderChart.class), u_id);
        return orderCharts;
    }

    /**
     * 3.获取用户订单方法，重载：特定订单状态
     * @param u_id
     * @param orderStatus
     * @return
     */
    public List<OrderChart> queryWaitingOrder(int u_id,int orderStatus){
        String sql = "SELECT  o.orderCode,o.userId,o.dataId,o.orderStatus,b.name,b.da_time,b.sploc,b.da_size from " +
                "order_confirm AS o LEFT JOIN basic_info AS b on o.dataId = b.id where o.userId = ? and orderStatus = ?;";
        List<OrderChart> orderCharts = template.query(sql, new BeanPropertyRowMapper<>(OrderChart.class), u_id,orderStatus);
        return orderCharts;
    }

    /**
     * key 模糊查询
     *
     * @param key orderConfirm orderCode
     * @return 对应account 的 admin
     */
    public List<OrderChart> queryWaitingOrderLikeByKey(String key, String value, int orderStatus) {
        value = "%" + value + "%";
        String sql ="SELECT  o.orderCode,o.userId,o.dataId,o.orderStatus,b.name,b.da_time,b.sploc,b.da_size from " +
                "order_confirm AS o LEFT JOIN basic_info AS b on o.dataId = b.id where o." + key + " like '" + value +"' and o.orderStatus = ?;";
        return template.query(sql, new BeanPropertyRowMapper<>(OrderChart.class),orderStatus);
    }

    /**
     * 3.2管理员-获取所有用户的待审核订单
     * @param orderStatus
     * @return
     */
    public List<OrderChart> checkAllOrder(int orderStatus){
        String sql = "SELECT  o.orderCode,o.userId,o.dataId,o.orderStatus,b.name,b.da_time,b.sploc,b.da_size from " +
                "order_confirm AS o LEFT JOIN basic_info AS b on o.dataId = b.id where  orderStatus = ?;";
        List<OrderChart> orderCharts = template.query(sql, new BeanPropertyRowMapper<>(OrderChart.class),orderStatus);
        return orderCharts;
    }

    /**
     * 4.修改订单状态
     * @param u_id
     * @param data_id
     * @param status
     * @return
     */
    public int updateShopcar(int u_id,int data_id,int status){
        String sql = "update order_confirm set orderStatus = ? where userId = ? and dataId =?;";
        int rows = template.update(sql, status,u_id, data_id);
        return rows;
    }

    /**
     * 5.判断同一个用户的某条待提交订单是否重复。
     * 不能重复下单。
     * @param u_id
     * @param data_id
     * @return
     */
    public int judgeWaitingOrder(int u_id,int data_id){
        String sql = "SELECT count(*) from order_confirm where userId =? and dataId =? ;";
        int rows = template.queryForObject(sql,Integer.class,u_id, data_id);
        return rows;
    }

    /**
     * 6.查询当前用户的信息
     */
    public UserConfi queryUser(int id){
        String sql = "select realName,workUnit,email,phone from user where id = ? ;";
        UserConfi uc = template.queryForObject(sql, UserConfi.class, id);
        return uc;
    }

    /**
     * 7.查询订单状态
     * @param u_id
     * @param data_id
     * @return
     */
    public int queryOrderStatus(int u_id, int data_id) {
        String sql = "select orderStatus from order_confirm where userId = ? and dataId = ? ;";
        int status = template.queryForObject(sql, Integer.class, u_id, data_id);
        return status;
    }
}
