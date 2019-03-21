<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<title><s:text name="commandsending" /></title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<link type="text/css" rel="stylesheet" href="/iremote/css/iremotewebstyle.css" charset="utf-8"/>
<script>
var zwavedeviceid = <s:property value='zwavedeviceid'/>;

function onback()
{
	var url = "/iremote/device/lock/listlockuser?zwavedeviceid=" + zwavedeviceid;
	window.location.href=url;
}

function onQueryStatus(data)
{
	if ( data.status != 'sendingpassword')
		window.location.href= "/iremote/device/lock/showlockmessage?zwavedeviceid=" + zwavedeviceid;
	else 
		setTimeout(querystatus , 500);
}

function querystatus()
{
	jQuery.ajax(
			{
				url:'/iremote/device/lock/getlockoperationstatus?zwavedeviceid=' + zwavedeviceid,
				success: onQueryStatus
			});
}

querystatus();

</script>
</head>
<body class="operation_page">
<table class="title_table">
<tr >
	<td align="left" width="40px" ><a href="javascript:onback(0);"><img class="title_icon_back" src="/iremote/images/nav/nav_btn_back.png"/></a></td>
	<td align="center"><span class="title_c"><s:text name="doorlockuser" /></span></td>
	<td align="right" style="width:40px;">&nbsp;</td>
</tr>
</table>
<center>
<hr class="operation_line"/>
<img src="/iremote/images/lock/lock_img_tj.gif" class="tip_img"/>
<br>
<span class="message"><s:text name="commandsending" /></span>
</center>

</body>
</html>