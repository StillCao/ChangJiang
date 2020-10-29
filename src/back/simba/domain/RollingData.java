package back.simba.domain;

import java.util.Date;

/**
 * 用于创建轮播图(rolling表)数据对象的类
 * @author Simba
 * @date 2020/10/27 21:01
 */
public class RollingData {

    private int id;
    private String title;
    private String link;
    private String file;
    private Date time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
