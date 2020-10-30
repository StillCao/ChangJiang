package back.simba.dao;

import back.simba.domain.RecomendData;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐数据配置
 * @author Simba
 * @date 2020/10/27 11:05
 */
public class RecommendQuery {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 获取按照时间降序的数据列表
     * @return
     */
    public List<RecomendData> queryBasicInfoByTime(int currentPage, int pageSize){

        String sql2 = "SELECT id,name,status FROM basic_info ORDER BY up_time DESC LIMIT ?,?";
        //2.执行sql语句，封装结果
        List<RecomendData> res = template.query(sql2, new BeanPropertyRowMapper<>(RecomendData.class),(currentPage - 1) * pageSize, pageSize);
        return res;
    }

    /**
     * 获取按照点击量降序的数据列表
     * @return
     */
    public List<RecomendData> queryBasicInfoByClickCount(int currentPage,int pageSize){
        //1.创建sql语句，查询按照点击量降序的数据列表
        String sql = "SELECT id,name,status FROM basic_info ORDER BY click_count DESC LIMIT ?,?";
        //2.执行sql语句
        List<RecomendData> res = template.query(sql, new BeanPropertyRowMapper<>(RecomendData.class),(currentPage - 1) * pageSize, pageSize);
        return res;
    }

    /**
     * 所有数据的总数
     * @return
     */
    public Integer queryAllBasicInfo(){
        String sql = "SELECT COUNT(*) FROM basic_info";
        Integer counts = template.queryForObject(sql, Integer.class);
        return counts;
    }

    /**
     *
     * @param jsons
     * @return
     */
    public Map<Integer,Boolean> updateStatusOfBasicInfo(JSONArray jsons){
        //定义id获取每个json对象中的id值，status同理
        int id = 0;int status = 0;
        JSONObject json;
        boolean flag = false;//记录每个id的状态修改是否成功
        Map<Integer,Boolean> isSuccessed = new HashMap<>();
        for (int i = 0; i < jsons.size(); i++) {
            json = jsons.getJSONObject(i);
            id = json.getInteger("id");//获得前端传来的id
            status = json.getInteger("status");//获得前端传来的status
            String sql = "UPDATE basic_info SET status = ? WHERE id = ?";
            int res = template.update(sql, status, id);
            if (res != 0){
                flag = true;
            }else {
                flag = false;
            }
            isSuccessed.put(id,flag);
        }
        return isSuccessed;
    }

    /**
     * 查询被推荐的数据（status=1）的总数
     * @return
     */
    public Integer queryAllRecommenedDatas(){

        String sql = "SELECT COUNT(*) FROM basic_info WHERE status = ?";
        Integer counts = template.queryForObject(sql, Integer.class, 1);
        return counts;
    }
}
