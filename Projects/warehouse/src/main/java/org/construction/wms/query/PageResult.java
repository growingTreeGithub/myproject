package org.construction.wms.query;

import java.util.Collections;
import java.util.List;

//PageResult is used to return the necessary page display info.It does not accept any information from the frontend jsp.
public class PageResult {
    private int totalCount; // Count of total queries
    private List result; // List of query result

    private int currentPage = 1; // the currentPage number which shows the queries result, default currentPage number is 1
    private int pageSize = 10; // total queries that shown in one page, default pageSize is 10

    private int totalPage; //the number of page numbers that exist for all the queries, depending on the number of queries(pageSize) showing on each page
    private int prevPage; //previous page number of the currentPage
    private int nextPage; //next page number of the currentPage

    //return a empty set of pageResult if there is no record for the query
    public static PageResult empty(int pageSize){
        return new PageResult(0, Collections.emptyList(), 1, pageSize);
    }

    public PageResult(int totalCount, List result, int currentPage, int pageSize) {
        this.totalCount = totalCount;
        this.result = result;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        //if the totalCount is not divisible by pageSize, then add one more page to totalPage.
        totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        //if the previous page is smaller or equal to one, then the first page is the previous page
        prevPage = currentPage - 1 >= 1 ? currentPage - 1 : 1;
        //if the next page is greater than or equal to totalPage, then the totalPage(lastPage) is the next page.
        nextPage = currentPage + 1 <= totalPage ? currentPage + 1 : totalPage;
    }



    public int getTotalCount() {
        return totalCount;
    }

    public List getResult() {
        return result;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public int getNextPage() {
        return nextPage;
    }
}
