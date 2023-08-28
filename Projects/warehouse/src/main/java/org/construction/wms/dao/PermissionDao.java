package org.construction.wms.dao;

import org.construction.wms.domain.Permission;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface PermissionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission permission);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission permission);
    //return the total number of records in permission table and put it into totalCount of the pageResult
    Long queryByConditionCount(QueryObject qo);
    //return a list of permission showing in a particular page
    List<Permission> queryByCondition(QueryObject qo);
    //return all permission that a role has
    List<Permission> queryPermissionByRoleId(Integer roleId);
    /*to check whether the expression exist in permission table
    in order to check the specific operation needs permission control in the warehouse management system
    */
    Permission queryByExpression(String expression);
    /*to check all permissions that a employee have in the warehouse management system
    and used in PermissionUtils checkPermission method to establish a permission control system for different users
    */
    List<Permission> queryPermissionByEmployeeId(Integer id);
}
