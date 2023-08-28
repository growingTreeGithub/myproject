<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="permissionFn" uri="http://construction.org/wms"%>
<html>
<head>
    <title>Warehouse Management System MainPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/main.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/mainpage.js"></script>
</head>
<body>
<div id="warehouse">
    <div id="header">
        <h1 id="title">Warehouse Management System</h1>
        <c:url var="logout" value="/logout"/>
        <span class="button_logout" data-url="<c:out value="${logout}"/>">Logout</span>
    </div>
    <div id="main">
        <div id="left">
            <ul>
                <li>
                    <c:url var="brandName_list" value="/brandName/list">
                    </c:url>
                    <%--the target attribute in <a:href> tag specifies where the linked content should be opened when the user clicks
                    on the link. target="mainFrame" indicates that the linked content should be displayed in a iframe with the name="mainFrame".--%>
                    <%--This is the main title Basic Warehouse info. It redirect to the list of brandName info when clicking this main title.--%>
                    <a href="<c:out value="${brandName_list}"/>" target="mainFrame" class="basic_main_title">Basic Warehouse Info</a>
                    <img src="/asset/images/chevron-down.svg" class="basic_arrow"/>
                    <%--subtitles under main title Basic Warehouse Info, clicking main title Basic Warehouse Info, the below subtitles
                    will appear if the user has permission. It will show no permission to operate if the user do not have permission to operate
                    the below modules.--%>
                    <ul>
                        <%--if the user has the permission to see the list of brandName, show the page of list of brandName info.--%>
                        <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.BrandNameController:list')}">
                            <li>
                                <c:url var="brandName_list" value="/brandName/list">
                                </c:url>
                                <a href="<c:out value="${brandName_list}"/>" target="mainFrame" class="basic_sub_title"> - BrandName Info</a>
                            </li>
                        </c:if>
                            <%--if the user has the permission to see the list of supplier, show the page of list of supplier info--%>
                        <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.SupplierController:list')}">
                            <li>
                                <c:url var="supplier_list" value="/supplier/list">
                                </c:url>
                                <a href="<c:out value="${supplier_list}"/>" target="mainFrame" class="basic_sub_title"> - Supplier Info</a>
                            </li>
                        </c:if>
                            <%--if the user has the permission to see the list of product, show the page of list of product info--%>
                        <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.ProductController:list')}">
                            <li>
                                <c:url var="product_list" value="/product/list">
                                </c:url>
                                <a href="<c:out value="${product_list}"/>" target="mainFrame" class="basic_sub_title"> - Product Info</a>
                            </li>
                        </c:if>
                            <%--if the user has the permission to see the list of construction site, show the page of list of construction site info--%>
                        <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.ConstructionSiteController:list')}">
                            <li>
                                <c:url var="constructionSite_list" value="/constructionSite/list">
                                </c:url>
                                <a href="<c:out value="${constructionSite_list}"/>" target="mainFrame" class="basic_sub_title"> - Construction Site Info</a>
                            </li>
                        </c:if>
                    </ul>
                </li>
                <li id="stockListItem">
                    <c:url var="procurementRequest_list" value="/procurementRequest/list">
                    </c:url>
                    <%--This is the main title stock management. It redirect to the list of procurement request info when clicking this main title.--%>
                    <a href="<c:out value="${procurementRequest_list}"/>" target="mainFrame" class="stock_main_title">Stock Management</a>
                    <img src="/asset/images/chevron-down.svg" class="stock_arrow"/>
                    <%--subtitles under main title stock management, clicking main title stock management, the below subtitles
                    will appear if the user has permission. It will show no permission to operate if the user do not have permission to operate
                    the below modules.--%>
                    <ul>
                        <%--if the user has the permission to see the list of procurement request info, show the page of list of procurement request info.--%>
                        <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.ProcurementRequestController:list')}">
                            <li>
                                <c:url var="procurementRequest_list" value="/procurementRequest/list">
                                </c:url>
                                <a href="<c:out value="${procurementRequest_list}"/>" target="mainFrame" class="stock_sub_title"> - Procurement Request Info</a>
                            </li>
                        </c:if>
                    </ul>
                    <ul>
                        <%--if the user has the permission to see the list of inBound Order info, show the page of list of inBound Order info.--%>
                        <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.InBoundOrderController:list')}">
                            <li>
                                <c:url var="inBoundOrder_list" value="/inBoundOrder/list">
                                </c:url>
                                <a href="<c:out value="${inBoundOrder_list}"/>" target="mainFrame" class="stock_sub_title"> - InBound Order Info</a>
                            </li>
                        </c:if>
                    </ul>
                    <ul>
                        <%--if the user has the permission to see the list of outBound Order info, show the page of list of outBound Order info.--%>
                        <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.OutBoundOrderController:list')}">
                            <li>
                                <c:url var="outBoundOrder_list" value="/outBoundOrder/list">
                                </c:url>
                                <a href="<c:out value="${outBoundOrder_list}"/>" target="mainFrame" class="stock_sub_title"> - OutBound Order Info</a>
                            </li>
                        </c:if>
                    </ul>
                    <ul>
                        <%--if the user has the permission to see the inventory report, show the page of inventory report.--%>
                        <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.InventoryController:list')}">
                            <li>
                                <c:url var="inventory_list" value="/inventory/list">
                                </c:url>
                                <a href="<c:out value="${inventory_list}"/>" target="mainFrame" class="stock_sub_title"> - Inventory Report</a>
                            </li>
                        </c:if>
                    </ul>
                </li>
                <li id="permissionListItem">
                    <c:url var="employee_list" value="/employee/list">
                    </c:url>
                    <%--This is the main title permission management. It redirect to the list of employee info when clicking this main title.--%>
                    <a href="<c:out value="${employee_list}"/>" target="mainFrame" class="permission_main_title">Permission Management</a>
                    <img src="/asset/images/chevron-down.svg" class="permission_arrow"/>
                    <%--subtitles under main title permission management, clicking main title permission management, the below subtitles
                    will appear if the user has permission. It will show no permission to operate if the user do not have permission to operate
                    the below modules.--%>
                    <ul>
                        <%--if the user has the permission to see the list of employee info, show the page of list of employee info.--%>
                        <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.EmployeeController:list')}">
                            <li>
                                <c:url var="employee_list" value="/employee/list">
                                </c:url>
                                <a href="<c:out value="${employee_list}"/>" target="mainFrame" class="permission_sub_title"> - Employee Info</a>
                            </li>
                        </c:if>
                            <%--if the user has the permission to see the list of department info, show the page of list of department info.--%>
                        <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.DepartmentController:list')}">
                            <li>
                                <c:url var="department_list" value="/department/list">
                                </c:url>
                                <a href="<c:out value="${department_list}"/>" target="mainFrame" class="permission_sub_title"> - Department Info</a>
                            </li>
                        </c:if>
                            <%--if the user has the permission to see the list of role info, show the page of list of role info.--%>
                        <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.RoleController:list')}">
                            <li>
                                <c:url var="role_list" value="/role/list">
                                </c:url>
                                <a href="<c:out value="${role_list}"/>" target="mainFrame" class="permission_sub_title"> - Role Info</a>
                            </li>
                        </c:if>
                            <%--if the user has the permission to see the list of permission info, show the page of list of permission info.--%>
                        <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.PermissionController:list')}">
                            <li>
                                <c:url var="permission_list" value="/permission/list">
                                </c:url>
                                <a href="<c:out value="${permission_list}"/>" target="mainFrame" class="permission_sub_title"> - Permission Info</a>
                            </li>
                        </c:if>
                    </ul>

                </li>
            </ul>
        </div>
        <div id="right">
            <%--iframe are HTML elements that allow you to divide a web page into multiple sections or windows.
            The iframe tag is used when content from another domain or web page need to be included within the jsp page.--%>
            <iframe name="mainFrame" frameborder="0" width="100%" height="100%"/>
        </div>
    </div>
</div>
</body>
</html>
