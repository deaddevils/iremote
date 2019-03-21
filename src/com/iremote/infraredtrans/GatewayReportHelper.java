package com.iremote.infraredtrans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.InfraredDeviceEvent;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.GatewayCapability;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.CameraService;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.ZWaveDeviceService;

public class GatewayReportHelper
{
	public static boolean checkhomeid(byte[] b , Remote remote)
	{
		boolean changed = false ;
		Integer homeid = TlvWrap.readInteter(b, 73, 4);

		if ( homeid != null && remote.getHomeid() != null && remote.getHomeid() != 0 && !remote.getHomeid().equals(homeid) )
			changed = true;
		
		if ( homeid != null )
			remote.setHomeid(homeid);
		
		return changed ;
	}
	
	public static void clearRemote(Remote remote)
	{
		clearRemoteInfo(remote);
		clearZWaveDevice(remote.getDeviceid());
		clearInfraredDevice(remote.getDeviceid());
		clearCamera(remote.getDeviceid());
	}
	
	public static Remote createRemote(String deviceid)
	{
		Remote remote = new Remote();
		remote.setDeviceid(deviceid);
		remote.setCreatetime(new Date());
		remote.setLastupdatetime(new Date());
		remote.setPlatform(Utils.getRemotePlatform(deviceid));
		remote.setVersion("1.0.9");
		remote.setIversion(0x0009);
		remote.setNetworkintensity(100);
		
		return remote;
	}
	
	public static boolean setRemote(byte[] request , Remote r)
	{
		boolean changed = false ;
		String ssid = TlvWrap.readString(request, 13, 4);
		Integer longitude = TlvWrap.readInteter(request, 14, 4);
		Integer latitude = TlvWrap.readInteter(request, 15, 4);
		String ip = TlvWrap.readString(request, 17, 4);
		String mac = TlvWrap.readString(request, 18, 4);
		
		if ( StringUtils.isNotBlank(ssid) && !ssid.equals(r.getSsid()) )
		{
			r.setSsid(ssid);
			changed = true;
		}
		if ( latitude != null )
			r.setLatitude(latitude);
		if (longitude != null  )
			r.setLongitude(longitude);
		if ( StringUtils.isNotBlank(ip) && !ip.equals(r.getIp()) )
		{
			r.setIp(ip);
			changed = true;
		}
		if ( StringUtils.isNotBlank(mac))
			r.setMac(mac);
		r.setStatus(IRemoteConstantDefine.REMOTE_STATUS_ONLINE);
	
		byte[] sk = TlvWrap.readTag(request, 76, 4) ;
		if ( sk != null )
			r.setZwavescuritykey(sk);
		String version =  TlvWrap.readString(request, 4, 4);
		if ( version != null && version.length() > 0 )
			r.setVersion(version);
			
		int iv = TlvWrap.readInt(request, 3, 4);
		if ( iv != Integer.MIN_VALUE )
			r.setIversion(iv);
		
		Integer ntw = TlvWrap.readInteter(request, 108, 4 );
		if ( ntw != null )
			r.setNetwork(ntw);
		Integer remotetype = TlvWrap.readInteter(request, TagDefine.TAG_REMOTE_TYPE, 4);
		if ( remotetype != null )
		{
			r.setRemotetype(remotetype);
			ensureGatewayCapability(r);
		}
		Integer ntwi = TlvWrap.readInteter(request, 109, 4 );
		if ( ntwi != null && ntw != null && ntw != IRemoteConstantDefine.NETWORK_WIRED )
			r.setNetworkintensity(ntwi);
		
		return changed;
	}
	
	private static Map<Integer , Integer[]> GATEWAY_TYPE_CAPABILITY_MAP = new HashMap<Integer , Integer[]>();
	static
	{
		GATEWAY_TYPE_CAPABILITY_MAP.put(1, new Integer[]{1,2,10001});
		GATEWAY_TYPE_CAPABILITY_MAP.put(2, new Integer[]{1,2,10002});
		GATEWAY_TYPE_CAPABILITY_MAP.put(15, new Integer[]{1,2});
		GATEWAY_TYPE_CAPABILITY_MAP.put(17, new Integer[]{1,2});
		GATEWAY_TYPE_CAPABILITY_MAP.put(23, new Integer[]{1,2});
		GATEWAY_TYPE_CAPABILITY_MAP.put(28, new Integer[]{1,2,10028});
		GATEWAY_TYPE_CAPABILITY_MAP.put(29, new Integer[]{1,2,10038});  //deprecated
		GATEWAY_TYPE_CAPABILITY_MAP.put(36, new Integer[]{1,2,10036});
		GATEWAY_TYPE_CAPABILITY_MAP.put(38, new Integer[]{1,2,10038});
		GATEWAY_TYPE_CAPABILITY_MAP.put(39, new Integer[]{1,2,10039});
	}
	
	public static void ensureGatewayCapability(Remote r)
	{
		if ( r == null )
			return ;
		
		Integer[] c = GATEWAY_TYPE_CAPABILITY_MAP.get(r.getRemotetype());
		if ( c == null )
			return ;
		
		ensureGatewayCapability(r , c);
	}
	
	public static void ensureGatewayCapability(Remote r , Integer[] capability)
	{
		if ( r == null || capability == null || capability.length == 0 )
			return ;
		
		for ( Integer i : capability )
			ensureGatewayCapability(r, i);
	}
	
	public static void ensureGatewayCapability(Remote r , int capability)
	{
		if ( r == null )
			return ;
		
		if ( r.getCapability() == null )
			r.setCapability(new ArrayList<GatewayCapability>());
		
		if ( hasGatewayCapability(r , capability) )
			return ;
		
		r.getCapability().add(new GatewayCapability(r , capability));
	}
	
	public static boolean hasGatewayCapability(Remote r , int capability)
	{
		if ( r == null )
			return false;
		
		if ( r.getCapability() == null )
			return false;
		
		for ( GatewayCapability gc : r.getCapability() )
			if ( gc.getCapabilitycode() == capability)
				return true;
		return false ;
	}
	
	private static void clearRemoteInfo( Remote r)
	{
		r.setName(Utils.getGatewayDefaultName(r.getDeviceid()));
		r.setPassword("");
	}
	
	private static void clearZWaveDevice(String deviceid)
	{
		ZWaveDeviceService svr = new ZWaveDeviceService();
		List<ZWaveDevice> lst = svr.querybydeviceid(deviceid);

		
		if ( lst != null )
		{
			for ( ZWaveDevice zd : lst )
			{
				DeviceHelper.clearZwaveDevice(zd);
				svr.delete(zd);
				
				ZWaveDeviceEvent zde = new ZWaveDeviceEvent(zd.getZwavedeviceid() , zd.getDeviceid() , zd.getNuid() ,IRemoteConstantDefine.EVENT_DELETE_ZWAVE_DEVICE, new Date() , 0);
				zde.setWarningstatuses(zd.getWarningstatuses());
				zde.setDevicetype(zd.getDevicetype());
				zde.setApplianceid(zd.getApplianceid());
				JMSUtil.sendmessage(IRemoteConstantDefine.EVENT_DELETE_ZWAVE_DEVICE, zde);
			}
		}
	}
	
	private static void clearInfraredDevice(String deviceid)
	{
		InfraredDeviceService svr = new InfraredDeviceService();
		List<InfraredDevice> lst = svr.querybydeviceid(deviceid);
		
		if ( lst != null )
		{
			for ( InfraredDevice id : lst )
			{
				DeviceHelper.clearInfraredDevice(id);
				svr.delete(id);
				JMSUtil.sendmessage(IRemoteConstantDefine.EVENT_DELETE_INFRARED_DEVICE, new InfraredDeviceEvent(id.getInfrareddeviceid() , id.getDeviceid() , id.getApplianceid()));
			}
		}
	}
	
	private static void clearCamera(String deviceid)
	{
		CameraService cs = new CameraService();
		cs.delete(deviceid);
	}
	
}
