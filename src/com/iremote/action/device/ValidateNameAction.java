package com.iremote.action.device;

import java.util.List;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveSubDeviceService;
import com.opensymphony.xwork2.Action;

public class ValidateNameAction {
	private PhoneUser phoneuser ;
	private String name;
	private String switchsNames;
	private int channelnumber;
	private int zwavedeviceid;
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private List<String> lst;
	private ZWaveDevice zwavedevice;
	public String execute(){
		ZWaveDeviceService zds = new ZWaveDeviceService();
		lst = PhoneUserHelper.queryDeviceidbySharetoPhoneuserid(phoneuser.getPhoneuserid());
		zwavedevice = zds.query(zwavedeviceid);
		validateZwaveName();
		validateChannelName();
 		return Action.SUCCESS;
	}
	
	public void validateZwaveName(){
		if(name == null)
			return;
		
		if(name.trim().length() == 0){
			resultCode = ErrorCodeDefine.DEVICE_RETURN_ERROR;
			return;
		}
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> dlst = zds.queryByDeviceidAndNameAndNotInZwavedeviceid(lst , name , zwavedeviceid);
		if ( dlst != null && dlst.size() > 0 ){
			resultCode = ErrorCodeDefine.NAME_IS_EXIST;
		}
		return;
	}
	
	public void validateChannelName(){
		if(switchsNames == null)
			return;
		String[] switchsName = switchsNames.split(",");
		if(switchsName.length == 0){
			resultCode = ErrorCodeDefine.DEVICE_RETURN_ERROR;
			return;
		}
		/*for(int i = 0; i < switchsName.length; i++){
			ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
			switchsName[i] = switchsName[i].replaceAll(" ", "");
			List<ZWaveSubDevice> zsdls = zsds.querybydeviceidandNameAndNotInId(lst, switchsName[i],zwavedevice);
			if(zsdls != null && zsdls.size() > 1){
				resultCode = ErrorCodeDefine.NAME_IS_EXIST;
				break;
			}
		}*/
		
		return;
		
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSwitchsNames(String switchsNames) {
		this.switchsNames = switchsNames;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setChannelnumber(int channelnumber) {
		this.channelnumber = channelnumber;
	}
	
	
}
