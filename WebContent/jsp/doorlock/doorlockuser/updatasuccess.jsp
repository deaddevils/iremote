<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<title><s:text name="editdoorlock" /></title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<link type="text/css" rel="stylesheet" href="/iremote/css/iremotewebstyle.css" charset="utf-8"/>
<script>
var system = "";

function setSystem(stm) {
    console.log('android parameter：'+ stm);
    system = stm;
}

<s:if test=" zwavedeviceid != 0 ">

var zwavedeviceid = <s:property value='zwavedeviceid'/>;

function onbackapp()
{
	var url = "/iremote/device/lock/listlockuser?zwavedeviceid=" + zwavedeviceid;
	window.location.href=url;
}
</s:if>
<s:else>
function onbackapp()
{
	//if(system == "android"){
    if(JSInterface.sumbit && typeof(JSInterface.sumbit) == "function"){
		JSInterface.sumbit();
	}else{
		JSInterface.back();
	}
}
</s:else>

setTimeout(onbackapp , 1000);


</script>
</head>
<body class="operation_page">
<table class="title_table">
<tr >
	<td align="left" width="40px" ><a href="javascript:onback(0);"><img class="title_icon_back" src="/iremote/images/nav/nav_btn_back.png"/></a></td>
	<td align="center"><span class="title_c"><s:text name="editdoorlock" /></span></td>
	<td align="right" style="width:40px;">&nbsp;</td>
</tr>
</table>
<center>
<hr class="operation_line"/>
<span class="message">
		<img src="/iremote/images/lock/lock_img_xgcg.png" class="tip_img"/>
		<br>
		<s:text name="editsuccessfully" />

</span>
<br>
<span class="message"><s:text name="backinonesecond" /></span>
</center>

</body>
</html>
