<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="addgateway" /></title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript">
	function addDevice(){
		var bc = true;
		bc = isNull($("#deviceid"),'<s:text name="gatewayidrequired" />');
		if(!bc)return;
		bc = isNull($("#key"),'<s:text name="checkcoderequired" />');
		if(!bc)return;
		bc = isNull($("#name"),'<s:text name="gatewaynamerequired" />');
		if(bc){
			$("#from").submit();
		}
	}
	
	function isNull(obj,name){
		if($(obj).val() == ""){
			alert(name);
			$(obj).focus(); 
			return false;
		}
		return true;
	}
	
</script>

<body style="background-color:#f4f4f4;margin:0px;padding:0px">
	<table style="background-color:#4fc1e9" width="100%" height="50px">
		<tr >
			<td align="left" width="30%" ></td>
			<td align="center"><span ><s:text name="gateway" /> </span></td>
			<td width="30%"></td>
		</tr>
	</table>
<form action="/iremote/webconsole/setgatewayowner" id="from" method="POST">
<table width="100%">
	<tr>
		<td align="right" colspan=3><a href="/iremote/webconsole/listgateway"><s:text name="gateway" /></a>&nbsp;<a href="/iremote/airconditioner/airconditionerpage"><s:text name="addac" /></a></td>
	</tr>
	<tr>
		<td align="right" width="40%"><s:text name="gatewayid" />:</td>
		<td align="left" width="60%"><input id="deviceid" name="deviceid" size="32"/></td> 
	</tr>
	<tr>
		<td align="right" width="40%"><s:text name="checkcode" />:</td>
		<td align="left" width="60%"><input id="key" name="key" size="32"/></td>
	</tr>
	<tr>
		<td align="right" width="40%"><s:text name="gatewayname" />:</td>
		<td align="left" width="60%"><input id="name" name="name" size="32"/></td>
	</tr>
	<tr>
		<td align="right" width="40%">wifi ssid:</td>
		<td align="left" width="60%"><input id="ssid" name="ssid" size="32"/></td>
	</tr>
	<s:if test="resultCode != null">
		<s:if test="resultCode != 0">
			<tr>
				<td align="right" width="40%"><s:text name="operationfailed" />,</td>
				<td align="left" width="60%"><s:text name="errorcode" />：<s:property value='resultCode'/></td>
			</tr> 
		</s:if>
		<s:if test="resultCode == 0">
			<tr>
				<td align="center" colspan=2><s:text name="operatesuccessfully" /></td>
			</tr> 
		</s:if>
	</s:if>
	<tr>
		<td colspan="2" align="center"><input type="button" onclick="addDevice()" name="OK" value="OK"/></td>
	</tr>
	
</table>
</form>
</body>
</html>