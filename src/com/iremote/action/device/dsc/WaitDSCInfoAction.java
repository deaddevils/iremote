package com.iremote.action.device.dsc;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.DeviceExtendInfo;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.service.DeviceExtendInfoService;
import com.iremote.service.ZWaveDeviceService;

public class WaitDSCInfoAction
{
	private String deviceid ;
	private String name;
	private String password;
	private ZWaveDevice zwaveDevice ;
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int zwavedeviceid;
	private PhoneUser phoneuser;
	private int platform;
	public String execute(){
		ZWaveDeviceService zds = new ZWaveDeviceService();
		zwaveDevice = zds.querybydeviceid(deviceid, 11401);
		
		if ( zwaveDevice != null ){
			this.resultCode = ErrorCodeDefine.DEVICE_HAS_EXIST;
			return "back";
		}
		platform = phoneuser.getPlatform();
		ZWaveDevice zd = new ZWaveDevice();
		zd.setDeviceid(deviceid);
		zd.setNuid(11401);
		zd.setBattery(100);
		zd.setDevicetype(IRemoteConstantDefine.DEVICE_TYPE_DSC);
		zd.setStatus(0);
		if(StringUtils.isNotBlank(name)){
			zd.setName(name.trim());
		}else{
			zd.setName("DSC");
		}
		zd.setApplianceid(Utils.createtoken());
		zwavedeviceid = zds.save(zd);
		savePassword();
		CommandTlv commandTlv = new CommandTlv(107,1);
		byte [] bytes = "00191".getBytes();
        byte [] endString = new byte[]{0x0D, 0x0A};
        byte [] newBytes = new byte[bytes.length + endString.length];
        System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
        System.arraycopy(endString, 0, newBytes, bytes.length, endString.length);

        commandTlv.addUnit(new TlvByteUnit(93,newBytes));
        SynchronizeRequestHelper.asynchronizeRequest(deviceid, commandTlv, 1);
		return "dsc";
	}
	private void savePassword()
	{
		if(phoneuser.getPlatform()==9&&"undefined".equals(password))
			password="123456";
		if ( StringUtils.isBlank(password))
			return ;
	
		for ( ; password.length() < 6 ;)
			password += "0";
		
		DeviceExtendInfoService dis = new DeviceExtendInfoService();
		List<DeviceExtendInfo> dil = dis.querybyzwavedeviceid(zwavedeviceid);
		
		if ( dil == null || dil.size() == 0 )
		{
			DeviceExtendInfo dei = new DeviceExtendInfo();
			dei.setDevicepassword(password);
			dei.setZwavedeviceid(this.zwavedeviceid);
			
			dis.save(dei);
			return ;
		}
		
		for ( DeviceExtendInfo dei : dil)
			if ( StringUtils.isNotBlank(dei.getDevicepassword()))
			{
				dei.setDevicepassword(password);
				return ;
			}
		dil.get(0).setDevicepassword(password);
	}
	public String getDeviceid(){
		return deviceid;
	}

	public void setDeviceid(String deviceid){
		this.deviceid = deviceid;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
	public int getPlatform() {
		return platform;
	}
	
}