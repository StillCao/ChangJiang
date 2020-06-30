package front.basic_page.Dao;

import front.basic_page.Domain.BasicData;
import front.basic_page.Domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

/**
 * Fun:
 * Created by CW on 2020/6/17 4:50 下午
 */
public class QueryBasicData {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 1.根据id,查询用户信息
     *
     * @param id
     * @return
     */
    public User queryUserById(int id) {
        String sql = "select id,account,phone,email,workUnit,addr from admin where id = ? ;";
        List<User> userList = template.query(sql, new BeanPropertyRowMapper<>(User.class), id);
        User user = userList.get(0);
        return user;
    }

    /**
     * 2.根据id,查询该条数据的详细信息
     *
     * @param id
     * @return
     */
    public BasicData queryBasicDataById(int id) {
        String sql = "select id,name,sploc,docname,up_time,uper_name,uper_place,datm_range,subj_cfi,point1_lat,point1_lon,point2_lat,point2_lon,topic_w1,topic_w2,topic_w3,topic_cfi," +
                "da_summ,da_size,up_id,da_type,da_source,da_method,da_projection,da_quality,da_refer from basic_info where id = ? ;";
        List<BasicData> basicDataList = template.query(sql, new BeanPropertyRowMapper<>(BasicData.class), id);
        BasicData basicData = basicDataList.get(0);
        return basicData;
    }

    /**
     * 3.根据id,查询缩略图
     * @param id
     * @return 缩略图的id
     */
    public String queryImage(int id ){
        String sql = "select image from basic_info where id = ? ;";
        String imageUrl = template.queryForObject(sql, String.class, id);
        return imageUrl;
    }
}
