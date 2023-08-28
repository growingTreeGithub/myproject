<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="permissionFn" uri="http://construction.org/wms"%>
<html>
<head>
    <title>BrandName ListPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/list.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.structure.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.theme.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-validation-1.19.5/jquery.validate.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.js"></script>
    <script type="text/javascript" src="/asset/js/common.js"></script>
    <script type="text/javascript" src="/asset/js/domain/brandName.js"></script>
</head>
<body>
    <%@include file="/WEB-INF/views/common/msg.jsp"%>
    <form:form id="searchForm" action="/brandName/list" method="POST" modelAttribute="qo">
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
                <span id="domainTitle">List of BrandName</span>
                <%--c:url is used for url rewriting and encoding.var specifies the name of a variable to store the generated URL.
                value specifies the url that needs to be generated.It can be a relative or absolute URL.--%>
                <c:url var="add" value="/brandName/input"/>
                <%--Attribute data-url can attach additional information to input field, which can be used by jQuery
                to retrieve the value of the data-url attribute and perform AJAX requests to the specified URL.--%>
                <input type="button" value="Add a BrandName" class="button_redirect" data-url="<c:out value="${add}"/>"/>
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
                        <%--c:forEach tag is used to iterate over a collection and executing content for each element.
                        var specifies the name of loop variable that represents the current element of the iteration.
                        item specifies the collection or array to iterate over.--%>
                        <c:forEach var="brandName" items="${pageResult.result}">
                            <tr>
                                <%--c:out tag is used to output the value of an expression.
                                Attribute value specifies the expression or variable whose value needs to be displayed.
                                It can be any valid JSP expression or a variable.--%>
                                <td><c:out value="${brandName.id}"/></td>
                                <td><c:out value="${brandName.name}"/></td>
                                <td>
                                    <%--c:if tag is used for conditionally include or exclude content within the tag based on
                                    the evaluation of a Boolean expression.
                                    The below tag is used with PermissionUtils's checkPermission method to determine whether a user
                                    has permission to have operation on the content.If a user have permission, c:if tag will include the content.
                                    If a user does not have permission, c:if tag will exclude the content.--%>
                                    <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.BrandNameController:input')}">
                                        <c:url var="edit" value="/brandName/input">
                                            <c:param name="id" value="${brandName.id}"/>
                                        </c:url>
                                        <a href="<c:out value="${edit}"/>">Edit</a>
                                    </c:if>
                                    <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.BrandNameController:delete')}">
                                        <c:url var="delete" value="/brandName/delete">
                                            <c:param name="id" value="${brandName.id}"/>
                                        </c:url>
                                        <a href="javascript:;"  class="button_brandName_delete" data-url="<c:out value="${delete}"/>">Delete</a>
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
