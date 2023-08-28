package org.construction.wms.web.controller;

import org.construction.wms.domain.OutBoundOrder;
import org.construction.wms.domain.Product;
import org.construction.wms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/outBoundOrderItem")
public class OutBoundOrderItemController {
    @Autowired
    private OutBoundOrderItemService outBoundOrderItemService;
    @Autowired
    private OutBoundOrderService outBoundOrderService;
    @Autowired
    private ProductService productService;

    //to delete an outBoundOrderItem in an outBoundOrder
    @RequestMapping("/delete")
    public ModelAndView delete(Integer orderId, Integer itemId){
        //to set the page destination
        ModelAndView view = new ModelAndView("outBoundOrder/input");
        OutBoundOrder outBoundOrder = new OutBoundOrder();
        if(orderId!= null){
            //get outBoundOrder with corresponding outBoundOrder id
            outBoundOrder = outBoundOrderService.get(orderId);
        }
        if(itemId!= null){
            //delete an outBoundOrderItem in an outBoundOrder and update outBoundOrder information
            outBoundOrderItemService.delete(itemId);
        }
        //set the outBoundOrder object into ModelAndView object so that the outBoundOrder information can be display on outBoundOrder/input.jsp
        view.addObject("outBoundOrder", outBoundOrder);
        return view;
    }

    //change and display the corresponding brandName when clicking and select a specific product in a outBoundOrderItem row on outBoundOrder/input.jsp
    //Please refer to line 24 in outBoundOrderList.js
    @RequestMapping("/productData")
    @ResponseBody
    public Product getProductData(Integer id){
        Product product = productService.get(id);
        return product;
    }
}
