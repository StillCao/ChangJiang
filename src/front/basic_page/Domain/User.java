package front.basic_page.Domain;

import java.util.Date;

/**
 * Fun:
 * Created by CW on 2020/6/17 5:01 下午
 */
public class User {
    private int id;
    private String account;
    private String phone;
    private String email;
    private String workUnit;
    private String addr;

    public User() {
    }

    public User(int id, String account, String phone, String email, String workUnit, String addr) {
        this.id = id;
        this.account = account;
        this.phone = phone;
        this.email = email;
        this.workUnit = workUnit;
        this.addr = addr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", workUnit='" + workUnit + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}
