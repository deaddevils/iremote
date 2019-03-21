package com.iremote.action.qrcode;

import java.util.*;

import com.iremote.action.helper.*;
import com.iremote.common.encrypt.AES;
import com.iremote.domain.*;
import com.iremote.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteOwnerChangeEvent;
import com.iremote.common.message.MessageManager;
import com.iremote.event.gateway.AutoCreateDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.GatewayReportHelper;
import com.iremote.thirdpart.cobbe.event.CreateWifiLock;
import com.iremote.vo.RemoteData;
import com.opensymphony.xwork2.Action;

public class AddGatewayAction implements IScanQrCodeProcessor
{
	private static Map<String , Integer> GATEWAYTYPEMAP = new HashMap<String , Integer>();

	protected int resultCode = ErrorCodeDefine.SUCCESS;
	private String message;
	private String appendmessage;
	private GatewayInfo gatewayinfo;
	private PhoneUser phoneuser;
	private Remote remotedb ;
	private RemoteData remote ;
	private boolean gatewayonline = false ;
	private String deviceid ;
	private String password;
	private String timezoneid;

	@Override
	public String execute()
	{
		if ( StringUtils.isBlank(message) || StringUtils.isBlank(appendmessage))
		{
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		
		JSONObject jm = JSON.parseObject(message);
		JSONObject am = JSON.parseObject(appendmessage);
		
		if ( check(jm) == false )
			return Action.SUCCESS;
		if ( checkGateway() == false )
			return Action.SUCCESS;
		updateInfo(jm , am);
		createdevice(am);
		pushmessage();

		this.gatewayonline = ConnectionManager.contants(remotedb.getDeviceid());

		remote = GatewayHelper.createRemoteData(remotedb);
		return Action.SUCCESS;
	}
	
	private void createdevice(JSONObject am)
	{
		AutoCreateDevice acd = new AutoCreateDevice(remotedb.getDeviceid() , new Date() , 0 , phoneuser.getPhoneuserid() , null , phoneuser.getPhonenumber() , System.currentTimeMillis());
		acd.setRemote(remotedb);
		acd.run();
		
		ZWaveDevice zd = acd.getZwavedevice();

		if (zd == null) {
			List<ZWaveDevice> zWaveDeviceList = new ZWaveDeviceService().querybydeviceidtype(remotedb.getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK);
			for (ZWaveDevice zWaveDevice : zWaveDeviceList) {
				if (PhoneUserBlueToothHelper.isBlueToothLock(zWaveDevice)) {
					setForBlueTooThLock(zWaveDevice);
					zd = zWaveDevice;
					GatewayHelper.removeGatewayCapability(remotedb, IRemoteConstantDefine.GATEWAY_CAPABILITY_AP_MODE);
					break;
				}
			}
		}

		if ( zd == null )
		{
			CreateWifiLock cwl = new CreateWifiLock(remotedb.getDeviceid() , new Date() , 0 , phoneuser.getPhoneuserid() , null , phoneuser.getPhonenumber() , System.currentTimeMillis());
			cwl.setRemote(remotedb);
			cwl.run();
			zd = cwl.getZwavedevice();
		}

		if ( zd != null ){
			zd.setName(am.getString("name"));
		}
		if(IRemoteConstantDefine.IREMOTE_TYPE_FINGERPRINTREADER==remotedb.getRemotetype()){
			String name = MessageManager.getmessage("defaultname_53" ,phoneuser.getPlatform(),phoneuser.getLanguage());
			zd.setName(name);
		}
	}

	private void setForBlueTooThLock(ZWaveDevice zd) {
		List<Integer> phoneUserIdList = PhoneUserHelper.queryAuthorityByDeviceid(zd.getDeviceid(), zd.getZwavedeviceid(), null);
		DeviceCapability sequence = PhoneUserBlueToothHelper.getCapability(zd, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_SEQUENCE);
		if (sequence != null) {
			sequence.setCapabilityvalue(String.valueOf(Integer.valueOf(sequence.getCapabilityvalue()) + 1));
		}
		Map<Integer, String> map = PhoneUserBlueToothHelper.createPasswordPacket(zd, phoneUserIdList, null);
		if (map == null) {
			return;
		}

		BlueToothPasswordService blueToothPasswordService = new BlueToothPasswordService();
		Integer ownerId = new RemoteService().queryOwnerId(zd.getDeviceid());
		blueToothPasswordService.deleteByZwaveDeviceId(zd.getZwavedeviceid());

		for (Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
			Map.Entry<Integer, String> entry = iterator.next();

			blueToothPasswordService.save(new BlueToothPassword(zd.getZwavedeviceid(), entry.getKey(), entry.getValue()));
			if (entry.getKey().equals(ownerId)) {
				this.password = Base64.encodeBase64String(AES.decrypt(entry.getValue()));
			} else {
//				PhoneUserHelper.sendInfoChangeMessage(new PhoneUserService().query(entry.getKey()));
			}
		}
	}

	private void updateInfo(JSONObject jm , JSONObject am)
	{
		if ( remotedb == null )
		{
			remotedb = GatewayReportHelper.createRemote(deviceid);
			remotedb.setVersion("1.1.2");
			remotedb.setIversion(12);
			remotedb.getCapability().add(new GatewayCapability(remotedb , IRemoteConstantDefine.GATEWAY_CAPABILITY_AP_MODE));
		}
		else 
			checkCapability();
		
		remotedb.setLastupdatetime(new Date());
		remotedb.setPhonenumber(phoneuser.getPhonenumber());
		remotedb.setPhoneuserid(phoneuser.getPhoneuserid());
		remotedb.setName(am.getString("name"));
		if ( remotedb.getSsid() == null )
			remotedb.setSsid("");

		changeRemoteType(jm, am);

		GatewayReportHelper.ensureGatewayCapability(remotedb);
		
		IremotepasswordService svr = new IremotepasswordService();
		if("nblockgateway".equals(gatewayinfo.getGatewaytype())){
			for (Iterator it = remotedb.getCapability().iterator(); it.hasNext(); ) {
				GatewayCapability gc = (GatewayCapability) it.next();
	            if (gc.getCapabilitycode()==IRemoteConstantDefine.GATEWAY_CAPABILITY_AP_MODE) {
	                it.remove();
	            }
	        }
	        GatewayHelper.setGatewayCapability(remotedb, IRemoteConstantDefine.GATEWAY_CAPABILITY_NOT_SUPPORT_NETWORK);
			remotedb.setNetwork(9);
			remotedb.setStatus(1);
			remotedb.setPowertype(IRemoteConstantDefine.REMOTE_POWER_TYPE_BATTERY);
		}

		createTimeZone();
		svr.saveOrUpdate(remotedb);
		
		this.phoneuser.setLastupdatetime(new Date());
	}

	private void changeRemoteType(JSONObject jm, JSONObject am) {
		if (remotedb.getRemotetype() == IRemoteConstantDefine.IREMOTE_TYPE_DRESS_HELPER) {
			changeDressHelperName(remotedb, am);
			return;
		}

		Integer rt = GATEWAYTYPEMAP.get(jm.getString("type"));
		if (StringUtils.isNotEmpty(gatewayinfo.getGatewaytype()) && GATEWAYTYPEMAP.get(gatewayinfo.getGatewaytype()) != null) {
			remotedb.setRemotetype(GATEWAYTYPEMAP.get(gatewayinfo.getGatewaytype()));
		} else {
			if (rt != null) {
				remotedb.setRemotetype(rt);
			} else
				remotedb.setRemotetype(IRemoteConstantDefine.IREMOTE_TYPE_NORMAL);
		}
	}

	private void changeDressHelperName(Remote remotedb, JSONObject am) {
		if (IRemoteConstantDefine.IREMOTE_TYPE_DRESS_HELPER == remotedb.getRemotetype()) {
			ZWaveDeviceService service = new ZWaveDeviceService();
			List<ZWaveDevice> zWaveDevices = service.querybydeviceid(remotedb.getDeviceid());
			String name = am.getString("name");
			if (zWaveDevices != null && zWaveDevices.size() != 0) {
				zWaveDevices.get(0).setName(name);
			}
		}
	}

	private void createTimeZone() {
		if ( StringUtils.isNotBlank(timezoneid)) {
			TimeZone tz = TimeZone.getTimeZone(timezoneid);
			if ( tz != null ){
				if ( remotedb != null )
				{
					if ( remotedb.getTimezone() == null )
						remotedb.setTimezone(new Timezone());
					remotedb.getTimezone().setId(tz.getID());
					remotedb.getTimezone().setZonetext(TimeZoneHelper.createTimezoneDisplayname(tz , phoneuser));
					remotedb.getTimezone().setZoneid(TimeZoneHelper.createTimezoneOffsetHour(tz));
				}
			}
		}
	}

	private void checkCapability()
	{
		if ( remotedb.getCapability() != null && remotedb.getCapability().size() != 0 )
		{
			for ( GatewayCapability gc : remotedb.getCapability() )
				if ( gc.getCapabilitycode() == IRemoteConstantDefine.GATEWAY_CAPABILITY_AP_MODE)
					return ;
		}
		if ( remotedb.getCapability() == null )
			remotedb.setCapability(new ArrayList<GatewayCapability>());
		remotedb.getCapability().add(new GatewayCapability(remotedb , IRemoteConstantDefine.GATEWAY_CAPABILITY_AP_MODE));
	}
	
	private void pushmessage()
	{
		RemoteOwnerChangeEvent re = new RemoteOwnerChangeEvent(remotedb.getDeviceid() , new Date() , 0 , phoneuser.getPhoneuserid() , null , phoneuser.getPhonenumber() , System.currentTimeMillis());
		re.setRemote(remotedb);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_OWNER_CHANGED,re );
		PhoneUserHelper.sendInfoChangeMessage(phoneuser);
	}
	
	private boolean check(JSONObject jm)
	{
		if ( StringUtils.isBlank(jm.getString("qid")))
		{
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return false;
		}
		
		GatewayInfoService gs = new GatewayInfoService();
		gatewayinfo = gs.querybykey(jm.getString("qid"));
		
		if ( gatewayinfo == null )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return false;
		}
		
		this.deviceid = gatewayinfo.getDeviceid();
		
		return true;
	}

	private boolean checkGateway()
	{
		if ( Utils.getRemotePlatform(gatewayinfo.getDeviceid()) != phoneuser.getPlatform() )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return false;
		}
		
		RemoteService rs = new RemoteService();
		remotedb = rs.getIremotepassword(gatewayinfo.getDeviceid());
		
		if ( remotedb == null )
			return true;
		
		if ( remotedb.getPhoneuserid() != null 
				&& remotedb.getPhoneuserid() != 0 
				&& remotedb.getPhoneuserid() != phoneuser.getPhoneuserid())
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return false;
		}
		
		if ( remotedb.getPlatform() != phoneuser.getPlatform() )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return false;
		}
		
		return true;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public void setAppendmessage(String appendmessage)
	{
		this.appendmessage = appendmessage;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	@Override
	public void setTimezoneid(String timezoneid) {
		this.timezoneid = timezoneid;
	}

	public boolean isGatewayonline()
	{
		return gatewayonline;
	}

	public String getDeviceid()
	{
		return deviceid;
	}

	public String getPassword() {
		return password;
	}

	static
	{
		GATEWAYTYPEMAP.put("gateway", IRemoteConstantDefine.IREMOTE_TYPE_NORMAL);
		GATEWAYTYPEMAP.put("lockgateway", IRemoteConstantDefine.IREMOTE_TYPE_COBBE_WIFI_LOCK);
		GATEWAYTYPEMAP.put("airqulitygateway", IRemoteConstantDefine.IREMOTE_TEYP_AIRQUILITY);
		GATEWAYTYPEMAP.put("fingerprintreader", IRemoteConstantDefine.IREMOTE_TYPE_FINGERPRINTREADER);
		GATEWAYTYPEMAP.put("nblockgateway", IRemoteConstantDefine.IREMOTE_TYPE_COBBE_WIFI_LOCK);
	}

	public RemoteData getRemote()
	{
		return remote;
	}
}
