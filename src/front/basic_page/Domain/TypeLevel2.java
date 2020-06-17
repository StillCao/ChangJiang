package front.basic_page.Domain;

/**
 * 描述:
 * 二级数据类型
 *
 * @author black-leaves
 * @createTime 2020-06-17  15:36
 */

public class TypeLevel2 {
    private int id;
    private String t2_code;
    private String t2_name;
    private String t1_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getT2_code() {
        return t2_code;
    }

    public void setT2_code(String t2_code) {
        this.t2_code = t2_code;
    }

    public String getT2_name() {
        return t2_name;
    }

    public void setT2_name(String t2_name) {
        this.t2_name = t2_name;
    }

    public String getT1_id() {
        return t1_id;
    }

    public void setT1_id(String t1_id) {
        this.t1_id = t1_id;
    }

    @Override
    public String toString() {
        return "TypeLevel2{" +
                "id=" + id +
                ", t2_code='" + t2_code + '\'' +
                ", t2_name='" + t2_name + '\'' +
                ", t1_id='" + t1_id + '\'' +
                '}';
    }
}
