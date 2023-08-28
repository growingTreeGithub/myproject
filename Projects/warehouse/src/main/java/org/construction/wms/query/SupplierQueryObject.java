package org.construction.wms.query;

public class SupplierQueryObject extends QueryObject{
    //the search keyword entered in search box on supplier info page under basic warehouse info module
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
