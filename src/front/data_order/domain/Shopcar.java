package front.data_order.domain;

/**
 * Fun:
 * Created by CW on 2020/6/27 6:18 下午
 */
public class Shopcar {
    private int id;
    private String name;
    private String sploc;
    private String da_time;
    private String da_size;
    private int status;

    public Shopcar(int id, String name, String sploc, String da_time, String da_size, int status) {
        this.id = id;
        this.name = name;
        this.sploc = sploc;
        this.da_time = da_time;
        this.da_size = da_size;
        this.status = status;
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

    public String getDa_time() {
        return da_time;
    }

    public void setDa_time(String da_time) {
        this.da_time = da_time;
    }

    public String getDa_size() {
        return da_size;
    }

    public void setDa_size(String da_size) {
        this.da_size = da_size;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
