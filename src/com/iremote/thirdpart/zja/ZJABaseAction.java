package com.iremote.thirdpart.zja;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.Camera;
import com.iremote.service.CameraService;
import com.opensymphony.xwork2.Action;

public abstract class ZJABaseAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	protected String applianceuuid ;
	protected String reporttime;
	protected Camera camera;
	
	public String execute()
	{
		CameraService cs = new CameraService();
		camera = cs.querybyapplianceuuid(applianceuuid);
		if ( camera == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		resultCode = process();
		
		return Action.SUCCESS;
	}

	public abstract int process();
	
	public int getResultCode() 
	{
		return resultCode;
	}

	public void setApplianceuuid(String applianceuuid) {
		this.applianceuuid = applianceuuid;
	}

	public void setReporttime(String reporttime) {
		this.reporttime = reporttime;
	}
}
