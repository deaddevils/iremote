<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试控制台</title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript">
var data = [
{
	'title':'取设备列表',
	'url':'/iremote/device/querylogicdevicelist',
	'params':[]
},
{
	'title':'同步数据',
	'url':'/iremote/data/synchronize2',
	'params':[]
},
{
	'title':'设置用户姓名',
	'url':'/iremote/phoneuser/setusername',
	'params':[{'title':'姓名','name':'name'}]
},
{
	'title':'设置网关主人',
	'url':'/iremote/gateway/setgatewayowner',
	'params':[{'title':'网关ID','name':'deviceid'},
	          {'title':'校验串','name':'key'},]
},
{
	'title':'修改网关',
	'url':'/iremote/gateway/editgateway',
	'params':[{'title':'网关ID','name':'deviceid'},
	          {'title':'网关名称','name':'name'},]
},
{
	'title':'删除网关',
	'url':'/iremote/gateway/deletegateway',
	'params':[{'title':'网关ID','name':'deviceid'}]
},
{
	'title':'增加zWave设备',
	'url':'/iremote/device/addzwavedevice',
	'params':[{'title':'网关ID','name':'deviceid'},
	          {'title':'设备NUID','name':'nuid'},
	          {'title':'设备名称','name':'name'},
	          {'title':'设备类型','name':'devicetype'},
	          {'title':'设备附加信息','name':'appendmessage'},]
},
{
	'title':'删除zWave设备',
	'url':'/iremote/device/deletezwavedevice',
	'params':[{'title':'设备ID','name':'zwavedeviceid'}]
},
{
	'title':'增加红外设备',
	'url':'/iremote/device/addinfreraddevice',
	'params':[{'title':'网关ID','name':'deviceid'},
	          {'title':'设备名称','name':'name'},
	          {'title':'设备类型','name':'devicetype'},
	          {'title':'码库序号','name':'codeindex'},
	          {'title':'码库ID','name':'codeid'},
	          {'title':'红外码库','name':'codelibery'},
	          {'title':'厂商ID','name':'productorid'},
	          {'title':'遥控器型号','name':'controlmodeid'},]
},
{
	'title':'删除红外设备',
	'url':'/iremote/device/deleteinfreraddevice',
	'params':[{'title':'设备ID','name':'infrareddeviceid'}]
},
{
	'title':'下发命令',
	'url':'/iremote/device/command',
	'params':[{'title':'网关ID','name':'deviceid','placeholder':'可选'},
	          {'title':'设备ID','name':'zwavedeviceid','placeholder':'zwavedeviceid,与deviceid二选一填入即可'},
	          {'title':'设备命令','name':'zwavecommand','placeholder':'如:[0,71,0,4,0,0,43,193,0,72,0,1,0,0,79,0,1,4,0,70,0,3,98,1,1]'}]
},
{
	'title':'查询设备状态',
	'url':'/iremote/device/querydevicestatus',
	'params':[{'title':'网关ID','name':'deviceid'},
	          {'title':'设备ID','name':'nuid','placeholder':'nuid,可选'},
	          {'title':'设备ID','name':'zwavedeviceid','placeholder':'zwavedeviceid,可选'}]
},
{
	'title':'下发门锁密码',
	'url':'/iremote/device/setlockuserpassword',
	'params':[
			  {'title':'设备ID','name':'zwavedeviceid','placeholder':'zwavedeviceid'},
	          {'title':'用户ID','name':'usercode'},
	          {'title':'密码','name':'password'},
	          {'title':'有效期开始时间','name':'validfrom'},
	          {'title':'有效结束时间','name':'validthrough'},
	          {'title':'有效星期','name':'weekday'},
	          {'title':'有效星期开始时间','name':'starttime'},
	          {'title':'有效星期结束时间','name':'endtime'},
	          {'title':'同步发送','name':'asynch' , 'placeholder':'1：同步发送，0：异步发送'}]
},
{
	'title':'删除门锁密码',
	'url':'/iremote/device/deletelockuserpassword',
	'params':[
	          {'title':'用户ID','name':'usercode'},
	          {'title':'锁ID','name':'zwavedeviceid','placeholder':'zwavedeviceid'}]
},
{
	'title':'下发门锁管理员密码',
	'url':'/iremote/device/setlockadminpassword',
	'params':[
			  {'title':'设备ID','name':'zwavedeviceid','placeholder':'zwavedeviceid'},
	          {'title':'密码','name':'password'},
	          {'title':'有效期开始时间','name':'validfrom'},
	          {'title':'有效结束时间','name':'validthrough'},
	          {'title':'同步发送','name':'asynch' , 'placeholder':'1：同步发送，0：异步发送'}]
},
{
	'title':'下发门锁临时密码',
	'url':'/iremote/device/setlockpassword',
	'params':[
			  {'title':'设备ID','name':'zwavedeviceid','placeholder':'zwavedeviceid'},
	          {'title':'密码','name':'password'},
	          {'title':'有效期开始时间','name':'validfrom'},
	          {'title':'有效结束时间','name':'validthrough'},
	          {'title':'同步发送','name':'asynch' , 'placeholder':'1：同步发送，0：异步发送'}]
},
{
	'title':'删除门锁临时密码',
	'url':'/iremote/device/deletelockpassword',
	'params':[{'title':'锁ID','name':'zwavedeviceid','placeholder':'zwavedeviceid'}]
},
{
	'title':'设置指纹有效期',
	'url':'/iremote/device/setlockfingerprintuser',
	'params':[
			  {'title':'设备ID','name':'zwavedeviceid','placeholder':'zwavedeviceid'},
			  {'title':'用户ID','name':'usercode'},
	          {'title':'有效期开始时间','name':'validfrom'},
	          {'title':'有效结束时间','name':'validthrough'},
	          {'title':'同步发送','name':'asynch' , 'placeholder':'1：同步发送，0：异步发送'}]
},
{
	'title':'删除门锁指纹用户',
	'url':'/iremote/device/deletelockfingerprintuser',
	'params':[{'title':'锁ID','name':'zwavedeviceid','placeholder':'zwavedeviceid'},
	          {'title':'用户ID','name':'usercode'}]
},
{
	'title':'重置门锁',
	'url':'/iremote/device/resetdoorlock',
	'params':[{'title':'锁ID','name':'zwavedeviceid','placeholder':'zwavedeviceid'}]
},
{
	'title':'门锁用户',
	'url':'/iremote/device/lock/listlockuser',
	'target':'_blank',
	'params':[{'title':'锁ID','name':'zwavedeviceid','placeholder':'zwavedeviceid'}]
},
{
	'title':'门锁用户，下发卡号',
	'url':'/iremote/device/lock/addDoorlockCard',
	'params':[{'title':'设备ID','name':'zwavedeviceid','placeholder':'zwavedeviceid'},
	          {'title':'用户姓名','name':'cardname'},
	          {'title':'卡号','name':'cardinfo'},
	          {'title':'卡类型','name':'cardtype','placeholder':'1：MF卡，2：身份证，3：其他'},
	          {'title':'有效期开始时间','name':'validfrom'},
	          {'title':'有效期结束时间','name':'validthrough'}]
},
{
	'title':'删除门锁卡用户',
	'url':'/iremote/device/lock/deleteDoorlockCard',
	'params':[{'title':'设备ID','name':'zwavedeviceid','placeholder':'zwavedeviceid'},
	          {'title':'用户ID','name':'usercode'}]
},
{
	'title':'打开设备',
	'url':'/iremote/device/switchon',
	'params':[{'title':'设备ID','name':'zwavedeviceid','placeholder':'zwavedeviceid'}, 
	          {'title':'通道','name':'channel','placeholder':'可选'}]
},
{
	'title':'关闭设备',
	'url':'/iremote/device/switchoff',
	'params':[{'title':'设备ID','name':'zwavedeviceid','placeholder':'zwavedeviceid'}, 
	          {'title':'通道','name':'channel','placeholder':'可选'}]
},
{
	'title':'设置设备状态',
	'url':'/iremote/device/setdevicestatus',
	'params':[{'title':'设备ID','name':'zwavedeviceid','placeholder':'zwavedeviceid'},
	          {'title':'状体','name':'status','placeholder':'0~255'}]
},
{
	'title':'新建情景',
	'url':'/iremote/scene/addscene',
	'params':[{'title':'情景id','name':'sceneid'},
	          {'title':'情景名称','name':'name'},
	          {'title':'情景类型','name':'scenetype','placeholder':'1：情景,2：联动,3：定时'},
	          {'title':'情景图标','name':'icon'},
	          {'title':'触发设备','name':'associationlist'},
	          {'title':'定时设置','name':'timerlist'},
	          {'title':'情景命令','name':'commandlist'}]
},
{
	'title':'修改情景',
	'url':'/iremote/scene/editscene',
	'params':[{'title':'情景id','name':'scenedbid','placeholder':'scenedbid'},
	          {'title':'情景名称','name':'name'},
	          {'title':'情景图标','name':'icon'},
	          {'title':'触发设备','name':'associationlist'},
	          {'title':'定时设置','name':'timerlist'},
	          {'title':'情景命令','name':'commandlist'}]
},
{
	'title':'删除情景',
	'url':'/iremote/scene/deletescene',
	'params':[{'title':'情景id','name':'scenedbid','placeholder':'scenedbid'}]
},
{
	'title':'新增或修改多个情景',
	'url':'/iremote/scene/editmultiscene',
	'params':[{'title':'情景id','name':'scene','placeholder':'json'}]
},
{
	'title':'授权',
	'url':'/iremote/share/sharerequest',
	'params':[{'title':'国际区号','name':'countrycode','placeholder':'+86'},
	          {'title':'电话号码','name':'phonenumber'},
	          {'title':'授权方式','name':'type','placeholder':'0:朋友,1：家人'},
	          {'title':'设备授权类型','name':'sharedevicetype','placeholder':'0:全部,1：按设备'},
	          {'title':'设备授权ID','name':'sharedevice','placeholder':'[]'}]
},
{
	'title':'授权回复',
	'url':'/iremote/share/shareresponse',
	'params':[{'title':'授权ID','name':'shareid'},
	          {'title':'是否接受','name':'response','placeholder':'0:拒绝,1：接受'}]
}
,
{
	'title':'删除授权',
	'url':'/iremote/share/deletesharerequest',
	'params':[{'title':'授权ID','name':'shareid'}]
},
{
	'title':'小白多通道测试',
	'url':'/iremote/device/operationbygowild',
	'params':[{'title':'命令','name':'command'}]
},
{
	'title':'增加房间',
	'url':'/iremote/room/addroom',
	'params':[{'title':'房间名称','name':'name'},
	          {'title':'zWave设备','name':'zwavedeviceids','placeholder':'zWave设备的ID数组，格式如[12,42,325]，可以为空'},
	          {'title':'红外设备','name':'infrareddeviceids','placeholder':'红外设备的ID数组，格式如[12,42,325]，可以为空'}]
},
{
	'title':'修改房间',
	'url':'/iremote/room/editroom',
	'params':[{'title':'房间ID','name':'roomdbid'},
	          {'title':'房间名称','name':'name'}]
},
{
	'title':'增加房间设备',
	'url':'/iremote/room/addroomappliance',
	'params':[{'title':'房间ID','name':'roomdbid'},
	          {'title':'zWave设备','name':'zwavedeviceid','placeholder':'单个zWave设备的ID，可以为空'},
	          {'title':'红外设备','name':'infrareddeviceid','placeholder':'单个红外设备的ID，可以为空'},
	          {'title':'zWave设备','name':'zwavedeviceids','placeholder':'zWave设备的ID数组，格式如[12,42,325]，可以为空'},
	          {'title':'红外设备','name':'infrareddeviceids','placeholder':'红外设备的ID数组，格式如[12,42,325]，可以为空'}]
}
,
{
	'title':'删除房间设备',
	'url':'/iremote/room/deleteroomappliance',
	'params':[{'title':'房间ID','name':'roomdbid'},
	          {'title':'zWave设备','name':'zwavedeviceid','placeholder':'单个zWave设备的ID，可以为空'},
	          {'title':'红外设备','name':'infrareddeviceid','placeholder':'单个红外设备的ID，可以为空'},
	          {'title':'zWave设备','name':'zwavedeviceids','placeholder':'zWave设备的ID数组，格式如[12,42,325]，可以为空'},
	          {'title':'红外设备','name':'infrareddeviceids','placeholder':'红外设备的ID数组，格式如[12,42,325]，可以为空'}]
}
];

function onoperationchange()
{
	if ( sel.value == '' )
		return ;
	var html = '<form id="frm_params"><table width="100%">';
	var paras = data[sel.value]['params'];
	for (var i = 0 ; i < paras.length ; i ++ )
	{
		html += '<tr><td align="left" width="1%" nowrap="nowrap">' + paras[i]['title'] + ':</td>';
		html += '<td align="left"><input type="text" size="128" name="' + paras[i]['name'] + '" placeholder="' + paras[i]['placeholder']+ '""/></td></tr>'
	}
	html +="</table></form>";
	//alert(html);
	paradiv.innerHTML = html;
	td_rst.innerHTML = '';
}

function onSubmit()
{
	if ( data[sel.value]['target'] == '_blank'  )
	{
		window.open(data[sel.value]['url'] + "?" +$("#frm_params").serialize());
	}
	else 
		jQuery.ajax(
			{
				url:data[sel.value]['url'],
				data:$("#frm_params").serialize(),
				type:'POST',
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				success: function(data)
				{
					td_rst.innerHTML = JSON.stringify(data);
				}
			});
}

</script>
</head>
<body>
<center>
	WEB测试
</center>
<table width="100%" style="table-layout:fixed;word-break:break-all;">
	<tr>
		<td align="right" width="20%" nowrap="nowrap">操作:</td>
		<td>
			<select id="optionselect" onchange="onoperationchange();"></select>
		</td>
	</tr>
	<tr>
		<td align="right" width="20%" nowrap="nowrap">参数:</td>
	</tr>
	<tr>
		<td align="right" width="20%">&nbsp;</td>
		<td>
			<div id="div_parameter"></div>
		</td>
	</tr>
	<tr>
		<td align="right" width="20%">&nbsp;</td>
		<td id="td_rst" width="80%"></td>
	</tr>
	<tr>
		<td align="right" width="20%">&nbsp;</td>
		<td>
			<input type="button" value="提交" onclick="onSubmit();"/>
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
var sel= document.getElementById("optionselect"); 
var paradiv = document.getElementById("div_parameter"); 
var td_rst = document.getElementById("td_rst"); 
for ( var i = 0 ; i < data.length ; i ++ )
{
	sel.options.add(new Option(data[i]['title'],i));
	var params = data[i]['params'];
	for ( var j = 0 ; j < params.length ; j ++)
	if ( typeof(params[j]['placeholder']) == "undefined" )
	{
		//alert('1');
		params[j]['placeholder'] = '';
	}
}
onoperationchange();
</script>
</html>