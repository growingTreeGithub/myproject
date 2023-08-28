package org.construction.wms.dao;

import org.construction.wms.domain.OutBoundOrderItem;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface OutBoundOrderItemDao {
    int deleteByPrimaryKey(Integer id);

    int insert(OutBoundOrderItem outBoundOrderItem);

    OutBoundOrderItem selectByPrimaryKey(Integer id);

    List<OutBoundOrderItem> selectAll();

    int updateByPrimaryKey(OutBoundOrderItem outBoundOrderItem);
    //return the number of outBoundOrderItem
    Long queryByConditionCount(QueryObject qo);
    //return a list of the outBoundOrderItems showing in a particular page
    List<OutBoundOrderItem> queryByCondition(QueryObject qo);
    //to search all the outBoundOrderItems inside an outBoundOrder with its corresponding outBoundOrder id
    List<OutBoundOrderItem> queryOutBoundOrderItemByOutBoundOrderId(Integer outBoundOrderId);
    /* get the id of the new added outBoundOrderItems in order to
      save each new added outBoundOrderItem with its corresponding outBoundOrder
      into join table outBoundOrder_items
    * */
    int getMaxId();
    //delete an outBoundOrderItem in an outBoundOrder by outBoundOrderItem id
    int deleteItemsByOutBoundOrderItemId(Integer outBoundOrderItemId);
    //delete all the outBoundOrderItems in an outBoundOrder with its corresponding outBoundOrder id in outBoundOrderItem table
    int deleteItemsByOutBoundOrderId(Integer outBoundOrderId);
    /*to query all the outBoundOrderItem which has this product. if there is any outBoundOrderItem using this
    product, this product cannot be deleted since it will lead to data loss.
    */
    List<OutBoundOrderItem> queryOutBoundOrderItemByProductId(Integer productId);
}
