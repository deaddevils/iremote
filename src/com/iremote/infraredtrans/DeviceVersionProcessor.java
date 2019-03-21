package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.DeviceUpgradePackage;
import com.iremote.domain.DeviceVersionInfo;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.DeviceUpgradePackageService;
import com.iremote.service.DeviceVersionInfoService;
import com.iremote.service.ZWaveDeviceService;

public class DeviceVersionProcessor implements IRemoteRequestProcessor
{
//	private static Log log = LogFactory.getLog(DeviceVersionProcessor.class);
	
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException
	{
		String version = TlvWrap.readString(request, TagDefine.TAG_STR_VERSION, TagDefine.TAG_HEAD_LENGTH);		
		if ( version == null )
			return null;
		
		String deviceid = nbc.getDeviceid();

		if ( GatewayUtils.isLockGateway(deviceid))
		{
			ZWaveDeviceService zds = new ZWaveDeviceService();
			ZWaveDevice zd = zds.querybydeviceid(deviceid, IRemoteConstantDefine.DEVICE_NUID_WIFI_LOCK);
			if ( zd == null )
				return null;

			String productor = TlvWrap.readString(request, TagDefine.TAG_SELF_DEFINE_RPODUCTOR, TagDefine.TAG_HEAD_LENGTH);
			String model = TlvWrap.readString(request, TagDefine.TAG_SELF_DEFINE_MODEL, TagDefine.TAG_HEAD_LENGTH);

			if ( StringUtils.equals(productor, zd.getProductor())
					&& StringUtils.equals(model, zd.getModel())
					&& StringUtils.equals(version, zd.getVersion2()))
				return null;
					
			if ( StringUtils.isNotBlank(productor))
			{
				zd.setProductor(productor);
				zd.setModel(model);
			}
			
			if ( !version.equals(zd.getVersion2()) )
			{
				zd.setVersion2(version);
				if ( StringUtils.isBlank(productor))
				{
					DeviceVersionInfoService svr = new DeviceVersionInfoService();
					DeviceVersionInfo dvi = svr.querybyVersion(version);
					if ( dvi != null )
					{
						zd.setProductor(dvi.getProductor());
						zd.setModel(dvi.getModel());
					}
				}
				
				DeviceUpgradePackageService dps = new DeviceUpgradePackageService();
				List<DeviceUpgradePackage> lst = dps.query(zd.getDevicetype(), zd.getProductor(), zd.getModel());
				if ( lst == null )
					return null ;
				
				boolean uga = false ;
				for ( DeviceUpgradePackage dp : lst )
				{
					if ( StringUtils.isNotBlank(dp.getVersion1())
							&& !dp.getVersion1().equals(zd.getVersion1()))
						uga = true;
					if ( StringUtils.isNotBlank(dp.getVersion2())
							&& !dp.getVersion2().equals(zd.getVersion2()))
						uga = true;
				}
				
				if ( uga == true)
				{
					for ( DeviceCapability dc : zd.getCapability())
					{
						if ( dc.getCapabilitycode() == 101 )
							return null;
					}
					zd.getCapability().add(new DeviceCapability(zd , 101));
				}
				else 
				{
					ListIterator<DeviceCapability> it = zd.getCapability().listIterator();
					for ( ; it.hasNext();)
					{
						DeviceCapability dc = it.next();
						if ( dc.getCapabilitycode() == 101 )
							it.remove();
					}
				}
			}
		}
		
		return null;
	}

}
