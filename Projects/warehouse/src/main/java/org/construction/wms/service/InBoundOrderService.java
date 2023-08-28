package org.construction.wms.service;

import org.construction.wms.domain.InBoundOrder;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface InBoundOrderService {
    int save(InBoundOrder inBoundOrder);
    int update(InBoundOrder inBoundOrder);
    int delete(Integer id);
    InBoundOrder get(Integer id);
    List<InBoundOrder> selectAll();
    PageResult selectByCondition(QueryObject qo);
    void audit(Integer id);
}
