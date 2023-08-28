package org.construction.wms.service;

import org.construction.wms.domain.InBoundOrderItem;
import org.construction.wms.domain.ProcurementRequestItem;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface InBoundOrderItemService {
    int save(InBoundOrderItem inBoundOrderItem);
    int update(InBoundOrderItem inBoundOrderItem);
    int delete(Integer id);
    InBoundOrderItem get(Integer id);
    List<InBoundOrderItem> selectAll();
    PageResult selectByCondition(QueryObject qo);
    List<InBoundOrderItem> queryInBoundOrderItemByInBoundOrderId(Integer id);
}
