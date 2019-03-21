<%@ page import="java.util.*,com.iremote.domain.ThirdPart" language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Cooperative partner</title>
</head>
<body>
  <form action="cooperativeincrease" method="post">
    <table align="center" width="80%" ><!-- style="border-collapse:separate; border-spacing:15px;" -->
      <tr align="center" style="font-size: 170%"><td colspan="5">合&nbsp;作&nbsp;伙&nbsp;伴<br></td></tr>
      <tr style="font-size: 130%">
         <td>代码</td><td>名称</td><td>平台</td><td>管理员前缀</td><td>创建时间</td>
      </tr>
     <s:iterator value="list" id="gr">
      <tr>
         <td>
           <s:property value="#gr.code"/>
         </td>
         <td>
           <s:if test="#gr.name==null||#gr.name==''">空</s:if>
           <s:else><s:property value="#gr.name"/></s:else>
         </td>
         <td>
            <s:property value="#gr.platform"/>
         </td>
         <td>
           <s:if test="#gr.adminprefix==null||#gr.adminprefix==''">无</s:if>
           <s:else><s:property value="#gr.adminprefix"/></s:else>
         </td>
         <td>
           <fmt:formatDate value="${gr.createtime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
         </td>
      </tr>
     </s:iterator>
      <tr align="center"><td colspan="5"><br><input type="submit" value="增加"></td></tr>
    </table> 
  </form>
</body>
</html>



