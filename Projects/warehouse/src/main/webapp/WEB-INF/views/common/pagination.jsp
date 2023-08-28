<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="pagination_data">
    Total query records : <c:out value="${pageResult.totalCount}"/>
    , current at Page <c:out value="${pageResult.currentPage}"/>
</div>
<div id="pagination_control">
    <%--data-page can be used to store page number information in which the input field is located.
    In JQuery, the data() method is used to retrieve the value of the data-page attribute.--%>
    <input type="button" value="First Page" class="pagination_button" data-page="1"/>
    <input type="button" value="Previous Page" class="pagination_button" data-page="<c:out value="${pageResult.prevPage}"/>"/>
    <input type="button" value="Next Page" class="pagination_button" data-page="<c:out value="${pageResult.nextPage}"/>"/>
    <input type="button" value="Last Page" class="pagination_button totalPage" data-page="<c:out value="${pageResult.totalPage}"/>"/>
    <%--form:select is used to render a dropdown/select field bound to a model attribute.
    It can be used to display a list of options and bind the selected value back to the model such as QueryObject.--%>
    <form:select path="pageSize">
        <form:option value="10" label="10"/>
        <form:option value="20" label="20"/>
        <form:option value="50" label="50"/>
    </form:select>
    jump to Page: <form:input path="currentPage" cssClass="pageNumber_ui"/>
    <input type="button" value="Jump to Page" class="pagination_button"/>
</div>
