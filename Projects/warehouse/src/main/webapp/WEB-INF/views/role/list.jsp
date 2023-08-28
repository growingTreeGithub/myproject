<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="permissionFn" uri="http://construction.org/wms"%>
<html>
<head>
    <title>Role ListPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/list.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.structure.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.theme.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.js"></script>
    <script type="text/javascript" src="/asset/js/common.js"></script>
</head>
<body>
    <%@include file="/WEB-INF/views/common/msg.jsp"%>
    <form:form id="searchForm" action="/role/list" method="POST" modelAttribute="qo">
        <div class="listForm">
            <div id="domainList">
                <span id="domainTitle">List of Role</span>
                <c:url var="add" value="/role/input"/>
                <input type="button" value="Add a Role" class="button_redirect" data-url="<c:out value="${add}"/>"/>
            </div>
            <div class="tableResult">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID Number</th>
                            <th>name</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="role" items="${pageResult.result}">
                            <tr>
                                <td><c:out value="${role.id}"/></td>
                                <td><c:out value="${role.name}"/></td>
                                <td>
                                    <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.RoleController:input')}">
                                        <c:url var="edit" value="/role/input">
                                            <c:param name="id" value="${role.id}"/>
                                        </c:url>
                                        <a href="<c:out value="${edit}"/>">Edit</a>
                                    </c:if>
                                    <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.RoleController:delete')}">
                                        <c:url var="delete" value="/role/delete">
                                            <c:param name="id" value="${role.id}"/>
                                        </c:url>
                                        <a href="javascript:;"  class="button_delete" data-url="<c:out value="${delete}"/>">Delete</a>
                                    </c:if>
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
