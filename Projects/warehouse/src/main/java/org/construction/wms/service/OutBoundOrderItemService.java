package org.construction.wms.service;

import org.construction.wms.domain.OutBoundOrderItem;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface OutBoundOrderItemService {
    int save(OutBoundOrderItem outBoundOrderItem);
    int update(OutBoundOrderItem outBoundOrderItem);
    int delete(Integer id);
    OutBoundOrderItem get(Integer id);
    List<OutBoundOrderItem> selectAll();
    PageResult selectByCondition(QueryObject qo);
    List<OutBoundOrderItem> queryOutBoundOrderItemByOutBoundOrderId(Integer id);
}
