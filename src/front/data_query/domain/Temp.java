package front.data_query.domain;

public class Temp {

     /*用来封装临时表的相关字段：
        num(每条属性值的数据总数)
        at_val_id(该数据属性值id)
        at_key_id(该数据属性关键字id)
        v_name(该数据属性值名称)
        k_name(该数据属性关键字名称)
     */

    private Integer num;
    private Integer at_val_id;
    private Integer at_key_id;
    private String v_name;
    private String k_name;

    public Temp() {
    }

    public Temp(Integer num, Integer at_val_id, Integer at_key_id, String v_name, String k_name) {
        this.num = num;
        this.at_val_id = at_val_id;
        this.at_key_id = at_key_id;
        this.v_name = v_name;
        this.k_name = k_name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAt_val_id() {
        return at_val_id;
    }

    public void setAt_val_id(Integer at_val_id) {
        this.at_val_id = at_val_id;
    }

    public Integer getAt_key_id() {
        return at_key_id;
    }

    public void setAt_key_id(Integer at_key_id) {
        this.at_key_id = at_key_id;
    }

    public String getV_name() {
        return v_name;
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public String getK_name() {
        return k_name;
    }

    public void setK_name(String k_name) {
        this.k_name = k_name;
    }

}
