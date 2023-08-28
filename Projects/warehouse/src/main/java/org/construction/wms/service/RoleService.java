package org.construction.wms.service;

import org.construction.wms.domain.Employee;
import org.construction.wms.domain.Role;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface RoleService {
    int save(Role role);
    int update(Role role);
    int delete(Integer id);
    Role get(Integer id);
    List<Role> selectAll();
    PageResult selectByCondition(QueryObject qo);
    List<Role> queryRoleByEmployeeId(Integer id);
}
