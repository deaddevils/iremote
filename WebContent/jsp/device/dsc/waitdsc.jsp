<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<title><s:text name="title" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.toast.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/device/deviceName/mockJsInterface.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/iremote.js"></script>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/iremotewebstyle.css" charset="utf-8"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.toast.css" />

<script type="text/javascript">


$(document).ready(function(){
	stopentersubmit();
	var timer;
});
window.onload=function(){         
	timer = setInterval('countDown()',1000);   
}
var sec = 9;                  
function countDown() {        
	if(sec > 0) {
		num.innerHTML = sec--;
	} else {
		window.clearInterval(timer);
		var url="/iremote/device/readDSCInfo?deviceid=" + "<s:property value='deviceid'/>";
		window.location.href=url; 
       }
}
function stopentersubmit()
{
	$("input").on('keypress',  
		function(e)
		{
			var key = window.event ? e.keyCode : e.which;
			if(key.toString() == "13")
				return false;
		}
	);
}
</script>

</head>
<body class="listpage">

	<h2 align="center"><s:text name="waitdscinfo" /></h2>
	<h3 align="center" id="num" face="impact">10</h3>
</body>
</html>