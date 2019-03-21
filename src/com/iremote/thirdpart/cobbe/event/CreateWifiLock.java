package com.iremote.thirdpart.cobbe.event;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteOwnerChangeEvent;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;

public class CreateWifiLock extends RemoteOwnerChangeEvent implements ITextMessageProcessor
{

	private ZWaveDeviceService svr = new ZWaveDeviceService() ;
	private ZWaveDevice zwavedevice ;
	
	public CreateWifiLock()
	{
		super();
	}

	public CreateWifiLock(String deviceid, Date eventtime, int oldownerid, int newownerid, String oldownerphonenumber,
			String newownerphonenumber, long taskid)
	{
		super(deviceid, eventtime, oldownerid, newownerid, oldownerphonenumber, newownerphonenumber, taskid);
	}

	@Override
	public void run()
	{
		RemoteService rs = new RemoteService();
		Remote r =rs.getIremotepassword(super.getDeviceid());
		
		if ( r != null 
				&& r.getRemotetype() != IRemoteConstantDefine.IREMOTE_TYPE_NORMAL    //for those WIFI locks that not report remote type attribute.
				&& r.getRemotetype() != IRemoteConstantDefine.GATEWAY_TYPE_COBBE_LOCK 
				&& r.getRemotetype() != IRemoteConstantDefine.GATEWAY_TYPE_TONGXIN_LOCK)
			return ;
		
		if ( !GatewayUtils.isLockGateway(r))
			return ;
		
		zwavedevice = svr.querybydeviceid(super.getDeviceid(), IRemoteConstantDefine.DEVICE_NUID_WIFI_LOCK);

		if (zwavedevice == null) {
			createWifiLock();
			createCapability();
		}

//		createLockPassword();
		
		svr.saveOrUpdate(zwavedevice);
		
		DoorLockPasswordHelper.BroadcaseSendPassowordEvent(super.getDeviceid());
	}
	
	private void createWifiLock()
	{
		zwavedevice = new ZWaveDevice();
		zwavedevice.setApplianceid(Utils.createtoken());
		zwavedevice.setBattery(100);
		zwavedevice.setDeviceid(super.getDeviceid());
		zwavedevice.setNuid(IRemoteConstantDefine.DEVICE_NUID_WIFI_LOCK);
		zwavedevice.setDevicetype(IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK);
		zwavedevice.setEnablestatus(IRemoteConstantDefine.DEVICE_ENABLE_STATUS_ENABLE);
		
		if ( super.getRemote() != null && StringUtils.isNotBlank(super.getRemote().getName()))
			zwavedevice.setName(super.getRemote().getName());
		else 
			zwavedevice.setName(super.getDeviceid().substring(super.getDeviceid().length() - 6));
		zwavedevice.setStatus(Utils.getDeviceDefaultStatus(zwavedevice.getDevicetype()));
	}
	
	private void createCapability()
	{
		if ( zwavedevice.getCapability() == null )
			zwavedevice.setCapability(new ArrayList<DeviceCapability>());
		
		Set<Integer> set = new HashSet<Integer>();
		
		for ( DeviceCapability dc : zwavedevice.getCapability())
			set.add(dc.getCapabilitycode());
		
		if ( zwavedevice.getCapability().isEmpty() )
			zwavedevice.getCapability().add(new DeviceCapability(zwavedevice , 1) );
		
		for ( int i = 2 ; i <= 3 ; i ++ )
		{
			if ( !set.contains(i))
				zwavedevice.getCapability().add(new DeviceCapability(zwavedevice , i) );
		}

		if (zwavedevice.getFunctionversion() == null) {
			zwavedevice.getCapability().removeIf(c -> c.getCapabilitycode() >= 4 && c.getCapabilitycode() <= 6);
			zwavedevice.setFunctionversion("FFFF");
		}
	}

//	private void createLockPassword()
//	{
//		DoorlockPasswordService dpsvr = new DoorlockPasswordService();
//		Random r = new Random(System.currentTimeMillis());
//		for ( int i = 0 ; i < 10 ; i ++ )
//		{
//			DoorlockPassword doorlockpassword = DoorLockPasswordHelper.createLockTempPassword(zd.getZwavedeviceid(), 0xf3 + i, String.format("%06d", r.nextInt(1000000)));
//
//			dpsvr.save(doorlockpassword);
//		}
//	}
	
	public ZWaveDevice getZwavedevice()
	{
		return zwavedevice;
	}

	@Override
	public String getTaskKey()
	{
		return super.getDeviceid();
	}


}
