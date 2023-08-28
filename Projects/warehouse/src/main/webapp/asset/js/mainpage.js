/*
* toggleClass() function is used to add or remove one or remove one or more CSS classes
* from selected elements.It toggles the presence of a class on the selected elements.
* That means if the class is already applied to an element,it will be removed, and if
* it's not applied, it will be added.
* When clicking the main titles, the css class "flip" will be added to basic arrow and
* css class "hide" will be added to sub titles.It will create a animation that a arrow sign
* rotate 180 degree and the sub title appear under the main titles.When clicking the main titles
* again, the arrow sign will rotate 180 degree again and the sub title will appear because the
* css class "flip" and "hide" will be removed if it is already inside the css property of the
* elements.
* */
$(function(){
    $(".basic_main_title").click(function(){
        $(".basic_arrow").toggleClass("flip");
        $(".basic_sub_title").toggleClass("hide");
    });
    $(".permission_main_title").click(function(){
        $(".permission_arrow").toggleClass("flip");
        $(".permission_sub_title").toggleClass("hide");
    });
    $(".stock_main_title").click(function(){
        $(".stock_arrow").toggleClass("flip");
        $(".stock_sub_title").toggleClass("hide");
    });
});

//to jump to another page.for example, Add a Employee
$(function(){
    $(".button_logout").click(function(){
        window.location.href=$(this).data("url");
    });
});
