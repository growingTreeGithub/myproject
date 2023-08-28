package org.construction.wms.dao;

import org.construction.wms.domain.BrandName;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface BrandNameDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BrandName brandname);

    BrandName selectByPrimaryKey(Integer id);

    List<BrandName> selectAll();

    int updateByPrimaryKey(BrandName brandname);
    //return the number of search result searching by keyword
    Long queryByConditionCount(QueryObject qo);
    //return the search result of the brandName showing in a particular page
    List<BrandName> queryByCondition(QueryObject qo);
    /*to search brandName details of a product by searching product id with its corresponding
    procurementRequestItem id, and then searching brandName id with its corresponding product id,
    then find brandName details by its brandName id
    and set the brandName details into product so that the brandName of the product
    can be shown in the jsp on frontend
    * */
    BrandName queryBrandNameByProcurementRequestItemId(Integer id);
    /*to search brandName details of a product by searching product id with its corresponding
    inBoundOrderItem id, and then searching brandName id with its corresponding product id,
    then find brandName details by its brandName id
    and set the brandName details into product so that the brandName of the product
    can be shown in the jsp on frontend
    * */
    BrandName queryBrandNameByInBoundOrderItemId(Integer id);
    /*to search brandName details of a product by searching product id with its corresponding
    outBoundOrderItem id, and then searching brandName id with its corresponding product id,
    then find brandName details by its brandName id
     and set the brandName details into product so that the brandName of the product
     can be shown in the jsp on frontend
     * */
    BrandName queryBrandNameByOutBoundOrderItemId(Integer id);
    /*to search brandName details of a product by searching brandName id with its corresponding product id,
    then find brandName details by its brandName id
     and set the brandName details into product so that the brandName of the product
     can be shown in the jsp on frontend
     * */
    BrandName queryBrandNameByProductId(Integer id);
}
