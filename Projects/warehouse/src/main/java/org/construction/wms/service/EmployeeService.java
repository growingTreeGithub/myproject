package org.construction.wms.service;

import org.construction.wms.domain.Employee;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface EmployeeService {
    int save(Employee employee);
    int update(Employee employee);
    int delete(Integer id);
    Employee get(Integer id);
    List<Employee> selectAll();
    PageResult selectByCondition(QueryObject qo);
    Employee queryByLogin(String name, String password);
    int updateState(Integer id);
}
