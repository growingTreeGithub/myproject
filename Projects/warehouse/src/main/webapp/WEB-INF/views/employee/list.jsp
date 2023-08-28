<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="permissionFn" uri="http://construction.org/wms"%>
<html>
<head>
    <title>Employee ListPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/list.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.structure.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.theme.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-validation-1.19.5/jquery.validate.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.js"></script>
    <script type="text/javascript" src="/asset/js/common.js"></script>
    <script type="text/javascript" src="/asset/js/domain/employee.js"></script>
</head>
<body>
    <%@include file="/WEB-INF/views/common/msg.jsp"%>
    <form:form id="searchForm" action="/employee/list" method="POST" modelAttribute="qo">
        <div class="listForm">
            <div class="searchBox">
                <div class="searchTitle">
                    SearchBox
                </div>
                <div class="searchCriteria">
                    <div id="searchRegion">
                        Search By Name: <form:input path="keyword"/>
                    </div>
                    <div>
                        <input type="submit" value="Search" class="button_redirect"/>
                    </div>
                </div>
            </div>
            <div id="domainList">
                <span id="domainTitle">List of Employee</span>
                <c:url var="add" value="/employee/input"/>
                <input type="button" value="Add an Employee" class="button_redirect" data-url="<c:out value="${add}"/>"/>
            </div>
            <div class="tableResult">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID Number</th>
                            <th>name</th>
                            <th>age</th>
                            <th>department</th>
                            <th>status</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="employee" items="${pageResult.result}">
                            <tr>
                                <td><c:out value="${employee.id}"/></td>
                                <td><c:out value="${employee.name}"/></td>
                                <td><c:out value="${employee.age}"/></td>
                                <td><c:out value="${employee.dept.name}"/></td>
                                <td>
                                    <%--The <c:set> tag is executed, and a variable named status is created. The value of the variable
                                    is obtained evaluating the employee.status expression using EL. The value is then assigned to the
                                    status variable. The <c:out> tag is executed, and it outputs the value of the status variable on the
                                    page.--%>
                                    <c:set var="status"><c:out value="${employee.status}"/></c:set>
                                    <c:choose>
                                        <%--if the value of the above status variable is equal to 0, 0 means the employee is still employed.
                                        Show EMPLOYED on the page.--%>
                                        <c:when test="${status == 0}">
                                            <span style="color:green">EMPLOYED</span>
                                        </c:when>
                                        <%--otherwise, if the value of the above status variable is not equal to 0, the employee has resigned.
                                        Show RESIGNED on the page.--%>
                                        <c:otherwise>
                                            <span style="color:red">RESIGNED</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:set var="status"><c:out value="${employee.status}"/></c:set>
                                    <c:choose>
                                        <%--if the value of the above status variable is equal to 0, 0 means the employee is still employed.
                                        Show Edit and Resign link for the user to edit information or to confirm resignation for the resigned
                                        employee.--%>
                                        <c:when test="${status == 0}">
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.EmployeeController:input')}">
                                                <c:url var="edit" value="/employee/input">
                                                    <c:param name="id" value="${employee.id}"/>
                                                </c:url>
                                                <a href="<c:out value="${edit}"/>">Edit</a>
                                            </c:if>
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.EmployeeController:delete')}">
                                                <c:url var="delete" value="/employee/delete">
                                                    <c:param name="id" value="${employee.id}"/>
                                                </c:url>
                                                <a href="javascript:;"  class="button_employee_delete" data-url="<c:out value="${delete}"/>">Resign</a>
                                            </c:if>
                                        </c:when>
                                        <%--otherwise, if the value of the above status variable is not equal to 0, the employee has resigned.
                                        Show Read link for the user to read the resigned employee's information. The employee information
                                        cannot be edited or click resign button again for the resigned employee.--%>
                                        <c:otherwise>
                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.EmployeeController:read')}">
                                                <c:url var="read" value="/employee/read">
                                                    <c:param name="id" value="${employee.id}"/>
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
