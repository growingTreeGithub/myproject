package org.construction.wms.web.controller;

import org.construction.wms.query.InventoryQueryObject;
import org.construction.wms.query.PageResult;
import org.construction.wms.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    //to redirect the page to list.jsp
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") InventoryQueryObject qo, HttpSession session){
        PageResult result = null;
        //return the search result of inventory showing in a particular page
        result = inventoryService.selectByCondition(qo);
        //set the pageResult into session so that the result can be displayed on inventory/list.jsp
        session.setAttribute("pageResult", result);
        //redirect the page to inventory/list
        return "inventory/list";
    }
}
