package org.construction.wms.service;

import org.construction.wms.domain.Supplier;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface SupplierService {
    int save(Supplier supplier);
    int update(Supplier supplier);
    int delete(Integer id);
    Supplier get(Integer id);
    List<Supplier> selectAll();
    PageResult selectByCondition(QueryObject qo);
}
