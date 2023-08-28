package org.construction.wms.dao;

import org.apache.ibatis.annotations.Param;
import org.construction.wms.domain.Employee;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface EmployeeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee employee);

    Employee selectByPrimaryKey(Integer id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee employee);
    //return the number of search result searching by keyword
    Long queryByConditionCount(QueryObject qo);
    //return the search result of the employee showing in a particular page
    List<Employee> queryByCondition(QueryObject qo);
    //to find if there are any existing user registered in database by searching using name and password entered by user
    Employee queryByLogin(@Param("name")String name, @Param("password")String password);
    //to insert role id with corresponding employee id into join table
    int handlerRelation(@Param("eid")Integer eid, @Param("rid")Integer rid);
    //to delete all the role id with corresponding employee id in join table
    int deleteRoleByEmployeeId(Integer employeeId);
    //to get the employee id of the new added employee so that the role id with its corresponding new employee id can be added into join table
    int getMaxId();
    //to update the employee status into resigned so as to act as a logical deletion in database
    int updateState(Integer id);
    /*to query if there any employee using this department information,
     if yes, then delete all the employee using this department information so as to delete this department information
     if no, delete the department information directly as it will not result in data loss in other module
    * */
    List<Employee> queryEmployeeByDeptId(Integer deptId);

}
