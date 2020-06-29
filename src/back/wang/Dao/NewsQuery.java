package back.wang.Dao;


import back.wang.Domain.Admin;
import back.wang.Domain.News;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import utils.JDBCUtils;

import java.util.List;

/**
 * 描述:
 * 新闻查询
 *
 * @author black-leaves
 * @createTime 2020-06-29  16:09
 */

public class NewsQuery {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    NamedParameterJdbcTemplate npjTemplate = new NamedParameterJdbcTemplate(JDBCUtils.getDataSource());

    /**
     * key 模糊查询
     *
     * @param key admin account
     * @return 对应account 的 admin
     */
    public List<News> queryNewsByKeyLikeByPage(String key, String value, int startPos, int count) {
        value = "%" + value + "%";
        String sql = "select * from news where " + key + " like '" + value + "' limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(News.class), startPos, count);
    }

    /**
     *
     * @return 查询news表条数
     */
    public int queryNewsCount(){
        String sql = "select count(*) from news";
        return template.queryForObject(sql,Integer.class);
    }

    /**
     *
     * @return 查询news表条数
     */
    public int queryNewsByKeyCount(String key, String value){
        value = "%" + value + "%";
        String sql = "select count(*) from news where " + key + " like ?";
        return template.queryForObject(sql,Integer.class,value);
    }

    /**
     *
     * @param startPos 起始位置
     * @param count 查询条数
     * @return news集合
     */
    public List<News> queryAllNewsByPage(int startPos, int count){
        String sql = "select * from news limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(News.class), startPos, count);
    }

    /**
     * 插入一条news
     * @param news
     * @return
     */
    public boolean insertNews(News news){
        String sql = "insert into news (id,title,DATE,source,TYPE,news_cont,localaddr) Values(null,:title,:DATE,:source,:TYPE,:news_cont,:localaddr)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        npjTemplate.update(sql, new BeanPropertySqlParameterSource(news), keyHolder);
        return keyHolder.getKey().intValue() > 0;
    }


}
