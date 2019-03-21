package com.iremote.action.device;

import com.iremote.action.helper.AddDevice2RemoteHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid")
public class AddDevice2RemoteAction{

	private int resultCode = ErrorCodeDefine.SUCCESS ;

	private String deviceid;

	public String execute(){
		AddDevice2RemoteHelper helper = new AddDevice2RemoteHelper();
		helper.addSpecialDevice2Remote(deviceid);
		resultCode = helper.getResultCode();
	
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setDeviceid(String deviceid){
		this.deviceid = deviceid;
	}

}
