package back.simba.domain;

/**
 * 描述：
 * 创建PlatformLink对象，获取pla_link表的数据
 *
 * @author Simba
 * @date 2020/10/26 16:18
 */
public class PlatformLink {

    private int id;
    private String name;
    private String url;
    private int type;
    private int status;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PlatformLink{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
