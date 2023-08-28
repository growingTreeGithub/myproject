package org.construction.wms.service.impl;

import org.construction.wms.dao.RoleDao;
import org.construction.wms.domain.Permission;
import org.construction.wms.domain.Role;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public int save(Role role) {
        //to insert new role data into role table
        int effectCount = roleDao.insert(role);
        //to get the id of the new added role
        int maxId = roleDao.getMaxId();
        //to deal with Join table relationship
        //As the relationship of role to permission is many-to-many, the join table relationship between role and permission must need to be clarify
        //to get all the permissions of the role
        List<Permission> permissions = role.getPermissions();
        //if role has permissions
        if(permissions != null){
            for (Permission permission : permissions) {
                /*add the id of new added role and its corresponding permission ids
                into the join table to deal with many-to-many relationship between role and permission
                * */
                roleDao.handlerRelation(maxId, permission.getId());
            }
        }
        return effectCount;
    }

    @Override
    public int update(Role role) {
        //to update Role table data
        int effectCount = roleDao.updateByPrimaryKey(role);
        //to deal with Join table relationship
        //to delete old permission data with corresponding role id
        roleDao.deletePermissionByRoleId(role.getId());
        //to update new data
        //to insert the join table relationship of the updated role and all the updated permissions that the role had into the join table
        List<Permission> permissions = role.getPermissions();
        //if role has permissions
        if(permissions != null){
            for (Permission permission : permissions) {
                /*to handle many-to-many relationship between permission and role by inserting
                permission id and role id into the join table role_permission
                */
                roleDao.handlerRelation(role.getId(), permission.getId());
            }
        }
        return effectCount;
    }

    @Override
    public int delete(Integer id) {
        //to delete all the permission id with corresponding role id in join table
        roleDao.deletePermissionByRoleId(id);
        //then delete the specific role with role id
        return roleDao.deleteByPrimaryKey(id);
    }

    @Override
    public Role get(Integer id) {
        return roleDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Role> selectAll() {
        return roleDao.selectAll();
    }

    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)roleDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the role showing in a particular page
            List<Role> result = roleDao.queryByCondition(qo);
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }
    //to check which roles that a employee has with its employee id
    @Override
    public List<Role> queryRoleByEmployeeId(Integer id) {
        List<Role> result = roleDao.queryRoleByEmployeeId(id);
        return result;
    }

}
