package back.wang.Domain;

/**
 * 描述:
 * 管理员表
 *
 * @author black-leaves
 * @createTime 2020-06-23  9:32
 */

public class Admin {
    private int id;
    private String account;
    private String password;
    private int type = 1;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                '}';
    }
}
