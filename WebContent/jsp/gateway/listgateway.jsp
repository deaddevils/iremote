<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="gateway" /></title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/iremote/js/jquery-confirm.js"></script>
<link type="text/css" rel="stylesheet" href="/iremote/css/jquery-confirm.css">
<link type="text/css" rel="stylesheet" href="/iremote/css/jquery-confirm-center.css">
<link type="text/css" rel="stylesheet" href="/iremote/css/iremotewebstyle.css" charset="utf-8"/>
<script type="text/javascript">
function ondelete(deviceid)
{
	$.confirm({
	    title: '',
	    content: '<s:text name="confirmdeletegateway"/>'+deviceid+'?',
	    buttons: {
	    	<s:text name="ok"/>: function () {
	    		var url = "/iremote/webconsole/deletegatewayowner?deviceid=" + deviceid;
	    		window.location.href=url;	        },
	        <s:text name="cancel"/>: function () {
	           
	        }
	    },
	    theme: 'center'
	});
	

}
function modifyZwavedevice(deviceid,zawavedeviceid)
{
	var url = "/iremote/device/setdeivitynamepage?deviceid="+deviceid+"&zwavedeviceid=" + zawavedeviceid;
	window.location.href=url;
}
function toAirAdd(deviceid ){
	var url = "/iremote/airconditioner/airconditionerpage?deviceid=" + deviceid +"&capabilitycode=10028";
	window.location.href=url;
}

function toAddDehumidify(deviceid)
{
	var url = "/iremote/airconditioner/airconditionerpage?deviceid=" + deviceid +"&capabilitycode=10036";
	window.location.href=url;
}

function toAddFreshAir(deviceid)
{
	var url = "/iremote/airconditioner/airconditionerpage?deviceid=" + deviceid +"&capabilitycode=10038";
	window.location.href=url;
}

function toAddDsc(deviceid)
{
	var url = "/iremote/webconsole/createdscdevicepage?deviceid=" + deviceid;
	window.location.href=url;
}

function toDeviceidAdd(deviceid){
	var url = "/iremote/webconsole/setgatewayownerpage";
	window.location.href=url;
}

function listAppliance(deviceid)
{
	var url = "/iremote/webconsole/listappliance?deviceid=" + deviceid;
	window.location.href=url;
}

function editgateway(deviceid)
{
	var url = "/iremote/gateway/editgatewaypage?deviceid=" + deviceid;
	window.location.href=url;
}
function logout(){
	var url ="/iremote/webconsole/logout";
	window.location.href=url;
}
</script>
<body style="background-color:#f4f4f4;margin:0px;padding:0px">
	<table style="background-color:#4fc1e9" width="100%" height="50px">
		<tr >
			<td align="left" width="30%" ></td>
			<td align="center"><span ><s:text name="gateway" /> </span></td>
			<td align=right width="30%"><a href="javascript:logout(0);" style="text-decoration:none;"><s:text name="logout"/></a>&nbsp;&nbsp;</td>
		</tr>
	</table>
&nbsp;<p>
<input type="hidden" name="deviceid" id="deviceid" value=""/>
<center>
<table width="70%" border="1">
		<tr>
			<td align="center"><s:text name="gatewayname"/></td>
			<td align="center"><s:text name="gatewayid"/></td>  
			<td align="center"><s:text name="operate"/></td>
		</tr>
	<s:iterator value="remote" id="r">
		<tr>
			<td><s:property value='#r.name'/></td>
			<td><s:property value='#r.deviceid'/></td>
			<td>
				<button onclick="editgateway('<s:property value='#r.deviceid'/>')"><s:text name='modify'/></button>
				<button onclick="ondelete('<s:property value='#r.deviceid'/>')"><s:text name='delete' /></button>
				<s:if test="#r.airconditionGateway == true">
					<button onclick="toAirAdd('<s:property value='#r.deviceid'/>')"><s:text name="addac" /></button>
				</s:if>
				<s:if test="#r.dehumidityGateway == true">
					<button onclick="toAddDehumidify('<s:property value='#r.deviceid'/>')"><s:text name="adddehumidificationdevice" /></button>
				</s:if>
				<s:if test="#r.freshAirGateway == true">
					<button onclick="toAddFreshAir('<s:property value='#r.deviceid'/>')"><s:text name="addventilationdevice" /></button>
				</s:if>
				<s:if test="#r.dscGateway == true && #r.isexistdsc == false">
					<button onclick="toAddDsc('<s:property value='#r.deviceid'/>')"><s:text name='addDSC'/></button>
				</s:if>
				<s:elseif test="#r.dscGateway == true && #r.isexistdsc == true">
					<button onclick="modifyZwavedevice('<s:property value='#r.deviceid'/>','<s:property value='#r.zwavedeviceid'/>')"><s:text name='modifydsc'/></button>
				</s:elseif>

				<button onclick="listAppliance('<s:property value='#r.deviceid'/>')"><s:text name='device'/></button>
			</td>
		</tr>
	</s:iterator>	
	<s:if test="resultCode != null">
		<s:if test="resultCode != 0">
			<tr>
				<td align="center" colspan="3"><s:text name="errmsg_errcode" />：<s:property value='resultCode'/></td>
			</tr> 
		</s:if>
	</s:if>
</table>
<br></br>

		<button onclick="toDeviceidAdd()"><s:text name='addgateway' /></button>
</center>
</body>
</html>