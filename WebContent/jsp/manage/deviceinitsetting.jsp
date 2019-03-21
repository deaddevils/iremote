
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>

<script type="text/javascript">
function add()
{
	
	$.ajax({
		type:"post",
		dataType: "json",
		url: '<%=request.getContextPath()%>/webconsole/adddeviceinitsetting',
		data: $("#div_devicesetting_new").serialize(),
		success: function(json) 
		{
			if ( json.resultCode == 0 )
			{
				alert("成功");
				$('#mid').val('');
			}
			else if ( json.resultCode == 30315)
				alert('参数错误，请检查输入');
			else if ( json.resultCode == 30316 )
				alert('厂商ID重复')
		},
		failure: function(json) 
		{
			alert("网络故障");
		}
	});
}

function dump()
{
	window.location.href='<%=request.getContextPath()%>/webconsole/dumpdeviceinitsetting';
}
</script>
</head>
<body>
<form action="adddeviceinitsetting" enctype="multipart/form-data" method="post">
<!-- <input type="file" name="adddeviceInitSettingFile">
<input type="submit" value="确认"> -->

		<div style="margin-top: 50px" align="center">
	       <h3>设备初始化设置</h3>
	       <table>
		       <tr>
			       <td><input type="file" name="adddeviceInitSettingFile" style="width:290px"></td>
		       </tr>
		       <tr><td><br></td></tr>
		       <tr>
			       <td align="center"><input type="submit" value="确认" style="width:150px"></td>
		       </tr>
	       </table>
	     </div>
<!--   
<div >
	<table>
		<tr>
			<td>
				厂商ID：
			</td>
			<td>
				<input type="text" name="mid" value='' placeholder='021c80521000'/>
			</td>
		</tr>
		<tr>
			<td>厂商代码:</td>
			<td><input type="text" name="manufacture" value='' placeholder='jwzh'/></td>
		</tr>
		<tr>
			<td>设备类型:</td>
			<td><input type="text" name="devicetype" value='' placeholder='1'/></td>
		</tr>
		<tr>
			<td>初始化命令:</td>
			<td><input type="text" name="initcmds" size="80" value='' placeholder='["7004010104","840400a8c001"]'/></td>
		</tr>
		<tr>
			<td>版本号:</td>
			<td><input type="text" name="version" value='<s:property value="version"/>' /></td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<input type="button" onclick="return add();" value="增加"/>&nbsp;&nbsp;&nbsp;
				<input type="button" onclick="return dump();" value="导出"/>
			</td>
		</tr>
	</table>   
	<hr>
</div>	
 -->
</form>
</body>
</html>