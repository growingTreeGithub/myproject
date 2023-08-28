package org.construction.wms.service.impl;

import org.construction.wms.dao.OutBoundOrderDao;
import org.construction.wms.dao.OutBoundOrderItemDao;
import org.construction.wms.domain.*;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.OutBoundOrderItemService;
import org.construction.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class OutBoundOrderItemServiceImpl implements OutBoundOrderItemService {
    @Autowired
    private OutBoundOrderItemDao outBoundOrderItemDao;
    @Autowired
    private OutBoundOrderDao outBoundOrderDao;

    @Override
    public int save(OutBoundOrderItem outBoundOrderItem) {
        return outBoundOrderItemDao.insert(outBoundOrderItem);
    }

    @Override
    public int update(OutBoundOrderItem outBoundOrderItem) {
        return outBoundOrderItemDao.updateByPrimaryKey(outBoundOrderItem);
    }

    @Override
    public int delete(Integer id) {
        //to find the outBoundOrder that an outBoundOrderItem belongs to
        OutBoundOrder outBoundOrder = outBoundOrderDao.queryOutBoundOrderByOutBoundOrderItemId(id);
        //record the user who have delete the record and get the user from session and put it into outBoundOrder
        outBoundOrder.setInputUser((Employee) UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION));
        //delete an outBoundOrderItem in an outBoundOrder by outBoundOrderItem id in join table outBoundOrder_items
        outBoundOrderItemDao.deleteItemsByOutBoundOrderItemId(id);
        //to search all the remaining outBoundOrderItems inside an outBoundOrder with its corresponding outBoundOrder id
        List<OutBoundOrderItem> items = outBoundOrderItemDao.queryOutBoundOrderItemByOutBoundOrderId(outBoundOrder.getId());
        // to get totalAmount and totalPrice of outBound Order, and to set outBound Order item under corresponding outBound Order
        dealWithItemDetails(outBoundOrder, items);
        //update the latest data for the outBoundOrder to outBoundOrder table
        outBoundOrderDao.updateByPrimaryKey(outBoundOrder);
        //delete the specific outBoundOrderItem in outBoundOrderItem table
        return outBoundOrderItemDao.deleteByPrimaryKey(id);
    }

    // to get totalAmount and totalPrice of outBound Order, and to set outBound Order item under corresponding outBound Order
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
    public OutBoundOrderItem get(Integer id) {
        return outBoundOrderItemDao.selectByPrimaryKey(id);
    }

    @Override
    public List<OutBoundOrderItem> selectAll() {
        return outBoundOrderItemDao.selectAll();
    }

    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)outBoundOrderItemDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the outBoundOrderItem showing in a particular page
            List<OutBoundOrderItem> result = outBoundOrderItemDao.queryByCondition(qo);
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }
    //to search all the outBoundOrderItems inside an outBoundOrder with its corresponding outBoundOrder id
    @Override
    public List<OutBoundOrderItem> queryOutBoundOrderItemByOutBoundOrderId(Integer id) {
        List<OutBoundOrderItem> result = outBoundOrderItemDao.queryOutBoundOrderItemByOutBoundOrderId(id);
        return result;
    }
}