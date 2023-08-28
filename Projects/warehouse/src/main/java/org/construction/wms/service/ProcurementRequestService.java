package org.construction.wms.service;

import org.construction.wms.domain.ProcurementRequest;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface ProcurementRequestService {
    int save(ProcurementRequest procurementRequest);
    int update(ProcurementRequest procurementRequest);
    int delete(Integer id);
    ProcurementRequest get(Integer id);
    List<ProcurementRequest> selectAll();
    PageResult selectByCondition(QueryObject qo);
    void purchase(Integer id);
}
