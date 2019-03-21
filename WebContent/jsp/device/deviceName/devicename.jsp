<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<title><s:text name="title" /></title>
<link type="text/css" rel="stylesheet" href="/iremote/css/iremotewebstyle.css" charset="utf-8"/>
<link rel="stylesheet" type="text/css" href="/iremote/css/jquery.toast.css" />
<s:if test="zwaveDevice.devicetype== 60">
	<link rel="stylesheet" type="text/css" href="/iremote/css/bootstrap-3.3.4.css">
	<link rel="stylesheet" href="/iremote/css/mpicker.css">
</s:if>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/iremote/js/jquery.toast.js"></script>
<script type="text/javascript" src="/iremote/js/iremote.js"></script>
<script type="text/javascript" src="/iremote/jsp/device/deviceName/mockJsInterface.js"></script>
<style type="text/css">

.slide-wapper {
	padding:0px;
	margin-top: -5px;
	height: 70px;
	overflow-y: scroll;
	/* background: #eee; */
}
.input_option
{
	width:60px;
	height:40px;
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
	
	margin-left:/* 2 */0px;

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

var lock = '<s:property value="isExit"/>' ;
var hasArmFunction = '<s:property value="hasArmFunction"/>' ;
var isOnlyBlueToothLock = '<s:property value="isOnlyBlueToothLock"/>' ;

function showLock(){  
    if (lock == 'true' ){ 
    	lock = 'false';
    	$("#backlock").val("false");
    }else{
    	lock = 'true';
    	$("#backlock").val("true");
    }  
	$("#lock").toggleClass("check_box_uncheck");
	$("#lock").toggleClass("check_box_checked");
}
function showAlarmBox(){  
	if(1==<s:property value="inhomenotalarm"/>){ 
    	$("#inhomenotalarm").val(0);
    }else{
    	$("#inhomenotalarm").val(1);
    }  
	$("#alarm").toggleClass("check_box_uncheck");
	$("#alarm").toggleClass("check_box_checked");
}
function initShowLockBox()
{
    if (lock == 'true' )
    { 
    	$("#backlock").val("true");
    	$("#lock").addClass("check_box_checked");    	
    }
    else
    {
    	$("#backlock").val("false");
    	$("#lock").addClass("check_box_uncheck");    	
    }  
}

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
	initShowLockBox();
	initCentigradeBox();
	stopentersubmit();
	inhomenotalarm();
});

function inhomenotalarm(){
	/* if(1==<s:property value="inhomenotalarm"/>){
		$("#inhomenotalarm").prop("checked","checked");
	} */
	if (1==<s:property value="inhomenotalarm"/>){ 
		$("#inhomenotalarm").val(1);
		$("#alarm").addClass("check_box_checked");    	
	}else{
		$("#inhomenotalarm").val(0);
		$("#alarm").addClass("check_box_uncheck");  
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
	for(var i=0;i<array.length;i++ ){
		if(!validateChannelName(array[i])){
			return;
		};
	}
	if(!validateDelayTime()){
		return;
	};
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

//效验通道名
function validateChannelName(obj){
	var switchsNames = $(obj).val();
	var zwavedeviceid = $("#zwavedeviceid").val();
	if(switchsNames.match(regRule)) {
		switchsNames = switchsNames.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
	    $(obj).val(switchsNames);
	    toast('<s:text name="wildcharnotsupport" />');
	    return false;
	} 
	var array=$("input[name='switchsNames']");
	for(var i=0;i<array.length;i++ ){
		if(array[i].value==""){
			toast('<s:text name="channelnamerequired" />');
			return false;
		}
		for(var j=i+1;j<array.length;j++){
			if(array[i].value!="" && array[i].value==array[j].value){
				toast('<s:text name="channelnamerepeated" />');
				return false;
			}
		}
	}
	var data = {"switchsNames":switchsNames,"zwavedeviceid":zwavedeviceid}; 
	var message = '<s:text name="channelnameexist" />';
	return validate(data,false,message);
}

//效验延时时间
function validateDelayTime(){
	var delaytime = $("#delay").val();	
	if(delaytime == undefined ||delaytime == null || delaytime == "" || delaytime>=0&&delaytime<=255){		
		return true;
	}else{
		toast('<s:text name="delaytimeerror" />');
		return false;
	}
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
	<input type="hidden" id="areaid" name="areaid" value="<s:property value='areaid'/>"/>

	<table width="100%" style="background:#ffffff;margin-top:5px;">	
      	<tr height="55px">
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

	<s:if test=" channelcount >= 2 ">
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
	</s:if>
	
	<s:if test="channelcount >= 3 ">
		<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="55px">
			<td align="right" width="5px">&nbsp;</td>
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font"><s:text name="channel" /> 3:</span>
			</td>
			<td align="left" >  
				<div>
					<input type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[2].name"/>"/>
				</div>
			</td> 
		</tr>
	</s:if>
	
		<s:if test="channelcount >= 4 ">
		<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="55px">
			<td align="right" width="5px">&nbsp;</td>
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font"><s:text name="channel" /> 4:</span>
			</td>
			<td align="left" >  
				<div>
					<input type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[3].name"/>"/>
				</div>
			</td> 
		</tr>
		</s:if>
	
		<s:if test="channelcount >= 6 ">
		<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="55px">
			<td align="right" width="5px">&nbsp;</td>
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font"><s:text name="channel" /> 5:</span>
			</td>
			<td align="left" >  
				<div>
					<input type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[4].name"/>"/>
				</div>
			</td> 
		</tr>
		<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="55px">
			<td align="right" width="5px">&nbsp;</td>
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font"><s:text name="channel" /> 6:</span>
			</td>
			<td align="left" >  
				<div>
					<input type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[5].name"/>"/>
				</div>
			</td> 
		</tr>
		</s:if>
		<s:if test="hasArmFunction==true && isOnlyBlueToothLock!=true">
			<s:if test="zwaveDevice.devicetype== 4 ||zwaveDevice.devicetype== 5
				||zwaveDevice.devicetype== 6 || zwaveDevice.devicetype== 55 || zwaveDevice.devicetype== 54">
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
			</s:if>
		</s:if>
		<s:if test="isOnlyBlueToothLock!=true">
			<s:if test="zwaveDevice.devicetype == 5 || zwaveDevice.devicetype== 55">
				<tr height="55px">
					<td align="right" width="5px">&nbsp;</td>
					<td align="left" nowrap="nowrap" width="5%">
						<span class="content_font"><s:text name="timezone" />:</span>
					</td>
					<td align="left" >  
						<div>
							<select class="input_text" name="timezoneid" id="timezoneid">
								<s:iterator value="timezonenames" id="tzn">
									<option value="<s:property value='#tzn.right'/>"
										<s:if test="currenttimezone == #tzn.right"> selected=true </s:if>
									><s:property value='#tzn.left'/></option>
								</s:iterator>
							</select>
						</div>
					</td> 
				</tr>
			</s:if>
		</s:if>
		<s:if test="hasArmFunction==true && isOnlyBlueToothLock!=true">
			<s:if test="zwaveDevice.devicetype== 4 ||zwaveDevice.devicetype== 5
				||zwaveDevice.devicetype== 6 || zwaveDevice.devicetype== 55 || zwaveDevice.devicetype== 54">
			<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
	      	<tr height="55px">
				<td align="right" width="5px">&nbsp;</td>
				<!-- <td align="right" nowrap="nowrap" width="5%">
					
				</td> -->
				<td align="left" colspan=4>  
					<div>
						<input type="hidden" name="inhomenotalarm" id="inhomenotalarm" value="1" style="zoom:180%;vertical-align: -5px;" >
						
						<input type="text" onclick="showAlarmBox()" class="input_option content_font" id="alarm"  value="<s:text name="inhomenotalarm" />" readonly="readonly" style="width:280px;"/>
					</div>
				</td> 
			</tr>
			</s:if>
		</s:if>
		<s:if test="isOnlyBlueToothLock!=true">
			<s:if test="zwaveDevice.devicetype == 5 || zwaveDevice.devicetype== 55">
			<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
		      	<tr height="55px">
					<td align="right" width="5px">&nbsp;</td>
					<!-- <td align="left" nowrap="nowrap" ></td>  -->
					<td align="left" width="20px">
						<input type="hidden" id="backlock" name="backlock" value="false">
						<input type="text" onclick="showLock()" class="input_option content_font" id="lock"  value="<s:text name="lock" />" readonly="readonly" style="width:80px;"/>
					</td>
				</tr>
			</s:if>
		</s:if>
	</table>   
	
	
	
	<table width="100%" style="margin-top:5px;">  
		<%-- <s:if test="zwaveDevice.devicetype == 5">
	      	<tr height="55px">
				<td align="left" width="90px">&nbsp;</td>
				<td align="left" nowrap="nowrap" >
					<input type="hidden" id="backlock" name="backlock" value="false">
					<input type="text" onclick="showLock()" class="input_option content_font" id="lock"  value="<s:text name="lock" />" readonly="readonly" style="width:80px;"/>
				</td> 
				<td align="left" width="20px">&nbsp;</td>
			</tr>
		</s:if> --%>
		<s:if test="zwaveDevice.devicetype == 14">
			<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
	      	<tr height="55px">
				<td align="right" width="15px">&nbsp;</td>
				<td align="right" nowrap="nowrap">
					<input type="hidden" id="fahrenheit" name="fahrenheit" value="false">
					<input type="text" onclick="showCentigrade()" class="input_option content_font" id="centigrade"  value="<s:text name="centigrade" />" readonly="readonly" style="width:80px;"/>
				</td> 
			</tr>
		</s:if>
	</table>
</form>
<s:if test="zwaveDevice.devicetype== 60">
	<div class="slide-wapper">
		<div class="slide-item">
			<div class="console"></div>
			<br>
			<input type="text" class="select-value form-control" value="<s:if test="addrinfo == null">选择地址</s:if><s:else><s:property value='addrinfo'/></s:else>">
			<br><br>
		</div>
	</div>
	<script type="text/javascript" src="/iremote/js/json.js"></script>
	<script type="text/javascript" src="/iremote/js/mPicker.min.js"></script>
	<script chartset="UTF-8">
		$(function() {
			/**
			 * 联动的picker
			 * 三级
			 */
			$('.select-value').mPicker({
				level:3,
				dataJson: city3,
				Linkage:true,
				rows:5,
				idDefault:true,
				splitStr:'-',
				header:'<div class="mPicker-header">选择地址</div>',
				confirm:function(json){
					var value = json.values.replace(/[^0-9]/ig,"");
					$("#areaid").attr("value",value);
					// console.info('选中的value为：', value);

				},
				cancel:function(json){
					// console.info('当前选中json：',json);
				}
			})
			//获取mpicker实例
			var method= $('.select-value').data('mPicker');
			// console.info('第一个mpicker的实例为：',method);
		});
	</script>
</s:if>
</body>
</html>
