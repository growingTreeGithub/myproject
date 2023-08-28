package org.construction.wms.web.controller;

import org.construction.wms.domain.ConstructionSite;
import org.construction.wms.domain.Product;
import org.construction.wms.query.ConstructionSiteQueryObject;
import org.construction.wms.query.PageResult;
import org.construction.wms.service.ConstructionSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/constructionSite")
public class ConstructionSiteController {
    @Autowired
    private ConstructionSiteService constructionSiteService;

    //to redirect the page to list.jsp
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") ConstructionSiteQueryObject qo, HttpSession session){
        PageResult result = null;
        //return the search result of construction site showing in a particular page
        result = constructionSiteService.selectByCondition(qo);
        //set the pageResult into session so that the result can be displayed on constructionSite/list.jsp
        session.setAttribute("pageResult", result);
        //redirect the page to constructionSite/list
        return "constructionSite/list";
    }

    //to redirect the page to input.jsp
    @RequestMapping("/input")
    public ModelAndView input(ConstructionSite constructionSite){
        //to set the page destination
        ModelAndView view = new ModelAndView("constructionSite/input");
        //if the id of construction site is not null, it means it is a edit operation
        if(constructionSite.getId()!= null){
            //get the constructionSite information from the database by constructionSite id
            constructionSite = constructionSiteService.get(constructionSite.getId());
        }
        //set the constructionSite object into ModelAndView object so that the constructionSite information can be display on constructionSite/input.jsp
        view.addObject("constructionSite", constructionSite);
        return view;
    }

    //to save or update constructionSite information into database
    @RequestMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(ConstructionSite constructionSite){
        try{
            //if the id of constructionSite is null, it is a save operation
            if(constructionSite.getId() == null){
                //to set the page destination
                ModelAndView view = new ModelAndView("constructionSite/list");
                //save the constructionSite information to constructionSite table
                constructionSiteService.save(constructionSite);
                /*
                As in constructionSite/list.jsp line 21, in <form> tag, when the modelAttribute value is equal to qo,
                it means that it need to retrieve the value for qo in a HttpRequest domain.
                if you do not save the value for qo in request domain before entering the constructionSite/list.jsp,
                it will have a error called Neither BindingResult nor plain target object for bean name available as request attribute.
                To solve the problem, write a value for qo in request domain before redirect to constructionSite/list.jsp.
                Therefore, view.addObject("qo", qo) is used to solve this problem.
                */
                ConstructionSiteQueryObject qo = new ConstructionSiteQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in constructionSite/list.jsp)
                view.addObject("saveMsg","Save successfully");
                view.addObject("domainName","constructionSite");
                return view;
            }else{
                //if the id of constructionSite is not null, it is a update operation
                //to set the page destination
                ModelAndView view = new ModelAndView("constructionSite/list");
                //update the constructionSite information to constructionSite table
                constructionSiteService.update(constructionSite);
                //same as the above situation, setting qo value before redirecting to constructionSite/list.jsp
                ConstructionSiteQueryObject qo = new ConstructionSiteQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in constructionSite/list.jsp)
                view.addObject("updateMsg","Update successfully");
                view.addObject("domainName","constructionSite");
                return view;
            }
        }catch(Exception e){
            e.printStackTrace();
            //if error exist, redirect to common/error.jsp
            ModelAndView view = new ModelAndView("common/error");
            view.addObject("Exception", e);
            return view;
        }
    }

    //to delete a constructionSite
    @RequestMapping("/delete")
    @ResponseBody
    public int delete(ConstructionSite constructionSite){
        //connection is used to store value return from constructionSiteService to determine whether the deletion success or not
        int connection = 0;
        if(constructionSite.getId()!= null){
            /*return 1 by ajax to tell the user to delete all the data related to this construction site so that
            the user can delete the construction site successfully.(please refer to line 49 in constructionSite.js file )

            return 0 by ajax to tell the user that the construction site has been deleted successfully.
            */
            connection = constructionSiteService.delete(constructionSite.getId());
        }
        return connection;
    }
}
