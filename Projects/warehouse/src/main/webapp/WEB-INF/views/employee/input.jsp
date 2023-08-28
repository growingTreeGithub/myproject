<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Employee InputPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/input.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-validation-1.19.5/jquery.validate.js"></script>
    <script type="text/javascript" src="/asset/js/common.js"></script>
    <script type="text/javascript" src="/asset/js/domain/employee.js"></script>
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
                    <%--if the id of employee is empty, it means it is a new employee information. Show the below
                    two input fields for entering new password for employee. If the id of employee is not empty,
                    the password is set and cannot be changed. Therefore, do not show the below two input fields
                    for entering password.--%>
                    <c:if test="${empty employee.id}">
                        <tr>
                            <td class="text_left">New Password</td>
                            <td class="text_right">
                                <%--form:password tag renders the password input field as a masked or hidden field,
                                ensuring that the entered password is not visible to others.--%>
                                <form:password path="password" cssClass="input_ui" id="password"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="text_left">Confirm New Password</td>
                            <td class="text_right">
                                <%--The confirm password value will not submit to the form backing object.
                                It is only used in JQuery validation to double confirm the password entered is correct.--%>
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
                            <%--<form:select> is used to render a dropdown/select field bound to a model attribute.
                            The path attribute specifies the path to the corresponding form backing object's property,
                            which will be bound to the selected value from the select list. The dept.id match with the
                            property name in the form backing object.--%>
                            <form:select path="dept.id" cssClass="select_ui">
                                <%--<form:options> tag simplifies the process of generating options for the select list
                                by using a collection or iterable object and specifying the label and value properties.
                                The items attribute specifies the collection or iterable object from which the options
                                will be generated.
                                The itemLabel attribute specifies the name of the property in the collection that will be
                                used as the label for the options.
                                The itemValue attribute specifies the name of the property in the collection that will be
                                used as the value for the options.--%>
                                <form:options items="${deptList}" itemLabel="name" itemValue="id"/>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td class="text_left">Admin</td>
                        <td class="text_right">
                            <%--form:checkbox is used to render checkboxes.It can be used to bind boolean values
                             to the property name of the form backing object.--%>
                            <form:checkbox path="admin" cssClass="checkbox_ui"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="text_left">
                            Role that can be assigned
                        </td>
                        <td></td>
                        <td class="text_right">
                            Assign Role to this employee
                        </td>
                    </tr>
                    <tr>
                        <td class="text_left">
                            <%--the multiple="true" attribute in the <form:select> tag is used to enable multiple selection
                            in a select list, allowing users to select multiple options at the same time.--%>
                            <form:select multiple="true" path="" cssClass="multiselect_ui allRoleList">
                                <form:options items="${allRoleList}" itemLabel="name" itemValue="id"/>
                            </form:select>
                        </td>
                        <td align="center">
                            <input type="button" id="select" value="select One role to right" class="direction_button"/><br>
                            <input type="button" id="selectAll" value="select All roles to right" class="direction_button"/><br>
                            <input type="button" id="deselect" value="select One role to left" class="direction_button"/><br>
                            <input type="button" id="deselectAll" value="select All roles to left" class="direction_button"/><br>
                        </td>
                        <td class="text_right">
                            <form:select multiple="true" path="roleArray" cssClass="multiselect_ui selectedRoleList">
                                <form:options items="${selectedRoleList}" itemLabel="name" itemValue="id"/>
                            </form:select>
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
