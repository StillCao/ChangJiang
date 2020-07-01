package front.data_order.domain;

/**
 * Fun:待提交订单的JavaBean对象
 * Created by CW on 2020/6/28 5:37 下午
 */
public class OrderChart {
    private int userId;
    private int dataId;
    private int orderStatus;
    private  String name;
    private String da_time;
    private String sploc;
    private String da_size;
    private String orderCode;

    public OrderChart(int userId, int dataId, int orderStatus, String name, String da_time, String sploc, String da_size, String orderCode) {
        this.userId = userId;
        this.dataId = dataId;
        this.orderStatus = orderStatus;
        this.name = name;
        this.da_time = da_time;
        this.sploc = sploc;
        this.da_size = da_size;
        this.orderCode = orderCode;
    }

    public OrderChart() {
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

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDa_time() {
        return da_time;
    }

    public void setDa_time(String da_time) {
        this.da_time = da_time;
    }

    public String getSploc() {
        return sploc;
    }

    public void setSploc(String sploc) {
        this.sploc = sploc;
    }

    public String getDa_size() {
        return da_size;
    }

    public void setDa_size(String da_size) {
        this.da_size = da_size;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Override
    public String toString() {
        return "OrderChart{" +
                "userId=" + userId +
                ", dataId=" + dataId +
                ", orderStatus=" + orderStatus +
                ", name='" + name + '\'' +
                ", da_time='" + da_time + '\'' +
                ", sploc='" + sploc + '\'' +
                ", da_size='" + da_size + '\'' +
                ", orderCode='" + orderCode + '\'' +
                '}';
    }
}
