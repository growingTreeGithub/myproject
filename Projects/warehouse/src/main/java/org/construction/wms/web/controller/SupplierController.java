package org.construction.wms.web.controller;

import org.construction.wms.domain.ConstructionSite;
import org.construction.wms.domain.Supplier;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.SupplierQueryObject;
import org.construction.wms.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    //to redirect the page to list.jsp
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") SupplierQueryObject qo, HttpSession session){
        PageResult result = null;
        //return the search result of supplier showing in a particular page
        result = supplierService.selectByCondition(qo);
        //set the pageResult into session so that the result can be displayed on supplier/list.jsp
        session.setAttribute("pageResult", result);
        //redirect the page to supplier/list
        return "supplier/list";
    }

    //to redirect the page to input.jsp
    @RequestMapping("/input")
    public ModelAndView input(Supplier supplier){
        //to set the page destination
        ModelAndView view = new ModelAndView("supplier/input");
        //if the id of supplier is not null, it means it is a edit operation
        if(supplier.getId()!= null){
            //get the supplier information from the database by supplier id
            supplier = supplierService.get(supplier.getId());
        }
        //set the supplier object into ModelAndView object so that the supplier information can be display on supplier/input.jsp
        view.addObject("supplier", supplier);
        return view;
    }

    //to save or update supplier information into database
    @RequestMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(Supplier supplier){
        try{
            //if the id of supplier is null, it is a save operation
            if(supplier.getId() == null){
                //to set the page destination
                ModelAndView view = new ModelAndView("supplier/list");
                //save the supplier information to supplier table
                supplierService.save(supplier);
                /*
                As in supplier/list.jsp line 21, in <form> tag, when the modelAttribute value is equal to qo,
                it means that it need to retrieve the value for qo in a HttpRequest domain.
                if you do not save the value for qo in request domain before entering the supplier/list.jsp,
                it will have a error called Neither BindingResult nor plain target object for bean name available as request attribute.
                To solve the problem, write a value for qo in request domain before redirect to supplier/list.jsp.
                Therefore, view.addObject("qo", qo) is used to solve this problem.
                */
                SupplierQueryObject qo = new SupplierQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in supplier/list.jsp)
                view.addObject("saveMsg","Save successfully");
                view.addObject("domainName","supplier");
                return view;
            }else{
                //if the id of supplier is not null, it is a update operation
                //to set the page destination
                ModelAndView view = new ModelAndView("supplier/list");
                //update the supplier information to supplier table
                supplierService.update(supplier);
                //same as the above situation, setting qo value before redirecting to supplier/list.jsp
                SupplierQueryObject qo = new SupplierQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in supplier/list.jsp)
                view.addObject("updateMsg","Update successfully");
                view.addObject("domainName","supplier");
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

    //to delete a supplier
    @RequestMapping("/delete")
    @ResponseBody
    public int delete(Supplier supplier){

        //connection is used to store value return from SupplierService to determine whether the deletion success or not
        int connection = 0;
        if(supplier.getId()!= null){
            /*return 1 by ajax to tell the user to delete all the data related to this supplier so that
              the user can delete the supplier successfully.(please refer to line 55 in supplier.js file )

              return 0 by ajax to tell the user that the supplier has been deleted successfully.
            */
            connection = supplierService.delete(supplier.getId());
        }
        return connection;
    }


}
