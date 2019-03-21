<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 

<title><s:text name="doorlockuser" /></title>
<link type="text/css" rel="stylesheet" href="/iremote/css/iremotewebstyle.css" charset="utf-8"/>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script>
var zwavedeviceid = <s:property value='zwavedeviceid'/>;

function onback()
{
	JSInterface.back();
}

var hassubmit = false ;

function onadd()
{
	var url = "/iremote/device/lock/addlockusermenu?zwavedeviceid=" + zwavedeviceid;
	window.location.href=url;
}

function ondelete(doorlockuserid)
{
	if ( hassubmit == true )
		return ;
	hassubmit = true;
	window.location.href = "/iremote/device/lock/deletelockuser?doorlockuserid=" + doorlockuserid + "&zwavedeviceid=" + zwavedeviceid;
}

function onedit(doorlockuserid){
	if ( hassubmit == true )
		return ;
	hassubmit = true;
	window.location.href = "/iremote/device/lock/showDoorlockUserAction?doorlockuserid=" + doorlockuserid + "&zwavedeviceid=" + zwavedeviceid;
}

function togglemanagebutton(userid , show)
{
	$('.'+userid+"_ctrl").each(function()
			{
				$(this).toggle();
			});
	
	$('.'+userid+"_edit").each(function()
			{
				$(this).toggleClass("visiblity_hidden");
			});
}


</script>
<style>
.visiblity_hidden
{
	display:none;
}
</style>
</head>
<body class="listpage">
<table class="title_table">
<tr >
	<td align="left" width="40px" ><a href="javascript:onback(0);"><img class="title_icon_back" src="/iremote/images/nav/nav_btn_back.png" class="nav_img"/></a></td>
	<td align="center"><div class="title_c" ><s:text name="doorlockusermanage"/></div></td>
	<td align="right" width="40px">
		<s:if test="supportAddUser == true">
			<a href="javascript:onadd(0);"><img src="/iremote/images/nav/nav_btn_add_to.png" class="nav_img"/></a>
		</s:if>
		<s:else>
			&nbsp;
		</s:else>
	</td>
	
</tr>
</table>

<table class="list_content_table" >
<s:if test="doorlockuserlst.size() > 0">
<s:iterator value="doorlockuserlst" id="h">
	<tr class="list_content_table_tr">
	<td width="100%">
		<table width="100%">
		<tr>
			<td width="5px">
						<s:if test="#h.usertype == 21"> 
							<img src="/iremote/images/icon/icon_password.png" class="list_icon">
						</s:if>
						<s:elseif test="#h.usertype == 22">   
							<img src="/iremote/images/icon/icon_fingerprint.png" class="list_icon">
						</s:elseif>
						<s:elseif test="#h.usertype == 32"> 
							<img src="/iremote/images/icon/icon_card.png"  class="list_icon">
						</s:elseif>
			</td>
			<td align="left">
					<div class="list_conent">
						<s:property value='#h.username'/>
					</div>  
			</td>
			<td nowrap="nowrap">
					<div style="float:right;">
						<a class="<s:property value='#h.doorlockuserid'/>_edit visiblity_hidden" href="javascript:onedit(<s:property value='#h.doorlockuserid'/>);" ><img src="/iremote/images/top/top_edit.png" class="list_function_icon" style="margin-right:15px;"/></a>
						<s:if test="supportAddUser == true">
							<a class="<s:property value='#h.doorlockuserid'/>_edit visiblity_hidden" href="javascript:ondelete(<s:property value='#h.doorlockuserid'/>);" ><img src="/iremote/images/top/top_delete.png" class="list_function_icon"  /></a>
						</s:if>
						<a class="<s:property value='#h.doorlockuserid'/>_ctrl" href="javascript:togglemanagebutton(<s:property value='#h.doorlockuserid'/> , false);" style="display:none"><img src="/iremote/images/list/list_btn_more.png" class="list_function_icon" /></a>
						<a class="<s:property value='#h.doorlockuserid'/>_ctrl" href="javascript:togglemanagebutton(<s:property value='#h.doorlockuserid'/> , true);"><img src="/iremote/images/list/list_back.png" class="list_function_icon" /></a>
					</div>
			</td>
		</tr>
		</table>
	</td>
</tr>
</s:iterator>
</s:if>
<s:else>
	<br><br>
	<tr bgcolor="#F4F4F4">
	<td width="20%"></td>
	<td align="center">
		<p style="color:#767676;"><s:text name="addlockuseronlockitself"/></p>
	</td>
	<td width="20%"></td>
</s:else>
</table>
</body>
</html>