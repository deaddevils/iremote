package com.iremote.action.device.doorlock;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.encrypt.AES;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.BlueToothPassword;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.BlueToothPasswordService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class QueryLockpasswordPackageAction {
	private int zwavedeviceid;
	private String password;
	private PhoneUser phoneuser;
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private static Log log = LogFactory.getLog(QueryLockpasswordPackageAction.class);
	
	public String execute(){
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zwaveDevice = zds.query(zwavedeviceid);
		if(zwaveDevice == null){
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		BlueToothPasswordService blueService = new BlueToothPasswordService();
		BlueToothPassword bluetoothpassword = blueService.findByZwaveDeviceIdAndPhoneUserId(zwavedeviceid, phoneuser.getPhoneuserid());
		if(bluetoothpassword==null){
			log.info("can not find bluetoothpassword in db with zwavedeviceid:"+zwavedeviceid+" and phoneuserid:"+phoneuser.getPhoneuserid());
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		byte[] decrypt = AES.decrypt(bluetoothpassword.getPassword());
		password = Base64.encodeBase64String(decrypt);
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public String getPassword() {
		return password;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
	
}
