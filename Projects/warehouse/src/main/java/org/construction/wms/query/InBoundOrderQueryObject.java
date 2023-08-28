package org.construction.wms.query;

import java.util.Date;

public class InBoundOrderQueryObject extends QueryObject{
    //it represents the startDate of the search time range that the inBoundDate lies in and the startDate is located in search box on inbound order info page
    private Date startDate;
    //it represents the endDate of the search time range that the inBoundDate lies in and the endDate is located in search box on inbound order info page
    private Date endDate;

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

}
