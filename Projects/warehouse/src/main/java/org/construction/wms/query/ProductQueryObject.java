package org.construction.wms.query;

public class ProductQueryObject extends QueryObject{
    //the search keyword entered in search box on product info page under basic warehouse info module
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
