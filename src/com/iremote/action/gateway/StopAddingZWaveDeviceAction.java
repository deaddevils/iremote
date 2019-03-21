package com.iremote.action.gateway;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.constant.GatewayAddZWaveDeviceSteps;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.DeviceOperationStepsService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid")
public class StopAddingZWaveDeviceAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String deviceid ;
	
	public String execute()
	{
		if ( !ConnectionManager.isOnline(deviceid))
		{
			this.resultCode = ErrorCodeDefine.TRANSOFFLINE;
			return Action.SUCCESS;
		}
		
		CommandTlv ct = new CommandTlv(TagDefine.COMMAND_CLASS_DEVICE_MANAGER , TagDefine.COMMAND_SUB_CLASS_STOP_ZWAVEDEVICE_ADDING_PROGRESS_REQUEST);

		byte[] rst = SynchronizeRequestHelper.synchronizeRequest(deviceid, ct, 1);
		
		if ( rst == null )
		{
			this.resultCode = ErrorCodeDefine.TIME_OUT;
			return Action.SUCCESS;
		}
		
		Integer ir = TlvWrap.readInteter(rst, TagDefine.TAG_RESULT, TagDefine.TAG_HEAD_LENGTH);

		if ( ir == null )
		{
			this.resultCode = ErrorCodeDefine.TIME_OUT;
			return Action.SUCCESS;
		}
		
		this.resultCode = ir ;
		if ( this.resultCode != ErrorCodeDefine.SUCCESS)
			return Action.SUCCESS;
		
		DeviceOperationStepsService doss = new DeviceOperationStepsService();
		DeviceOperationSteps dos = doss.querybydeviceidandtype(deviceid,DeviceOperationType.addzWavedevice);
		
		if ( dos != null )
		{
			dos.setStatus(GatewayAddZWaveDeviceSteps.cancled.getStep());
			dos.setAppendmessage(null);
		}
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
}
