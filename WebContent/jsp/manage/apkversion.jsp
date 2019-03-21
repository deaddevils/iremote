<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>

<script type="text/javascript">
function modify(id)
{
	$.ajax({
		type:"post",
		dataType: "json",
		url: '<%=request.getContextPath()%>/webconsole/editapkversion',
		data: $("#"+id).serialize(),
		success: function(json) 
		{
			alert("成功");
			window.location.href='<%=request.getContextPath()%>/webconsole/apkversion';
		},
		failure: function(json) 
		{
			alert("网络故障");
		}
	});
}

</script>
</head>
<body>

<s:iterator value="appversions" id="av">
<form id='div_av_<s:property value="#av.appversionid"/>'>
<div >
	<table>
		<tr>
			<td>
				<input type="hidden" name="appversionid" value='<s:property value="#av.appversionid"/>'/>
				<input type="hidden" name="platform" value='<s:property value="#av.platform"/>'/>
				厂商：
			</td>
			<td>
				<s:if test="#av.platform == 0">
					经纬
				</s:if>
				<s:if test="#av.platform == 2">
					创佳
				</s:if>
				<s:if test="#av.platform == 3">
					多灵
				</s:if>
				<s:if test="#av.platform == 6">
					小虎
				</s:if>
				(<s:property value="#av.platform"/>)
			</td>
		</tr>
		<tr>
			<td>版本:</td>
			<td><input type="text" name="latestversion" value='<s:property value="#av.latestversion"/>'/></td>
		</tr>
		<tr>
			<td>数字版本:</td>
			<td><input type="text" name="latestiversion" value='<s:property value="#av.latestiversion"/>'/></td>
		</tr>
		<tr>
			<td>下载地址:</td>
			<td><input type="text" name="downloadurl" size="79" value='<s:property value="#av.downloadurl"/>'/></td>
		</tr>
		<tr>
			<td>说明:</td>
			<td><textarea rows="8" cols="80" name="description" ><s:property value="#av.description"/></textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="right"><input type="button" onclick="return modify('div_av_<s:property value="#av.appversionid"/>');" value="修改"></button></td>
		</tr>
	</table>   
	<hr>
</div>	
</form>

</s:iterator>

<form id='div_av_new'>
<div >
	<table>
		<tr>
			<td>
				厂商：
			</td>
			<td>
				<input type="text" name="platform" value=''/>
			</td>
		</tr>
		<tr>
			<td>版本:</td>
			<td><input type="text" name="latestversion" value=''/></td>
		</tr>
		<tr>
			<td>数字版本:</td>
			<td><input type="text" name="latestiversion" value=''/></td>
		</tr>
		<tr>
			<td>下载地址:</td>
			<td><input type="text" name="downloadurl" size="79" value=''/></td>
		</tr>
		<tr>
			<td>说明:</td>
			<td><textarea rows="8" cols="80" name="description" ></textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="right"><input type="button" onclick="return modify('div_av_new');" value="增加"></button></td>
		</tr>
	</table>   
	<hr>
</div>	
</form>
</body>
</html>