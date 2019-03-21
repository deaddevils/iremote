<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*,com.iremote.domain.*" pageEncoding="UTF-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<title><s:text name="editdoorlockuser" /></title>
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
var usertype = <s:property value='doorlocklser.usertype'/>;
var hassubmit = false ;
var hasArmFunction = '<s:property value="hasArmFunction"/>' ;
var platform = <s:property value='platform'/>;
function onSubmit()
{	
	if(!validatePassword()){
		return ;
	} 
	if ( $.trim($("#username").val()) == '' )
	{
		$("#username").focus();
		return ;
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
	{
		for(var i = 1; i < 6; i++){
			$("#alarmphone_" + i).val("");
		}
	}
	
	if ( hassubmit == true )
		return ; 
	
	$("#localtime").val(new Date().format("yyyy-MM-dd hh:mm:ss"));


	hassubmit = true;
	toast('<s:text name="editingdoorlockuser" />');
	document.getElementById("editlockuserform").submit();
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

function onback()
{
	JSInterface.back();
}

var showAlarmphone = <s:property value='isalarm'/> ;
var isscene = <s:property value='isscene'/> ;
var ispartition = <s:property value='ispartition'/> ;
var ispartition2 = <s:property value='ispartition2'/> ;
function showHideAlarmphone(){
	if(showAlarmphone == false){
		showAlarmphone = true;
		$("#tbl_alarmphone").show();
	}else{
		showAlarmphone = false;
		$("#tbl_alarmphone").hide();
	}
	$("#showhidealarm").toggleClass("check_box_uncheck");
	$("#showhidealarm").toggleClass("check_box_checked");
}
/* 执行情景 */
function showHideScene(){
	if(isscene == false){
		isscene = true;
		$("#tbl_scene").show();
	}else{
		isscene = false;
		$("#tbl_scene").hide();
	}
	$("#showhidescene").toggleClass("check_box_uncheck");
	$("#showhidescene").toggleClass("check_box_checked");
}
/* 撤防防区 */
function showHidePartition(){
	if(ispartition == false){
		ispartition = true;
		$("#tbl_partition").show();
	}else{
		ispartition = false;
		$("#tbl_partition").hide();
	}
	$("#showhidepartition").toggleClass("check_box_uncheck");
	$("#showhidepartition").toggleClass("check_box_checked");
}
/* 设防防区 */
function showHidePartition2(){
	if(ispartition2 == false){
		ispartition2 = true;
		$("#tbl_armpartition").show();
	}else{
		ispartition2 = false;
		$("#tbl_armpartition").hide();
	}
	$("#showhidepartition2").toggleClass("check_box_uncheck");
	$("#showhidepartition2").toggleClass("check_box_checked");
}
function showchoosescene(){
	var sil = ${choosescenelist};
	var slen = $('input[id^="scene_"]').size();
	for(var i = 0;i<sil.length;i++){
		for(var j=0;j<slen;j++){
			var sid = $("#scene_"+j).val();
			if(sil[i]==sid){
				$("#scene_"+j).attr("checked",'true');
			}
		}
	}
}
function showchoosepartition(){
	var pil = ${choosepartitionlist};
	var plen = $('input[id^="partition_"]').size();
	
	for(var i = 0;i<pil.length;i++){
		for(var j=0;j<plen;j++){
			var pid = $("#partition_"+j).val();
			var pidd = pid.split("#")[1];
			if(pil[i]==pidd){
				$("#partition_"+j).attr("checked",'true');
			}
		}
	}
	
}
function showchoosepartition2(){
	var armtypelist = ${choosepartitiontype};
	var pil = ${choosepartitionlist2};
	var plen = $('input[id^="armpartition_"]').size();
	
	for(var i = 0;i<pil.length;i++){
		for(var j=0;j<plen;j++){
			var pid = $("#armpartition_"+j).val();
			var pidd = pid.split("#")[1];
			if(pil[i]==pidd){
				$("#armpartition_"+j).attr("checked",'true');
				$("#armtype_"+j).val(armtypelist[i]);
			}
		}
	}
	
}
function initAlarmphoneBox()
{
	if(showAlarmphone == false){
		$("#tbl_alarmphone").hide();
		$("#showhidealarm").addClass("check_box_uncheck");
	}else{
		$("#tbl_alarmphone").show();
		$("#showhidealarm").addClass("check_box_checked");
	}
	if(isscene == false){
		$("#tbl_scene").hide();
		$("#showhidescene").addClass("check_box_uncheck");
	}else{
		$("#tbl_scene").show();
		$("#showhidescene").addClass("check_box_checked");
	}
	if(ispartition == false){
		$("#tbl_partition").hide();
		$("#showhidepartition").addClass("check_box_uncheck");
	}else{
		$("#tbl_partition").show();
		$("#showhidepartition").addClass("check_box_checked");
	}
	
	if(ispartition2 == false){
		$("#tbl_armpartition").hide();
		$("#showhidepartition2").addClass("check_box_uncheck");
	}else{
		$("#tbl_armpartition").show();
		$("#showhidepartition2").addClass("check_box_checked");
	}
}
//效验密码，4或6位数字
function validatePassword(){
//TODO
	var passwordarray=$("input[name='password']");
	var partitionarray=$("input[name='partition']");
	for(var i=0;i<partitionarray.length;i++){
		if($("#partition_"+i).prop('checked')){
			var passwordval = $("#password_"+i).val();
			var reg = /^(((\d){4})|((\d){6}))$/;
			var r = passwordval.match(reg); 
			if(passwordval==""||passwordval==undefined||r==null){
				toast('<s:text name="disarmpasswordformat" />');
				return false;
			} 			
		}
	}
	return true;
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

var str = "";
/**
 * 添加国家区号
 */
function addCountry(){
	for(var i = 1 ; i < 6 ; i++){
		$("#alarmphone_contrycode_" + i).append(countryNumber());
	}
	<s:iterator value="alarmphoneArray" status = "phone" var = "item">
		if(0=='<s:property value="#item.countrycode"/>'){
			//不变更
		}else{
			if('<s:property value="#item.countrycode"/>'==1){
				obj = document.getElementById('alarmphone_contrycode_<s:property value="#phone.index + 1"/>');
				for(i=0;i<obj.length;i++){
				    if(obj[i].value==1&&i<100)
				        obj[i].selected = true;
				}
			}else{
				$('#alarmphone_contrycode_<s:property value="#phone.index + 1"/>').val('<s:property value="#item.countrycode"/>');
			}
		}
		//$('#alarmphone_contrycode_<s:property value="#phone.index + 1"/>  option[value=<s:property value="#item.countrycode"/>]').attr("selected", true);
	</s:iterator>
}

var ccode = <s:property value="#session.IREMOTE_USER.countrycode"/>
	function countryNumber(){
	var str = "";
	if(ccode==0){//邮箱用户
		for(var i = 0; i < countryCode.length; i++){
			if(countryCode[i].countryCode == '1' && platform=='9' && i<100)
				str += "<option value='" + countryCode[i].countryCode +"' selected='selected' id=\"canadacountry\">" + countryCode[i].countryName + "</option>";
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

$(document).ready(function()
{
	$('.numbersOnly').keyup(function () {
	    if (this.value != this.value.replace(/[^0-9]/g, '')) {
	       this.value = this.value.replace(/[^0-9]/g, '');
	    }
	});
	addCountry();
	initAlarmphoneBox();
	$("#startDiv").DateTimePicker();
	$("#endDiv").DateTimePicker();
	
});
window.onload=function (){

	showchoosescene();
	showchoosepartition();
	showchoosepartition2();

};
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
<form id="editlockuserform" action="/iremote/device/lock/editDoorlockUserAlarmAction" method="POST">
<input type="hidden" name="zwavedeviceid" value="<s:property value='zwavedeviceid'/>"/>
<input type="hidden" id="usertype" name="usertype" value="<s:property value='usertype'/>"/>
<input type="hidden" id="notificationid" name="notificationid" value="<s:property value='notificationid'/>"/>
<input type="hidden" id="localtime" name="localtime" value="0"/>
<table class="title_table">
<tr >
	<td align="left"  width="40px">
		<s:if test="toapp == true">
			<a href="javascript:onback()"><img src="/iremote/images/nav/nav_btn_back.png"  class="nav_img"/></a>
		</s:if>
		<s:else>
			<a href="/iremote/device/lock/listlockuser?zwavedeviceid=<s:property value='doorlocklser.zwavedeviceid'/>"><img src="/iremote/images/nav/nav_btn_back.png"  class="nav_img"/></a>
		</s:else>
	</td>
	<td align="center"><span class="title_c"><s:text name="edit" />
	<s:if test="doorlocklser.usertype == 21"><s:text name="passworduser" /></s:if>
	<s:if test="doorlocklser.usertype == 22"><s:text name="fingerprintuser" /></s:if>
	<s:if test="doorlocklser.usertype == 32"><s:text name="carduser" /></s:if>
	</span>
	</td>
	<td align="right"  width="40px"><a href="javascript:onSubmit(this);"><img src="/iremote/images/nav/nav_btn_preservation.png"  class="nav_img"/></a></td>
</tr>
</table>
	<input type="hidden" value="<s:property value = "doorlocklser.doorlockuserid"/>" name="doorlockuserid" />
	<div style="background:#ffffff;margin-top:5px;">
		<input type="text" name="username" id="username" class="input_text"  value="<s:property value = "doorlocklser.username"/>" maxlength="16" style="background:#ffffff url(/iremote/images/icon/icon_user.png) no-repeat;background-size:32px 32px;"/>
	</div>
	<!-- 执行情景 -->
	<div style="background:#ffffff;width:100%;height:50px;margin-top:5px;">
		<div style="float:left;">
			<input type="text" onclick="showHideScene();" id="showhidescene" class="input_usertype" readonly="readonly" /><s:text name='executescene' />
		</div>
	</div>
	<!-- 情景表格 -->
	 <table width="100%" id ="tbl_scene" style="background:#ffffff;display:none;margin-top:5px;">
	    <s:iterator value="scenelist" status = "scene" var = "item">
      	<tr height="55px" id="tr_scene_<s:property/>" style="white-space:nowrap;">
			<td align="right" width="5px">&nbsp;</td>
			<td align="right" width="80px">
				<input type="checkbox" name="scene" id="scene_<s:property value="#scene.index"/>" value="<s:property value="#item.scenedbid"/>" style="zoom:200%;"/>
			</td>
			<td >
				<div style="margin:0px 28px 0px 0px;">
					<s:property value="#item.name"/>
					
				</div>
			</td>
		</tr>
   		</s:iterator>  
	</table>
	<s:if test="hasArmFunction==true">
		<!-- 撤防防区 -->
		 <div style="background:#ffffff;width:100%;height:50px;margin-top:5px;">
			<div style="float:left;">
				<input type="text" onclick="showHidePartition();" id="showhidepartition" class="input_usertype" readonly="readonly" /><s:text name='disarmpartition' />
			</div>
		</div>
		<!-- 防区表格 -->
	     <table width="100%" id ="tbl_partition" style="background:#ffffff;display:none;margin-top:5px;">
		    <s:iterator value="partitionlist" status = "partition" var = "item">
	      	<tr height="55px" id="tr_partition_<s:property/>" style="white-space:nowrap;">
				<td align="right" width="5px">&nbsp;</td>
				<td align="right" width="80px">
					<input type="checkbox" name="partition" id="partition_<s:property value="#partition.index"/>"  value="<s:property value="#partition.index"/>#<s:property value="#item.partitionid"/>" style="zoom:200%;"/>
				</td>
				<td >
					<div style="margin:0px 28px 0px 0px;">
						<s:property value="#item.name"/>
						<span style="float:right">
						<input type="text" name="password" id="password_<s:property value="#partition.index"/>" placeholder="<s:text name='disarmcode' />" style="width:80px"/>
						</span>
					</div>
				</td>
			</tr>
	   		</s:iterator>  
		</table>
		<!-- 设防防防区 -->
		 <div style="background:#ffffff;width:100%;height:50px;margin-top:5px;">
			<div style="float:left;">
				<input type="text" onclick="showHidePartition2();" id="showhidepartition2" class="input_usertype" readonly="readonly" /><s:text name='armpartition' />
			</div>
		</div>
		<!-- 防区表格 -->
	     <table width="100%" id ="tbl_armpartition" style="background:#ffffff;display:none;margin-top:5px;">
		    <s:iterator value="partitionlist" status = "partition" var = "item">
	      	<tr height="55px" id="tr_armpartition_<s:property/>" style="white-space:nowrap;">
				<td align="right" width="5px">&nbsp;</td>
				<td align="right" width="80px">
					<input type="checkbox" name="armpartition" id="armpartition_<s:property value="#partition.index"/>"  value="<s:property value="#partition.index"/>#<s:property value="#item.partitionid"/>" style="zoom:200%;"/>
				</td>
				<td >
					<div style="margin:0px 28px 0px 0px;">
						<s:property value="#item.name"/>
						<span style="float:right">
							<select name="armtype" id="armtype_<s:property value="#partition.index"/>">
								<option value="3"><s:text name='arminhome'/></option>
								<option value="4"><s:text name='armleavehome'/></option>
							</select>
						</span>
					</div>
				</td>
			</tr>
	   		</s:iterator>  
		</table>
	</s:if>
	<!-- 告警用户 -->
	<div style="background:#ffffff;width:100%;height:50px;margin-top:5px;">
		<div style="float:left;">
			<input type="text" onclick="showHideAlarmphone();" id="showhidealarm" class="input_usertype" readonly="readonly" /><s:text name='alarmuser' />
		</div>
	</div>	

<table width="100%" id ="tbl_alarmphone" style="background:#ffffff;display:none;margin-top:5px;">	
	    <s:iterator value="alarmphoneArray" status = "phone" var = "item">
      	<tr height="55px" id="tr_alarmphone_<s:property/>" style="white-space:nowrap;">
			<td align="right" width="5px">&nbsp;</td>
			<td align="right" width="80px">
				<select name="countrycode" class="selectCounrty" id="alarmphone_contrycode_<s:property value="#phone.index + 1"/>">
					
				</select>
			</td>
			<td >
				<div style="margin:0px 28px 0px 0px;">
					<input type="text" name="alarmphone" id="alarmphone_<s:property value="#phone.index + 1"/>" style="width:100%" value="<s:property value="#item.alarmphone"/>" class="input_text_alarm numbersOnly" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  placeholder="<s:text name="enteralarmphonenunmber" />" maxlength="16"/>
				</div>
			</td>
		</tr>
   		</s:iterator>  
</table>    

<div style="background:#ffffff;">
	<div style="margin:5px 60px 0px 10px;">
		<s:text name="starttime"/>
		<input placeholder="<s:text name='selectstartnumber' />" class="input_date" readonly="readonly" maxlength="16" id="validfrom" name="validfrom" type="text" value="<s:property value="validfrom"/>"  data-field="datetime">
		<div id="startDiv"></div>
		<s:text name="endtime"/>
		<input placeholder="<s:text name='selectendnumber' />" class="input_date" readonly="readonly" maxlength="16" id="validthrough" name="validthrough" type="text"  value="<s:property value="validthrough"/>"  data-field="datetime" >
		<div id="endDiv"></div>
	</div>    
</div>

</form>
</body>
</html>