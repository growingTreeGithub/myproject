package org.construction.wms.service;

import org.construction.wms.domain.Inventory;
import org.construction.wms.domain.Product;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface InventoryService {
    int save(Inventory inventory);
    int update(Inventory inventory);
    int delete(Integer id);
    Inventory get(Integer id);
    List<Inventory> selectAll();
    PageResult selectByCondition(QueryObject qo);
    void storeInWarehouse(Product product, BigDecimal amount, BigDecimal costPrice);
    //int checkInventoryStatus(Product product, BigDecimal amount);
    Map checkInventoryStatus(Product product, BigDecimal amount);
    void takeOutFromWarehouse(Product product, BigDecimal amount);
}
