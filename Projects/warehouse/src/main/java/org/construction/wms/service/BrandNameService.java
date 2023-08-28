package org.construction.wms.service;

import org.construction.wms.domain.BrandName;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface BrandNameService {
    int save(BrandName brandName);
    int update(BrandName brandName);
    int delete(Integer id);
    BrandName get(Integer id);
    List<BrandName> selectAll();
    PageResult selectByCondition(QueryObject qo);
    BrandName queryBrandNameByProcurementRequestItemId(Integer id);
    BrandName queryBrandNameByInBoundOrderItemId(Integer id);
    BrandName queryBrandNameByOutBoundOrderItemId(Integer id);
}
