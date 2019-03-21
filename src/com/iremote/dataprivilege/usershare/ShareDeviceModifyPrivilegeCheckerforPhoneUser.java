package com.iremote.dataprivilege.usershare;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.dataprivilege.zwavedevice.DeviceOperationPrivilegeChecker;
import com.iremote.domain.PhoneUser;
import com.iremote.service.CameraService;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;

public class ShareDeviceModifyPrivilegeCheckerforPhoneUser extends DeviceOperationPrivilegeChecker<PhoneUser>
{
	@Override
	public boolean checkprivilege()
	{
		if(user == null)
			return false;
		RemoteService rs = new RemoteService();
		List<String> device = rs.queryDeviceidbyPhoneUserid(user.getPhoneuserid()); 
		
		if((sharedevice == null || "0".equals(sharedevice) || "".equals(sharedevice)) && sharedevicetype == 0){
			return true;
		}
		
		JSONArray sharedeviceja = JSON.parseArray(sharedevice);
		
		List<Integer> zwavedeviceid = new ArrayList<Integer>();
		List<Integer> infrareddeviceid = new ArrayList<Integer>();
		List<Integer> cameraid = new ArrayList<Integer>();
		
		for(int i=0; i < sharedeviceja.size(); i++ ){
			JSONObject json = sharedeviceja.getJSONObject(i);
			if(json.getInteger("zwavedeviceid") != null && json.getInteger("zwavedeviceid") != 0){
				zwavedeviceid.add(json.getInteger("zwavedeviceid"));
			}
			if(json.getInteger("infrareddeviceid") != null && json.getInteger("infrareddeviceid") != 0){
				infrareddeviceid.add(json.getInteger("infrareddeviceid"));
			}
			if(json.getInteger("cameraid") != null && json.getInteger("cameraid") != 0){
				cameraid.add(json.getInteger("cameraid"));
			}
		}
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		InfraredDeviceService ids = new InfraredDeviceService();
		CameraService cs = new CameraService();
		
		if(( zwavedeviceid.size() != 0 && zds.queryCountBySharedeviceAndRemode(device, zwavedeviceid) != zwavedeviceid.size())){
			return false;
		}
		if((infrareddeviceid.size() != 0 && ids.queryCountBySharedeviceAndRemode(device, infrareddeviceid) != infrareddeviceid.size())){
			return false;
		}
		if((cameraid.size() != 0 && cs.queryCountBySharedeviceAndRemode(device, cameraid) != cameraid.size())){
			return false;
		}
		return true;
	}

}
