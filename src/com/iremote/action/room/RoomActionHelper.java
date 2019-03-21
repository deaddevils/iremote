package com.iremote.action.room;

import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.*;
import com.iremote.service.ZWaveSubDeviceService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.service.CameraService;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.ZWaveDeviceService;

public class RoomActionHelper
{
	private static Log log = LogFactory.getLog(RoomActionHelper.class);
	
	public static void createRoomZwaveAppliance(Room room , List<Integer> zwavedeviceids)
	{
		if ( zwavedeviceids == null || zwavedeviceids.size() == 0 )
			return ;
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> zls = zds.query( zwavedeviceids);
		
		if ( zls == null || zls.size() == 0 )
			return ;

		for ( ZWaveDevice zd : zls )
		{
			RoomAppliance ra = createRoomAppliance(room , zd);
			room.getAppliancelist().add(ra);
		}
	}
	
	public static void createRoomInfraredAppliance(Room room , List<Integer> infrareddeviceids)
	{
		if ( infrareddeviceids == null || infrareddeviceids.size() == 0 )
			return ;
		
		InfraredDeviceService ids = new InfraredDeviceService();
		List<InfraredDevice> lst = ids.query(infrareddeviceids);
		
		if ( lst == null )
			return ;
		
		for ( InfraredDevice id : lst )
		{
			RoomAppliance ra = createRoomAppliance(room , id);
			room.getAppliancelist().add(ra);
		}
	}
	
	public static void createRoomCameraAppliance(Room room , List<Integer> cameraids)
	{
		if ( cameraids == null || cameraids.size() == 0 )
			return ;
		
		CameraService cs = new CameraService();
		List<Camera> lst = cs.query(cameraids);
		
		if ( lst == null )
			return ;
		
		for ( Camera c : lst )
		{
			RoomAppliance ra = createRoomAppliance(room , c);
			room.getAppliancelist().add(ra);
		}
	}

	public static void createRoomSubDeviceAppliance(Room room , List<Integer> subdeviceids)
	{
		if ( subdeviceids == null || subdeviceids.size() == 0 )
			return ;

		ZWaveSubDeviceService zss = new ZWaveSubDeviceService();
		List<ZWaveSubDevice> lst = zss.query(subdeviceids);

		if ( lst == null )
			return ;

		for ( ZWaveSubDevice z : lst )
		{
			RoomAppliance ra = createRoomAppliance(room , z);
			ra.setDeviceid(z.getZwavedevice().getDeviceid());
			ra.setZwavedeviceid(z.getZwavedevice().getZwavedeviceid());
			ra.setSubdeviceid(z.getZwavesubdeviceid());
			ra.setChannelid(z.getChannelid());
			ra.setMajortype(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE);
			ra.setDevicetype(z.getZwavedevice().getDevicetype());
			room.getAppliancelist().add(ra);
		}
	}
	
	public static RoomAppliance createRoomAppliance(Room room ,Object obj)
	{
		RoomAppliance ra = new RoomAppliance();
		try
		{
			PropertyUtils.copyProperties(ra, obj);
		} 
		catch (Throwable t)
		{
			log.error(t.getMessage() , t);
			return null; 
		} 
		ra.setRoom(room);
		return ra ;
	}
}
