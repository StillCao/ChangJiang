package back.simba.dao;

import back.simba.domain.PlatformLink;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 描述：
 * 平台链接的后台管理功能
 *
 * @author Simba
 * @date 2020/10/26 16:14
 */
public class PlatQuery {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *
     * 判断新增数据是否成功加入到表中，0添加成功，1添加失败
     * @param pl
     * @return
     */
    public int addNewPlatform(PlatformLink pl){
        //1.创建sql语句，实现新增功能
        String sql = "INSERT INTO pla_link VALUES(null,?,?,?,?)";
        //2.执行sql语句，返回是否成功
        int result = template.update(sql, pl.getName(), pl.getUrl(), pl.getType(), pl.getStatus());
        return result;
    }

    /**
     * 返回分页查询和条件查询的结果。条件查询可以为空
     * @param searchWord
     * @param currentPage
     * @param pageSize
     * @return
     */
    public List<PlatformLink> queryPlatformLink(String searchWord,int currentPage, int pageSize){
        List<PlatformLink> result = new ArrayList<>();
        if (searchWord == null){
            String sql = "SELECT * FROM pla_link LIMIT ?,?";
            result = template.query(sql,new BeanPropertyRowMapper<>(PlatformLink.class),(currentPage - 1) * pageSize,pageSize);
            return result;
        }
        String sql1 = "SELECT * FROM pla_link WHERE name LIKE '%" +searchWord+ "%' OR url LIKE '%" +searchWord+ "%' LIMIT ?,? ";
        //2.执行sql语句，获取所有数据
        result = template.query(sql1, new BeanPropertyRowMapper<>(PlatformLink.class), (currentPage - 1) * pageSize, pageSize);
        return result;
    }

    /**
     * 对pla_link(平台连接表)进行全表查询
     * @return
     */
    public List<PlatformLink> queryAllPLs(){
        String sql = "SELECT * FROM pla_link";
        List<PlatformLink> res = template.query(sql, new BeanPropertyRowMapper<>(PlatformLink.class));
        return res;
    }

    /**
     *根据id，删除相关数据
     * @param id
     * @return
     */
    public boolean deletePlatformLinkById(Integer id){
        //1.创建sql语句，删除id对应的数据
        String sql = "DELETE FROM pla_link WHERE id = ?";
        String sql1 = "ALTER TABLE pla_link AUTO_INCREMENT = 1";
        //2.执行sql语句，返回是否成功
        int res = template.update(sql, id);
        template.update(sql1);//防止删除数据后，新增数据的id不连续
        //3.返回结果为0，表示删除成功；反之，删除不成功
        boolean flag = false;//保存对数据库的删除功能执行结果
        if (res != 0){
            flag = true;
        }

        return flag;
    }
}
