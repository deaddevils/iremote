package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;

import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;

public class GatewayInfoReportProcessor implements IRemoteRequestProcessor
{

	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException
	{
		Integer rst = TlvWrap.readInteter(request, TagDefine.TAG_RESULT, TagDefine.TAG_HEAD_LENGTH);
		if ( rst == null || rst != 0 )
			return null ;

		String version = TlvWrap.readString(request, TagDefine.TAG_STR_VERSION, TagDefine.TAG_HEAD_LENGTH);
		
		if ( version == null )
			return null;
		
		String deviceid = nbc.getDeviceid();
		if ( deviceid == null )
			return null ;
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		
		r.setIversion(TlvWrap.readInteter(request, TagDefine.TAG_INT_VERSION, TagDefine.TAG_HEAD_LENGTH));
		r.setVersion(version);
		
		if ( GatewayUtils.isLockGateway(r))
		{
			ZWaveDeviceService zds = new ZWaveDeviceService();
			ZWaveDevice zd = zds.querybydeviceid(deviceid, IRemoteConstantDefine.DEVICE_NUID_WIFI_LOCK);
			if ( zd != null )
			{
				zd.setVersion1(version);
				return new CommandTlv((byte)0xfe,(byte)0xf3);
			}
		}
		
		return null ;
	}
	
}
