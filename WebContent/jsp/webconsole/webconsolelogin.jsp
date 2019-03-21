<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>

<title><s:text name="login" /></title>
</head>
<body>

<script type="text/javascript">
	$(function(){
	    var type = $("#login_type option:selected").val();
        loginChange(type);
	})

	function loginChange(type) {
		if(type == 0){
			$("#mail_login").hide();
            $("#maildiv").hide();
            $("#phone_login").show();
        }else if(type == 1){
            $("#phone_login").hide();
            $("#mail_login").show();
            $("#maildiv").show();
        }
    }

    function login() {
        var type = $("#login_type").val();
        var phoneuser = $("#phoneuser").val();
        var mailuser = $("#mailuser").val();
    	
		if(type == 1){
			if(mailuser==""||mailuser==null){
	    		return;
	    	}
			$("#loginform").attr('action','/iremote/webconsole/maillogin');
            $("#loginform").submit();
		}else {
			if(phoneuser==""||phoneuser==null){
	    		return;
	    	}
			$("#loginform").attr('action','/iremote/webconsole/login');
			$("#loginform").submit();
		}
    }
</script>
<form id="loginform" action="/iremote/webconsole/login" method="POST">
<table width="50%" align="center">
	<tr>
		<td align="right" width="50%"><s:text name="loginmode"/>:</td>
		<td align="left" width="50%">
			<select id="login_type" onchange="loginChange($(this).val());" style="width:200px;">
				<option value ="0" selected><s:text name="byPhone"/></option>
				<option value ="1"><s:text name="byEmail"/></option>
			</select>
		</td>
	</tr>
	<tr id="phone_login" >
		<td align="right" width="50%"><s:text name="account"/>:</td>
		<td align="left" width="50%"><input name="phonenumber" id="phoneuser" style="width:200px;"/></td>
	</tr>
	<tr id="mail_login">
		<td align="right" width="50%"><s:text name="email"/>:</td>
		<td align="left" width="50%"><input name="mail" id="mailuser" style="width:200px;"/></td>
	</tr>
	<tr>
		<td align="right" width="50%"><s:text name="password"/>:</td>
		<td align="left" width="50%"><input type="password" name="password" style="width:200px;"/></td>
	</tr>
	<tr>
		<td align="right" width="50%"><s:text name="vendorcode"/>:</td>
		<td align="left" width="50%"><input style="width:200px;" name="platform" placeholder="0:<s:text name='isurpass'/>,3:<s:text name='dorlink'/>,6:<s:text name='itiger'/>" /></td>
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
				<a href="/iremote/mailuser/mailregist" style="text-decoration:none;">&nbsp<s:text name='emailregist'/>&nbsp</a>
				<a href="/iremote/mailuser/mailresetpassword" style="text-decoration:none;">&nbsp<s:text name='forgotpassword'/>&nbsp</a>
			</div>
		</td>
	</tr>
	
</table>
</form>
</body>
</html>