package com.iremote.action.databackup;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Databackup;
import com.iremote.domain.User;
import com.iremote.service.DatabackupService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class DeleteInfoAction {
	private int resultCode = 0 ;
	private String filename;

	public String execute()
	{
		User user = (User) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		DatabackupService svr = new DatabackupService();
		Databackup dbu = svr.loadDatabackup(user.getUserid(), filename);
		
		if ( dbu == null )
		{
			resultCode = ErrorCodeDefine.USE_BACKUP_DATA_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		svr.delete(dbu);
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
