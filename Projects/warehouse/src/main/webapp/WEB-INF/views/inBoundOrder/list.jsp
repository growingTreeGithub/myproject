<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="permissionFn" uri="http://construction.org/wms"%>
<html>
<head>
    <title>Inbound Order ListPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/list.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.structure.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.theme.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.js"></script>
    <script type="text/javascript" src="/asset/js/common.js"></script>
    <script type="text/javascript" src="/asset/js/domain/inBoundOrder.js"></script>
</head>
<body>
    <%@include file="/WEB-INF/views/common/msg.jsp"%>
    <form:form id="searchForm" action="/inBoundOrder/list" method="POST" modelAttribute="qo">
        <div class="listForm">
            <div class="searchBox">
                <div class="searchTitle">
                    SearchBox
                </div>
                <div class="searchCriteria">
                    <div id="searchRegion">
                        Search By startDate: <form:input type="date" path="startDate" cssClass="input_ui"/>
                        Search By endDate: <form:input type="date" path="endDate" cssClass="input_ui"/>
                    </div>
                    <div>
                        <input type="submit" value="Search" class="button_redirect"/>
                    </div>
                </div>
            </div>
            <div id="domainList">
                <span id="domainTitle">List of Inbound Order</span>
                <c:url var="add" value="/inBoundOrder/input"/>
                <input type="button" value="Add a Inbound Order" class="button_redirect" data-url="<c:out value="${add}"/>"/>
            </div>
            <div class="tableResult">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Serial Number</th>
                            <th>inBoundDate</th>
                            <th>inBound totalAmount</th>
                            <th>inBound totalPrice</th>
                            <th>inputUser</th>
                            <th>auditor</th>
                            <th>status</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="inBoundOrder" items="${pageResult.result}">
                            <tr>
                                <td><c:out value="${inBoundOrder.serialNumber}"/></td>
                                <td>
                                        <%--<fmt:formatDate> tag is used to format the value of the inBoundDate variable.
                                            The pattern attribute is set to "yyyy-MM-dd", which represents the desired date format.
                                            The resulting formatted date is then displayed on the page.
                                            The var attribute is used to assign the formatted date to the variable inBoundDate.The variable
                                            inBoundDate can be used elsewhere in jsp page to access the formatted date value or display
                                            the formatted date in different parts of the jsp page.--%>
                                    <fmt:formatDate value="${inBoundOrder.inBoundDate}" var="inBoundDate" pattern="yyyy-MM-dd" />
                                            <%--c:out tag is used to output the value of an expression.
                                                Attribute value specifies the expression or variable whose value needs to be displayed.
                                                It can be any valid JSP expression or a variable.--%>
                                    <c:out value="${inBoundDate}"/>
                                </td>
                                <td><c:out value="${inBoundOrder.totalAmount}"/></td>
                                <td><c:out value="${inBoundOrder.totalPrice}"/></td>
                                <td><c:out value="${inBoundOrder.inputUser.name}"/></td>
                                <td><c:out value="${inBoundOrder.auditor.name}"/></td>
                                <td>
                                        <%--The <c:set> tag is executed, and a variable named status is created. The value of the variable
                                        is obtained evaluating the inBoundOrder.status expression using EL. The value is then assigned to the
                                        status variable. The <c:out> tag is executed, and it outputs the value of the status variable on the
                                        page.--%>
                                    <c:set var="status"><c:out value="${inBoundOrder.status}"/></c:set>
                                    <c:choose>
                                        <%--if the value of the above status variable is equal to 0, 0 means the inBoundOrder is not audited.
                                        Show NOT AUDIT on the page.--%>
                                        <c:when test="${status == 0}">
                                            <span style="color:red">NOT AUDIT</span>
                                        </c:when>
                                        <%--otherwise, if the value of the above status variable is not equal to 0, the inBoundOrder has been audited.
                                        Show AUDIT on the page.--%>
                                        <c:otherwise>
                                            <span style="color:green">AUDIT</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <%--Show Audit, Edit, Delete, Read link to user with different permission--%>
                                    <c:set var="status"><c:out value="${inBoundOrder.status}"/></c:set>
                                    <c:choose>
                                        <%--if the value of the above status variable is equal to 0, 0 means the inBoundOrder is not audited.
                                        Show Audit, Edit, Delete link on the page to user with permission to access.--%>
                                        <c:when test="${status == 0}">
                                            <%--if the user has permission to audit a inBoundOrder, show AUDIT link on the page.
                                            Otherwise, do not show AUDIT link on the page.--%>
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.InBoundOrderController:audit')}">
                                                <c:url var="inBoundAudit" value="/inBoundOrder/audit">
                                                    <c:param name="id" value="${inBoundOrder.id}"/>
                                                </c:url>
                                                <a href="javascript:;"  class="button_inBoundAudit" data-url="<c:out value="${inBoundAudit}"/>">Audit</a>
                                            </c:if>
                                            <%--if the user has permission to edit a inBoundOrder, show EDIT link on the page.
                                            Otherwise, do not show EDIT link on the page.--%>
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.InBoundOrderController:input')}">
                                                <c:url var="edit" value="/inBoundOrder/input">
                                                    <c:param name="id" value="${inBoundOrder.id}"/>
                                                </c:url>
                                                <a href="<c:out value="${edit}"/>">Edit</a>
                                            </c:if>
                                            <%--if the user has permission to delete a inBoundOrder, show DELETE link on the page.
                                            Otherwise, do not show DELETE link on the page.--%>
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.InBoundOrderController:delete')}">
                                                <c:url var="delete" value="/inBoundOrder/delete">
                                                    <c:param name="id" value="${inBoundOrder.id}"/>
                                                </c:url>
                                                <a href="javascript:;"  class="button_delete" data-url="<c:out value="${delete}"/>">Delete</a>
                                            </c:if>
                                        </c:when>
                                        <%--Otherwise, if the value of the above status variable is not equal to 0, the inBoundOrder has been audited.
                                        Show Read link on the page to user with permission to access. Do not show Audit, Edit, Delete link when the inBoundOrder
                                        has been audited.--%>
                                        <c:otherwise>
                                            <%--if the user has permission to read a audited inBoundOrder, show Read link on the page.
                                            Otherwise, do not show Read link on the page.--%>
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.InBoundOrderController:read')}">
                                                <c:url var="read" value="/inBoundOrder/read">
                                                    <c:param name="id" value="${inBoundOrder.id}"/>
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
