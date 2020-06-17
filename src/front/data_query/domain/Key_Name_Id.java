package front.data_query.domain;

public class Key_Name_Id {

    /*
   封装属性关键字的id和名称
    */
    private Integer k_id;
    private String k_name;

    public Key_Name_Id() {
    }

    public Key_Name_Id(Integer k_id, String k_name) {
        this.k_id = k_id;
        this.k_name = k_name;
    }

    public Integer getK_id() {
        return k_id;
    }

    public void setK_id(Integer k_id) {
        this.k_id = k_id;
    }

    public String getK_name() {
        return k_name;
    }

    public void setK_name(String k_name) {
        this.k_name = k_name;
    }
}
