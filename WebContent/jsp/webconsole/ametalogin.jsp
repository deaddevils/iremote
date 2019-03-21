<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<title><s:text name="login" /></title>
</head>
<body style="background-color:#4FC0E8;">

<script type="text/javascript">
	$(function(){
	    var type = $("#login_type option:selected").val();
        loginChange(type);
	})

	function loginChange(type) {

            $("#phone_login").hide();
            $("#mail_login").show();
            $("#maildiv").show();
    }

    function login() {
    	var emailuser = $(".phoneuser").val();
    	if(emailuser==""||emailuser==null){
    		return;
    	}
        $("#loginform").submit();
    }
	
$(document).ready(function()
{
	loginChange(1)
});
</script>
<form id="loginform" action="/iremote/webconsole/maillogin" method="POST">
<input type="hidden" name="platform" value="9"/>
<input type="hidden" id="login_type" value="1"/>

<table width="50%" align="center">
	<tr id="centertitle" >
		<td align="center" colspan="2" width="50%"><h1>Ai Security APP Login</h1></td>
	</tr>
	<tr id="mail_login">
		<td align="right" width="40%"><s:text name="email"/>:</td>
		<td align="left" width="60%"><input name="mail" class="phoneuser" style="width:200px;"/></td>
	</tr>
	<tr>
		<td align="right" width="40%"><s:text name="password"/>:</td>
		<td align="left" width="60%"><input type="password" name="password" style="width:200px;"/></td>
	</tr>
	<s:if test="resultCode != null">
		<s:if test="resultCode != 0">
			<tr>
				<td align="right" width="40%"><s:text name='loginfailed'/>:</td>
				<td align="left" width="60%"><%-- <s:property value='resultCode'/> --%>
				<s:if test='resultCode == 10016'><s:text name='10016'/></s:if>
				<s:elseif test='resultCode == 10010'><s:text name='10010'/></s:elseif>
				<s:elseif test='resultCode == 10011'><s:text name='10011'/></s:elseif>
				<s:else><s:text name='error'/></s:else>
				</td>
			</tr>
		</s:if>
	</s:if>
	<tr>
		<td colspan="2" align="center">
			<input type="button" onclick="login()" value="<s:text name='ok'/>" style="width:100px;"/>
			<div id="maildiv">
				
			</div>
		</td>
	</tr>
	
</table>
</form>
</body>
</html>