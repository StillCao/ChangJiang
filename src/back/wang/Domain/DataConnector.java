package back.wang.Domain;

/**
 * @author wwx-sys
 * @time 2021-03-06-10:05
 * @description 数据联系者
 */
public class DataConnector {
    private int id;
    private int basic_id;
    private String basic_name;
    private String name;
    private String phone;
    private String unit;
    private String mail_address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBasic_id() {
        return basic_id;
    }

    public void setBasic_id(int basic_id) {
        this.basic_id = basic_id;
    }

    public String getBasic_name() {
        return basic_name;
    }

    public void setBasic_name(String basic_name) {
        this.basic_name = basic_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMail_address() {
        return mail_address;
    }

    public void setMail_address(String mail_address) {
        this.mail_address = mail_address;
    }

    @Override
    public String toString() {
        return "DataConnector{" +
                "id=" + id +
                ", basic_id=" + basic_id +
                ", basic_name='" + basic_name + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", unit='" + unit + '\'' +
                ", mail_address='" + mail_address + '\'' +
                '}';
    }
}
