package front.data_order.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

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
    public int insertShopcar(int u_id,int data_id,int status ,long create_time){

        String sql = "insert into data_shopcar (u_id,data_id,status,create_time) VALUES (?,?,?,?);";
        int i = template.update(sql,u_id,data_id,status,create_time);
        return i;
    }

}
