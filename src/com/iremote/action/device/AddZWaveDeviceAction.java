package com.iremote.action.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.*;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.common.message.MessageManager;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.*;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.*;
import com.iremote.vo.Appliance;
import com.opensymphony.xwork2.Action;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid")
public class AddZWaveDeviceAction
{
	private static Log log = LogFactory.getLog(AddZWaveDeviceAction.class);
	
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String name;
	private String devicetype;
	private int nuid;
	private String deviceid ;
	private String appendmessage;
	private ZWaveDevice zwavedevice;
	private Appliance appliance ;
	private PhoneUser phoneuser;
	private DeviceInitSetting deviceinitsetting;
	private int zwavedeviceid;

	public String execute()
	{
		createZWavedevice();
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		zds.saveOrUpdate(zwavedevice);
		this.zwavedeviceid = zwavedevice.getZwavedeviceid();

		DeviceHelper.readDeviceProductor(zwavedevice);
		
		if ( phoneuser != null )
		{
			phoneuser.setLastupdatetime(new Date());
			PhoneUserHelper.sendInfoChangeMessage(phoneuser);
		}
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, new InfoChange(deviceid , new Date() , 0) );

		saveDeviceExtendInfo(zwavedevice);

		appliance = this.createAppliance(zwavedevice);
		
		return Action.SUCCESS;
	}

	public int addZWaveDevice(byte[] request)
	{
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		
		if ( r == null )
			return ErrorCodeDefine.DEVICE_NOT_EXSIT;
		
		if ( r.getPhoneuserid() != null )
		{
			PhoneUserService pus = new PhoneUserService();
			phoneuser = pus.query(r.getPhoneuserid());
		}
		
		byte[] bp = TlvWrap.readTag(request, TagDefine.TAG_PRODUCTOR, TagDefine.TAG_HEAD_LENGTH);
		if ( bp == null )
			return ErrorCodeDefine.PARMETER_ERROR;
		String pd = JWStringUtils.toHexString(bp);
		
		DeviceInitSettingService diss = new DeviceInitSettingService();
		deviceinitsetting = diss.querybymid(pd);
		
		if ( deviceinitsetting == null )
			return ErrorCodeDefine.UNKOWN_DEVICE_TYPE;
		
		this.devicetype = deviceinitsetting.getDevicetype();
		
		if (StringUtils.isBlank(name))
			this.name = MessageManager.getmessage(String.format("defaultname_%s", devicetype), r.getPlatform(), PhoneUserHelper.getLanguange(phoneuser)); 
		
		bp = TlvWrap.readTag(request, TagDefine.TAG_PRODUCTOR, TagDefine.TAG_HEAD_LENGTH);
		byte[] bm = TlvWrap.readTag(request, TagDefine.TAG_MODEL, TagDefine.TAG_HEAD_LENGTH);
		byte[] bc = TlvWrap.readTag(request, TagDefine.TAG_DEVICE_SUPOORT_CLASSES, TagDefine.TAG_HEAD_LENGTH);
		
		if ( bm == null )
			bm = new byte[0];
		if ( bc == null )
			bc = new byte[0];
		
		bp = (new TlvByteUnit(TagDefine.TAG_PRODUCTOR , bp)).getByte();
		bm = (new TlvByteUnit(TagDefine.TAG_MODEL , bm)).getByte();
		bc = (new TlvByteUnit(TagDefine.TAG_DEVICE_SUPOORT_CLASSES , bc)).getByte();
		
		int[] am = new int[bp.length+bm.length+bc.length];
		for ( int i = 0 ; i < bp.length ; i ++ )
			am[i] = bp[i] & 0xff ;
		for ( int i = 0 ; i < bm.length ; i ++ )
			am[bp.length + i ] = bm[i] & 0xff ;
		for ( int i = 0 ; i < bc.length ; i ++ )
			am[bp.length + bm.length + i ] = bc[i] & 0xff; 
		
//		System.arraycopy(bp, 0, am, 0, bp.length);
//		System.arraycopy(bm, 0, am, bp.length, bm.length);
//		System.arraycopy(bc, 0, am, bp.length + bm.length, bc.length);
		
		this.appendmessage = JSON.toJSON(am).toString();
		this.execute();
				
		return this.resultCode;
	}
	
	private void createZWavedevice()
	{
		zwavedevice = new ZWaveDevice();
		zwavedevice.setStatus(Utils.getDeviceDefaultStatus(devicetype));
		zwavedevice.setStatuses(Utils.getDeviceDefaultStatuses(devicetype));
		zwavedevice.setBattery(100);

		zwavedevice.setApplianceid(Utils.createsecuritytoken(16));
		zwavedevice.setNuid(nuid);
		zwavedevice.setDevicetype(devicetype);
		zwavedevice.setName(name);
		zwavedevice.setDeviceid(deviceid);
		
		setZWavedeviceProductor(zwavedevice );

		addDeviceCapabiltiy(zwavedevice);

		if ( phoneuser != null )
			DeviceHelper.createSubDevice(zwavedevice, phoneuser.getPlatform(), phoneuser.getLanguage());
		//addSubDevice(zwavedevice);
	}

	@Deprecated
	private void addSubDevice(ZWaveDevice zwavedevice) {
		List<ZWaveSubDevice> zWaveSubDevices = new ArrayList<ZWaveSubDevice>();
		int channelcount = Utils.getDeviceChannelCount(zwavedevice.getDevicetype());
		if ( channelcount > 0)
		{
			for ( int i = 0 ; i < channelcount ; i ++ )
			{
				String name = getChannelDefaultName(zwavedevice);
				ZWaveSubDevice zms = new ZWaveSubDevice(i + 1, String.format("%s%d", name , (i + 1)), zwavedevice, Utils.getDeviceChannelDefaultStatuses(devicetype));
				zWaveSubDevices.add(zms);
			}
			zwavedevice.setzWaveSubDevices(zWaveSubDevices);
		}
	}

	private String getChannelDefaultName(ZWaveDevice zwavedevice) {
		String name = MessageManager.getmessage(String.format("%s_%s", IRemoteConstantDefine.MESSGAE_TYPE_CHANNEL, zwavedevice.getDevicetype()),phoneuser.getPlatform(),phoneuser.getLanguage());
		if (name == null) {
            name = MessageManager.getmessage(IRemoteConstantDefine.MESSGAE_TYPE_CHANNEL ,phoneuser.getPlatform(),phoneuser.getLanguage());
        }
		return name;
	}

	private Appliance createAppliance(Object obj)
	{
		Appliance a = new Appliance();
		try
		{
			PropertyUtils.copyProperties(a, obj);
		} 
		catch (Throwable t)
		{
			log.error(t.getMessage() , t);
			return null ;
		}
		return a ;
	}
	
	protected void addDeviceCapabiltiy(ZWaveDevice device)
	{
		if (  IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(device.getDevicetype()))
		{
			device.setCapability(new ArrayList<DeviceCapability>());
			device.getCapability().add(new DeviceCapability(device , 4));
			if ( ServerRuntime.getInstance().getSystemcode() != IRemoteConstantDefine.PLATFORM_NORTH_AMERICAN
					&& Utils.getRemotePlatform(device.getDeviceid()) != IRemoteConstantDefine.PLATFORM_AMETA )
				device.getCapability().add(new DeviceCapability(device , 1));
		}
		else if (  IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER.equals(device.getDevicetype())
				&&  ServerRuntime.getInstance().getSystemcode() == IRemoteConstantDefine.PLATFORM_NORTH_AMERICAN)
		{
			device.setCapability(new ArrayList<DeviceCapability>());
			device.getCapability().add(new DeviceCapability(device , 1));
		}

		addCapabilityForStandardZwave(device);
	}

	private void addCapabilityForStandardZwave(ZWaveDevice device) {
		List<String> list = JSONArray.parseArray(ServerRuntime.getInstance().getStandardzwaveproductor(), String.class);
		if (list == null || list.size() == 0) {
			return ;
		}

		boolean hasCapabilityToSetPasswordUser = false;

		if (deviceinitsetting == null && StringUtils.isNotBlank(zwavedevice.getProductor())) {
			DeviceInitSettingService diss = new DeviceInitSettingService();
			deviceinitsetting = diss.querybymid(zwavedevice.getProductor());
		}

		if (deviceinitsetting != null && list.contains(deviceinitsetting.getMid())) {
			for (DeviceCapability deviceCapability : device.getCapability()) {
				if (deviceCapability.getCapabilitycode() == 5) {
					hasCapabilityToSetPasswordUser = true;
					break;
				}
			}
			if (!hasCapabilityToSetPasswordUser){
				device.getCapability().add(new DeviceCapability(device, 5));
				device.getCapability().add(new DeviceCapability(device, IRemoteConstantDefine.DEVICE_CAPABILITY_CODE_STANDARD_ZWAVE_DOOR_LOCK));
			}
		}
	}


	private void saveDeviceExtendInfo(ZWaveDevice device )
	{
		DeviceExtendInfo dei = new DeviceExtendInfo();
		dei.setZwavedeviceid(device.getZwavedeviceid());
		dei.setZwaveproductormessage(JSON.toJSONString(appendmessage));
		
		DeviceExtendInfoService svr = new DeviceExtendInfoService();
		svr.save(dei);
	}
	
	private void setZWavedeviceProductor(ZWaveDevice z)
	{
		int[] am = Utils.jsontoIntArray(appendmessage);
		if ( am == null || am.length == 0 )
			return ;
		byte[] bam =new byte[am.length];
		for ( int i = 0 ; i < am.length ; i ++ )
			bam[i] = (byte)am[i];
		
		byte[] bp = TlvWrap.readTag(bam, TagDefine.TAG_PRODUCTOR, 0);
		if ( bp != null )
			z.setProductor(JWStringUtils.toHexString(bp));
		bp = TlvWrap.readTag(bam, TagDefine.TAG_MODEL, 0);
		if ( bp != null )
			z.setModel(JWStringUtils.toHexString(bp));
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setDevicetype(String devicetype)
	{
		this.devicetype = devicetype;
	}

	public void setNuid(int nuid)
	{
		this.nuid = nuid;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setAppendmessage(String appendmessage)
	{
		this.appendmessage = appendmessage;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public Appliance getZwavedevice()
	{
		return appliance;
	}

	public DeviceInitSetting getDeviceinitsetting() {
		return deviceinitsetting;
	}

	public int getZwavedeviceid() {
		return zwavedeviceid;
	}
}
