<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<title><s:text name="title" /></title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/iremote/js/jquery.toast.js"></script>
<script type="text/javascript" src="/iremote/js/iremote.js"></script>
<script type="text/javascript" src="/iremote/jsp/gateway/mockJsInterface.js"></script>

<link type="text/css" rel="stylesheet" href="/iremote/css/iremotewebstyle.css" charset="utf-8"/>
<link rel="stylesheet" type="text/css" href="/iremote/css/jquery.toast.css" />

<style type="text/css">

.input_text
{
	width:90%;
	height:40px;
	font: normal 20px arial, sans-serif; 
	font-family:"Source Han Sans";
	color:#333333;
	margin-top:0px;
	
	border-radius:5px;
	border-collapse:collapse;
	border-spacing:0;
	border:0px;
	
	margin-left:20px;
}
</style>
<script type="text/javascript">

var deviceid = "<s:property value='deviceid'/>";
var system = "";

var regRule = /\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g;


function onback()
{
	JSInterface.back();
}

$(document).ready(function(){
	stopentersubmit();
});

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

function onSubmit()
{
	var name = $("#name").val();
	if(name == null || name == "")
	{
		toast('<s:text name="devicenameisrequired"/>');
		return false;
	}
	
	if(name.match(regRule)) 
	{
	    name = name.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
	    $(obj).val(name);
	    toast('<s:text name="wildcharnotsupport"/>');
	    return false;
	} 
	
	$.ajax({
        type: "post",
        url:"/iremote/gateway/editgateway",
        data:$('#formSumbit').serialize(),
        success : function(data) 
        {
        	if ( data.resultCode == 0 )
        	{
	        	JSInterface.back();
        	}
        	else if ( data.resultCode == 30545)
        		toast('<s:text name="devicenamecrash"/>');
        },
        error : function(data) {
        	alert('<s:text name="networkerror"/>');
        }
	})
}

</script>

</head>
<body class="listpage">
	<table class="title_table">
		<tr>
			<td align="left" width="40px"><a href="javascript:onback(0);"><img src="/iremote/images/nav/nav_btn_back.png"  class="nav_img"/></a></td>
			<td align="center"><span class="title_c"><s:text name="modifygateway"/></span></td>
			<td align="right" width="40px"><a class="button" href="javascript:onSubmit();"><img src="/iremote/images/nav/nav_btn_preservation.png" class="nav_img"/></a></td>
		</tr>
	</table>
	
<form action="/iremote/gateway/editgateway" id="formSumbit" method="post">
	<input type="hidden" id="deviceid" name="deviceid" value="<s:property value='deviceid'/>"/>
	<table width="100%" style="background:#ffffff;margin-top:5px;">			
      	<tr height="55px">
			<td align="right" width="5px">&nbsp;</td>
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font"><s:text name="gatewayname"/>:</span>
			</td>
			<td align="left" >  
				<div>
					<input type="text" class="input_text" id="name" name="name" maxlength="32" value="<s:property value="name"/>"/>
				</div>
			</td> 
		</tr>
	</table>
</form>
	
</body>
</html>