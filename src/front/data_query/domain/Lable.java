package front.data_query.domain;

public class Lable {

    private String value;
    private Integer counts;

    public Lable() {
    }

    public Lable(String value, int counts) {
        this.value = value;
        this.counts = counts;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int count) {
        this.counts = count;
    }
}
