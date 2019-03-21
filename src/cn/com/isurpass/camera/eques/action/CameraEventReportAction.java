package cn.com.isurpass.camera.eques.action;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.DahuaCameraReportType;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.CameraEvent;
import com.opensymphony.xwork2.Action;

public class CameraEventReportAction 
{
	protected int resultCode = ErrorCodeDefine.SUCCESS ;
	private int  method;
	private String  devid ;
	private String  fid ;
	private String action;
	
	public String execute()
	{
		if ( !"PUSH_MESSAGE".equals(action))
			return Action.SUCCESS;
		
		String type = "";
		if ( method == 2)
			type = DahuaCameraReportType.call.getType();
		else if ( method == 1 )
			type = DahuaCameraReportType.move.getType();
		else 
			return Action.SUCCESS ;
		
		JMSUtil.sendmessage(IRemoteConstantDefine.DAHUA_CAMERA_REPORT, new CameraEvent(devid , type));
		JMSUtil.commitmessage();
		
		return Action.SUCCESS;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setMethod(int method) {
		this.method = method;
	}
	public void setDevid(String devid) {
		this.devid = devid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
}
