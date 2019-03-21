<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.iremote.domain.ZWaveDevice,java.util.*" pageEncoding="UTF-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
	<title><s:text name="creategatewayqrcode" /></title>
	<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>

</head>
<body style="background-color:#f4f4f4;margin:0px;padding:0px">
<form action="/iremote/webconsole/creategatewayqrcode" method="POST">
	<table style="background-color:#4fc1e9" width="100%" height="50px">
		<tr >
			<td align="left" width="30%" ></td>
			<td align="center"><span ><s:text name="creategatewayqrcode" /></span></td>
			<td width="30%"></td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<td align="right" colspan=2><a href="/iremote/webconsole/listgateway"><s:text name="gateway" /></a>&nbsp;
			<a href="/iremote/webconsole/setgatewayownerpage"><s:text name="addgateway" /></a></td>
		</tr>
	</table>
	<table align="center">
		<tr>
			<td><s:text name="gatewayid" />：</td>
			<td>
				<input name="deviceid" maxlength="64" value="<s:property value='deviceid'/>" />
			</td>
			<td><s:text name="gatewaytype" />：</td>
			<td>
				<select name="gatewaytype">
					<option value="gateway"><s:text name="gateway" /></option>
					<option value="lockgateway">Lock</option>
					<option value="airqulitygateway"><s:text name="airbox" /></option>
					<option value="nblockgateway">NB</option>
					<option value="fingerprintreader">Fingerprint Reader</option>
				</select>
			</td>
			<td>
				<input type="checkbox" name="saveindb" value="true" maxlength="64" /><s:text name="writeintodatabase" />&nbsp;&nbsp;
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><s:text name="instruction" />：</td>
			<td colspan="4">
				<input name="description" size="64" />
			</td>
			<td>
				<button type="submit" ><s:text name="submit" /></button>
			</td>
		</tr>
	</table>
	
<s:if test="deviceid != ''">
	<table align="center">
		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
	<s:if test="resultCode == 0 ">
			<tr>
				<td><s:text name="gatewayid" />：</td>
				<td>
					<s:property value='deviceid'/>
				</td>
				<td rowspan="3"> <img alt="" width="100px" height="100px" src="/iremote/webconsole/gatewayqrcode?qrcodestring=<s:property value='encodeqrstring'/>&filename=<s:property value='filename'/>"> </td>
			</tr>
			<tr>
				<td><s:text name="gatewaytype" />：</td>
				<td>
					<s:if test="gatewaytype == 'gateway'"><s:text name="gateway" /></s:if>
					<s:if test="gatewaytype == 'lockgateway'">Lock</s:if>
					<s:if test="gatewaytype == 'airqulitygateway'"><s:text name="airbox" /></s:if>
					<s:if test="gatewaytype == 'nblockgateway'">NB</s:if>
				</td>
			</tr>
			<tr>
				<td><s:text name="qrcodeid" />：</td>
				<td>
					<s:if test="exists">(<s:text name="alreadexist" />)</s:if>
					<s:property value='qid'/>
				</td>
			</tr>
			<tr>
				<td><s:text name="qrcodestring" />：</td>
				<td colspan="3">
					<s:property value='qrstring'/>
				</td>
			</tr>
			<tr>
				<td>SQL：</td>
				<td colspan="3">
					<s:if test="exists == false ">
						<s:if test="saveindb">(<s:text name="alreadwirteintodatabase" />)</s:if>
						<s:else>(<s:text name="notwriteintodatabase" />)</s:else>
					</s:if>
					<s:property value='qrsql'/>
				</td>
			</tr>
		</s:if>
		<s:else>
			<tr>
				<td><s:text name="errorcode" />：</td>
				<td>
					<s:property value='resultCode'/>
				</td>
			</tr>
		</s:else>
</table>
</s:if>
</form>
</body>
<script>


</script>
</html>