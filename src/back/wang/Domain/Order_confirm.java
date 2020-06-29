package back.wang.Domain;

/**
 * 描述:
 * 订单确认表 bean
 *
 * @author black-leaves
 * @createTime 2020-06-29  17:15
 */

public class Order_confirm {
    private int id;
    private String orderCode;
    private int orderStatus;
    private int userId;
    private int dataId;
    private int down_aim;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public int getDown_aim() {
        return down_aim;
    }

    public void setDown_aim(int down_aim) {
        this.down_aim = down_aim;
    }

    @Override
    public String toString() {
        return "Order_confirm{" +
                "id=" + id +
                ", orderCode='" + orderCode + '\'' +
                ", orderStatus=" + orderStatus +
                ", userId=" + userId +
                ", dataId=" + dataId +
                ", down_aim=" + down_aim +
                '}';
    }
}
