package org.construction.wms.service.impl;

import org.construction.wms.dao.DepartmentDao;
import org.construction.wms.dao.EmployeeDao;
import org.construction.wms.domain.Department;
import org.construction.wms.domain.Employee;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Override
    public int save(Department dept) {
        return departmentDao.insert(dept);
    }

    @Override
    public int update(Department dept) {
        return departmentDao.updateByPrimaryKey(dept);
    }

    @Override
    public int delete(Integer id) {
        //to query if there are any employee using department information
        List<Employee> employeeList = employeeDao.queryEmployeeByDeptId(id);
        /*if there is employee using department information, return 1 to inform user in frontend to delete all the
        employee that are using this department information so that the deletion of this department will not result
        in data loss in employee info module
        */
        if(employeeList.size() > 0){
            return 1;
        }else{
            //there aren't any employee using this department information so that the department information can be deleted directly
            departmentDao.deleteByPrimaryKey(id);
            return 0;
        }
    }

    @Override
    public Department get(Integer id) {
        return departmentDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Department> selectAll() {
        return departmentDao.selectAll();
    }

    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)departmentDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the department showing in a particular page
            List<Department> result = departmentDao.queryByCondition(qo);
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }
}
