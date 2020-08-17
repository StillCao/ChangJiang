package back.wang.Domain;

/**
 * @author wwx-sys
 * @time 2020-08-17-14:29
 * @description 典型算法标签 实体类
 */
public class TypicalAlgoTags {
    private int id;
    private String name;
    private String algo;

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

    public String getAlgo() {
        return algo;
    }

    public void setAlgo(String algo) {
        this.algo = algo;
    }

    @Override
    public String toString() {
        return "TypicalAlgoTags{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", algo='" + algo + '\'' +
                '}';
    }
}
