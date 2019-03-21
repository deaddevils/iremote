package com.iremote.action.device;

import java.util.List;

import com.iremote.common.constant.GatewayCapabilityType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntArray;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

//Add air condition from web GUI.
@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid")
public class AddDeviceAction
{
	private String deviceid ;
	private int[] nuid;
	private String message;
	private int capabilitycode;
	
	public String execute() 
	{
		GatewayCapabilityType gct = GatewayCapabilityType.valueof(capabilitycode);
		if ( gct == null )
			return Action.ERROR;
		ZWaveDeviceService zwDeviceService = new ZWaveDeviceService();
		
		List<ZWaveDevice> zwList = zwDeviceService.queryDeviceByDeviceType(deviceid ,new String[]{String.valueOf(gct.getInnerdevicetype()) , String.valueOf(gct.getOutdevicetype())} );
		Integer len = zwList.size();
		if(len != null && len > 0){
			nuid = new int[len];
			for(int i = 0; i < len; i++ ){
				nuid[i] = zwList.get(i).getNuid();
			}
		}
		
		CommandTlv ct = new CommandTlv(105 ,1 );
		
		ct.addUnit(new TlvIntArray(83 , nuid , 4));
		
		byte[] rp = SynchronizeRequestHelper.synchronizeRequest(deviceid, ct , 8);
		
		if ( rp == null )
			message = "synchronize fail";
	   	else 
	   		message = "synchronize success";
	   		
	   	return Action.SUCCESS;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getMessage() {
		return message;
	}

	public void setCapabilitycode(int capabilitycode)
	{
		this.capabilitycode = capabilitycode;
	}

	
}
