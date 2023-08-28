package org.construction.wms.domain;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    public static final int EMPLOYED = 0; // employee are in service
    public static final int RESIGNED = 1; // employee resigned from the company

    private Integer id;
    private String name;
    private String password; //password of this employee who can login the warehouse management system
    private Integer age;
    private boolean admin = false; //whether this employee is the admin of the warehouse management system or not
    private int status = Employee.EMPLOYED; //whether this employee are in service or resigned from the company
    private Department dept; //the department that this employee is under
    private String[] roleArray; /*the id of the roles that this employee has,
    if this roleArray and its getter and setter method doesn't exist, the data of the id of the roleArray
    cannot be collected from the frontend to backend controller*/
    private List<Role> roles = new ArrayList<>(); // the roles that this employee has

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String[] getRoleArray() {
        return roleArray;
    }

    public void setRoleArray(String[] roleArray) {
        this.roleArray = roleArray;
    }

    public static int getEMPLOYED() {
        return EMPLOYED;
    }

    public static int getRESIGNED() {
        return RESIGNED;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
