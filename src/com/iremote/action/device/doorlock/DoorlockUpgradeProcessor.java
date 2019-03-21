package com.iremote.action.device.doorlock;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.push.PushMessage;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.DeviceUpgradePackage;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.event.gateway.GatewayEventConsumerStore;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.infraredtrans.zwavecommand.notifiy.ZwaveReportNotifyManager;
import com.iremote.service.DeviceUpgradePackageService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;

public class DoorlockUpgradeProcessor implements IDoorlockOperationProcessor
{
	private static Log log = LogFactory.getLog(DoorlockUpgradeProcessor.class);
	public static enum DeviceStatus
	{
		blank,
		init,
		gatewayoffline,
		sendingcommand,
		failed,
		success,
		devicebussing
	}
	
	private int zwavedeviceid ;
	private ZWaveDevice zwavedevice;
	private DeviceUpgradePackage curupgradepackage = null ;
	private DeviceUpgradePackage upgradepackage1 = null ;
	private DeviceUpgradePackage upgradepackage2 = null ;
	private byte[] packagecontent;
	private DeviceStatus devicestatus = DeviceStatus.init ;
	private long expiretime = System.currentTimeMillis() + 30 * 1000;
	private boolean hassendcommand = false ;
	private int index = 0 ;
	private int sequence = 0 ;
	private byte packagenum ;
	private boolean packagecontentfinished = false ;
	private String deviceversion1afterupgarde ;
	private String deviceversion2afterupgarde ;
	
	public DoorlockUpgradeProcessor(int zwavedeviceid)
	{
		super();
		this.zwavedeviceid = zwavedeviceid;
	}
	
	@Override
	public void init()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		this.zwavedevice = zds.query(zwavedeviceid);
		
		if ( this.zwavedevice == null )
		{
			this.devicestatus = DeviceStatus.failed;
			return ;
		}
		
		initupgradepackage();
		
		if ( this.devicestatus.equals(DeviceStatus.failed))
			return ;
		
		GatewayEventConsumerStore.getInstance().put(this.zwavedevice.getDeviceid(), this);
		DoorlockOperationStore.getInstance().put(String.valueOf(zwavedeviceid), this);
		ZwaveReportNotifyManager.getInstance().regist(this.zwavedevice.getDeviceid(), this.zwavedevice.getNuid(), 
				new Byte[]{(byte)TagDefine.COMMAND_CLASS_DEVICE_VERSION , (byte)TagDefine.COMMAND_SUB_CLASS_DEVICE_UPGRADE_RESPONSE}, this);

		if ( !ConnectionManager.contants(this.zwavedevice.getDeviceid()))
			this.devicestatus = DeviceStatus.gatewayoffline;
	}

	private void initupgradepackage()
	{
		DeviceUpgradePackageService dps = new DeviceUpgradePackageService();
		List<DeviceUpgradePackage> lst = dps.query(this.zwavedevice.getDevicetype(), this.zwavedevice.getProductor(), this.zwavedevice.getModel());
		
		if ( lst != null )
		{
			for (DeviceUpgradePackage dup : lst )
			{
				if ( StringUtils.isNotBlank(dup.getVersion1())
						&& !dup.getVersion1().equals(this.zwavedevice.getVersion1()))
					upgradepackage1 = dup ;
				if ( StringUtils.isNotBlank(dup.getVersion2())
						&& !dup.getVersion2().equals(this.zwavedevice.getVersion2()))
					upgradepackage2 = dup ;
			}
		}
		
		if ( this.upgradepackage1 != null )
			curupgradepackage = this.upgradepackage1;
		else if ( this.upgradepackage2 != null )
			curupgradepackage = this.upgradepackage2;
		
		if ( curupgradepackage == null )
		{
			this.devicestatus = DeviceStatus.failed;
			return ;
		}
		
		initpackagecontent();
	}
	
	private void initpackagecontent()
	{
		String packagepath = curupgradepackage.getPackagepath();
		if ( StringUtils.isNotBlank(curupgradepackage.getVersion1()))
			packagenum = (byte) 0xf0 ;
		else 
			packagenum = (byte) 0xf2 ;
		try
		{
			this.packagecontent = FileUtils.readFileToByteArray(new File(packagepath));
		} 
		catch (IOException e)
		{
			log.error(e.getMessage() , e);
			this.devicestatus = DeviceStatus.failed;
			return ;
		}
		
		this.sequence = 0 ;
		this.index = 0 ;
		this.hassendcommand = false ;
		this.packagecontentfinished = false ;
	}
	
	@Override
	public void reportArrive(String deviceid, int nuid, byte[] report)
	{
		if ( Utils.isByteMatch(new Byte[]{(byte)0xfe , packagenum}, report) )
		{
			if ( packagecontentfinished )
			{
				Integer rst = TlvWrap.readInteter(report, 1, 4);
				if ( rst == null  )
					sendpackagefinishedcommand();
				else if ( rst == ErrorCodeDefine.SUCCESS)
					this.devicestatus = DeviceStatus.success;
				else 
					this.devicestatus = DeviceStatus.failed;
			}
			else 
			{
				Integer sq = TlvWrap.readInteter(report, 31, 4);
				if ( sq != null && sq == this.sequence - 1)
					sendpackagecontent();
			}
		}
		else if ( Utils.isByteMatch(new Byte[]{(byte)0xfe , (byte)0xf1}, report) )
		{
			Integer rst = TlvWrap.readInteter(report, 1, 4);
			if ( rst == null  ) 
				return ;
			else if ( rst == ErrorCodeDefine.SUCCESS)
			{
				processUpgradeResultReport(report);
			}
		}
	}
	
	private void processUpgradeResultReport(byte[] report)
	{
		Integer rst = TlvWrap.readInteter(report, TagDefine.TAG_RESULT, TagDefine.TAG_HEAD_LENGTH);
		if ( rst == null || rst != 0 )
		{
			this.devicestatus = DeviceStatus.failed;
			return ;
		}
		
		String version = TlvWrap.readString(report, TagDefine.TAG_STR_VERSION, TagDefine.TAG_HEAD_LENGTH);
		
		if ( this.curupgradepackage == this.upgradepackage1)
			this.deviceversion1afterupgarde = version;
		else if ( this.curupgradepackage == this.upgradepackage2)
			this.deviceversion2afterupgarde = version;
		
//		if ( this.curupgradepackage == this.upgradepackage1 
//				&& this.upgradepackage2 != null )
//		{
//			this.curupgradepackage = this.upgradepackage2;
//			this.initpackagecontent();
//			if ( !this.devicestatus.equals(DeviceStatus.failed))
//				sendcommand();
//		}
//		else
		{
			// Working thread changed , zwavedevice is out of hibernate session , so load it again .
			ZWaveDeviceService zds = new ZWaveDeviceService();
			this.zwavedevice = zds.query(zwavedeviceid);
			
			if ( StringUtils.isNotBlank(this.deviceversion1afterupgarde))
				this.zwavedevice.setVersion1(this.deviceversion1afterupgarde);
			if ( StringUtils.isNotBlank(this.deviceversion2afterupgarde))
				this.zwavedevice.setVersion2(this.deviceversion2afterupgarde);
			
			removeupgradecapability();
			
			
			this.devicestatus = DeviceStatus.success;
			List<String> lst = PhoneUserHelper.queryAuthorityAliasByDeviceid(this.zwavedevice.getDeviceid(), zwavedeviceid);
			if ( lst != null )
				PushMessage.pushInfoChangedMessage(lst.toArray(new String[0]) , Utils.getRemotePlatform(this.zwavedevice.getDeviceid()));
		}
	}
		
	private void removeupgradecapability()
	{
		if ( this.curupgradepackage == this.upgradepackage1 
				&& this.upgradepackage2 != null )
			return ;
		ListIterator<DeviceCapability> it = this.zwavedevice.getCapability().listIterator();
		for ( ; it.hasNext();)
		{
			DeviceCapability dc = it.next();
			if ( dc.getCapabilitycode() == 101 )
				it.remove();
		}
		
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(this.zwavedevice.getDeviceid());
		
		if ( r != null && r.getPhoneuserid() != null )
		{
			PhoneUserService pus = new PhoneUserService();
			PhoneUser pu = pus.query(r.getPhoneuserid());
			if ( pu != null )
				pu.setLastupdatetime(new Date());
		}
	}

	@Override
	public long getExpireTime()
	{
		return expiretime;
	}

	@Override
	public void onGatewayOnline()
	{
		sendcommand();
	}

	@Override
	public void onGatewayOffline()
	{
		
	}

	@Override
	public int getStatus()
	{
		if ( expiretime < System.currentTimeMillis())
			this.devicestatus = DeviceStatus.failed;
		return devicestatus.ordinal();
	}

	@Override
	public String getMessage()
	{
		if ( expiretime > System.currentTimeMillis())
			this.devicestatus = DeviceStatus.failed;
		return devicestatus.name();
	}

	@Override
	public boolean isFinished()
	{
		return (devicestatus.equals(DeviceStatus.devicebussing)
				|| devicestatus.equals(DeviceStatus.failed)
				|| devicestatus.equals(DeviceStatus.success));
	}

	@Override
	public synchronized void sendcommand()
	{
		if ( this.packagecontent == null )
			return ;
		if ( hassendcommand == true )
			return ;
		hassendcommand = true;
		this.devicestatus = DeviceStatus.sendingcommand;
		
		expiretime = System.currentTimeMillis() + 15 * 1000;
		
		ZwaveReportNotifyManager.getInstance().regist(this.zwavedevice.getDeviceid(), this.zwavedevice.getNuid(), 
				new Byte[]{(byte)TagDefine.COMMAND_CLASS_DEVICE_VERSION , packagenum}, this);
		
		sendpackagecontent();
	}
	
	private void sendpackagecontent()
	{
		
		byte[] sc = null ;
		if ( index + 128 < this.packagecontent.length )
			sc = new byte[128];
		else 
		{
			sc = new byte[this.packagecontent.length - index];
			packagecontentfinished = true;
		}
		System.arraycopy(this.packagecontent, index, sc, 0, sc.length);
		index += sc.length;
		
		CommandTlv ct = new CommandTlv(0xfe , this.packagenum & 0xff);
		ct.addUnit(new TlvByteUnit(0x34 , sc));
		ct.addUnit(new TlvIntUnit(0x1f , sequence++ , 2));
		
		try
		{
			SynchronizeRequestHelper.sendData(this.zwavedevice.getDeviceid(), ct);
			expiretime = System.currentTimeMillis() + 10 * 1000;
		} 
		catch (Throwable e)
		{
			log.error(e.getMessage() , e);
			this.devicestatus = DeviceStatus.failed;
		} 
	}

	private void sendpackagefinishedcommand()
	{
		CommandTlv ct = new CommandTlv(0xfe , this.packagenum & 0xff);
		ct.addUnit(new TlvIntUnit(0x01 , 0 , 2));
		ct.addUnit(new TlvIntUnit(0x1f , sequence++ , 2));
		
		try
		{
			SynchronizeRequestHelper.sendData(this.zwavedevice.getDeviceid(), ct);
		} 
		catch (Throwable e)
		{
			log.error(e.getMessage() , e);
			this.devicestatus = DeviceStatus.failed;
		} 
	}
}
