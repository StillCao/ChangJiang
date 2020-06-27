package front.data_order.dao;

import front.data_order.domain.DataId;
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
     * 2.根据数据id,查询数据的部分信息。
     * @param id
     * @return
     */
    public Shopcar queryUserShopcar(int id){
        String sql = "select id,name,sploc,da_time,da_size from basic_info where id = ?";
        Shopcar shopcar = template.queryForObject(sql, Shopcar.class, id);
        return shopcar;
    }

    /**
     * 3.查询用户购物车
     * @param u_id
     * @return
     */
    public List<DataId> queryShopcarId(int u_id){
        String sql = "select data_id from data_shopcar where u_id = ?";
        List<DataId> dataList= template.query(sql, new BeanPropertyRowMapper<>(DataId.class), u_id);
        return dataList;
    }

}
