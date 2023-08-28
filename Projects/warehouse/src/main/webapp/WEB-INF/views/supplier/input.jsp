<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Supplier InputPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/input.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-validation-1.19.5/jquery.validate.js"></script>
    <script type="text/javascript" src="/asset/js/common.js"></script>
    <script type="text/javascript" src="/asset/js/domain/supplier.js"></script>
</head>
<body>
    <form:form action="/supplier/saveOrUpdate" modelAttribute="supplier" method="POST" id="myForm">
       <form:hidden path="id"/>
        <div id="inputForm">
            <div id="navigation">
                <span id="nav_title">Supplier Information</span>
            </div>
            <div class="inputArea">
                <table class="table">
                    <tr>
                        <td class="text_left">Supplier Name</td>
                        <td class="text_right">
                            <form:input path="name" cssClass="input_ui"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="text_left">Supplier Address</td>
                        <td class="text_right">
                            <form:textarea path="address" rows="5" cols="40" cssClass="textarea_ui"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="text_left">Supplier Phone Number</td>
                        <td class="text_right">
                            <form:input path="phone" cssClass="input_ui"/>
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
