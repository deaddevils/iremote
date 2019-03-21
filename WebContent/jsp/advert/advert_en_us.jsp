<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <link rel="stylesheet" href="css/themes/default/default.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/themes/light/light.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/themes/dark/dark.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/themes/bar/bar.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/nivo-slider.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
</head>
    <body style="margin:0px;padding:0px">
            <div class="slider-wrapper theme-default" style="margin:0px;padding:0px;widht:100%;height:100%">
            <div id="slider" class="nivoSlider" style="margin:0px;padding:0px;widht:100%;height:100%">
            	<img style="width:100%;height:100%" src="images/advert/en_us/title1.jpg" data-thumb="images/advert/en_us/title1.jpg" alt=""  style="margin:0px;padding:0px;widht:100%;height:100%"/>
            	<img style="width:100%;height:100%" src="images/advert/en_us/title2.jpg" data-thumb="images/advert/en_us/title2.jpg" alt=""  style="margin:0px;padding:0px;widht:100%;height:100%"/>
            	<img style="width:100%;height:100%" src="images/advert/en_us/title3.jpg" data-thumb="images/advert/en_us/title3.jpg" alt=""  style="margin:0px;padding:0px;widht:100%;height:100%"/>
            	<img style="width:100%;height:100%" src="images/advert/en_us/title4.jpg" data-thumb="images/advert/en_us/title4.jpg" alt=""  style="margin:0px;padding:0px;widht:100%;height:100%"/>
            </div>
        </div>
	   <script type="text/javascript" src="js/jquery-1.9.0.min.js"></script>
	    <script type="text/javascript" src="js/jquery.nivo.slider.js"></script>
	    <script type="text/javascript">
	    $(window).load(function() {
	        $('#slider').nivoSlider({controlNav:false,effect:'fade'});
	    });
	    </script>
    </body>
</html>
