package org.construction.wms.service;

import org.construction.wms.domain.Permission;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface PermissionService {
    int save(Permission permission);
    int update(Permission permission);
    int delete(Integer id);
    Permission get(Integer id);
    List<Permission> selectAll();
    PageResult selectByCondition(QueryObject qo);
    List<Permission> queryPermissionByRoleId(Integer roleId);
    Permission queryByExpression(String expression);
    List<Permission> queryPermissionByEmployeeId(Integer id);
}
