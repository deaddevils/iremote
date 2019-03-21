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
		           	 <s:if test="platform == 9 ">
				         <img style="width:100%;height:100%" src="advert/ametaweather?ip=<s:property value='ip'/>&longitude=<s:property value='longitude'/>&latitude=<s:property value='latitude'/>&time=<s:property value='time'/>" data-thumb="advert/weather?ip=<s:property value='ip'/>&longitude=<s:property value='longitude'/>&latitude=<s:property value='latitude'/>&time=<s:property value='time'/>" alt="" style="margin:0px;padding:0px;widht:100%;height:100%"/>
				         <img style="width:100%;height:100%" src="advert/ametaweather3?ip=<s:property value='ip'/>&longitude=<s:property value='longitude'/>&latitude=<s:property value='latitude'/>&time=<s:property value='time'/>" data-thumb="advert/weather?ip=<s:property value='ip'/>&longitude=<s:property value='longitude'/>&latitude=<s:property value='latitude'/>&time=<s:property value='time'/>" alt="" style="margin:0px;padding:0px;widht:100%;height:100%"/>
					</s:if>
					<s:else>
						<s:if test=" adverpicname == null ">
				         	<img src="images/advert_oem/en_us/home_img_en.png" style="margin:0px;padding:0px;width:100%;height:100%"/>
						</s:if>
						<s:else>
							<s:iterator value="adverpicname" id="apn">
								<img src="images/advert_oem/en_us/<s:property value='platform'/>/<s:property value='#apn'/>" style="margin:0px;padding:0px;width:100%;height:100%"/>
							</s:iterator>
						</s:else>
					</s:else>
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
