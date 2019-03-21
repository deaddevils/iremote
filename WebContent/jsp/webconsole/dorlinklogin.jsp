<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name='login'/></title>
</head>
<body>
<form action="/iremote/webconsole/login" method="POST">
<input type="hidden" name="platform" value="3"/>
<table width="100%" style="margin-top:50px">
	<tr>
		<td align="right" width="40%"><s:text name="account"/>:</td>
		<td align="left" width="60%"><input name="phonenumber"/></td>
	</tr>
	<tr>
		<td align="right" width="40%"><s:text name="password"/>:</td>
		<td align="left" width="60%"><input type="password" name="password"/></td>
	</tr>
	<s:if test="resultCode != null">
		<s:if test="resultCode != 0">
			<tr>
				<td align="right" width="40%"><s:text name='loginfailed'/>:</td>
				<td align="left" width="60%"><s:property value='resultCode'/></td>
			</tr>
		</s:if>
	</s:if>
	<tr>
		<td colspan="2" align="center"><input type="submit" name="OK" value="<s:text name='login'/>"/></td>
	</tr>
	
</table>
</form>
</body>
</html>