//this function is used to clear all the data in the input field when there is only one row left inside the outBoundOrder list
function clearTrData(tr){
    //tr refers to jQuery object that represent a <tr> element or a set of <tr> elements.
    /*tr.find('[tag=itemsId]') is used to search for <tr> elements that have a custom attribute
    named "tag" with a value of "itemsId".
    * */
    tr.find('[tag=itemsId]').attr('value','');
    tr.find('[tag=productId]').attr('value','');
    tr.find('[tag=brandName]').attr('value','');
    tr.find('[tag=costPrice]').attr('value','');
    tr.find('[tag=amount]').attr('value','');
    tr.find('[tag=totalPrice]').attr('value','');
    tr.find('[tag=itemsId]').val("");
    tr.find('[tag=productId]').val("");
    tr.find('[tag=brandName]').val("");
    tr.find('[tag=costPrice]').val("");
    tr.find('[tag=amount]').val("");
    tr.find('[tag=totalPrice]').val("");
}


//this function is used to change and display the corresponding brandName when clicking and select a specific product in a outBoundOrderItem row
//refer line 72 to 73 in input.jsp of the outBoundOrder
$(function(){
    $(".itemProductList").change(function(){
        var $option = $(this).find("option:selected");
        var id =$option.val();
        var json = $.ajax({
            url: "/outBoundOrderItem/productData?id="+id,
            type:"POST",
            dataType: 'json',
            async: false,
            data:"",
            success: function(data) {
                return data;
            }
        });
        var result = JSON.parse(json.responseText);
        var tr = $(this).closest("tr");
        //get the name of brandName by selectByPrimaryKey() method in ProductDao
        tr.find("[tag=brandName]").val(result.brandName.name);
    });

    //this is used to add a new row in outBoundOrderList for entering a outBoundOrderItem's data
    $(".addRow").click(function(){
        /*clone the first table row<tr> within <tbody id="edit_table_body"> tag.
        The parameter true passed to clone() method indicates that event handlers and data associated
        with the element <tr> should also be cloned.
        * */
        var tr = $("#edit_table_body tr:first").clone(true);
        /*clear the data within the cloned table row<tr>, ensuring that the value of the input fields
        inside the row are reset before appending it.
        * */
        clearTrData(tr);
        //append the cloned row as a new row within the table.
        tr.appendTo($("#edit_table_body"));
    });

});

/*this function is used to calculate totalPrice of an outBoundOrderItem and show it on the page to prevent the user
* from calculating a wrong total price.
* */
/*when a change event occurs on an element within <tbody id="edit_table_body"> that has either tag="costPrice
or tag="amount", the event handler function will be executed. The function finds the closest<tr> element relative
to the changed element and assigns it to the tr variable. */
$(function() {
    $("#edit_table_body").on("change", "[tag=costPrice],[tag=amount]", function (){
        var tr = $(this).closest("tr");
        /*Find the input field within the current table row that has tag="costPrice" and retrieve its value
        * using .val() and converts it to a floating point number using parseFloat()
        * */
        var costPrice = parseFloat(tr.find("[tag=costPrice]").val());
        /*
        * it finds the input field within the current table row that has tag="amount" and retrieve its value
        * using .val() and converts it to a floating point number using parseFloat()
        * */
        var amount = parseFloat(tr.find("[tag=amount]").val());
        /*if both amount and costPrice variables are not null, undefined, NaN or an empty string.
        * if both values are present, the code inside the if statement is executed.
        * */
        if (amount && costPrice) {
            /*calculate the total price by multiplying costPrice and amount, and then uses .toFixed(2) to round
            * the result to two decimal places. Finally, set the value of the calculated total price of the
            * input field with tag="totalPrice" within the current table row<tr>.
            * */
            tr.find("[tag=totalPrice]").val((costPrice * amount).toFixed(2));
        }
    })
});

//this function is used to delete an outBoundOrderItem
$(function(){
    $(".button_delete").click(function(){
        var tr = $(this).closest("tr");
        var targetId = tr.find('[tag=itemsId]').val();
        //for the case when the existing input field having data is not an existing saved outBoundOrderItem in database
        //that means just fill in the data in the input field and still not submit and save in database
        if(targetId =='0'|| targetId == '' || targetId == 'undefined' || targetId == null ){
            //For the case when there is only one input row remaining in the outBoundOrder
            if($("#edit_table_body tr").length == 1){
                //remain one input row and clean all the data in the row
                clearTrData(tr);
            }else{
                //For the case when there are more than one input row remaining in the outBoundOrder
                //just remove the new added row
                tr.remove();
            }
        }else{
            //For the case when the existing input data is an existing saved outBoundOrderItem in database
            var $dialog = $('<div><p>This information will be deleted permanently and cannot be recovered. Are you sure?</p></div>');
            //For the case when there is only one input row remaining in the outBoundOrder
            if($("#edit_table_body tr").length == 1){
                var orderId = $("#orderId").val();
                var itemId = targetId;
                //clearTrData(tr);
                $dialog.dialog({
                    resizable:false,
                    height: "auto",
                    width: 400,
                    title:"Delete Information",
                    modal: true,
                    buttons: {
                        "Delete item": function() {
                            /*if there is remaining one row to be deleted, keep the row and clean the row data.
                            Otherwise, data will remain in the row input field.
                             */
                            //if the row data isn't cleaned, the itemsId will still exist and it will cause error when clicking confirm button.
                            clearTrData(tr);
                            //use ajax to send request to delete the item
                            $.ajax({
                                url: "/outBoundOrderItem/delete?itemId="+itemId+"&orderId="+orderId,
                                type:"POST",
                                dataType: 'json',
                                async: false,
                                data:"",
                                success: function(data) {

                                }
                            });
                            //a new popup to notice the item has been deleted successfully
                            var $confirm =$('<div><p>This item has been deleted successfully.</p></div>');
                            $confirm.dialog({
                                autoOpen:true,
                                modal: true,
                                title:"Delete complete",
                                buttons: {
                                    Ok: function() {
                                        //location.reload();
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

            }else{
                //For the case when there are more than one input row remaining in the outBoundOrder
                var orderId = $("#orderId").val();
                var itemId = targetId;
                $dialog.dialog({
                    resizable:false,
                    height: "auto",
                    width: 400,
                    title:"Delete Information",
                    modal: true,
                    buttons: {
                        "Delete item": function() {
                            //use ajax to send request to delete the item
                            $.ajax({
                                url: "/outBoundOrderItem/delete?itemId="+itemId+"&orderId="+orderId,
                                type:"POST",
                                dataType: 'json',
                                async: false,
                                data:"",
                                success: function(data) {

                                }
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
            }
        }
    });
});

//jquery validation rules for input jsp
//please refer to jquery validation plugin official website for detail api documentation
$(function(){
    $("#myForm").validate({
        rules:{
            serialNumber:{
                required:true
            },
            outBoundDate:{
                required:true
            },
            itemsCostPrice:{
                required:true
            },
            itemsAmount:{
                required:true
            }
        },
        messages:{
            serialNumber:{
                required:"This field is required"
            },
            outBoundDate:{
                required:"This field is required"
            },
            itemsCostPrice:{
                required:"This field is required"
            },
            itemsAmount:{
                required:"This field is required"
            }
        }
    });
});

//to reset fill in form
$(function(){
    $("#reset_field").click(function(){
        $("#myForm").trigger("reset");
    });
});