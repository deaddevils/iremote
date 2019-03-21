<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第三方接口演示</title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript">
var data = [
{
	'title':'登录',
	'url':'/iremote/thirdpart/login',
	'params':[{'title':'伙伴平台代码','name':'code'},
	          {'title':'密码','name':'password'}],
	'description':'伙伴平台代码和密码请向本公司商务索取.<br/>测试用户可以使用测试账号：thirdparter_open_demo，密码：c1412bf534b84da289662efba5f10761989276'
},
{
	'title':'新建小区管理员',
	'url':'/iremote/thirdpart/zufang/comnunityadministrator/addadministrator',
	'params':[{'title':'token','name':'token'},
	          {'title':'管理员','name':'administrator'}],
	'description':'一次可以添加多个管理员.<br/>管理员字段为JSON数组格式：如：[{"loginname":"0050001","password":"123456","comnutiyid":1}]<br/>'
},
{
	'title':'修改小区管理员密码',
	'url':'/iremote/thirdpart/zufang/comnunityadministrator/updatepassword',
	'params':[{'title':'token','name':'token'},
	          {'title':'账号','name':'loginname'},
	          {'title':'密码','name':'password'}]
},
{
	'title':'查询设备',
	'url':'/iremote/thirdpart/zufang/querythirdpartdevices',
	'params':[{'title':'token','name':'token'},
	          {'title':'管理员','name':'loginname','placeholder':'小区管理员账号'}]
},
{
	'title':'开锁',
	'url':'/iremote/thirdpart/zufang/unlock',
	'params':[{'title':'token','name':'token'},
	          {'title':'锁ID','name':'zwavedeviceid','placeholder':'通过小区管理员添加门锁后，同步接口会把本ID推送到伙伴平台'}]
},
{
	'title':'开锁',
	'url':'/iremote/thirdpart/zufang/unlock',
	'params':[{'title':'token','name':'token'},
	          {'title':'锁ID','name':'zwavedeviceid','placeholder':'通过小区管理员添加门锁后，同步接口会把本ID推送到伙伴平台'}]
},
{
	'title':'下发临时密码',
	'url':'/iremote/thirdpart/zufang/setlockpassword',
	'params':[{'title':'token','name':'token'},
	          {'title':'锁ID','name':'zwavedeviceid','placeholder':'通过小区管理员添加门锁后，同步接口会把本ID推送到伙伴平台'},
	          {'title':'密码','name':'password'},
	          {'title':'有效期开始时间','name':'validfrom','placeholder':'时间格式为2017-01-01 10:00:00'},
	          {'title':'有效期结束时间','name':'validthrough','placeholder':'时间格式为2017-01-01 10:00:00'}]
},
{
	'title':'删除临时密码',
	'url':'/iremote/thirdpart/zufang/deletelockpassword',
	'params':[{'title':'token','name':'token'},
	          {'title':'锁ID','name':'zwavedeviceid','placeholder':'通过小区管理员添加门锁后，同步接口会把本ID推送到伙伴平台'}]
},
{
	'title':'下发用户密码',
	'url':'/iremote/thirdpart/zufang/setlockuserpassword',
	'params':[{'title':'token','name':'token'},
	          {'title':'锁ID','name':'zwavedeviceid','placeholder':'通过小区管理员添加门锁后，同步接口会把本ID推送到伙伴平台'},
	          {'title':'锁用户ID','name':'usercode','placeholder':'取值为1到10'},
	          {'title':'密码','name':'password'},
	          {'title':'有效期开始时间','name':'validfrom','placeholder':'时间格式为2017-01-01 10:00:00'},
	          {'title':'有效期结束时间','name':'validthrough','placeholder':'时间格式为2017-01-01 10:00:00'},
	          {'title':'同步/异步','name':'asynch','placeholder':'0：异步下发,1：同步下发'},
	          {'title':'第三方ID','name':'tid'}]
},
{
	'title':'删除用户密码',
	'url':'/iremote/thirdpart/zufang/deletelockuserpassword',
	'params':[{'title':'token','name':'token'},
	          {'title':'锁ID','name':'zwavedeviceid','placeholder':'通过小区管理员添加门锁后，同步接口会把本ID推送到伙伴平台'},
	          {'title':'锁用户ID','name':'usercode','placeholder':'取值为1到10'},
	          {'title':'同步/异步','name':'asynch','placeholder':'0：异步下发,1：同步下发'},
	          {'title':'第三方ID','name':'tid'}]
},
{
	'title':'下发卡用户',
	'url':'/iremote/thirdpart/zufang/addDoorlockCard',
	'params':[{'title':'token','name':'token'},
	          {'title':'锁ID','name':'zwavedeviceid','placeholder':'通过小区管理员添加门锁后，同步接口会把本ID推送到伙伴平台'},
	          {'title':'姓名','name':'cardname'},
	          {'title':'卡号','name':'cardinfo'},
	          {'title':'卡类型','name':'cardtype','placeholder':'1:MF卡；2:ID卡'},
	          {'title':'有效期开始时间','name':'validfrom','placeholder':'时间格式为2017-01-01 10:00:00'},
	          {'title':'有效期结束时间','name':'validthrough','placeholder':'时间格式为2017-01-01 10:00:00'},
	          {'title':'同步/异步','name':'asynch','placeholder':'0：异步下发,1：同步下发'},
	          {'title':'第三方ID','name':'tid'}]
},
{
	'title':'删除卡用户',
	'url':'/iremote/thirdpart/zufang/deleteDoorlockCard',
	'params':[{'title':'token','name':'token'},
	          {'title':'锁ID','name':'zwavedeviceid','placeholder':'通过小区管理员添加门锁后，同步接口会把本ID推送到伙伴平台'},
	          {'title':'锁用户ID','name':'usercode','placeholder':'取值为1到10'},
	          {'title':'同步/异步','name':'asynch','placeholder':'0：异步下发,1：同步下发'},
	          {'title':'第三方ID','name':'tid'}]
},
{
	'title':'下发指纹信息',
	'url':'/iremote/thirdpart/zufang/setlockfingerprintuserinfo',
	'params':[{'title':'token','name':'token'},
	          {'title':'锁ID','name':'zwavedeviceid','placeholder':'通过小区管理员添加门锁后，同步接口会把本ID推送到伙伴平台'},
	          {'title':'指纹信息','name':'fingerprint','placeholder':''},
	          {'title':'有效期开始','name':'validfrom','placeholder':'2016-01-11 23:19:00'},
	          {'title':'有效期结束','name':'validthrough','placeholder':'2016-12-11 23:19:00'},
	          {'title':'星期有效期','name':'weekday','placeholder':''},
	          {'title':'星期有效期开始时间','name':'starttime','placeholder':'08:30'},
	          {'title':'星期有效期结束时间','name':'endtime','placeholder':'18:30'},
	          {'title':'同步/异步','name':'asynch','placeholder':'0：异步下发,1：同步下发'},
	          {'title':'第三方ID','name':'tid'}]
},
{
	'title':'删除指纹用户',
	'url':'/iremote/thirdpart/zufang/deletelockfingerprintuser',
	'params':[{'title':'token','name':'token'},
	          {'title':'锁ID','name':'zwavedeviceid','placeholder':'通过小区管理员添加门锁后，同步接口会把本ID推送到伙伴平台'},
	          {'title':'锁用户ID','name':'usercode','placeholder':''},
	          {'title':'同步/异步','name':'asynch','placeholder':'0：异步下发,1：同步下发'},
	          {'title':'第三方ID','name':'tid'}]
},
{
	'title':'启动门锁指纹添加流程',
	'url':'/iremote/thirdpart/zufang/startaddingfingerprintuser',
	'params':[{'title':'token','name':'token'},
		{'title':'用户名','name':'username','placeholder':'通过小区管理员添加门锁后，同步接口会把本ID推送到伙伴平台'},
		{'title':'锁ID','name':'zwavedeviceid','placeholder':''},
		{'title':'有效期开始','name':'validfrom','placeholder':'2016-01-11 23:19:00'},
		{'title':'有效期结束','name':'validthrough','placeholder':'2016-12-11 23:19:00'}]
},
{
	'title':'绑定网关',
	'url':'/iremote/thirdpart/zufang/scanqrcode',
	'params':[{'title':'token','name':'token'},
		{'title':'二维码内容','name':'message','placeholder':'二维码内容，扫码后原样传到经纬平台'},
		{'title':'附加信息','name':'appendmessage','placeholder':''},
		{'title':'管理员账号','name':'loginname','placeholder':''}]
},
{
    'title':'解绑网关',
    'url':'/iremote/thirdpart/zufang/gateway/deletegateway',
    'params':[{'title':'token','name':'token'},
        {'title':'网关ID','name':'deviceid','placeholder':''}]
}
];

function onoperationchange()
{
	if ( sel.value == '' )
		return ;
	 
	var html = '<form id="frm_params"><table width="100%">';
	html += '<tr><td align="left">URL:</td><td>' + data[sel.value]['url'] +'</td></tr>';
	var paras = data[sel.value]['params'];
	for (var i = 0 ; i < paras.length ; i ++ )
	{
		html += '<tr><td align="left" width="1%" nowrap="nowrap">' + paras[i]['title'] + ':</td>';
		html += '<td align="left"><input type="text" size="128" name="' + paras[i]['name'] + '" placeholder="' + paras[i]['placeholder']+ '""/></td></tr>'
	}
	if ( data[sel.value]['description'] != null )
	{
		html += '<tr><td>&nbsp;</td></tr><tr><td>说明</td><td>' + data[sel.value]['description'] +'</td></tr>';
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