package front.basic_page.service;

import front.basic_page.Dao.QueryNews;
import front.basic_page.Domain.NewsPageBean;
import front.basic_page.Domain.News_Total;

import java.util.List;

/**
 * Fun:
 * Created by CW on 2020/6/15 4:55 下午
 */
public class NewsService {
    /**
     * 新闻分页查询，逻辑控制代码
     */
    public NewsPageBean findNewsByPage(int type,int currentPage,int rows){
        NewsPageBean newsPageBean = new NewsPageBean();
        //当前页码
        newsPageBean.setCurrentPage(currentPage);
        //每页显示行数
        newsPageBean.setRows(rows);
        //查询该类新闻总记录数
        int count = new QueryNews().queryNewsTotalCount(type);
        newsPageBean.setTotalCount(count);
        //计算总页码
        int totalPage = count % rows == 0 ? count /rows : count / rows + 1;
        newsPageBean.setTotalPage(totalPage);
        //查询该页的数据list集合
        int start = (currentPage - 1) * rows;
        List<News_Total>  newsList= new QueryNews().findByPage(type,start,rows);
        newsPageBean.setNewsList(newsList);
        //返回封装好的对象
        return  newsPageBean;

    }


}
