<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<title><s:text name="deviceoperation"/></title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/iremote/js/jquery.cookie.js"></script>
<link type="text/css" rel="stylesheet" href="/iremote/css/iremotewebstyle.css" charset="utf-8"/>
<style>
  
.input_text
{
	width:200px;
	height:40px;
	font: normal 18px arial, sans-serif; 
	font-family:"Microsoft YaHei";
	margin-top:15px;
	
	border-radius:6px;
	border-collapse:collapse;
	border-spacing:0;
	
	border-left:1px solid #999999;
	border-top:1px solid #999999;
	border-right:1px solid #999999;
	border-bottom:1px solid #999999;
	
	padding-left:10px ;
	padding-right:10px ;
}

.button {
	color: black;
    border: none;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
	margin-top:15px;
    cursor: pointer;
	border-radius:6px;
	border-left:1px solid #999999;
	border-top:1px solid #999999;
	border-right:1px solid #999999;
	border-bottom:1px solid #999999;
	width:220px;  
	height:44px;  
	background-color:#ffffff 
}

.button:active {
	position: relative;
	top: 1px;
}

.button:disabled {
	color: #888;
}

input::-ms-input-placeholder{text-align: center;}
input::-webkit-input-placeholder{text-align: center;}

.message
{
	width:100%;
} 
</style>
<script>

	var w30s = '<s:text name="wakeuplockin30second"/>';
	var errmsg = {
		"err30312" : '<s:text name="networkfault"/>',
		"err30315" : '<s:text name="securitycodephonenumbernotmatch"/>',
		"err10104" : '<s:text name="verificationcodenotcorrect"/>',
		"err30317" : '<s:text name="securitycodephonenumbernotmatch"/>',
		"err30022" : '<s:text name="noprivilege"/>',
		"err" : '<s:text name="operationfailed"/>',
		"sucess" : '<s:text name="operation_success"/>'
	};

	function showvalidmessage(data) {
		var mt = null;
		if (data.validtype == 1) {
			if (data.day != 0) {
				mt = '<s:text name="validindayhourmin"/>'.replace('{0}',
						data.day).replace('{1}', data.hour).replace('{2}',
						data.min);
			} else if (data.hour != 0) {
				mt = '<s:text name="validinhourmin"/>'
						.replace('{0}', data.hour).replace('{1}', data.min);
			} else
				mt = '<s:text name="validinmin"/>'.replace('{0}', data.min);

		} else if (data.validtype == 2) {
			if (data.day != 0) {
				mt = '<s:text name="validexpireindayhourmin"/>'.replace('{0}',
						data.day).replace('{1}', data.hour).replace('{2}',
						data.min);
			} else if (data.hour != 0) {
				mt = '<s:text name="validexpireinhourmin"/>'.replace('{0}',
						data.hour).replace('{1}', data.min);
			} else
				mt = '<s:text name="validexpireinmin"/>'.replace('{0}',
						data.min);
		} else if (data.validtype == 3)
			mt = '<s:text name="authorizationexpired"/>';

		if (mt != null) {
			$("#message").html(mt);
		}
	}
	function showundervalidmessage(validtype,day,hour,min) {
		var mt = null;
		if (validtype == 1) {
			if (day != 0) {
				mt = '<s:text name="validindayhourmin"/>'.replace('{0}',
						day).replace('{1}', hour).replace('{2}',
						min);
			} else if (hour != 0) {
				mt = '<s:text name="validinhourmin"/>'
						.replace('{0}', hour).replace('{1}', min);
			} else
				mt = '<s:text name="validinmin"/>'.replace('{0}', min);

		} else if (validtype == 2) {
			if (day != 0) {
				mt = '<s:text name="validexpireindayhourmin"/>'.replace('{0}',
						day).replace('{1}', hour).replace('{2}',
						min);
			} else if (hour != 0) {
				mt = '<s:text name="validexpireinhourmin"/>'.replace('{0}',
						hour).replace('{1}', min);
			} else
				mt = '<s:text name="validexpireinmin"/>'.replace('{0}',
						min);
		} else if (validtype == 3)
			mt = '<s:text name="authorizationexpired"/>';

		if (mt != null) {
			$("#undermessage").html(mt);
		}
	}

	function operate() {
		$("#commitbutton").attr("disabled", "disabled");

		setTimeout(check, 5000);
		if (wakeupintervalfun != null)
			clearInterval(wakeupintervalfun);

		$("#message").html(" ");
		$("#errormessage").text(" ");

		jQuery.ajax({
			type : 'POST',
			url : '/iremote/device/authorizedevicepperate',
			data : $("#deviceprivilegegrantform").serialize(),
			success : onSuccess,
			error : onError
		});
	}

	var wakeuptimeout = 30;
	var wakeupintervalfun = null;
	function changeWakeupTimeoutTip() {
		if (wakeuptimeout == -1) {
			$("#errormessage").text(" ");
			clearInterval(wakeupintervalfun);
		} else
			$("#errormessage").text(w30s.replace('\{0\}', wakeuptimeout));
		wakeuptimeout -= 1;
	}

	function onSuccess(data) {
		if (data.resultCode != 0) {
			var k = "err" + data.resultCode;
			if (data.resultCode == 30312 && data.wakeupdevice == true) {
				k = k + "wakeup";
				wakeuptimeout = 30;
				changeWakeupTimeoutTip();
				wakeupintervalfun = setInterval(changeWakeupTimeoutTip, 1000);
			} else
				showerrormessage(k);
		}else{
			$("#errormessage").text(errmsg["sucess"]);
		}
		if("<s:property value='token'/>"==""){
			showvalidmessage(data);
		}
		
	}

	function showerrormessage(key) {

		if (errmsg[key] != null)
			$("#errormessage").text(errmsg[key]);
		else
			$("#errormessage").text(errmsg["err"]);
	}

	function onError() {
		$("#errormessage").text(errmsg["err"]);
	}

	function check() {
		if ($("#phonenumber").val() != null
				&& ($("#securitycode").val().length == 6||$("#securitycode").val().length == 32)) {
			$("#commitbutton").removeAttr("disabled");
		} else {
			$("#commitbutton").attr("disabled", "disabled");
		}
	}

	function oninputtextkeydown() {
		if (event.keyCode == 13)
			return false;
	}

	$(document).ready(function() {
		if("<s:property value='token'/>"!=""){
			$("#phonenumber").val("<s:property value='phonenumber'/>");
			$("#securitycode").val("<s:property value='token'/>");
			document.getElementById("phonenumber").style.visibility="hidden";
			document.getElementById("securitycode").style.visibility="hidden";
			var validtype = <s:property value='validtype'/>
			var day = "<s:property value='day'/>";
			var hour ="<s:property value='hour'/>";
			var min ="<s:property value='min'/>";
			showundervalidmessage(validtype,day,hour,min);
		}else{
			var pk = $.cookie("phonenumber");
			if (pk != null)
				$("#phonenumber").val(pk);
	
			var sk = $.cookie("authtoken");
			if (sk != null)
				$("#securitycode").val(sk);
		}
		check();
	});
</script>
</head>
<body style="background-color:#f4f4f4;margin:0px;padding:0px">
<form id="deviceprivilegegrantform" action="/iremote/device/authorizedevicepperate" method="POST" onkeydown="return oninputtextkeydown();">
<input type="hidden" name="operation" value="open"/>
<table style="background-color:#00afe1" width="100%" >
<tr >
	<td width="100%" align="center">
		<img src="/iremote/images/door/doorlock/icon_doorlock.png" style="width:150px;height:150px;margin-top:10px;margin-bottom:10px"/>
	</td>   
</tr>
</table>    
<center>  

<div style="margin-top:10px">
<span class="message" id="message" ></span>
</div>
<div style="margin-top:22px;color:#F00">
<span class="message" id="errormessage"></span>
</div>
<span class="message" >
<input type="text" name="phonenumber" id="phonenumber" maxLength="16" class="input_text" placeholder="<s:text name="inputphonenumber"/>"   /><br/> 
<input type="text" name="securitycode" id="securitycode" maxLength="6" onkeyup="check();" onchange="check();" class="input_text" placeholder="<s:text name="inputsecuritycode"/>"   /><br/>
<input type="button" name="ok" id="commitbutton" onclick="operate();" class="button" value="<s:text name="unlock"/>" disabled="true" />
</span>
<div style="margin-top:10px">
<span class="message" id="undermessage" ></span>
</div>
</center>
</form>  
</body>
</html>