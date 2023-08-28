$(function(){
    $(".usernamebox").change(function(){
        //when the username box's value is not equal to ""
        if($(".usernamebox").val() !==""){
            //the span that provide hints for entering the username disappear
            $(".username_placeholder").css("visibility","hidden");
        }else{
            //otherwise, when the username box's value is equal to "", the span that provide hints appear again.
            $(".username_placeholder").css("visibility","visible");
        }
    });

    $(".passwordbox").change(function(){
        //when the password box's value is not equal to ""
        if($(".passwordbox").val() !==""){
            //the span that provide hints for entering the password disappear
            $(".password_placeholder").css("visibility","hidden");
        }else{
            //otherwise, when the password box's value is equal to "", the span that provide hints appear again.
            $(".password_placeholder").css("visibility","visible");
        }
    });

    $.fn.submitForm = function(){
        $.ajax({
            type:"post",
            url:"/employee/login",
            //serialize() is a jquery method used to collect data from an HTML form and convert it into a serialized string
            data:$("form").serialize(),
            //data refers to json returned by login method in employeeController
            success:function(data){
                if(data.success){
                    //redirect to mainPage
                    window.location.href="/main";
                }else{
                    /*alert(data.msg);*/
                    var $loginMsg =$('<div><p>name or password is invalid</p></div>');
                    $loginMsg.dialog({
                        autoOpen:true,
                        modal: true,
                        title:"Login Failure",
                        buttons: {
                            Ok: function() {
                                location.reload();
                                $( this ).dialog( "close" );
                            }
                        }
                    });
                }
            },
            dataType:"json"
        });
    }
    //the data in html form in login.jsp is submitted by jquery
    $(".login_button").click(function(){
        $.fn.submitForm();
    });
});