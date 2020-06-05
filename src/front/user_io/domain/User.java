package front.user_io.domain;

import java.util.Date;

/**
 * Fun:
 * Created by CW on 2020/6/4 10:48 上午
 */
public class User {
    private int u_id;
    private String userName;
    private String password;
    private String realName;
    private String phone;
    private String email;
    private String workUnit;
    private String addr;
    private String creatTime;
    private String updateTime;

    public User(int u_id, String userName, String password, String realName, String phone, String email, String workUnit, String addr, String creatTime, String updateTime) {
        this.u_id = u_id;
        this.userName = userName;
        this.password = password;
        this.realName = realName;
        this.phone = phone;
        this.email = email;
        this.workUnit = workUnit;
        this.addr = addr;
        this.creatTime = creatTime;
        this.updateTime = updateTime;
    }

    public User() {
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "u_id='" + u_id + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", workUnit='" + workUnit + '\'' +
                ", addr='" + addr + '\'' +
                ", creatTime='" + creatTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
