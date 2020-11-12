package back.simba.dao;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

/**
 * @author Simba
 * @date 2020/11/12 20:32
 */
public class FileQuery {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据数据id来判断该数据的file_url是否为空（其实就是判断是否有原始数据），不为空返回1，为空放回0
     * @param id
     * @return
     */
    public int queryFileUrlById(int id){
        String sql = "SELECT file_url FROM basic_info WHERE id = ?";
        String file_url = template.queryForObject(sql, String.class, id);
        int result = 1;//默认原始数据不为空，返回1
        if (file_url == null || file_url.equals("")){
            result = 0;
        }
        return result;
    }
}
