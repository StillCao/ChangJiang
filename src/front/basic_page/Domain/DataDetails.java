package front.basic_page.Domain;

/**
 * Fun:
 * Created by CW on 2020/6/17 5:06 下午
 */
public class DataDetails {
    private BasicData basicData;
    private User user;

    public DataDetails(BasicData basicData, User user) {
        this.basicData = basicData;
        this.user = user;
    }

    public DataDetails() {
    }

    public BasicData getBasicData() {
        return basicData;
    }

    public void setBasicData(BasicData basicData) {
        this.basicData = basicData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "DataDetails{" +
                "basicData=" + basicData +
                ", user=" + user +
                '}';
    }
}
