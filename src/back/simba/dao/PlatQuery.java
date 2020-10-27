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
     * 根据id，返回满足不同要求的请求数据
     * @param id
     * @param currentPage
     * @param pageSize
     * @return
     */
    public List<PlatformLink> queryPlatformLink(Integer id,int currentPage, int pageSize){
        List<PlatformLink> result = new ArrayList<>();
        if (id == null){
            //1.创建sql语句，分页查询所有数据
            String sql1 = "SELECT * FROM pla_link LIMIT ?,?";
            //2.执行sql语句，获取所有数据
             result = template.query(sql1, new BeanPropertyRowMapper<>(PlatformLink.class), currentPage - 1, pageSize);
            return result;
        }
        //id != null, ，返回对应数据
        String sql2 = "SELECT FORM pla_link WHERE id = ?";
        result = template.query(sql2, new BeanPropertyRowMapper<>(PlatformLink.class), id);
        return result;
    }

    /**
     * 根据id，更新对应数据的属性值
     * @param id
     * @param name
     * @param url
     * @param type
     * @param status
     * @return
     */
    public boolean updatePlatformLink(Integer id,String name,String url,String type,String status){
        //1.创建sql语句，更新表中数据
        String sql = "UPDATE pla_link SET name = ?,url = ?, type = ?, status = ? WHERE id = ?";
        //2.执行sql语句，返回是否成功
        int res = template.update(sql, name, url, type, status);
        boolean flag = false;
        if (res != 0){
            flag = true;
        }
        return true;
    }

    /**
     *根据id，删除相关数据
     * @param id
     * @return
     */
    public boolean deletePlatformLinkById(Integer id){
        //1.创建sql语句，删除id对应的数据
        String sql = "DELETE FROM pla_link WHERE id = ?";
        //2.执行sql语句，返回是否成功
        int res = template.update(sql, id);
        //3.返回结果为0，表示删除成功；反之，删除不成功
        boolean flag = false;//保存对数据库的删除功能执行结果
        if (res != 0){
            flag = true;
        }

        return flag;
    }
}
