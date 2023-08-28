package org.construction.wms.web.controller;


import org.construction.wms.domain.BrandName;
import org.construction.wms.domain.Product;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.ProductQueryObject;
import org.construction.wms.service.BrandNameService;
import org.construction.wms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandNameService brandNameService;

    //to redirect the page to list.jsp
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") ProductQueryObject qo, HttpSession session){
        PageResult result = null;
        //return the search result of product showing in a particular page
        result = productService.selectByCondition(qo);
        //set the pageResult into session so that the result can be displayed on product/list.jsp
        session.setAttribute("pageResult", result);
        //redirect the page to product/list
        return "product/list";
    }

    //to redirect the page to input.jsp
    @RequestMapping("/input")
    public ModelAndView input(Product product){
        //to set the page destination
        ModelAndView view = new ModelAndView("product/input");
        //if the id of product is not null, it means it is a edit operation
        if(product.getId()!= null){
            //get the product information from the database by product id
            product = productService.get(product.getId());
        }
        //set the product object into ModelAndView object so that the product information can be display on product/input.jsp
        view.addObject("product", product);
        return view;
    }

    //to save or update product information into database
    @RequestMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(Product product){
        try{
            //if the id of product is null, it is a save operation
            if(product.getId() == null){
                //to set the page destination
                ModelAndView view = new ModelAndView("product/list");
                //save the product information to product table
                productService.save(product);
                /*
                As in product/list.jsp line 21, in <form> tag, when the modelAttribute value is equal to qo,
                it means that it need to retrieve the value for qo in a HttpRequest domain.
                if you do not save the value for qo in request domain before entering the product/list.jsp,
                it will have a error called Neither BindingResult nor plain target object for bean name available as request attribute.
                To solve the problem, write a value for qo in request domain before redirect to product/list.jsp.
                Therefore, view.addObject("qo", qo) is used to solve this problem.
                */
                ProductQueryObject qo = new ProductQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in product/list.jsp)
                view.addObject("saveMsg","Save successfully");
                view.addObject("domainName","product");
                return view;
            }else{
                //if the id of product is not null, it is a update operation
                //to set the page destination
                ModelAndView view = new ModelAndView("product/list");
                //update the product information to product table
                productService.update(product);
                //same as the above situation, setting qo value before redirecting to product/list.jsp
                ProductQueryObject qo = new ProductQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in product/list.jsp)
                view.addObject("updateMsg","Update successfully");
                view.addObject("domainName","product");
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

    //to delete a product
    @RequestMapping("/delete")
    @ResponseBody
    public int delete(Product product){
        //connection is used to store value return from productService to determine whether the deletion success or not
        int connection = 0;
        if(product.getId()!= null){
            /*return 1 by ajax to tell the user to delete all the data related to this product so that
            the user can delete the product successfully.(please refer to line 51 in product.js file )

            return 0 by ajax to tell the user the product has been deleted successfully
            */
            connection = productService.delete(product.getId());
        }
        return connection;
    }

    //prepare brandNameList data that needed to be displayed in product/input.jsp
    @ModelAttribute("brandNameList")
    public List<BrandName> getBrandNameList(){
        List<BrandName> brandNameList = new ArrayList<>();
        brandNameList = brandNameService.selectAll();
        return brandNameList;
    }

}
