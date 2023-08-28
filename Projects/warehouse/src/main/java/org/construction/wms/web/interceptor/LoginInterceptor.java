package org.construction.wms.web.interceptor;

import org.construction.wms.domain.Employee;
import org.construction.wms.util.PermissionUtils;
import org.construction.wms.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class LoginInterceptor implements HandlerInterceptor{
        //preHandle method gets invoked before the actual handler method is executed.
        //it can be used to perform pre-processing tasks or make decisions based on the incoming request before the handler method is invoked.
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
            //to make use of the threadLocal in UserContext, set the request object at the beginning of the request processing flow
            UserContext.set(request);
            //to check whether a request is invoked by a user who have already login the system or not
            //because some request may be invoked by just entering url in browser and does not login the system to control
            Employee user = (Employee) request.getSession().getAttribute(UserContext.USER_IN_SESSION);
            //if the user is null, it means the user does not login the system
            //redirect to login.jsp and stop the request from further processing
            if(user == null){
                response.sendRedirect("/login.jsp");
                return false;
            }

            //to control permission by url
            //1. to transform request into permission expression
            /*
            the handler parameter in the preHandle method represents the handler method that is going to
            be executed for the current request.
            the handler object will typically be an instance of HandlerMethod. It provides information about
            the controller class, method details, annotations.
            * */
               HandlerMethod handlerMethod = (HandlerMethod) handler;
               //use function in HandlerMethod to get Bean object and method object
               Object bean = handlerMethod.getBean();
               Method method = handlerMethod.getMethod();
               //use reflection technique to get the fully qualified name of the class and the name of a method
               String expression = bean.getClass().getName() +":"+method.getName();
               //if the result is true, that means the user has the permission or the request is not constricted by permission.
               //if the result is false, the request is intercepted.
               if(PermissionUtils.checkPermission(expression)){
                   return true;
               }else{
                   /*
                   redirect to nopermission.jsp and return false indicates the processing of the current request
                   should stop and no further processing should occur.
                   * */
                   response.sendRedirect("/nopermission");
                   return false;
               }
        }
}
