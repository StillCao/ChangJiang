package back.wang.Domain;

import java.util.Date;

/**
 * 描述:
 * 新闻Bean
 *
 * @author black-leaves
 * @createTime 2020-06-29  20:14
 */

public class News {
    private int id;
    private String title;
    private Date date;
    private String source;
    private int type;
    private String localaddr;
    private String news_cont;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLocaladdr() {
        return localaddr;
    }

    public void setLocaladdr(String localaddr) {
        this.localaddr = localaddr;
    }

    public String getNews_cont() {
        return news_cont;
    }

    public void setNews_cont(String news_cont) {
        this.news_cont = news_cont;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", source='" + source + '\'' +
                ", type=" + type +
                ", localaddr='" + localaddr + '\'' +
                ", news_cont='" + news_cont + '\'' +
                '}';
    }
}
