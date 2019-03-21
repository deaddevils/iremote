package com.iremote.action.device;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.iremote.action.device.doorlock.DoorlockOperationStore;
import com.iremote.action.device.doorlock.DoorlockUpgradeProcessor;
import com.iremote.action.device.doorlock.IDoorlockOperationProcessor;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class DeviceUpgradeAction
{
	private static Log log = LogFactory.getLog(DeviceUpgradeAction.class);
	
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int zwavedeviceid;
	
	public String execute()
	{
		IDoorlockOperationProcessor setter = DoorlockOperationStore.getInstance().get(String.valueOf(zwavedeviceid));
		
		if ( setter != null && !setter.isFinished() )
		{
			if ( log.isInfoEnabled())
				log.info(JSON.toJSONString(setter));
			resultCode = ErrorCodeDefine.NO_PRIVILEGE_3;
			return Action.SUCCESS;
		}
		
		setter = new DoorlockUpgradeProcessor(zwavedeviceid);
		
		setter.init();
		
		if ( setter.getStatus() == DoorlockUpgradeProcessor.DeviceStatus.gatewayoffline.ordinal())
			this.resultCode = ErrorCodeDefine.WAKEUP_DEVICE;
		else 
			setter.sendcommand();
		
		if (DoorlockUpgradeProcessor.DeviceStatus.failed.ordinal() == setter.getStatus())
			this.resultCode = ErrorCodeDefine.UNKNOW_ERROR;
		
		return Action.SUCCESS;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}
	
	
}
