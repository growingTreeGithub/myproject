package org.construction.wms.dao;

import org.apache.ibatis.annotations.Param;
import org.construction.wms.domain.ProcurementRequest;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface ProcurementRequestDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ProcurementRequest procurementRequest);

    ProcurementRequest selectByPrimaryKey(Integer id);

    List<ProcurementRequest> selectAll();

    int updateByPrimaryKey(ProcurementRequest procurementRequest);
    //return the number of search result searching by keyword
    Long queryByConditionCount(QueryObject qo);
    //return the list of search result of the procurementRequest showing in a particular page
    List<ProcurementRequest> queryByCondition(QueryObject qo);
    /*to handle many-to-many relationship between procurementRequest and procurementRequestItem by inserting
     procurementRequest id and procurementRequestItem id into the join table procurementRequest_items
    */
    int handlerRelation(@Param("procurementRequestId")Integer procurementRequestId, @Param("itemsId")Integer itemsId);
    //delete procurementRequestItems with corresponding procurementRequestId in join table procurementRequest_items
    int deleteItemsByProcurementRequestId(Integer procurementRequestId);
    /*get the id of the new added procurementRequest so that the procurementRequest id can be added
    with its corresponding procurementRequestItems id in join table procurementRequest_items
    */
    int getMaxId();
    /*to find the procurementRequest that an procurementRequestItem belongs to
    in order to deal with the total price and total amount of the procurementRequest
    after the deletion of the procurementRequestItem
     */
    ProcurementRequest queryProcurementRequestByProcurementRequestItemId(Integer itemsId);
    //return all the procurementRequest that has this supplier
    List<ProcurementRequest> queryProcurementRequestBySupplierId(Integer supplierId);
}
