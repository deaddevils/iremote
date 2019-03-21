<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<title><s:text name="grantdeviceprivielge"/></title>
<jsp:include page="/jsp/language.jsp"/>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/iremote/js/jquery.toast.js"></script>
<script type="text/javascript" src="/iremote/js/iremote.js"></script>
<link type="text/css" rel="stylesheet" href="/iremote/css/iremotewebstyle.css" charset="utf-8"/>
<script type="text/javascript" src="/iremote/js/DateTimePicker.js"></script>

<link rel="stylesheet" type="text/css" href="/iremote/css/jquery.toast.css" />
<link rel="stylesheet" type="text/css" href="/iremote/css/DateTimePicker.css" />
<script>

var zwavedeviceid = <s:property value='zwavedeviceid'/>;
var hassubmit = false ;
var platform = <s:property value='platform'/>;
function onSubmit()
{	
	if ( $.trim($("#phonenumber").val()) == '' )
	{
		$("#phonenumber").focus();
		toast('<s:text name="errmsg_inputphonenumber"/>');
		return ;
	}
	else 
	{
// 		var re = /^1\d{10}$/;
// 		if ( !re.test($("#phonenumber").val()))
// 		{
// 			$("#phonenumber").focus();
// 			toast('<s:text name="errmsg_phonenumberformaterror"/>');
// 			return ;
// 		}
	}
	
	if(!compareDate($("#validfrom"),$("#validthrough")))
	{
		toast('<s:text name="validfromshouldbeforevalidthrougth"/>');
		return ;
	}
	if ( hassubmit == true )
		return ; 
	hassubmit = true;
	
	document.getElementById("deviceprivilegegrantform").submit()
}


/**
 * 添加国家区号
 */
function addCountry(){
	for(var i = 1 ; i < 6 ; i++){
		$("#alarmphone_" + i).append(countryNumber());
	}
}

function countryNumber(){
	var str = "";
	for(var i = 0; i < countryCode.length; i++){
		if(countryCode[i].countryCode == '86')
			str += "<option value='" + countryCode[i].countryCode +"' selected='selected' >" + countryCode[i].countryName + "</option>";
		else
			str += "<option value='" + countryCode[i].countryCode +"'>" + countryCode[i].countryName + "</option>";
	}
	return str;
}

function compareDate(date1 , date2){
	var beginDate = date1.val();
	var endDate = date2.val();
	if(beginDate == null || beginDate.length < 1
			&& endDate == null || endDate.length < 1 ){
		return true;
	}
	var d1 = new Date(beginDate.replace(/\-/g,"\/"))
	var d2 = new Date(endDate.replace(/\-/g,"\/"))
	if(beginDate!="" && endDate!="" && d1 >=d2) 
	{ 
	 return false; 
	}
	return true;
}

$(document).ready(function()
{
	$('.numbersOnly').keyup(function () {
	    if (this.value != this.value.replace(/[^0-9]/g, '')) {
	       this.value = this.value.replace(/[^0-9]/g, '');
	    }
	});
	addCountry();
	$("#startDiv").DateTimePicker();
	$("#endDiv").DateTimePicker();
	
});

function addCountry(){
	var str = "";
	for(var i = 0; i < countryCode.length; i++){
		if(countryCode[i].countryCode == '1' && platform=='9' && i<100)
			str += "<option value='" + countryCode[i].countryCode +"' selected='selected' >" + countryCode[i].countryName + "</option>";
		else if(countryCode[i].countryCode == '86' && platform!='9')
			str += "<option value='" + countryCode[i].countryCode +"' selected='selected' >" + countryCode[i].countryName + "</option>";
		else
			str += "<option value='" + countryCode[i].countryCode +"'>" + countryCode[i].countryName + "</option>";
	}
	$("#countrycode").append(str);
}

function validtypeclick()
{
	if ( $("#validtype").val() == 0 )
	{
		$("#validtype").val(1);
	}
	else 
	{
		$("#validtype").val(0);
	}
	$("#cbvalidtype").toggleClass("check_box_uncheck");
	$("#cbvalidtype").toggleClass("check_box_checked");
}

</script>
<style>

.input_text
{
	width:80%;
	height:40px;
	font: normal 18px arial, sans-serif; 
	font-family:"Source Han Sans";
	color:#333333;
	margin-top:5px;
	
	border-collapse:collapse;
	border-spacing:0;
	border-left:0px;
	border-top:0px;
	
	border-right:0px;
	border-bottom:0px;
	padding-left:40px ;
}

.input_usertype
{
	height:50px;
	width:50px;
	border:0px ;
}
</style>
</head>
<body class="listpage">
<form id="deviceprivilegegrantform" action="/iremote/device/deviceprivilegegrant" method="POST">
<input type="hidden" name="zwavedeviceid" value="<s:property value='zwavedeviceid'/>"/>
<input type="hidden" name="validtype" id="validtype" value="0"/>
<table class="title_table">
<tr >
	<td align="left" width="40px" ><a href="/iremote/device/listdeviceauthorize?zwavedeviceid=<s:property value='zwavedeviceid'/>"><img src="/iremote/images/nav/nav_btn_back.png" class="nav_img"/></a></td>
	<td align="center"><span class="title_c"><s:text name="grantdeviceprivielge"/></span>
	</td>
	<td align="right"width="40px" ><a href="javascript:onSubmit(this);"><img src="/iremote/images/nav/nav_btn_preservation.png" class="nav_img"/></a></td>
</tr>
</table>
	<div class="backgrouddiv">
		<select id="countrycode" name="countrycode" class="input_text" style="background:url(/iremote/images/icon/icon_map.png) 0px 5px no-repeat;background-size:32px 32px;"></select>
	</div>
	<div class="backgrouddiv" style="margin-top:2px;">
		<input type="text" name="phonenumber" id="phonenumber" autocomplete="off"  maxLength="13"  class="input_text numbersOnly" placeholder="<s:text name="inputphonenumber"/>" style="background:url(/iremote/images/icon/icon_number.png) 0px 5px no-repeat;background-size:32px 32px"/>
	</div>
	<div  class="backgrouddiv" style="margin-top:2px;">
		<input type="text" name="username" id="username" autocomplete="off"  class="input_text"  placeholder="<s:text name='enterthename' />" maxlength="16" style="background:#ffffff url(/iremote/images/icon/icon_user.png) 0px 5px no-repeat;background-size:32px 32px;"/>
	</div>
	
<div  class="backgrouddiv">
		<input placeholder="<s:text name='inputvalidfrom' />" class="input_text" maxlength="16" readonly="readonly" id="validfrom" name="validfrom" type="text"  data-field="datetime" style="background:url(/iremote/images/icon/icon_time.png) 0px 5px no-repeat;background-size:32px 32px">
		<div id="startDiv"></div>
</div>
<div  class="backgrouddiv" style="margin-top:2px;">
		<input placeholder="<s:text name='inputvalidthrougth' />" class="input_text" maxlength="16" readonly="readonly" id="validthrough" name="validthrough" type="text"  data-field="datetime" style="background:url(/iremote/images/icon/icon_time.png) 0px 5px no-repeat;background-size:32px 32px">
		<div id="endDiv"></div>
</div>
<div  class="backgrouddiv" style="margin-top:2px;height:40px">
		<div class="content_font" style="margin-top:8px;margin-left:5px;float:left;"><s:text name="onceonly"/></div>
		<input type="text" onclick="validtypeclick();" name="cbvalidtype" id="cbvalidtype" class="input_usertype check_box_uncheck"  readonly="readonly"  style="float:right"/>
</div>
</form>
</body>
</html>