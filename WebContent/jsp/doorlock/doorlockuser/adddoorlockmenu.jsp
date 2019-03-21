<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<title><s:text name="usertype" /></title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<link type="text/css" rel="stylesheet" href="/iremote/css/iremotewebstyle.css" charset="utf-8"/>

<script>

var zwavedeviceid = <s:property value='zwavedeviceid'/>;

function onback()
{
	var url = "/iremote/device/lock/listlockuser?zwavedeviceid=" + zwavedeviceid;
	window.location.href=url;
}

var hassubmit = false ;

$(document).ready(function()
{
	$('.numbersOnly').keyup(function () {
	    if (this.value != this.value.replace(/[^0-9]/g, '')) {
	       this.value = this.value.replace(/[^0-9]/g, '');
	    }
	});
	
	$('.input_usertype').first().click();
	
});



function adddoorlockuser(capabilitycode)
{
	var url = "/iremote/device/lock/addlockuserpage?zwavedeviceid=" + zwavedeviceid + "&usertype=" + capabilitycode;;	
	window.location.href=url;
}

</script>
<style>

.td_menu
{
	text-align:center;
} 

</style>
</head>
<body class="listpage">
	<input type="hidden" name="zwavedeviceid" value="<s:property value='zwavedeviceid'/>"/>
	<input type="hidden" id="usertype" name="usertype" value="21"/>
	<table class="title_table">
		<tr >
			<td align="left" width="30%" ><a href="javascript:onback(0);"><img class="title_icon_back nav_img" src="/iremote/images/nav/nav_btn_back.png"/></a></td>
			<td align="center"><span class="title_c"><s:text name="usertype" /></span></td>
			<td width="30%"></td>
		</tr>
	</table>
	<table width="100%" class="list_content_table">
		<s:if test="supoprtPasswordUser == true">
			<tr class="list_content_table_tr" onclick="adddoorlockuser(21)">
				<td width="5px">
					<img src="/iremote/images/icon/icon_password.png"  class="list_icon">
				</td>
				<td align="left">
					<div class="list_conent"> 
						<s:text name="passworduser" />
					</div>
				</td>
				<td nowrap="nowrap" align="right">
					<img src="/iremote/images/list/list_btn_more.png" class="list_function_icon" />
				</td>
			</tr>
		</s:if>

		<s:if test="supoprtFingerprintUser == true">
			<tr class="list_content_table_tr" onclick="adddoorlockuser(22)">
				<td width="5px">
						<img src="/iremote/images/icon/icon_fingerprint.png"  class="list_icon">
				</td>
				<td align="left">
						<div class="list_conent"> 
							<s:text name="fingerprintuser" />
						</div>
				</td>
				<td nowrap="nowrap" align="right">
						<img src="/iremote/images/list/list_btn_more.png" class="list_function_icon" />
				</td>
			</tr>			
		</s:if>

		<s:if test="supoprtCardUser == true">
			<tr class="list_content_table_tr" onclick="adddoorlockuser(32)">
				<td width="5px">
								<img src="/iremote/images/icon/icon_card.png"  class="list_icon">
				</td>
				<td align="left">
						<div class="list_conent"> 
							<s:text name="carduser" />
						</div>
				</td>
				<td nowrap="nowrap" align="right">
						<img src="/iremote/images/list/list_btn_more.png" class="list_function_icon" />
				</td>
			</tr>			
		</s:if>
	</table>
</body>
</html>