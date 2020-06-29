package front.data_order.domain;

/**
 * Fun:
 * Created by CW on 2020/6/29 2:15 下午
 */
public class UserConfi {
    private String realName;
    private String email;
    private String workUnit;
    private String phone;

    public UserConfi(String realName, String email, String workUnit, String phone) {
        this.realName = realName;
        this.email = email;
        this.workUnit = workUnit;
        this.phone = phone;
    }

    public UserConfi() {
    }

    @Override
    public String toString() {
        return "UserConfi{" +
                "realName='" + realName + '\'' +
                ", email='" + email + '\'' +
                ", workUnit='" + workUnit + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
