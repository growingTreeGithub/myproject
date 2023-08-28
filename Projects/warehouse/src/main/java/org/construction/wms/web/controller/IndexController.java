package org.construction.wms.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    //it is used to redirect to the main page of the warehouse management system
    @RequestMapping("/main")
    public String main(){return "main";}

    //it is used to redirect to nopermission page when a user does not have permission to visit a page
    @RequestMapping("/nopermission")
    public String nopermission(){
        return "common/nopermission";
    }

}
