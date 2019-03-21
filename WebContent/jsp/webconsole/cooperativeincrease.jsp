<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Cooperative partner</title>
</head>
<body>
  <center style="margin-top: 3%">
	   <form action="createthirdpart" method="post">
	   <div style="margin-left: 3.5%">
	          代码：<input type="text" name="code"><br><br>
	          名称：<input type="text" name="name"><br><br>
	          平台：<input type="text" name="platform"><br><br>
	   </div>
	   <div>管理员前缀：<input type="text" name="adminprefix"><br><br></div>
	   <div style="margin-left: 4%">
       <input type="reset" value="重置">
       <input type="submit" value="确定" style="margin-left: 3%">
       </div>
	   </form>
  </center>
</body>
</html>



