<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<title><s:text name="adddoorlockuser" /></title>
<jsp:include page="/jsp/language.jsp"/>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/iremote/js/DateTimePicker.js"></script>
<script type="text/javascript" src="/iremote/js/jquery.toast.js"></script>
<script type="text/javascript" src="/iremote/js/iremote.js"></script>
<script type="text/javascript" src="/iremote/js/dateformat.js"></script>

<link type="text/css" rel="stylesheet" href="/iremote/css/iremotewebstyle.css" charset="utf-8"/>
<link rel="stylesheet" type="text/css" href="/iremote/css/jquery.toast.css" />
<link rel="stylesheet" type="text/css" href="/iremote/css/DateTimePicker.css" />
<script>

var zwavedeviceid = <s:property value='zwavedeviceid'/>;
var hassubmit = false ;
var platform = <s:property value='platform'/>;
function onSubmit()
{	
	var usertype = $("#usertype").val();
	if ( $.trim($("#username").val()) == '' )
	{
		$("#username").focus();
		return ;
	}
	
	if ( usertype == 21)
	{
		if ( $("#password").val() == '' )
		{
			$("#password").focus();
			return ;
		}
		
		if ($("#password").val() != $("#password").val().replace(/[^0-9]/g, '') || $("#password").val().length < 4 || $("#password").val().length > 10 )
		{
			toast('<s:text name="passwordhastobesixmumbers" />');
			return ;
		}
	}		
	if(!compareDate($("#validfrom"),$("#validthrough"))){
		toast('<s:text name="validfromshouldbeforevalidthrougth" />'); 
		 return ;
	}
	if(showAlarmphone == true)
	{
		if(AlarmIsNull())
		{
			toast('<s:text name="enteratleaseonedefensenumber" />');
			return ;
		}
	}
	else 
		clearAlarmNumber();

	if ( hassubmit == true )
		return ; 

	$("#localtime").val(new Date().format("yyyy-MM-dd hh:mm:ss"));
	
	hassubmit = true;
	document.getElementById("addlockuserform").submit()
}

function AlarmIsNull(){
	var isNull = true;
	for(var i = 1; i < 6; i++){
		var phone = $("#alarmphone_" + i).val();
		if(phone != "")
			isNull = false;
	}
	return isNull;
}

function clearAlarmNumber()
{
	for(var i = 1; i < 6; i++)
		$("#alarmphone_" + i).val("");
}

var showpassword = false ;
function ShowHidePW(ele) 
{  
    if (showpassword == false ) 
    { 
    	showpassword = true;
    	$('#password').attr('type', 'text');
    } 
    else 
    {
    	showpassword = false;
    	$('#password').attr('type', 'password');
    }  
	$("#showhidepw").toggleClass("check_box_uncheck");
	$("#showhidepw").toggleClass("check_box_checked");
    
}  
/**
 * 添加国家区号
 */
function addCountry(){
	for(var i = 1 ; i < 6 ; i++){
		$("#alarmphone_contrycode_" + i).append(countryNumber());
	}
}

/* function countryNumber(){
	var str = "";
	for(var i = 0; i < countryCode.length; i++){
		if(countryCode[i].countryCode == '<s:property value="#session.IREMOTE_USER.countrycode"/>')
			str += "<option value='" + countryCode[i].countryCode +"' selected='selected' >" + countryCode[i].countryName + "</option>";
		else
			str += "<option value='" + countryCode[i].countryCode +"'>" + countryCode[i].countryName + "</option>";
	}
	return str;
} */
var ccode = <s:property value="#session.IREMOTE_USER.countrycode"/>
function countryNumber(){
	var str = "";
	if(ccode==0){//邮箱用户
		for(var i = 0; i < countryCode.length; i++){
			if(countryCode[i].countryCode == '1' && platform=='9' && i<100)
				str += "<option value='" + countryCode[i].countryCode +"' selected='selected' >" + countryCode[i].countryName + "</option>";
			else if(countryCode[i].countryCode == '86' && platform!='9')
				str += "<option value='" + countryCode[i].countryCode +"' selected='selected' >" + countryCode[i].countryName + "</option>";
			else
				str += "<option value='" + countryCode[i].countryCode +"'>" + countryCode[i].countryName + "</option>";
		}
	}else{//手机用户
		for(var i = 0; i < countryCode.length; i++){
			if(countryCode[i].countryCode == ccode)
				str += "<option value='" + countryCode[i].countryCode +"' selected='selected' >" + countryCode[i].countryName + "</option>";
			else
				str += "<option value='" + countryCode[i].countryCode +"'>" + countryCode[i].countryName + "</option>";
		}
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
var showAlarmphone = true ;
function showHideAlarmphone()
{
	if(showAlarmphone == false)
	{
		showAlarmphone = true;
		$("#tbl_alarmphone").show();
		$("#alarmuser").val("1");
	}
	else
	{
		showAlarmphone = false;
		$("#tbl_alarmphone").hide();
		$("#alarmuser").val("0");
	}
	$("#showhidealarm").toggleClass("check_box_uncheck");
	$("#showhidealarm").toggleClass("check_box_checked");

}

$(document).ready(function()
{
	$('.numbersOnly').keyup(function () {
	    if (this.value != this.value.replace(/[^0-9]/g, '')) {
	       this.value = this.value.replace(/[^0-9]/g, '');
	    }
	});
	addCountry();
	$('#showhidepw').first().click();
	$('#showhidealarm').first().click();
	$("#startDiv").DateTimePicker();
	$("#endDiv").DateTimePicker();
});

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

.input_date
{
	width:100%;
	height:40px;
	font: normal 18px arial, sans-serif; 
	border-radius:5px;
	border-collapse:collapse;
	border-spacing:0;
	border:2px solid #888;
	padding-left:40px ;
}    

.input_text_alarm
{
	height:40px;
	font: normal 18px arial, sans-serif; 
	font-family:"Source Han Sans";
	margin-top:0px;
	margin-right:10px;
	
	border-radius:5px;
	border-collapse:collapse;
	border-spacing:0;
	border:2px solid #888;
	
	background:#ffffff;
	padding-left:10px ;
}

.input_usertype
{
	height:50px;
	width:32px;
	margin-top:0px;
	 
	border-collapse:collapse;
	border-spacing:0;
	
	border-left:0px solid #888;
	border-top:0px solid #888;
	border-right:0px solid #888;
	border-bottom:0px solid #888;
	padding-left:10px ;
}

.selectCounrty {
	width:80px;
	padding-left:25px;
	
	height:45px;
	font: normal 18px arial, sans-serif; 
	font-family:"Source Han Sans";
	margin-top:0px;
	
	border-radius:5px;
	border-collapse:collapse;
	border-spacing:0;
	border:2px solid #888;
	
	background:#ffffff;
	padding-left:10px ;
}

</style>
</head>
<body class="listpage">
<form id="addlockuserform" action="/iremote/device/lock/addlockuser" method="POST">
<input type="hidden" name="zwavedeviceid" value="<s:property value='zwavedeviceid'/>"/>
<input type="hidden" id="usertype" name="usertype" value="<s:property value='usertype'/>"/>
<input type="hidden" id="alarmuser" name="alarmuser" value="0"/>
<input type="hidden" id="localtime" name="localtime" value="0"/>
<table class="title_table">
<tr >
	<td align="left" width="40px" ><a href="/iremote/device/lock/addlockusermenu?zwavedeviceid=<s:property value='zwavedeviceid'/>"><img src="/iremote/images/nav/nav_btn_back.png" class="nav_img"/></a></td>
	<td align="center"><span class="title_c"><s:text name="add" />
	<s:if test="usertype == 21"><s:text name="passworduser" /></s:if>
	<s:if test="usertype == 22"><s:text name="fingerprintuser" /></s:if>
	<s:if test="usertype == 32"><s:text name="carduser" /></s:if>
	</span>
	</td>
	<td align="right"  width="40px"><a href="javascript:onSubmit(this);"><img src="/iremote/images/nav/nav_btn_preservation.png"  class="nav_img"/></a></td>
</tr>
</table>

	<div class="backgrouddiv">
		<input type="text" name="username" id="username" class="input_text"  placeholder="<s:text name='enterthename' />" maxlength="16" style="background:#ffffff url(/iremote/images/icon/icon_user.png) 5px 5px no-repeat;background-size:32px 32px;"/>
	</div>
<s:if test="usertype == 21"> 
	<div class="backgrouddiv">
		<input type="password" name="password" autocomplete="off" id="password" placeholder="<s:text name='enterpasswordnumberonly' />" class="input_text numbersOnly" maxlength="6" style="background:#ffffff url(/iremote/images/icon/icon_password.png) 5px 5px no-repeat;background-size:32px 32px;" />
	</div>
</s:if>
	<div class="backgrouddiv" style="width:100%;height:50px;overflow:hidden">
	<table width="100%">
		<tr>
			<td width="50%">
				<div  class="list_conent">
					<input type="text" onclick="showHideAlarmphone();" id="showhidealarm" class="input_usertype check_box_checked" readonly="readonly" /><s:text name='alarmuser' />
				</div>
			</td>
	<s:if test="usertype == 21"> 
			<td width="50%">
				<div  class="list_conent">
					<input type="text" onclick="ShowHidePW();" id="showhidepw" class="input_usertype check_box_uncheck"  value="" readonly="readonly"/><s:text name='displaypassword' />
				</div>
			</td>
	</s:if>
		</tr>
	</table>
	<div style="clear:both"></div> 
	</div>

<table width="100%" id ="tbl_alarmphone" style="background:#ffffff;display:none;margin-top:5px;">	
	<s:bean name="org.apache.struts2.util.Counter" id="counter">
   	<s:param name="first" value="1" />
   	<s:param name="last" value="5" />
      	<s:iterator>
      	<tr height="55px" id="tr_alarmphone_<s:property/>" style="white-space:nowrap;">
			<td align="right" width="5px">&nbsp;</td>
			<td align="right" width="80px">
				<select id="alarmphone_contrycode_<s:property/>" name="countrycode" class="selectCounrty"></select>
			</td>
			<td >
				<div style="margin:0px 28px 0px 0px;">
					<input type="text" name="alarmphone"  id="alarmphone_<s:property/>"  style="width:100%" maxLength="13"  class="input_text_alarm numbersOnly" placeholder="<s:text name='enteralarmphonenunmber' />" />
				</div>
			</td>
		</tr>
   		</s:iterator>  
	</s:bean>
</table>    
<div></div>  
<s:if test="supportValidTime == true"> 
<div style="background:#ffffff;">
	<div style="margin:5px 60px 0px 10px;">
		<s:text name="starttime"/>
		<input placeholder="<s:text name='selectstartnumber' />" class="input_date" maxlength="16" readonly="readonly" id="validfrom" name="validfrom" type="text"  data-field="datetime">
		<div id="startDiv"></div>
		<s:text name="endtime"/>
		<input placeholder="<s:text name='selectendnumber' />" class="input_date" maxlength="16" readonly="readonly" id="validthrough" name="validthrough" type="text"  data-field="datetime" >
		<div id="endDiv"></div>
	</div>    
</div>
</s:if>
<br/>
<br/>
<br/>
<br/>
</form>
</body>
</html>