
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>

    <script type="text/javascript">

    </script>
</head>
<body>
<form action="generatedevice" enctype="multipart/form-data" method="post">
    <div style="margin-top: 50px" align="center">
        <h3>设备信息生成</h3>
        <table>
            <tr>
                <td>网关能力集</td>
            </tr>
            <tr align="center">
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="1" style="zoom:150%;vertical-align: -3px;" checked/>无红外功能(1)</label></td>
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="2" style="zoom:150%;vertical-align: -3px;" checked/>无ZWave功能(2)</label></td>
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="3" style="zoom:150%;vertical-align: -3px;" />支持新AP模式(3)</label></td>
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="4" style="zoom:150%;vertical-align: -3px;"/>支持自主添加ZWave设备(4)</label></td>
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="5" style="zoom:150%;vertical-align: -3px;"/>支持批量执行命令(5)</label></td>

            </tr>
            <tr align="center">
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="6" style="zoom:150%;vertical-align: -3px;"/>支持组命令(6)</label></td>
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="7" style="zoom:150%;vertical-align: -3px;"/>支持DSC安防设备(7)</label></td>
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="8" style="zoom:150%;vertical-align: -3px;"/>不支持网络设置(8)</label></td>
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="10" style="zoom:150%;vertical-align: -3px;"/>支持无电源设备(10)</label></td>
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="10001" style="zoom:150%;vertical-align: -3px;"/>支持10个临时密码(10001)</label></td>
            </tr>
            <tr>
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="10002" style="zoom:150%;vertical-align: -3px;"/>支持1个临时密码(10002)</label></td>
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="10028" style="zoom:150%;vertical-align: -3px;"/>支持中央空调(10028)</label></td>
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="10036" style="zoom:150%;vertical-align: -3px;"/>支持除湿设备(10036)</label></td>
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="10038" style="zoom:150%;vertical-align: -3px;"/>支持新风设备(10038)</label></td>
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="10039" style="zoom:150%;vertical-align: -3px;"/>支持背景音乐(10039)</label></td>
            </tr>
            <tr>
                <td align="left"><label><input type="checkbox" name="gateWayCapabilityCodeList" value="10060" style="zoom:150%;vertical-align: -3px;" checked/>支持穿衣小助手(10060)</label></td>
            </tr>
            <tr><td colspan=5><br></td></tr>
            <tr>
                <td align="left" colspan=1>网关 ID: <input name="deviceid"></td>
                <td align="center">或者</td>
                <td colspan=2><input type="file" name="deviceIdListFile" style="width:290px"></td>
            </tr>
            <tr>
                <td align="left" colspan=3>设备类型:
                    <select name="devicetype">
                        <option value ="60">穿衣小助手</option>
                    </select>
                </td>
            </tr>
            <tr><td colspan=5><br></td></tr>
            <tr>
                <td align="center" colspan=5><input type="submit" value="确认" style="width:150px"></td>
            </tr>
        </table>
    </div>
</form>
</body>
</html>