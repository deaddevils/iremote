<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/language.jsp"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" /> 
<title><s:text name="title" /></title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/iremote/js/jquery.toast.js"></script>
<script type="text/javascript" src="/iremote/js/iremote.js"></script>
<script type="text/javascript" src="/iremote/jsp/device/deviceName/mockJsInterface.js"></script>
<link type="text/css" rel="stylesheet" href="/iremote/css/iremotewebstyle.css" charset="utf-8"/>
<link rel="stylesheet" type="text/css" href="/iremote/css/jquery.toast.css" />
<link rel="stylesheet" href="/iremote/css/rangeslider.css">
<script src="/iremote/js/rangeslider.min.js"></script>
<style type="text/css">
.input_option
{
	width:50px;
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
	width:80%;
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
function initrange(){
    var $document   = $(document);
    var selector    = '[data-rangeslider]';
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
    };
    // Update value output
    $document.on('input', selector, function(e) {
        valueOutput(e.target);
    });
    // Initialize the elements
    $inputRange.rangeslider({
        polyfill: false
    });
    // Example functionality to demonstrate programmatic value changes
    $document.on('click', '#Slider1 button', function(e) {
        var $inputRange = $('input[type="range"]', e.target.parentNode);
        var value = $('input[type="number"]', e.target.parentNode)[0].value;
        $inputRange
            .val(value)
            .change();
    });
    // Example functionality to demonstrate programmatic attribute changes
    $document.on('click', '#js-example-change-attributes button', function(e) {
        var $inputRange = $('input[type="range"]', e.target.parentNode);
        var attributes = {
            min: $('input[name="min"]', e.target.parentNode)[0].value,
            max: $('input[name="max"]', e.target.parentNode)[0].value,
            step: $('input[name="step"]', e.target.parentNode)[0].value
        };
        $inputRange
            .attr(attributes)
            .rangeslider('update', true);
    });
}




var deviceid = "<s:property value='deviceid'/>";
var system = "";

function setSystem(stm) {
    console.log('android parameter：'+ stm);
    system = stm;
}

var regRule = /\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g;

var lock = '<s:property value="isExit"/>' ;
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
	initneo();
	initrange();
	inhomenotalarm();
});
function inhomenotalarm(){
	if(1==<s:property value="inhomenotalarm"/>){
		$("#inhomenotalarm").prop("checked","checked");
	}
}
function initneo(){
	
	if(<s:property value="sensitivity"/>!=0){
		$('#Slider1').val(<s:property value="sensitivity"/>);
	}
	if(<s:property value="silentseconds"/>!=0){
		//document.getElementById("Slider2").value=233;
		$('#Slider2').val(<s:property value="silentseconds"/>);	
	}
	if('<s:property value="sensitivitytime"/>'!=''){
		document.getElementById("senrecordtime").innerHTML='<s:property value="sensitivitytime"/>';
		document.getElementById("senrecordvalue").innerHTML='<s:text name="read"/>:'+$('#Slider1').val();
	}else{
		document.getElementById("senrecordtime").innerHTML='<s:text name="noreadrecord"/>';
		document.getElementById("senrecordvalue").innerHTML='';
	}
	if('<s:property value="silentsecondstime"/>'!=''){
		document.getElementById("senrecordtime2").innerHTML='<s:property value="silentsecondstime"/>';
		document.getElementById("senrecordvalue2").innerHTML='<s:text name="read"/>:'+$('#Slider2').val();
	}else{
		document.getElementById("senrecordtime2").innerHTML='<s:text name="noreadrecord"/>';
		document.getElementById("senrecordvalue2").innerHTML='';
	}
	/* document.getElementById("oo").innerHTML=$('#Slider1').val();
	document.getElementById("hh").innerHTML=$('#Slider2').val(); */
	/* $('#Slider1').change(function(){  
        document.getElementById("oo").innerHTML=this.value;
    }); 
	$('#Slider2').change(function(){  
        document.getElementById("hh").innerHTML=this.value;
    });  */
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
function setandget(o,b){
	var zwavedeviceid = $("#zwavedeviceid").val();
	var sensitivity = $('#Slider1').val();
	var silentseconds = $('#Slider2').val();
	var data = "";
	if(o=="set"&&b=="1"){
		data = {"deviceid":deviceid,"zwavedeviceid":zwavedeviceid,"hope":"set","order":"1","sensitivity":sensitivity}; 
	}
	if(o=="get"&&b=="1"){
		data = {"deviceid":deviceid,"zwavedeviceid":zwavedeviceid,"hope":"get","order":"1"}; 
	}
	if(o=="set"&&b=="2"){
		data = {"deviceid":deviceid,"zwavedeviceid":zwavedeviceid,"hope":"set","order":"2","silentseconds":silentseconds}; 
	}
	if(o=="get"&&b=="2"){
		data = {"deviceid":deviceid,"zwavedeviceid":zwavedeviceid,"hope":"get","order":"2"}; 
	}
	$.ajax({
        type: "post",
        url:"/iremote/device/setandgetneostatus",
        data:data,
        async: false,
        success : function(data) {
        	if(data.resultCode==0){
        		toast('<s:text name="wakeupneo" />');
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
function myreload(){
	window.location.reload();
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
				
			</td> -->
			<td align="left" colspan="4">  
				<div>
					<input type="checkbox" name="inhomenotalarm" id="inhomenotalarm" value="1" style="zoom:180%;vertical-align: -5px;" >
					<%-- <span class="content_font"> --%><s:text name="inhomenotalarm" /> <!-- no alarm even setarm at home --><%-- </span> --%>
				</div>
			</td> 
		</tr>
	</table> 
	<table  width="100%">
		<tr>
			<td  width="15%" align="center"><s:text name="sensitivity" />:</td>
			<td colspan="3" align="center" width="35%"><span id="oo"></span></td>
			<td width="15%" align="center"><input type="button" style="width:55px" value='<s:text name="set" />' onclick="setandget('set','1')"/></td>
		</tr>
		<tr>	
			<td align="center" colspan="5">
			<output></output>
	        <input id="Slider1" name="sensitivity" type="range" min="8" max="255" value="12" data-rangeslider/>
			</td>
		</tr>
		<tr>	
			<td align="right"><s:text name="high" /></td>
			<td align="center" colspan="3">
			</td>
			<td align="left"><s:text name="low" /></td>
		</tr>
		<tr>
			<td id="senrecordvalue" width="15%" align="center"></td>
			<td colspan="3" width="35%" align="center" id="senrecordtime"></td>
			<td width="15%" align="center"><input type="button" style="width:55px" value='<s:text name="read" />' onclick="setandget('get','1')"/></td>
		</tr>
		<tr><td colspan="5"><br/></td></tr>
		<tr>
			<td width="15%" align="center"><s:text name="silentseconds" />:</td>
			<td colspan="3" width="35%" align="center"><span id="hh"></span></td>
			<td width="15%" align="center"><input type="button" style="width:55px" value='<s:text name="set" />' onclick="setandget('set','2')"/></td>
		</tr>
		<tr>
			<td align="center" colspan="5">
			<output></output><s:text name="seconds" />
			<input id="Slider2" name="silentseconds" type="range" min="10" max="600" value="30" data-rangeslider/></td>
		</tr>
		<tr height="15px"><td colspan="5"></td></tr>
		<tr>
			<td id="senrecordvalue2" width="15%" align="center"></td>
			<td width="35%" align="center" colspan="3"  id="senrecordtime2"></td>
			<td width="15%" align="center"><input type="button" style="width:55px" value='<s:text name="read" />' onclick="setandget('get','2')"/></td>
		</tr>
		<tr><td colspan="5"><br/></td></tr>
		<tr><td colspan="5" align="center"><input type="button" value='<s:text name="refresh" />' onclick="myreload()"/></td></tr>
		<tr><td colspan="5" align="center" ><p><s:text name="neotips"/></p></td></tr>
	</table>
</form>
	
</body>
</html>
