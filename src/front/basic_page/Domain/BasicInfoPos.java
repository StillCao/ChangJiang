package front.basic_page.Domain;

/**
 * 描述:
 * 数据空间位置展示Bean
 *
 * @author black-leaves
 * @createTime 2020-06-16  19:35
 */

public class BasicInfoPos {
    private long point1_lat;
    private long point2_lat;
    private long point1_lon;
    private long point2_lon;

    public long getPoint1_lat() {
        return point1_lat;
    }

    public void setPoint1_lat(long point1_lat) {
        this.point1_lat = point1_lat;
    }

    public long getPoint2_lat() {
        return point2_lat;
    }

    public void setPoint2_lat(long point2_lat) {
        this.point2_lat = point2_lat;
    }

    public long getPoint1_lon() {
        return point1_lon;
    }

    public void setPoint1_lon(long point1_lon) {
        this.point1_lon = point1_lon;
    }

    public long getPoint2_lon() {
        return point2_lon;
    }

    public void setPoint2_lon(long point2_lon) {
        this.point2_lon = point2_lon;
    }

    @Override
    public String toString() {
        return "BasicInfoPos{" +
                "point1_lat=" + point1_lat +
                ", point2_lat=" + point2_lat +
                ", point1_lon=" + point1_lon +
                ", point2_lon=" + point2_lon +
                '}';
    }
}
