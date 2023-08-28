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

//use jquery ui to set up popup box to alert user to be careful to delete brandName.
$(function(){
    $(".button_brandName_delete").click(function(){
        //it retrieve the value of the data-url attribute and perform AJAX requests to the specified URL.
        var url = $(this).data("url");
        /*In javascript, including jQuery, a variable declared with the var keyword can store HTML elements, jQuery objects,
        * or any other value that javaScript supports. var $dialog variable holds a reference to this jQuery object, which
        * provides access to the underlying HTML elements and a set of methods and properties defined by jQuery.
        * */
        var $dialog = $('<div><p>This information will be deleted permanently and cannot be recovered. Are you sure?</p></div>');
        /*.dialog() method is an api provided by jquery ui to create a dialog window to provide message for user*/
        $dialog.dialog({
            //set resizable false means the dialog window cannot be resizable
            resizable:false,
            height: "auto",
            width: 400,
            title:"Delete Information",
            //set modal true means blocking interaction with the rest of the page
            modal: true,
            buttons: {
                "Delete item": function() {
                    //use ajax to send request to delete the brandName
                    var json = $.ajax({
                        //url to which the POST request is sent
                        url: url,
                        //specifies the HTTP method is a POST method
                        type:"POST",
                        /*the response from the server is in JSON format.
                        * jQuery will automatically parse the response into a Javascript object.
                        * */
                        dataType: 'json',
                        /*set the request to be synchronous, meaning that javascript execution
                        * will pause until the request completes.
                        * */
                        async: false,
                        /*optional data to be sent with the POST request. This can be in the form of
                        * a query string or an object containing key-value pairs
                        * */
                        data:"",
                        /*A callback function executed when the request is successful. The data parameter
                        * represents the response data returned by the server. Returning the data within
                        * the callback does not assign it to the json variable. The json variable will
                        * contain the XMLHttpRequest object returned by the $.ajax() method.
                        * */
                        success: function(data) {
                            return data;
                        }
                    });
                    /*json.responseText refers to the response text received from the server. The responseText property
                    contains the raw response data as a string. JSON.parse() is a built-in javaScript function that parses
                    a JSON string and converts it into a Javascript object. It takes the response text as input and returns
                    the parsed javascript object. if the returned JSON is simply a number one, the result variable will simply
                    store the number value 1. It will not wrapped in an object or assigned to a specific property such as result.value
                    for a JSON object.
                    * */
                    var result = JSON.parse(json.responseText);
                    if(result == 1){
                        //a new popup to notice the brandName data used in other module and cannot be deleted
                        var $confirm =$('<div><p>This brandName data has been used in other module.Please delete all the data connected with this brandName so that the deletion of this brandName data will not result in loss of data in other module.</p></div>');
                        $confirm.dialog({
                            autoOpen:true,
                            modal: true,
                            title:"Delete failure",
                            buttons: {
                                Ok: function() {
                                    //to redirect to the same page
                                    location.reload();
                                    $( this ).dialog( "close" );
                                }
                            }
                        });
                    }else{
                        //a new popup to notice the supplier is not used in other module and has been deleted successfully
                        var $confirm =$('<div><p>This brandName has been deleted successfully.</p></div>');
                        $confirm.dialog({
                            autoOpen:true,
                            modal: true,
                            title:"Delete complete",
                            buttons: {
                                Ok: function() {
                                    //to redirect to the same page
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