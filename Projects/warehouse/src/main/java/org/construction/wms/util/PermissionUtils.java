package org.construction.wms.util;

import org.construction.wms.domain.Employee;
import org.construction.wms.domain.Permission;
import org.construction.wms.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;

@Component
public class PermissionUtils {
    private static PermissionService permissionService;

    //a function used with core tag in jsp to determine whether a user has permission to do operation in warehouse management system
    /*to check permission expression on jsp with the permission expression of a user which stored in permission table
    to determine a user has permission to do certain operation on jsp
    * */
    public static boolean checkPermission(String expression) {
        //to get session
        HttpSession session = UserContext.get().getSession();
        //to get user which was stored in session when the user login the system
        Employee user = (Employee) session.getAttribute(UserContext.USER_IN_SESSION);
        //if the user already resigned from the company, the user does not have permission to do any operation
        if(user.getStatus() == Employee.getRESIGNED()){
            return false;
        }
        //if user is admin, the user has permission to do anything in the warehouse management system
        if(user.isAdmin()){
            return true;
        }
        //to check permission table to see whether it needs permission control, to check whether the expression exist in permission table
        Permission permission = permissionService.queryByExpression(expression);
        //if it needs permission control, the permission expression will exist in permission table.
        //One of the example of expression is org.construction.wms.web.controller.EmployeeController:list
        if(permission != null){
            //the request needs permission control
            //to get employee's permission from session which was stored in session when the employee login the system
            List<Permission> userPermissionList= (List<Permission>) UserContext.get().getSession().getAttribute(UserContext.PERMISSION_IN_SESSION);
            //to split the expression and make the expression end with all.For example, org.construction.wms.web.controller.EmployeeController:all
            //permission expression end with all means that the user have all permission to deal with a specific module. For example, employee module
            String allExpression = expression.split(":")[0] + ":" + "all";
            for (Permission userPermission : userPermissionList) {
                // For a expression of this operation which it exactly appears in this user's permission list. For example, org.construction.wms.web.controller.EmployeeController:list
                // Or a expression of this operation end with all which it exactly appears in this user's permission list. For example, org.construction.wms.web.controller.EmployeeController:all
                if(userPermission.getExpression().equals(expression) || userPermission.getExpression().equals(allExpression)){
                    //if equals, it means user have the permission
                    return true;
                }
            }
            //if not equals, it means the user does not have the permission
            return false;
        }else {
            //if permission is null, the expression does not exist in permission table
            //the request does not need permission control and allowed to pass interceptor
            return true;
        }
    }
    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        PermissionUtils.permissionService = permissionService;
    }
}
