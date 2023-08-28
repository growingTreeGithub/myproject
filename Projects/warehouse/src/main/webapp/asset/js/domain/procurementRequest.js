//use jquery ui to set up popup box to alert user to be careful of the purchased request.
$(function(){
    $(".button_purchase").click(function(){
        var url = $(this).data("url");
        var $dialog = $('<div><p>Do you confirm that all of the items in the request have been purchased ? ' +
            'Once you confirm the data in the request, the data can only be read-only. Are you sure?</p></div>');
        $dialog.dialog({
            resizable:false,
            height: "auto",
            width: 400,
            title:"Confirm procurement Information",
            modal: true,
            buttons: {
                "Confirm": function() {
                    //use ajax to send request to delete the item
                    $.get(url,function(data){

                    });
                    //a new popup to notice the item has been deleted successfully
                    var $confirm =$('<div><p>The items of the request have all been purchased.</p></div>');
                    $confirm.dialog({
                        autoOpen:true,
                        modal: true,
                        title:"Confirm procurement",
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