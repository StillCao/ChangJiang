package front.basic_page.Domain;

import back.wang.Domain.DataConnector;

/**
 * Fun:
 * Created by CW on 2020/6/17 5:06 下午
 * Modified by wwx on 2021/03/06
 */
public class DataDetails {
    private BasicData basicData;

    public DataConnector getDataConnector() {
        return dataConnector;
    }

    public void setDataConnector(DataConnector dataConnector) {
        this.dataConnector = dataConnector;
    }

    //    private User user;
    private DataConnector dataConnector;

    public DataDetails(BasicData basicData, DataConnector dataConnector) {
        this.basicData = basicData;
        this.dataConnector = dataConnector;
    }

    public DataDetails() {
    }

    public BasicData getBasicData() {
        return basicData;
    }

    public void setBasicData(BasicData basicData) {
        this.basicData = basicData;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    @Override
    public String toString() {
        return "DataDetails{" +
                "basicData=" + basicData +
                ", dataConnector=" + dataConnector +
                '}';
    }
}
