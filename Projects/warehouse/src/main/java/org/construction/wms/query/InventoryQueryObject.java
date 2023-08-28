package org.construction.wms.query;

public class InventoryQueryObject extends QueryObject{
    //the search keyword entered in search box on inventory report page under stock management module
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
