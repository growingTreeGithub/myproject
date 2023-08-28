package org.construction.wms.dao;

import org.construction.wms.domain.InBoundOrderItem;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface InBoundOrderItemDao {
    int deleteByPrimaryKey(Integer id);

    int insert(InBoundOrderItem inBoundOrderItem);

    InBoundOrderItem selectByPrimaryKey(Integer id);

    List<InBoundOrderItem> selectAll();

    int updateByPrimaryKey(InBoundOrderItem inBoundOrderItem);
    //return the number of search result searching by keyword
    Long queryByConditionCount(QueryObject qo);
    //return the search result of inBoundOrderItem showing in a particular page
    List<InBoundOrderItem> queryByCondition(QueryObject qo);
    //to search all the inBoundOrderItems inside an inBoundOrder with its corresponding inBoundOrder id
    List<InBoundOrderItem> queryInBoundOrderItemByInBoundOrderId(Integer inBoundOrderId);
    //to save each new added inBoundOrderItem with its corresponding inBoundOrder into join table inBoundOrder_items
    int getMaxId();
    //delete an inBoundOrderItem in an inBoundOrder by inBoundOrderItem id
    int deleteItemsByInBoundOrderItemId(Integer inBoundOrderItemId);
    //delete all the inBoundOrderItems in an inBoundOrder with its corresponding inBoundOrder id in table inBoundOrderItem
    int deleteItemsByInBoundOrderId(Integer inBoundOrderId);
    /*to query all the inBoundOrderItem which has this product. if there is any inBoundOrderItem using this
    product, this product cannot be deleted since it will lead to data loss.
    */
    List<InBoundOrderItem> queryInBoundOrderItemByProductId(Integer productId);
}
