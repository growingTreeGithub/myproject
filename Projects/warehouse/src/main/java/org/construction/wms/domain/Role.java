package org.construction.wms.domain;

import java.util.ArrayList;
import java.util.List;

public class Role {
    private Integer id;  // the id of the role
    private String name; // the name of the role
    private String[] permissionArray; //the id of the permission that this role has
    private List<Permission> permissions = new ArrayList<>(); // the list of permission object that this role has

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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String[] getPermissionArray() {
        return permissionArray;
    }

    public void setPermissionArray(String[] permissionArray) {
        this.permissionArray = permissionArray;
    }
}
