<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="permissionFn" uri="http://construction.org/wms"%>

<html>
<head>
    <title>Outbound Order ReadPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/input.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript">
        $(function(){
            /*$(":input") selects all input elements on the page, including <input type="text">,
            *<input type="checkbox">,etc.
            * .prop("readonly", true) is used to set the "readonly" property of the selected elements
            * to true. By executing this line of code, all input elements on the page will become read-only,
            * preventing users from modifying their values.
            * */
            $(":input").prop("readonly", true);
        });
    </script>
</head>
<body>
<form:form action="/outBoundOrder/saveOrUpdate" modelAttribute="outBoundOrder" method="POST" id="myForm">
    <form:hidden path="id"/>
    <div id="inputForm">
        <div id="navigation">
            <span id="nav_title">Outbound Order Information</span>
        </div>
        <div class="inputArea">
            <table class="table">
                <tr>
                    <td class="text_left">Outbound Order Serial Number</td>
                    <td class="text_right">
                        <form:input path="serialNumber" cssClass="input_ui"/>
                    </td>
                </tr>
                <tr>
                    <td class="text_left">Construction Site</td>
                    <td class="text_right">
                        <form:input path="constructionSite.name" cssClass="input_ui"/>
                    </td>
                </tr>
                <tr>
                    <td class="text_left">OutBoundDate</td>
                    <td class="text_right">
                        <fmt:formatDate value="${outBoundOrder.outBoundDate}" var="outBoundDate" pattern="yyyy-MM-dd" />
                        <form:input type="date" path="outBoundDate"  value="${outBoundDate}" cssClass="input_ui"/>
                    </td>
                </tr>
                <tr>
                    <td class="text_left">
                        Outbound Order Item List
                    </td>
                    <td class="text_right">
                        <div class="tableResult">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Product</th>
                                    <th>BrandName</th>
                                    <th>costPrice</th>
                                    <th>amount</th>
                                    <th>totalPrice</th>
                                </tr>
                                </thead>
                                <tbody id="edit_table_body">
                                        <c:forEach var="outBoundOrderItem" items="${selectedItemList}">
                                            <c:set var="itemsId"><c:out value="${outBoundOrderItem.id}"/></c:set>
                                            <form:hidden path="itemsId" tag="itemsId" value="${itemsId}"/>
                                            <tr>
                                                <td>
                                                        <%--set the outBoundOrderItem's productId into variable productId--%>
                                                    <c:set var="productId"><c:out value="${outBoundOrderItem.product.id}"/></c:set>
                                                    <form:select path="itemsProduct" cssClass="select_ui itemProductList" tag="productId" multiple="false" disabled="true">
                                                        <c:forEach var="product" items="${allProductList}">
                                                            <c:choose>
                                                                <%--compare the product id on the product list and outBoundOrderItem's productId.
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
                                                    <c:set var="brandName"><c:out value="${outBoundOrderItem.product.brandName.name}"/></c:set>
                                                    <form:input path="itemsBrandName" cssClass="input_ui" tag="brandName" value="${brandName}" readonly="true"/>
                                                </td>
                                                <td>
                                                    <c:set var="costPrice"><c:out value="${outBoundOrderItem.costPrice}"/></c:set>
                                                    <form:input path="itemsCostPrice" cssClass="input_ui" tag="costPrice" value="${costPrice}"/>
                                                </td>
                                                <td>
                                                    <c:set var="amount"><c:out value="${outBoundOrderItem.amount}"/></c:set>
                                                    <form:input path="itemsAmount" cssClass="input_ui" tag="amount" value="${amount}"/>
                                                </td>
                                                <td>
                                                    <c:set var="totalPrice"><c:out value="${outBoundOrderItem.totalPrice}"/></c:set>
                                                    <form:input path="itemsTotalPrice" cssClass="input_ui" tag="totalPrice" value="${totalPrice}" readonly="true"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="text_left"></td>
                    <td class="text_right">
                        <%--window.history.back() takes the user back to the previous page they visited.--%>
                        <input type="button" value="Return to Outbound Order List" class="return_button_ui" onclick="window.history.back()"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form:form>
</body>
</html>
