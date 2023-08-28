package org.construction.wms.util;

import javax.servlet.http.HttpServletRequest;

public class UserContext {
    //UserContext.USER_IN_SESSION equals "userInSession"
    public static final String USER_IN_SESSION = "userInSession";
    //UserContext.PERMISSION_IN_SESSION equals "permissionInSession"
    public static final String PERMISSION_IN_SESSION = "permissionInSession";
    /*
    * In the context of a web application, each request is typically processed by a separate thread
    * on the server side. However, various components or layers of the application may need access to the
    * request object, such as service classes, utility classes.
    * By storing the request object in a ThreadLocal variable, the request object is accessible to
    * any code running within the same thread. The ThreadLocal class provides a container that holds
    * separate instances of an object for each thread. Each thread can access and modify its own copy
    * of the object without interfering with other threads.
    * By using ThreadLocal in this manner, the request object is accessible from any part of the code
    * that is running within the same thread, without having to pass it explicitly as a method parameter.
    * */
    private static ThreadLocal<HttpServletRequest> local = new ThreadLocal<HttpServletRequest>();
    //getter and setter method to get and set request in ThreadLocal
    public static void set(HttpServletRequest request){
        local.set(request);
    }
    public static HttpServletRequest get(){
        return local.get();
    }
}
