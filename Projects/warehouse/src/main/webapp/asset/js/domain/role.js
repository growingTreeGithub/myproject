//to move permission
$(function(){
    //to select one permission to the right selected permission list
    $("#select").click(function(){
        $(".allPermissionList option:selected").appendTo(".selectedPermissionList");
    });
    //to move all the permission to the right selected permission list
    $("#selectAll").click(function(){
        $(".allPermissionList option").appendTo(".selectedPermissionList");
    });
    //to select one permission to the left all Permission List
    $("#deselect").click(function(){
        $(".selectedPermissionList option:selected").appendTo(".allPermissionList");
    });
    //to move all permission to the left all Permission List
    $("#deselectAll").click(function(){
        $(".selectedPermissionList option").appendTo(".allPermissionList");
    });
    //use javascript below when update information with multiple select list
    //to get the id value of all options under selected Permission List through mapping function
     var ids = $.map($(".selectedPermissionList option"), function(item){
        return item.value;
    });
    $.each($(".allPermissionList option"),function(index, item){
        var id = item.value;
        /*to compare each option under all Permission List with the options under selected Permission List,
        if the option under all permission List has the same value in selected permission list, remove this option
        under all Permission List
         */
        if($.inArray(id, ids)>=0){
            $(item).remove();
        }
    });

});
//it is neccessary to make the option under selected permission list to be selected, otherwise no option will be submitted.
$(function(){
   $("#myForm").submit(function(){
       $(".selectedPermissionList option").prop("selected", true);
   });
});

//jquery validation rules for input jsp
//please refer to jquery validation plugin official website for detail api documentation
$(function(){
    $("#myForm").validate({
        rules:{
            name:{
                required:true
            }
        },
        messages:{
            name:{
                required:"This field is required"
            }
        }
    });
});