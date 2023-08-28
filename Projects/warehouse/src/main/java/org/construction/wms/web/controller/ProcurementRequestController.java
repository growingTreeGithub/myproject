package org.construction.wms.web.controller;

import org.construction.wms.domain.*;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.ProcurementRequestQueryObject;
import org.construction.wms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/procurementRequest")
public class ProcurementRequestController {
    @Autowired
    private ProcurementRequestService procurementRequestService;
    @Autowired
    private ProcurementRequestItemService procurementRequestItemService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandNameService brandNameService;

    //to redirect the page to list.jsp
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") ProcurementRequestQueryObject qo, HttpSession session){
        PageResult result = null;
        //return the search result of procurement request showing in a particular page
        result = procurementRequestService.selectByCondition(qo);
        //set the pageResult into session so that the result can be displayed on procurementRequest/list.jsp
        session.setAttribute("pageResult", result);
        //redirect the page to procurementRequest/list
        return "procurementRequest/list";
    }

    //to redirect the page to input.jsp
    @RequestMapping("/input")
    public ModelAndView input(ProcurementRequest procurementRequest){
        //to set the page destination
        ModelAndView view = new ModelAndView("procurementRequest/input");
        //if procurementRequest id is not null, it means that it is an edit operation, retrieve the data to display on input.jsp
        if(procurementRequest.getId()!= null){
            //get the procurementRequest information from the database by procurementRequest id
            procurementRequest = procurementRequestService.get(procurementRequest.getId());
        }
        //retrieve the data to display procurementRequest and serialNumber
        view.addObject("procurementRequest", procurementRequest);
        return view;
    }

    //to save or update procurementRequest information into database
    @RequestMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(ProcurementRequest procurementRequest, @RequestParam(value="itemsId",required=false) String[] itemsId,
                                     @RequestParam(value="itemsProduct") String[] itemsProduct, @RequestParam(value="itemsBrandName",required=false) String[] itemsBrandName,
                                     @RequestParam(value="itemsCostPrice") String[] itemsCostPrice,@RequestParam(value="itemsAmount") String[] itemsAmount,
                                     @RequestParam(value="itemsTotalPrice",required=false) String[] itemsTotalPrice){
        try{
            //with procurementRequest id equals to null, save the procurement request
            if(procurementRequest.getId() == null){
                //to set the page destination
                ModelAndView view = new ModelAndView("procurementRequest/list");
                //the value of the itemsProduct is obtained from the allProductList in procurementRequest/input.jsp
                for(int i = 0; i < itemsProduct.length; i++){
                    ProcurementRequestItem procurementRequestItem = new ProcurementRequestItem();
                    //get product from the id of product
                    Product product = productService.get(Integer.parseInt(itemsProduct[i]));
                    //the brandname of the product has returned from the backend, therefore the procurementRequestItem does not need to set brandName by using itemsBrandName
                    //set product, cost price, amount, into procurementRequestItem. Set procurementRequestItem into procurementRequest.
                    procurementRequestItem.setProduct(product);
                    procurementRequestItem.setCostPrice(new BigDecimal(itemsCostPrice[i]));
                    procurementRequestItem.setAmount(new BigDecimal(itemsAmount[i]));
                    procurementRequest.getItems().add(procurementRequestItem);
                }
                //save the procurementRequest information to procurementRequest table
                procurementRequestService.save(procurementRequest);
                /*
                As in procurementRequest/list.jsp line 21, in <form> tag, when the modelAttribute value is equal to qo,
                it means that it need to retrieve the value for qo in a HttpRequest domain.
                if you do not save the value for qo in request domain before entering the procurementRequest/list.jsp,
                it will have a error called Neither BindingResult nor plain target object for bean name available as request attribute.
                To solve the problem, write a value for qo in request domain before redirect to procurementRequest/list.jsp.
                Therefore, view.addObject("qo", qo) is used to solve this problem.
                */
                ProcurementRequestQueryObject qo = new ProcurementRequestQueryObject();
                view.addObject("qo", qo);
                //put Save successfully msg into saveMsg in session so as to it can display by jquery ui in msg.jsp
                view.addObject("saveMsg","Save successfully");
                //put procurementRequest in domainName in session so that it can redirect to /procurementRequest/list page
                view.addObject("domainName","procurementRequest");
                return view;
            }else{
                //with procurementRequest id not equals to null, update the procurement request
                ModelAndView view = new ModelAndView("procurementRequest/list");
                for(int i = 0; i < itemsProduct.length; i++){
                    //if the length of itemsProduct is greater than the length of itemsId, it means this is a new added item.
                    /*Case 1: itemsId == null, when user edit an procurementRequest and delete all the procurementRequestItem inside the
                    procurementRequest and click confirm, error will exist. However, the edited procurementRequest will still exist and
                     the totalAmount and totalPrice of the procurementRequest will be zero. When the user edit the procurementRequest again,
                     as there aren't any items in a the procurement request list, the input.jsp will show a jsp page with no itemsId
                     inside the page(PLease refer to input.jsp of procurementRequest on line 116).
                     Therefore, when the user add new items in the procurement request List, the data sent to backend will
                     not have itemsId and therefore itemsId will equal to null and will execute the below coding with the case
                     ItemsId == null.

                    Case 2: itemsId[i]=="" means it is clone row or the deleted row in a table and all values set to be ""
                    When user click edit an procurement request, if the user just add new rows, as there are procurementRequestItems in the list,
                    it will show jsp of the procurement request with itemsId(please refer to input.jsp of procurement request on line 145).
                    Another Case is when a user edit an procurementRequest and delete all the items in an procurementRequest, the user then
                    add new procurementRequestItems, the itemsId of these new procurementRequestItems == "".
                    Therefore, when the user add new items in the procurement request list, the data sent to backend will have
                    itemsId == "" and will execute the below coding with the case ItemsId == "". It is also considered as
                    adding a new procurementRequestItem in an procurementRequest.

                    Case 3: For those existing items in procurement request, if the user edit the procurement request List and just amend
                    the details of the existing items, it will execute the below coding with the else case.
                     */
                    if( itemsId==null || (itemsId.length == 0 )){
                        ProcurementRequestItem newProcurementRequestItem = new ProcurementRequestItem();
                        //get product from the id of product
                        Product product = productService.get(Integer.parseInt(itemsProduct[i]));
                        //set product, cost price, amount, into procurementRequestItem. Set procurementRequestItem into procurementRequest.
                        newProcurementRequestItem.setProduct(product);
                        newProcurementRequestItem.setCostPrice(new BigDecimal(itemsCostPrice[i]));
                        newProcurementRequestItem.setAmount(new BigDecimal(itemsAmount[i]));
                        procurementRequest.getItems().add(newProcurementRequestItem);
                    }else if(itemsId[i]==""){
                        ProcurementRequestItem newProcurementRequestItem = new ProcurementRequestItem();
                        //get product from the id of product
                        Product product = productService.get(Integer.parseInt(itemsProduct[i]));
                        //set product, cost price, amount, into procurementRequestItem. Set procurementRequestItem into procurementRequest.
                        newProcurementRequestItem.setProduct(product);
                        newProcurementRequestItem.setCostPrice(new BigDecimal(itemsCostPrice[i]));
                        newProcurementRequestItem.setAmount(new BigDecimal(itemsAmount[i]));
                        procurementRequest.getItems().add(newProcurementRequestItem);
                    }else{
                        //deal with existing procurementRequestItem row in procurementRequest, update the data if there is any amendment
                        ProcurementRequestItem procurementRequestItem = procurementRequestItemService.get(Integer.parseInt(itemsId[i]));
                        //get product from the id of product
                        Product product = productService.get(Integer.parseInt(itemsProduct[i]));
                        //set product, cost price, amount, into procurementRequestItem. Set procurementRequestItem into procurementRequest.
                        procurementRequestItem.setProduct(product);
                        procurementRequestItem.setCostPrice(new BigDecimal(itemsCostPrice[i]));
                        procurementRequestItem.setAmount(new BigDecimal(itemsAmount[i]));
                        procurementRequest.getItems().add(procurementRequestItem);
                    }
                }
                //update the procurementRequest information to procurementRequest table
                procurementRequestService.update(procurementRequest);
                //same as the above situation, setting qo value before redirecting to procurementRequest/list.jsp
                ProcurementRequestQueryObject qo = new ProcurementRequestQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in procurementRequest/list.jsp)
                view.addObject("updateMsg","Update successfully");
                view.addObject("domainName","procurementRequest");
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

    //to delete an procurementRequest
    @RequestMapping("/delete")
    public ModelAndView delete(ProcurementRequest procurementRequest){
        //to set the page destination
        ModelAndView view = new ModelAndView("procurementRequest/list");
        if(procurementRequest.getId()!= null){
            //delete an procurementRequest with procurementRequest id
            procurementRequestService.delete(procurementRequest.getId());
        }
        //same as the above situation, setting qo value before redirecting to procurementRequest/list.jsp
        ProcurementRequestQueryObject qo = new ProcurementRequestQueryObject();
        view.addObject("qo", qo);
        return view;
    }

    //prepare supplierList data that needed to be displayed in procurementRequest/input.jsp
    @ModelAttribute("supplierList")
    public List<Supplier> getSupplierList(){
        List<Supplier> supplierList = new ArrayList<>();
        supplierList = supplierService.selectAll();
        return supplierList;
    }

    //prepare allProductList data that needed to be displayed in procurementRequest/input.jsp
    @ModelAttribute("allProductList")
    public List<Product> getProductList(){
        List<Product> allProductList = new ArrayList<>();
        allProductList = productService.selectAll();
        return allProductList;
    }

    //to search whether there is any procurementRequestItem under a procurementRequest
    //the jsp displayed to the user for empty selectedItemList and not empty selectedItemList is different.
    //prepare selectedItemList data that needed to be displayed in procurementRequest/input.jsp
    @ModelAttribute("selectedItemList")
    public List<ProcurementRequestItem> queryProcurementRequestItemByProcurementRequestId(Integer id){
        List<ProcurementRequestItem> result = new ArrayList<>();
        //to search all the procurementRequestItems inside an procurementRequest with its corresponding procurementRequest id
        result = procurementRequestItemService.queryProcurementRequestItemByProcurementRequestId(id);
        for(ProcurementRequestItem item: result){
            //find brandName information by procurementRequestItem id and set the brandName information into product of the procurementRequestItem
            BrandName brandName = brandNameService.queryBrandNameByProcurementRequestItemId(item.getId());
            item.getProduct().setBrandName(brandName);
        }
        return result;
    }

    //to process purchase procedures
    @RequestMapping("/purchase")
    public ModelAndView purchase(ProcurementRequest procurementRequest){
        //to set the page destination
        ModelAndView view = new ModelAndView("procurementRequest/list");
        if(procurementRequest.getId()!= null){
            //process purchase procedures with procurementRequest id
            procurementRequestService.purchase(procurementRequest.getId());
        }
        //same as the above situation, setting qo value before redirecting to procurementRequest/list.jsp
        ProcurementRequestQueryObject qo = new ProcurementRequestQueryObject();
        view.addObject("qo", qo);
        return view;
    }

    //to redirect to procurementRequest/read.jsp
    @RequestMapping("/read")
    public ModelAndView read(ProcurementRequest procurementRequest){
        //to set the page destination
        ModelAndView view = new ModelAndView("procurementRequest/read");
        if(procurementRequest.getId()!= null){
            procurementRequest = procurementRequestService.get(procurementRequest.getId());
        }
        //set the procurementRequest object into ModelAndView object so that the procurement request information can be display on procurementRequest/read.jsp
        view.addObject("procurementRequest", procurementRequest);
        return view;
    }

}
