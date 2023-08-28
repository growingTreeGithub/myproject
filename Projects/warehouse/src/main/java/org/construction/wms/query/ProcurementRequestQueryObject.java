package org.construction.wms.query;

import java.util.Date;

public class ProcurementRequestQueryObject extends QueryObject{
    //it represents the startDate of the search time range that the inputDate lies in and the startDate is located in search box on inventory report page
    private Date startDate;
    //it represents the endDate of the search time range that the inputDate lies in and the endDate is located in search box on inventory report page
    private Date endDate;

    private String keyword;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
