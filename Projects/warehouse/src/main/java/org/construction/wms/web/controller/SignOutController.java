package org.construction.wms.web.controller;

import org.construction.wms.util.UserContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class SignOutController {

    //it is used to logout the user from the warehouse management system and redirect to login.jsp
    @RequestMapping("/logout")
    public void logout(HttpServletResponse response) throws Exception {
        UserContext.get().getSession().invalidate();
        response.sendRedirect("/login.jsp");
    }
}
