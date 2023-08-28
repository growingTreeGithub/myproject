package org.construction.wms.web.controller;

import org.construction.wms.domain.*;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.ProcurementRequestItemQueryObject;
import org.construction.wms.query.ProcurementRequestQueryObject;
import org.construction.wms.service.BrandNameService;
import org.construction.wms.service.ProcurementRequestItemService;
import org.construction.wms.service.ProcurementRequestService;
import org.construction.wms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/procurementRequestItem")
public class ProcurementRequestItemController {
    @Autowired
    private ProcurementRequestItemService procurementRequestItemService;
    @Autowired
    private ProcurementRequestService procurementRequestService;
    @Autowired
    private ProductService productService;

    //to delete an procurementRequestItem in an procurementRequest
    @RequestMapping("/delete")
    public ModelAndView delete(Integer requestId, Integer itemId){
        //to set the page destination
        ModelAndView view = new ModelAndView("procurementRequest/input");
        ProcurementRequest procurementRequest = new ProcurementRequest();
       if(requestId!= null){
           //get procurementRequest with corresponding procurementRequest id
            procurementRequest = procurementRequestService.get(requestId);
        }
        if(itemId!= null){
            //delete an procurementRequestItem in an procurementRequest and update procurementRequest information
            procurementRequestItemService.delete(itemId);
        }
        //set the procurementRequest object into ModelAndView object so that the procurementRequest information can be display on procurementRequest/input.jsp
        view.addObject("procurementRequest", procurementRequest);
        return view;
    }

    //change and display the corresponding brandName when clicking and select a specific product in a procurementRequestItem row on procurementRequest/input.jsp
    //Please refer to line 24 in procurementRequestList.js
    @RequestMapping("/productData")
    @ResponseBody
    public Product getProductData(Integer id){
        Product product = productService.get(id);
        return product;
    }
}
