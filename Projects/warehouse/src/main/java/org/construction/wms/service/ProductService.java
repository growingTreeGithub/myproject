package org.construction.wms.service;

import org.construction.wms.domain.Product;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface ProductService {
    int save(Product product);
    int update(Product product);
    int delete(Integer id);
    Product get(Integer id);
    List<Product> selectAll();
    PageResult selectByCondition(QueryObject qo);
}
