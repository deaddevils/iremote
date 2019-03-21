<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<title>网关登录设置</title>
</head>

<script type="text/javascript">
	function operateremote(obj){
		var deviceid = $("#deviceid").val();
		if(deviceid!=""&&deviceid!=undefined){
			jQuery.ajax(
			{
				type: 'POST',
				url:'/iremote/gateway/forbiddenorrecover',
				data:{"deviceid":deviceid,"operate":obj},
				success: onSuccess,
				error:onError
			});
		}
		
	};
	function onSuccess(data){
		if ( data.resultCode != 0 ){
			if(data.resultCode==10001){
				showmessage("网关不存在!");
			}else if(data.resultCode==10022){
				showmessage("无权限!");
			}		
		}else{
			showmessage("操作成功!");
		}
	}
	function onError(){
		showmessage("操作失败!");
	}
	function showmessage(mes){  
		$("#message" ).text(mes);
	}
</script>
<body>
   <form action="/iremote/webconsole/changedeviceenv" method="post">
     <div style="margin-top: 50px" align="center">
       <h3>网关登录设置</h3>
       <table>
	       <tr>
		       <td>ID:</td>
		       <td><input type="text" id="deviceid" name="deviceid" style="width: 200px"></td>
	       </tr>
	       <tr><td></td><td></td></tr>
	       <tr><td></td><td align="center">&nbsp;<span class="message" id="message"></span></td></tr>
	       <tr><td></td><td></td></tr>
	       <tr>
		       <td></td>
		       <td><input type="button" id="forbidden" onclick="operateremote('forbidden')" value="禁止登录" style="width:98px"/>&nbsp;&nbsp;
		       <input type="button" id="recover" onclick="operateremote('recover')" value="恢复" style="width:98px"/></td>
	       </tr>
       </table>
     </div>
   </form>
</body>
</html>



