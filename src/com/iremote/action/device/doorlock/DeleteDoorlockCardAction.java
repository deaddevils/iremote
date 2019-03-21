package com.iremote.action.device.doorlock;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DoorlockUserService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class DeleteDoorlockCardAction 
{
	private ZWaveDevice zWaveDevice;
	private int zwavedeviceid;
	private int usercode;
	private DoorlockUser doorlockUser;	

	private int resultCode = ErrorCodeDefine.SUCCESS;
	
	public String execute() throws InterruptedException
	{
		if(!getObject())
			return Action.SUCCESS;
		
		if ( doorlockUser == null )
			return Action.SUCCESS; 
		
		if ( StringUtils.isNotBlank(zWaveDevice.getProductor())
				&& zWaveDevice.getProductor().toLowerCase().startsWith(IRemoteConstantDefine.JWZH_CHUANGJIA_PRODUCTOR_PREFIX.toLowerCase()))
		{
			ChuangjiaDeleteDoorlockCardAction action = new ChuangjiaDeleteDoorlockCardAction();
			action.setzWaveDevice(zWaveDevice);
			action.setDoorlockUser(doorlockUser);
			
			String rst = action.execute();
			this.resultCode = action.getResultCode();
			
			return rst ;
		}
		else 
		{
			JwzhDeleteDoorlockCardAction action = new JwzhDeleteDoorlockCardAction();
			action.setzWaveDevice(zWaveDevice);
			action.setDoorlockUser(doorlockUser);
			
			String rst = action.execute();
			this.resultCode = action.getResultCode();
			
			return rst ;
		}

	}
	
	
	private boolean getObject(){
		DoorlockUserService doorlockUserService = new DoorlockUserService();
		doorlockUser = doorlockUserService.query(zwavedeviceid,IRemoteConstantDefine.DOOR_LOCK_USERTYPE_CARD,usercode);
		
		if(doorlockUser == null){
			return true;
		}
		
		ZWaveDeviceService zwDeviceService = new ZWaveDeviceService();
		zWaveDevice = zwDeviceService.query(doorlockUser.getZwavedeviceid());
		
		if(zWaveDevice == null){
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return false;
		}
		
		return true;
	}


	public int getResultCode() {
		return resultCode;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setUsercode(int usercode) {
		this.usercode = usercode;
	}


}
