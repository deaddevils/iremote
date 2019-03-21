<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<tr height="25px" >
	<td align="left" colspan=1>&nbsp;&nbsp;
	<s:text name="channeltype" />:
	</td>
	<td align="left" colspan=4>
		<select name="subdevicetype" style="width:120px">
			<option value=""><s:text name="None"/></option>
			<option value ="110"><s:text name="Fire"/></option>
			<option value ="100"><s:text name="Medical"/></option>
			<option value ="111"><s:text name="Smoke"/></option>
			<option value ="154"><s:text name="Waterleakage"/></option>
			<option value ="151"><s:text name="Gasdetected"/></option>
			<option value ="130"><s:text name="Burglary"/></option>
			<option value ="120"><s:text name="SOS"/></option>
			<option value ="131"><s:text name="Doorwindow"/></option>
			<option value ="132"><s:text name="Motion"/></option>
		</select>
	</td>
</tr>
<tr height="25px" >
<td align="left" colspan=1>&nbsp;&nbsp;
	<s:text name="belongsto" />:
	</td>
	
<td align="left"  colspan=4>
	<select name="belongsto" style="width:120px" >
		<option value ="1"><s:text name="partition"/> 1</option>
		<option value ="2"><s:text name="partition"/> 2</option>
		<option value ="3"><s:text name="partition"/> 3</option>
		<option value ="4"><s:text name="partition"/> 4</option>
		<option value ="5"><s:text name="partition"/> 5</option>
		<option value ="6"><s:text name="partition"/> 6</option>
		<option value ="7"><s:text name="partition"/> 7</option>
		<option value ="8"><s:text name="partition"/> 8</option>
	</select>
</td>
</tr>