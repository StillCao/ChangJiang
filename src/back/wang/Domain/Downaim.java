package back.wang.Domain;

import java.util.Date;

/**
 * 描述:
 * 下载目的Bean
 *
 * @author black-leaves
 * @createTime 2020-06-29  15:06
 */

public class Downaim {
    private int id;
    private String location;
    private double north;
    private double south;
    private double east;
    private double west;
    private String timeRange;
    private int useType;
    private String proofUrl;
    private String projLevel;
    private String projName;
    private String projCode;
    private String projAdmin;
    private String projWorkUnit;
    private Date projEndTime;
    private String title;
    private String schoolName;
    private String teacherName;
    private String teacherPhone;
    private Date endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getNorth() {
        return north;
    }

    public void setNorth(double north) {
        this.north = north;
    }

    public double getSouth() {
        return south;
    }

    public void setSouth(double south) {
        this.south = south;
    }

    public double getEast() {
        return east;
    }

    public void setEast(double east) {
        this.east = east;
    }

    public double getWest() {
        return west;
    }

    public void setWest(double west) {
        this.west = west;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }


    public int getUseType() {
        return useType;
    }

    public void setUseType(int useType) {
        this.useType = useType;
    }

    public String getProofUrl() {
        return proofUrl;
    }

    public void setProofUrl(String proofUrl) {
        this.proofUrl = proofUrl;
    }

    public String getProjLevel() {
        return projLevel;
    }

    public void setProjLevel(String projLevel) {
        this.projLevel = projLevel;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public String getProjAdmin() {
        return projAdmin;
    }

    public void setProjAdmin(String projAdmin) {
        this.projAdmin = projAdmin;
    }

    public String getProjWorkUnit() {
        return projWorkUnit;
    }

    public void setProjWorkUnit(String projWorkUnit) {
        this.projWorkUnit = projWorkUnit;
    }

    public Date getProjEndTime() {
        return projEndTime;
    }

    public void setProjEndTime(Date projEndTime) {
        this.projEndTime = projEndTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Downaim{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", north=" + north +
                ", south=" + south +
                ", east=" + east +
                ", west=" + west +
                ", timeRange='" + timeRange + '\'' +
                ", useType=" + useType +
                ", proofUrl='" + proofUrl + '\'' +
                ", projLevel='" + projLevel + '\'' +
                ", projName='" + projName + '\'' +
                ", projCode='" + projCode + '\'' +
                ", projAdmin='" + projAdmin + '\'' +
                ", projWorkUnit='" + projWorkUnit + '\'' +
                ", projEndTime=" + projEndTime +
                ", title='" + title + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", teacherPhone='" + teacherPhone + '\'' +
                ", endTime=" + endTime +
                '}';
    }
}
