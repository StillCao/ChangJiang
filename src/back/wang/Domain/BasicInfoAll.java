package back.wang.Domain;

import java.util.Date;

/**
 * 描述:
 * basic_info 表的Bean （全字段）
 *
 * @author black-leaves
 * @createTime 2020-06-24  13:43
 */

public class BasicInfoAll {
    private int id ;
    private String name ;
    private String  sploc;
    private String docname;
    private Date up_time;
    private long point1_lat;
    private long point1_lon;
    private long point2_lat;
    private long point2_lon;
    private String topic_w1;
    private String topic_w2;
    private String topic_w3;
    private String topic_cfi;
    private String da_summ;
    private String da_size;
    private String da_url;
    private int up_id;
    private int da_type;
    private String image;
    private String uper_name;
    private String uper_place;
    private String file_url;
    private String sample_url;
    private String datm_range;
    private String da_source;
    private String da_method;
    private String da_projection;
    private String da_quality;
    private String da_refer;

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

    public String getSploc() {
        return sploc;
    }

    public void setSploc(String sploc) {
        this.sploc = sploc;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public Date getUp_time() {
        return up_time;
    }

    public void setUp_time(Date up_time) {
        this.up_time = up_time;
    }

    public long getPoint1_lat() {
        return point1_lat;
    }

    public void setPoint1_lat(long point1_lat) {
        this.point1_lat = point1_lat;
    }

    public long getPoint1_lon() {
        return point1_lon;
    }

    public void setPoint1_lon(long point1_lon) {
        this.point1_lon = point1_lon;
    }

    public long getPoint2_lat() {
        return point2_lat;
    }

    public void setPoint2_lat(long point2_lat) {
        this.point2_lat = point2_lat;
    }

    public long getPoint2_lon() {
        return point2_lon;
    }

    public void setPoint2_lon(long point2_lon) {
        this.point2_lon = point2_lon;
    }

    public String getTopic_w1() {
        return topic_w1;
    }

    public void setTopic_w1(String topic_w1) {
        this.topic_w1 = topic_w1;
    }

    public String getTopic_w2() {
        return topic_w2;
    }

    public void setTopic_w2(String topic_w2) {
        this.topic_w2 = topic_w2;
    }

    public String getTopic_w3() {
        return topic_w3;
    }

    public void setTopic_w3(String topic_w3) {
        this.topic_w3 = topic_w3;
    }

    public String getTopic_cfi() {
        return topic_cfi;
    }

    public void setTopic_cfi(String topic_cfi) {
        this.topic_cfi = topic_cfi;
    }

    public String getDa_summ() {
        return da_summ;
    }

    public void setDa_summ(String da_summ) {
        this.da_summ = da_summ;
    }

    public String getDa_size() {
        return da_size;
    }

    public void setDa_size(String da_size) {
        this.da_size = da_size;
    }

    public String getDa_url() {
        return da_url;
    }

    public void setDa_url(String da_url) {
        this.da_url = da_url;
    }

    public int getUp_id() {
        return up_id;
    }

    public void setUp_id(int up_id) {
        this.up_id = up_id;
    }

    public int getDa_type() {
        return da_type;
    }

    public void setDa_type(int da_type) {
        this.da_type = da_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUper_name() {
        return uper_name;
    }

    public void setUper_name(String uper_name) {
        this.uper_name = uper_name;
    }

    public String getUper_place() {
        return uper_place;
    }

    public void setUper_place(String uper_place) {
        this.uper_place = uper_place;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getSample_url() {
        return sample_url;
    }

    public void setSample_url(String sample_url) {
        this.sample_url = sample_url;
    }

    public String getDatm_range() {
        return datm_range;
    }

    public void setDatm_range(String datm_range) {
        this.datm_range = datm_range;
    }

    public String getDa_source() {
        return da_source;
    }

    public void setDa_source(String da_source) {
        this.da_source = da_source;
    }

    public String getDa_method() {
        return da_method;
    }

    public void setDa_method(String da_method) {
        this.da_method = da_method;
    }

    public String getDa_projection() {
        return da_projection;
    }

    public void setDa_projection(String da_projection) {
        this.da_projection = da_projection;
    }

    public String getDa_quality() {
        return da_quality;
    }

    public void setDa_quality(String da_quality) {
        this.da_quality = da_quality;
    }

    public String getDa_refer() {
        return da_refer;
    }

    public void setDa_refer(String da_refer) {
        this.da_refer = da_refer;
    }

    @Override
    public String toString() {
        return "BasicInfoAll{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sploc='" + sploc + '\'' +
                ", docname='" + docname + '\'' +
                ", up_time=" + up_time +
                ", point1_lat=" + point1_lat +
                ", point1_lon=" + point1_lon +
                ", point2_lat=" + point2_lat +
                ", point2_lon=" + point2_lon +
                ", topic_w1='" + topic_w1 + '\'' +
                ", topic_w2='" + topic_w2 + '\'' +
                ", topic_w3='" + topic_w3 + '\'' +
                ", topic_cfi='" + topic_cfi + '\'' +
                ", da_summ='" + da_summ + '\'' +
                ", da_size='" + da_size + '\'' +
                ", da_url='" + da_url + '\'' +
                ", up_id=" + up_id +
                ", da_type=" + da_type +
                ", image='" + image + '\'' +
                ", uper_name='" + uper_name + '\'' +
                ", uper_place='" + uper_place + '\'' +
                ", file_url='" + file_url + '\'' +
                ", sample_url='" + sample_url + '\'' +
                ", datm_range='" + datm_range + '\'' +
                ", da_source='" + da_source + '\'' +
                ", da_method='" + da_method + '\'' +
                ", da_projection='" + da_projection + '\'' +
                ", da_quality='" + da_quality + '\'' +
                ", da_refer='" + da_refer + '\'' +
                '}';
    }
}
