package com.iremote.action.gateway;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.asycresponse.IAsyncResponse;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid")
public class SetWIFIAccountGatewayAction {
	private String deviceid;
	private String ssid;
	private String password;
	private int resultCode = ErrorCodeDefine.SUCCESS;
	
	public String execute(){
		if(StringUtils.isEmpty(deviceid)){
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		byte[] ssidby = ssid.getBytes();
		byte[] passby = password.getBytes();
		IAsyncResponse asynchronizeRequest = SynchronizeRequestHelper.asynchronizeRequest(deviceid, createCommandTlv(ssidby,passby), 0);
		//TODO wait to get response

		
		return Action.SUCCESS;
	}
	
	private CommandTlv createCommandTlv(byte[] ssidby,byte[] passby){
		CommandTlv ct = new CommandTlv(3 , 11) ;
		ct.addUnit(new TlvByteUnit(13 , ssidby));
		ct.addUnit(new TlvByteUnit(27 , passby));
		return ct;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getResultCode() {
		return resultCode;
	}
}
