package org.construction.wms.service.impl;

import org.construction.wms.dao.ProcurementRequestDao;
import org.construction.wms.dao.SupplierDao;
import org.construction.wms.domain.OutBoundOrder;
import org.construction.wms.domain.ProcurementRequest;
import org.construction.wms.domain.Supplier;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private ProcurementRequestDao procurementRequestDao;
    @Override
    public int save(Supplier supplier) {
        return supplierDao.insert(supplier);
    }

    @Override
    public int update(Supplier supplier) {
        return supplierDao.updateByPrimaryKey(supplier);
    }

    @Override
    public int delete(Integer id) {
        /*to query all the procurementRequest which has this supplier. if there is any procurementRequest using this
        supplier, this supplier cannot be deleted since it will lead to data loss.
        */
        List<ProcurementRequest> procurementRequestList = procurementRequestDao.queryProcurementRequestBySupplierId(id);
        if(procurementRequestList.size() > 0){
            /*return 1 by ajax to tell the user to delete all the data related to this supplier so that
            the user can delete the supplier successfully.(please refer to line 54 in supplier.js file )
            */
            return 1;
        }else{
            /*
            return 0 by ajax to tell the user that the supplier has been deleted successfully
            * */
            supplierDao.deleteByPrimaryKey(id);
            return 0;
        }
    }

    @Override
    public Supplier get(Integer id) {
        return supplierDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Supplier> selectAll() {
        return supplierDao.selectAll();
    }

    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)supplierDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the supplier showing in a particular page
            List<Supplier> result = supplierDao.queryByCondition(qo);
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }
}
