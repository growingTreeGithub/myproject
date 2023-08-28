package org.construction.wms.service;

import org.construction.wms.domain.ProcurementRequestItem;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface ProcurementRequestItemService {
    int save(ProcurementRequestItem procurementRequestItem);
    int update(ProcurementRequestItem procurementRequestItem);
    int delete(Integer id);
    ProcurementRequestItem get(Integer id);
    List<ProcurementRequestItem> selectAll();
    PageResult selectByCondition(QueryObject qo);
    List<ProcurementRequestItem> queryProcurementRequestItemByProcurementRequestId(Integer id);
}
