package org.construction.wms.dao;

import org.construction.wms.domain.Department;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface DepartmentDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Department department);

    Department selectByPrimaryKey(Integer id);

    List<Department> selectAll();

    int updateByPrimaryKey(Department department);
    //return the number of search result searching by keyword
    Long queryByConditionCount(QueryObject qo);
    //return the search result of department showing in a particular page
    List<Department> queryByCondition(QueryObject qo);
}
