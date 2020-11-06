package front.data_order.Test;

import org.junit.Test;

import front.data_order.dao.OrderManage;

/**
 * @author wwx-sys
 * @time 2020-11-06-16:56
 * @description 订单类测试
 */
public class OrderTest {
    @Test
    public void QueryWaitingOrderLikeByKey(){
        System.out.println(new OrderManage().queryWaitingOrderLikeByKey("orderCode","test",2));
    }
}
