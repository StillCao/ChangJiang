package front.data_query.domain;

public class TempLink {

    private Integer basi_info_id;
    private Integer at_val_id;
    private Integer at_key_id;
    private String v_name;
    private String k_name;

    public TempLink() {
    }

    public TempLink(Integer basi_info_id, Integer at_val_id, Integer at_key_id, String v_name, String k_name) {
        this.basi_info_id = basi_info_id;
        this.at_val_id = at_val_id;
        this.at_key_id = at_key_id;
        this.v_name = v_name;
        this.k_name = k_name;
    }

    public Integer getBasi_info_id() {
        return basi_info_id;
    }

    public void setBasi_info_id(Integer basi_info_id) {
        this.basi_info_id = basi_info_id;
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
