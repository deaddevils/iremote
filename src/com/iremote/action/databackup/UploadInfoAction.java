package com.iremote.action.databackup;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Databackup;
import com.iremote.domain.User;
import com.iremote.service.DatabackupService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class UploadInfoAction {

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
			dbu = new Databackup();
			dbu.setData(data);
			dbu.setFilename(filename);
			dbu.setUserid(user.getUserid());
			
			svr.save(dbu);
		}
		else
			dbu.setData(data);
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setData(String data) {
		this.data = data;
	}

}
