package org.construction.wms.dao;

import org.apache.ibatis.annotations.Param;
import org.construction.wms.domain.InBoundOrder;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface InBoundOrderDao {
    int deleteByPrimaryKey(Integer id);

    int insert(InBoundOrder inBoundOrder);

    InBoundOrder selectByPrimaryKey(Integer id);

    List<InBoundOrder> selectAll();

    int updateByPrimaryKey(InBoundOrder inBoundOrder);
    //return the number of search result searching by keyword
    Long queryByConditionCount(QueryObject qo);
    //return the search result of inBoundOrder showing in a particular page
    List<InBoundOrder> queryByCondition(QueryObject qo);
    /*to handle many-to-many relationship between inBoundOrder and inBoundOrderItem by inserting
     inBoundOrder id and inBoundOrderItem id into the join table inBoundOrder_items
    */
    int handlerRelation(@Param("inBoundOrderId")Integer inBoundOrderId, @Param("itemsId")Integer itemsId);
    //delete all InBoundOrderItems with corresponding inBoundOrderId in join table inBoundOrder_items
    int deleteItemsByInBoundOrderId(Integer inBoundOrderId);
    /*get the id of the new added inBoundOrder so that the inBoundOrder id can be added
    with its corresponding inBoundOrderItems id in join table inBoundOrder_items
    */
    int getMaxId();
    //to find the inBoundOrder that an inBoundOrderItem belongs to
    InBoundOrder queryInBoundOrderByInBoundOrderItemId(Integer itemsId);
}
