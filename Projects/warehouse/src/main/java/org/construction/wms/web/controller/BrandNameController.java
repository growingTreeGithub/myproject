package org.construction.wms.web.controller;

import org.construction.wms.domain.BrandName;
import org.construction.wms.query.BrandNameQueryObject;
import org.construction.wms.query.PageResult;
import org.construction.wms.service.BrandNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/brandName")
public class BrandNameController {
    @Autowired
    private BrandNameService brandNameService;

    //to redirect the page to list.jsp
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") BrandNameQueryObject qo, HttpSession session){
        PageResult result = null;
        //return the search result of brandName showing in a particular page
        result = brandNameService.selectByCondition(qo);
        //set the pageResult into session so that the result can be displayed on brandname/list.jsp
        session.setAttribute("pageResult", result);
        //redirect the page to brandName/list
        return "brandName/list";
    }

    //to redirect the page to input.jsp
    @RequestMapping("/input")
    public ModelAndView input(BrandName brandName){
        //to set the page destination
        ModelAndView view = new ModelAndView("brandName/input");
        //if the id of brandName is not null, it means it is a edit operation
        if(brandName.getId()!= null){
            //get the brandName information from the database by brandName id
            brandName = brandNameService.get(brandName.getId());
        }
        //set the brandName object into ModelAndView object so that the brandName information can be display on brandName/input.jsp
        view.addObject("brandName", brandName);
        return view;
    }

    //to save or update brandName information into database
    @RequestMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(BrandName brandName){
        try{
            //if the id of brandName is null, it is a save operation
            if(brandName.getId() == null){
                //to set the page destination
                ModelAndView view = new ModelAndView("brandName/list");
                //save the brandName information to brandName table
                brandNameService.save(brandName);
                /*
                As in brandName/list.jsp line 21, in <form> tag, when the modelAttribute value is equal to qo,
                it means that it need to retrieve the value for qo in a HttpRequest domain.
                if you do not save the value for qo in request domain before entering the brandName/list.jsp,
                it will have a error called Neither BindingResult nor plain target object for bean name available as request attribute.
                To solve the problem, write a value for qo in request domain before redirect to brandName/list.jsp.
                Therefore, view.addObject("qo", qo) is used to solve this problem.
                */
                BrandNameQueryObject qo = new BrandNameQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in brandName/list.jsp)
                view.addObject("saveMsg","Save successfully");
                view.addObject("domainName","brandName");
                return view;
            }else{
                //if the id of brandName is not null, it is a update operation
                //to set the page destination
                ModelAndView view = new ModelAndView("brandName/list");
                //update the brandName information to brandName table
                brandNameService.update(brandName);
                //same as the above situation, setting qo value before redirecting to brandName/list.jsp
                BrandNameQueryObject qo = new BrandNameQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in brandName/list.jsp)
                view.addObject("updateMsg","Update successfully");
                view.addObject("domainName","brandName");
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

    //to delete a brandName
    @RequestMapping("/delete")
    @ResponseBody
    public int delete(BrandName brandName){
        //connection is used to store value return from brandNameService to determine whether the deletion success or not
        int connection = 0;
        if(brandName.getId()!= null){
            /*return 1 by ajax to tell the user to delete all the data related to this brandName so that
              the user can delete the brandName successfully.(please refer to line 74 in brandName.js file )

              return 0 by ajax to tell the user that the brandName has been deleted successfully.
            */
            connection = brandNameService.delete(brandName.getId());
        }
        return connection;
    }
}
