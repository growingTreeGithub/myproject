<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>FrontPage</title>
    <link href="/asset/style/reset.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/style/login.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.structure.css" rel="stylesheet" type="text/css"/>
    <link href="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.theme.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/asset/js/jquery/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="/asset/js/plugins/jquery-ui-1.13.2.custom/jquery-ui.js"></script>
    <script type="text/javascript" src="/asset/js/login/login.js"></script>
</head>
<body>
<div id="login">
        <form:form action="">
            <div id="header">
                <h1 id="title">Warehouse Management System</h1>
            </div>
            <div class="login_row">
                <div class="username">
                        <input type="text" class="usernamebox" name="name"/>
                        <span class="username_placeholder">please enter your username</span>
                </div>
            </div>
            <div class="login_row">
                <div class="password">
                    <%--input type="password" is specifically designed for capturing sensitive information,
                    such as passwords, where the characters entered by the user should be masked or hidden
                    for security reasons.--%>
                        <input type="password" class="passwordbox" name="password"/>
                        <span class="password_placeholder">please enter your password</span>
                </div>
            </div>
            <div class="button_row">
                <%--the login data is submitted by jquery and ajax to backend--%>
                <div id="button">
                        <input type="button" class="login_button" value="Login"/>
                </div>
            </div>
        </form:form>
</div>
</body>
</html>
