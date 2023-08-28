package org.construction.wms.dao;

import org.construction.wms.domain.Supplier;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface SupplierDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Supplier supplier);

    Supplier selectByPrimaryKey(Integer id);

    List<Supplier> selectAll();

    int updateByPrimaryKey(Supplier supplier);
    //return the number of search result searching by keyword
    Long queryByConditionCount(QueryObject qo);
    //return the search result of the supplier showing in a particular page
    List<Supplier> queryByCondition(QueryObject qo);
}
