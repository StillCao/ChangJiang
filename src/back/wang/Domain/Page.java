package back.wang.Domain;

import java.util.List;

/**
 * 描述:
 * 分页bean
 *
 * @author black-leaves
 * @createTime 2020-06-23  9:56
 */


public class Page<T> {
    private int totalPage;//总页数
    private int currentPage;//当前页数

    private int currentCount;//当前页显示数目
    private int totalCount;
    private List<T> list;

    public Page(int currentPage, int currentCount, int totalPage, int totalCount, List<T> list) {
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.currentCount = currentCount;
        this.totalCount = totalCount;
        this.list = list;
    }

    public Page() {
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}

