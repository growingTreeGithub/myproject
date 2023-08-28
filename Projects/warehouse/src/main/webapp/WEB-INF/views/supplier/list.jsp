<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="permissionFn" uri="http://construction.org/wms"%>
<html>
<head>
    <title>Supplier ListPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/list.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.structure.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.theme.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-validation-1.19.5/jquery.validate.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.js"></script>
    <script type="text/javascript" src="/asset/js/common.js"></script>
    <script type="text/javascript" src="/asset/js/domain/supplier.js"></script>
</head>
<body>
    <%@include file="/WEB-INF/views/common/msg.jsp"%>
    <form:form id="searchForm" action="/supplier/list" method="POST" modelAttribute="qo">
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
                <span id="domainTitle">List of Supplier</span>
                <c:url var="add" value="/supplier/input"/>
                <input type="button" value="Add a Supplier" class="button_redirect" data-url="<c:out value="${add}"/>"/>
            </div>
            <div class="tableResult">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID Number</th>
                            <th>name</th>
                            <th>address</th>
                            <th>phone</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="supplier" items="${pageResult.result}">
                            <tr>
                                <td><c:out value="${supplier.id}"/></td>
                                <td><c:out value="${supplier.name}"/></td>
                                <td><c:out value="${supplier.address}"/></td>
                                <td><c:out value="${supplier.phone}"/></td>
                                <td>
                                    <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.SupplierController:input')}">
                                        <c:url var="edit" value="/supplier/input">
                                            <c:param name="id" value="${supplier.id}"/>
                                        </c:url>
                                        <a href="<c:out value="${edit}"/>">Edit</a>
                                    </c:if>
                                    <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.SupplierController:delete')}">
                                        <c:url var="delete" value="/supplier/delete">
                                            <c:param name="id" value="${supplier.id}"/>
                                        </c:url>
                                        <a href="javascript:;"  class="button_supplier_delete" data-url="<c:out value="${delete}"/>">Delete</a>
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
