package back.simba.dao;

import back.simba.domain.RollingData;
import com.alibaba.fastjson.JSONObject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 对rolling执行相关操作
 * @author Simba
 * @date 2020/10/27 10:01
 */
public class RollingQuery {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 配置轮播图：在rolling表中插入一条数据
     * @param json
     * @return
     * @throws ParseException
     */
    public boolean addRollingData(JSONObject json) throws ParseException {
        //1.获取json数组中相应字段的值
        String title = json.getString("title");
        String link = json.getString("link");
        String file = json.getString("file");
        //String格式的数据转换成Date格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date time = format.parse(json.getString("time"));

        //2.创建sql语句，插入数据
        String sql = "INSERT INTO rolling VALUES(null,?,?,?,?)";

        //3.执行sql语句，返回是否插入成功
        int res = template.update(sql, title, link, file, time);
        boolean flag = false;
        if (res != 0){
            flag = true;
        }
        return flag;
    }

    /**
     * 返回所有轮播图数据
     * @return
     */
    public List<RollingData> queryAllData(){

        String sql = "SELECT * FROM rolling";
        List<RollingData> res = template.query(sql, new BeanPropertyRowMapper<>(RollingData.class));
        return res;
    }

    /**
     * 根据id获取某一条数据
     * @param id
     * @return
     */
    public RollingData queryRollingById(Integer id){

        String sql = "SELECT * FROM rolling WHERE id = ?";
        RollingData res = template.queryForObject(sql, RollingData.class);
        return res;
    }

    /**
     * 删除操作。结果为true，删除成功；结果为false，删除失败。
     * @param id
     * @return
     */
    public boolean deleteRollingById(Integer id){

        String sql = "DELETE FROM rolling WHERE id = ?";
        int res = template.update(sql);
        boolean flag = false;
        if (res != 0){
            flag = true;
        }
        return flag;
    }

    /**
     * 编辑操作
     * @return
     */
    public boolean updateRollingById(){
        return false;
    }

}
