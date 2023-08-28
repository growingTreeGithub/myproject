package org.construction.wms.dao;

import org.construction.wms.domain.ProcurementRequestItem;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface ProcurementRequestItemDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ProcurementRequestItem procurementRequestItem);

    ProcurementRequestItem selectByPrimaryKey(Integer id);

    List<ProcurementRequestItem> selectAll();

    int updateByPrimaryKey(ProcurementRequestItem procurementRequestItem);
    //return the number of procurementRequestItem
    Long queryByConditionCount(QueryObject qo);
    //return a list of the procurementRequestItem showing in a particular page
    List<ProcurementRequestItem> queryByCondition(QueryObject qo);
    //to search all the procurementRequestItems inside an procurementRequest with its corresponding procurementRequest id
    List<ProcurementRequestItem> queryProcurementRequestItemByProcurementRequestId(Integer procurementRequestId);
    /* get the id of the new added procurementRequestItem in order to
      save each new added procurementRequestItem with its corresponding procurementRequest
      into join table procurementRequest_items
    * */
    int getMaxId();
    //delete an procurementRequestItem in an procurementRequest by procurementRequestItem id
    int deleteItemsByProcurementRequestItemId(Integer procurementRequestItemId);
    //delete all the procurementRequestItems in an procurementRequest with its corresponding procurementRequest id in procurementRequestItem table
    int deleteItemsByProcurementRequestId(Integer procurementRequestId);
    /*to query all the procurementRequestItem which has this product. if there is any procurementRequestItem using this
    product, this product cannot be deleted since it will lead to data loss.
    */
    List<ProcurementRequestItem> queryProcurementRequestItemByProductId(Integer productId);
}
