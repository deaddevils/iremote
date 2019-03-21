package com.iremote.action.databackup;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Databackup;
import com.iremote.domain.User;
import com.iremote.service.DatabackupService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class DownloadInfoAction {
	private int resultCode = 0 ;
	private String filename;
	private String data;
	
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
		
		data = dbu.getData();
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public String getData() {
		return data;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
