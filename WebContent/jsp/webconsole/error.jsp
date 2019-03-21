<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="/jsp/language.jsp"/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="/iremote/js/jquery-1.9.0.min.js"></script>
    <title>操作失败</title>
</head>
<style type="text/css">
    .tip{
        width: 100%;
        height: 100%;
        text-align:center;
    }
    .tip p{
        font-size:16px ;
    }
</style>

<script type="text/javascript">
    $(function(){
        var resultCode = $("#resultCode").val();
        var errorstr = getErrorMsg(resultCode)
        if(errorstr == null || errorstr == "" || errorstr == undefined){
            $("#error").text("错误码：" + resultCode)
        }else{
            $("#error").text(errorstr)
        }
    })

    function onback()
    {
        JSInterface.back();
    }

</script>
<body>
<table width="100%">
    <tr>
        <td align="left" width="40px"><a href="javascript:onback(0);"><img src="/iremote/images/nav/nav_btn_back.png"  class="nav_img"/></a></td>
        <td align="center"></td>
        <td align="right" width="40px" >&nbsp;</td>
    </tr>
</table>


<input type="hidden" id="resultCode" value="<s:property value='resultCode'/>">
<div class="tip">
    <p id="error"></p>
</div>
</body>
</html>