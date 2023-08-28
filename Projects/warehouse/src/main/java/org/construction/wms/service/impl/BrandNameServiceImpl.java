package org.construction.wms.service.impl;

import org.construction.wms.dao.BrandNameDao;
import org.construction.wms.dao.ProductDao;
import org.construction.wms.domain.BrandName;
import org.construction.wms.domain.Product;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.BrandNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandNameServiceImpl implements BrandNameService {
    @Autowired
    private BrandNameDao brandNameDao;
    @Autowired
    private ProductDao productDao;
    @Override
    public int save(BrandName brandName) {
        return brandNameDao.insert(brandName);
    }

    @Override
    public int update(BrandName brandName) {
        return brandNameDao.updateByPrimaryKey(brandName);
    }

    @Override
    public int delete(Integer id) {
        /*to query all the product which have this brandName. If there is any product using this
        brandName, this brandName cannot be deleted since it will lead to data loss.
        */
        List<Product> productList = productDao.queryProductByBrandNameId(id);
        if(productList.size() > 0){
            /*return 1 by ajax to tell the user to delete all the data related to this brandName so that
            the user can delete the brandName successfully.(please refer to line 42 in brandName.js file )
            */
            return 1;
        }else{
            brandNameDao.deleteByPrimaryKey(id);
            /*return 0 by ajax to tell the user that the brandName has been deleted successfully.
            */
            return 0;
        }
    }

    @Override
    public BrandName get(Integer id) {
        return brandNameDao.selectByPrimaryKey(id);
    }

    @Override
    public List<BrandName> selectAll() {
        return brandNameDao.selectAll();
    }

    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)brandNameDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the brandName showing in a particular page
            List<BrandName> result = brandNameDao.queryByCondition(qo);
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }

    /*to search brandName details of a product by searching product id with its corresponding
    procurementRequestItem id, and then searching brandName id with its corresponding product id,
    then find brandName details by its brandName id
    and set the brandName details into product so that the brandName of the product
    can be shown in the jsp on frontend
    * */
    @Override
    public BrandName queryBrandNameByProcurementRequestItemId(Integer id) {
        return brandNameDao.queryBrandNameByProcurementRequestItemId(id);
    }

    /*to search brandName details of a product by searching product id with its corresponding
    inBoundOrderItem id, and then searching brandName id with its corresponding product id,
    then find brandName details by its brandName id
    and set the brandName details into product so that the brandName of the product
    can be shown in the jsp on frontend
    * */
    @Override
    public BrandName queryBrandNameByInBoundOrderItemId(Integer id) {
        return brandNameDao.queryBrandNameByInBoundOrderItemId(id);
    }
    /*to search brandName details of a product by searching product id with its corresponding
    outBoundOrderItem id, and then searching brandName id with its corresponding product id,
    then find brandName details by its brandName id
     and set the brandName details into product so that the brandName of the product
     can be shown in the jsp on frontend
     * */
    @Override
    public BrandName queryBrandNameByOutBoundOrderItemId(Integer id) {
        return brandNameDao.queryBrandNameByOutBoundOrderItemId(id);
    }
}
