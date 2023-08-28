package org.construction.wms.service;

import org.construction.wms.domain.OutBoundOrder;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.util.List;
import java.util.Map;

public interface OutBoundOrderService {
    int save(OutBoundOrder outBoundOrder);
    int update(OutBoundOrder outBoundOrder);
    int delete(Integer id);
    OutBoundOrder get(Integer id);
    List<OutBoundOrder> selectAll();
    PageResult selectByCondition(QueryObject qo);
    /*int audit(Integer id);*/
    Map audit(Integer id);
}
