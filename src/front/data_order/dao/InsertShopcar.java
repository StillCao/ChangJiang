package front.data_order.dao;

import front.data_order.domain.Shopcar;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

/**
 * Fun:
 * Created by CW on 2020/6/27 5:06 下午
 */
public class InsertShopcar {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    /**
     * 1.用户购物车（收藏列表）
     * @param u_id
     * @param data_id
     * @param status
     * @param create_time
     * @return
     */
    public int insertShopcar(int u_id,int data_id,int status ,String create_time){

        String sql = "insert into data_shopcar (u_id,data_id,status,create_time) VALUES (?,?,?,?);";
        int i = template.update(sql,u_id,data_id,status,create_time);
        return i;
    }

    /**
     * 1.2判断该条购物车记录是否是第一次加入
     * @param u_id
     * @param data_id
     * @return
     */
    public int queryShop(int u_id,int data_id ){
        String sql = "SELECT count(*) from data_shopcar where u_id = ? and data_id =?;";
        int rows = template.queryForObject(sql, Integer.class, u_id, data_id);
        return rows;
    }

    /**
     * 2.用户购物车查询，左外连接
     * @param u_id
     * @return
     */
    public List<Shopcar> queryUserShopcar(int u_id){
        String sql = "select basic_info.id,basic_info.name,basic_info.sploc,basic_info.da_time,basic_info.da_size, data_shopcar.status from basic_info INNER JOIN " +
                "data_shopcar on data_shopcar.data_id = basic_info.id where u_id = ? ;";
        List<Shopcar> shopcar = template.query(sql, new BeanPropertyRowMapper<>(Shopcar.class), u_id);
        return shopcar;
    }

    /**
     * 3.1取消购物车的收藏
     * @param u_id
     * @param data_id
     * @return
     */
    public int deleteShopcar(int u_id,int data_id){
        String sql = "update data_shopcar set status = -1 where u_id = ? and data_id =?;";
        int rows = template.update(sql, u_id, data_id);
        return rows;
    }

    /**
     * 3.2修改收藏状态
     * @param u_id
     * @param data_id
     * @param status
     * @return
     */
    public int updateShopcar(int u_id,int data_id,int status){
        String sql = "update data_shopcar set status = ? where u_id = ? and data_id =?;";
        int rows = template.update(sql, status,u_id, data_id);
        return rows;
    }


}
