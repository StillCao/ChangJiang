package back.wang.Domain;

import java.util.Date;

/**
 * @author wwx-sys
 * @time 2020-10-29-8:53
 * @description 关于平台bean
 */
public class AboutPlatform {
    private int id;
    private String title;
    private Date publishTime;
    private String htmlContent;

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

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    @Override
    public String toString() {
        return "AboutPlatform{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publishTime=" + publishTime +
                ", htmlContent='" + htmlContent + '\'' +
                '}';
    }
}
