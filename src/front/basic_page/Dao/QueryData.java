package front.basic_page.Dao;

import front.basic_page.Domain.BasicInfo;
import front.basic_page.Domain.News;
import utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class QueryData {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * @return 查询最新8条新闻
     */
    public List<News> QueryLatest8News() {
        String sql = "SELECT * from news Order By Date Desc limit 8";
        return template.query(sql, new BeanPropertyRowMapper<>(News.class));
    }

    /**
     * @return 查询最新8条基础数据
     */
    public List<BasicInfo> QueryLatest8BasicInfo() {
        String sql = "SELECT * from basic_info Order By up_time Desc limit 8";
        return template.query(sql, new BeanPropertyRowMapper<>(BasicInfo.class));
    }

    /**
     * 根据二级标签名查询对应的基础数据
     *
     * @param tagName 二级标签名
     * @return 所有属于二级标签名的数据
     */
    public List<BasicInfo> QueryBasicInfoByTagLevel2(String tagName) {
        return null;
    }

    /**
     * 根据一级标签查询对应的基础数据
     *
     * @param tagName 一级标签名
     * @return 所有属于一级标签名的数据
     */
    public List<BasicInfo> QueryBasicInfoByTagLevel1(String tagName) {
        return null;
    }



}
