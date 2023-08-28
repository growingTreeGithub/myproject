package org.construction.wms.service.impl;

import org.construction.wms.dao.*;
import org.construction.wms.domain.*;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private InBoundOrderItemDao inBoundOrderItemDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private OutBoundOrderItemDao outBoundOrderItemDao;
    @Autowired
    private ProcurementRequestItemDao procurementRequestItemDao;

    @Override
    public int save(Product product) {
        return productDao.insert(product);
    }

    @Override
    public int update(Product product) {
        return productDao.updateByPrimaryKey(product);
    }

    @Override
    public int delete(Integer id) {
        /*to query all the inBoundOrderItem which has this product. if there is any inBoundOrderItem using this
        product, this product cannot be deleted since it will lead to data loss.
        */
        List<InBoundOrderItem> inBoundOrderItemList = inBoundOrderItemDao.queryInBoundOrderItemByProductId(id);
        if(inBoundOrderItemList.size() > 0){
            /*return 1 by ajax to tell the user to delete all the data related to this product so that
            the user can delete the product successfully.(please refer to line 52 in product.js file )
            */
            return 1;
        }
        /*to query all the inventory which has this product. if there is any inventory using this
        product, this product cannot be deleted since it will lead to data loss.
        */
        List<Inventory> inventoryList = inventoryDao.queryInventoryByProductId(id);
        if(inventoryList.size() > 0){
            /*return 1 by ajax to tell the user to delete all the data related to this product so that
            the user can delete the product successfully.(please refer to line 52 in product.js file )
            */
            return 1;
        }
        /*to query all the outBoundOrderItem which has this product. if there is any outBoundOrderItem using this
        product, this product cannot be deleted since it will lead to data loss.
        */
        List<OutBoundOrderItem>  outBoundOrderItemList = outBoundOrderItemDao.queryOutBoundOrderItemByProductId(id);
        if(outBoundOrderItemList.size() > 0){
            /*return 1 by ajax to tell the user to delete all the data related to this product so that
            the user can delete the product successfully.(please refer to line 52 in product.js file )
            */
            return 1;
        }
        /*to query all the procurementRequestItem which has this product. if there is any procurementRequestItem using this
        product, this product cannot be deleted since it will lead to data loss.
        */
        List<ProcurementRequestItem> procurementRequestItemList = procurementRequestItemDao.queryProcurementRequestItemByProductId(id);
        if(procurementRequestItemList.size() > 0){
            /*return 1 by ajax to tell the user to delete all the data related to this product so that
            the user can delete the product successfully.(please refer to line 52 in product.js file )
            */
            return 1;
        }else{
            productDao.deleteByPrimaryKey(id);
            //return 0 by ajax to tell the user the product has been deleted successfully
            return 0;
        }
    }

    @Override
    public Product get(Integer id) {
        return productDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Product> selectAll() {
        return productDao.selectAll();
    }

    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)productDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the product showing in a particular page
            List<Product> result = productDao.queryByCondition(qo);
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }
}
