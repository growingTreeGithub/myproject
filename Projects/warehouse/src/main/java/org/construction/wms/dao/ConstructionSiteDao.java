package org.construction.wms.dao;

import org.construction.wms.domain.ConstructionSite;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface ConstructionSiteDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ConstructionSite constructionSite);

    ConstructionSite selectByPrimaryKey(Integer id);

    List<ConstructionSite> selectAll();

    int updateByPrimaryKey(ConstructionSite constructionSite);
    //return the number of search result searching by keyword
    Long queryByConditionCount(QueryObject qo);
    //return the search result of the construction site showing in a particular page
    List<ConstructionSite> queryByCondition(QueryObject qo);
}
