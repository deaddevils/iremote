<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/language.jsp"/>
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
.input_option
{
	width:60px;
	height:0px;
	font: normal 16px arial, sans-serif; 
	font-family:"Microsoft YaHei";
	margin-top:0px;
	border-radius:5px;
	border-collapse:collapse;
	border-spacing:0;
	border-left:0px solid #888;
	border-top:0px solid #888;
	border-right:0px solid #888;
	border-bottom:0px solid #888;
	
	padding-left:40px ;
}

.input_text
{
	width:85%;
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

function setSystem(stm) {
    console.log('android parameter：'+ stm);
    system = stm;
}

var regRule = /\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g;

var centigrade = '<s:property value="isExit"/>' ;
function showCentigrade(){  
    if (centigrade == 'true' ){ 
    	centigrade = 'false';
    	$("#fahrenheit").val("false");
    }else{
    	centigrade = 'true';
    	$("#fahrenheit").val("true");
    }
	$("#centigrade").toggleClass("check_box_uncheck");
	$("#centigrade").toggleClass("check_box_checked");
}

function initCentigradeBox()
{
    if (centigrade == 'true' )
    { 
    	$("#fahrenheit").val("true");
    	$("#centigrade").addClass("check_box_checked");    	
    }
    else
    {
    	$("#fahrenheit").val("false");
    	$("#centigrade").addClass("check_box_uncheck");    	
    }
}

function onback()
{
	JSInterface.back();
}

$(document).ready(function(){
	initCentigradeBox();
	stopentersubmit();
	initsiren();
});

function initsiren(){
	var sound = <s:property value="sound"/>;
	var time = <s:property value="time"/>;
	var music = <s:property value="music"/>;
	if(sound!=0){
		$("#sound").val(sound);
	}
	if(music!=0){
		$("#music").val(music);
	}
	if(time!=0){
		$("#time").val(time);
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

function onSubmit(){
	if(!validateZwaveName($("#name"))){
		return;
	};
	var array=$("input[name='switchsNames']");

	var data = $('#formSumbit').serialize();
	var message = '<s:text name="devicenamecrash" />';
	validate(data,true,message);
}

//效验设备名
function validateZwaveName(obj){
	var name = $(obj).val();
	var zwavedeviceid = $("#zwavedeviceid").val();
	if(name == null || name == ""){
		toast('<s:text name="devicenameisrequired" />');
		return false;
	}
	if(name.match(regRule)) {
	    name = name.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
	    $(obj).val(name);
	    toast('<s:text name="wildcharnotsupport" />');
	    return false;
	} 

	var data = {"name":name,"zwavedeviceid":zwavedeviceid};
	var message = '<s:text name="devicenamecrash" />';
	return validate(data,false,message);
}


function Submit(){
	$.ajax({
        type: "post",
        url:"/iremote/device/setdeivityname",
        data:$('#formSumbit').serialize(),
        success : function(data) {
        	//if(system == "android"){
            if(JSInterface.sumbit && typeof(JSInterface.sumbit) == "function"){
        		JSInterface.sumbit();
        	}else{
        		JSInterface.back();
        	}
        },
        error : function(data) {
        	toast('<s:text name="errorraise" />');
        }
	})
}

function validate(data,flag,message){
	var isExist = false;
	$.ajax({
        type: "post",
        url:"/iremote/device/validateName",
        data:data,
        async: false,
        success : function(data) {
        	if(data.resultCode == 0){
        		if(flag)
					Submit();
        		isExist = true;
        	}else{
        		toast(message);
        	}
        },
        error : function(data) {
        	toast('<s:text name="errorraise" />');
        }
	})
	return isExist;
}
function doalarm(obj){
	var zwavedeviceid = $("#zwavedeviceid").val(); 
	var data = "";
	if(obj=="1"){
		data = {"deviceid":deviceid,"zwavedeviceid":zwavedeviceid,"doalarm":1}; 
	}
	if(obj=="2"){
		data = {"deviceid":deviceid,"zwavedeviceid":zwavedeviceid,"doalarm":2}; 
	}
	$.ajax({
        type: "post",
        url:"/iremote/device/setsirenconfig",
        data:data,
        async: false,
        success : function(data) {
        	if(data.resultCode==0){
        		toast('<s:text name="operatesuccessfully" />');
        	}else if(data.resultCode==30313){
        		toast('<s:text name="wakeupdevice" />');
        	}else if(data.resultCode==10020){
        		toast('<s:text name="wakeupgateway" />');
        	}else if(data.resultCode==10006){
        		toast('<s:text name="timeout" />');
        	}else if(data.resultCode==30312){
        		toast('<s:text name="gatewayoffline" />');     		
			}else if(data.resultCode==10023){
				toast('<s:text name="targetnotexist" />');
			}else if(data.resultCode==30021){
				toast('<s:text name="unsupportrequest" />');
			}else{
        		toast(getErrorMsg(data.resultCode));
        	}
        },
        error : function(data) {
        	toast('<s:text name="errorraise" />');
        }
	})
}

function set(b){
	var zwavedeviceid = $("#zwavedeviceid").val(); 
	var sound = $("#sound").val();
	var time = $('#time').val();
	if(time==4){
		time=0;
	}
	var music = $('#music').val();
	var data = "";
	if(b=="1"){
		data = {"deviceid":deviceid,"zwavedeviceid":zwavedeviceid,"order":"1","config":sound}; 
	}
	if(b=="2"){
		data = {"deviceid":deviceid,"zwavedeviceid":zwavedeviceid,"order":"2","config":time}; 
	}
	if(b=="3"){
		data = {"deviceid":deviceid,"zwavedeviceid":zwavedeviceid,"order":"3","config":music}; 
	}
	$.ajax({
        type: "post",
        url:"/iremote/device/setsirenconfig",
        data:data,
        async: false,
        success : function(data) {
        	if(data.resultCode==0){
        		toast('<s:text name="operatesuccessfully" />');
        	}else if(data.resultCode==30313){
        		toast('<s:text name="wakeupdevice" />');
        	}else if(data.resultCode==10020){
        		toast('<s:text name="wakeupgateway" />');
        	}else if(data.resultCode==10006){
        		toast('<s:text name="timeout" />');
        	}else if(data.resultCode==30312){
        		toast('<s:text name="gatewayoffline" />');     		
			}else if(data.resultCode==10023){
				toast('<s:text name="targetnotexist" />');
			}else if(data.resultCode==30021){
				toast('<s:text name="unsupportrequest" />');
			}else{
        		toast(getErrorMsg(data.resultCode));
        	}
        },
        error : function(data) {
        	toast('<s:text name="errorraise" />');
        }
	})
}
</script>

</head>
<body class="listpage">
	<table class="title_table">
		<tr>
			<td align="left" width="40px"><a href="javascript:onback(0);"><img src="/iremote/images/nav/nav_btn_back.png"  class="nav_img"/></a></td>
			<td align="center"><span class="title_c"><s:text name="title" /></span></td>
			<td align="right" width="40px" ><a href="javascript:onSubmit(this);"><img src="/iremote/images/nav/nav_btn_preservation.png"  class="nav_img"/></a></td>
		</tr>
	</table>
	
<form action="/iremote/device/setdeivityname" id="formSumbit" method="post">
	<input type="hidden" id="zwavedeviceid" name="zwavedeviceid" value="<s:property value='zwavedeviceid'/>"/>
 	
	<table width="100%" style="background:#ffffff;margin-top:5px;">	
      	<tr height="45px">
			<td align="right" width="5px">&nbsp;</td>
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font"><s:text name="name" />:</span>
			</td>
			<td align="left" >  
				<div>
					<input type="text" class="input_text" id="name" name="name" maxlength="32" value="<s:property value="zwaveDevice.name"/>"/>
				</div>
			</td> 
		</tr>
		<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=3 width="100%"></td></tr>
		<tr height="45px" >
			
			<td align="right" width="5px">&nbsp;</td>
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font"><s:text name="volume" />:</span>
			</td>
			<td align="left" >
				<select name="sound" style="width:120px" id="sound">
					<option value ="1"><s:text name="low"/></option>
					<option value ="2" selected="selected"><s:text name="medium"/></option>
					<option value ="3"><s:text name="high"/></option>
				</select>&nbsp;&nbsp;
				<input type="button" style="width:45px" value='<s:text name="set" />' onclick="set('1')"/>
			</td>
		</tr>
		<tr height="45px" >
			<td align="right" width="5px">&nbsp;</td>
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font"><s:text name="ringtime" />:</span>
			</td>
			<td align="left" >
				<select name="time" style="width:120px" id="time">
					<option value ="4"><s:text name="quiet"/></option>
					<option value ="1"><s:text name="30s"/></option>
					<option value ="2" selected="selected">1<s:text name="minute"/></option>
					<option value ="3">5<s:text name="minute"/></option>
					<option value ="255"><s:text name="continuous"/></option>
				</select>&nbsp;&nbsp;
				<input type="button" style="width:45px" value='<s:text name="set" />' onclick="set('2')"/>
			</td>
		</tr>
		<tr height="45px" >
			<td align="right" width="5px">&nbsp;</td>
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font"><s:text name="ring" />:</span>
			</td>
			<td align="left" >
				<select name="music" style="width:120px" id="music">
					<option value ="1"><s:text name="ring"/>1</option>
					<option value ="2"><s:text name="ring"/>2</option>
					<option value ="3"><s:text name="ring"/>3</option>
					<option value ="4"><s:text name="ring"/>4</option>
					<option value ="5"><s:text name="ring"/>5</option>
					<option value ="6"><s:text name="ring"/>6</option>
					<option value ="7"><s:text name="ring"/>7</option>
					<option value ="8"><s:text name="ring"/>8</option>
					<option value ="9" selected="selected"><s:text name="ring"/>9</option>
					<option value ="10"><s:text name="ring"/>10</option>
				</select>&nbsp;&nbsp;
				<input type="button" style="width:45px" value='<s:text name="set" />' onclick="set('3')"/>
			</td>
		</tr>
		<tr height="45px" >
			<td align="right" width="5px">&nbsp;</td>
			<td align="left" nowrap="nowrap" width="5%">
			</td>
			<td align="left" >
				<input type="button"  value='<s:text name="auditionmusic" />' onclick="doalarm('1')"/>
				<input type="button"  value='<s:text name="removemusic" />' onclick="doalarm('2')"/>
			</td>
		</tr>
	</table>
</form>
	
</body>
</html>
