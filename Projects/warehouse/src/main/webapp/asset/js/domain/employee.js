//to move role
$(function(){
    //to select one role to the right selected role list
    $("#select").click(function(){
        $(".allRoleList option:selected").appendTo(".selectedRoleList");
    });
    //to move all the role to the right selected role list
    $("#selectAll").click(function(){
        $(".allRoleList option").appendTo(".selectedRoleList");
    });
    //to select one role to the left all role List
    $("#deselect").click(function(){
        $(".selectedRoleList option:selected").appendTo(".allRoleList");
    });
    //to move all role to the left all role List
    $("#deselectAll").click(function(){
        $(".selectedRoleList option").appendTo(".allRoleList");
    });
    //use javascript below when update information with multiple select list
    //to get the id value of all options under selected role List through mapping function
    var ids = $.map($(".selectedRoleList option"),function(item){
        return item.value;
    });

    $.each($(".allRoleList option"),function(index, item){
        var id = item.value;
        /*to compare each option under all Role List with the options under selected Role List,
        if the option under all Role List has the same value in selected Role list, remove this option
        under all Role List
         */
        if($.inArray(id, ids)>=0){
            $(item).remove();
        }
    });
});
//it is neccessary to make the option under selected role list to be selected, otherwise no option will be submitted.
$(function(){
    $("#myForm").submit(function(){
        $(".selectedRoleList option").prop("selected",true);
    });
});
//jquery validation rules for input jsp
//please refer to jquery validation plugin official website for detail api documentation
$(function(){
    $("#myForm").validate({
        rules:{
            name:{
                required:true
            },
            password:{
                required:true,
                minlength:4
            },
            //The confirm password value need to equal to the above password value.
            //Otherwise, the below message will appear to make sure both passwords match.
            confirm_password:{
                equalTo:"#password"
            },
            age:{
                required:true,
                range:[18,65]
            }
        },
        messages:{
            name:{
                required:"This field is required"
            },
            password:{
                required:"This field is required",
                minlength:"Passwords must be at least 4 characters in length"
            },
            confirm_password:{
                equalTo:"Please make sure both passwords match"
            },
            age:{
                required:"This field is required",
                range:"age must be in the range of 18 and 65"
            }
        }
    });
});

//use jquery ui to set up popup box to alert user to be careful of the resignation of the employee.
$(function(){
    $(".button_employee_delete").click(function(){
        var url = $(this).data("url");
        var $dialog = $('<div><p>Do you confirm that the employee has resigned and ' +
            'once confirmed the employee has resigned, the data of the employee can only be read-only. Are you sure?</p></div>');
        $dialog.dialog({
            resizable:false,
            height: "auto",
            width: 400,
            title:"Resign Employee",
            modal: true,
            buttons: {
                "Confirm": function() {
                    //use ajax to send request to resign the employee
                    $.get(url,function(data){

                    });
                    //a new popup to notice the employee has resigned successfully
                    var $confirm =$('<div><p>the employee has resigned.</p></div>');
                    $confirm.dialog({
                        autoOpen:true,
                        modal: true,
                        title:"Confirm resignation",
                        buttons: {
                            Ok: function() {
                                location.reload();
                                $( this ).dialog( "close" );
                            }
                        }
                    });


                    $( this ).dialog( "close" );
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            }
        });


    });

});