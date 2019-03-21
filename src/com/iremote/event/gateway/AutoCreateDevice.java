package com.iremote.event.gateway;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteOwnerChangeEvent;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;

public class AutoCreateDevice extends RemoteOwnerChangeEvent implements ITextMessageProcessor
{
//	private static Log log = LogFactory.getLog(AutoCreateDevice.class);
	
	private static Map<String , AutoCreateDeviceMetadata> map = new HashMap<String , AutoCreateDeviceMetadata>();
	private ZWaveDeviceService svr = new ZWaveDeviceService() ;
	private ZWaveDevice zwavedevice ;

	public AutoCreateDevice()
	{
		super();
	}

	public AutoCreateDevice(String deviceid, Date eventtime, int oldownerid, int newownerid, String oldownerphonenumber,
			String newownerphonenumber, long taskid)
	{
		super(deviceid, eventtime, oldownerid, newownerid, oldownerphonenumber, newownerphonenumber, taskid);
	}

	@Override
	public void run()
	{
		if (super.getRemote() == null )
			return ;
		AutoCreateDeviceMetadata md = map.get(String.valueOf(super.getRemote().getRemotetype()));
		if ( md == null )  
			return ;
		   
		zwavedevice = svr.querybydeviceid(super.getDeviceid(),md.getNuid());
		
		if ( zwavedevice == null )
			createDevice(md);
		
		initCapablity(md);
		ensureRequiredCapability(md);
		
		svr.saveOrUpdate(zwavedevice);
	}
	
	private void createDevice(AutoCreateDeviceMetadata md)
	{
		zwavedevice = new ZWaveDevice();
		zwavedevice.setApplianceid(Utils.createtoken());
		zwavedevice.setBattery(100);
		zwavedevice.setDeviceid(super.getDeviceid());
		zwavedevice.setNuid(md.getNuid());
		zwavedevice.setDevicetype(String.valueOf(md.getDevicetype()));
		zwavedevice.setEnablestatus(IRemoteConstantDefine.DEVICE_ENABLE_STATUS_ENABLE);
		
		if ( super.getRemote() != null && StringUtils.isNotBlank(super.getRemote().getName()))
			zwavedevice.setName(super.getRemote().getName());
		else 
			zwavedevice.setName(super.getDeviceid().substring(super.getDeviceid().length() - 6));
		
		zwavedevice.setStatus(md.getDefaultstatus());
		zwavedevice.setStatuses(md.getDefaultstatuses());
		if ( super.getRemote() != null )
			zwavedevice.setVersion1(super.getRemote().getVersion());
	}

	private void initCapablity(AutoCreateDeviceMetadata md)
	{
		if ( zwavedevice.getCapability() == null )
			zwavedevice.setCapability(new ArrayList<DeviceCapability>());
		
		if ( !zwavedevice.getCapability().isEmpty() || md.getInitcapabilitycode() == null || md.getInitcapabilitycode().length == 0 )
			return ;
		
		for ( int c : md.getInitcapabilitycode())
			zwavedevice.getCapability().add(new DeviceCapability(zwavedevice , c) );
	}
	
	private void ensureRequiredCapability(AutoCreateDeviceMetadata md)
	{
		if ( zwavedevice.getCapability() == null )
			zwavedevice.setCapability(new ArrayList<DeviceCapability>());
		
		if ( md.getRequiredcapabilitycode() == null || md.getRequiredcapabilitycode().length == 0 )
			return ;
			
		Set<Integer> set = new HashSet<Integer>();
		for ( DeviceCapability dc : zwavedevice.getCapability())
			set.add(dc.getCapabilitycode());
		
		for ( int c : md.getRequiredcapabilitycode() )
		{
			if ( !set.contains(c))
				zwavedevice.getCapability().add(new DeviceCapability(zwavedevice , c) );
		}
	}
	
	@Override
	public String getTaskKey()
	{
		return super.getDeviceid();
	}
	
	public ZWaveDevice getZwavedevice()
	{
		return zwavedevice;
	}

	static 
	{
		//map.put(IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, 
		//		new AutoCreateDeviceMetadata(IRemoteConstantDefine.DEVICE_NUID_WIFI_LOCK , IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK , 
		//									new int[]{2,3,4,5,6},new int[]{1},255,"[]"));
		map.put("3",   // access control
				new AutoCreateDeviceMetadata(IRemoteConstantDefine.DEVICE_NUID_WIFI_LOCK , IRemoteConstantDefine.DEVICE_TYPE_DOOR_CONTROL , 
											new int[]{2,3,4,5,7},null,255,"[]"));
		map.put(IRemoteConstantDefine.DEVICE_TYPE_AIR_QUALITY , 
				new AutoCreateDeviceMetadata(IRemoteConstantDefine.DEVICE_NUID_WIFI_LOCK , IRemoteConstantDefine.DEVICE_TYPE_AIR_QUALITY , 
						new int[]{3},null,255,"[0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]"));
		map.put(IRemoteConstantDefine.DEVICE_TYPE_FINGERPRING , 
				new AutoCreateDeviceMetadata(IRemoteConstantDefine.DEVICE_NUID_WIFI_LOCK , IRemoteConstantDefine.DEVICE_TYPE_FINGERPRING , 
						new int[]{},new int[]{},0,"[]"));
		
	}

	
}
