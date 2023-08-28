package org.construction.wms.dao;

import org.apache.ibatis.annotations.Param;
import org.construction.wms.domain.Employee;
import org.construction.wms.domain.Role;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface RoleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Role role);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role role);
    //return the total number of records in role table and put it into totalCount of the pageResult
    Long queryByConditionCount(QueryObject qo);
    //return a list of role showing in a particular page
    List<Role> queryByCondition(QueryObject qo);
    /*to handle many-to-many relationship between permission and role by inserting
     permission id and role id into the join table role_permission
    */
    int handlerRelation(@Param("rid")Integer rid, @Param("pid")Integer pid);
    //to delete all the permission id with corresponding role id in join table
    int deletePermissionByRoleId (Integer roleId);
    //to get the role id of the new added role so that the permission id with its corresponding new role id can be added into join table
    int getMaxId();
    //to check which roles that a employee has with its employee id
    List<Role> queryRoleByEmployeeId(Integer id);
}
