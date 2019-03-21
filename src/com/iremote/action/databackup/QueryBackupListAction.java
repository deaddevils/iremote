package com.iremote.action.databackup;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.User;
import com.iremote.service.DatabackupService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class QueryBackupListAction {

	private int resultCode = 0 ;
	private String[] filename = new String[0];
	
	public String execute()
	{
		User user = (User) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		DatabackupService svr = new DatabackupService();
		filename = svr.queryFilenameList(user.getUserid());
		
		return Action.SUCCESS;
	}
	public int getResultCode() {
		return resultCode;
	}
	public String[] getFilename() {
		return filename;
	}
}
