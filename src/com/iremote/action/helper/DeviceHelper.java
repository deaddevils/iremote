package com.iremote.action.helper;

import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.iremote.common.JWStringUtils;
import com.iremote.common.Utils;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.*;
import org.apache.commons.lang3.StringUtils;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.device.ClearCamera;
import com.iremote.device.ClearInfraredDevice;
import com.iremote.device.ClearZwaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.DeviceFunctionVersionCapabilityService;
import com.iremote.service.DeviceVersionCapablityService;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.task.devicecommand.ExecuteDeviceCommand;

public class DeviceHelper 
{
	public static ZWaveDevice createSelfdefineDevice(String deviceid , int nuid)
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		ZWaveDevice device = new ZWaveDevice();
		device.setDeviceid(deviceid);
		device.setDevicetype(getDeviceType(nuid));
		if ( device.getDevicetype() == null )
			return null ;
		device.setEnablestatus(IRemoteConstantDefine.DEVICE_ENABLE_STATUS_ENABLE);
		device.setNuid(nuid);
		device.setApplianceid(UUID.randomUUID().toString().replaceAll("-", ""));
		device.setStatus(0);
		device.setName(String.format("%s_%d" ,deviceid.substring(deviceid.length() - 6 ) , nuid));
		zds.saveOrUpdate(device);

		return device;   
	}

	public static void createSubDevice(ZWaveDevice zwaveDevice , int platform , String language)
	{
		int channelcount = Utils.getDeviceChannelCount(zwaveDevice.getDevicetype());;
		if ( channelcount == 0 )
			return ;

		if(zwaveDevice.getzWaveSubDevices() == null)
			zwaveDevice.setzWaveSubDevices(new ArrayList<ZWaveSubDevice>());

		String ss = Utils.getDeviceDefaultStatuses(zwaveDevice.getDevicetype());
		for ( int i = 0 ; i < channelcount ; i ++ )
		{
			ZWaveSubDevice zsd = findZWaveSubDevice(zwaveDevice.getzWaveSubDevices() , i+1);
			if ( zsd == null )
			{
				String name = MessageManager.getmessage(IRemoteConstantDefine.MESSGAE_TYPE_CHANNEL ,platform,language);
				ZWaveSubDevice zms = new ZWaveSubDevice(i + 1, String.format("%s%d", name , (i + 1)), zwaveDevice);
				zms.setStatus(Utils.getJsonArrayValue(ss, i));
				zms.setStatuses(Utils.getDeviceChannelDefaultStatuses(zwaveDevice.getDevicetype()));
				zwaveDevice.getzWaveSubDevices().add(zms);
			}
			else if ( StringUtils.isBlank(zsd.getName()))
				zsd.setName(MessageManager.getmessage(IRemoteConstantDefine.MESSGAE_TYPE_CHANNEL ,platform,language) + (i+1));
		}
	}

	public static ZWaveSubDevice findZWaveSubDevice(Collection<ZWaveSubDevice> zwavesubdevice , int channelid)
	{
		if ( zwavesubdevice == null || zwavesubdevice.size() == 0 )
			return null ;
		for ( ZWaveSubDevice zsd : zwavesubdevice)
			if ( zsd.getChannelid() == channelid)
				return zsd ;
		return null ;
	}

	public static String getDeviceType(int nuid)
	{
		if ( nuid >= 10001 && nuid <= 10200 )
			return IRemoteConstantDefine.DEVICE_TYPE_TUJIA_ELECTRIC_METER;
		if ( nuid >= 11001 && nuid <= 11200 )
			return IRemoteConstantDefine.DEVICE_TYPE_WATER_METER;
//		if ( nuid == 11201 )
//			return IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK;
		return null ;
	}
	
	public static Set<Integer> initDeviceCapability(ZWaveDevice zd)
	{
		Set<Integer> sc = new HashSet<Integer>();
	
		if ( zd == null )
			return sc ;
		
		if ( zd.getCapability() != null )
		{
			for(DeviceCapability item : zd.getCapability())
				sc.add(item.getCapabilitycode());
		}
		
		DeviceVersionCapablityService svr = new DeviceVersionCapablityService();
		List<DeviceVersionCapablity>  lst = svr.queryByVersion(zd.getProductor(), zd.getModel(), zd.getVersion2());
		if ( lst != null )
		{		
			for ( DeviceVersionCapablity dvc : lst)
				sc.add(dvc.getCapabilitycode());
		}
		
		DeviceFunctionVersionCapabilityService dfvs = new DeviceFunctionVersionCapabilityService();
		List<DeviceFunctionVersionCapability> dfl = dfvs.queryByVersion(zd.getDevicetype(), zd.getFunctionversion());
		
		if ( dfl != null )
		{
			for ( DeviceFunctionVersionCapability dfvc : dfl )
				sc.add(dfvc.getCapabilitycode());
		}
		return sc;
	}
	
	public static void clearZwaveDevice(ZWaveDevice zd)
	{
		ClearZwaveDevice czd = new ClearZwaveDevice(zd);
		czd.clear();
	}
	
	public static void clearZwaveDeviceServiceData(ZWaveDevice zd)
	{
		ClearZwaveDevice czd = new ClearZwaveDevice(zd);
		czd.clearServerData();
	}
	
	public static void clearCamera(Camera c)
	{
		ClearCamera cc = new ClearCamera(c);
		cc.clear();
	}
	
	public static void clearInfraredDevice(InfraredDevice id)
	{
		ClearInfraredDevice cid = new ClearInfraredDevice(id);
		cid.clear();
	}
	
	public static void readDeviceProductor(ZWaveDevice zd )
	{
		if ( zd == null )
			return ;
		if ( !IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zd.getDevicetype()))
			return ;

		if (  isZwavedevice(zd) && StringUtils.isBlank(zd.getProductor()))
		{
			ScheduleManager.excutein(5, new ExecuteDeviceCommand(zd.getDeviceid() ,CommandUtil.createZWaveManufactureCommand(zd.getNuid()) , 1));
			return ;
		}
		
		if ( isZwavedevice(zd) 
				&& !StringUtils.isBlank(zd.getProductor()) 
				&& zd.getProductor().toLowerCase().startsWith(IRemoteConstantDefine.JWZH_CHUANGJIA_PRODUCTOR_PREFIX.toLowerCase())
				&& !zd.getProductor().startsWith(IRemoteConstantDefine.JWZH_CHUANGJIA_PRODUCTOR_PREFIX_2))
			return ;
		
		if ( !isZwavedevice(zd))
		{
			if( StringUtils.isBlank(zd.getFunctionversion()) || "FFFF".equals(zd.getFunctionversion()))
			{
				ScheduleManager.excutein(5, new ExecuteDeviceCommand(zd.getDeviceid() ,CommandUtil.createDoorlockGetVersionComand(zd.getNuid()) , 1));
				return ;
			}
		}
		
		if ( zd.getProductor() != null 
				//&& zd.getProductor().startsWith(IRemoteConstantDefine.JWZH_ZWAVE_PRODUCTOR)
				)
		{
			if (StringUtils.isBlank(zd.getFunctionversion()) ||  "FFFF".equals(zd.getFunctionversion()))
			{
				ScheduleManager.excutein(5, new ExecuteDeviceCommand(zd.getDeviceid() ,CommandUtil.createDoorlockGetVersionComand(zd.getNuid()) , 1));
				return ;
			}
		}
		
	}
	
	public static boolean isZwavedevice(int nuid)
	{
		return (nuid <= 232 );
	}
	
	public static boolean isZwavedevice(ZWaveDevice zd)
	{
		if ( zd == null )
			return false ;
		return isZwavedevice(zd.getNuid());
	}
	
	public static void unalarmAlarmDevice(PhoneUser phoneuser)
	{
		List<Integer> pidl;
		if ( phoneuser.getFamilyid() != null && phoneuser.getFamilyid() != 0 )
			pidl = PhoneUserHelper.queryPhoneuseridbyfamilyid(phoneuser.getFamilyid());
		else 
		{
			pidl = new ArrayList<Integer>(1);
			pidl.add(phoneuser.getPhoneuserid());
		}

		IremotepasswordService rs = new IremotepasswordService();
		List<String> didl = rs.queryDeviceidbyPhoneUserid(pidl);
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> lst = zds.querybydeviceid(didl);

		unalarmAlarmDevice(lst);
	}
	
	public static boolean isDeviceArm(ZWaveDevice zd){
		return true;
	}
	
	private static void unalarmAlarmDevice(List<ZWaveDevice> lst)
	{
		for ( ZWaveDevice d : lst )
		{
			if ( !IRemoteConstantDefine.DEVICE_TYPE_ALARM.equals(d.getDevicetype()))
				continue;
			if ( !ConnectionManager.contants(d.getDeviceid()))
				continue;
			CommandTlv ct = CommandUtil.createUnalarmCommand(d.getNuid());
			SynchronizeRequestHelper.asynchronizeRequest(d.getDeviceid(), ct , 0);
		}
	}

	public static JSONArray getZWaveDeviceStatusesJSONArray(String statuses){
		return JSONArray.parseArray(StringUtils.isBlank(statuses) ? "[]" :statuses);
	}

	public static boolean isUsedSubDeviceStatus(String deviceType) {
		return IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE.equals(deviceType)
				|| IRemoteConstantDefine.DEVICE_TYPE_4_CHANNEL_BINARY_SWITCH.equals(deviceType);
	}

	public static ZWaveDevice getUniqueZWaveDevice(String deviceId, ZWaveDeviceService service){
		if (service == null) {
			service = new ZWaveDeviceService();
		}
		List<ZWaveDevice> zWaveDevices = service.querybydeviceid(deviceId);
		if (JWStringUtils.isEmpty(zWaveDevices)) {
			return null;
		}
		return zWaveDevices.get(0);
	}

	public static Integer getUniqueZWaveDeviceId(String deviceId, ZWaveDeviceService service) {
		ZWaveDevice zWaveDevice = getUniqueZWaveDevice(deviceId, service);
		return zWaveDevice == null ? null : zWaveDevice.getZwavedeviceid();
	}

	public static Integer getUniqueZWaveDeviceId(String deviceId) {
		return getUniqueZWaveDeviceId(deviceId, null);
	}
}
