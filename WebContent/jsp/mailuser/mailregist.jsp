<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/language.jsp"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/iremote/css/iremote.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>

<title><s:text name="mailregist_title" /></title>
</head>
<body>
<style type="text/css">
	.text_left
	{
		padding-left:20px;
	}
	.bottom_line{
		border-bottom:#999999 solid 1px;
	}
</style>
<script type="text/javascript">
    function regist(){
        var error = $("#error");
        var mail = $("#mail").val();
        var platform = $("#platform").val();
        var password = $("#password").val();
        if (mail == null || mail == undefined || mail == ''){
            error.text(mail_str + " "+ not_null);
            return false;
        }
        if (password == null || password == undefined || password == ''){
            error.text("密码"+ not_null);
            return false;
        }
        if (platform == null || platform == undefined || platform == ''){
            error.text(platform_str + " "+ not_null);
            return false;
        }
        error.text("正在发送邮件");
        $.ajax({
            type: "post",
            url:"/iremote/mailuser/register",
            data:$('#registerform').serialize(),
            success : function(data) {
                if(data.resultCode == 0){
                    error.text("邮件发送成功");
				}else{
                    error.text(getErrorMsg(data.resultCode));
                }
            },
            error : function(data) {
                error.val("请求异常");
            }
        })
    }
</script>
<form id="registerform" method="POST">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr id="mail_login">
		<td align="right" class="text_left bottom_line"><s:text name="email"/>:</td>
		<td align="left" class="bottom_line" ><input id="mail" name="mail" class="input_text"/></td>
	</tr>
	<tr>
		<td align="right" class="text_left bottom_line"><s:text name="password"/>:</td>
		<td align="left" class="bottom_line"><input id="password" type="password" class="input_text" name="password"/></td>
	</tr>
	<tr>
		<td align="right" class="text_left bottom_line"><s:text name="vendorcode"/>:</td>
		<td align="left" class="bottom_line"><input id="platform" name="platform" class="input_text" placeholder="0:<s:text name='isurpass'/>,3:<s:text name='dorlink'/>,6:<s:text name='itiger'/>" /></td>
	</tr>
	<tr>
		<td colspan="2" height="50px" align="center"><spen id="error"></spen></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input class="btn_success" type="button" onclick="regist()" name="OK" value="OK"/></td>
	</tr>
</table>
</form>
</body>
</html>