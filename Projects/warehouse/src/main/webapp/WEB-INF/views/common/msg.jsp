<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    //use jquery ui plugin to create the pop up to alert the user
    <c:if test="${not empty saveMsg}">
    /*saveOrUpdate method, if save or update operation is successful, it will return domainName,
    * saveMsg or updateMsg so that it will have a pop up showing save successfully or update successfully
    * by jquery ui.
    * */
        var msg = $('<div><p><c:out value="${saveMsg}"/></p></div>');
        msg.dialog({
            resizable:false,
            height: "auto",
            width: 400,
            title:"Save Information",
            modal: true,
            buttons: {
                Ok: function() {
                    window.location.href="/<c:out value="${domainName}"/>/list";
                    $( this ).dialog( "close" );
                }
            }
        });
    </c:if>
    <c:if test="${not empty updateMsg}">
        var msg = $('<div><p><c:out value="${updateMsg}"/></p></div>');
        msg.dialog({
            resizable:false,
            height: "auto",
            width: 400,
            title:"Update Information",
            modal: true,
            buttons: {
                Ok: function() {
                    window.location.href="/<c:out value="${domainName}"/>/list";
                    $( this ).dialog( "close" );
                }
            }
        });
    </c:if>

</script>