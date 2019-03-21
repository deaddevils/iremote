function toast(content)
{
	$.toast({
	    text: content, // Text that is to be shown in the toast
	    showHideTransition: 'fade', // fade, slide or plain
	    allowToastClose: false, // Boolean value true or false
	    hideAfter: 3000, // false to make it sticky or number representing the miliseconds as time after which toast needs to be hidden
	    stack: 5, // false if there should be only one toast at a time or a number representing the maximum number of toasts to be shown at a time
	    position: 'bottom-center', // bottom-left or bottom-right or bottom-center or top-left or top-right or top-center or mid-center or an object representing the left, right, top, bottom values
	    
	    bgColor: '#808080',  // Background color of the toast
	    textColor: '#000000',  // Text color of the toast
	    textAlign: 'center',  // Text alignment i.e. left, right or center
	    loader: false,  // Whether to show loader or not. True by default
	    loaderBg: '#9ec600',  // Background color of the toast loader
	    beforeShow: function () {}, // will be triggered before the toast is shown
	    afterShown: function () {}, // will be triggered after the toat has been shown
	    beforeHide: function () {}, // will be triggered before the toast gets hidden
	    afterHidden: function () {}  // will be triggered after the toast has been hidden
	});
}