package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Date;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.NotificationHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ZWaveDeviceService;

public class DeviceMalfunctionProcessor implements IRemoteRequestProcessor {

	private CommandTlv result = new CommandTlv(TagDefine.COMMAND_CLASS_DEVICE , TagDefine.COMMAND_SUB_CLASS_DEVICE_MALFUNCTION_RESPONSE);
	
	private Remote remote;
	private ZWaveDeviceService zds = new ZWaveDeviceService();
	
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException 
	{
		String deviceid = nbc.getDeviceid();

		IremotepasswordService svr = new IremotepasswordService();
		remote = svr.getIremotepassword(deviceid);
		
		processtag78(deviceid , request);
		processtag71(deviceid , request);
		
		result.addUnit(new TlvIntUnit(TagDefine.TAG_RESULT,ErrorCodeDefine.SUCCESS,TagDefine.TAG_LENGTH_2));
		
		return result;
	}

	private void processtag78(String deviceid , byte[] request)
	{
		byte b[] = TlvWrap.readTag(request, 78 , TagDefine.TAG_HEAD_LENGTH);
		
		if ( b == null || b.length == 0 )
			return ;

		for ( int i = 0 ; i < b.length - 1 ; i += 2 )
		{
			if ( b[i + 1] == 0 )  // device is working.
				continue;
									
			devicemalfunction(deviceid , b[i] & 0xff);
		}
	}
	
	private ZWaveDevice createSelfdefineDevice(String deviceid , int nuid)
	{
		ZWaveDevice device = DeviceHelper.createSelfdefineDevice(deviceid, nuid);
		if ( device == null )
			return device ;

		if ( this.remote.getPhoneuserid() != null )
		{
			PhoneUserService pus = new PhoneUserService();
			PhoneUser pu = pus.query(this.remote.getPhoneuserid());
			if ( pu != null )
				pu.setLastupdatetime(new Date());
			PhoneUserHelper.sendInfoChangeMessage(pu);
		}
		
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, new InfoChange(deviceid , new Date() , 0) );
		return device;
	}
	
	private void processtag71(String deviceid , byte[] request)
	{
		Integer nuid = TlvWrap.readInteter(request, TagDefine.TAG_NUID, TagDefine.TAG_HEAD_LENGTH);
		if ( nuid == null )
			return ;
		result.addUnit(new TlvIntUnit(TagDefine.TAG_NUID , nuid , CommandUtil.getnuIdLenght(nuid)));

		devicemalfunction(deviceid , nuid);
	}
	
	private void devicemalfunction(String deviceid , int nuid)
	{
		ZWaveDevice zd = zds.querybydeviceid(deviceid, nuid);
		if ( zd == null && nuid > 10000 )
			zd = createSelfdefineDevice(deviceid , nuid);
		if ( zd == null 
				|| IRemoteConstantDefine.DEVICE_TYPE_SOS_ALARM.equals(zd.getDevicetype())
				|| IRemoteConstantDefine.DEVICE_TYPE_ARM_STATUS_SETTER.equals(zd.getDevicetype()))
			return;
		
		if ( zd.getStatus() != null && zd.getStatus() == -1 )  // device has been malfunction
			return;
		
		zd.setStatus(-1);
		zd.setShadowstatus(null);
		
		Notification n = new Notification(zd , IRemoteConstantDefine.WARNING_TYPE_MALFUNCTION);
		n.setReporttime(new Date());
		n.setPhoneuserid(remote.getPhoneuserid());
		
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);

		NotificationHelper.pushWarningNotification(n,NotificationHelper.catDevicename(remote, zd) , null , false);
		
		JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_MALFUNCTION, new ZWaveDeviceEvent(zd.getZwavedeviceid() , zd.getDeviceid() , zd.getNuid() , IRemoteConstantDefine.WARNING_TYPE_MALFUNCTION,n.getReporttime(),0));
		
	}
}
