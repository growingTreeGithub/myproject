package org.construction.wms.web.controller;

import org.construction.wms.domain.Department;
import org.construction.wms.query.DepartmentQueryObject;
import org.construction.wms.query.PageResult;
import org.construction.wms.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    //to redirect the page to list.jsp
    @RequestMapping("/list")
    public String list(@ModelAttribute("qo") DepartmentQueryObject qo, HttpSession session){
        PageResult result = null;
        //return the search result of the department showing in a particular page
        result = departmentService.selectByCondition(qo);
        //set the pageResult into session so that the result can be displayed on department/list.jsp
        session.setAttribute("pageResult", result);
        //redirect the page to department/list
        return "department/list";
    }

    //to redirect the page to input.jsp
    @RequestMapping("/input")
    public ModelAndView input(Department department){
        //to set the page destination
        ModelAndView view = new ModelAndView("department/input");
        //if the id of department is not null, it means it is a edit operation
        if(department.getId()!= null){
            //get the department information from the database by department id
            department = departmentService.get(department.getId());
        }
        //set the department object into ModelAndView object so that the department information can be display on department/input.jsp
        view.addObject("department", department);
        return view;
    }

    //to save or update department information into database
    @RequestMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(Department department){
        try{
            //if the id of department is null, it is a save operation
            if(department.getId() == null){
                //to set the page destination
                ModelAndView view = new ModelAndView("department/list");
                //save the department information to department table
                departmentService.save(department);
                /*
                As in department/list.jsp line 21, in <form> tag, when the modelAttribute value is equal to qo,
                it means that it need to retrieve the value for qo in a HttpRequest domain.
                if you do not save the value for qo in request domain before entering the department/list.jsp,
                it will have a error called Neither BindingResult nor plain target object for bean name available as request attribute.
                To solve the problem, write a value for qo in request domain before redirect to department/list.jsp.
                Therefore, view.addObject("qo", qo) is used to solve this problem.
                */
                DepartmentQueryObject qo = new DepartmentQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in department/list.jsp)
                view.addObject("saveMsg","Save successfully");
                view.addObject("domainName","department");
                return view;
            }else{
                //if the id of department is not null, it is a update operation
                //to set the page destination
                ModelAndView view = new ModelAndView("department/list");
                //update the department information to department table
                departmentService.update(department);
                //same as the above situation, setting qo value before redirecting to department/list.jsp
                DepartmentQueryObject qo = new DepartmentQueryObject();
                view.addObject("qo", qo);
                //it is used to save success msg and domainName for the plugin jquery UI in msg.jsp(Please refer to line 20 in department/list.jsp)
                view.addObject("updateMsg","Update successfully");
                view.addObject("domainName","department");
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

    //to delete a department
    @RequestMapping("/delete")
    @ResponseBody
    public int delete(Department department){
        //connection is used to store value return from departmentService to determine whether the deletion success or not
        int connection = 0;
        if(department.getId()!= null){
            /*if there is employee using department information, return 1 to inform user in frontend to delete all the
            employee that are using this department information so that the deletion of this department will not result
            in data loss in employee info module

            return 0 by ajax that there aren't any employee using this department information so that
            the department information can be deleted directly
            */
            connection = departmentService.delete(department.getId());
        }
        return connection;
    }
}
