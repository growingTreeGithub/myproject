package org.construction.wms.service.impl;

import org.construction.wms.dao.BrandNameDao;
import org.construction.wms.dao.InventoryDao;
import org.construction.wms.dao.ProductDao;
import org.construction.wms.domain.BrandName;
import org.construction.wms.domain.Inventory;
import org.construction.wms.domain.Product;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private BrandNameDao brandNameDao;
    @Autowired
    private ProductDao productDao;
    @Override
    public int save(Inventory inventory) {
        return inventoryDao.insert(inventory);
    }

    @Override
    public int update(Inventory inventory) {
        return inventoryDao.updateByPrimaryKey(inventory);
    }

    @Override
    public int delete(Integer id) {
        return inventoryDao.deleteByPrimaryKey(id);
    }

    @Override
    public Inventory get(Integer id) {
        return inventoryDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Inventory> selectAll() {
        return inventoryDao.selectAll();
    }

    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)inventoryDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the inventory showing in a particular page
            List<Inventory> result = inventoryDao.queryByCondition(qo);
            for(Inventory inventory : result){
                /*to search brandName details of a product by searching brandName id with its corresponding product id,
                then find brandName details by its brandName id
                and set the brandName details into product so that the brandName of the product
                can be shown in the jsp on frontend
                * */
                BrandName brandName = brandNameDao.queryBrandNameByProductId(inventory.getProduct().getId());
                inventory.getProduct().setBrandName(brandName);
            }
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }

    @Override
    public void storeInWarehouse(Product product, BigDecimal amount, BigDecimal costPrice) {
        //to check whether there is inventory in warehouse
        Inventory inventory = inventoryDao.queryByProductId(product.getId());
        //to calculate totalPrice of the item that going to be stored in warehouse
        BigDecimal totalPrice = amount.multiply(costPrice).setScale(2, RoundingMode.HALF_UP);
        if(inventory != null){
            //if the item exist in warehouse, update warehouse Inventory amount, Inventory totalPrice and average Price
            inventory.setInventoryAmount(inventory.getInventoryAmount().add(amount));
            inventory.setTotalPrice(inventory.getTotalPrice().add(totalPrice));
            inventory.setAveragePrice(inventory.getTotalPrice().divide(inventory.getInventoryAmount(),2, RoundingMode.HALF_UP));
            inventoryDao.updateByPrimaryKey(inventory);
        }else{
            //if item does not exist in warehouse, create a new inventory and make the item as inventory stored in warehouse
            inventory = new Inventory();
            //As there is no existing item in warehouse, the average price must be the costPrice of item
            //because the average price is based on the new total costPrice divide by the amount of the item
            inventory.setAveragePrice(costPrice);
            inventory.setInventoryAmount(amount);
            inventory.setTotalPrice(totalPrice);
            inventory.setProduct(product);
            inventoryDao.insert(inventory);
        }
    }

    public Map checkInventoryStatus(Product product, BigDecimal amount){
        //this hashMap is used to store information need to return to frontend by ajax for interaction with the user
        Map<String, Object> result = new HashMap<>();
        //to check whether there is inventory in warehouse
        Inventory inventory = inventoryDao.queryByProductId(product.getId());
        //to search product Name
        Product productDetail = productDao.selectByPrimaryKey(product.getId());
        if(inventory == null){
            //if there is no inventory left for that product
            //these information put in hashMap will be used in outBoundOrder.js to give different response
            result.put("inventoryStatus", 0);
            result.put("productName",productDetail.getName());
            result.put("inventoryAmount",0);
            return result;
        }else if(inventory.getInventoryAmount().compareTo(amount) < 0){
            //there is inventory amount left for that product but no enough for the outbound order
            //these information put in hashMap will be used in outBoundOrder.js to give different response
            result.put("inventoryStatus", 0);
            result.put("productName",productDetail.getName());
            result.put("inventoryAmount",inventory.getInventoryAmount());
            return result;
        }else{
            //there is enough inventory amount for the item on the outbound order
            //these information put in hashMap will be used in outBoundOrder.js to give different response
            result.put("inventoryStatus", 1);
            return result;
        }
    }

    @Override
    public void takeOutFromWarehouse(Product product, BigDecimal amount) {
        //to check whether there is inventory in warehouse
        //using the product of the inBoundOrderItem to retrieve inventory information for the inBoundOrderItem
        Inventory inventory = inventoryDao.queryByProductId(product.getId());
        /*if(inventory == null || inventory.getInventoryAmount().compareTo(amount) < 0){
            throw new RuntimeException(product.getName() + " inventory amount is not enough, there is only "+inventory.getInventoryAmount() + " left!");
        }*/
        //subtract the inventory amount which has been left the warehouse and sent to construction site
        inventory.setInventoryAmount(inventory.getInventoryAmount().subtract(amount));
        //calculate the new total price for the inventory.The average price of the inventory will not be changed.
        inventory.setTotalPrice(inventory.getInventoryAmount().multiply(inventory.getAveragePrice()));
        //update inventory information
        inventoryDao.updateByPrimaryKey(inventory);
    }
}
