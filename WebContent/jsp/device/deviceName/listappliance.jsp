<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="gateway" /></title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/iremote/js/jquery.toast.js"></script>
<script type="text/javascript" src="/iremote/js/iremote.js"></script>
<script type="text/javascript" src="/iremote/jsp/device/deviceName/mockJsInterface.js"></script>
<link type="text/css" rel="stylesheet" href="/iremote/css/iremotewebstyle.css" charset="utf-8" />
<link rel="stylesheet" type="text/css" href="/iremote/css/jquery.toast.css" />
<script type="text/javascript">
function modifyZwavedevice(zawavedeviceid)
{
	var url = "/iremote/device/setdeivitynamepage?deviceid=<s:property value='deviceid'/>&zwavedeviceid=" + zawavedeviceid;
	window.location.href=url;
}

function deleteZwavedevice(zawavedeviceid)
{
	$.ajax({
        type: "post",
        url:"/iremote/device/deletezwavedevice?deviceid=<s:property value='deviceid'/>&zwavedeviceid=" + zawavedeviceid,
        success : function(data) 
        {
        	if ( data.resultCode == 0 )
        	{
        		window.location.href='<%=request.getContextPath()%>/webconsole/listappliance?deviceid=<s:property value="deviceid"/>';
        	}
        },
        error : function(data) {
        	toast('<s:text name="errorraise" />');
        }
	})

}

function modifyInfrareddevice(infrareddeviceid)
{
	var url = "/iremote/device/setdeivitynamepage?deviceid=<s:property value='deviceid'/>&infrareddeviceid=" + infrareddeviceid;
	window.location.href=url;
	
}

function modifyCamera(cameraid)
{
	var url = "/iremote/device/setdeivitynamepage?deviceid=<s:property value='deviceid'/>&cameraid=" + cameraid;
	window.location.href=url;
}

function onback()
{
	var url = "/iremote/webconsole/listgateway.action";
	window.location.href=url;
}

function toAddDsc(deviceid)
{
	var url = "/iremote/webconsole/createdscdevicepage?deviceid=" + "<s:property value='deviceid'/>";
	window.location.href=url;
}
function hideadddscbutton(){
	$("#adddsctr").hide();
}
function toAddDevice(deviceid,type)
{
    var url = "/iremote/webconsole/createdevicepage?deviceid=" + "<s:property value='deviceid'/>" + "&&devicetype=" + type;
    window.location.href=url;
}
function sysdevice(){
    var deviceid = "<s:property value='deviceid'/>";
	 $.ajax({
			type:"post",
			url:"/iremote/device/addDevice2Remote",
			dataType: "json",
			data: {"deviceid":deviceid},
			success:function(data){
				if (data.resultCode == 0) {
					toast('<s:text name="operation_success" />');
				} else if (data.resultCode == 30313) {
					toast('<s:text name="wakeupdevice" />');
				} else if (data.resultCode == 10020) {
					toast('<s:text name="wakeupgateway" />');
				} else if (data.resultCode == 10006) {
					toast('<s:text name="timeout" />');
				} else if (data.resultCode == 30312) {
					toast('<s:text name="gatewayoffline" />');
				} else if (data.resultCode == 10023) {
					toast('<s:text name="targetnotexist" />');
				} else if (data.resultCode == 30021) {
					toast('<s:text name="unsupportrequest" />');
				} else {
					toast(data.resultCode);
				}
			},
			error:function(error) {
				alert("ajax error!")
                console.log(error);          
            },
        });
}
</script>
<body style="background-color:#f4f4f4;margin:0px;padding:0px">
	<table style="background-color:#4fc1e9" width="100%" height="50px">
		<tr >
			<td align="left" width="40px"><a href="javascript:onback(0);"><img src="/iremote/images/nav/nav_btn_back.png"  class="nav_img"/></a></td>
			<td align="center"><span ><s:text name="device"/></span></td>
			<td width="40px">&nbsp;</td>
		</tr>
	</table>
&nbsp;<p>
<center>
<table width="70%" border="1">
		<tr>
			<td align="center"><s:text name="deviceid"/></td>  
			<td align="center"><s:text name="name"/></td>
			<td align="center"><s:text name="type"/></td>
			<td align="center"><s:text name="operate"/></td>
		</tr>
	<s:iterator value="appliance" id="ap">
		<tr>
			<td>
				<s:if test="#ap.zwavedeviceid != null">
					<s:property value='#ap.zwavedeviceid'/>
				</s:if>
				<s:if test="#ap.infrareddeviceid != null">
					<s:property value='#ap.infrareddeviceid'/>
				</s:if>
				<s:if test="#ap.cameraid != null">
					<s:property value='#ap.cameraid'/>
				</s:if>
			</td>
			<td><s:property value='#ap.name'/></td>
			<td>
				<s:if test="#ap.zwavedeviceid != null">
					<s:if test="#ap.devicetype == 1"><s:text name="devicetype1"/></s:if>
					<s:if test="#ap.devicetype == 2"><s:text name="devicetype2"/></s:if>
					<s:if test="#ap.devicetype == 3"><s:text name="devicetype3"/></s:if>
					<s:if test="#ap.devicetype == 4"><s:text name="devicetype4"/></s:if>
					<s:if test="#ap.devicetype == 5"><s:text name="devicetype5"/></s:if>
					<s:if test="#ap.devicetype == 6"><s:text name="devicetype6"/></s:if>
					<s:if test="#ap.devicetype == 7"><s:text name="devicetype7"/></s:if>
					<s:if test="#ap.devicetype == 8"><s:text name="devicetype8"/></s:if>
					<s:if test="#ap.devicetype == 9"><s:text name="devicetype9"/></s:if>
					<s:if test="#ap.devicetype == '10'"><s:text name="devicetype10"/></s:if>
					<s:if test="#ap.devicetype == '11'"><s:text name="devicetype11"/></s:if>
					<s:if test="#ap.devicetype == '12'"><s:text name="devicetype12"/></s:if>
					<s:if test="#ap.devicetype == '13'"><s:text name="devicetype13"/></s:if>
					<s:if test="#ap.devicetype == '14'"><s:text name="devicetype14"/></s:if>
					<s:if test="#ap.devicetype == '15'"><s:text name="devicetype15"/></s:if>
					<s:if test="#ap.devicetype == '16'"><s:text name="devicetype16"/></s:if>
					<s:if test="#ap.devicetype == '17'"><s:text name="devicetype17"/></s:if>
					<s:if test="#ap.devicetype == '18'"><s:text name="devicetype18"/></s:if>
					<s:if test="#ap.devicetype == '19'"><s:text name="devicetype19"/></s:if>
					<s:if test="#ap.devicetype == '20'"><s:text name="devicetype20"/></s:if>
					<s:if test="#ap.devicetype == '21'">----</s:if>
					<s:if test="#ap.devicetype == '22'"><s:text name="devicetype22"/></s:if>
					<s:if test="#ap.devicetype == '23'"><s:text name="devicetype23"/></s:if>
					<s:if test="#ap.devicetype == '24'"><s:text name="devicetype24"/></s:if>
					<s:if test="#ap.devicetype == '25'"><s:text name="devicetype25"/></s:if>
					<s:if test="#ap.devicetype == '26'"><s:text name="devicetype26"/></s:if>
					<s:if test="#ap.devicetype == '27'"><s:text name="devicetype27"/></s:if>
					<s:if test="#ap.devicetype == '28'"><s:text name="devicetype28"/></s:if>
					<s:if test="#ap.devicetype == '29'"><s:text name="devicetype29"/></s:if>
					<s:if test="#ap.devicetype == '30'"><s:text name="devicetype30"/></s:if>
					<s:if test="#ap.devicetype == '31'"><s:text name="devicetype31"/></s:if>
					<s:if test="#ap.devicetype == '32'"><s:text name="devicetype32"/></s:if> 
					<s:if test="#ap.devicetype == '33'"><s:text name="devicetype33"/></s:if>
					<s:if test="#ap.devicetype == '34'"><s:text name="devicetype34"/></s:if>
					<s:if test="#ap.devicetype == '35'"><s:text name="devicetype35"/></s:if>
					<s:if test="#ap.devicetype == '36'"><s:text name="devicetype36"/></s:if>
					<s:if test="#ap.devicetype == '37'"><s:text name="devicetype37"/></s:if>
					<s:if test="#ap.devicetype == '38'"><s:text name="devicetype38"/></s:if>
					<s:if test="#ap.devicetype == '39'"><s:text name="devicetype39"/></s:if>
					<s:if test="#ap.devicetype == '40'"><s:text name="devicetype40"/></s:if>
					<s:if test="#ap.devicetype == '41'"><s:text name="devicetype41"/></s:if>
					<s:if test="#ap.devicetype == '42'"><s:text name="devicetype42"/></s:if>
					<s:if test="#ap.devicetype == '43'"><s:text name="devicetype43"/></s:if>
					<s:if test="#ap.devicetype == '44'"><s:text name="devicetype44"/></s:if>
					<s:if test="#ap.devicetype == '45'"><s:text name="devicetype45"/></s:if>
					<s:if test="#ap.devicetype == '46'"><s:text name="devicetype46"/></s:if>
					<s:if test="#ap.devicetype == '47'"><s:text name="devicetype47"/></s:if>
					<s:if test="#ap.devicetype == '48'"><s:text name="devicetype48"/></s:if>
					<s:if test="#ap.devicetype == '49'"><s:text name="devicetype49"/></s:if>
					<s:if test="#ap.devicetype == '50'"><s:text name="devicetype50"/></s:if>
					<s:if test="#ap.devicetype == '51'"><s:text name="devicetype51"/></s:if>
					<s:if test="#ap.devicetype == '52'"><s:text name="devicetype52"/></s:if>
					<s:if test="#ap.devicetype == '53'"><s:text name="devicetype53"/></s:if>
					<s:if test="#ap.devicetype == '54'"><s:text name="devicetype54"/></s:if>
					<s:if test="#ap.devicetype == '55'"><s:text name="devicetype55"/></s:if>
					<s:if test="#ap.devicetype == '56'"><s:text name="devicetype56"/></s:if>
					<s:if test="#ap.devicetype == '57'"><s:text name="devicetype57"/></s:if>
					<s:if test="#ap.devicetype == '61'"><s:text name="devicetype61"/></s:if>
					<s:if test="#ap.devicetype == '62'"><s:text name="devicetype62"/></s:if>
				</s:if>
				<s:if test="#ap.infrareddeviceid != null">
					<s:if test="#ap.devicetype == 'TV'">
						<s:text name="devicetypetv"/>
					</s:if>
					<s:if test="#ap.devicetype == 'AC'">
						<s:text name="devicetypeac"/>
					</s:if>
					<s:if test="#ap.devicetype == 'STB'">
						<s:text name="devicetypestb"/>
					</s:if>
				</s:if>
				<s:if test="#ap.cameraid != null">
					<s:text name="devicetypecamera"/>
				</s:if>
			</td>
			<td>
				<s:if test="#ap.zwavedeviceid != null">
					<button onclick="modifyZwavedevice('<s:property value='#ap.zwavedeviceid'/>')"><s:text name="modify"/></button>
				</s:if>
				<s:if test="#ap.infrareddeviceid != null">
					<button onclick="modifyInfrareddevice('<s:property value='infrareddeviceid'/>')"><s:text name="modify"/></button>
				</s:if>
				<s:if test="#ap.cameraid != null">
					<button onclick="modifyCamera('<s:property value='cameraid'/>')"><s:text name="modify"/></button>
				</s:if>
				<s:if test="#ap.zwavedeviceid != null && (#ap.devicetype == '47' || #ap.devicetype == '39')">
					<button onclick="deleteZwavedevice('<s:property value='#ap.zwavedeviceid'/>')"><s:text name="delete"/></button>
				</s:if>
			</td>
		</tr>
	</s:iterator>	
	<s:if test="resultCode != null">
		<s:if test="resultCode != 0">
			<tr>
				<td align="center" colspan="4"><s:text name="errmsg_errcode" />：<s:property value='resultCode'/></td>
			</tr> 
		</s:if>
	</s:if>
		<s:if test="gatewayCapabilities.size > 0">			
			<s:iterator value="gatewayCapabilities" id="gc">
				<s:if test="#gc.capabilitycode == 7">
					<tr id="adddsctr">
						<td align="center" colspan="4">
							<button  onclick="toAddDsc('<s:property value='#r.deviceid'/>')"><s:text name="addDSC"/></button>
						</td>
					</tr>
				</s:if>
				<s:if test="#gc.capabilitycode == 10039">
					<tr>
					<td align="center" colspan="4">
						<button onclick="toAddDevice('<s:property value='#r.deviceid'/>','39')"><s:text name="addbgm"/></button>
					</td>
					</tr>
				</s:if>
			</s:iterator>

		</s:if>
		<s:iterator value="appliance" id="ad">
		<s:if test="#ad.zwavedeviceid != null && #ad.devicetype == '47'">
		<script language="javascript">hideadddscbutton()</script>
		</s:if>
	</s:iterator>
</table>
	<br>
	<div align="center">
		<button onclick="sysdevice()"><s:text name="writeintogateway" /></button>
	</div>
	<br></br>
</center>
</body>
</html>