package front.basic_page.Domain;

/**
 * 描述:
 * 一级数据类型
 *
 * @author black-leaves
 * @createTime 2020-06-17  15:36
 */

public class TypeLevel1 {
    private int id;
    private String t1_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getT1_name() {
        return t1_name;
    }

    public void setT1_name(String t1_name) {
        this.t1_name = t1_name;
    }

    @Override
    public String toString() {
        return "TypeLevel1{" +
                "id=" + id +
                ", t1_name='" + t1_name + '\'' +
                '}';
    }
}
