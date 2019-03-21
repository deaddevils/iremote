<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<a href="<%=request.getContextPath()%>/webconsole/listgateway" target="main" style="text-decoration: none">用户网关</a><br><br>
<s:if test="adminUser == true"> 
	<a href="<%=request.getContextPath()%>/webconsole/apkversion" target="main" style="text-decoration:none">APK版本管理</a><br><br>
	<a href="<%=request.getContextPath()%>/webconsole/creategatewayqrcode" target="main" style="text-decoration: none">网关二维码</a><br><br>
	<a href="<%=request.getContextPath()%>/webconsole/querydeviceinitsetting" target="main" style="text-decoration: none">设备初始化设置</a><br><br>
	<a href="<%=request.getContextPath()%>/webconsole/importbluetoothlockpage" target="main" style="text-decoration: none">蓝牙锁信息导入</a><br><br>
	<a href="<%=request.getContextPath()%>/webconsole/generatedevicepage" target="main" style="text-decoration: none">设备信息生成</a><br><br>
	<a href="<%=request.getContextPath()%>/gateway/onlinegatewaynumber" target="main" style="text-decoration: none">在线网关数</a><br><br>
	<a href="<%=request.getContextPath()%>/webconsole/reloadoemproductorsetting" target="main" style="text-decoration: none">加载OEM厂商设置</a><br><br>
	<a href="<%=request.getContextPath()%>/webconsole/environmentalpage" target="main" style="text-decoration: none">网关环境切换</a><br><br>
	<a href="<%=request.getContextPath()%>/webconsole/forbiddenremotepage" target="main" style="text-decoration: none">禁止网关登录</a><br><br>
	<a href="<%=request.getContextPath()%>/webconsole/cooperative" target="main" style="text-decoration: none">合作伙伴管理</a><br><br>
</s:if>
<s:if test="ametaAdminUser == true"> 
	<a href="<%=request.getContextPath()%>/webconsole/registuserpage" target="main" style="text-decoration: none">APP注册用户</a><br><br>
</s:if>
</body>
</html>