package org.construction.wms.web.controller;

import org.construction.wms.domain.InBoundOrder;
import org.construction.wms.domain.Product;
import org.construction.wms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/inBoundOrderItem")
public class InBoundOrderItemController {
    @Autowired
    private InBoundOrderItemService inBoundOrderItemService;
    @Autowired
    private InBoundOrderService inBoundOrderService;
    @Autowired
    private ProductService productService;

    //to delete an inBoundOrderItem in an inBoundOrder
    @RequestMapping("/delete")
    public ModelAndView delete(Integer orderId, Integer itemId){
        //to set the page destination
        ModelAndView view = new ModelAndView("inBoundOrder/input");
        InBoundOrder inBoundOrder = new InBoundOrder();
        if(orderId!= null){
            //get inBoundOrder with corresponding inBoundOrder id
            inBoundOrder = inBoundOrderService.get(orderId);
        }
        if(itemId!= null){
            //delete an inBoundOrderItem in an inBoundOrder and update inBoundOrder information
            inBoundOrderItemService.delete(itemId);
        }
        //set the inBoundOrder object into ModelAndView object so that the inBoundOrder information can be display on inBoundOrder/input.jsp
        view.addObject("inBoundOrder", inBoundOrder);
        return view;
    }

    //change and display the corresponding brandName when clicking and select a specific product in a inBoundOrderItem
    //Please refer to line 24 in inBoundOrderList.js
    @RequestMapping("/productData")
    @ResponseBody
    public Product getProductData(Integer id){
        Product product = productService.get(id);
        //return to frontend in json format using ResponseBody annotation
        return product;
    }
}
