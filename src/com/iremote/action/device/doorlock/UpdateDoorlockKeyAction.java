package com.iremote.action.device.doorlock;

import com.iremote.action.helper.PhoneUserBlueToothHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.common.encrypt.Tea;
import com.iremote.common.jms.JMSUtil;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.*;
import com.iremote.service.BlueToothPasswordService;
import com.iremote.service.DeviceCapabilityService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class UpdateDoorlockKeyAction {
	private int zwavedeviceid;
	private String keypackage;
	private String testcontent;
	private String key3;
	private PhoneUser phoneuser;
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private static Log log = LogFactory.getLog(UpdateDoorlockKeyAction.class);
	
	public String execute(){
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zwaveDevice = zds.query(zwavedeviceid);
		if(zwaveDevice == null){
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}

		DeviceCapability key1Capability = PhoneUserBlueToothHelper.getCapability(zwaveDevice, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_1);
		DeviceCapability key2Capability = PhoneUserBlueToothHelper.getCapability(zwaveDevice, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_2);
		DeviceCapability key3Capability = PhoneUserBlueToothHelper.getCapability(zwaveDevice, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_3);
		DeviceCapability macAddressCapability = PhoneUserBlueToothHelper.getCapability(zwaveDevice, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_MAC_ADDRESS);
		DeviceCapability sequence = PhoneUserBlueToothHelper.getCapability(zwaveDevice, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_SEQUENCE);

		if (!checkParameters(key1Capability, key2Capability, key3Capability, macAddressCapability, sequence)){
			return Action.SUCCESS;
		}

		byte[] key1 = AES.decrypt(key1Capability.getCapabilityvalue());

		byte[] newkey2bytes = Utils.createsecuritykey(16);
		byte[] newkey3bytes = Utils.createsecuritykey(16);

//        byte[] key2 = Tea.encrypt(newkey2bytes, 0, key1, 32);
//        byte[] key3 = Tea.encrypt(newkey3bytes, 0, key1, 32);

        this.key3 = Base64.encodeBase64String(newkey3bytes);

		key2Capability.setCapabilityvalue(AES.encrypt(newkey2bytes));
		key3Capability.setCapabilityvalue(AES.encrypt(newkey3bytes));

		// capability value will change in method: PhoneUserBlueToothHelper.modifyBlueToothPassword
		sequence.setCapabilityvalue(String.valueOf(0));

        PhoneUserBlueToothHelper.modifyBlueToothDevicePassword(Arrays.asList(zwaveDevice), null);

        phoneuser.setLastupdatetime(new Date());
        PhoneUserHelper.sendInfoChangeMessage(phoneuser);

		if (createKeyPackage(zwaveDevice, macAddressCapability, key1, newkey2bytes, newkey3bytes)
				&& queryBlueToothPasswordPackage()) {
			writeLog(zwaveDevice);
		}
		return Action.SUCCESS;
	}

	private void writeLog(ZWaveDevice zwaveDevice) {
		Notification notification = new Notification();
		notification.setZwavedeviceid(zwaveDevice.getZwavedeviceid());
		notification.setPhoneuserid(phoneuser.getPhoneuserid());
		notification.setDevicetype(zwaveDevice.getDevicetype());
		notification.setDeviceid(zwaveDevice.getDeviceid());
		notification.setReporttime(new Date());
		notification.setMessage(IRemoteConstantDefine.NOTIFICATION_TYPE_BLUE_TOOTH_KEY_REFRESH);
		notification.setName(zwaveDevice.getName());
		notification.setMajortype(zwaveDevice.getMajortype());
		notification.setAppendmessage(phoneuser.getPhonenumber());
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
	}

	protected boolean checkParameters(DeviceCapability key1Capability
			,DeviceCapability key2Capability
			,DeviceCapability key3Capability
			,DeviceCapability macAddressCapability
			,DeviceCapability sequence) {

		if (key1Capability == null || key2Capability == null
				|| key3Capability == null || sequence == null || macAddressCapability == null
				|| StringUtils.isBlank(key1Capability.getCapabilityvalue()) || StringUtils.isBlank(key2Capability.getCapabilityvalue())
				|| StringUtils.isBlank(key3Capability.getCapabilityvalue()) || StringUtils.isBlank(sequence.getCapabilityvalue())
				|| StringUtils.isBlank(macAddressCapability.getCapabilityvalue())) {
			log.error("can not find capability by zwavedeviceid : " + zwavedeviceid);
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return false;
		}
		return true;
	}

	protected boolean createKeyPackage(ZWaveDevice zwaveDevice, DeviceCapability macAddressCapability, byte[] key1, byte[] key2, byte[] key3) {
		RemoteService rs = new RemoteService();
		Remote remote = rs.querybyDeviceid(zwaveDevice.getDeviceid());
		String zoneid;
        if (remote.getTimezone() == null || remote.getTimezone().getId() == null) {
            zoneid = TimeZone.getDefault().getID();
        } else {
            zoneid = remote.getTimezone().getId();
        }
		byte[] keypackage0 = PhoneUserBlueToothHelper.createBlueToothPasswordPackets(
				key2, key3, macAddressCapability.getCapabilityvalue(), zoneid);
		byte[] keypackage1 = Tea.encryptByTea(keypackage0, key1);
		keypackage = Base64.encodeBase64String(keypackage1);

		return true;
	}

	private boolean queryBlueToothPasswordPackage() {
		BlueToothPassword password = new BlueToothPasswordService().findByZwaveDeviceIdAndPhoneUserId(zwavedeviceid, phoneuser.getPhoneuserid());
		if (password == null || password.getPassword() == null) {
			resultCode = ErrorCodeDefine.GATEWAY_BUSSING;
			return false;
		}
		testcontent = Base64.encodeBase64String(AES.decrypt(password.getPassword()));
		return true;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

    public String getKey3() {
        return key3;
    }

    public String getKeypackage() {
		return keypackage;
	}

	public String getTestcontent() {
		return testcontent;
	}
	
}
