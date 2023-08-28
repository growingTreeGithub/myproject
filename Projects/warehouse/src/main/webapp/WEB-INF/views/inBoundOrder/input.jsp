<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<fmt:> prefix is used to format and display text, numbers, dates and times based on the user's locale.--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="permissionFn" uri="http://construction.org/wms"%>

<html>
<head>
    <title>Inbound Order InputPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/input.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.structure.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.theme.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-validation-1.19.5/jquery.validate.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.js"></script>
    <script type="text/javascript" src="/asset/js/domain/inBoundOrderList.js"></script>
</head>
<body>
    <form:form action="/inBoundOrder/saveOrUpdate" modelAttribute="inBoundOrder" method="POST" id="myForm">
       <form:hidden path="id" id="orderId"/>
        <div id="inputForm">
            <div id="navigation">
                <span id="nav_title">Inbound Order Information</span>
            </div>
            <div class="inputArea">
                <table class="table">
                    <tr>
                        <td class="text_left">Inbound Order Serial Number</td>
                        <td class="text_right">
                            <form:input path="serialNumber" cssClass="input_ui"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="text_left">InBoundDate</td>
                        <td class="text_right">
                            <%--<fmt:formatDate> tag is used to format the value of the inBoundDate variable.
                            The pattern attribute is set to "yyyy-MM-dd", which represents the desired date format.
                            The resulting formatted date is then displayed on the page.
                            The var attribute is used to assign the formatted date to the variable inBoundDate.The variable
                            inBoundDate can be used elsewhere in jsp page to access the formatted date value or display
                            the formatted date in different parts of the jsp page.--%>
                            <fmt:formatDate value="${inBoundOrder.inBoundDate}" var="inBoundDate" pattern="yyyy-MM-dd" />
                                    <%--<form:input type="date"> is used to create an input field for the "inBoundDate" property
                                    of inBoundOrder.It allows the user to select a date form a calendar or enter it manually.
                                    The selected or entered date will be sent back to the server as part of the form data.
                                    The path attribute specifies the name or path of the model attribute that the input field is
                                    bound to.--%>
                            <form:input type="date" path="inBoundDate"  value="${inBoundDate}" cssClass="input_ui"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="text_left">
                            Inbound Order Item List
                        </td>
                        <td class="text_right">
                            <div class="tableResult">
                                <input type="button" value="Add a row for an item" class="button_ui addRow"/>
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>BrandName</th>
                                        <th>costPrice</th>
                                        <th>amount</th>
                                        <th>totalPrice</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody id="edit_table_body">
                                    <c:choose>
                                        <%--When the inBoundOrder id is empty, it means the user add a inBoundOrder.
                                         Show the user with a page with empty input field.--%>
                                        <c:when test="${empty inBoundOrder.id}">
                                            <tr>
                                                <td>
                                                    <form:select path="itemsProduct" cssClass="select_ui itemProductList" tag="productId" multiple="false">
                                                        <form:options items="${allProductList}" itemLabel="name" itemValue="id"/>
                                                    </form:select>
                                                </td>
                                                <td>
                                                    <form:input path="itemsBrandName" cssClass="input_ui" tag="brandName" readonly="true"/>
                                                </td>
                                                <td>
                                                    <form:input path="itemsCostPrice" cssClass="input_ui" tag="costPrice"/>
                                                </td>
                                                <td>
                                                    <form:input path="itemsAmount" cssClass="input_ui" tag="amount"/>
                                                </td>
                                                <td>
                                                    <form:input path="itemsTotalPrice" cssClass="input_ui" tag="totalPrice" readonly="true"/>
                                                </td>
                                                <td>
                                                    <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.InBoundOrderItemController:delete')}">
                                                        <a href="javascript:;"  class="button_delete">Delete</a>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <%--If the id of inBoundOrder is not null, it means the user edit an inBoundOrder.--%>
                                            <c:choose>
                                                <%--(Please refer to line 125 in inBoundOrderController when itemsId == null in saveOrUpdate method.)
                                                when all the items has been deleted and there are no items in a existing inbound order list, the user click edit
                                                and it will return a page with inbound order serial number, inBoundDate and a empty inbound order item list.--%>
                                                <c:when test="${empty selectedItemList}">
                                                    <tr>
                                                        <td>
                                                            <form:select path="itemsProduct" cssClass="select_ui itemProductList" tag="productId" multiple="false">
                                                                <form:options items="${allProductList}" itemLabel="name" itemValue="id"/>
                                                            </form:select>
                                                        </td>
                                                        <td>
                                                            <%--set the brandName read only and do not let the user enter brandName which does not exist--%>
                                                            <form:input path="itemsBrandName" cssClass="input_ui" tag="brandName" readonly="true"/>
                                                        </td>
                                                        <td>
                                                            <form:input path="itemsCostPrice" cssClass="input_ui" tag="costPrice"/>
                                                        </td>
                                                        <td>
                                                            <form:input path="itemsAmount" cssClass="input_ui" tag="amount"/>
                                                        </td>
                                                        <td>
                                                            <%--set the totalPrice read only to prevent the user enter a wrong calculated total price.--%>
                                                            <form:input path="itemsTotalPrice" cssClass="input_ui" tag="totalPrice" readonly="true"/>
                                                        </td>
                                                        <td>
                                                            <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.InBoundOrderItemController:delete')}">
                                                                <a href="javascript:;"  class="button_delete">Delete</a>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:when>
                                                <c:otherwise>
                                                    <%--For the case when there is any inBoundOrderItem in an inBoundOrder, it will show page with inBoundOrderItem.
                                                    For Case 2 and Case 3 in saveOrUpdate method in inBoundOrderController, it will show below page.--%>
                                                    <c:forEach var="inBoundOrderItem" items="${selectedItemList}">
                                                        <tr>
                                                            <c:set var="itemsId"><c:out value="${inBoundOrderItem.id}"/></c:set>
                                                            <form:hidden path="itemsId" tag="itemsId" value="${itemsId}"/>
                                                            <td>
                                                                <%--set the inBoundOrderItem's productId into variable productId--%>
                                                                <c:set var="productId"><c:out value="${inBoundOrderItem.product.id}"/></c:set>
                                                                <form:select path="itemsProduct" cssClass="select_ui itemProductList" tag="productId" multiple="false">
                                                                    <c:forEach var="product" items="${allProductList}">
                                                                        <c:choose>
                                                                            <%--compare the product id on the product list and inBoundOrderItem's productId.
                                                                            If the product exist, show the corresponding product name.--%>
                                                                            <c:when test="${product.id==productId}">
                                                                                <form:option selected="true" value="${product.id}">
                                                                                    ${product.name}
                                                                                </form:option>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <%--otherwise, show the default product list.--%>
                                                                                <form:option value="${product.id}">
                                                                                    ${product.name}
                                                                                </form:option>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:forEach>
                                                                </form:select>
                                                            </td>
                                                            <td>
                                                                <c:set var="brandName"><c:out value="${inBoundOrderItem.product.brandName.name}"/></c:set>
                                                                <form:input path="itemsBrandName" cssClass="input_ui" tag="brandName" value="${brandName}" readonly="true"/>
                                                            </td>
                                                            <td>
                                                                <c:set var="costPrice"><c:out value="${inBoundOrderItem.costPrice}"/></c:set>
                                                                <form:input path="itemsCostPrice" cssClass="input_ui" tag="costPrice" value="${costPrice}"/>
                                                            </td>
                                                            <td>
                                                                <c:set var="amount"><c:out value="${inBoundOrderItem.amount}"/></c:set>
                                                                <form:input path="itemsAmount" cssClass="input_ui" tag="amount" value="${amount}"/>
                                                            </td>
                                                            <td>
                                                                <c:set var="totalPrice"><c:out value="${inBoundOrderItem.totalPrice}"/></c:set>
                                                                <form:input path="itemsTotalPrice" cssClass="input_ui" tag="totalPrice" value="${totalPrice}" readonly="true"/>
                                                            </td>
                                                            <td>
                                                                <c:if test="${permissionFn:checkPermission('org.construction.wms.web.controller.InBoundOrderItemController:delete')}">
                                                                    <a href="javascript:;" class="button_delete">Delete</a>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                    </tbody>
                                </table>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="text_left"></td>
                        <td class="text_right">
                            <input type="submit" value="Confirm" class="button_ui"/>
                            <input type="button" value="Reset" class="button_ui" id="reset_field"/>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </form:form>
</body>
</html>
