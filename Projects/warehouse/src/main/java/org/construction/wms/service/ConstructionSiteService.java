package org.construction.wms.service;

import org.construction.wms.domain.ConstructionSite;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;

import java.util.List;

public interface ConstructionSiteService {
    int save(ConstructionSite constructionSite);
    int update(ConstructionSite constructionSite);
    int delete(Integer id);
    ConstructionSite get(Integer id);
    List<ConstructionSite> selectAll();
    PageResult selectByCondition(QueryObject qo);
}
