package back.simba.domain;

/**
 *
 * 创建该类的对象，获取basic_info表中id，name，image字段的数据返回给前端
 * @author Simba
 * @date 2020/10/27 16:22
 */
public class RecomendData {

    private int id;
    private String name;
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
