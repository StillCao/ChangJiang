package front.data_query.domain;

public class Datatype_Fir {

    private Integer id;
    private String t1_code;
    private String t1_name;

    public Datatype_Fir() {
    }

    public Datatype_Fir(Integer id, String t1_code, String t1_name) {
        this.id = id;
        this.t1_code = t1_code;
        this.t1_name = t1_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getT1_code() {
        return t1_code;
    }

    public void setT1_code(String t1_code) {
        this.t1_code = t1_code;
    }

    public String getT1_name() {
        return t1_name;
    }

    public void setT1_name(String t1_name) {
        this.t1_name = t1_name;
    }
}
