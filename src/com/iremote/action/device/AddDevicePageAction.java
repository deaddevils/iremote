package com.iremote.action.device;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDevicePageAction
{
	private String deviceid ;
	private Map<Integer,String> nuids = new HashMap<Integer,String>() ;
	private PhoneUser phoneuser ;
	private String devicetype;

	public String execute()
	{
		nuidfromtype(devicetype);
		return Action.SUCCESS;
	}


	private void nuidfromtype(String type){
		ZWaveDeviceService zds = new ZWaveDeviceService();

		List<ZWaveDevice> lst = zds.querybydeviceid(deviceid);

		if(IRemoteConstantDefine.DEVICE_TYPE_BGM.equals(type)){
			for(int i = 128 ; i <= 176; i++ ){//0x80 to 0xB0
				nuids.put(Integer.valueOf(i),String.format("0x%02x", i));
			}
		}
		for ( ZWaveDevice zd : lst )
			nuids.remove(new Integer(zd.getNuid()));
	}

	public String getDeviceid()
	{
		return deviceid;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}


	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public Map<Integer, String> getNuids() {
		return nuids;
	}

	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
}
