package back.wang.Domain;

import java.util.Date;

/**
 * @author wwx-sys
 * @time 2020-10-27-19:09
 * @description 特色数据
 */
public class SpecialData {
    private int id;
    private String specialTitle;
    private Date publishTime;
    private String specialContent;
    private String specialLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecialTitle() {
        return specialTitle;
    }

    public void setSpecialTitle(String specialTitle) {
        this.specialTitle = specialTitle;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getSpecialContent() {
        return specialContent;
    }

    public void setSpecialContent(String specialContent) {
        this.specialContent = specialContent;
    }

    public String getSpecialLink() {
        return specialLink;
    }

    public void setSpecialLink(String specialLink) {
        this.specialLink = specialLink;
    }

    @Override
    public String toString() {
        return "SpecialData{" +
                "id=" + id +
                ", specialTitle='" + specialTitle + '\'' +
                ", publishTime=" + publishTime +
                ", specialContent='" + specialContent + '\'' +
                ", specialLink='" + specialLink + '\'' +
                '}';
    }
}
