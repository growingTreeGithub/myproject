<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="permissionFn" uri="http://construction.org/wms"%>
<html>
<head>
    <title>Outbound Order ListPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/list.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.structure.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.theme.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.js"></script>
    <script type="text/javascript" src="/asset/js/common.js"></script>
    <script type="text/javascript" src="/asset/js/domain/outBoundOrder.js"></script>
</head>
<body>
    <%@include file="/WEB-INF/views/common/msg.jsp"%>
    <form:form id="searchForm" action="/outBoundOrder/list" method="POST" modelAttribute="qo">
        <div class="listForm">
            <div class="searchBox">
                <div class="searchTitle">
                    SearchBox
                </div>
                <div class="searchCriteria">
                    <div id="searchRegion">
                        Search By startDate: <form:input type="date" path="startDate" cssClass="input_ui"/>
                        Search By endDate: <form:input type="date" path="endDate" cssClass="input_ui"/>
                        Search By Construction Site Name: <form:input path="keyword" cssClass="construction_ui"/>
                    </div>
                </div>
                <div class="searchButton">
                    <input type="submit" value="Search" class="button_redirect"/>
                </div>
            </div>
            <div id="domainList">
                <span id="domainTitle">List of Outbound Order</span>
                <c:url var="add" value="/outBoundOrder/input"/>
                <input type="button" value="Add an Outbound Order" class="button_redirect" data-url="<c:out value="${add}"/>"/>
            </div>
            <div class="tableResult">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Serial Number</th>
                            <th>outBoundDate</th>
                            <th>construction site</th>
                            <th>outBound totalAmount</th>
                            <th>outBound totalPrice</th>
                            <th>inputUser</th>
                            <th>auditor</th>
                            <th>status</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="outBoundOrder" items="${pageResult.result}">
                            <tr>
                                <td><c:out value="${outBoundOrder.serialNumber}"/></td>
                                <td>
                                        <%--<fmt:formatDate> tag is used to format the value of the outBoundDate variable.
                                                The pattern attribute is set to "yyyy-MM-dd", which represents the desired date format.
                                                The resulting formatted date is then displayed on the page.
                                                The var attribute is used to assign the formatted date to the variable outBoundDate.The variable
                                                outBoundDate can be used elsewhere in jsp page to access the formatted date value or display
                                                the formatted date in different parts of the jsp page.--%>
                                    <fmt:formatDate value="${outBoundOrder.outBoundDate}" var="outBoundDate" pattern="yyyy-MM-dd" />
                                            <%--c:out tag is used to output the value of an expression.
                                                Attribute value specifies the expression or variable whose value needs to be displayed.
                                                It can be any valid JSP expression or a variable.--%>
                                            <c:out value="${outBoundDate}"/>
                                </td>
                                <td><c:out value="${outBoundOrder.constructionSite.name}"/></td>
                                <td><c:out value="${outBoundOrder.totalAmount}"/></td>
                                <td><c:out value="${outBoundOrder.totalPrice}"/></td>
                                <td><c:out value="${outBoundOrder.inputUser.name}"/></td>
                                <td><c:out value="${outBoundOrder.auditor.name}"/></td>
                                <td>
                                        <%--The <c:set> tag is executed, and a variable named status is created. The value of the variable
                                            is obtained evaluating the outBoundOrder.status expression using EL. The value is then assigned to the
                                            status variable. The <c:out> tag is executed, and it outputs the value of the status variable on the
                                            page.--%>
                                    <c:set var="status"><c:out value="${outBoundOrder.status}"/></c:set>
                                    <c:choose>
                                        <%--if the value of the above status variable is equal to 0, 0 means the outBoundOrder is not audited.
                                        Show NOT AUDIT on the page.--%>
                                        <c:when test="${status == 0}">
                                            <span style="color:red">NOT AUDIT</span>
                                        </c:when>
                                        <%--otherwise, if the value of the above status variable is not equal to 0, the outBoundOrder has been audited.
                                        Show AUDIT on the page.--%>
                                        <c:otherwise>
                                            <span style="color:green">AUDIT</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <%--Show Audit, Edit, Delete, Read link to user with different permission--%>
                                    <c:set var="status"><c:out value="${outBoundOrder.status}"/></c:set>
                                    <c:choose>
                                        <%--if the value of the above status variable is equal to 0, 0 means the outBoundOrder is not audited.
                                        Show Audit, Edit, Delete link on the page to user with permission to access.--%>
                                        <c:when test="${status == 0}">
                                            <%--if the user has permission to audit a outBoundOrder, show AUDIT link on the page.
                                            Otherwise, do not show AUDIT link on the page.--%>
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.OutBoundOrderController:audit')}">
                                                <c:url var="outBoundAudit" value="/outBoundOrder/audit">
                                                    <c:param name="id" value="${outBoundOrder.id}"/>
                                                </c:url>
                                                <a href="javascript:;"  class="button_outBoundAudit" data-url="<c:out value="${outBoundAudit}"/>">Audit</a>
                                            </c:if>
                                            <%--if the user has permission to edit a outBoundOrder, show EDIT link on the page.
                                            Otherwise, do not show EDIT link on the page.--%>
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.OutBoundOrderController:input')}">
                                                <c:url var="edit" value="/outBoundOrder/input">
                                                    <c:param name="id" value="${outBoundOrder.id}"/>
                                                </c:url>
                                                <a href="<c:out value="${edit}"/>">Edit</a>
                                            </c:if>
                                            <%--if the user has permission to delete a outBoundOrder, show DELETE link on the page.
                                            Otherwise, do not show DELETE link on the page.--%>
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.OutBoundOrderController:delete')}">
                                                <c:url var="delete" value="/outBoundOrder/delete">
                                                    <c:param name="id" value="${outBoundOrder.id}"/>
                                                </c:url>
                                                <a href="javascript:;"  class="button_delete" data-url="<c:out value="${delete}"/>">Delete</a>
                                            </c:if>
                                        </c:when>
                                        <%--Otherwise, if the value of the above status variable is not equal to 0, the outBoundOrder has been audited.
                                        Show Read link on the page to user with permission to access. Do not show Audit, Edit, Delete link when the outBoundOrder
                                        has been audited.--%>
                                        <c:otherwise>
                                            <%--if the user has permission to read a audited outBoundOrder, show Read link on the page.
                                            Otherwise, do not show Read link on the page.--%>
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.OutBoundOrderController:read')}">
                                                <c:url var="read" value="/outBoundOrder/read">
                                                    <c:param name="id" value="${outBoundOrder.id}"/>
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
