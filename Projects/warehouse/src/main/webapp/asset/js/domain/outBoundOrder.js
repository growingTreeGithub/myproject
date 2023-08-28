//use jquery ui to set up popup box to alert user to be careful of the outBoundOrderItems on the outBoundOrder has left the warehouse or not.
$(function(){
    $(".button_outBoundAudit").click(function(){
        var url = $(this).data("url");
        var $dialog = $('<div><p>Do you confirm that all of the items in the outbound order have left the warehouse and reached the construction site? ' +
            'Once you confirm the data in the outbound order, the data can only be read-only. Are you sure?</p></div>');
        $dialog.dialog({
            resizable:false,
            height: "auto",
            width: 400,
            title:"Confirm outbound order Information",
            modal: true,
            buttons: {
                "Confirm": function() {
                    //use ajax to send request to audit the outBound Order
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
                    //retrieve the value stored in checkInventoryStatus() method in InventoryServiceImpl
                    var productName = result.productName;
                    var inventoryAmount = result.inventoryAmount;

                    if(result.inventoryStatus == 1){
                        //a new popup to notice the item has been deleted successfully
                        var $confirm =$('<div><p>The items of the outbound order have all left the warehouse and reached the construction site.</p></div>');
                        $confirm.dialog({
                            autoOpen:true,
                            modal: true,
                            title:"Confirm outbound order",
                            buttons: {
                                Ok: function() {
                                    location.reload();
                                    $( this ).dialog( "close" );
                                }
                            }
                        });
                    }else{
                        //a new popup to notice the inventory number cannot match the number that needed for outbound order.
                        var $confirm =$('<div><p>'+ productName +' on the outbound order has no enough inventory number.There is only '+ inventoryAmount +' left in the warehouse.Please purchase more items into warehouse so that the required items can meet the demand.</p></div>');
                        $confirm.dialog({
                            autoOpen:true,
                            modal: true,
                            title:"Audit failure",
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