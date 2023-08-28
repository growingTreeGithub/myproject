package org.construction.wms.web.controller;

import org.construction.wms.query.PageResult;
import org.construction.wms.query.PermissionQueryObject;
import org.construction.wms.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    //to redirect the page to list.jsp
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") PermissionQueryObject qo, HttpSession session){
        PageResult result = null;
        //return the search result of permission showing in a particular page
        result = permissionService.selectByCondition(qo);
        //set the pageResult into session so that the result can be displayed on permission/list.jsp
        session.setAttribute("pageResult", result);
        //redirect the page to permission/list
        return "permission/list";
    }


}
