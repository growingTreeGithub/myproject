package org.construction.wms.web.controller;

import org.construction.wms.domain.*;
import org.construction.wms.query.OutBoundOrderQueryObject;
import org.construction.wms.query.PageResult;
import org.construction.wms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/outBoundOrder")
public class OutBoundOrderController {
    @Autowired
    private OutBoundOrderService outBoundOrderService;
    @Autowired
    private OutBoundOrderItemService outBoundOrderItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandNameService brandNameService;
    @Autowired
    private ConstructionSiteService constructionSiteService;

    //to redirect the page to list.jsp
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") OutBoundOrderQueryObject qo, HttpSession session){
        PageResult result = null;
        //return the search result of outBoundOrder showing in a particular page
        result = outBoundOrderService.selectByCondition(qo);
        //set the pageResult into session so that the result can be displayed on outBoundOrder/list.jsp
        session.setAttribute("pageResult", result);
        //redirect the page to outBoundOrder/list
        return "outBoundOrder/list";
    }

    //to redirect the page to input.jsp
    @RequestMapping("/input")
    public ModelAndView input(OutBoundOrder outBoundOrder){
        //to set the page destination
        ModelAndView view = new ModelAndView("outBoundOrder/input");
        //if outBoundOrder id is not null, it means that it is an edit operation, retrieve the data to display on input.jsp
        if(outBoundOrder.getId()!= null){
            //get the outBoundOrder information from the database by outBoundOrder id
            outBoundOrder = outBoundOrderService.get(outBoundOrder.getId());
        }
        //retrieve the data to display outBoundDate and serialNumber
        view.addObject("outBoundOrder", outBoundOrder);
        return view;
    }

    //to save or update outBoundOrder information into database
    @RequestMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(OutBoundOrder outBoundOrder, @RequestParam(value="itemsId",required=false) String[] itemsId,
                                     @RequestParam(value="itemsProduct") String[] itemsProduct, @RequestParam(value="itemsBrandName",required=false) String[] itemsBrandName,
                                     @RequestParam(value="itemsCostPrice") String[] itemsCostPrice,@RequestParam(value="itemsAmount") String[] itemsAmount,
                                     @RequestParam(value="itemsTotalPrice",required=false) String[] itemsTotalPrice){
        try{
            //with outBoundOrder id equals to null, save the outBound Order
            if(outBoundOrder.getId() == null){
                //to set the page destination
                ModelAndView view = new ModelAndView("outBoundOrder/list");
                //the value of the itemsProduct is obtained from the allProductList in outBoundOrder/input.jsp
                for(int i = 0; i < itemsProduct.length; i++){
                    OutBoundOrderItem outBoundOrderItem = new OutBoundOrderItem();
                    //get product from the id of product
                    Product product = productService.get(Integer.parseInt(itemsProduct[i]));
                    //the brandname of the product has returned from the backend, therefore the outBoundOrderItem does not need to set brandName by using itemsBrandName
                    //set product, cost price, amount, into outBoundOrderItem. Set outBoundOrderItem into outBoundOrder.
                    outBoundOrderItem.setProduct(product);
                    outBoundOrderItem.setCostPrice(new BigDecimal(itemsCostPrice[i]));
                    outBoundOrderItem.setAmount(new BigDecimal(itemsAmount[i]));
                    outBoundOrder.getItems().add(outBoundOrderItem);
                }
                //save the outBoundOrder information to outBoundOrder table
                outBoundOrderService.save(outBoundOrder);
                /*
                As in outBoundOrder/list.jsp line 21, in <form> tag, when the modelAttribute value is equal to qo,
                it means that it need to retrieve the value for qo in a HttpRequest domain.
                if you do not save the value for qo in request domain before entering the outBoundOrder/list.jsp,
                it will have a error called Neither BindingResult nor plain target object for bean name available as request attribute.
                To solve the problem, write a value for qo in request domain before redirect to outBoundOrder/list.jsp.
                Therefore, view.addObject("qo", qo) is used to solve this problem.
                */
                OutBoundOrderQueryObject qo = new OutBoundOrderQueryObject();
                view.addObject("qo", qo);
                //put Save successfully msg into saveMsg in session so as to it can display by jquery ui in msg.jsp
                view.addObject("saveMsg","Save successfully");
                //put outBoundOrder in domainName in session so that it can redirect to /outBoundOrder/list page
                view.addObject("domainName","outBoundOrder");
                return view;
            }else{
                //with outBoundOrder id not equals to null, update the outBound Order
                ModelAndView view = new ModelAndView("outBoundOrder/list");
                for(int i = 0; i < itemsProduct.length; i++){
                    //if the length of itemsProduct is greater than the length of itemsId, it means this is a new added item.
                    /*Case 1: itemsId == null, when user edit an outBoundOder and delete all the outBoundOrderItem inside the
                    outBoundOrder and click confirm, error will exist. However, the edited outBoundOrder will still exist and
                     the totalAmount and totalPrice of the outBoundOrder will be zero. When the user edit the outBoundOrder again,
                     as there aren't any items in a the outBound Order list, the input.jsp will show a jsp page with no itemsId
                     inside the page(PLease refer to input.jsp of outBoundOrder on line 116).
                     Therefore, when the user add new items in the outBoundOrder List, the data sent to backend will
                     not have itemsId and therefore itemsId will equal to null and will execute the below coding with the case
                     ItemsId == null.

                    Case 2: itemsId[i]=="" means it is clone row or the deleted row in a table and all values set to be ""
                    When user click edit an outBoundOrder, if the user just add new rows, as there are outBoundOrderItems in the list,
                    it will show jsp of the outBoundOrder with itemsId(please refer to input.jsp of outBoundOrder on line 145).
                    Another Case is when a user edit an outBoundOrder and delete all the items in an outBoundOrder, the user then
                    add new outBoundOrderItems, the itemsId of these new outBoundOrderItems == "".
                    Therefore, when the user add new items in the outBoundOrder list, the data sent to backend will have
                    itemsId == "" and will execute the below coding with the case ItemsId == "". It is also considered as
                    adding a new outBoundOrderItem in an outBoundOrder.

                    Case 3: For those existing items in outBoundOrder, if the user edit the outBoundOrder List and just amend
                    the details of the existing items, it will execute the below coding with the else case.
                     */
                    if( itemsId==null || (itemsId.length == 0)){
                        OutBoundOrderItem newOutBoundOrderItem = new OutBoundOrderItem();
                        //get product from the id of product
                        Product product = productService.get(Integer.parseInt(itemsProduct[i]));
                        //set product, cost price, amount, into outBoundOrderItem. Set outBoundOrderItem into outBoundOrder.
                        newOutBoundOrderItem.setProduct(product);
                        newOutBoundOrderItem.setCostPrice(new BigDecimal(itemsCostPrice[i]));
                        newOutBoundOrderItem.setAmount(new BigDecimal(itemsAmount[i]));
                        outBoundOrder.getItems().add(newOutBoundOrderItem);
                    }else if(itemsId[i]==""){
                        OutBoundOrderItem newOutBoundOrderItem = new OutBoundOrderItem();
                        //get product from the id of product
                        Product product = productService.get(Integer.parseInt(itemsProduct[i]));
                        //set product, cost price, amount, into outBoundOrderItem. Set outBoundOrderItem into outBoundOrder.
                        newOutBoundOrderItem.setProduct(product);
                        newOutBoundOrderItem.setCostPrice(new BigDecimal(itemsCostPrice[i]));
                        newOutBoundOrderItem.setAmount(new BigDecimal(itemsAmount[i]));
                        outBoundOrder.getItems().add(newOutBoundOrderItem);
                    }else{
                        //deal with existing outBoundOrderItem row in outBoundOrder, update the data if there is any amendment
                        OutBoundOrderItem outBoundOrderItem = outBoundOrderItemService.get(Integer.parseInt(itemsId[i]));
                        //get product from the id of product
                        Product product = productService.get(Integer.parseInt(itemsProduct[i]));
                        //set product, cost price, amount, into outBoundOrderItem. Set outBoundOrderItem into outBoundOrder.
                        outBoundOrderItem.setProduct(product);
                        outBoundOrderItem.setCostPrice(new BigDecimal(itemsCostPrice[i]));
                        outBoundOrderItem.setAmount(new BigDecimal(itemsAmount[i]));
                        outBoundOrder.getItems().add(outBoundOrderItem);
                    }
                }
                //update the outBoundOrder information to outBoundOrder table
                outBoundOrderService.update(outBoundOrder);
                //same as the above situation, setting qo value before redirecting to outBoundOrder/list.jsp
                OutBoundOrderQueryObject qo = new OutBoundOrderQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in outBoundOrder/list.jsp)
                view.addObject("updateMsg","Update successfully");
                view.addObject("domainName","outBoundOrder");
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

    //to delete an outBoundOrder
    @RequestMapping("/delete")
    public ModelAndView delete(OutBoundOrder outBoundOrder){
        //to set the page destination
        ModelAndView view = new ModelAndView("outBoundOrder/list");
        if(outBoundOrder.getId()!= null){
            //delete an outBoundOrder with outBoundOrder id
            outBoundOrderService.delete(outBoundOrder.getId());
        }
        //same as the above situation, setting qo value before redirecting to outBoundOrder/list.jsp
        OutBoundOrderQueryObject qo = new OutBoundOrderQueryObject();
        view.addObject("qo", qo);
        return view;
    }

    //prepare allProductList data that needed to be displayed in outBoundOrder/input.jsp
    @ModelAttribute("allProductList")
    public List<Product> getProductList(){
        List<Product> allProductList = new ArrayList<>();
        allProductList = productService.selectAll();
        return allProductList;
    }

    //to search whether there is any outBoundOrderItem under a outBoundOrder
    //the jsp displayed to the user for empty selectedItemList and not empty selectedItemList is different.
    //prepare selectedItemList data that needed to be displayed in outBoundOrder/input.jsp
    @ModelAttribute("selectedItemList")
    public List<OutBoundOrderItem> queryOutBoundOrderItemByOutBoundOrderId(Integer id){
        List<OutBoundOrderItem> result = new ArrayList<>();
        //to search all the outBoundOrderItems inside an outBoundOrder with its corresponding outBoundOrder id
        result = outBoundOrderItemService.queryOutBoundOrderItemByOutBoundOrderId(id);
        for(OutBoundOrderItem item: result){
            //find brandName information by outBoundOrderItem id and set the brandName information into product of the outBoundOrderItem
            BrandName brandName = brandNameService.queryBrandNameByOutBoundOrderItemId(item.getId());
            item.getProduct().setBrandName(brandName);
        }
        return result;
    }

    //to process audit procedures and check whether there is enough stock in warehouse for the outBoundOrderItems on outBoundOrder to left the warehouse
    @RequestMapping("/audit")
    @ResponseBody
    public Map audit(OutBoundOrder outBoundOrder){
        //hashMap is used to store inventory status, inventory amount and product name
        //the data sent to frontend jsp by ajax to have interaction with the user
        Map<String, Object> result = new HashMap<>();
        if(outBoundOrder.getId()!= null){
            //process audit procedures with outBoundOrder id
            result = outBoundOrderService.audit(outBoundOrder.getId());
        }
        return result;
    }

    //to redirect to outBoundOrder/read.jsp
    @RequestMapping("/read")
    public ModelAndView read(OutBoundOrder outBoundOrder){
        //to set the page destination
        ModelAndView view = new ModelAndView("outBoundOrder/read");
        if(outBoundOrder.getId()!= null){
            outBoundOrder = outBoundOrderService.get(outBoundOrder.getId());
        }
        //set the outBoundOrder object into ModelAndView object so that the outBoundOrder information can be display on outBoundOrder/read.jsp
        view.addObject("outBoundOrder", outBoundOrder);
        return view;
    }

    //prepare ConstructionSite data that needed to be displayed in outBoundOrder/input.jsp
    @ModelAttribute("siteList")
    public List<ConstructionSite> getSiteList(){
        List<ConstructionSite> siteList = new ArrayList<>();
        siteList = constructionSiteService.selectAll();
        return siteList;
    }
}
