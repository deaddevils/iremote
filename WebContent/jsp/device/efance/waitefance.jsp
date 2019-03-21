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
	var zwavedeviceid = "<s:property value='zwavedeviceid'/>";
	var channel = "<s:property value='channel'/>";
	var data = {
			"zwavedeviceid" : zwavedeviceid,
			"channel" : channel
	};
	$.ajax({
		type : "post",
		url : "/iremote/device/readefanceconfig",
		data : data,
		async : true,
		success : function(data) {
			 /* if (data.resultCode == 0) {
				 window.location.href="/iremote/device/setdeivitynamepage?zwavedeviceid=" + "<s:property value='zwavedeviceid'/>";
			} else if (data.resultCode == 30313) {
				toast('<s:text name="wakeupdevice" />');
			} else if (data.resultCode == 10020) {
				toast('<s:text name="wakeupgateway" />');
			} else if (data.resultCode == 10006) {
				toast('<s:text name="timeout" />');
			} else if (data.resultCode == 30312) {
				toast('<s:text name="gatewayoffline" />');
			} else if (data.resultCode == 10023) {
				toast('<s:text name="targetnotexist" />');
			} else if (data.resultCode == 30021) {
				toast('<s:text name="unsupportrequest" />');
			} else {
				toast(getErrorMsg(data.resultCode));
			}  */
		},
		error : function(data) {
			toast('<s:text name="errorraise" />');
		}
	})
}
var sec = 9;                  
function countDown() {        
	if(sec > 0) {
		num.innerHTML = sec--;
	} else {
		window.clearInterval(timer);
		var url="/iremote/device/setdeivitynamepage?zwavedeviceid=" + "<s:property value='zwavedeviceid'/>";
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

	<h2 align="center">loading...</h2>
	<h3 align="center" id="num" face="impact">10</h3>
</body>
</html>