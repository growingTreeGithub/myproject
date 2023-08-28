//jquery validation rules for input jsp
//please refer to jquery validation plugin official website for detail api documentation
$(function(){
    $("#myForm").validate({
        rules:{
            name:{
                required:true
            },
            address:{
                required:true
            },
            phone:{
                required:true
            }
        },
        messages:{
            name:{
                required:"This field is required"
            },
            address:{
                required:"This field is required"
            },
            phone:{
                required:"This field is required"
            }
        }
    });
});

//use jquery ui to set up popup box to alert user to be careful to delete supplier.
$(function(){
    $(".button_supplier_delete").click(function(){
        var url = $(this).data("url");
        var $dialog = $('<div><p>This information will be deleted permanently and cannot be recovered. Are you sure?</p></div>');
        $dialog.dialog({
            resizable:false,
            height: "auto",
            width: 400,
            title:"Delete Information",
            modal: true,
            buttons: {
                "Delete item": function() {
                    //use ajax to send request to delete the supplier
                    var json = $.ajax({
                        url: url,
                        type:"POST",
                        dataType: 'json',
                        async: false,
                        data:"",
                        success: function(data) {
                            return data;
                        }
                    });
                    var result = JSON.parse(json.responseText);
                    if(result == 1){
                        //a new popup to notice the supplier data used in other module and cannot be deleted
                        var $confirm =$('<div><p>This supplier data has been used in other module.Please delete all the data connected with this supplier so that the deletion of this supplier data will not result in loss of data in other module.</p></div>');
                        $confirm.dialog({
                            autoOpen:true,
                            modal: true,
                            title:"Delete failure",
                            buttons: {
                                Ok: function() {
                                    location.reload();
                                    $( this ).dialog( "close" );
                                }
                            }
                        });
                    }else{
                        //a new popup to notice the supplier is not used in other module and has been deleted successfully
                        var $confirm =$('<div><p>This supplier has been deleted successfully.</p></div>');
                        $confirm.dialog({
                            autoOpen:true,
                            modal: true,
                            title:"Delete complete",
                            buttons: {
                                Ok: function() {
                                    location.reload();
                                    $( this ).dialog( "close" );
                                }
                            }
                        });
                    }

                    $( this ).dialog( "close" );
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            }
        });


    });

});