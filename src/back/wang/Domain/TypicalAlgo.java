package back.wang.Domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wwx-sys
 * @time 2020-08-17-14:28
 * @description 典型算法实体类
 */
public class TypicalAlgo {
    private int id;
    private String name;
    public byte[] tags;
    private String digest;
    private String description;
    private String doc_url;
    private String up_user;
    private String up_unit;
    private Date up_date;
    private String tagNames;

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
        return byte2String(tags);
    }

    public void setTags(byte[] tags) {
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

    public String getTagNames() {
        return tagNames;
    }

    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }

    //从数据库的二进制的algo转化为对应的id    (1,2,3,4)这种
    public String byte2String(byte[] tags) {
        if (tags == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tags.length; i++) {
            if (tags[i] == '1') {
                sb.append(tags.length - i).append(",");
            }
        }
        if (sb.toString().endsWith(",")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    //从数据库的二进制的algo转化为对应的id
    public List<Integer> byte2ints() {
        if (tags == null) {
            return null;
        }
        List<Integer> idLists = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            if (tags[i] == '1') {
                idLists.add(tags.length - i);
            }
        }
        return idLists;
    }


    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tags='" + byte2String(tags) + '\'' +
                ", digest='" + digest + '\'' +
                ", description='" + description + '\'' +
                ", doc_url='" + doc_url + '\'' +
                ", up_user='" + up_user + '\'' +
                ", up_unit='" + up_unit + '\'' +
                ", tagNames='" + tagNames + '\'' +
                ", up_date=" + up_date +
                '}';
    }
}
