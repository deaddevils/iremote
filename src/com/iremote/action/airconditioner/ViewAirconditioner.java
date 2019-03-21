package com.iremote.action.airconditioner;

import java.util.ArrayList;
import java.util.List;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.GatewayCapabilityType;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class ViewAirconditioner {
	private String deviceid;
	private List<ZWaveDevice> zwLsit = new ArrayList<ZWaveDevice>();
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int capabilitycode;
	
	public String execute(){
		return Action.SUCCESS;
	}

	public List<ZWaveDevice> getZwLsit(){
		
		GatewayCapabilityType gct = GatewayCapabilityType.valueof(capabilitycode);
		if ( gct == null )
			return zwLsit;

		List<ZWaveDevice> list = null;
		if(deviceid != null){
			ZWaveDeviceService zwService = new ZWaveDeviceService();
			list = zwService.queryDeviceByDeviceType(deviceid ,new String[]{String.valueOf(gct.getInnerdevicetype()) , String.valueOf(gct.getOutdevicetype())} );
		}
		
		
		if(list != null){
			for(ZWaveDevice zwaveDevice : list){
				int outid = -1;
				if(zwaveDevice.getDevicetype().equals(String.valueOf(gct.getOutdevicetype()))){
					zwLsit.add(zwaveDevice);
					int nuid = zwaveDevice.getNuid();
					outid = (nuid & 0xff00) / 256;
					for(ZWaveDevice zd : list){
						if(zd.getDevicetype().equals(String.valueOf(gct.getInnerdevicetype()))){
							int id = (zd.getNuid() & 0xff00) / 256 ;
							if(id == outid){
								zwLsit.add(zd);
							}
						}
					}
				}
			}
		}
		return zwLsit;
	}
	
	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setCapabilitycode(int capabilitycode)
	{
		this.capabilitycode = capabilitycode;
	}
	
}
