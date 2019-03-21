<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/language.jsp"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/iremote/css/iremote.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="/iremote/css/DateTimePicker.css" />
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/iremote/js/DateTimePicker.js"></script>

<title><s:text name="resetpassword_title" /></title>
</head>
<style type="text/css">
.text_left
{
	padding-left:20px;
}
.bottom_line{
	border-bottom:#999999 solid 1px;
}
.input_date
{
	width:10%;
	height:22px;
	
	font: normal 15px arial, sans-serif; 
	border-radius:5px;
	border-collapse:collapse;
	border-spacing:0;
	border:1px solid #888;
	padding-left:5px ;
}
</style>

<script type="text/javascript">
$(document).ready(function(){

	$("#startDiv").DateTimePicker();
	$("#endDiv").DateTimePicker();
	
});

function queryData(){
	var name = $("#name").val();
	var phonenumber =$("#phonenumber").val();
	//var deviceid= $("#deviceid").val();
	var validfrom=$("#validfrom").val();
	var validthrough=$("#validthrough").val();
	var pageSize=30;
	location.href="registuserpage?currentPage=1&name="+name+"&phonenumber="+phonenumber+"&validfrom="+validfrom+"&validthrough="+validthrough+"&pageSize="+pageSize;
}
</script>
<body style="background-color:#f4f4f4;margin:0px;padding:0px">
<table style="background-color:#4fc1e9" width="100%" height="50px" align="center">
		<tr >
			<td align="left" width="30%" ></td>
			<td align="center"><span >已注册用户</span></td>
			<td align=right width="30%"></td>
		</tr>
	</table>

	
<br/>
<table width="90%" align="center">
<tr><td colspan="4">
	用户名<input type="text" name="name" id="name" value="<s:property value='name'/>">
	用户账号<input type="text" name="phonenumber" id="phonenumber" value="<s:property value='phonenumber'/>">

	
	<span style="margin:5px 60px 0px 10px;">
		<s:text name="starttime"/>
		<input placeholder="<s:text name='selectstartnumber' />" class="input_date" readonly="readonly" maxlength="16" id="validfrom" name="validfrom" type="text" value="<s:property value="validfrom"/>"  data-field="datetime">
		<span id="startDiv"></span>
		<s:text name="endtime"/>
		<input placeholder="<s:text name='selectendnumber' />" class="input_date" readonly="readonly" maxlength="16" id="validthrough" name="validthrough" type="text"  value="<s:property value="validthrough"/>"  data-field="datetime" >
		<span id="endDiv"></span>
	</span>    
	
	<input type="button" id="query" value="查询" onclick="queryData()">
</td></tr>
</table>
<table border="1" cellspacing="1" width="90%" align="center">
	<tr>
		<td align="center" style="width:15%">用户名</td>
		<td align="center" style="width:15%">用户账号</td>  
		<td align="center" style="width:15%">注册时间</td>
		<td align="center" style="width:45%">网关ID</td>
	</tr>
	<s:iterator value="pager.content" id="r">
		<tr>
			<td ><s:property value='#r.phoneuser.name'/></td>
			<td ><s:property value='#r.phoneuser.phonenumber'/></td>
			<td ><s:property value='#r.phoneuser.createtime'/><%-- <fmt:formatDate value="${r.phoneuser.createtime}" type="date" pattern="yyyy-MM-dd HH:mm"/> --%></td>
			<td ><s:iterator value="#r.devices" id="ids" status="index">
			<s:if test="#index.index != 0">,</s:if><s:property value='deviceid'/>
			</s:iterator></td>
		</tr>
	</s:iterator>

	</table>
	<table  width="90%" align="center">
		<tr><td colspan="4"  align="right">
		
		<a href="registuserpage?currentPage=${pager.firstPage}" style="text-decoration:none">&lt;&lt; 首页 </a>
			<s:if test="pager.currentPage == 1">&lt;上一页</s:if>
			<s:if test="pager.currentPage > 1">
		    <a href="registuserpage?currentPage=${pager.currentPage-1 }" style="text-decoration:none">&lt; 上一页 </a>
		    </s:if>
		    <select><option>25</option><option>50</option></select>
		    <strong>第${pager.currentPage}页/共${pager.pageTotal}页</strong>
		    <s:if test="pager.currentPage == pager.pageTotal">下一页 &gt;</s:if>
		    <s:if test="pager.currentPage < pager.pageTotal">
		    <a href="registuserpage?currentPage=${pager.currentPage+1}" style="text-decoration:none">下一页 &gt;</a>
		    </s:if>
		    <a href="registuserpage?currentPage=${pager.lastPage}"  style="text-decoration:none">末页 &gt;&gt;</a>
		   <!--  跳转到<input type="text" id="pageCode" style="width:30px"/> 页 <input >确定 -->
		</td></tr>
	</table>
	

</body>
</html>