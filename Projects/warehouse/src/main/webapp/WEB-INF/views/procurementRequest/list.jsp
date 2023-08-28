<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="permissionFn" uri="http://construction.org/wms"%>
<html>
<head>
    <title>Procurement Request ListPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/list.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.structure.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.theme.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.js"></script>
    <script type="text/javascript" src="/asset/js/common.js"></script>
    <script type="text/javascript" src="/asset/js/domain/procurementRequest.js"></script>
</head>
<body>
    <%@include file="/WEB-INF/views/common/msg.jsp"%>
    <form:form id="searchForm" action="/procurementRequest/list" method="POST" modelAttribute="qo">
        <div class="listForm">
            <div class="searchBox">
                <div class="searchTitle">
                    SearchBox
                </div>
                <div class="searchCriteria">
                    <div id="searchRegion">
                        Search By startDate: <form:input type="date" path="startDate" cssClass="input_ui"/>
                        Search By endDate: <form:input type="date" path="endDate" cssClass="input_ui"/>
                        Search By Supplier Name: <form:input path="keyword"/>
                    </div>
                </div>
                <div class="searchButton">
                    <input type="submit" value="Search" class="button_redirect"/>
                </div>
            </div>
            <div id="domainList">
                <span id="domainTitle">List of Procurement Request</span>
                <c:url var="add" value="/procurementRequest/input"/>
                <input type="button" value="Add a Procurement Request" class="button_redirect" data-url="<c:out value="${add}"/>"/>
            </div>
            <div class="tableResult">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Serial Number</th>
                            <th>inputDate</th>
                            <th>supplier</th>
                            <th>purchased totalAmount</th>
                            <th>purchased totalPrice</th>
                            <th>inputUser</th>
                            <th>merchandiser</th>
                            <th>status</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="procurementRequest" items="${pageResult.result}">
                            <tr>
                                <td><c:out value="${procurementRequest.serialNumber}"/></td>
                                <td>
                                        <%--<fmt:formatDate> tag is used to format the value of the procurementRequest variable.
                                                    The pattern attribute is set to "yyyy-MM-dd", which represents the desired date format.
                                                    The resulting formatted date is then displayed on the page.
                                                    The var attribute is used to assign the formatted date to the variable inputDate.The variable
                                                    inputDate can be used elsewhere in jsp page to access the formatted date value or display
                                                    the formatted date in different parts of the jsp page.--%>
                                    <fmt:formatDate value="${procurementRequest.inputDate}" var="inputDate" pattern="yyyy-MM-dd" />
                                                <%--c:out tag is used to output the value of an expression.
                                                    Attribute value specifies the expression or variable whose value needs to be displayed.
                                                    It can be any valid JSP expression or a variable.--%>
                                    <c:out value="${inputDate}"/>
                                </td>
                                <td><c:out value="${procurementRequest.supplier.name}"/></td>
                                <td><c:out value="${procurementRequest.totalAmount}"/></td>
                                <td><c:out value="${procurementRequest.totalPrice}"/></td>
                                <td><c:out value="${procurementRequest.inputUser.name}"/></td>
                                <td><c:out value="${procurementRequest.merchandiser.name}"/></td>
                                <td>
                                        <%--The <c:set> tag is executed, and a variable named status is created. The value of the variable
                                                is obtained evaluating the procurementRequest.status expression using EL. The value is then assigned to the
                                                status variable. The <c:out> tag is executed, and it outputs the value of the status variable on the
                                                page.--%>
                                    <c:set var="status"><c:out value="${procurementRequest.status}"/></c:set>
                                    <c:choose>
                                        <%--if the value of the above status variable is equal to 0, 0 means the items on procurementRequest is not purchased.
                                        Show NOT PURCHASED on the page.--%>
                                        <c:when test="${status == 0}">
                                            <span style="color:red">NOT PURCHASED</span>
                                        </c:when>
                                        <%--otherwise, if the value of the above status variable is not equal to 0, the items on procurementRequest have been purchased.
                                        Show PURCHASED on the page.--%>
                                        <c:otherwise>
                                            <span style="color:green">PURCHASED</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                        <%--Show Purchased, Edit, Delete, Read link to user with different permission--%>
                                    <c:set var="status"><c:out value="${procurementRequest.status}"/></c:set>
                                    <c:choose>
                                        <%--if the value of the above status variable is equal to 0, 0 means the items on procurement request is not purchased.
                                        Show Purchased, Edit, Delete link on the page to user with permission to access.--%>
                                        <c:when test="${status == 0}">
                                            <%--if the user has permission to purchase the items on a procurementRequest, show PURCHASED link on the page.
                                            Otherwise, do not show PURCHASED link on the page.--%>
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.ProcurementRequestController:purchase')}">
                                                <c:url var="purchase" value="/procurementRequest/purchase">
                                                    <c:param name="id" value="${procurementRequest.id}"/>
                                                </c:url>
                                                <a href="javascript:;"  class="button_purchase" data-url="<c:out value="${purchase}"/>">Purchased</a>
                                            </c:if>
                                            <%--if the user has permission to edit a procurementRequest, show EDIT link on the page.
                                            Otherwise, do not show EDIT link on the page.--%>
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.ProcurementRequestController:input')}">
                                                <c:url var="edit" value="/procurementRequest/input">
                                                    <c:param name="id" value="${procurementRequest.id}"/>
                                                </c:url>
                                                <a href="<c:out value="${edit}"/>">Edit</a>
                                            </c:if>
                                            <%--if the user has permission to delete a procurementRequest, show DELETE link on the page.
                                            Otherwise, do not show DELETE link on the page.--%>
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.ProcurementRequestController:delete')}">
                                                <c:url var="delete" value="/procurementRequest/delete">
                                                    <c:param name="id" value="${procurementRequest.id}"/>
                                                </c:url>
                                                <a href="javascript:;"  class="button_delete" data-url="<c:out value="${delete}"/>">Delete</a>
                                            </c:if>
                                        </c:when>
                                        <%--Otherwise, if the value of the above status variable is not equal to 0, the items on procurment request have all been purchased.
                                        Show Read link on the page to user with permission to access. Do not show Purchased, Edit, Delete link when the items
                                        on procurement request have all been purchased.--%>
                                        <c:otherwise>
                                            <%--if the user has permission to read a purchased procurement request, show Read link on the page.
                                            Otherwise, do not show Read link on the page.--%>
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.ProcurementRequestController:read')}">
                                                <c:url var="read" value="/procurementRequest/read">
                                                    <c:param name="id" value="${procurementRequest.id}"/>
                                                </c:url>
                                                <a href="<c:out value="${read}"/>">Read</a>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="pagination">
                <%@include file="/WEB-INF/views/common/pagination.jsp"%>
            </div>
        </div>
    </form:form>
</body>
</html>
