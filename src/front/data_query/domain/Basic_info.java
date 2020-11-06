package front.data_query.domain;

import java.util.Date;

public class Basic_info {

    private Integer id;
    private String name;
    private String sploc;
    private String docname;
    private Date up_time;
    private Double point1_lat;
    private Double point1_lon;
    private Double point2_lat;
    private Double point2_lon;
    private String topic_w1;
    private String topic_w2;
    private String topic_w3;
    private String topic_cfi;
    private String da_summ;
    private String da_size;
    private String da_url;
    private Integer up_id;
    private Integer da_type;
    private String image;
    private String uper_name;
    private String uper_place;
    private Integer click_count;

    public Basic_info() {
    }

    public Basic_info(Integer id, String name, String sploc, String docname, Date up_time, Double point1_lat, Double point1_lon, Double point2_lat, Double point2_lon, String topic_w1, String topic_w2, String topic_w3, String topic_cfi, String da_summ, String da_size, String da_url, Integer up_id, Integer da_type, String image, String uper_name, String uper_place, Integer click_count) {
        this.id = id;
        this.name = name;
        this.sploc = sploc;
        this.docname = docname;
        this.up_time = up_time;
        this.point1_lat = point1_lat;
        this.point1_lon = point1_lon;
        this.point2_lat = point2_lat;
        this.point2_lon = point2_lon;
        this.topic_w1 = topic_w1;
        this.topic_w2 = topic_w2;
        this.topic_w3 = topic_w3;
        this.topic_cfi = topic_cfi;
        this.da_summ = da_summ;
        this.da_size = da_size;
        this.da_url = da_url;
        this.up_id = up_id;
        this.da_type = da_type;
        this.image = image;
        this.uper_name = uper_name;
        this.uper_place = uper_place;
        this.click_count = click_count;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Double getPoint1_lat() {
        return point1_lat;
    }

    public void setPoint1_lat(Double point1_lat) {
        this.point1_lat = point1_lat;
    }

    public Double getPoint1_lon() {
        return point1_lon;
    }

    public void setPoint1_lon(Double point1_lon) {
        this.point1_lon = point1_lon;
    }

    public Double getPoint2_lat() {
        return point2_lat;
    }

    public void setPoint2_lat(Double point2_lat) {
        this.point2_lat = point2_lat;
    }

    public Double getPoint2_lon() {
        return point2_lon;
    }

    public void setPoint2_lon(Double point2_lon) {
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

    public Integer getUp_id() {
        return up_id;
    }

    public void setUp_id(Integer up_id) {
        this.up_id = up_id;
    }

    public Integer getDa_type() {
        return da_type;
    }

    public void setDa_type(Integer da_type) {
        this.da_type = da_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getClick_count() {
        return click_count;
    }

    public void setClick_count(Integer click_count) {
        this.click_count = click_count;
    }
}
