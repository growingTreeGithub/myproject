package org.construction.wms.service.impl;

import org.construction.wms.dao.InBoundOrderDao;
import org.construction.wms.dao.InBoundOrderItemDao;
import org.construction.wms.domain.*;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.InBoundOrderService;
import org.construction.wms.service.InventoryService;
import org.construction.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class InBoundOrderServiceImpl implements InBoundOrderService {
    @Autowired
    private InBoundOrderDao inBoundOrderDao;
    @Autowired
    private InBoundOrderItemDao inBoundOrderItemDao;
    @Autowired
    private InventoryService inventoryService;
    @Override
    public int save(InBoundOrder inBoundOrder) {
        //to record the user who have saved this record
        inBoundOrder.setInputUser((Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION));
        //set the default status of inBoundOrder to NOT_AUDIT
        inBoundOrder.setStatus(InBoundOrder.NOT_AUDIT);
        //set the audit date to null as the inBoundOrder has not yet audited by any employee
        inBoundOrder.setAuditDate(null);
        //set the auditor to null as the inBoundOrder has not yet audited by any employee
        inBoundOrder.setAuditor(null);
        List<InBoundOrderItem> items = inBoundOrder.getItems();
        //to get total amount and total price of an inBoundOrder
        dealWithItemDetails(inBoundOrder, items);
        //insert the inBoundOrder data into inBoundOrder table
        int effectCount = inBoundOrderDao.insert(inBoundOrder);
        //to find the id of the new added inBoundOrder
        int maxId = inBoundOrderDao.getMaxId();
        //to deal with join table relationship
        //set the newest id to inBoundOrder
        inBoundOrder.setId(maxId);
        //for each inBoundOrderItem, set its corresponding inBoundOrder which they belong to
        for(InBoundOrderItem inBoundOrderItem: items){
            inBoundOrderItem.setInBoundOrder(inBoundOrder);
        }
        //if items is not equal to null, it means an inBoundOrder has inBoundOrderItems under it.
        if(items != null){
            for (InBoundOrderItem item : items) {
                //add new inBoundOrderItem to inBoundOrderItem table
                inBoundOrderItemDao.insert(item);
                /*get the id of the newest added inBoundOrder and inBoundOrderItem in order to
                add both id into join table inBoundOrder_items
                */
                inBoundOrderDao.handlerRelation(maxId, inBoundOrderItemDao.getMaxId());
            }
        }
        return effectCount;
    }

    // to get totalAmount and totalPrice of inBoundOrder, and to set inBound Order item under corresponding inBound Order
    private void dealWithItemDetails(InBoundOrder inBoundOrder, List<InBoundOrderItem> items){
        //to set the total amount of inBoundOrder to zero and calculate the total amount again from zero
        inBoundOrder.setTotalAmount(BigDecimal.ZERO);
        //to set the total price of inBoundOrder to zero and calculate the total price again from zero
        inBoundOrder.setTotalPrice(BigDecimal.ZERO);
        for (InBoundOrderItem inBoundOrderItem: items) {
            //to calculate the totalPrice of each item
            inBoundOrderItem.setTotalPrice(inBoundOrderItem.getAmount().multiply(inBoundOrderItem.getCostPrice().setScale(2, RoundingMode.HALF_UP)));
            //to calculate the totalAmount and totalPrice of inBound Order by adding each inBoundOrderItem amount and totalPrice to inBoundOrder totalAmount and totalPrice
            inBoundOrder.setTotalAmount(inBoundOrder.getTotalAmount().add(inBoundOrderItem.getAmount()));
            inBoundOrder.setTotalPrice(inBoundOrder.getTotalPrice().add(inBoundOrderItem.getTotalPrice()));
        }
    }

    @Override
    public int update(InBoundOrder inBoundOrder) {
        //when the inBoundOrder is not audit, it can be updated. If an inBoundOrder is audited, it cannot be updated.
        if(inBoundOrder.getStatus() == InBoundOrder.getNotAudit()){
            //to record the employee who has updated the entry
            inBoundOrder.setInputUser((Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION));
            List<InBoundOrderItem> items = inBoundOrder.getItems();
            //to get total amount and total price of an inBoundOrder
            dealWithItemDetails(inBoundOrder, items);
            //to update inBoundOrder table data
            int effectCount = inBoundOrderDao.updateByPrimaryKey(inBoundOrder);
            //to deal with Join table relationship
            //to delete all old item data with corresponding inBoundOrder id
            inBoundOrderDao.deleteItemsByInBoundOrderId(inBoundOrder.getId());
            //to deal with join table relationship with new added inBound order item
            //firstly, set the new added inBoundOrderItem with corresponding inBoundOrder as they didn't have any inBoundOrder data in it
            for(InBoundOrderItem inBoundOrderItem: items){
                //if inBoundOrderItem.getId() == null, it means this item are new added into inBoundOrder List, therefore set the inBoundOrderItem with corresponding inBoundOrder
                if(inBoundOrderItem.getId() == null){
                    inBoundOrderItem.setInBoundOrder(inBoundOrder);
                }
            }
            //to update new data
            //double check items is not equal to null, if items equals to null, no need to execute the following coding
            if(items != null){
                for (InBoundOrderItem item : items) {
                    //if inBoundOrderItem.getId() == null, it means this item are new added into inBoundOrder List
                    if(item.getId() == null){
                        //for new added inBound Order Item
                        //add the new added inBound Order item into inBoundOrderItem table
                        inBoundOrderItemDao.insert(item);
                        /*get the id of the newest added inBoundOrder and inBoundOrderItem in order to
                        add both id into join table inBoundOrder_items
                        */
                        inBoundOrderDao.handlerRelation(inBoundOrder.getId(), inBoundOrderItemDao.getMaxId());
                    }else{
                        //for old already exist inBound Order Item, update the items data with it corresponding id
                        inBoundOrderItemDao.updateByPrimaryKey(item);
                        //added the old already exist inBound Order Item into the join table as it was deleted in the above
                        inBoundOrderDao.handlerRelation(inBoundOrder.getId(), item.getId());
                    }
                }
            }
            return effectCount;
        }
        return 0;
    }

    @Override
    public int delete(Integer id) {
        //delete all InBoundOrderItems with corresponding inBoundOrderId in join table inBoundOrder_items
        inBoundOrderDao.deleteItemsByInBoundOrderId(id);
        //delete all the inBoundOrderItems in an inBoundOrder with its corresponding inBoundOrder id in table inBoundOrderItem
        inBoundOrderItemDao.deleteItemsByInBoundOrderId(id);
        //delete the specific inBoundOrder with inBoundOrder id in table inBoundOrder
        return inBoundOrderDao.deleteByPrimaryKey(id);
    }

    @Override
    public InBoundOrder get(Integer id) {
        return inBoundOrderDao.selectByPrimaryKey(id);
    }

    @Override
    public List<InBoundOrder> selectAll() {
       return inBoundOrderDao.selectAll();
    }

    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)inBoundOrderDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the inBoundOrder showing in a particular page
            List<InBoundOrder> result = inBoundOrderDao.queryByCondition(qo);
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }

    @Override
    public void audit(Integer id) {
        //get the corresponding inBoundOrder
        InBoundOrder inBoundOrder = inBoundOrderDao.selectByPrimaryKey(id);
        //if inBoundOrder is not audited, and now confirm to audit, execute the following coding
        if(inBoundOrder.getStatus() == InBoundOrder.getNotAudit()){
            //set already audit status for the inBoundOrder
            inBoundOrder.setStatus(InBoundOrder.AUDIT);
            //set auditor and auditDate for the inBoundOrder
            inBoundOrder.setAuditor((Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION));
            inBoundOrder.setAuditDate(new Date());
            //to search all the inBoundOrderItems inside an inBoundOrder with its corresponding inBoundOrder id
            List<InBoundOrderItem> itemList= inBoundOrderItemDao.queryInBoundOrderItemByInBoundOrderId(id);
            for(InBoundOrderItem item:itemList){
                //the inBoundOrder is audited means that the inBoundOrderItems are now arrived and stored in the warehouse.
                //the inBoundOrderItems are now become inventory and calculate the inventory new average price, total price and inventory amount
                inventoryService.storeInWarehouse(item.getProduct(), item.getAmount(), item.getCostPrice());
            }
            //update the corresponding inBoundOrder
            inBoundOrderDao.updateByPrimaryKey(inBoundOrder);
        }
    }
}
