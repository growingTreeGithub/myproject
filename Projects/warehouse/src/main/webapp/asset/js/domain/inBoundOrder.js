//use jquery ui to set up popup box to alert user to be careful whether the inBoundOrderItems on the inBoundOrder have all reached the warehouse or not.
$(function(){
    $(".button_inBoundAudit").click(function(){
        var url = $(this).data("url");
        var $dialog = $('<div><p>Do you confirm that all of the items in the inbound order have reached the warehouse?' +
            'Once you confirm the data in the inbound order, the data can only be read-only. Are you sure?</p></div>');
        $dialog.dialog({
            resizable:false,
            height: "auto",
            width: 400,
            title:"Confirm inbound order Information",
            modal: true,
            buttons: {
                "Confirm": function() {
                    //use ajax to send request to delete the item
                    /*url include url to which GET request is sent and also request data from the frontend client
                    * For example: url can represent /inBoundOrder/audit?id=9
                    * The data parameter within the callback function represents the response data returned by the server,
                    * not the request data received from the frontend client.
                    * */
                    $.get(url,function(data){

                    });
                    //a new popup to notice the item has been deleted successfully
                    var $confirm =$('<div><p>The items of the inbound order have all reached the warehouse.</p></div>');
                    $confirm.dialog({
                        autoOpen:true,
                        modal: true,
                        title:"Confirm inbound order",
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