<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/language.jsp"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/iremote/css/iremote.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>

<title><s:text name="resetpassword_title" /></title>
</head>
<body>
<style type="text/css">
.text_left
{
	padding-left:0px;
}
.bottom_line{
	border-bottom:#999999 solid 1px;
}
</style>

<script type="text/javascript">
    function resetpassword(){
		var error = $("#error");
        error.text("");
        var newpassword = $("#newpassword").val();
        var comfirmpassword = $("#comfirmpassword").val();
        var mail = $("#mail").val();
        if (mail == null || mail == undefined || mail == ''){
            error.text(mail_str + " "+ not_null);
            return false;
        }
        if(!checkStr($("#passwordtip").text(),newpassword)
			|| !checkStr($("#comfirmpasswordtip").text(),comfirmpassword)){
            return;
		}
		if(newpassword != comfirmpassword){
            error.text(newpassword_notequest_confirmedpassword);
            return;
		}
        document.getElementById('resetpasswordfrom').submit();
    }

    function checkStr(tip,str) {
        if (str == null || str == undefined || str == ''){
            $("#error").text(tip + " "+ not_null);
            return false;
        }
        if(str.length < 8){
            $("#error").text(tip + " " + lack_of_length);
            return false;
        }
        var re = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,10}$/;
        var reg = new RegExp(re);
        if (!reg.test(str)) {
            $("#error").text(tip + " " + lack_of_length);
            return false;
        }
        return true;
    }

    function onback()
    {
        JSInterface.back();
    }
</script>
	<table width="100%">
		<tr>
			<td align="left" width="40px"><a href="javascript:onback(0);"><img src="/iremote/images/nav/nav_btn_back.png"  class="nav_img"/></a></td>
			<td align="center"><s:text name="resetpassword_title" /></td>
			<td align="right" width="40px" >&nbsp;</td>
		</tr>
	</table>
	
<form id="resetpasswordfrom" action="/iremote/mailuser/resetpasswordset" method="POST">
	<input type="hidden" id="platform" name="platform" value="<s:property value='platform'/>">
	<input type="hidden" id="mail" name="mail" value="<s:property value='mail'/>">
	<input type="hidden" id="randcode" name="randcode" value="<s:property value='randcode'/>">

	<table width="50%" border="0" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td align="right" class="text_left" id="passwordtip"><s:text name="resetpassword_newpassword" />:</td>
			<td align="left" class="bottom_line"><input name="newpassword" type="password" class="input_text" id="newpassword" maxlength="10"/></td>
		</tr>
		<tr>
			<td align="right" class="text_left" id="comfirmpasswordtip"><s:text name="resetpassword_comfirmpassword" />:</td>
			<td align="left" class="bottom_line"><input name="comfirmpassword" type="password" class="input_text" id="comfirmpassword" maxlength="10"/></td>
		</tr>
		<tr>
			<td colspan="2" height="50px" align="center"><spen id="error"></spen></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="button" class="btn_success" onclick="resetpassword()" name="OK" value="OK"/></td>
		</tr>
	</table>
</form>
</body>
</html>