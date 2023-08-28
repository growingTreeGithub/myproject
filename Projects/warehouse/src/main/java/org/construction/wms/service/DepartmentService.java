package org.construction.wms.service;


import org.construction.wms.domain.Department;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface DepartmentService {
    int save(Department dept);
    int update(Department dept);
    int delete(Integer id);
    Department get(Integer id);
    List<Department> selectAll();
    PageResult selectByCondition(QueryObject qo);
}
