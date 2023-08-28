package org.construction.wms.service.impl;

import org.construction.wms.dao.ProcurementRequestDao;
import org.construction.wms.dao.ProcurementRequestItemDao;
import org.construction.wms.domain.Employee;
import org.construction.wms.domain.ProcurementRequest;
import org.construction.wms.domain.ProcurementRequestItem;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.ProcurementRequestService;
import org.construction.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class ProcurementRequestServiceImpl implements ProcurementRequestService {
    @Autowired
    private ProcurementRequestDao procurementRequestDao;
    @Autowired
    private ProcurementRequestItemDao procurementRequestItemDao;
    @Override
    public int save(ProcurementRequest procurementRequest) {
        //to record the user who have saved this record
        procurementRequest.setInputUser((Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION));
        //set the default status of procurement request to NOT_PURCHASED
        procurementRequest.setStatus(ProcurementRequest.NOT_PURCHASED);
        //set the finish date to null as the product on the procurement request has not yet purchased by any merchandiser
        procurementRequest.setFinishDate(null);
        //set the merchandiser to null as the product on the procurement request has not yet purchased by any merchandiser
        procurementRequest.setMerchandiser(null);
        List<ProcurementRequestItem> items = procurementRequest.getItems();
        // to get totalAmount and totalPrice of procurementRequest
        dealWithItemDetails(procurementRequest, items);
        //insert the procurement request data into procurement request table
        int effectCount = procurementRequestDao.insert(procurementRequest);
        //get the id of the new added procurementRequest
        int maxId = procurementRequestDao.getMaxId();
        //to deal with join table relationship
        //set the newest id to procurement request
        procurementRequest.setId(maxId);
        //for each procurement request item, set its corresponding procurement request which they belong to
        for(ProcurementRequestItem procurementRequestItem: items){
            procurementRequestItem.setProcurementRequest(procurementRequest);
        }
        //if items is not equal to null, it means an procurement request has procurement request items under it.
        if(items != null){
            for (ProcurementRequestItem item : items) {
                //add new procurementRequestItem into procurementRequestItem table
                procurementRequestItemDao.insert(item);
                /*get the id of the newest added procurementRequest and procurementRequestItem in order to
                add both id into join table procurementRequest_items
                */
                procurementRequestDao.handlerRelation(maxId, procurementRequestItemDao.getMaxId());
            }
        }
        return effectCount;
    }

    // to get totalAmount and totalPrice of procurementRequest, and to set procurement Request item under corresponding procurementRequest
    private void dealWithItemDetails(ProcurementRequest procurementRequest, List<ProcurementRequestItem> items){
        //to set the total amount of procurement request to zero and calculate the total amount again from zero
        procurementRequest.setTotalAmount(BigDecimal.ZERO);
        //to set the total price of procurement request to zero and calculate the total price again from zero
        procurementRequest.setTotalPrice(BigDecimal.ZERO);
        for (ProcurementRequestItem procurementRequestItem: items) {
            //to calculate the totalPrice of each item
            procurementRequestItem.setTotalPrice(procurementRequestItem.getAmount().multiply(procurementRequestItem.getCostPrice().setScale(2, RoundingMode.HALF_UP)));
            //to calculate the totalAmount and totalPrice of procurement request by adding each procurement request item amount and totalPrice to procurement request totalAmount and totalPrice
            procurementRequest.setTotalAmount(procurementRequest.getTotalAmount().add(procurementRequestItem.getAmount()));
            procurementRequest.setTotalPrice(procurementRequest.getTotalPrice().add(procurementRequestItem.getTotalPrice()));
        }
    }




    @Override
    public int update(ProcurementRequest procurementRequest) {
        //when the product on the procurement request is not purchased, it can be updated.
        // If the product on the procurement request is purchased, it cannot be updated.
        if(procurementRequest.getStatus() == ProcurementRequest.getNotPurchased()){
            //to record the employee who has updated the entry
            procurementRequest.setInputUser((Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION));
            List<ProcurementRequestItem> items = procurementRequest.getItems();
            // to get totalAmount and totalPrice of procurementRequest
            dealWithItemDetails(procurementRequest, items);
            //to update ProcurementRequest table data
            int effectCount = procurementRequestDao.updateByPrimaryKey(procurementRequest);
            //to deal with Join table relationship
            //to delete old item data with corresponding ProcurementRequest id
            procurementRequestDao.deleteItemsByProcurementRequestId(procurementRequest.getId());
            //to deal with join table relationship with new added procurementRequestItem
            //firstly, set the new added procurementRequestItem with corresponding procurementRequest as they didn't have any procurementRequest data in it
            for(ProcurementRequestItem procurementRequestItem: items){
                //if procurementRequestItem.getId() == null, it means this item are new added into procurementRequest List, therefore set the procurementRequestItem with corresponding procurementRequest
                if(procurementRequestItem.getId() == null){
                    procurementRequestItem.setProcurementRequest(procurementRequest);
                }
            }
            //to update new data
            //double check items is not equal to null, if items equals to null, no need to execute the following coding
            if(items != null){
                for (ProcurementRequestItem item : items) {
                    //if procurementRequestItem.getId() == null, it means this item are new added into procurementRequest List
                    if(item.getId() == null){
                        //for new added procurement Request Item
                        //add the new added procurement Request item into procurementRequestItem table
                        procurementRequestItemDao.insert(item);
                        /*to handle many-to-many relationship between procurementRequest and procurementRequestItem by inserting
                        procurementRequest id and procurementRequestItem id into the join table procurementRequest_items
                        */
                        procurementRequestDao.handlerRelation(procurementRequest.getId(), procurementRequestItemDao.getMaxId());
                    }else{
                        //for old already exist procurement Request Item, update the items data with it corresponding id
                        procurementRequestItemDao.updateByPrimaryKey(item);
                        //added the old already exist procurement Request Item into the join table as it was deleted in the above
                        procurementRequestDao.handlerRelation(procurementRequest.getId(), item.getId());
                    }
                }
            }
            return effectCount;
        }
        return 0;
    }

    @Override
    public int delete(Integer id) {
        //delete procurementRequestItems with corresponding procurementRequestId in join table procurementRequest_items
        procurementRequestDao.deleteItemsByProcurementRequestId(id);
        //delete all the procurementRequestItems in an procurementRequest with its corresponding procurementRequest id in procurementRequestItem table
        procurementRequestItemDao.deleteItemsByProcurementRequestId(id);
        //delete the specific procurement request with procurement request id in table procurementRequest
        return procurementRequestDao.deleteByPrimaryKey(id);
    }

    @Override
    public ProcurementRequest get(Integer id) {
        return procurementRequestDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ProcurementRequest> selectAll() {
        return procurementRequestDao.selectAll();
    }

    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)procurementRequestDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the procurement request showing in a particular page
            List<ProcurementRequest> result = procurementRequestDao.queryByCondition(qo);
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }

    @Override
    public void purchase(Integer id) {
        //get the corresponding procurement request with its id
        ProcurementRequest procurementRequest = procurementRequestDao.selectByPrimaryKey(id);
        //if product on procurement request is not purchased, and now confirm the product has been purchased, execute the following coding
        if(procurementRequest.getStatus() == ProcurementRequest.getNotPurchased()){
            //set already purchased status for the procurement request
            procurementRequest.setStatus(ProcurementRequest.getPURCHASED());
            //set merchandiser and finishDate for the procurement request
            procurementRequest.setMerchandiser((Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION));
            procurementRequest.setFinishDate(new Date());
            //update procurement request information
            procurementRequestDao.updateByPrimaryKey(procurementRequest);
        }
    }


}
