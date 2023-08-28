package org.construction.wms.service.impl;

import org.construction.wms.dao.EmployeeDao;
import org.construction.wms.domain.Employee;
import org.construction.wms.domain.Role;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.EmployeeService;
import org.construction.wms.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;
    @Override
    public int save(Employee employee) {
        //to encode employee password into MD5 string to enhance security and save it into database
        employee.setPassword(MD5Utils.getMd5(employee.getPassword()));
        //to insert new employee data into employee table
        int effectCount = employeeDao.insert(employee);
        //to get the employee id of the new added employee
        int maxId = employeeDao.getMaxId();
        //to deal with join table relationship
        //As the relationship of employee to roles is many-to-many, the join table relationship between employee and roles must need to be clarify
        //to get all the roles of the employee
        List<Role> roles = employee.getRoles();
        //to insert the join table relationship of the new added employee and all the roles that the employee had into the join table
        //if employee has roles
        if(roles != null){
            for (Role role : roles) {
                //to insert role id with corresponding employee id into join table
                employeeDao.handlerRelation(maxId, role.getId());
            }
        }
        return effectCount;
    }

    @Override
    public int update(Employee employee) {
        //to update Employee table data
        int effectCount = employeeDao.updateByPrimaryKey(employee);
        //to deal with Join table relationship
        //to delete all old role data with corresponding employee id
        employeeDao.deleteRoleByEmployeeId(employee.getId());
        //to update new data
        //to insert the join table relationship of the updated employee and all the updated roles that the employee had into the join table
        List<Role> roles = employee.getRoles();
        //if employee has roles
        if(roles != null){
            for (Role role : roles) {
                //to insert role id with corresponding employee id into join table
                employeeDao.handlerRelation(employee.getId(), role.getId());
            }
        }
        return effectCount;
    }

    @Override
    public int delete(Integer id) {
        //to delete all the role id with corresponding employee id in join table employee_role
        employeeDao.deleteRoleByEmployeeId(id);
        //then delete the specific employee with employee id
        return employeeDao.deleteByPrimaryKey(id);
    }

    @Override
    public Employee get(Integer id) {
        return employeeDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Employee> selectAll() {
        return employeeDao.selectAll();
    }

    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)employeeDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the employee showing in a particular page
            List<Employee> result = employeeDao.queryByCondition(qo);
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }

    //to query whether there exist a employee with the given name and password from user input at frontend
    @Override
    public Employee queryByLogin(String name, String password) {
        return employeeDao.queryByLogin(name, password);
    }
    //to update employee status into resigned so as to act as a logical deletion in database
    @Override
    public int updateState(Integer id) {
        return employeeDao.updateState(id);
    }
}
