package org.construction.wms.web.controller;

import org.construction.wms.domain.Permission;
import org.construction.wms.domain.Role;
import org.construction.wms.query.PageResult;
import org.construction.wms.query.RoleQueryObject;
import org.construction.wms.service.PermissionService;
import org.construction.wms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    //to redirect the page to list.jsp
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") RoleQueryObject qo, HttpSession session){
        PageResult result = null;
        //return the search result of role showing in a particular page
        result = roleService.selectByCondition(qo);
        //set the pageResult into session so that the result can be displayed on role/list.jsp
        session.setAttribute("pageResult", result);
        //redirect the page to role/list
        return "role/list";
    }

    //to redirect the page to input.jsp
    @RequestMapping("/input")
    public ModelAndView input(Role role){
        //to set the page destination
        ModelAndView view = new ModelAndView("role/input");
        //if the id of role is not null, it means it is a edit operation
        if(role.getId()!= null){
            //get the role information from the database by role id
            role = roleService.get(role.getId());
        }
        //set the role object into ModelAndView object so that the role information can be display on role/input.jsp
        view.addObject("role", role);
        return view;
    }

    //to save or update role information into database
    @RequestMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(@ModelAttribute("role") Role role, @RequestParam(value="permissionArray",required=false) String[] permissionArray){
        try{
            //if the id of role is null, it is an save operation
            if(role.getId() == null){
                //to set the page destination
                ModelAndView view = new ModelAndView("role/list");
                //the value of the permissionArray is obtained from the selectedPermissionList in role/input.jsp
                //if the permissionArray is not null, it means that there are selected permissions for the role
                if(permissionArray != null){
                    for(int i= 0 ; i < permissionArray.length; i++){
                        //get the permission from the id of the permission
                        Permission permission = permissionService.get(Integer.parseInt(permissionArray[i]));
                        //set permission into the role
                        role.getPermissions().add(permission);
                    }
                }
                //save the role information to role table
                roleService.save(role);
                /*
                As in role/list.jsp line 19, in <form> tag, when the modelAttribute value is equal to qo,
                it means that it need to retrieve the value for qo in a HttpRequest domain.
                if you do not save the value for qo in request domain before entering the role/list.jsp,
                it will have a error called Neither BindingResult nor plain target object for bean name available as request attribute.
                To solve the problem, write a value for qo in request domain before redirect to role/list.jsp.
                Therefore, view.addObject("qo", qo) is used to solve this problem.
                */
                RoleQueryObject qo = new RoleQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 18 in role/list.jsp)
                view.addObject("saveMsg","Save successfully");
                view.addObject("domainName","role");
                return view;
            }else{
                //if the id of role is not null, it is a update operation
                //to set the page destination
                ModelAndView view = new ModelAndView("role/list");
                //the value of the permissionArray is obtained from the selectedPermissionList in role/input.jsp
                //if the permissionArray is not null, it means that there are selected permissions for the role
                if(permissionArray != null){
                    for(int i= 0 ; i < permissionArray.length; i++){
                        //get the permission from the id of the permission
                        Permission permission = permissionService.get(Integer.parseInt(permissionArray[i]));
                        //set permission into the role
                        role.getPermissions().add(permission);
                    }
                }
                //update the role information to role table
                roleService.update(role);
                //same as the above situation, setting qo value before redirecting to role/list.jsp
                RoleQueryObject qo = new RoleQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 18 in role/list.jsp)
                view.addObject("updateMsg","Update successfully");
                view.addObject("domainName","role");
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

    //to delete an role
    @RequestMapping("/delete")
    public ModelAndView delete(Role role){
        //to set the page destination
        ModelAndView view = new ModelAndView("role/list");
        if(role.getId()!= null){
            //delete an role with role id
            roleService.delete(role.getId());
        }
        //same as the above situation, setting qo value before redirecting to role/list.jsp
        RoleQueryObject qo = new RoleQueryObject();
        view.addObject("qo", qo);
        return view;
    }

    //prepare allPermissionlist data that need to be displayed in role/input.jsp
    @ModelAttribute("allPermissionList")
    public List<Permission> getPermissionList(){
        List<Permission> allPermissionList = new ArrayList<>();
        allPermissionList = permissionService.selectAll();
        return allPermissionList;
    }

    //prepare selected permission list data that need to be displayed in role/input.jsp
    @ModelAttribute("selectedPermissionList")
    public List<Permission> queryPermissionByRoleId(Integer id){
        List<Permission> result = new ArrayList<>();
        //return all permission that a role has
        result = permissionService.queryPermissionByRoleId(id);
        return result;
    }
}
