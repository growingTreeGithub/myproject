package org.construction.wms.query;

//QueryObject is used to accept necessary page display info from the frontend jsp.
public class QueryObject {
    private Integer currentPage = 1; // the currentPage number which shows the queries result, default currentPage number is 1
    private Integer pageSize = 10; // total queries that shown in one page, default pageSize is 10

    //using currentPage and pageSize to calculate the start number in sql for LIMIT #{start}, #{pageSize}
    public Integer getStart(){
        return (this.currentPage - 1)*this.pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
