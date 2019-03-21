package com.iremote.action.gateway;

import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.constant.GatewayAddZWaveDeviceSteps;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.service.DeviceOperationStepsService;
import com.opensymphony.xwork2.Action;

public class QueryStatusofAddZwavedeviceAction 
{
	private int resultCode ;
	private String deviceid ;
	private int status ;
	private Integer zwavedeviceid;
	
	public String execute()
	{
		DeviceOperationStepsService doss = new DeviceOperationStepsService();
		
		DeviceOperationSteps dos = doss.querybydeviceidandtype(deviceid,DeviceOperationType.addzWavedevice);
		if ( dos == null || dos.getExpiretime().getTime() < System.currentTimeMillis() - 15 * 1000 )
		{
			this.status = GatewayAddZWaveDeviceSteps.normal.getStep();
			return Action.SUCCESS;
		}

		this.status = dos.getStatus();
		if(dos.getZwavedeviceid() != null && this.status == GatewayAddZWaveDeviceSteps.finished.getStep()){
			this.zwavedeviceid = dos.getZwavedeviceid();
		}
		return Action.SUCCESS;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public int getStatus() {
		return status;
	}

	public Integer getZwavedeviceid() {
		return zwavedeviceid;
	}
}
