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

function onback()
{
	JSInterface.back();
}

$(document).ready(function(){
});


function onSubmit(){
	if(!validateZwaveName($("#name"))){
		return;
	};
	var array=$("input[name='switchsNames']");
	var length=$('#channelnumber option:selected').val();
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
        url:"/iremote/device/addDevice2Action",
        data:$('#formSumbit').serialize(),
        success : function(data) 
        {
        	if ( data.resultCode == 0 )
        	{
        		window.location.href='<%=request.getContextPath()%>/webconsole/listappliance?deviceid=' + deviceid;
        	}
        	else if ( data.resultCode == 30316)
        		toast('ID crash');
        	else if ( data.resultCode == 10006)
        		toast('Time out');
        	else if ( data.resultCode == 30312)
        		toast('Gateway offline');
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
			<td align="center"><span class="title_c">Create Device</span></td>
			<td align="right" width="40px" ><a href="javascript:onSubmit(this);"><img src="/iremote/images/nav/nav_btn_preservation.png"  class="nav_img"/></a></td>
		</tr>
	</table>
	
<form action="/iremote/device/setdeivityname" id="formSumbit" method="post">
	<input type="hidden" name="deviceid" id="deviceid" value="<s:property value='deviceid'/>"/>
	<input type="hidden" name="devicetype" id="devicetype" value="<s:property value='devicetype'/>"/>

	<table width="100%" style="background:#ffffff;margin-top:5px;">	
	     
      	<tr height="55px">
			<td align="right" width="5px">&nbsp;</td>
			<td align="left" nowrap="nowrap" width="5%">
				<span class="content_font">ID:</span>
			</td>
			<td align="left" >  
				<div>
					<select id="nuid" name="nuid">
						<s:iterator value="nuids" id="nid">
							<option value='<s:property value="#nid.key"/>' ><s:property value="#nid.value"/></option>
						</s:iterator>
					</select>
				</div>
			</td> 
		</tr>
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
</tbody>
	</table>   

</form>
	
</body>
</html>