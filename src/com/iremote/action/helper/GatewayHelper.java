package com.iremote.action.helper;

import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.device.ClearInfraredDevice;
import com.iremote.device.ClearZwaveDevice;
import com.iremote.domain.*;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.vo.Appliance;
import com.iremote.vo.RemoteData;
import com.iremote.vo.SubDevice;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GatewayHelper
{
	private static Log log = LogFactory.getLog(GatewayHelper.class);
	
	public static boolean isOnline(String deviceid)
	{
		return ConnectionManager.contants(deviceid);
	}
	
	public static void clearDeviceShare(String deviceid)
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> lst = zds.querybydeviceid(deviceid);
		clearZWaveDeviceShares(lst);
		
		InfraredDeviceService ids = new InfraredDeviceService();
		List<InfraredDevice> idl = ids.querybydeviceid(deviceid);
		
		clearInfraredDeviceShares(idl);
	}
	
	public static void clearZWaveDeviceShares(List<ZWaveDevice> lst)
	{
		if ( lst == null || lst.size() == 0 )
			return ;
		
		for ( ZWaveDevice z : lst )
		{
			ClearZwaveDevice c = new ClearZwaveDevice(z);
			c.clearUserShare();
			c.clearDeviceShare();
		}
	}
	
	public static void clearInfraredDeviceShares(List<InfraredDevice> lst)
	{
		if ( lst == null || lst.size() == 0 )
			return ;

		for ( InfraredDevice id : lst )
		{
			ClearInfraredDevice c = new ClearInfraredDevice(id);
			c.clearUserShare();
			c.clearDeviceShare();
		}
	}
	
	public static RemoteData createRemoteData(Remote r)
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		InfraredDeviceService ids = new InfraredDeviceService();
		
		RemoteData d = new RemoteData() ; //JSON.parseObject(r.getData(), RemoteData.class);
		d.setPhonenumber(r.getPhonenumber());
		d.setDeviceid(r.getDeviceid());
		d.setName(r.getName());
		d.setTimezone(r.getTimezone());
		d.setType(getRemoteType(r));
		d.setCapability(r.getCapability());
		d.setSsid(r.getSsid());
		d.setIp(r.getIp());
		d.setStatus(r.getStatus());
		d.setNetwork(r.getNetwork());
		d.setNetworkintensity(r.getNetworkintensity());
		d.setBattery(r.getBattery());
		d.setVersion(r.getVersion());
		d.setIversion(r.getIversion());
		d.setPowertype(r.getPowertype());
		d.setRemotetype(r.getRemotetype());
		
		List<Appliance> appliancelist = new ArrayList<Appliance>();
		
		List<ZWaveDevice> zdl = zds.querybydeviceid(r.getDeviceid());
							
		for ( ZWaveDevice zd : zdl )
		{
			Appliance a = createAppliance(zd);
			if ( a == null )
				continue;

			filterCapability(a);

			if (StringUtils.isBlank(a.getStatuses()))
				a.setStatuses("[]");
			if ( a.getSubdevice() == null )
				a.setSubdevice(new ArrayList<SubDevice>());
			if ( GatewayUtils.isCobbeLock(r))
				a.setWakeuptype(2);
			a.setCodelibery(new int[0]);
			appliancelist.add(a);
		}

		List<InfraredDevice> idl = ids.querybydeviceid(r.getDeviceid());
		for ( InfraredDevice id : idl )
		{
			Appliance a = createAppliance(id);
			if ( a == null )
				continue;

			if (StringUtils.isBlank(a.getStatuses()))
				a.setStatuses("[]");
			if ( a.getSubdevice() == null )
				a.setSubdevice(new ArrayList<SubDevice>());
			appliancelist.add(a);
		}
		
		d.setAppliancelist(appliancelist);
		
		return  d;
	}

	public static void filterCapability(Appliance appliance){
		if (appliance == null || appliance.getCapability() == null
				|| appliance.getCapability().size() == 0) {
			return;
		}
		ArrayList<DeviceCapability> capabilities = new ArrayList<>();
		for (DeviceCapability deviceCapability : appliance.getCapability()) {
			if (!IRemoteConstantDefine.DEVICE_CAPABILITY_DIA_ABLE_SHOW_FOR_APP.contains(deviceCapability.getCapabilitycode())) {
				capabilities.add(new DeviceCapability(
						null,
						deviceCapability.getCapabilitycode(),
						deviceCapability.getCapabilitycode() == IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_3
								? Base64.encodeBase64String(AES.decrypt(deviceCapability.getCapabilityvalue()))
								: deviceCapability.getCapabilityvalue()
						)
				);

			}
		}

		appliance.setCapability(capabilities);
	}
	public static Appliance createAppliance(Object obj)
	{
		Appliance a = new Appliance();
		try {
			PropertyUtils.copyProperties(a, obj);
		} catch (Throwable e) {
			log.error(e.getMessage() , e);
			return null;
		} 
		return a ;
	}
	
	public static String getRemoteTimezoneId(String deviceid)
	{
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		return getRemoteTimezoneId(r);
	}
	
	public static String getRemoteTimezoneId(Remote r)
	{
		if ( r == null || r.getTimezone() == null || StringUtils.isBlank(r.getTimezone().getId()) )
			return null;
		return r.getTimezone().getId();
	}
	
	private static int getRemoteType(Remote r)
	{
		for ( String did : Utils.WAKEUP_GATEWAY_ID_PREFIX )
			if ( r.getDeviceid().startsWith(did))
				return IRemoteConstantDefine.IREMOTE_TYPE_COBBE_WIFI_LOCK;
		if ( r.getRemotetype() == 0 )
			return IRemoteConstantDefine.IREMOTE_TYPE_NORMAL;
		return IRemoteConstantDefine.IREMOTE_TYPE_COBBE_WIFI_LOCK;
	}

	public static boolean hasCapability(Remote remote, int capabilityCode) {
		return getCapability(remote, capabilityCode) != null;
	}

	public static GatewayCapability getCapability(Remote remote, int capabilityCode) {
		if (remote == null || remote.getCapability() == null) {
			return null;
		}
		for (GatewayCapability capability : remote.getCapability()) {
			if (capability.getCapabilitycode() == capabilityCode) {
				return capability;
			}
		}
		return null;
	}

	public static void setGatewayCapability(Remote remote, int code){
		GatewayCapability capability = getGatewayCapability(remote, code);

		if (capability == null) {
			if (remote.getCapability() == null) {
				remote.setCapability(new ArrayList<>());
			}
			remote.getCapability().add(new GatewayCapability(remote, IRemoteConstantDefine.GATEWAY_CAPABILITY_NOT_SUPPORT_NETWORK));
		}
	}

	private static GatewayCapability getGatewayCapability(Remote remote, int code) {
		if (remote == null || remote.getCapability() == null || remote.getCapability().size() == 0) {
			return null;
		}
		for (GatewayCapability gatewayCapability : remote.getCapability()) {
			if (gatewayCapability.getCapabilitycode() == code) {
				return gatewayCapability;
			}
		}
		return null;
	}

	public static void removeGatewayCapability(Remote remote, int code) {
		if (remote == null || remote.getCapability() == null || remote.getCapability().size() == 0) {
			return;
		}
		Iterator<GatewayCapability> iterator = remote.getCapability().iterator();
		while (iterator.hasNext()) {
			GatewayCapability capability = iterator.next();
			if (capability.getCapabilitycode() == code) {
				iterator.remove();
				break;
			}
		}
	}
}
