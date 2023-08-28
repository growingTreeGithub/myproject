package org.construction.wms.query;

public class DepartmentQueryObject extends QueryObject{
    //the search keyword entered in search box on department info page under permission management module
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
