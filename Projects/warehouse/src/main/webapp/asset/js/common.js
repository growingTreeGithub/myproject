//to jump to another page.for example, Add a Employee
$(function(){
    $(".button_redirect").click(function(){
        //it retrieve the value of the data-url attribute and redirect the page to the specified URL.
        window.location.href=$(this).data("url");
    });
});

$(function(){
    $(".pagination_button").click(function() {
        //Get jumpPageNo from textfield or clickButton. It retrieve the value from pagination.jsp.
        var jumpPage = $(this).data("page") || $(":input[name='currentPage']").val();
        //to put jumpPageNo value into textfield
        $(":input[name='currentPage']").val(jumpPage);
        //submit searchForm including search criteria and paging requirement
        $("#searchForm").submit();
    });
    //to reload page when changing pageSize
    $(":input[name='pageSize']").change(function(){
        $("#searchForm").submit();
    });
});


//use jquery ui to set up popup box to alert user to be careful of the delete item.
$(function(){
    $(".button_delete").click(function(){
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
                    //use ajax to send request to delete the item
                    $.get(url,function(data){

                    });
                    //a new popup to notice the item has been deleted successfully
                    var $confirm =$('<div><p>This item has been deleted successfully.</p></div>');
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


                    $( this ).dialog( "close" );
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            }
        });


    });

});

//to reset fill in form
$(function(){
    $("#reset_field").click(function(){
        $("#myForm").trigger("reset");
    });
});