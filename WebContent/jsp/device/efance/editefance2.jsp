<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/language.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" />
<title><s:text name="title" /></title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/iremote/js/jquery.toast.js"></script>
<script type="text/javascript" src="/iremote/js/iremote.js"></script>
<script type="text/javascript"
	src="/iremote/jsp/device/deviceName/mockJsInterface.js"></script>
<link type="text/css" rel="stylesheet"
	href="/iremote/css/iremotewebstyle.css" charset="utf-8" />
<link rel="stylesheet" type="text/css"
	href="/iremote/css/jquery.toast.css" />
<link rel="stylesheet" href="/iremote/css/rangeslider.css">
<script src="/iremote/js/rangeslider.min.js"></script>
<style type="text/css">
.input_option {
	width: 50px;
	height: 40px;
	font: normal 16px arial, sans-serif;
	font-family: "Microsoft YaHei";
	margin-top: 0px;
	border-radius: 5px;
	border-collapse: collapse;
	border-spacing: 0;
	border-left: 0px solid #888;
	border-top: 0px solid #888;
	border-right: 0px solid #888;
	border-bottom: 0px solid #888;
	padding-left: 40px;
}

.input_text {
	width: 80%;
	height: 40px;
	font: normal 20px arial, sans-serif;
	font-family: "Source Han Sans";
	color: #333333;
	margin-top: 0px;
	border-radius: 5px;
	border-collapse: collapse;
	border-spacing: 0;
	border: 0px;
	margin-left: 20px;
}
</style>
<script type="text/javascript">
	function initrange() {
		var $document = $(document);
		var selector = '[data-rangeslider]';
		var $inputRange = $(selector);
		// Example functionality to demonstrate a value feedback
		// and change the output's value.
		function valueOutput(element) {
			var value = element.value;
			var output = element.parentNode.getElementsByTagName('output')[0];
			output.innerHTML = value;
		}
		// Initial value output
		for (var i = $inputRange.length - 1; i >= 0; i--) {
			valueOutput($inputRange[i]);
		}
		;
		// Update value output
		$document.on('input', selector, function(e) {
			valueOutput(e.target);
		});
		// Initialize the elements
		$inputRange.rangeslider({
			polyfill : false
		});
		// Example functionality to demonstrate programmatic value changes
		$document
				.on('click', '#Slider1 button',
						function(e) {
							var $inputRange = $('input[type="range"]',
									e.target.parentNode);
							var value = $('input[type="number"]',
									e.target.parentNode)[0].value;
							$inputRange.val(value).change();
						});
		// Example functionality to demonstrate programmatic attribute changes
		$document.on('click', '#js-example-change-attributes button', function(
				e) {
			var $inputRange = $('input[type="range"]', e.target.parentNode);
			var attributes = {
				min : $('input[name="min"]', e.target.parentNode)[0].value,
				max : $('input[name="max"]', e.target.parentNode)[0].value,
				step : $('input[name="step"]', e.target.parentNode)[0].value
			};
			$inputRange.attr(attributes).rangeslider('update', true);
		});
	}

	var deviceid = "<s:property value='deviceid'/>";
	var system = "";

	function setSystem(stm) {
		console.log('android parameter：' + stm);
		system = stm;
	}

	var regRule = /\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g;

	function onback() {
		JSInterface.back();
	}

	$(document).ready(function() {
		stopentersubmit();
		initefance();
		initrange();
	});

	function stopentersubmit() {
		$("input").on('keypress', function(e) {
			var key = window.event ? e.keyCode : e.which;
			if (key.toString() == "13")
				return false;
		});
	}

	function onSubmit() {
		if (!validateZwaveName($("#name"))) {
			return;
		}
		var array = $("input[name='switchsNames']");
		for (var i = 0; i < array.length; i++) {
			if (!validateChannelName(array[i])) {
				return;
			}
		}
		var data = $('#formSumbit').serialize();
		var message = '<s:text name="devicenamecrash" />';
		validate(data, true, message);
	}

	//效验设备名
	function validateZwaveName(obj) {
		var name = $(obj).val();
		var zwavedeviceid = $("#zwavedeviceid").val();
		if (name == null || name == "") {
			toast('<s:text name="devicenameisrequired" />');
			return false;
		}
		if (name.match(regRule)) {
			name = name.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g,
					"");
			$(obj).val(name);
			toast('<s:text name="wildcharnotsupport" />');
			return false;
		}

		var data = {
			"name" : name,
			"zwavedeviceid" : zwavedeviceid
		};
		var message = '<s:text name="devicenamecrash" />';
		return validate(data, false, message);
	}

	//效验通道名
	function validateChannelName(obj) {
		var switchsNames = $(obj).val();
		var zwavedeviceid = $("#zwavedeviceid").val();
		if (switchsNames.match(regRule)) {
			switchsNames = switchsNames.replace(
					/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
			$(obj).val(switchsNames);
			toast('<s:text name="wildcharnotsupport" />');
			return false;
		}
		var array = $("input[name='switchsNames']");
		for (var i = 0; i < array.length; i++) {
			if (array[i].value == "") {
				toast('<s:text name="channelnamerequired" />');
				return false;
			}
			for (var j = i + 1; j < array.length; j++) {
				if (array[i].value != "" && array[i].value == array[j].value) {
					toast('<s:text name="channelnamerepeated" />');
					return false;
				}
			}
		}
		var data = {
			"switchsNames" : switchsNames,
			"zwavedeviceid" : zwavedeviceid
		};
		var message = '<s:text name="channelnameexist" />';
		return validate(data, false, message);
	}

	function Submit() {
		$.ajax({
			type : "post",
			url : "/iremote/device/setdeivityname",
			data : $('#formSumbit').serialize(),
			success : function(data) {
				//if(system == "android"){
				if (JSInterface.sumbit
						&& typeof (JSInterface.sumbit) == "function") {
					JSInterface.sumbit();
				} else {
					JSInterface.back();
				}
			},
			error : function(data) {
				toast('<s:text name="errorraise" />');
			}
		})
	}

	function validate(data, flag, message) {
		var isExist = false;
		$.ajax({
			type : "post",
			url : "/iremote/device/validateName",
			data : data,
			async : false,
			success : function(data) {
				if (data.resultCode == 0) {
					if (flag)
						Submit();
					isExist = true;
				} else {
					toast(message);
				}
			},
			error : function(data) {
				toast('<s:text name="errorraise" />');
			}
		})
		return isExist;
	}
	
	function initefance() {
		$(":radio[name='voltagechoose1'][value='" + <s:property value="voltagechoose[0]"/> + "']").prop("checked", "checked");
		if(<s:property value="frequency[0]"/>!=-1){
			$('#Slider1').val(<s:property value="frequency[0]"/>);		
		}else{
			document.getElementById("frequency1").innerHTML = '<s:text name="unsynchronized"/>';
		}
		if(<s:property value="sensitivityofefance[0]"/>!=-1){
			$('#Slider2').val(<s:property value="sensitivityofefance[0]"/>);		
		}else{
			document.getElementById("sensitivity1").innerHTML = '<s:text name="unsynchronized"/>';
		}
		if(<s:property value="baojingmenxian[0]"/>!=-1){
			$('#Slider3').val(<s:property value="baojingmenxian[0]"/>);		
		}else{
			document.getElementById("baojingmenxian1").innerHTML = '<s:text name="unsynchronized"/>';
		}
		if(<s:property value="delaytimes[0]"/>!=-1){
			$('#Slider4').val(<s:property value="delaytimes[0]"/>);		
		}else{
			document.getElementById("delaytimes1").innerHTML = '<s:text name="unsynchronized"/>';
		}
		if(<s:property value="alarmtimes[0]"/>!=-1){
			$('#Slider5').val(<s:property value="alarmtimes[0]"/>);		
		}else{
			document.getElementById("alarmtimes1").innerHTML = '<s:text name="unsynchronized"/>';
		}
		if(<s:property value="highvoltage[0]"/>!=-1){
			$('#Slider6').val(<s:property value="highvoltage[0]"/>);		
		}else{
			document.getElementById("highvoltage1").innerHTML = '<s:text name="unsynchronized"/>';
		}	
		if(<s:property value="lowvoltage[0]"/>!=-1){
			$('#Slider7').val(<s:property value="lowvoltage[0]"/>);		
		}else{
			document.getElementById("lowvoltage1").innerHTML = '<s:text name="unsynchronized"/>';
		}
		
		$(":radio[name='voltagechoose2'][value='" + <s:property value="voltagechoose[1]"/> + "']").prop("checked", "checked");
		if(<s:property value="frequency[1]"/>!=-1){
			$('#Slider8').val(<s:property value="frequency[1]"/>);		
		}else{
			document.getElementById("frequency2").innerHTML = '<s:text name="unsynchronized"/>';
		}
		if(<s:property value="sensitivityofefance[1]"/>!=-1){
			$('#Slider9').val(<s:property value="sensitivityofefance[1]"/>);		
		}else{
			document.getElementById("sensitivity2").innerHTML = '<s:text name="unsynchronized"/>';
		}
		if(<s:property value="baojingmenxian[1]"/>!=-1){
			$('#Slider10').val(<s:property value="baojingmenxian[1]"/>);		
		}else{
			document.getElementById("baojingmenxian2").innerHTML = '<s:text name="unsynchronized"/>';
		}
		if(<s:property value="delaytimes[1]"/>!=-1){
			$('#Slider11').val(<s:property value="delaytimes[1]"/>);		
		}else{
			document.getElementById("delaytimes2").innerHTML = '<s:text name="unsynchronized"/>';
		}
		if(<s:property value="alarmtimes[1]"/>!=-1){
			$('#Slider12').val(<s:property value="alarmtimes[1]"/>);		
		}else{
			document.getElementById("alarmtimes2").innerHTML = '<s:text name="unsynchronized"/>';
		}
		if(<s:property value="highvoltage[1]"/>!=-1){
			$('#Slider13').val(<s:property value="highvoltage[1]"/>);		
		}else{
			document.getElementById("highvoltage2").innerHTML = '<s:text name="unsynchronized"/>';
		}	
		if(<s:property value="lowvoltage[1]"/>!=-1){
			$('#Slider14').val(<s:property value="lowvoltage[1]"/>);		
		}else{
			document.getElementById("lowvoltage2").innerHTML = '<s:text name="unsynchronized"/>';
		}
	}
	function calibrate(obj){
		var zwavedeviceid = $("#zwavedeviceid").val();
		var calibrate = 1;
		var channel = 1;
		if(obj=="2"){
			channel = 2;
		}
		
		var data = {
			"deviceid" : deviceid,
			"zwavedeviceid" : zwavedeviceid,
			"calibrate" : calibrate,
			"channel" : channel
		};
		$.ajax({
			type : "post",
			url : "/iremote/device/setefanceconfig2",
			data : data,
			async : false,
			success : function(data) {
				if (data.resultCode == 0) {
					toast('<s:text name="operation_success" />');
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
				}
			},
			error : function(data) {
				toast('<s:text name="errorraise" />');
			}
		})
	}
	function set(obj) {
		var zwavedeviceid = $("#zwavedeviceid").val();
		var voltagechoose = $("input[name='voltagechoose1']:checked").val();
		var frequency = $('#Slider1').val();
		var sensitivity = $('#Slider2').val();
		var baojingmenxian = $('#Slider3').val();
		var delaytimes = $('#Slider4').val();
		var alarmtimes = $('#Slider5').val();
		var highvoltage = $('#Slider6').val();
		var lowvoltage = $('#Slider7').val();
		var channel = 1;
		if(obj=="2"){
			channel = 2;
			voltagechoose = $("input[name='voltagechoose2']:checked").val();
			frequency = $('#Slider8').val();                                
			sensitivity = $('#Slider9').val();                              
			baojingmenxian = $('#Slider10').val();                           
			delaytimes = $('#Slider11').val();                               
			alarmtimes = $('#Slider12').val();                               
			highvoltage = $('#Slider13').val();                              
			lowvoltage = $('#Slider14').val();                               
		}
		var data = {
				"deviceid" : deviceid,
				"zwavedeviceid" : zwavedeviceid,
				"voltagechoose" : voltagechoose,
				"frequency" : frequency,
				"sensitivity" : sensitivity,
				"baojingmenxian" : baojingmenxian,
				"delaytimes" : delaytimes,
				"alarmtimes" : alarmtimes,
				"highvoltage" : highvoltage,
				"channel" : channel
		};
		var data1 = {
				"deviceid" : deviceid,
				"zwavedeviceid" : zwavedeviceid,
				"lowvoltage" : lowvoltage,
				"channel" : channel
		};
		$.ajax({
			type : "post",
			url : "/iremote/device/setefanceconfig2",
			data : data,
			async : false,
			success : function(data) {
				if (data.resultCode == 0) {
					$.ajax({
						type : "post",
						url : "/iremote/device/setefanceconfig2",
						data : data1,
						async : false,
						success : function(data) {
							if (data.resultCode == 0) {
								toast('<s:text name="operation_success" />');
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
							}
						},
						error : function(data) {
							toast('<s:text name="theoperationoflowvoltagefailed" />');
						}
					})
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
				}
			},
			error : function(data) {
				toast('<s:text name="errorraise" />');
			}
		})
	}
	function readConfig(obj) {
		var channel = 1;
		if(obj=="2"){
			channel = 2;                             
		}
		var url = "/iremote/device/readefanceconfigre?zwavedeviceid=" + "<s:property value='zwavedeviceid'/>&channel="+channel;
		window.location.href=url;
	}
	/* function readConfig(obj) {
		var zwavedeviceid = $("#zwavedeviceid").val();
		var channel = 1;
		if(obj=="2"){
			channel = 2;                             
		}
		var data = {
				"zwavedeviceid" : zwavedeviceid,
				"channel" : channel
		};
		$.ajax({
			type : "post",
			url : "/iremote/device/readefanceconfig",
			data : data,
			async : false,
			success : function(data) {
				if (data.resultCode == 0) {
					window.location.reload();
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
				}
			},
			error : function(data) {
				toast('<s:text name="errorraise" />');
			}
		})
	} */
</script>

</head>
<body class="listpage">
	<table class="title_table">
		<tr>
			<td align="left" width="40px"><a href="javascript:onback(0);"><img
					src="/iremote/images/nav/nav_btn_back.png" class="nav_img" /></a></td>
			<td align="center"><span class="title_c"><s:text
						name="title" /></span></td>
			<td align="right" width="40px"><a href="javascript:onSubmit(this);"><img
					src="/iremote/images/nav/nav_btn_preservation.png" class="nav_img" /></a></td>
		</tr>
	</table>

	<form action="/iremote/device/setdeivityname" id="formSumbit"
		method="post">
		<input type="hidden" id="zwavedeviceid" name="zwavedeviceid"
			value="<s:property value='zwavedeviceid'/>" />

		<table width="100%" style="background: #ffffff; margin-top: 5px;">
			<tr height="55px">
				<td align="right" width="5px">&nbsp;</td>
				<td align="left" nowrap="nowrap" width="5%"><span class="content_font"><s:text name="name" />:</span></td>
				<td align="left">
					<div>
						<input type="text" class="input_text" id="name" name="name" maxlength="32" value="<s:property value="zwaveDevice.name"/>" />
					</div>
				</td>
			</tr>
			<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
	      	<tr height="55px">
				<td align="right" width="5px">&nbsp;</td>
				<td align="left" nowrap="nowrap" width="5%">
					<span class="content_font"><s:text name="channel" /> 1:</span>
				</td>
				<td align="left" >  
					<div>
						<input type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[0].name"/>"/>
					</div>
				</td> 
			</tr>
			<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
			<tr height="55px">
				<td align="right" width="5px">&nbsp;</td>
				<td align="left" nowrap="nowrap" width="5%">
					<span class="content_font"><s:text name="channel" /> 2:</span>
				</td>
				<td align="left" >  
					<div>
						<input type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[1].name"/>"/>
					</div>
				</td> 
			</tr>
		</table>
		<table width="100%">
		<tr><td colspan="5" align="center"><h4><s:text name="configurationofchannel1" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value='<s:text name="read" />' onclick="readConfig('1')" style="height:30px;width:80px;display:inline-block;"/></h4></td></tr>
			<tr>
				<td width="15%" align="center"><s:text name="highorlowvoltage" />:</td>
				<td colspan="3" align="center" width="35%">
					<input name="voltagechoose1" type="radio" value="1" style="zoom: 150%;" /><s:text name="highvoltage" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="voltagechoose1" checked type="radio" value="0" style="zoom: 150%;" /><s:text name="lowvoltage" />
				</td>
				<td width="15%" align="center"></td>
			</tr>
			<tr><td colspan="5" height="3px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td width="15%" align="center"><s:text name="frequency" />:</td>
				<td colspan="3" width="35%" align="left" id="frequency1"></td>
				<td width="15%" align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="5"><output></output> 
				<input id="Slider1" name="frequency1" type="range" min="1" max="4" value="1" data-rangeslider /></td>
			</tr>
			<tr><td colspan="5" height="3px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td width="15%" align="center"><s:text name="sensitivity" />:</td>
				<td width="35%" align="left" colspan="3" id="sensitivity1"></td>
				<td width="15%" align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="5"><output></output>
				<input id="Slider2" name="sensitivity1" type="range" min="1" max="8" value="1" data-rangeslider /></td>
			</tr>
			<tr><td colspan="5" height="3px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td width="15%" align="center"><s:text name="baojingmenxian" />:</td>
				<td width="35%" align="left" colspan="3" id="baojingmenxian1"></td>
				<td width="15%" align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="5"><output></output>
				<input id="Slider3" name="baojingmenxian1" type="range" min="1" max="8" value="1" data-rangeslider /></td>
			</tr>
			<tr><td colspan="5" height="3px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td width="15%" align="center"><s:text name="delaytimes" />:</td>
				<td width="35%" align="left" colspan="3" id="delaytimes1"></td>
				<td width="15%" align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="5"><output></output><s:text name="seconds" /> 
				<input id="Slider4" name="delaytimes1" type="range" min="0" max="5" value="0" data-rangeslider /></td>
			</tr>
			<tr><td colspan="5" height="3px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td width="15%" align="center"><s:text name="alarmtimes" />:</td>
				<td width="35%" align="left" colspan="3" id="alarmtimes1"></td>
				<td width="15%" align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="5"><output></output><s:text name="seconds" /> 
				<input id="Slider5" name="alarmtimes1" type="range" min="5" max="999" value="1" data-rangeslider /></td>
			</tr>
			<tr><td colspan="5" height="3px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td width="15%" align="center"><s:text name="configofhighvoltage" /> :</td>
				<td width="35%" align="left" colspan="3" id="highvoltage1"></td>
				<td width="15%" align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="5"><output></output>V 
				<input id="Slider6" name="highvoltage1" type="range" min="3800" max="6400" value="3800" data-rangeslider /></td>
			</tr>
			<tr><td colspan="5" height="3px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td width="15%" align="center"><s:text name="configoflowvoltage" /> :</td>
				<td width="35%" align="left" colspan="3" id="lowvoltage1"></td>
				<td width="15%" align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="5"><output></output>V 
				<input id="Slider7" name="lowvoltage1" type="range" min="800" max="1800" value="1" data-rangeslider /></td>
			</tr>
			<tr><td colspan="5" height="5px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td colspan="5" align="center">
					<s:text name="calibratetip" />
				</td>
			</tr>
			<tr>
				<td colspan="5" align="center">
					<input type="button" value='<s:text name="calibrate" />' onclick="calibrate('1')" style="height:30px;width:80px;display:inline-block;"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value='<s:text name="set" />' onclick="set('1')" style="height:30px;width:80px;display:inline-block;"/>
				</td>
			</tr>
			<!-- ############################################################################################## -->
			<tr><td colspan="5" align="center"><h4><s:text name="configurationofchannel2" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value='<s:text name="read" />' onclick="readConfig('2')" style="height:30px;width:80px;display:inline-block;"/></h4></td></tr>
			<tr>
				<td width="15%" align="center"><s:text name="highorlowvoltage" />:</td>
				<td colspan="3" align="center" width="35%">
					<input name="voltagechoose2" type="radio" value="1" style="zoom: 150%;" /><s:text name="highvoltage" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="voltagechoose2" checked type="radio" value="0" style="zoom: 150%;" /><s:text name="lowvoltage" />
				</td>
				<td width="15%" align="center"></td>
			</tr>
			<tr><td colspan="5" height="3px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td width="15%" align="center"><s:text name="frequency" />:</td>
				<td colspan="3" width="35%" align="left" id="frequency2"></td>
				<td width="15%" align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="5"><output></output> 
				<input id="Slider8" name="frequency2" type="range" min="1" max="4" value="1" data-rangeslider /></td>
			</tr>
			<tr><td colspan="5" height="3px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td width="15%" align="center"><s:text name="sensitivity" />:</td>
				<td width="35%" align="left" colspan="3" id="sensitivity2"></td>
				<td width="15%" align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="5"><output></output>
				<input id="Slider9" name="sensitivity2" type="range" min="1" max="8" value="1" data-rangeslider /></td>
			</tr>
			<tr><td colspan="5" height="3px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td width="15%" align="center"><s:text name="baojingmenxian" />:</td>
				<td width="35%" align="left" colspan="3" id="baojingmenxian2"></td>
				<td width="15%" align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="5"><output></output>
				<input id="Slider10" name="baojingmenxian2" type="range" min="1" max="8" value="1" data-rangeslider /></td>
			</tr>
			<tr><td colspan="5" height="3px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td width="15%" align="center"><s:text name="delaytimes" />:</td>
				<td width="35%" align="left" colspan="3" id="delaytimes2"></td>
				<td width="15%" align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="5"><output></output><s:text name="seconds" /> 
				<input id="Slider11" name="delaytimes2" type="range" min="0" max="5" value="0" data-rangeslider /></td>
			</tr>
			<tr><td colspan="5" height="3px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td width="15%" align="center"><s:text name="alarmtimes" />:</td>
				<td width="35%" align="left" colspan="3" id="alarmtimes2"></td>
				<td width="15%" align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="5"><output></output><s:text name="seconds" /> 
				<input id="Slider12" name="alarmtimes2" type="range" min="5" max="999" value="1" data-rangeslider /></td>
			</tr>
			<tr><td colspan="5" height="3px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td width="15%" align="center"><s:text name="configofhighvoltage" />:</td>
				<td width="35%" align="left" colspan="3" id="highvoltage2"></td>
				<td width="15%" align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="5"><output></output>V 
				<input id="Slider13" name="highvoltage2" type="range" min="3800" max="6400" value="3800" data-rangeslider /></td>
			</tr>
			<tr><td colspan="5" height="3px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td width="15%" align="center"><s:text name="configoflowvoltage" />:</td>
				<td width="35%" align="left" colspan="3" id="lowvoltage2"></td>
				<td width="15%" align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="5"><output></output>V 
				<input id="Slider14" name="lowvoltage2" type="range" min="800" max="1800" value="1" data-rangeslider /></td>
			</tr>
			<tr><td colspan="5" height="5px"></td></tr>
			<!-- ********************************************************************************************** -->
			<tr>
				<td colspan="5" align="center">
					<s:text name="calibratetip" />
				</td>
			</tr>
			<tr>
				<td colspan="5" align="center">
					<input type="button" value='<s:text name="calibrate" />' onclick="calibrate('2')" style="height:30px;width:80px;display:inline-block;"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value='<s:text name="set" />' onclick="set('2')" style="height:30px;width:80px;display:inline-block;"/>
				</td>
			</tr>
		</table>
	</form>
<br><br><br><br><br><br>
</body>
</html>
