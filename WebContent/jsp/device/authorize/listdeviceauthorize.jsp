<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/iremote/js/jquery-confirm.js"></script>
<title><s:text name="deviceprivielgegrant"/></title>
<link type="text/css" rel="stylesheet" href="/iremote/css/jquery-confirm.css">
<link type="text/css" rel="stylesheet" href="/iremote/css/jquery-confirm-center.css">
<link type="text/css" rel="stylesheet" href="/iremote/css/iremotewebstyle.css" charset="utf-8"/>
<script>
var zwavedeviceid = <s:property value='zwavedeviceid'/>;

function onback()
{
	JSInterface.back();
}

var hassubmit = false ;

function onadd()
{
	var url = "/iremote/device/deviceprivilegegrantpage?zwavedeviceid=" + zwavedeviceid;
	window.location.href=url;
}

function ondelete(shareid)
{
	if ( hassubmit == true )
		return ;
	
	$.confirm({
	    title: '',
	    content: '<s:text name="confirmdeleteprivilege"/>',
	    buttons: {
	    	<s:text name="ok"/>: function () {
	        	hassubmit = true;
	        	window.location.href = "/iremote/device/deletedeviceprivilege?zwavedeviceid=" +zwavedeviceid + "&zwavedeviceshareid=" + shareid ;
	        },
	        <s:text name="cancel"/>: function () {
	           
	        }
	    },
	    theme: 'center'
	});
}
</script>
</head>
<body class="listpage">
<table class="title_table">
<tr >
	<td align="left" width="40px" ><a href="javascript:onback(0);"><img class="title_icon_back nav_img" src="/iremote/images/nav/nav_btn_back.png"/></a></td>
	<td align="center"><span class="title_c"><s:text name="deviceprivielgegrant"/></span></td>
	<td align="right" width="40px"><a class="button" href="javascript:onadd(0);"><img src="/iremote/images/nav/nav_btn_add_to.png" class="nav_img"/></a></td>
</tr>
</table>
<s:if test="shares.size() > 0">
<s:iterator value="shares" id="h">
<div style="background-color:#ffffff;height:48px;">
	<div style="margin:5px 10px 0px 10px">
		<div style="float:left;margin-top:12px;">
			<s:text name="privilegegrantto"/>
			<s:if test="#h.validtype == 1"><span style="font:normal 12px arial, sans-serif;color:red">(<s:text name="onceonly"/>)</span></s:if>
			<s:if test="#h.validtype == 2"><span style="font:normal 12px arial, sans-serif;color:red">(<s:text name="onceonly"/>)</span></s:if>
		</div>
		<a href="javascript:ondelete(<s:property value='#h.id'/>);"><img src="/iremote/images/top/top_delete.png" style="float:right; height:32px; width:32px;margin-top:5px;"/></a>	
	</div>  
</div>
<div style="background-color:#ffffff;margin-top:2px;">
	<div style="margin:0px 10px 0px 10px;height:90px">
		<div style="height:30px;">  
			<div class="content_font content_wrap" style="float:left;width:48%;margin-top:10px"><s:text name="phonenumbershort"/>:&nbsp;<s:property value='#h.touser'/></div>
			<div class="content_font content_wrap" style="float:right;width:48%;margin-top:10px"><s:text name="usernameshort"/>:&nbsp;<s:property value='#h.username'/></div>
		</div>
		<div class="content_font" style="margin-top:10px"><s:text name="periodofvalidity"/>:</div>
		<div class="content_font content_wrap" style="width:100%;margin-buttom:10px"><s:date name="#h.validfrom" format="yyyy-MM-dd HH:mm"/> ~ <s:date name='#h.validthrough' format="yyyy-MM-dd HH:mm"/></div>
	</div>   
</div>  
</s:iterator>  
</s:if>
<s:else>
	<br><br>
<table width="100%">
	<tr bgcolor="#F4F4F4">
	<td width="20%">&nbsp;</td>
	<td align="center">
		<p style="color:#767676;"><s:text name="noprivilegegrantitemmessage"/></p>
	</td>
	<td width="20%">&nbsp;</td>
</table>
</s:else>
</body>
</html>