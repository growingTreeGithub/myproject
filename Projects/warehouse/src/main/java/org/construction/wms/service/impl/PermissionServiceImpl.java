package org.construction.wms.service.impl;

import org.construction.wms.dao.PermissionDao;
import org.construction.wms.domain.Permission;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public int save(Permission permission) {
        return permissionDao.insert(permission);
    }

    @Override
    public int update(Permission permission) {
        return permissionDao.updateByPrimaryKey(permission);
    }

    @Override
    public int delete(Integer id) {
        return permissionDao.deleteByPrimaryKey(id);
    }

    @Override
    public Permission get(Integer id) {
        return permissionDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Permission> selectAll() {
        return permissionDao.selectAll();
    }
    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)permissionDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the permission showing in a particular page
            List<Permission> result = permissionDao.queryByCondition(qo);
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }

    //return all permission that a role has
    @Override
    public List<Permission> queryPermissionByRoleId(Integer id) {
        List<Permission> result = permissionDao.queryPermissionByRoleId(id);
        return result;
    }

    /*to check whether the expression exist in permission table
    in order to check the specific operation needs permission control in the warehouse management system
    */
    @Override
    public Permission queryByExpression(String expression) {
        return permissionDao.queryByExpression(expression);
    }

    /*to check all permissions that a employee have in the warehouse management system
    and used in PermissionUtils checkPermission method to establish a permission control system for different users
    */
    @Override
    public List<Permission> queryPermissionByEmployeeId(Integer id) {
        return permissionDao.queryPermissionByEmployeeId(id);
    }
}
