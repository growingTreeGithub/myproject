package org.construction.wms.service.impl;

import org.construction.wms.dao.InBoundOrderDao;
import org.construction.wms.dao.InBoundOrderItemDao;
import org.construction.wms.domain.*;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.InBoundOrderItemService;
import org.construction.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class InBoundOrderItemServiceImpl implements InBoundOrderItemService {
    @Autowired
    private InBoundOrderItemDao inBoundOrderItemDao;
    @Autowired
    private InBoundOrderDao inBoundOrderDao;

    @Override
    public int save(InBoundOrderItem inBoundOrderItem) {
        return inBoundOrderItemDao.insert(inBoundOrderItem);
    }

    @Override
    public int update(InBoundOrderItem inBoundOrderItem) {
        return inBoundOrderItemDao.updateByPrimaryKey(inBoundOrderItem);
    }

    @Override
    public int delete(Integer id) {
        //to find the inBoundOrder that an inBoundOrderItem belongs to
        InBoundOrder inBoundOrder = inBoundOrderDao.queryInBoundOrderByInBoundOrderItemId(id);
        //record the user who have delete the record and get the user from session and put it into inBoundOrder
        inBoundOrder.setInputUser((Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION));
        //delete an inBoundOrderItem in an inBoundOrder by inBoundOrderItem id in join table inBoundOrder_items
        inBoundOrderItemDao.deleteItemsByInBoundOrderItemId(id);
        //to search all the remaining inBoundOrderItems inside an inBoundOrder with its corresponding inBoundOrder id
        List<InBoundOrderItem> items = inBoundOrderItemDao.queryInBoundOrderItemByInBoundOrderId(inBoundOrder.getId());
        // to get totalAmount and totalPrice of inBound Order
        dealWithItemDetails(inBoundOrder, items);
        //update the latest data for the inBoundOrder to inBoundOrder table
        inBoundOrderDao.updateByPrimaryKey(inBoundOrder);
        //delete the specific inBoundOrderItem in inBoundOrderItem table
        return inBoundOrderItemDao.deleteByPrimaryKey(id);
    }

    // to get totalAmount and totalPrice of inBound Order, and to set inBound Order item under corresponding inBound Order
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
    public InBoundOrderItem get(Integer id) {
        return inBoundOrderItemDao.selectByPrimaryKey(id);
    }

    @Override
    public List<InBoundOrderItem> selectAll() {
        return inBoundOrderItemDao.selectAll();
    }

    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)inBoundOrderItemDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the inBoundOrderItem showing in a particular page
            List<InBoundOrderItem> result = inBoundOrderItemDao.queryByCondition(qo);
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }
    //to search all the inBoundOrderItems inside an inBoundOrder with its corresponding inBoundOrder id
    @Override
    public List<InBoundOrderItem> queryInBoundOrderItemByInBoundOrderId(Integer id) {
        List<InBoundOrderItem> result = inBoundOrderItemDao.queryInBoundOrderItemByInBoundOrderId(id);
        return result;
    }
}
