<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title><s:text name="title" /></title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.0.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.toast.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jsp/device/deviceName/mockJsInterface.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/iremote.js"></script>

    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/iremotewebstyle.css" charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.toast.css" />
    <style type="text/css">
        .input_option
        {
            width:200px;
            height:40px;
            font: normal 16px arial, sans-serif;
            font-family:"Microsoft YaHei";
            margin-top:0px;
            border-radius:5px;
            border-collapse:collapse;
            border-spacing:0;
            border-left:0px solid #888;
            border-top:0px solid #888;
            border-right:0px solid #888;
            border-bottom:0px solid #888;

            padding-left:40px ;
        }

        .input_text
        {
            width:90%;
            height:40px;
            font: normal 20px arial, sans-serif;
            font-family:"Source Han Sans";
            color:#333333;
            margin-top:0px;

            border-radius:5px;
            border-collapse:collapse;
            border-spacing:0;
            border:0px;

            margin-left:20px;

        }

    </style>
    <script type="text/javascript">
        var regRule = /\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g;
        var deviceid = "<s:property value='deviceid'/>";

        function onback()
        {
            JSInterface.back();
        }

        function selectBaudRateOption() {
            var baudRate = getBaudRate(1);
            if (baudRate) {
                $('#baudrate option[value=' + baudRate + ']').prop("selected", true);
            }
        }

        function getBaudRate(code){
            var lis = $("#capabilityHolder").val();
            var li = JSON.parse(lis);
            for(var i = 0; i < li.length; i++) {
                if (li[i].capabilitycode == code) {
                    return li[i].capabilityvalue;
                }
            }
        }

        $(document).ready(function(){
            selectBaudRateOption();
            stopentersubmit();
            if('<s:property value="resultCode"/>'==30316){
                toast('<s:text name="repeatdsc" />');
            };
        });

        function stopentersubmit()
        {
            $("input").on('keypress',
                function(e)
                {
                    var key = window.event ? e.keyCode : e.which;
                    if(key.toString() == "13")
                        return false;
                }
            );
        }

        function onSubmit(){
            if(!validateZwaveName($("#name"))){
                return;
            };

            var data = $('#formSumbit').serialize();
            var message = '<s:text name="devicenamecrash" />';
            if(validate(data,true,message)) {
                Submit();
            }
        }

        //效验设备名
        function validateZwaveName(obj){
            var name = $(obj).val();
            var zwavedeviceid = $("#zwavedeviceid").val();
            if(name == null || name == ""){
                toast('<s:text name="devicenameisrequired" />');
                return false;
            }
            if(name.match(regRule)) {
                name = name.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
                $(obj).val(name);
                toast('<s:text name="wildcharnotsupport" />');
                return false;
            }

            var data = {"name":name,"zwavedeviceid":zwavedeviceid};
            var message = '<s:text name="devicenamecrash" />';
            return validate(data,false,message);
        }

        function Submit(){
            $.ajax({
                type: "post",
                url:"/iremote/device/setdeivityname",
                data:$('#formSumbit').serialize(),
                success : function(data) {
                    if ( data.resultCode == 0 ){
                        if(JSInterface.sumbit && typeof(JSInterface.sumbit) == "function"){
                            JSInterface.sumbit();
                        }else{
                            JSInterface.back();
                        }
                    }
                    else if ( data.resultCode == 30316)
                        toast('<s:text name="repeatdsc" />');
                    else if (data.resultCode == 30312)
                        toast('<s:text name="gatewayoffline" />');
                    else
                        toast('<s:text name="errorraise" />');
                },
                error : function(data) {
                    toast('<s:text name="errorraise" />');
                }
            })
        }

        function validate(data,flag,message){
            var isExist = false;
            $.ajax({
                type: "post",
                url:"/iremote/device/validateName",
                data:data,
                async: false,
                success : function(data) {
                    if(data.resultCode == 0){
                        isExist = true;
                    }else{
                        toast(message);
                    }
                },
                error : function(data) {
                    toast('<s:text name="errorraise" />');
                }
            })
            return isExist;
        }
    </script>

</head>
<body class="listpage">
<table class="title_table">
    <tr>
        <td align="left" width="40px"><a href="javascript:onback(0);"><img src="/iremote/images/nav/nav_btn_back.png"  class="nav_img"/></a></td>
        <td align="center"><span class="title_c"><s:text name="title" /></span></td>
        <td align="right" width="40px" ><a href="javascript:onSubmit(this);"><img src="/iremote/images/nav/nav_btn_preservation.png"  class="nav_img"/></a></td>
    </tr>
</table>

<form id="formSumbit" method="post">
    <input type="hidden" name="zwavedeviceid" id="zwavedeviceid" value="<s:property value='zwaveDevice.zwavedeviceid'/>"/>
    <input type="hidden" name="capabilityHolder" id="capabilityHolder" value="<s:property value='capabilitys'/>"/>
    <table width="100%" style="background:#ffffff;margin-top:5px;">
        <tr height="40px">
            <td align="right" nowrap="nowrap" width="5%">
                <span class="content_font"><s:text name="name" />:</span>
            </td>
            <td align="left" colspan=4 width="95%">
                <div>
                    <input type="text" class="input_text" id="name" name="name" maxlength="32" value="<s:property value="zwaveDevice.name"/>"/>
                </div>
            </td>
        </tr>

        <tr height="40px">
            <td align="right" nowrap="nowrap" >
                <span class="content_font"><s:text name="baudrate" />:</span>
            </td>
            <td align="left" colspan=4 width="95%">
                <div>
                    <select id="baudrate" name="baudrate" class="input_option">
                        <option value='2400'>2400</option>
                        <option value='4800'>4800</option>
                        <option value='9600'>9600</option>
                        <option value='19200'>19200</option>
                        <option value='38400'>38400</option>
                        <option value='57600'>57600</option>
                        <option value='115200'>115200</option>
                    </select>
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>