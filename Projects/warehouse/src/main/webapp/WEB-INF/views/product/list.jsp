<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="permissionFn" uri="http://construction.org/wms"%>
<html>
<head>
    <title>Product ListPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/list.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.structure.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.theme.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-validation-1.19.5/jquery.validate.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.js"></script>
    <script type="text/javascript" src="/asset/js/common.js"></script>
    <script type="text/javascript" src="/asset/js/domain/product.js"></script>
</head>
<body>
    <%@include file="/WEB-INF/views/common/msg.jsp"%>
    <form:form id="searchForm" action="/product/list" method="POST" modelAttribute="qo">
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
                <span id="domainTitle">List of Product</span>
                <c:url var="add" value="/product/input"/>
                <input type="button" value="Add a Product" class="button_redirect" data-url="<c:out value="${add}"/>"/>
            </div>
            <div class="tableResult">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID Number</th>
                            <th>name</th>
                            <th>costPrice</th>
                            <th>brandName</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${pageResult.result}">
                            <tr>
                                <td><c:out value="${product.id}"/></td>
                                <td><c:out value="${product.name}"/></td>
                                <td><c:out value="${product.costPrice}"/></td>
                                <td><c:out value="${product.brandName.name}"/></td>
                                <td>
                                    <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.ProductController:input')}">
                                        <c:url var="edit" value="/product/input">
                                            <c:param name="id" value="${product.id}"/>
                                        </c:url>
                                        <a href="<c:out value="${edit}"/>">Edit</a>
                                    </c:if>
                                    <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.ProductController:delete')}">
                                        <c:url var="delete" value="/product/delete">
                                            <c:param name="id" value="${product.id}"/>
                                        </c:url>
                                        <a href="javascript:;"  class="button_product_delete" data-url="<c:out value="${delete}"/>">Delete</a>
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
