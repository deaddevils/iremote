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
<script type="text/javascript" src="/iremote/jsp/device/deviceName/mockJsInterface.js"></script>

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
var hasArmFunction = '<s:property value="hasArmFunction"/>' ;

function setSystem(stm) {
    console.log('android parameter：'+ stm);
    system = stm;
}

var regRule = /\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g;


function onback()
{
	JSInterface.back();
}

$(document).ready(function(){
	stopentersubmit();
	if(1==<s:property value="inhomenotalarm"/>){
		$("#inhomenotalarm").prop("checked","checked");
	};
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
	var cameraid = $("#cameraid").val();
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
        url:"/iremote/camera/editcamera",
        data:$('#formSumbit').serialize(),
        success : function(data) 
        {
        	if ( data.resultCode == 0 )
        	{
	        	//if(system == "android")
                if(JSInterface.sumbit && typeof(JSInterface.sumbit) == "function")
	        		JSInterface.sumbit();
	        	else
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
			<td align="center"><span class="title_c"><s:property value="camera.name"/></span></td>
			<td align="right" width="40px"><a class="button" href="javascript:onSubmit();"><img src="/iremote/images/nav/nav_btn_preservation.png" class="nav_img"/></a></td>
		</tr>
	</table>
	
<form action="/iremote/device/editcamera" id="formSumbit" method="post">
	<input type="hidden" id="cameraid" name="cameraid" value="<s:property value='cameraid'/>"/>
	<table width="100%" style="background:#ffffff;margin-top:5px;">			
      	<tr height="55px">
			<td align="right" width="5px">&nbsp;</td>
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font"><s:text name="name" />:</span>
			</td>
			<td align="left" >  
				<div>
					<input type="text" class="input_text" id="name" name="name" maxlength="32" value="<s:property value="camera.name"/>"/>
				</div>
			</td> 
		</tr>
		<s:if test="hasArmFunction==true">
			<s:if test="camera.devicetype != 3 && camera.devicetype !=4 ">
			<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
	      	<tr height="55px">
				<td align="right" width="5px">&nbsp;</td>
				<td align="left" nowrap="nowrap" width="5%">
					<span class="content_font"><s:text name="delaytime" />(s):</span>
				</td>
				<td align="left" >  
					<div>
						<input id="delay" class="input_text" name="delay" value="<s:property value="delay"/>"/>
					</div>
				</td> 
			</tr>
			<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
	      	<tr height="55px">
				<td align="right" width="5px">&nbsp;</td>
				<!-- <td align="right" nowrap="nowrap" width="5%">
					<input type="checkbox" name="inhomenotalarm" id="inhomenotalarm" value="1" style="zoom:180%;" >
				</td> -->
				<td align="left" align="left" colspan=4>  
					<div>
						<input type="checkbox" name="inhomenotalarm" id="inhomenotalarm" value="1" style="zoom:180%;vertical-align: -4px;" >
						<span class="content_font"><s:text name="inhomenotalarm" /></span>
					</div>
				</td> 
			</tr>
			</s:if>
		</s:if>
	</table>
</form>
	
</body>
</html>
