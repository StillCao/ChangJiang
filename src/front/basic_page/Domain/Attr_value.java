package front.basic_page.Domain;

/**
 * 描述:
 * 二级专题词
 *
 * @author black-leaves
 * @createTime 2020-06-18  14:18
 */

public class Attr_value {
    private int v_id;
    private String v_name;
    private int v_id_k;

    public int getV_id() {
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }

    public String getV_name() {
        return v_name;
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public int getV_id_k() {
        return v_id_k;
    }

    public void setV_id_k(int v_id_k) {
        this.v_id_k = v_id_k;
    }

    @Override
    public String toString() {
        return "Attr_value{" +
                "v_id=" + v_id +
                ", v_name='" + v_name + '\'' +
                ", v_id_k=" + v_id_k +
                '}';
    }
}
