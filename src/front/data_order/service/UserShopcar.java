package front.data_order.service;

import front.data_order.dao.InsertShopcar;
import front.data_order.domain.DataId;
import front.data_order.domain.Shopcar;

import java.util.ArrayList;
import java.util.List;

/**
 * Fun:
 * Created by CW on 2020/6/27 6:47 下午
 */
public class UserShopcar {

    /**
     * 根据用户ID,查询收藏的数据ID,根据ID，去检索数据简介返回。
     * @param u_id
     * @return
     */
    public List<Shopcar> userShopcar(int u_id){
        List<Shopcar> sc = new ArrayList<>();
        //1.根据用户id,查询收藏列表
        InsertShopcar is = new InsertShopcar();
        List<DataId> shopcars = is.queryShopcarId(u_id);
        //2.根据数据id,查询收藏的数据详情
        for (DataId s : shopcars) {
            int data_id = s.getData_id();
            Shopcar sho = is.queryUserShopcar(data_id);
            sc.add(sho);
        }
        return sc;
    }
}
