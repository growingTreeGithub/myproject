<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="permissionFn" uri="http://construction.org/wms"%>

<html>
<head>
    <title>Employee ReadPage</title>
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
<form:form action="/employee/saveOrUpdate" modelAttribute="employee" method="POST" id="myForm">
    <form:hidden path="id"/>
    <div id="inputForm">
        <div id="navigation">
            <span id="nav_title">Employee Information</span>
        </div>
        <div class="inputArea">
            <table class="table">
                <tr>
                    <td class="text_left">Employee Name</td>
                    <td class="text_right">
                        <form:input path="name" cssClass="input_ui"/>
                    </td>
                </tr>
                <c:if test="${empty employee.id}">
                    <tr>
                        <td class="text_left">New Password</td>
                        <td class="text_right">
                            <form:password path="password" cssClass="input_ui" id="password"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="text_left">Confirm New Password</td>
                        <td class="text_right">
                            <input type="password" name="confirm_password" class="input_ui"/>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td class="text_left">Age</td>
                    <td class="text_right">
                        <form:input path="age" cssClass="input_ui"/>
                    </td>
                </tr>
                <tr>
                    <td class="text_left">Department</td>
                    <td class="text_right">
                        <form:select path="dept.id" cssClass="select_ui" disabled="true">
                            <form:options items="${deptList}" itemLabel="name" itemValue="id"/>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td class="text_left">Admin</td>
                    <td class="text_right">
                        <form:checkbox path="admin" cssClass="checkbox_ui" disabled="true"/>
                    </td>
                </tr>
                <tr>
                    <td class="text_left">
                        Role of this employee
                    </td>
                    <td class="text_right">
                        <form:select multiple="true" path="roleArray" cssClass="multiselect_ui selectedRoleList" disabled="true">
                            <form:options items="${selectedRoleList}" itemLabel="name" itemValue="id"/>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td class="text_left"></td>
                    <td class="text_right">
                        <%--window.history.back() takes the user back to the previous page they visited.--%>
                        <input type="button" value="Return to Employee List" class="return_button_ui" onclick="window.history.back()"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form:form>
</body>
</html>
