<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<title><s:text name='errorraise'/></title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<link type="text/css" rel="stylesheet" href="/iremote/css/phoneappstyle.css" charset="utf-8"/>
<script>
var errormessage = 
{
	30315 : "<s:text name='errmsg_notexists'/>"
};

var errmsg_errcode = "<s:text name='errmsg_errcode'/>";

function setmessage(msg)
{
	$("#message").text(msg); 
}

$(document).ready(function(){
	var errcode = <s:property value='resultCode'/>;
	
	if ( errormessage[errcode] != null )
		setmessage(errormessage[errcode]);
	else 
	{
		setmessage(errcode.replace('\{0\}' ,errcode));
	}
});
</script>

</head>
<body style="background-color:#f4f4f4;margin:0px;padding:0px">
<table style="background-color:#4fc1e9" width="100%" height="50px">
<tr >
	<td align="left" ><a class="button_back" href="javascript:onback(0);" style="visibility:hidden;"><span>&nbsp;</span></a>&nbsp;</td>
	<td align="center"><span class="title_c"><s:text name='errorraise'/></span></td>
	<td align="right" style="display: block; width:80px;"><span >&nbsp;</span></td>
</tr>
</table>
<center>
<img src="/iremote/images/icon_failed.png" class="tip_img"/>
<span class="message" id="message"></span>
</center>

</body>
</html>
