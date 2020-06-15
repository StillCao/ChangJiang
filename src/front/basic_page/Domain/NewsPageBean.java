package front.basic_page.Domain;

import java.util.List;

/**
 * Fun:
 * Created by CW on 2020/6/15 4:43 下午
 */
public class NewsPageBean {
    private int totalCount; //总记录数
    private int totalPage;  //总页码
    private List<News_Total> newsList;  //每页的数据集合list
    private int currentPage;  //当前页码
    private int rows;  //每页显示的记录条数

    public NewsPageBean(int totalCount, int totalPage, List<News_Total> newsList, int currentPage, int rows) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.newsList = newsList;
        this.currentPage = currentPage;
        this.rows = rows;
    }

    public NewsPageBean() {
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<News_Total> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News_Total> newsList) {
        this.newsList = newsList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "NewsPageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", newsList=" + newsList +
                ", currentPage=" + currentPage +
                ", rows=" + rows +
                '}';
    }
}
