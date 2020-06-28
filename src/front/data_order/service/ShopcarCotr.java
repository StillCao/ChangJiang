package front.data_order.service;

import front.data_order.dao.InsertShopcar;

/**
 * Fun:购物车的逻辑控制层
 * Created by CW on 2020/6/28 3:32 下午
 */
public class ShopcarCotr {

    /**
     * 1.加入购物车
     * @param u_id
     * @param data_id
     * @param status
     * @param create_time
     * @return
     */
    public int insertShopcarCotr(int u_id,int data_id,int status ,String create_time){
        InsertShopcar is = new InsertShopcar();
        int count = is.queryShop(u_id, data_id);
        if (count ==1){
            //该记录存在，状态重置为1
            int i = is.updateShopcar(u_id, data_id, 1);
            return i;
        }else if (count == 0){
            //第一次收藏，则插入该记录的收藏状态
            int i = is.insertShopcar(u_id, data_id, status, create_time);
            return i;
        }else
            return 0;

    }

}
