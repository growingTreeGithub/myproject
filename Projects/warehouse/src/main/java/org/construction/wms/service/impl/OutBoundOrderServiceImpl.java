package org.construction.wms.service.impl;

import org.construction.wms.dao.OutBoundOrderDao;
import org.construction.wms.dao.OutBoundOrderItemDao;
import org.construction.wms.domain.*;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.InventoryService;
import org.construction.wms.service.OutBoundOrderService;
import org.construction.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OutBoundOrderServiceImpl implements OutBoundOrderService {
    @Autowired
    private OutBoundOrderDao outBoundOrderDao;
    @Autowired
    private OutBoundOrderItemDao outBoundOrderItemDao;
    @Autowired
    private InventoryService inventoryService;
    @Override
    public int save(OutBoundOrder outBoundOrder) {
        //to record the user who have saved this record
        outBoundOrder.setInputUser((Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION));
        //set the default status of outBoundOrder to NOT_AUDIT
        outBoundOrder.setStatus(OutBoundOrder.NOT_AUDIT);
        //set the audit date to null as the outBoundOrder has not yet audited by any employee
        outBoundOrder.setAuditDate(null);
        //set the auditor to null as the outBoundOrder has not yet audited by any employee
        outBoundOrder.setAuditor(null);
        List<OutBoundOrderItem> items = outBoundOrder.getItems();
        //to get total amount and total price of an outBoundOrder
        dealWithItemDetails(outBoundOrder, items);
        //insert the outBoundOrder data into outBoundOrder table
        int effectCount = outBoundOrderDao.insert(outBoundOrder);
        //to find the id of the new added outBoundOrder
        int maxId = outBoundOrderDao.getMaxId();
        //to deal with join table relationship
        //set the newest id to outBoundOrder
        outBoundOrder.setId(maxId);
        //for each outBoundOrderItem, set its corresponding outBoundOrder which they belong to
        for(OutBoundOrderItem outBoundOrderItem: items){
            outBoundOrderItem.setOutBoundOrder(outBoundOrder);
        }
        //if items is not equal to null, it means an outBoundOrder has outBoundOrderItems under it.
        if(items != null){
            for (OutBoundOrderItem item : items) {
                //to add new outBoundOrderItem into outBoundOrderItem table
                outBoundOrderItemDao.insert(item);
                //to add each new added outBoundOrderItem with its corresponding outBoundOrder into join table outBoundOrder_items
                //maxId refers to the id of newest added outBoundOrderItem and outBoundOrder
                outBoundOrderDao.handlerRelation(maxId, outBoundOrderItemDao.getMaxId());
            }
        }
        return effectCount;
    }

    // to get totalAmount and totalPrice of outBoundOrder, and to set outBound Order item under corresponding outBound Order
    private void dealWithItemDetails(OutBoundOrder outBoundOrder, List<OutBoundOrderItem> items){
        //to set the total amount of outBoundOrder to zero and calculate the total amount again from zero
        outBoundOrder.setTotalAmount(BigDecimal.ZERO);
        //to set the total price of outBoundOrder to zero and calculate the total price again from zero
        outBoundOrder.setTotalPrice(BigDecimal.ZERO);
        for (OutBoundOrderItem outBoundOrderItem: items) {
            //to calculate the totalPrice of each item
            outBoundOrderItem.setTotalPrice(outBoundOrderItem.getAmount().multiply(outBoundOrderItem.getCostPrice().setScale(2, RoundingMode.HALF_UP)));
            //to calculate the totalAmount and totalPrice of outBound Order by adding each outBoundOrderItem amount and totalPrice to outBoundOrder totalAmount and totalPrice
            outBoundOrder.setTotalAmount(outBoundOrder.getTotalAmount().add(outBoundOrderItem.getAmount()));
            outBoundOrder.setTotalPrice(outBoundOrder.getTotalPrice().add(outBoundOrderItem.getTotalPrice()));
        }
    }

    @Override
    public int update(OutBoundOrder outBoundOrder) {
        //when the outBoundOrder is not audit, it can be updated. If an outBoundOrder is audited, it cannot be updated.
        if(outBoundOrder.getStatus() == OutBoundOrder.getNotAudit()){
            //to record the employee who has updated the entry
            outBoundOrder.setInputUser((Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION));
            List<OutBoundOrderItem> items = outBoundOrder.getItems();
            //to get total amount and total price of an outBoundOrder
            dealWithItemDetails(outBoundOrder, items);
            //to update outBoundOrder table data
            int effectCount = outBoundOrderDao.updateByPrimaryKey(outBoundOrder);
            //to deal with Join table relationship
            //to delete old item data with corresponding outBoundOrder id
            outBoundOrderDao.deleteItemsByOutBoundOrderId(outBoundOrder.getId());
            //to deal with join table relationship with new added outBound order Item
            //firstly, set the new added outBoundOrderItem with corresponding outBoundOrder as they didn't have any outBoundOrder data in it
            for(OutBoundOrderItem outBoundOrderItem: items){
                //if outBoundOrderItem.getId() == null, it means this item are new added into outBoundOrder List, therefore set the outBoundOrderItem with corresponding outBoundOrder
                if(outBoundOrderItem.getId() == null){
                    outBoundOrderItem.setOutBoundOrder(outBoundOrder);
                }
            }
            //to update new data
            //double check items is not equal to null, if items equals to null, no need to execute the following coding
            if(items != null){
                for (OutBoundOrderItem item : items) {
                    //if outBoundOrderItem.getId() == null, it means this item are new added into outBoundOrder List
                    if(item.getId() == null){
                        //for new added outBound Order Item
                        //add the new added outBound Order item into outBoundOrderItem table
                        outBoundOrderItemDao.insert(item);
                        /*get the id of the newest added outBoundOrder and outBoundOrderItem in order to
                        add both id into join table outBoundOrder_items
                        */
                        outBoundOrderDao.handlerRelation(outBoundOrder.getId(), outBoundOrderItemDao.getMaxId());
                    }else{
                        //for old already exist outBound Order Item, update the items data with it corresponding id
                        outBoundOrderItemDao.updateByPrimaryKey(item);
                        //added the old already exist outBound Order Item into the join table as it was deleted in the above
                        outBoundOrderDao.handlerRelation(outBoundOrder.getId(), item.getId());
                    }
                }
            }
            return effectCount;
        }
        return 0;
    }

    @Override
    public int delete(Integer id) {
        //delete all outBoundOrderItems with corresponding outBoundOrderId in join table outBoundOrder_items
        outBoundOrderDao.deleteItemsByOutBoundOrderId(id);
        //delete all the outBoundOrderItems in an outBoundOrder with its corresponding outBoundOrder id in outBoundOrderItem table
        outBoundOrderItemDao.deleteItemsByOutBoundOrderId(id);
        //delete the specific outBoundOrder with outBoundOrder id in table outBoundOrder
        return outBoundOrderDao.deleteByPrimaryKey(id);
    }

    @Override
    public OutBoundOrder get(Integer id) {
        return outBoundOrderDao.selectByPrimaryKey(id);
    }

    @Override
    public List<OutBoundOrder> selectAll() {
        return outBoundOrderDao.selectAll();
    }

    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)outBoundOrderDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the outBoundOrder showing in a particular page
            List<OutBoundOrder> result = outBoundOrderDao.queryByCondition(qo);
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }

    public Map audit(Integer id) {
        //this hashMap is used to store information need to return to frontend by ajax for interaction with the user
        Map<String, Object> result = new HashMap<>();
        //get the corresponding outBoundOrder
        OutBoundOrder outBoundOrder = outBoundOrderDao.selectByPrimaryKey(id);
        //if outBoundOrder is not audited, and now confirm to audit, execute the following coding
        if(outBoundOrder.getStatus() == OutBoundOrder.getNotAudit()){
            //to search all the outBoundOrderItems inside an outBoundOrder with its corresponding outBoundOrder id
            List<OutBoundOrderItem> checkItemList= outBoundOrderItemDao.queryOutBoundOrderItemByOutBoundOrderId(id);
            for(OutBoundOrderItem item:checkItemList){
                //to check whether there is any item on the outbound order does not have enough amount in the inventory
                result = inventoryService.checkInventoryStatus(item.getProduct(), item.getAmount());
                //for the case that there is no inventory or not enough inventory for the product in the warehouse
                if((int)(result.get("inventoryStatus")) == 0){
                    //return inventory status == 0 to show that audit is fail due to the insufficient amount in the inventory
                    //please refer to line 45 in outBoundOrder.js
                    return result;
                }
            }
            //for the case there is enough inventory amount for the product
            //set audit status for the outBoundOrder
            outBoundOrder.setStatus(OutBoundOrder.AUDIT);
            //set auditor and auditDate for the outBoundOrder
            outBoundOrder.setAuditor((Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION));
            outBoundOrder.setAuditDate(new Date());
            //to search all the outBoundOrderItems inside an outBoundOrder with its corresponding outBoundOrder id
            List<OutBoundOrderItem> itemList= outBoundOrderItemDao.queryOutBoundOrderItemByOutBoundOrderId(id);
            for(OutBoundOrderItem item:itemList){
                //using the product of the inBoundOrderItem to retrieve inventory information for the inBoundOrderItem
                //update the inventory amount and total price for the inventory confirmed to left the warehouse and sent to construction site
                inventoryService.takeOutFromWarehouse(item.getProduct(), item.getAmount());
            }
            //update outBoundOrder information
            outBoundOrderDao.updateByPrimaryKey(outBoundOrder);
            return result;
        }else{
            //prevent the user from directly enter hostname:portNumber/outBoundOrder/audit?id= outBoundOrder with id that is already audited
            result.put("inventoryStatus", 0);
            return result;
        }
    }
}
