<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<title>download</title>
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<link type="text/css" rel="stylesheet" href="/iremote/css/phoneappstyle.css" charset="utf-8"/>
<script type="text/javascript">
var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

if ( isAndroid )
	window.location.href= "<s:property value='androidappdownloadurl'/>";
else if ( isiOS ) 
	window.location.href= "<s:property value='iosappdownloadurl'/>";
	
</script>


</head>
<body bgcolor="#FFFFFF">
<center>
<br><br>
<a href="<s:property value='androidappdownloadurl'/>"><img src="images/os/android-logo_2.png" width="156" /></a><br><br>
<a href="<s:property value='iosappdownloadurl'/>"><img src="images/os/apple-logo_2.jpg" width="156" /></a><br>
</center>

</body>
</html>
