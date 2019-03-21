package com.iremote.action.device;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.iremote.action.device.passthrough.SetBaudRateAction;
import com.iremote.action.helper.PassThroughDeviceCmdHelper;
import com.iremote.action.helper.PhoneUserBlueToothHelper;
import org.apache.commons.lang3.StringUtils;

import com.iremote.action.helper.DoorlockHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.helper.TimeZoneHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.DeviceExtendInfo;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.RoomAppliance;
import com.iremote.domain.Timezone;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.DeviceCapabilityService;
import com.iremote.service.DeviceExtendInfoService;
import com.iremote.service.RemoteService;
import com.iremote.service.RoomApplianceService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class SetDeiviceNameAction {
	private int zwavedeviceid ;
	private ZWaveDevice zwaveDevice ;
	private String name;
	private String password;
	private List<DeviceCapability> capabilitys;
	private  boolean backlock = false;
	private  boolean fahrenheit = false;
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String switchsNames;
	private int channelnumber;
	private List<ZWaveSubDevice> zWaveSubDevices;
	protected DeviceCapabilityService dc = new DeviceCapabilityService();
	private String timezoneid;
	private PhoneUser phoneuser ;
	private int delay;
	private int inhomenotalarm;
	private Integer baudrate;
	private String areaid;

	
	public String execute(){
		save();
		return Action.SUCCESS;
	}

	public void save(){
		ZWaveDeviceService zds = new ZWaveDeviceService();
		zwaveDevice = zds.query(zwavedeviceid);
		if(zwaveDevice == null){
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return;
		}
		if(zwaveDevice.getDevicetype().equals(IRemoteConstantDefine.DEVICE_TYPE_DSC)){
			DeviceCapability dca = dc.query(zwaveDevice,1);
			if(dca!=null){
				dca.setCapabilityvalue(channelnumber+"");
				dc.update(dca);	
			}else{
				DeviceCapability dec = new DeviceCapability();
				ZWaveDevice z = new ZWaveDevice();
				z.setZwavedeviceid(zwavedeviceid);
				dec.setZwavedevice(z);
				dec.setCapabilitycode(1);
				dec.setCapabilityvalue(channelnumber+"");
				dc.save(dec);		
			}
		}		
		zwaveDevice.setName(name);
		RoomApplianceService roomApplianceService = new RoomApplianceService();
		List<RoomAppliance> roomAppliances = roomApplianceService.query(zwaveDevice.getDeviceid(), zwaveDevice.getApplianceid());
		if(roomAppliances != null && roomAppliances.size() > 0){
			RoomAppliance roomAppliance = roomAppliances.get(0);
			roomAppliance.setName(name);
			roomApplianceService.save(roomAppliance);
		}
		capabilitys = zwaveDevice.getCapability();
		zWaveSubDevices = zwaveDevice.getzWaveSubDevices();
		backlock();
		fahrenheit();
		savePassword();
		if (!changePassThroughDeviceBaudRate()) {
			return;
		}

		if(switchsNames != null && switchsNames.length() > 0)
		{
			String[] switchsName = switchsNames.split(",");
			int length = channelnumber>0?channelnumber:switchsName.length;
			for(int i = 0; i < length; i++)
			{
				ZWaveSubDevice zms = null;
				try {
					zms = zWaveSubDevices.get(i);
					zms.setName(switchsName[i].trim());
					
					RoomAppliance querybyZwavedeviceidAndChannelid = roomApplianceService.querybyZwavedeviceidAndChannelid(zwaveDevice.getZwavedeviceid(), (i+1));
					if(querybyZwavedeviceidAndChannelid!=null){
						querybyZwavedeviceidAndChannelid.setName(switchsName[i].trim());
					}
				}catch (Exception e){
					zms = new ZWaveSubDevice(i+1, switchsName[i].trim() , zwaveDevice);
					zWaveSubDevices.add(zms);
				}
			}
		}
		if(checkissensor(zwavedeviceid)){
			DeviceCapabilityService dcs = new DeviceCapabilityService();
			DeviceCapability query = dcs.query(zwaveDevice, IRemoteConstantDefine.DEVICE_CAPABILITY_DELAY_TIME_CAPABILITCODE);
			DeviceCapability query2 = dcs.query(zwaveDevice, IRemoteConstantDefine.DEVICE_CAPABILITY_ALARM_WHEN_HOME_ARMED);
			if(query!=null){
				query.setCapabilityvalue(String.valueOf(delay));
			}else{
				DeviceCapability deviceCapability = new DeviceCapability(zwaveDevice,IRemoteConstantDefine.DEVICE_CAPABILITY_DELAY_TIME_CAPABILITCODE);
				deviceCapability.setCapabilityvalue(String.valueOf(delay));
				dcs.saveOrUpdate(deviceCapability);
			}

			if(inhomenotalarm==1&&query2==null){
				DeviceCapability deviceCapability = new DeviceCapability(zwaveDevice,IRemoteConstantDefine.DEVICE_CAPABILITY_ALARM_WHEN_HOME_ARMED);
				dcs.save(deviceCapability);
			}else if(inhomenotalarm==0&&query2!=null){
				zwaveDevice.getCapability().remove(query2);
				//dcs.delete(query2);
			}
		}
		if (IRemoteConstantDefine.DEVICE_TYPE_DRESS_HELPER.equals(zwaveDevice.getDevicetype())) {
			processorDeviceCapability();
		}
		pushMessage();
	}

	private boolean changePassThroughDeviceBaudRate() {
		if (baudrate != null
				&& PassThroughDeviceCmdHelper.isPassThroughDevice(zwaveDevice.getDevicetype())) {
			SetBaudRateAction action = new SetBaudRateAction();
			action.setZwavedeviceid(zwavedeviceid);
			action.setBaudrate(baudrate);
			action.execute();
			resultCode = action.getResultCode();
			if (resultCode != ErrorCodeDefine.SUCCESS) {
				pushMessage();
				return false;
			}
		}
		return true;
	}

	private void pushMessage() {
		phoneuser.setLastupdatetime(new Date());
		PhoneUserHelper.sendInfoChangeMessage(phoneuser);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, new InfoChange(zwaveDevice.getDeviceid() , new Date() , 0) );
	}

	private void processorDeviceCapability() {
		DeviceCapability capability1 = PhoneUserBlueToothHelper.getCapability(zwaveDevice, IRemoteConstantDefine.DEVICE_CAPABILITY_DRESS_HELPER_AREA_ID);
		if (capability1 == null) {
			DeviceCapability capability = new DeviceCapability(zwaveDevice, IRemoteConstantDefine.DEVICE_CAPABILITY_DRESS_HELPER_AREA_ID, areaid);
			new DeviceCapabilityService().save(capability);
			return;
		}
		if (StringUtils.isNotBlank(areaid) && !capability1.getCapabilityvalue().equals(areaid)) {
			capability1.setCapabilityvalue(areaid);
		}
	}

	private boolean checkissensor(int zwavedeviceid){
    	List<String> sensortypelist = new ArrayList<String>();
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_SMOKE);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_DOOR_SENSOR);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_WATER);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_MOVE);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_GAS);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_SOS_ALARM);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK);
    	sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_WALLREADER);
		sensortypelist.add(IRemoteConstantDefine.DEVICE_TYPE_GARAGEDOOR);
		ZWaveDeviceService zwds = new ZWaveDeviceService();
    	ZWaveDevice query2 = zwds.query(zwavedeviceid);
    	if(query2!=null && sensortypelist.contains(query2.getDevicetype())){
    		return true;
    	}
    	return false;
    }
	public void backlock(){
		if(IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zwaveDevice.getDevicetype())){
			DeviceCapability item = null;
			for(DeviceCapability  capability : capabilitys){
				if(capability.getCapabilitycode() == 1 || capability.getCapabilitycode() == 0){
					item = capability;
					break;
				}
			}
			if(item == null){
				item = new DeviceCapability(zwaveDevice, 1);
				capabilitys.add(item);
			}
			if(backlock == true){
				item.setCapabilitycode(1);
			}else{
				item.setCapabilitycode(0);
			}
			if ( StringUtils.isNotBlank(timezoneid))
			{
				TimeZone tz = TimeZone.getTimeZone(timezoneid);
				if ( tz != null )
				{
					RemoteService rs = new RemoteService();
					Remote r = rs.getIremotepassword(zwaveDevice.getDeviceid());
					if ( r != null )
					{
						if ( r.getTimezone() == null )
							r.setTimezone(new Timezone());
						r.getTimezone().setId(tz.getID());
						r.getTimezone().setZonetext(TimeZoneHelper.createTimezoneDisplayname(tz , phoneuser));
						r.getTimezone().setZoneid(TimeZoneHelper.createTimezoneOffsetHour(tz));
						
						Date d = TimeZoneHelper.timezoneTranslate(new Date(), TimeZone.getDefault(), tz);
						DoorlockHelper.sendCurrentTime(zwaveDevice.getDeviceid(),zwaveDevice.getNuid()  , d);
					}
				}
			}
		}
	}
	
	public void fahrenheit(){
		if(zwaveDevice.getDevicetype().equals(IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER)){
			DeviceCapability item = null;
			for(DeviceCapability  capability : capabilitys){
				if(capability.getCapabilitycode() == 1){
					item = capability;
				}
			}
			if(fahrenheit == true){
				if(item == null){
					item = new DeviceCapability(zwaveDevice, 1);
					capabilitys.add(item);
				}
			}else{
				if(item != null){
					capabilitys.remove(item);
				}
			}
		}
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
	
	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCapabilitys(List<DeviceCapability> capabilitys) {
		this.capabilitys = capabilitys;
	}

	public void setBacklock(boolean backlock) {
		this.backlock = backlock;
	}

	public void setFahrenheit(boolean fahrenheit) {
		this.fahrenheit = fahrenheit;
	}

	public void setSwitchsNames(String switchsNames) {
		this.switchsNames = switchsNames;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	public void setChannelnumber(int channelnumber) {
		this.channelnumber = channelnumber;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void setInhomenotalarm(int inhomenotalarm) {
		this.inhomenotalarm = inhomenotalarm;
	}
	public void setTimezoneid(String timezoneid) {
		this.timezoneid = timezoneid;
	}

	public void setBaudrate(Integer baudrate) {
		this.baudrate = baudrate;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
}
