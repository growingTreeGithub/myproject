package org.construction.wms.service.impl;

import org.construction.wms.dao.ProcurementRequestDao;
import org.construction.wms.dao.ProcurementRequestItemDao;
import org.construction.wms.domain.Employee;
import org.construction.wms.domain.ProcurementRequest;
import org.construction.wms.domain.ProcurementRequestItem;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.ProcurementRequestItemService;
import org.construction.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ProcurementRequestItemServiceImpl implements ProcurementRequestItemService {
    @Autowired
    private ProcurementRequestItemDao procurementRequestItemDao;
    @Autowired
    private ProcurementRequestDao procurementRequestDao;

    @Override
    public int save(ProcurementRequestItem procurementRequestItem) {
        return procurementRequestItemDao.insert(procurementRequestItem);
    }

    @Override
    public int update(ProcurementRequestItem procurementRequestItem) {
        return procurementRequestItemDao.updateByPrimaryKey(procurementRequestItem);
    }

    @Override
    public int delete(Integer id) {
        /*to find the procurementRequest that an procurementRequestItem belongs to
          in order to deal with the total price and total amount of the procurementRequest
          after the deletion of the procurementRequestItem
        */
        ProcurementRequest procurementRequest = procurementRequestDao.queryProcurementRequestByProcurementRequestItemId(id);
        //record the user who have delete the record and get the user from session and put it into procurementRequest
        procurementRequest.setInputUser((Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION));
        //delete an procurementRequestItem in an procurementRequest by procurementRequestItem id
        procurementRequestItemDao.deleteItemsByProcurementRequestItemId(id);
        //to search all the procurementRequestItems inside an procurementRequest with its corresponding procurementRequest id
        List<ProcurementRequestItem> items = procurementRequestItemDao.queryProcurementRequestItemByProcurementRequestId(procurementRequest.getId());
        // to get totalAmount and totalPrice of procurement request, and to set procurement request item under corresponding procurement request
        dealWithItemDetails(procurementRequest, items);
        //update the latest data for the procurement request to procurement request table
        procurementRequestDao.updateByPrimaryKey(procurementRequest);
        //delete the specific procurement request item in procurement request item table
        return procurementRequestItemDao.deleteByPrimaryKey(id);
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
            //to calculate the totalAmount and totalPrice of procurementRequest by adding each procurement request item amount and totalPrice to procurement request totalAmount and totalPrice
            procurementRequest.setTotalAmount(procurementRequest.getTotalAmount().add(procurementRequestItem.getAmount()));
            procurementRequest.setTotalPrice(procurementRequest.getTotalPrice().add(procurementRequestItem.getTotalPrice()));
        }
    }

    @Override
    public ProcurementRequestItem get(Integer id) {
        return procurementRequestItemDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ProcurementRequestItem> selectAll() {
        return procurementRequestItemDao.selectAll();
    }

    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)procurementRequestItemDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the procurement request item showing in a particular page
            List<ProcurementRequestItem> result = procurementRequestItemDao.queryByCondition(qo);
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }
    //to search all the procurementRequestItems inside an procurementRequest with its corresponding procurementRequest id
    @Override
    public List<ProcurementRequestItem> queryProcurementRequestItemByProcurementRequestId(Integer id) {
        List<ProcurementRequestItem> result = procurementRequestItemDao.queryProcurementRequestItemByProcurementRequestId(id);
        return result;
    }
}
