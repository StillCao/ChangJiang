package front.basic_page.Dao;

import front.basic_page.Domain.News_Total;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

/**
 * Fun:
 * Created by CW on 2020/6/15 3:07 下午
 */
public class QueryNews {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 1.新闻动态页面，新闻导航
     * @return 查询三大类新闻的最新的5条记录。
     */
    public List<News_Total> queryNewsForType(int type){
        String sql = "SELECT id,title,date,type  FROM news WHERE TYPE = ? ORDER BY DATE DESC LIMIT 5;";
        return template.query(sql, new BeanPropertyRowMapper<>(News_Total.class),type);
    }

    /**
     * 2.查询某类新闻的记录条数
     * 参数：新闻类别：type;
     * @return totalCount
     */
    public int queryNewsTotalCount(int type){
        String sql = "select count(*) from news where type = ? ";
        return template.queryForObject(sql,Integer.class,type);
    }


    /**
     * 3.数据的分页查询
     *
     * @param type
     * @param start
     * @param rows
     * @return 新闻分页查询的详情字段（除新闻内容）
     */
    public List<News_Total> findByPage(int type, int start, int rows) {
        String sql = "SELECT id,title,date,source,type FROM news WHERE TYPE = ? ORDER BY DATE DESC LIMIT ?,?; ";
        List<News_Total> newsList = template.query(sql, new BeanPropertyRowMapper<>(News_Total.class), type, start, rows);
        return newsList;
    }

    /**
     * 4.查询某个新闻的内容
     * @param id
     * @return News_Total
     */
    public List<News_Total> newsContent(int id){
        String sql = "select * from news where id = ? ;";
        List<News_Total> news = template.query(sql, new BeanPropertyRowMapper(News_Total.class), id);
        return news;
    }
}
