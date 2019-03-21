package com.iremote.action.notification;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.ThirdPart;
import com.iremote.service.ThirdPartService;
import com.opensymphony.xwork2.Action;


public class QueryThirdPartAction{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private List<ThirdPart> list ;
	
	public String execute(){
		HttpServletRequest request = ServletActionContext.getRequest();
		ThirdPartService thirdpartservice = new ThirdPartService();
		list = thirdpartservice.query();
		request.setAttribute("list", list);
		return Action.SUCCESS;
	} 
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public List<ThirdPart> getList() {
		return list;
	}
	public void setList(List<ThirdPart> list) {
		this.list = list;
	}


}
