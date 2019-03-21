<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Environment switching</title>
</head>
<body>
   <form action="/iremote/webconsole/changedeviceenv" method="post">
     <div style="margin-top: 50px" align="center">
       <h3>环境切换</h3>
       &nbsp;&nbsp;I D：<input type="text" name="deviceid" style="width: 150px"><br><br>
                      环境：<select name="evn" style="width: 157px">
              <option value="">请选择需要切换的环境</option>
              <option value="255" >生产环境</option>
              <option value="252">测试环境</option>
           </select><br><br>
           <input type="reset" style="margin-left:1%" value="取消">
           <input type="submit" style="margin-left:3%" value="确认">
     </div>
   </form>
</body>
</html>



