package com.iremote.action.airconditioner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Utils;
import com.iremote.common.constant.GatewayCapabilityType;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class AddAirconditionerAciton {
	private Integer zwavedeviceid;
	private String deviceid; 
	private int outid ;
	private int id ;
	private Integer devicetype; 
	private String name ;
	private String message="";
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private PhoneUser phoneuser ;
	private int capabilitycode;
	private int innerdevicetype ;
	private int outdevicetype;
	
	public String execute()
	{
		GatewayCapabilityType gct = GatewayCapabilityType.valueof(capabilitycode);
		if ( gct == null )
			return Action.ERROR;
		
		this.innerdevicetype = gct.getInnerdevicetype();
		this.outdevicetype = gct.getOutdevicetype();
		
		saveZwavedvice();
		
		if ( phoneuser != null )
		{
			phoneuser.setLastupdatetime(new Date());
			PhoneUserHelper.sendInfoChangeMessage(phoneuser);
		}
		return Action.SUCCESS; 
	}

	public void saveZwavedvice(){
		ZWaveDeviceService zwaveDeviceService = new ZWaveDeviceService();
		ZWaveDevice zwaveDevice = null;
		Integer nuid = null;

		if(deviceid == null ){
			message = "Deviceid is not null";
			return;
		}
		if(devicetype == null ){
			message = "ZWave is not null";
			return;
		}	
		if(zwavedeviceid != null && zwavedeviceid > 0){
			zwaveDevice = zwaveDeviceService.query(zwavedeviceid);
		}else{
			zwaveDevice = new ZWaveDevice();
			zwaveDevice.setDeviceid(deviceid);
			
			zwaveDevice.setStatus(0);
			if(devicetype.equals(innerdevicetype)){
				zwaveDevice.setStatuses(Utils.getDeviceDefaultStatuses(innerdevicetype));
				nuid = 0x200000 | ( outid * 256 ) | id;
			}else if(devicetype.equals(outdevicetype)){
				zwaveDevice.setStatuses("[]");
				nuid = 0x100000 | ( id * 256 );
			}
			List<ZWaveDevice> list = zwaveDeviceService.queryDeviceByNuidType(deviceid,nuid,new String[]{String.valueOf(this.innerdevicetype) , String.valueOf(this.outdevicetype)});
			if(list != null && list.size() > 0){
				message = "id exists";
				return;
			}
			
			zwaveDevice.setNuid(nuid);
			zwaveDevice.setApplianceid(Utils.createtoken());
			zwaveDevice.setDevicetype(devicetype.toString());
			
			DeviceCapability capability = new DeviceCapability(zwaveDevice, 3);
			List<DeviceCapability> dcl = new ArrayList<DeviceCapability>();
			dcl.add(capability);
			zwaveDevice.setCapability(dcl);

		}
		zwaveDevice.setName(name);
		nuid = zwaveDevice.getNuid();
		zwaveDeviceService.saveOrUpdate(zwaveDevice);
		zwavedeviceid = zwaveDeviceService.queryDeviceByNuidType(deviceid, nuid,new String[]{String.valueOf(this.innerdevicetype) , String.valueOf(this.outdevicetype)}).get(0).getZwavedeviceid();
	}

	
	
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setOutid(int outid) {
		this.outid = outid;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDevicetype(Integer devicetype) {
		this.devicetype = devicetype;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setZwavedeviceid(Integer zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public String getMessage() {
		return message;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public Integer getZwavedeviceid() {
		return zwavedeviceid;
	}

	public void setCapabilitycode(int capabilitycode)
	{
		this.capabilitycode = capabilitycode;
	}	
	
}
