<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Role InputPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/input.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-validation-1.19.5/jquery.validate.js"></script>
    <script type="text/javascript" src="/asset/js/common.js"></script>
    <script type="text/javascript" src="/asset/js/domain/role.js"></script>
</head>
<body>
    <form:form action="/role/saveOrUpdate" modelAttribute="role" method="POST" id="myForm">
       <form:hidden path="id"/>
        <div id="inputForm">
            <div id="navigation">
                <span id="nav_title">Role Information</span>
            </div>
            <div class="inputArea">
                <table class="table">
                    <tr>
                        <td class="text_left">Role Name</td>
                        <td class="text_right">
                            <form:input path="name" cssClass="input_ui"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="text_left">
                            Permission that can be assigned
                        </td>
                        <td></td>
                        <td class="text_right">
                            Assign Permission to this role
                        </td>
                    </tr>
                    <tr>
                        <td class="text_left">
                                <%--the multiple="true" attribute in the <form:select> tag is used to enable multiple selection
                                in a select list, allowing users to select multiple options at the same time.--%>
                            <form:select multiple="true" path="" cssClass="multiselect_ui allPermissionList">
                                <form:options items="${allPermissionList}" itemLabel="name" itemValue="id"/>
                            </form:select>
                        </td>
                        <td align="center">
                            <input type="button" id="select" value="select One Permission to right" class="direction_button"/><br>
                            <input type="button" id="selectAll" value="select All Permission to right" class="direction_button"/><br>
                            <input type="button" id="deselect" value="select One Permission to left" class="direction_button"/><br>
                            <input type="button" id="deselectAll" value="select All Permission to left" class="direction_button"/><br>
                        </td>
                        <td class="text_right">
                            <form:select multiple="true" path="permissionArray" cssClass="multiselect_ui selectedPermissionList">
                                <form:options items="${selectedPermissionList}" itemLabel="name" itemValue="id"/>
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
