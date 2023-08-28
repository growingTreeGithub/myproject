package org.construction.wms.web.controller;

import org.construction.wms.domain.*;
import org.construction.wms.query.EmployeeQueryObject;
import org.construction.wms.query.PageResult;
import org.construction.wms.service.DepartmentService;
import org.construction.wms.service.EmployeeService;
import org.construction.wms.service.PermissionService;
import org.construction.wms.service.RoleService;
import org.construction.wms.util.MD5Utils;
import org.construction.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    //to redirect the page to list.jsp
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") EmployeeQueryObject qo, HttpSession session){
        PageResult result = null;
        //return the search result of employee showing in a particular page
        result = employeeService.selectByCondition(qo);
        //set the pageResult into session so that the result can be displayed on employee/list.jsp
        session.setAttribute("pageResult", result);
        //redirect the page to employee/list
        return "employee/list";
    }

    //to redirect the page to input.jsp
    @RequestMapping("/input")
    public ModelAndView input(Employee employee){
        //to set the page destination
        ModelAndView view = new ModelAndView("employee/input");
        //if the id of employee is not null, it means it is a edit operation
        if(employee.getId()!= null){
            //get the employee information from the database by employee id
            employee = employeeService.get(employee.getId());
        }
        //set the employee object into ModelAndView object so that the employee information can be display on employee/input.jsp
        view.addObject("employee", employee);
        return view;
    }

    //to determine whether the user login success or not
    @RequestMapping("/login")
    @ResponseBody
    public Map login(String name, String password, HttpSession session){
        //this hashMap is used to store information need to return to login.jsp by ajax for interaction with user
        Map<String, Object> result = new HashMap<>();
        //to find if there are any existing user registered in database by searching using name and password entered by user
        Employee user = employeeService.queryByLogin(name, MD5Utils.getMd5(password));
        //if user exist, it means username and password is correct for the user and the user data is registered in the database.
        if(user != null){
            //save user data in session for usage in coding of the warehouse management system
            session.setAttribute(UserContext.USER_IN_SESSION, user);
            //to query user's permission list by obtaining user's role and then query different permissions from different roles
            List<Permission> userPermission = permissionService.queryPermissionByEmployeeId(user.getId());
            //set the user's permission list into session and will be used in PermissionUtils
            session.setAttribute(UserContext.PERMISSION_IN_SESSION, userPermission);
            //these information put in hashMap will be used in login.js to give different response
            result.put("success",true);
            //result.put("msg","login success");
        }else{
            //if user is null, it means username or password is incorrect for the user, or the user data does not exist in database.
            result.put("success",false);
            //result.put("msg","name or password is invalid");
        }
        return result;
    }

    //to save or update employee information into database
    @RequestMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(Employee employee, @RequestParam(value="roleArray",required=false) String[] roleArray){
        try{
            //if the id of employee is null, it is an save operation
            if(employee.getId() == null){
                //to set the page destination
                ModelAndView view = new ModelAndView("employee/list");
                //the value of the roleArray is obtained from the selectedRoleList in employee/input.jsp
                //if the roleArray is not null, it means that there are selected roles for the employee
                if(roleArray != null){
                    for(int i= 0 ; i < roleArray.length; i++){
                        //get the roles from the id of the roles
                        Role role = roleService.get(Integer.parseInt(roleArray[i]));
                        //set the roles into the employee
                        employee.getRoles().add(role);
                    }
                }
                //save the employee information to employee table
                employeeService.save(employee);
                /*
                As in employee/list.jsp line 21, in <form> tag, when the modelAttribute value is equal to qo,
                it means that it need to retrieve the value for qo in a HttpRequest domain.
                if you do not save the value for qo in request domain before entering the employee/list.jsp,
                it will have a error called Neither BindingResult nor plain target object for bean name available as request attribute.
                To solve the problem, write a value for qo in request domain before redirect to employee/list.jsp.
                Therefore, view.addObject("qo", qo) is used to solve this problem.
                */
                EmployeeQueryObject qo = new EmployeeQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in employee/list.jsp)
                view.addObject("saveMsg","Save successfully");
                view.addObject("domainName","employee");
                return view;
            }else{
                //if the id of employee is not null, it is a update operation
                //to set the page destination
                ModelAndView view = new ModelAndView("employee/list");
                //the value of the roleArray is obtained from the selectedRoleList in employee/input.jsp
                //if the roleArray is not null, it means that there are selected roles for the employee
                if(roleArray != null){
                    for(int i = 0; i < roleArray.length; i++){
                        //get the roles from the id of the roles
                        Role role = roleService.get(Integer.parseInt(roleArray[i]));
                        //set the roles into the employee
                        employee.getRoles().add(role);
                    }
                }
                //update the employee information to employee table
                employeeService.update(employee);
                //same as the above situation, setting qo value before redirecting to employee/list.jsp
                EmployeeQueryObject qo = new EmployeeQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in employee/list.jsp)
                view.addObject("updateMsg","Update successfully");
                view.addObject("domainName","employee");
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

    //to logically delete a employee(do not delete data in database)
    @RequestMapping("/delete")
    public ModelAndView delete(Employee employee){
        //to save the page destination
        ModelAndView view = new ModelAndView("employee/list");
        if(employee.getId()!= null){
            //to update the employee status into resigned so as to act as a logical deletion in database
            employeeService.updateState(employee.getId());
        }
        //same as the above situation, setting qo value before redirecting to employee/list.jsp
        EmployeeQueryObject qo = new EmployeeQueryObject();
        view.addObject("qo", qo);
        return view;
    }

    //redirect to read.jsp if an employee status is resigned
    @RequestMapping("/read")
    public ModelAndView read(Employee employee){
        //to save the page destination
        ModelAndView view = new ModelAndView("employee/read");
        if(employee.getId()!= null){
            //get the employee information from the database by employee id
            employee = employeeService.get(employee.getId());
        }
        //set the employee object into ModelAndView object so that the employee information can be display on employee/read.jsp
        view.addObject("employee", employee);
        return view;
    }

    //prepare department list data that need to be displayed in employee/input.jsp
    @ModelAttribute("deptList")
    public List<Department> getDeptList(){
        List<Department> deptList = new ArrayList<>();
        deptList = departmentService.selectAll();
        return deptList;
    }

    //prepare all role list data that need to be displayed in employee/input.jsp
    @ModelAttribute("allRoleList")
    public List<Role> getRoleList(){
        List<Role> allRoleList = new ArrayList<>();
        allRoleList = roleService.selectAll();
        return allRoleList;
    }

    //prepare selected role list data that need to be displayed in employee/input.jsp
    @ModelAttribute("selectedRoleList")
    public List<Role> queryRoleByEmployeeId(Integer id){
        List<Role> result = new ArrayList<>();
        //to check which roles that a employee has with its employee id
        result = roleService.queryRoleByEmployeeId(id);
        return result;
    }
}
