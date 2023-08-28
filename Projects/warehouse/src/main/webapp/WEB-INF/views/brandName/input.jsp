<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--tag element with <form:> prefix provided by springframework is part of Spring Form tag library.
    It is used to facilitate form handling in web applications.
    The form tag automatically binds the entered value to the corresponding property of
    the form backing object during form submission.--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--core tag library with <c:> prefix provided by jsp standard tag library. It provides
a set of tags that are used for common tasks and control flow in JSP pages.--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>BrandName InputPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/input.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-validation-1.19.5/jquery.validate.js"></script>
    <script type="text/javascript" src="/asset/js/common.js"></script>
    <script type="text/javascript" src="/asset/js/domain/brandName.js"></script>
</head>
<body>
    <%-- The <form> tag helps in binding form inputs to java objects. The modelAttribute attribute
     specify the name of the java object that should receive the form data. The tag automatically
     maps the form inputs to corresponding properties of the java object.--%>
    <form:form action="/brandName/saveOrUpdate" modelAttribute="brandName" method="POST" id="myForm">
       <%-- form hidden tag is used to store the value of an ID or any other sensitive data
        that needs to be sent along with the form submission but should not be visible or editable
        by the user. The path attribute specifies the property path within the form backing object
        from which the value will be retrieved.In this case, it retrieves the value of the "id" property.--%>
        <form:hidden path="id"/>
        <div id="inputForm">
            <div id="navigation">
                <span id="nav_title">BrandName Information</span>
            </div>
            <div class="inputArea">
                <table class="table">
                    <tr>
                        <td class="text_left">BrandName</td>
                        <td class="text_right">
                            <%--form input tag is used to render an HTML input field bound to a specific model
                            attribute or form backing object. It automatically populates the input field with
                            the corresponding value, and the submitted data is bound back to the model.--%>
                            <form:input path="name" cssClass="input_ui"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="text_left"></td>
                        <td class="text_right">
                            <%--When the user clicks on the submit button, the form data will be sent to the server
                            using the specified HTTP method such as POST request method. The server can then process
                            the submitted data and perform the necessary actions based on the submitted values.--%>
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
