package org.construction.wms.web.controller;

import org.apache.ibatis.annotations.Case;
import org.construction.wms.domain.*;
import org.construction.wms.query.InBoundOrderQueryObject;
import org.construction.wms.query.PageResult;
import org.construction.wms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/inBoundOrder")
public class InBoundOrderController {
    @Autowired
    private InBoundOrderService inBoundOrderService;
    @Autowired
    private InBoundOrderItemService inBoundOrderItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandNameService brandNameService;

    //to redirect the page to list.jsp
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") InBoundOrderQueryObject qo, HttpSession session){
        PageResult result = null;
        //return the search result of inBoundOrder showing in a particular page
        result = inBoundOrderService.selectByCondition(qo);
        //set the pageResult into session so that the result can be displayed on inBoundOrder/list.jsp
        session.setAttribute("pageResult", result);
        //redirect the page to inBoundOrder/list
        return "inBoundOrder/list";
    }

    //to redirect the page to input.jsp
    @RequestMapping("/input")
    public ModelAndView input(InBoundOrder inBoundOrder){
        //to set the page destination
        ModelAndView view = new ModelAndView("inBoundOrder/input");
        //if inBoundOrder id is not null, it means that it is an edit operation, retrieve the data to display on input.jsp
        if(inBoundOrder.getId()!= null){
            //get the inBoundOrder information from the database by inBoundOrder id
            inBoundOrder = inBoundOrderService.get(inBoundOrder.getId());
        }
        //retrieve the data to display inBoundDate and serialNumber
        view.addObject("inBoundOrder", inBoundOrder);
        return view;
    }

    //to save or update inBoundOrder information into database
    @RequestMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(InBoundOrder inBoundOrder, @RequestParam(value="itemsId",required=false) String[] itemsId,
                                     @RequestParam(value="itemsProduct") String[] itemsProduct, @RequestParam(value="itemsBrandName",required=false) String[] itemsBrandName,
                                     @RequestParam(value="itemsCostPrice") String[] itemsCostPrice,@RequestParam(value="itemsAmount") String[] itemsAmount,
                                     @RequestParam(value="itemsTotalPrice",required=false) String[] itemsTotalPrice){
        try{
            //with inBoundOrder id equals to null, save the inBound Order
            if(inBoundOrder.getId() == null){
                //to set the page destination
                ModelAndView view = new ModelAndView("inBoundOrder/list");
                //the value of the itemsProduct is obtained from the allProductList in inBoundOrder/input.jsp
                for(int i = 0; i < itemsProduct.length; i++){
                    InBoundOrderItem inBoundOrderItem = new InBoundOrderItem();
                    //get product from the id of product
                    Product product = productService.get(Integer.parseInt(itemsProduct[i]));
                    //the brandname of the product has returned from the backend, therefore the inBoundOrderItem does not need to set brandName by using itemsBrandName
                    //set product, cost price, amount, into inBoundOrderItem. Set inBoundOrderItem into inBoundOrder.
                    inBoundOrderItem.setProduct(product);
                    inBoundOrderItem.setCostPrice(new BigDecimal(itemsCostPrice[i]));
                    inBoundOrderItem.setAmount(new BigDecimal(itemsAmount[i]));
                    inBoundOrder.getItems().add(inBoundOrderItem);
                }
                //save the inBoundOrder information to inBoundOrder table
                inBoundOrderService.save(inBoundOrder);
                /*
                As in inBoundOrder/list.jsp line 21, in <form> tag, when the modelAttribute value is equal to qo,
                it means that it need to retrieve the value for qo in a HttpRequest domain.
                if you do not save the value for qo in request domain before entering the inBoundOrder/list.jsp,
                it will have a error called Neither BindingResult nor plain target object for bean name available as request attribute.
                To solve the problem, write a value for qo in request domain before redirect to inBoundOrder/list.jsp.
                Therefore, view.addObject("qo", qo) is used to solve this problem.
                */
                InBoundOrderQueryObject qo = new InBoundOrderQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in inBoundOrder/list.jsp)
                view.addObject("saveMsg","Save successfully");
                view.addObject("domainName","inBoundOrder");
                return view;
            }else{
                //with inBoundOrder id not equals to null, update the inBound Order
                ModelAndView view = new ModelAndView("inBoundOrder/list");
                for(int i = 0; i < itemsProduct.length; i++){
                    //if the length of itemsProduct is greater than the length of itemsId, it means this is a new added item.
                    /*Case 1: itemsId == null, when user edit an inBoundOder and delete all the inBoundOrderItem inside the
                    inBoundOrder and click confirm, error will exist. However, the edited inBoundOrder will still exist and
                     the totalAmount and totalPrice of the inBoundOrder will be zero. When the user edit the inBoundOrder again,
                     as there aren't any items in a the inBound Order list, the input.jsp will show a jsp page with no itemsId
                     inside the page(PLease refer to input.jsp of inBoundOrder on line 105).
                     Therefore, when the user add new items in the inBoundOrder List, the data sent to backend will
                     not have itemsId and therefore itemsId will equal to null and will execute the below coding with the case
                     ItemsId == null.

                    Case 2: itemsId[i]=="" means it is clone row or the deleted row in a table and all values set to be ""
                    When user click edit an inBoundOrder, if the user just add new rows, as there are inBoundOrderItems in the list,
                    it will show jsp of the inBoundOrder with itemsId(please refer to input.jsp of inBoundOrder on line 139).
                    Another Case is when a user edit an inBoundOrder and delete all the items in an inBoundOrder, the user then
                    add new inBoundOrderItems, the itemsId of these new inBoundOrderItems == "".
                    Therefore, when the user add new items in the inBoundOrder list, the data sent to backend will have
                    itemsId == "" and will execute the below coding with the case ItemsId == "".It is also considered as
                    adding a new inBoundOrderItem in an inBoundOrder.

                    Case 3: For those existing items in inBoundOrder, if the user edit the inBoundOrder List and just amend
                    the details of the existing items, it will execute the below coding with the else case.
                     */
                    if( itemsId==null || (itemsId.length == 0)){
                        InBoundOrderItem newInBoundOrderItem = new InBoundOrderItem();
                        //get product from the id of product
                        Product product = productService.get(Integer.parseInt(itemsProduct[i]));
                        //set product, cost price, amount, into inBoundOrderItem. Set inBoundOrderItem into inBoundOrder.
                        newInBoundOrderItem.setProduct(product);
                        newInBoundOrderItem.setCostPrice(new BigDecimal(itemsCostPrice[i]));
                        newInBoundOrderItem.setAmount(new BigDecimal(itemsAmount[i]));
                        inBoundOrder.getItems().add(newInBoundOrderItem);
                    }else if(itemsId[i]==""){
                        InBoundOrderItem newInBoundOrderItem = new InBoundOrderItem();
                        //get product from the id of product
                        Product product = productService.get(Integer.parseInt(itemsProduct[i]));
                        //set product, cost price, amount, into inBoundOrderItem. Set inBoundOrderItem into inBoundOrder.
                        newInBoundOrderItem.setProduct(product);
                        newInBoundOrderItem.setCostPrice(new BigDecimal(itemsCostPrice[i]));
                        newInBoundOrderItem.setAmount(new BigDecimal(itemsAmount[i]));
                        inBoundOrder.getItems().add(newInBoundOrderItem);
                    }else{
                        //deal with existing inBoundOrderItem row in inBoundOrder, update the data if there is any amendment
                        InBoundOrderItem inBoundOrderItem = inBoundOrderItemService.get(Integer.parseInt(itemsId[i]));
                        //get product from the id of product
                        Product product = productService.get(Integer.parseInt(itemsProduct[i]));
                        //set product, cost price, amount, into inBoundOrderItem. Set inBoundOrderItem into inBoundOrder.
                        inBoundOrderItem.setProduct(product);
                        inBoundOrderItem.setCostPrice(new BigDecimal(itemsCostPrice[i]));
                        inBoundOrderItem.setAmount(new BigDecimal(itemsAmount[i]));
                        inBoundOrder.getItems().add(inBoundOrderItem);
                    }
                }
                //update the inBoundOrder information to inBoundOrder table
                inBoundOrderService.update(inBoundOrder);
                //same as the above situation, setting qo value before redirecting to inBoundOrder/list.jsp
                InBoundOrderQueryObject qo = new InBoundOrderQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in inBoundOrder/list.jsp)
                view.addObject("updateMsg","Update successfully");
                view.addObject("domainName","inBoundOrder");
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

    //to delete an inBoundOrder
    @RequestMapping("/delete")
    public ModelAndView delete(InBoundOrder inBoundOrder){
        //to set the page destination
        ModelAndView view = new ModelAndView("inBoundOrder/list");
        if(inBoundOrder.getId()!= null){
            //delete an inBoundOrder with inBoundOrder id
            inBoundOrderService.delete(inBoundOrder.getId());
        }
        //same as the above situation, setting qo value before redirecting to inBoundOrder/list.jsp
        InBoundOrderQueryObject qo = new InBoundOrderQueryObject();
        view.addObject("qo", qo);
        return view;
    }

    //prepare allProductList data that needed to be displayed in inBoundOrder/input.jsp
    @ModelAttribute("allProductList")
    public List<Product> getProductList(){
        List<Product> allProductList = new ArrayList<>();
        allProductList = productService.selectAll();
        return allProductList;
    }

    //to search whether there is any inBoundOrderItem under a inBoundOrder
    //the jsp displayed to the user for empty selectedItemList and not empty selectedItemList is different.
    //prepare selectedItemList data that needed to be displayed in inBoundOrder/input.jsp
    @ModelAttribute("selectedItemList")
    public List<InBoundOrderItem> queryInBoundOrderItemByInBoundOrderId(Integer id){
        List<InBoundOrderItem> result = new ArrayList<>();
        //to search all the inBoundOrderItems inside an inBoundOrder with its corresponding inBoundOrder id
        result = inBoundOrderItemService.queryInBoundOrderItemByInBoundOrderId(id);
        for(InBoundOrderItem item: result){
            //find brandName information by inBoundOrderItem id and set the brandName information into product of the inBoundOrderItem
            BrandName brandName = brandNameService.queryBrandNameByInBoundOrderItemId(item.getId());
            item.getProduct().setBrandName(brandName);
        }
        return result;
    }

    //to process audit procedures and confirm the inBoundOrderItems on inBoundOrder are stored in warehouse
    @RequestMapping("/audit")
    public ModelAndView audit(InBoundOrder inBoundOrder){
        //to set the page destination
        ModelAndView view = new ModelAndView("inBoundOrder/list");
        if(inBoundOrder.getId()!= null){
            //process audit procedures with inBoundOrder id
            inBoundOrderService.audit(inBoundOrder.getId());
        }
        //same as the above situation, setting qo value before redirecting to inBoundOrder/list.jsp
        InBoundOrderQueryObject qo = new InBoundOrderQueryObject();
        view.addObject("qo", qo);
        return view;
    }

    //to redirect to inBoundOrder/read.jsp
    @RequestMapping("/read")
    public ModelAndView read(InBoundOrder inBoundOrder){
        //to set the page destination
        ModelAndView view = new ModelAndView("inBoundOrder/read");
        if(inBoundOrder.getId()!= null){
            inBoundOrder = inBoundOrderService.get(inBoundOrder.getId());
        }
        //set the inBoundOrder object into ModelAndView object so that the inBoundOrder information can be display on inBoundOrder/read.jsp
        view.addObject("inBoundOrder", inBoundOrder);
        return view;
    }
}
