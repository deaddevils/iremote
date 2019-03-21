<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.iremote.domain.ZWaveDevice,java.util.*" pageEncoding="UTF-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
	<title>设备管理</title>
	<script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
<style type="text/css">
	.outaircondition_td {
		margin:0px;
		padding:0px;
		border:1px solid #ABABAB;
	}
	
	.inaircondition_td {
		margin:0px;
		padding:0px;
		border:1px solid #ABABAB;
	}
	
	.inputtext {
		width:100%;
		height: 100%;
		background:rgba(0, 0, 0, 0);
		padding:1px 3px 1px 3px;
		margin:0px;
		border:none; /* 输入框不要边框 */
		font-family:Arial; 
	}
	
	.noedit {
		readonly:true;
	}
	
	    /*========bordered table========*/
    .bordered {
        border: #ccc 1px;
        -moz-border-radius: 6px;
        -webkit-border-radius: 6px;
        border-radius: 6px;
        -webkit-box-shadow: 0 1px 1px #ccc;
        -moz-box-shadow: 0 1px 1px #ccc;
        box-shadow: 0 1px 1px #ccc;
    }
    
    .bordered tr {
        -o-transition: all 0.1s ease-in-out;
        -webkit-transition: all 0.1s ease-in-out;
        -moz-transition: all 0.1s ease-in-out;
        -ms-transition: all 0.1s ease-in-out;
        transition: all 0.1s ease-in-out;        
    }
    .bordered .highlight,
    .bordered tr:hover {
        background: #fbf8e9;        
    }
    .bordered td, 
    .bordered th {
        border-left: 1px solid #ccc;
        border-top: 1px solid #ccc;
        padding: 5px;
        text-align: left;
       	background: #BFEFFF;
    }
    .sn td{
 		background: #F0FFFF;        
 	}
    .bordered th {
        background-color: #dce9f9;
        background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9));
        background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);
        background-image: -moz-linear-gradient(top, #ebf3fc, #dce9f9);
        background-image: -ms-linear-gradient(top, #ebf3fc, #dce9f9);
        background-image: -o-linear-gradient(top, #ebf3fc, #dce9f9);
        background-image: linear-gradient(top, #ebf3fc, #dce9f9);
        filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0, startColorstr=#ebf3fc, endColorstr=#dce9f9);
        -ms-filter: "progid:DXImageTransform.Microsoft.gradient (GradientType=0, startColorstr=#ebf3fc, endColorstr=#dce9f9)";
        -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;
        -moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;
        box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;
        border-top: none;
        text-shadow: 0 1px 0 rgba(255,255,255,.5);
    }
    .bordered td:first-child, 
    .bordered th:first-child {
        border-left: none;
    }
    .bordered th:first-child {
        -moz-border-radius: 6px 0 0 0;
        -webkit-border-radius: 6px 0 0 0;
        border-radius: 6px 0 0 0;
    }
    .bordered th:last-child {
        -moz-border-radius: 0 6px 0 0;
        -webkit-border-radius: 0 6px 0 0;
        border-radius: 0 6px 0 0;
    }
    .bordered tr:last-child td:first-child {
        -moz-border-radius: 0 0 0 6px;
        -webkit-border-radius: 0 0 0 6px;
        border-radius: 0 0 0 6px;
    }
    .bordered tr:last-child td:last-child {
        -moz-border-radius: 0 0 6px 0;
        -webkit-border-radius: 0 0 6px 0;
        border-radius: 0 0 6px 0;
    }
    /*----------------------*/

</style>	

</head>
<body style="background-color:#f4f4f4;margin:0px;padding:0px">
	<input type="hidden" name="zwavedeviceid" value="<s:property value='zwavedeviceid'/>"/>
	<input type="hidden" id="usertype" name="usertype" value="21"/>
	<input type="hidden" id="capabilitycode" name="capabilitycode"/>
	<table style="background-color:#4fc1e9" width="100%" height="50px">
		<tr >
			<td align="left" width="30%" ></td>
			<td align="center"><span >设备管理 </span></td>
			<td width="30%"></td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<td align="right" colspan=2><a href="/iremote/webconsole/listgateway"><s:text name="gateway" /></a>&nbsp;
			<a href="/iremote/webconsole/setgatewayownerpage">增加网关</a></td>
		</tr>
	</table>
	<table align="center">
		<tr>
			<td><s:text name="gateway" />：</td>
			<td>
				<select id="deviceid" onchange="changDeviceid(this)">
					<option value="0">--请选择网关--</option>
					<s:iterator value="remoteList" id="c">
						<option value="<s:property value="#c.deviceid" />">
							<s:if test="#c.name != ''"> 
								<s:property value="#c.name" />
							</s:if>
							<s:else>
								<s:property value="#c.deviceid" />
							</s:else> 
						</option>
					</s:iterator>
				</select>
			</td>
		</tr>
	</table>
	
	<table align="center" class="bordered" id="aircondtion_tb" width="80%">
		<tr >
			<th width="100px" align="center">机型</th>
			<th width="100px" align="center">机名</th>
			<th width="100px" align="center">ID</th>
			<th width="100px" align="center">操作</th>
		</tr>
	</table>
	<div align="center">
		<button onclick="sysdevice()">写入网关</button>
		<button onclick="addOutAir()">添加室外机</button>
	</div>
	<br><br>
	<div align="center">
		<s:if test="capabilitycode == 10038">
			<p>百朗新风外机id默认为0，且一个网关下只允许添加一个</p>
		</s:if>
		<s:if test="capabilitycode == 10036">
			<p>除湿设备外机id默认为0，且一个网关下只允许添加一个</p>
		</s:if>
	</div>
</body>
<script>
	
	var outAir = '<s:property value="outdevicetype" />';
	var inAir = '<s:property value="innerdevicetype" />';
	var deviceid = "<s:property value="deviceid" />";
	var capabilitycode = '<s:property value="capabilitycode" />'
	
	$(document).ready(function(){ 
		initDeviceid();
	})
	
	function initDeviceid(){
		if(deviceid != "" && deviceid != null){
			$("#deviceid option").each(function(){  
		          if(deviceid == $(this).val()){  
		        	  $(this).attr("selected","selected");
		          }  
		     });  
			changDeviceid($("#deviceid"));
		}
	}
	
	function addOutAir(){
		$("#aircondtion_tb").append(createTb("",outAir));
	}
	
	function addInAir(obj , id){
		$(obj).parents('tr').after(createTb(obj,inAir , id));
	}
	
	function sysdevice(){
        var deviceid = $("#deviceid").val();

		if(deviceid == null || deviceid.length < 1 || deviceid == '0'){
        	alert("网关不能为空");
        	return;
        }
		 $.ajax({
				type:"post",
				url:"/iremote/device/addDeviceAction",
				dataType: "json",
				data: {"deviceid":deviceid,"capabilitycode":<s:property value="capabilitycode" />},
				success:function(data){
					alert(data.message);
				},
				error:function(error) {
					alert("ajax error!")
	                console.log(error);          
	            },
	        });
	}
	
	function to16(obj){
		str = "0x"
		if(obj < 10 ){
			str += "0" + obj.toString(16)
		}else{
			str += obj.toString(16)
		}
		return str;
	}
	
	function createTb(obj,airtype , outid){
		if(airtype == outAir){
	        var deviceid = $("#deviceid").val();

			if(deviceid == null || deviceid.length < 1 || deviceid == '0'){
	        	alert("网关不能为空");
	        	return;
	        }
		}
		var str = "<tr class='inaircondition_td";
			if(airtype == inAir){
				str += " sn'>"
				str += "<td><input class='inputtext' readonly='true' value='<s:property value="innerdevicetype" />' type='hidden' name = 'devicetype'/>&nbsp&nbsp&nbsp室内机</td>"
			}else if(airtype == outAir){
				str += "'>"
				str += "<td><input class='inputtext' readonly='true' value='<s:property value="outdevicetype" />' type='hidden' name = 'devicetype'/>室外机</td>"
			}
			str += "<td><input class='inputtext' type='text' name = 'name'/></td>"
				+ "<td><select id='id'>";
			if(airtype == inAir){
				str += "<option value='-1' selected='selected'>请选择室内机id</option>";
				for(var i = 0; i < 64; i++){
					str += "<option value='" + i +"'>" + to16(i) + "</option>";
				}
			}else if(airtype == outAir){
				if(capabilitycode != null && capabilitycode == '10028'){
					str += "<option value='-1' selected='selected'>请选择室外机id</option>";
					for(var i = 0; i < 16; i++){
						str += "<option value='" + i +"'>" + to16(i) + "</option>";
					}
				}else{
					str += "<option value='0' selected='selected'>0x00</option>";
				}
			}
			str += "</select></td>"
		    + "<td><button type='button' style='float: left;display:none' name='update' id='update' onclick='update(this)'>修改</button>"
		    + "<button type='button' name='save' onclick='save(this)' style='float: left;'>保存</button>"
			+ "<button type='button' name='save' onclick='deltb(this)' style='float: left;'>删除</button>";
			if(airtype == outAir){
				str += "<button type='button' style='float: left;display:none' onclick=''>添加室内机</button>"
					str += "<input class='inputtext' type='hidden' name = 'outid' value='" + $(obj).val() + "'/></td>"
			}
			else 
				str += "<input class='inputtext' type='hidden' name = 'outid' value='" + outid + "'/></td>"
			+ "</tr>";
		return str;
	}
	
	function deltb(obj){
		$(obj).parents('tr').remove();
	}
	
	function save(obj){
		//var tr1 = obj.parentNode.parentNode;  		
		//alert(tr1.rowIndex);
        //alert(tr1.cells[0].find($("#devicetype")).value); //获取的方法一  	
        var deviceid = $("#deviceid").val();
        var zwavedeviceid = $(obj).val();
        var devicetype = $(obj).parents('tr').children('td').eq(0).find('input').val();
        var name = $(obj).parents('tr').children('td').eq(1).find('input').val();
        var id = $(obj).parents('tr').children('td').eq(2).find('select').val();
        var outid = $(obj).parents('tr').children('td').eq(3).find('input').val();
        if(deviceid == null || deviceid.length < 1){
        	alert("网关不能为空");
        	return;
        }
        if(id == -1 || id == "underfined"){
        	var message;
        	if(devicetype == outAir)
        		message = "请选择室外机id";
        	else if (devicetype == inAir)
        		massage = "请选择室内机id";
        	alert(message);
        	return;
        }
        $.ajax({
			type:"post",
			url:"/iremote/airconditioner/addAirconditioner",
			data:{"deviceid":deviceid,"zwavedeviceid":zwavedeviceid,"devicetype":devicetype,"name":name,"id":id,"outid":outid,"capabilitycode":<s:property value="capabilitycode" />},
			dataType: "json",
			success:function(data){
				if(data.message != ""){
					alert(data.message);
				}else{
			       $(obj).hide();
			        $(obj).prev().show();
			        $(obj).next().attr("onclick","del(this)");
			        $(obj).next().val(data.zwavedeviceid)
			        if(devicetype == outAir){
			        	$(obj).next().next().show();
			            $(obj).next().next().value = id;
			            $(obj).next().next().attr("onclick","addInAir(this," + id + ");");
			        }
			        $(obj).parents('tr').children('td').eq(1).find('input').attr("readonly", true);
			        $(obj).parents('tr').children('td').eq(2).find('input').attr("readonly", true);
			        $(obj).parents('tr').children('td').eq(2).find('select').remove();
			        $(obj).parents('tr').children('td').eq(2).append("<input class='inputtext' readonly='true' type='text' name = 'id' value='" + to16(id) + "'></input>");;
				}
			},
			error:function(error) {
				alert("ajax error!")
                console.log(error);          
            },
        });
 	}
	
	function update(obj){
        $(obj).parents('tr').children('td').eq(1).find('input').attr("readonly", false);
        $(obj).hide();
        $(obj).next().show();
	}
	
	function del(obj){
        var zwavedeviceid = $(obj).val();
		$.ajax({
			type:"post",
			url:"/iremote/airconditioner/deleteairconditioner",
			data:{"zwavedeviceid":zwavedeviceid,"capabilitycode":<s:property value="capabilitycode" />},
			dataType: "json",
			success:function(data){
				if(data.message == "airin is not null"){
					alert("请先删除该室外机下的室内机！")
				}else if(data.resultCode != "0"){
					alert("删除失败");
				}else{
					$(obj).parents('tr').remove();
				}
			},
			error:function(error) {
				alert("ajax error!")
                console.log(error);          
            }
        });
	}
	
	function changDeviceid(obj){
		$(".outaircondition_td").remove();		
		$(".inaircondition_td").remove();
		var deviceid = obj.value;
		if(deviceid == null || deviceid == "undefined")
			deviceid = obj.val();
		var tab = "";
		$.ajax({
			type:"post",
			url:"/iremote/airconditioner/viewAirconditioner",
			data:{"deviceid":deviceid,"capabilitycode":<s:property value="capabilitycode" />},
			dataType: "json",
			success: function(data){
				var zwLsit = data.zwLsit;
				if(zwLsit != null && zwLsit.length > 0){
					var outid;
					
					for(var i = 0; i < zwLsit.length ; i++){
						var id;
						if(zwLsit[i].devicetype == inAir){
							id = zwLsit[i].nuid & 0xff;
							tab += "<tr class='inaircondition_td sn'>";
							tab += "<td><input class='inputtext' readonly='true' type='hidden' name = 'devicetype' value='" + zwLsit[i].devicetype + "'/>&nbsp&nbsp&nbsp室内机</td>"
						}else if(zwLsit[i].devicetype == outAir){
							id = (zwLsit[i].nuid & 0xff00) / 256;
							outid = id;
							tab += "<tr class='outaircondition_td'>";
							tab += "<td><input class='inputtext' readonly='true' type='hidden' name = 'devicetype' value='" + zwLsit[i].devicetype + "'/>室外机</td>"
						}
						tab += "<td><input class='inputtext' readonly='true' type='text' name = 'name' value='" + zwLsit[i].name + "'></input></td>"
							+ "<td><input class='inputtext' readonly='true' type='text' name = 'id' value='" + to16(id) + "'></input></td>"
							+"<td><button type='button' style='float: left;' name='update' id='update' onclick='update(this)'>修改</button>"
							+"<button type='button' style='float: left;display:none;' name='save' id='save' value='" + zwLsit[i].zwavedeviceid + "' onclick='save(this)'>保存</button>"
							+"<button type='button' style='float: left;' value='" + zwLsit[i].zwavedeviceid + "' onclick='del(this)'>删除</button>";
						if(zwLsit[i].devicetype == outAir){
							tab += "<button type='button' style='float: left;' value='" + outid + "' onclick='addInAir(this," + outid + ")'>添加室内机</button>";
						}else if(zwLsit[i].devicetype == inAir){
							tab += "<input class='inputtext' readonly='true' type='hidden' name = 'outid' value='" + outid + "'></input>";
						}
						tab +="</td></tr>";
					}
				}
				$("#aircondtion_tb").append(tab);
			},
			error:function(error) {
                console.log(error);          
            },
		});
	};
</script>
</html>