package org.construction.wms.domain;

public class Permission {
    private Integer id; //the id of permission
    private String name; //the name of permission
    private String expression; // the fully-qualified name of the permission

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

}
