package front.data_query.domain;

import java.sql.Date;

public class User {

    private Integer u_id;
    private String userName;
    private String password;
    private String realName;
    private String email;
    private String phone;
    private String workUnit;
    private String addr;
    private Date createTime;
    private Date updateTime;

    public User() {
    }

    public User(Integer u_id, String userName, String password, String realName, String email, String phone, String workUnit, String addr, Date createTime, Date updateTime) {
        this.u_id = u_id;
        this.userName = userName;
        this.password = password;
        this.realName = realName;
        this.email = email;
        this.phone = phone;
        this.workUnit = workUnit;
        this.addr = addr;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
