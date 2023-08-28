package org.construction.wms.service.impl;

import org.construction.wms.dao.ConstructionSiteDao;
import org.construction.wms.dao.OutBoundOrderDao;
import org.construction.wms.domain.ConstructionSite;
import org.construction.wms.domain.Employee;
import org.construction.wms.domain.OutBoundOrder;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.QueryObject;
import org.construction.wms.service.ConstructionSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstructionSiteServiceImpl implements ConstructionSiteService {
    @Autowired
    private ConstructionSiteDao constructionSiteDao;
    @Autowired
    private OutBoundOrderDao outBoundOrderDao;
    @Override
    public int save(ConstructionSite constructionSite) {
        return constructionSiteDao.insert(constructionSite);
    }

    @Override
    public int update(ConstructionSite constructionSite) {
        return constructionSiteDao.updateByPrimaryKey(constructionSite);
    }

    @Override
    public int delete(Integer id) {
        /*to query all the outBoundOrder which has the below construction site. if there is any outBoundOrder using this
        construction site, this construction site cannot be deleted since it will lead to data loss.
        */
        List<OutBoundOrder> outBoundOrderList = outBoundOrderDao.queryOutBoundOrderByConstructionSiteId(id);
        if(outBoundOrderList.size() > 0){
            /*return 1 by ajax to tell the user to delete all the data related to this construction site so that
            the user can delete the construction site successfully.(please refer to line 48 in constructionSite.js file )
            */
            return 1;
        }else{
            constructionSiteDao.deleteByPrimaryKey(id);
            /*return 0 by ajax to tell the user that the construction site has been deleted successfully.
             */
            return 0;
        }
    }

    @Override
    public ConstructionSite get(Integer id) {
        return constructionSiteDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ConstructionSite> selectAll() {
        return constructionSiteDao.selectAll();
    }

    @Override
    public PageResult selectByCondition(QueryObject qo) {
        //it is used to accept the currentPage number from the jsp page
        int currentPage = qo.getCurrentPage();
        //it is used to accept the pageSize selected from the jsp page
        int pageSize = qo.getPageSize();
        //return the number of search result searching by keyword
        int count = ((Long)constructionSiteDao.queryByConditionCount(qo)).intValue();
        if(count > 0){
            //return the search result of the construction site showing in a particular page
            List<ConstructionSite> result = constructionSiteDao.queryByCondition(qo);
            //put all the necessary page display info into pageResult to display on jsp
            return new PageResult(count, result, currentPage, pageSize);
        }else {
            //return a empty set of pageResult if there is no record for the query
            return PageResult.empty(pageSize);
        }
    }
}
