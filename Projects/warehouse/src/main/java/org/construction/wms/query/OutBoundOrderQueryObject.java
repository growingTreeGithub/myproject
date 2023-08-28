package org.construction.wms.query;

import java.util.Date;

public class OutBoundOrderQueryObject extends QueryObject{
    //it represents the startDate of the search time range that the outBoundDate lies in and the startDate is located in search box on outbound order info page
    private Date startDate;
    //it represents the endDate of the search time range that the outBoundDate lies in and the endDate is located in search box on outbound order info page
    private Date endDate;
    //the search keyword entered in search box on outbound order info page under stock management module
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
