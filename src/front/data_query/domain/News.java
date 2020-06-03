package front.data_query.domain;

import java.util.Date;

public class News {

    private Integer id;
    private String title;
    private Date date;
    private String source;
    private Integer TYPE;
    private String localaddr;

    public News() {
    }

    public News(Integer id, String title, Date date, String source, Integer TYPE, String localaddr) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.source = source;
        this.TYPE = TYPE;
        this.localaddr = localaddr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getTYPE() {
        return TYPE;
    }

    public void setTYPE(Integer TYPE) {
        this.TYPE = TYPE;
    }

    public String getLocaladdr() {
        return localaddr;
    }

    public void setLocaladdr(String localaddr) {
        this.localaddr = localaddr;
    }
}
