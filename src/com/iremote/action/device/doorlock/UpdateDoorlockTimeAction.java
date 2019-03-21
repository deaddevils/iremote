package com.iremote.action.device.doorlock;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.iremote.action.helper.GatewayHelper;
import com.iremote.action.helper.PhoneUserBlueToothHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.NumberUtil;
import com.iremote.common.encrypt.AES;
import com.iremote.common.encrypt.Tea;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DeviceCapabilityService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class UpdateDoorlockTimeAction {
	
	private int zwavedeviceid;
	private String timepackage;
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private static Log log = LogFactory.getLog(UpdateDoorlockTimeAction.class);
	
	public String execute(){
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zwaveDevice = zds.query(zwavedeviceid);
		if(zwaveDevice == null){
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		String deviceid = zwaveDevice.getDeviceid();
		String remoteTimezoneId = GatewayHelper.getRemoteTimezoneId(deviceid);
		if(remoteTimezoneId==null){
			log.error("can not find the timezoneid of deviceid:"+deviceid);
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		ZoneId zone = null ;
		try{
			zone = ZoneId.of(remoteTimezoneId);
		}catch(Exception e){
			log.error("The timezoneid:"+remoteTimezoneId+" of deviceid:"+deviceid+" is not available!");
			resultCode = ErrorCodeDefine.UNKNOW_ERROR;
			return Action.SUCCESS;
		}
		
		LocalDateTime localTime = LocalDateTime.now(zone);
		int year = localTime.getYear();
		int month = localTime.getMonthValue();
		int day = localTime.getDayOfMonth();
		int hour = localTime.getHour();
		int min = localTime.getMinute();
		int sec = localTime.getSecond(); 
		
		DeviceCapabilityService dcs = new DeviceCapabilityService();
		DeviceCapability macaddressca = dcs.query(zwavedeviceid, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_MAC_ADDRESS);
		DeviceCapability snca = dcs.query(zwavedeviceid, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_SEQUENCE);
		DeviceCapability key2ca = dcs.query(zwavedeviceid, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_2);
		if (macaddressca == null || snca == null || key2ca == null
				|| StringUtils.isBlank(macaddressca.getCapabilityvalue())
				|| StringUtils.isBlank(snca.getCapabilityvalue())
				|| StringUtils.isBlank(key2ca.getCapabilityvalue())) {
			log.info("can not find capability by macaddress or sn or key2 !");
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		String macvalue = macaddressca.getCapabilityvalue();
		String mac = macvalue.replaceAll(":", "");
		byte[] macbytes = NumberUtil.hexToBytes(mac);
		byte[] snbytes = NumberUtil.intToByte4(Integer.parseInt(snca.getCapabilityvalue()));
		byte[] yearbytes = new byte[]{(byte)(year/256),(byte)(year%256)};
		byte[] timePacket = new byte[18];
		System.arraycopy(macbytes, 0, timePacket, 0, 6);
		System.arraycopy(snbytes, 0, timePacket, 6, 4);
		System.arraycopy(yearbytes, 0, timePacket, 10, 2);
		System.arraycopy(new byte[]{(byte)month}, 0, timePacket, 12, 1);
		System.arraycopy(new byte[]{(byte)day}, 0, timePacket, 13, 1);
		System.arraycopy(new byte[]{(byte)hour}, 0, timePacket, 14, 1);
		System.arraycopy(new byte[]{(byte)min}, 0, timePacket, 15, 1);
		System.arraycopy(new byte[]{(byte)sec}, 0, timePacket, 16, 1);
		PhoneUserBlueToothHelper.addParityBit(timePacket);

		byte[] key2bytes = AES.decrypt(key2ca.getCapabilityvalue());
		
		byte[] encryptByTea = Tea.encryptByTea(timePacket, key2bytes, 16);
		
		timepackage = Base64.encodeBase64String(encryptByTea);
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public String getTimepackage() {
		return timepackage;
	}

}
