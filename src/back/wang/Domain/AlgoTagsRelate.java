package back.wang.Domain;

/**
 * @author wwx-sys
 * @time 2020-08-21-17:01
 * @description 算法和标签关联表Bean
 */
public class AlgoTagsRelate {
    private int id;
    private int algo_id;
    private int tag_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlgo_id() {
        return algo_id;
    }

    public void setAlgo_id(int algo_id) {
        this.algo_id = algo_id;
    }

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    @Override
    public String toString() {
        return "AlgoTagsRelate{" +
                "id=" + id +
                ", algo_id=" + algo_id +
                ", tag_id=" + tag_id +
                '}';
    }
}
