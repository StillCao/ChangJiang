package back.wang.Domain;

import java.util.Date;

/**
 * @author wwx-sys
 * @time 2020-10-26-18:00
 * @description 专题数据
 */
public class ThematicData {
    private int id;
    private String thematicTitle;
    private Date publishTime;
    private String thematicContent;
    private String thematicLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThematicTitle() {
        return thematicTitle;
    }

    public void setThematicTitle(String thematicTitle) {
        this.thematicTitle = thematicTitle;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getThematicContent() {
        return thematicContent;
    }

    public void setThematicContent(String thematicContent) {
        this.thematicContent = thematicContent;
    }

    public String getThematicLink() {
        return thematicLink;
    }

    public void setThematicLink(String thematicLink) {
        this.thematicLink = thematicLink;
    }

    @Override
    public String toString() {
        return "ThematicData{" +
                "id=" + id +
                ", thematicTitle='" + thematicTitle + '\'' +
                ", publishTime=" + publishTime +
                ", thematicContent='" + thematicContent + '\'' +
                ", thematicLink='" + thematicLink + '\'' +
                '}';
    }
}
