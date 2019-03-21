package com.iremote.action.device.doorlock;

import org.apache.commons.lang3.StringUtils;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;

public class ShowDoorlockMessageAction
{
	private String zwavedeviceid ;
	private String operation;
	private Boolean zwavedevice;
	
	public String execute()
	{
		IDoorlockOperationProcessor setter = (IDoorlockOperationProcessor)DoorlockOperationStore.getInstance().get(zwavedeviceid);
		
		String rst = null ;
		if ( setter == null )
		{
			if ( "delete".equals(operation))
				return DeleteDoorlockPasswordUser.STATUS[DeleteDoorlockPasswordUser.STATUS_FAILED];
			else if ("editdoorlockuser".equals(operation))
				return EditDoorlockUserAlarmAction.STATUS[EditDoorlockUserAlarmAction.STATUS_FAILED];
			else 
				return DoorlockPasswordUserSetter.STATUS[DoorlockPasswordUserSetter.STATUS_FAILED];
		}
				
		rst = setter.getMessage();
		
		if ( "gatewayoffline".equals(rst) && zwavedevice == null && StringUtils.isNotBlank(zwavedeviceid) )
		{
			ZWaveDeviceService zds = new ZWaveDeviceService();
			ZWaveDevice zd = zds.query(Integer.valueOf(zwavedeviceid));
			this.zwavedevice = DeviceHelper.isZwavedevice(zd);
		}
		
		return rst ;
	}

	public void setZwavedeviceid(String zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}

	public String getZwavedeviceid()
	{
		return zwavedeviceid;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	public String getOperation()
	{
		return operation;
	}

	public Boolean getZwavedevice() {
		return zwavedevice;
	}

	public void setZwavedevice(Boolean zwavedevice) {
		this.zwavedevice = zwavedevice;
	}


}
