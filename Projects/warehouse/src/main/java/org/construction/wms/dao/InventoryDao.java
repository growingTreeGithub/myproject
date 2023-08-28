package org.construction.wms.dao;

import org.construction.wms.domain.Inventory;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface InventoryDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Inventory inventory);

    Inventory selectByPrimaryKey(Integer id);

    List<Inventory> selectAll();

    int updateByPrimaryKey(Inventory inventory);
    //return the number of search result searching by keyword
    Long queryByConditionCount(QueryObject qo);
    //return the search result of the inventory showing in a particular page
    List<Inventory> queryByCondition(QueryObject qo);
    //return inventory which has the product with this product id
    Inventory queryByProductId(Integer productId);
    //return all the inventory which has this product id
    List<Inventory> queryInventoryByProductId(Integer productId);
}
