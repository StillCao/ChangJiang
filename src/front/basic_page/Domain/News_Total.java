package front.basic_page.Domain;

import java.util.Date;

/**
 * Fun:
 * Created by CW on 2020/6/15 3:00 下午
 */
public class News_Total {
    private int id;
    private String title;
    private Date DAte;
    private String source;
    private String news_cont;
    private String localaddr;

    public News_Total(int id, String title, Date DAte, String source, String news_cont, String localaddr) {
        this.id = id;
        this.title = title;
        this.DAte = DAte;
        this.source = source;
        this.news_cont = news_cont;
        this.localaddr = localaddr;
    }

    public News_Total() {
    }

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

    public Date getDAte() {
        return DAte;
    }

    public void setDAte(Date DAte) {
        this.DAte = DAte;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNews_cont() {
        return news_cont;
    }

    public void setNews_cont(String news_cont) {
        this.news_cont = news_cont;
    }

    public String getLocaladdr() {
        return localaddr;
    }

    public void setLocaladdr(String localaddr) {
        this.localaddr = localaddr;
    }

    @Override
    public String toString() {
        return "News_Total{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", DAte=" + DAte +
                ", source='" + source + '\'' +
                ", news_cont='" + news_cont + '\'' +
                ", localaddr='" + localaddr + '\'' +
                '}';
    }
}
