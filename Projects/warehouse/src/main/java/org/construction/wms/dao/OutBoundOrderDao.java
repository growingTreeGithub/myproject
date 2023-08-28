package org.construction.wms.dao;

import org.apache.ibatis.annotations.Param;
import org.construction.wms.domain.OutBoundOrder;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface OutBoundOrderDao {
    int deleteByPrimaryKey(Integer id);

    int insert(OutBoundOrder outBoundOrder);

    OutBoundOrder selectByPrimaryKey(Integer id);

    List<OutBoundOrder> selectAll();

    int updateByPrimaryKey(OutBoundOrder outBoundOrder);
    //return the number of search result searching by keyword
    Long queryByConditionCount(QueryObject qo);
    //return the search result of the outBoundOrder showing in a particular page
    List<OutBoundOrder> queryByCondition(QueryObject qo);
    /*to handle many-to-many relationship between outBoundOrder and outBoundOrderItem by inserting
     outBoundOrder id and outBoundOrderItem id into the join table outBoundOrder_items
    */
    int handlerRelation(@Param("outBoundOrderId")Integer outBoundOrderId, @Param("itemsId")Integer itemsId);
    //delete outBoundOrderItems with corresponding outBoundOrderId in join table outBoundOrder_items
    int deleteItemsByOutBoundOrderId(Integer outBoundOrderId);
    /*get the id of the new added outBoundOrder so that the outBoundOrder id can be added
    with its corresponding outBoundOrderItems id in join table outBoundOrder_items
    */
    int getMaxId();
    //to find the outBoundOrder that an outBoundOrderItem belongs to
    OutBoundOrder queryOutBoundOrderByOutBoundOrderItemId(Integer itemsId);
    //to find if there are outBoundOrder has the below construction site
    List<OutBoundOrder> queryOutBoundOrderByConstructionSiteId(Integer constructionSiteId);
}
