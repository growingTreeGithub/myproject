package org.construction.wms.dao;

import org.construction.wms.domain.Product;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface ProductDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Product product);

    Product selectByPrimaryKey(Integer id);

    List<Product> selectAll();

    int updateByPrimaryKey(Product product);
    //return the number of search result searching by keyword
    Long queryByConditionCount(QueryObject qo);
    //return the search result of the product showing in a particular page
    List<Product> queryByCondition(QueryObject qo);
    /*to query all the product which have this brandName. If there is any product using this
     brandName, this brandName cannot be deleted since it will lead to data loss.
     */
    List<Product> queryProductByBrandNameId(Integer brandNameId);
}
