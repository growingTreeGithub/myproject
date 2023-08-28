<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="permissionFn" uri="http://construction.org/wms"%>
<html>
<head>
    <title>Inventory ListPage</title>
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
    <form:form id="searchForm" action="/inventory/list" method="POST" modelAttribute="qo">
        <div class="listForm">
            <div class="searchBox">
                <div class="searchTitle">
                    SearchBox
                </div>
                <div class="searchCriteria">
                    <div id="searchRegion">
                        Search By Inventory Name: <form:input path="keyword"/>
                    </div>
                    <div>
                        <input type="submit" value="Search" class="button_redirect"/>
                    </div>
                </div>
            </div>
            <div id="domainList">
                <span id="domainTitle">Inventory Report</span>
            </div>
            <div class="tableResult">
                <table class="table">
                    <thead>
                        <tr>
                            <th>inventory name</th>
                            <th>brandName</th>
                            <th>average price</th>
                            <th>inventory amount</th>
                            <th>total price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="inventory" items="${pageResult.result}">
                            <tr>
                                <td><c:out value="${inventory.product.name}"/></td>
                                <td><c:out value="${inventory.product.brandName.name}"/></td>
                                <td><c:out value="${inventory.averagePrice}"/></td>
                                <td><c:out value="${inventory.inventoryAmount}"/></td>
                                <td><c:out value="${inventory.totalPrice}"/></td>
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
