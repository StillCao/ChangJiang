package back.wang.Domain;

import java.util.Date;

/**
 * @author wwx-sys
 * @time 2020-08-17-14:28
 * @description 典型算法实体类
 */
public class TypicalAlgo {
    private int id;
    private String name;
    private String tags;
    private String digest;
    private String description;
    private String doc_url;
    private String up_user;
    private String up_unit;
    private Date up_date;

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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoc_url() {
        return doc_url;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }

    public String getUp_user() {
        return up_user;
    }

    public void setUp_user(String up_user) {
        this.up_user = up_user;
    }

    public String getUp_unit() {
        return up_unit;
    }

    public void setUp_unit(String up_unit) {
        this.up_unit = up_unit;
    }

    public Date getUp_date() {
        return up_date;
    }

    public void setUp_date(Date up_date) {
        this.up_date = up_date;
    }

    @Override
    public String toString() {
        return "TypicalAlgo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tags='" + tags + '\'' +
                ", digest='" + digest + '\'' +
                ", description='" + description + '\'' +
                ", doc_url='" + doc_url + '\'' +
                ", up_user='" + up_user + '\'' +
                ", up_unit='" + up_unit + '\'' +
                ", up_date=" + up_date +
                '}';
    }
}
