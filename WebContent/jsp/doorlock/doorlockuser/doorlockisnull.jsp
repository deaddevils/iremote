<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<title><s:text name="doorlocknotexist" /></title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/iremote/js/jquery.alert.js"></script>
<link type="text/css" rel="stylesheet" href="/iremote/css/iremotewebstyle.css" charset="utf-8"/>
<script>
var toapp = <s:property value='toapp'/>;
function onback()
{
	var url = "/iremote/device/lock/listlockuser?zwavedeviceid=" + zwavedeviceid;
	window.location.href=url;
}


function onbackapp()
{
	JSInterface.back();
}

if(toapp){
	setTimeout(onbackapp , 3000);
}else{
	setTimeout(onback , 3000);
}

</script>
</head>
<body class="operation_page">
<table class="title_table">
<tr >
	<s:if test="toapp == true">
		<td align="left" ><a class="button_back" href="javascript:onbackapp()"><img class="title_icon_back" src="/iremote/images/nav/nav_btn_back.png"/></a></td>
	</s:if>
	<s:else>
		<td align="left" ><a class="button_back" href="javascript:onback()"><img class="title_icon_back" src="/iremote/images/nav/nav_btn_back.png"/></a></td>
	</s:else>
	<td align="center"><span class="title_c"><s:text name="doorlocknotexist" /></span></td>
	<td align="right" style="width:40px;">&nbsp;</td>
</tr>
</table>
<center>
<hr class="operation_line"/>
<img src="/iremote/images/lock/lock_img_non-existent.png" class="tip_img"/>
<br>
<span class="message"><s:text name="doorlocknotexist" /></span>
<br>
<span class="message"><s:text name="backin3seconds" /></span>
</center>

</body>
</html>