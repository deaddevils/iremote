package com.iremote.action.gateway;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.Remote;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.service.RemoteService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "admin", parameter = "deviceid")
public class ForbiddenOrRecoverGatewayAction {

	protected int resultCode = ErrorCodeDefine.SUCCESS;
	private String deviceid;
	private String operate;

	public String execute() {
		
		RemoteService rs = new RemoteService();
		Remote remote = rs.querybyDeviceid(deviceid);
		if(remote==null){
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXIST;
			return Action.SUCCESS;
		}
		
		if ("forbidden".equals(operate)) {
			remote.setStatus(IRemoteConstantDefine.REMOTE_STATUS_FORBIDDEN);
			
			new Thread(new Runnable(){
				@Override
				public void run() {
					Utils.sleep(3000);
					for (IConnectionContext connection = ConnectionManager.getConnection(
							deviceid); connection != null; connection = ConnectionManager.getConnection(deviceid)) {
						ConnectionManager.removeConnection(connection);
					}
				}
				
			}).start();
		} else if("recover".equals(operate)) {
			remote.setStatus(IRemoteConstantDefine.REMOTE_STATUS_OFFLINE);
		}
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

}
