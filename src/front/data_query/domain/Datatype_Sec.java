package front.data_query.domain;

public class Datatype_Sec {

    private Integer id;
    private String t2_code;
    private String t2_name;
    private Integer t1_id;

    public Datatype_Sec(Integer id, String t2_code, String t2_name, Integer t1_id) {
        this.id = id;
        this.t2_code = t2_code;
        this.t2_name = t2_name;
        this.t1_id = t1_id;
    }

    public Datatype_Sec() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getT1_id() {
        return t1_id;
    }

    public void setT1_id(Integer t1_id) {
        this.t1_id = t1_id;
    }
}
