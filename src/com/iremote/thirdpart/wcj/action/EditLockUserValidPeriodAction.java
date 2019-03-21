package com.iremote.thirdpart.wcj.action;

import org.apache.commons.lang3.StringUtils;
import com.iremote.action.helper.DoorlockHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class EditLockUserValidPeriodAction {
	private int zwavedeviceid;
	private int usertype;
	private byte usercode;
	private String validfrom;
	private String validthrough;
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private ZWaveDevice lock;
	
	public String execute(){
	
		ZWaveDeviceService zds = new ZWaveDeviceService();
		lock = zds.query(zwavedeviceid);
		
		if ( check() == false )
			return Action.SUCCESS;
		if( checkparameter() == false){
			return Action.SUCCESS;
		}
		if (StringUtils.isBlank(validfrom)) 
			validfrom = "2010-01-01 00:00:00";
		if (StringUtils.isBlank(validthrough))
			validthrough = "2099-01-01 00:00:00";
		this.resultCode = DoorlockHelper.sendTimeConfigure(lock.getDeviceid(), lock.getNuid(), (byte)usertype, usercode, validfrom, validthrough, true);
		
		return Action.SUCCESS;
	}
	
	private boolean check(){	
		
		if ( lock == null ){
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return false;
		}		
		
		if ( lock.getStatus() != null && lock.getStatus() == -1 ){
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}	
		
		if ( ConnectionManager.isOnline(lock.getDeviceid()) == false ){
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}
		
		return true;
	}
	private boolean checkparameter(){
		if(usertype!=21&&usertype!=22&&usertype!=32){
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return false;
		}
		if(usercode<1||usercode>255){
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return false;
		}
		
		return true;
	}
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
	public void setUsercode(byte usercode) {
		this.usercode = usercode;
	}
	public void setValidfrom(String validfrom) {
		this.validfrom = validfrom;
	}
	public void setValidthrough(String validthrough) {
		this.validthrough = validthrough;
	}
	public int getResultCode() {
		return resultCode;
	}
	
}
