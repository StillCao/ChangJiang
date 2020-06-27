package front.data_order.domain;

/**
 * Fun:
 * Created by CW on 2020/6/27 6:18 下午
 */
public class Shopcar {
    private int id;
    private String name;
    private String sploc;
    private String up_time;
    private String da_size;

    public Shopcar(int id, String name, String sploc, String up_time, String da_size) {
        this.id = id;
        this.name = name;
        this.sploc = sploc;
        this.up_time = up_time;
        this.da_size = da_size;
    }

    public Shopcar() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSploc() {
        return sploc;
    }

    public void setSploc(String sploc) {
        this.sploc = sploc;
    }

    public String getUp_time() {
        return up_time;
    }

    public void setUp_time(String up_time) {
        this.up_time = up_time;
    }

    public String getDa_size() {
        return da_size;
    }

    public void setDa_size(String da_size) {
        this.da_size = da_size;
    }
}
