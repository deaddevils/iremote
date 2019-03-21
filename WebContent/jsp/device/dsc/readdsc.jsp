<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<title><s:text name="title" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.toast.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/device/deviceName/mockJsInterface.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/iremote.js"></script>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/iremotewebstyle.css" charset="utf-8"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.toast.css" />
<style type="text/css">
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
	
	margin-left:0px;

}

</style>
<script type="text/javascript">
var deviceid = "<s:property value='deviceid'/>";
var system = "";
var ifAlert = 0;
var alarmtimes =0;

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
	channelChange(${channelcount});
	partitionChange(${partitioncount});
	var key=${channelcount};
	var parkey=${partitioncount};
    //根据值让option选中 
    $("#channelnumber").val(key); 
    $("#partitionnumber").val(parkey);
    
});

/*remove url of alert/confirm on phone ios not support*/
/* window.alert = function(name){
    var iframe = document.createElement("IFRAME");
    iframe.style.display="none";
    iframe.setAttribute("src", 'data:text/plain,');
    document.documentElement.appendChild(iframe);
    window.frames[0].window.alert(name);
    iframe.parentNode.removeChild(iframe);
}; */

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
	if ( validatepassword() == false )
		return ;
	var array=$("input[name='switchsNames']");
	var pararray=$("select[name='belongsto']");
	var length=$('#channelnumber option:selected').val();
	for(var i=0;i<length;i++ ){
		if(!validateChannelName(array[i])){
			return;
		};
	}
	for(var i=0;i<length;i++ ){
		if(!validatePartitionChoose(pararray[i])){
			return;
		};
	} 
	var partitionarray=$("input[name='partitionNames']");
	var partitionlength=$('#partitionnumber option:selected').val();
	for(var i=0;i<partitionlength;i++ ){
		if(!validatePartitionName(partitionarray[i])){
			return;
		};
	} 
	if(ifAlert>0&&alarmtimes==0){
		alarmtimes=1;
		toast('<s:text name="partitionnamelength" />');
		return;
	}
	var data = $('#formSumbit').serialize();
	var message = '<s:text name="devicenamecrash" />';
	validate(data,true,message);
}

//效验设备名
function validateZwaveName(obj){
	var name = $(obj).val();
	var zwavedeviceid = <s:property value="zwaveDevice.zwavedeviceid"/>;
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

function validatepassword()
{
	if ( $("#password").val() == '' )
		return true ;
	
	if ($("#password").val() != $("#password").val().replace(/[^0-9]/g, '') ||　$("#password").val().length > 6)
	{
		$("#username").focus();
		toast('<s:text name="passwordhastobeatmostsixmumbers" />');
		return false;
	}
	return true;
}

//效验通道名
 function validateChannelName(obj){
	var switchsNames = $(obj).val();
	var channelnumber = $("#channelnumber").val();
	if(switchsNames.match(regRule)) {
		switchsNames = switchsNames.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
	    $(obj).val(switchsNames);
	    toast('<s:text name="wildcharnotsupport" />');
	    return false;
	} 
	var array=$("input[name='switchsNames']");
	var length=$('#channelnumber option:selected') .val();
	for(var i=0;i<length;i++ ){
		if(array[i].value==""){
			toast('<s:text name="channelnamerequired" />');
			return false;
		}
		if(array[i].value.length>10){
			ifAlert = 1;
		}
		for(var j=i+1;j<length;j++){
			if(array[i].value!="" && array[i].value==array[j].value){
				toast('<s:text name="channelnamerepeated" />');
				return false;
			}
		}
	}
	return true;
} 
//效验通道选择是否大于通道数
 function validatePartitionChoose(obj){
	var partitionselect = $(obj).val();
	var partitionnumber = $("#partitionnumber").val(); 
	var length=$('#channelnumber option:selected').val();
	for(var i=0;i<length;i++ ){
		if(partitionselect > partitionnumber){
			toast('<s:text name="partitionerror" />');
			return false;
		}
	}
	return true;
} 
//效验防区名
 function validatePartitionName(obj){
	var parNames = $(obj).val();
	if(parNames.match(regRule)) {
		parNames = parNames.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
	    $(obj).val(parNames);
	    toast('<s:text name="wildcharnotsupport" />');
	    return false;
	} 
	var partitionarray=$("input[name='partitionNames']");
	var partitionlength=$('#partitionnumber option:selected').val();
	for(var i=0;i<partitionlength;i++ ){
		if(partitionarray[i].value==""){
			toast('<s:text name="partitionnamerequired" />');
			return false;
		}
		for(var j=i+1;j<partitionlength;j++){
			if(partitionarray[i].value!="" && partitionarray[i].value==partitionarray[j].value){
				toast('<s:text name="partitionnamerepeated" />');
				return false;
			}
		}
	}
	return true;
} 
function Submit(){
	$.ajax({
        type: "post",
        url:"/iremote/webconsole/createdscdevice",
        data:$('#formSumbit').serialize(),
        success : function(data) {
        	if ( data.resultCode == 0 ){
        		window.location.href='<%=request.getContextPath()%>/webconsole/listappliance?deviceid=' + deviceid;
        	}
        	else if ( data.resultCode == 30316)
        		toast('<s:text name="repeatdsc" />');
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

function partitionChange(type){
	$("select[name='belongsto']").empty();
	if(type == 1){		
		var optionTags = '<option value ="1"><s:text name="partition"/> 1</option>';			
		$("#2partition").hide();$("#3partition").hide();$("#4partition").hide();
		$("#5partition").hide();$("#6partition").hide();$("#7partition").hide();$("#8partition").hide();
	}else if(type == 2){
		var optionTags = '<option value ="1"><s:text name="partition"/> 1</option>'+
		'<option value ="2"><s:text name="partition"/> 2</option>';
		$("#2partition").show();$("#3partition").hide();$("#4partition").hide();
		$("#5partition").hide();$("#6partition").hide();$("#7partition").hide();$("#8partition").hide();
	}else if(type == 3){
		var optionTags = '<option value ="1"><s:text name="partition"/> 1</option>'+
		'<option value ="2"><s:text name="partition"/> 2</option>'+
		'<option value ="3"><s:text name="partition"/> 3</option>';
		$("#2partition").show();$("#3partition").show();$("#4partition").hide();
		$("#5partition").hide();$("#6partition").hide();$("#7partition").hide();$("#8partition").hide();
	}else if(type == 4){
		var optionTags = '<option value ="1"><s:text name="partition"/> 1</option>'+
		'<option value ="2"><s:text name="partition"/> 2</option>'+
		'<option value ="3"><s:text name="partition"/> 3</option>'+
		'<option value ="4"><s:text name="partition"/> 4</option>';
		$("#2partition").show();$("#3partition").show();$("#4partition").show();
		$("#5partition").hide();$("#6partition").hide();$("#7partition").hide();$("#8partition").hide();
	}else if(type == 5){
		var optionTags = '<option value ="1"><s:text name="partition"/> 1</option>'+
		'<option value ="2"><s:text name="partition"/> 2</option>'+
		'<option value ="3"><s:text name="partition"/> 3</option>'+
		'<option value ="4"><s:text name="partition"/> 4</option>'+
		'<option value ="5"><s:text name="partition"/> 5</option>';
		$("#2partition").show();$("#3partition").show();$("#4partition").show();
		$("#5partition").show();$("#6partition").hide();$("#7partition").hide();$("#8partition").hide();
	}else if(type == 6){
		var optionTags = '<option value ="1"><s:text name="partition"/> 1</option>'+
		'<option value ="2"><s:text name="partition"/> 2</option>'+
		'<option value ="3"><s:text name="partition"/> 3</option>'+
		'<option value ="4"><s:text name="partition"/> 4</option>'+
		'<option value ="5"><s:text name="partition"/> 5</option>'+
		'<option value ="6"><s:text name="partition"/> 6</option>';
		$("#2partition").show();$("#3partition").show();$("#4partition").show();
		$("#5partition").show();$("#6partition").show();$("#7partition").hide();$("#8partition").hide();
	}else if(type == 7){
		var optionTags = '<option value ="1"><s:text name="partition"/> 1</option>'+
		'<option value ="2"><s:text name="partition"/> 2</option>'+
		'<option value ="3"><s:text name="partition"/> 3</option>'+
		'<option value ="4"><s:text name="partition"/> 4</option>'+
		'<option value ="5"><s:text name="partition"/> 5</option>'+
		'<option value ="6"><s:text name="partition"/> 6</option>'+
		'<option value ="7"><s:text name="partition"/> 7</option>';
		$("#2partition").show();$("#3partition").show();$("#4partition").show();
		$("#5partition").show();$("#6partition").show();$("#7partition").show();$("#8partition").hide();
	}else if(type == 8){
		var optionTags = '<option value ="1"><s:text name="partition"/> 1</option>'+
		'<option value ="2"><s:text name="partition"/> 2</option>'+
		'<option value ="3"><s:text name="partition"/> 3</option>'+
		'<option value ="4"><s:text name="partition"/> 4</option>'+
		'<option value ="5"><s:text name="partition"/> 5</option>'+
		'<option value ="6"><s:text name="partition"/> 6</option>'+
		'<option value ="7"><s:text name="partition"/> 7</option>'+
		'<option value ="8"><s:text name="partition"/> 8</option>';
		$("#2partition").show();$("#3partition").show();$("#4partition").show();
		$("#5partition").show();$("#6partition").show();$("#7partition").show();$("#8partition").show();
	}
	$("select[name='belongsto']").append(optionTags);
}
function channelChange(type) {
	if(type == 8){
		$("#16channel").hide();$("#24channel").hide();$("#32channel").hide();
		$("#40channel").hide();$("#48channel").hide();$("#56channel").hide();$("#64channel").hide();
    }else if(type == 16){
        $("#16channel").show();$("#24channel").hide();$("#32channel").hide();
		$("#40channel").hide();$("#48channel").hide();$("#56channel").hide();$("#64channel").hide();
    }else if(type == 24){
        $("#16channel").show();$("#24channel").show();$("#32channel").hide();
		$("#40channel").hide();$("#48channel").hide();$("#56channel").hide();$("#64channel").hide();
    }else if(type == 32){
        $("#16channel").show();$("#24channel").show();$("#32channel").show();
		$("#40channel").hide();$("#48channel").hide();$("#56channel").hide();$("#64channel").hide();
    }else if(type == 40){
        $("#16channel").show();$("#24channel").show();$("#32channel").show();
		$("#40channel").show();$("#48channel").hide();$("#56channel").hide();$("#64channel").hide();
    }else if(type == 48){
        $("#16channel").show();$("#24channel").show();$("#32channel").show();
		$("#40channel").show();$("#48channel").show();$("#56channel").hide();$("#64channel").hide();
    }else if(type == 56){
        $("#16channel").show();$("#24channel").show();$("#32channel").show();
		$("#40channel").show();$("#48channel").show();$("#56channel").show();$("#64channel").hide();
    }else if(type == 64){
        $("#16channel").show();$("#24channel").show();$("#32channel").show();
        $("#40channel").show();$("#48channel").show();$("#56channel").show();$("#64channel").show();
    }
}
</script>

</head>
<body class="listpage">
	<table class="title_table">
		<tr>
			<td align="left" width="40px"><a href="javascript:onback(0);"><img src="/iremote/images/nav/nav_btn_back.png"  class="nav_img"/></a></td>
			<td align="center"><span class="title_c"><s:text name="createdscdevice" /></span></td>
			<td align="right" width="40px" ><a href="javascript:onSubmit(this);"><img src="/iremote/images/nav/nav_btn_preservation.png"  class="nav_img"/></a></td>
		</tr>
	</table>
	
<form action="/iremote/device/setdeivityname" id="formSumbit" method="post">
	<input type="hidden" name="deviceid" id="deviceid" value="<s:property value='deviceid'/>"/>
	<input type="hidden" name="zwavedeviceid" value="<s:property value='zwaveDevice.zwavedeviceid'/>"/>
<table width="100%" style="background:#ffffff;margin-top:5px;">	
      	<tr height="40px" style="display: none">
			<td align="right" nowrap="nowrap" >
				<span class="content_font">ID:</span>
			</td>
			<td align="left" >  
				<div>
					<select id="nuid" name="nuid">
						<%-- <s:iterator value="nuids" id="nid">
							<option value='<s:property value="#nid"/>'><s:property value="#nid"/></option>
						</s:iterator> --%>
						<option value='11401'><s:property value="#nid"/></option>
					</select>
				</div>
			</td> <td ></td><td ></td><td ></td>
		</tr>
      	<tr height="40px">
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="dscname" />:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div>
					<input type="text" class="input_text" id="name" name="name" maxlength="32" value='<s:property value="zwaveDevice.name"/>'/>
				</div>
			</td> 
		</tr>
      	<tr height="40px">
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="password" />:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div>
					<input type="password" class="input_text" id="password" name="password" maxlength="6" value="" placeholder="<s:text name="inputpassword1" />"/>
				</div>
			</td> 
		</tr>
<tbody id="1partition">
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
		<tr height="40px">
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="partitionnumber" /></span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<select id="partitionnumber" style="width:54px" name="partitionnumber" onchange="partitionChange($(this).val());" >
					<option value ="1">1</option>
					<option value ="2">2</option>
					<option value ="3">3</option>
					<option value ="4">4</option>
					<option value ="5">5</option>
					<option value ="6">6</option>
					<option value ="7">7</option>
					<option value ="8">8</option>
				</select>
			</td> 
		</tr>
	      	<tr height="40px" >
				<td align="left" nowrap="nowrap" width="5%">
					<span class="content_font">&nbsp;&nbsp;<s:text name="partition" /> 1:</span>
				</td>
				<td align="left" colspan=4 width="95%">  
					<div>
						<input type="text" class="input_text" name="partitionNames" maxlength="32" value="<s:property value="partitions[0].name"/>"/>
					</div>
				</td> 
			</tr>
		</tbody>
		<tbody id="2partition">
			<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
	      	<tr height="40px" >
				<td align="left" nowrap="nowrap" width="5%">
					<span class="content_font">&nbsp;&nbsp;<s:text name="partition" /> 2:</span>
				</td>
				<td align="left" colspan=4>  
					<div>
						<input type="text" class="input_text" name="partitionNames" maxlength="32" value="<s:property value="partitions[1].name"/>"/>
					</div>
				</td> 
			</tr>
		</tbody>
		<tbody id="3partition">
			<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
	      	<tr height="40px" >
				<td align="left" nowrap="nowrap" width="5%">
					<span class="content_font">&nbsp;&nbsp;<s:text name="partition" /> 3:</span>
				</td>
				<td align="left" colspan=4>  
					<div>
						<input type="text" class="input_text" name="partitionNames" maxlength="32" value="<s:property value="partitions[2].name"/>"/>
					</div>
				</td> 
			</tr>
			</tbody>
			<tbody id="4partition">
			<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
	      	<tr height="40px" >
				<td align="left" nowrap="nowrap" width="5%">
					<span class="content_font">&nbsp;&nbsp;<s:text name="partition" /> 4:</span>
				</td>
				<td align="left" colspan=4>  
					<div>
						<input type="text" class="input_text" name="partitionNames" maxlength="32" value="<s:property value="partitions[3].name"/>"/>
					</div>
				</td> 
			</tr>
		</tbody>
		<tbody id="5partition">
			<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
	      	<tr height="40px" >
				<td align="left" nowrap="nowrap" width="5%">
					<span class="content_font">&nbsp;&nbsp;<s:text name="partition" /> 5:</span>
				</td>
				<td align="left" colspan=4>  
					<div>
						<input type="text" class="input_text" name="partitionNames" maxlength="32" value="<s:property value="partitions[4].name"/>"/>
					</div>
				</td> 
			</tr>
			</tbody>
			<tbody id="6partition">
			<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
	      	<tr height="40px" >
				<td align="left" nowrap="nowrap" width="5%">
					<span class="content_font">&nbsp;&nbsp;<s:text name="partition" /> 6:</span>
				</td>
				<td align="left" colspan=4>  
					<div>
						<input type="text" class="input_text" name="partitionNames" maxlength="32" value="<s:property value="partitions[5].name"/>"/>
					</div>
				</td> 
			</tr>
			</tbody>
			<tbody id="7partition">
			<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
	      	<tr height="40px" >
				<td align="left" nowrap="nowrap" width="5%">
					<span class="content_font">&nbsp;&nbsp;<s:text name="partition" /> 7:</span>
				</td>
				<td align="left" colspan=4>  
					<div>
						<input type="text" class="input_text" name="partitionNames" maxlength="32" value="<s:property value="partitions[6].name"/>"/>
					</div>
				</td> 
			</tr>
			</tbody>
			<tbody id="8partition">
			<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
	      	<tr height="40px" >
				<td align="left" nowrap="nowrap" width="5%">
					<span class="content_font">&nbsp;&nbsp;<s:text name="partition" /> 8:</span>
				</td>
				<td align="left" colspan=4>  
					<div>
						<input type="text" class="input_text" name="partitionNames" maxlength="32" value="<s:property value="partitions[7].name"/>"/>
					</div>
				</td> 
			</tr>
		</tbody>
<tbody id="8channel">
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
		<tr height="40px">
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channelnumber" /></span>
			</td>
			<td align="left" colspan=4>  
				<div>
					<select id="channelnumber" style="width:54px" name="channelnumber" onchange="channelChange($(this).val());" >
						<option value ="8">8</option>
						<option value ="16">16</option>
						<option value ="24">24</option>
						<option value ="32">32</option>
						<option value ="40">40</option>
						<option value ="48">48</option>
						<option value ="56">56</option>
						<option value ="64">64</option>
					</select>
				</div>
			</td> 
		</tr>
		<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 1:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[0].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 2:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[1].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 3:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[2].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 4:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[3].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 5:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[4].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 6:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[5].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 7:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[6].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 8:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[7].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
</tbody>
<tbody id="16channel">
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 9:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[8].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 10:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[9].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 11:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[10].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 12:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[11].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 13:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[12].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 14:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[13].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 15:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[14].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 16:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[15].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
</tbody>
<tbody id="24channel">
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 17:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[16].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 18:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[17].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 19:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[18].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 20:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[19].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 21:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[20].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 22:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[21].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 23:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[22].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 24:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[23].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
</tbody>
<tbody id="32channel">
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 25:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[24].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 26:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[25].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 27:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[26].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 28:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[27].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 29:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[28].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 30:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[29].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 31:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[30].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 32:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[31].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
</tbody>
<tbody id="40channel">
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 33:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[32].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 34:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[33].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 35:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[34].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 36:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[35].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 37:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[36].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 38:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[37].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 39:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[38].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 40:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[39].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
</tbody>
<tbody id="48channel">
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 41:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[40].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 42:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[41].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 43:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[42].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 44:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[43].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 45:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[44].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 46:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[45].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 47:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[46].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 48:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[47].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
</tbody>
<tbody id="56channel">
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 49:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[48].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 50:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[49].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 51:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[50].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 52:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[51].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 53:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[52].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 54:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[53].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 55:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[54].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 56:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[55].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
</tbody>
<tbody id="64channel">
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 57:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[56].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 58:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[57].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 59:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[58].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 60:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[59].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 61:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[60].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 62:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[61].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 63:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[62].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
<tr ><td style="height:2px;background-color:#f4f4f4;" colspan=5 width="100%"></td></tr>
      	<tr height="40px" >
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">&nbsp;&nbsp;<s:text name="channel" /> 64:</span>
			</td>
			<td align="left" colspan=4 width="95%">  
				<div >
					<input  type="text" class="input_text" name="switchsNames" maxlength="32" value="<s:property value="zWaveSubDevices[63].name"/>"/>
				</div>
			</td> 
		</tr>
		<jsp:include flush="true" page="channeltypeselect.jsp"/>
</tbody>
		
	</table>   

</form>
<br><br><br><br><br><br>
</body>
</html>