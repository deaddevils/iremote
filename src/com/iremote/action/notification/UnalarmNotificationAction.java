package com.iremote.action.notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.NotificationHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.asiainfo.connection.WebSocketWrap;
import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.asiainfo.helper.AsiainfoMessageHelper;
import com.iremote.asiainfo.vo.AsiainfoMessage;
import com.iremote.asiainfo.vo.CommandInfo;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.common.jms.vo.ZWaveDeviceUnalarmEvent;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveSubDeviceService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class UnalarmNotificationAction {

	private static Log log = LogFactory.getLog(UnalarmNotificationAction.class);
	
	protected int resultCode = ErrorCodeDefine.SUCCESS ;
	private String deviceid;
	private int nuid;
	private int zwavedeviceid;
	protected PhoneUser phoneuser;
	protected Remote remote;
	protected ZWaveDevice zwavedevice;
	protected Date reporttime = new Date();
	private int channelid;
	private boolean needUnAlarmAlarm = true;

	protected void init()
	{
		if (zwavedevice == null) {
			ZWaveDeviceService zds = new ZWaveDeviceService();
			if (zwavedeviceid > 0) {
				zwavedevice = zds.query(zwavedeviceid);
			} else {
				zwavedevice = zds.querybydeviceid(deviceid, nuid);
			}
		} else {
			zwavedeviceid = zwavedevice.getZwavedeviceid();
		}

		if(zwavedevice != null){
			deviceid = zwavedevice.getDeviceid();
		}

		IremotepasswordService ips = new IremotepasswordService();
		remote = ips.getIremotepassword(deviceid);
	}
	
	public String execute()
	{
		init();
		if ( remote == null ||  zwavedevice == null )
		{
			resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
			return Action.SUCCESS;
		}

		if ( checkprivilege() == false )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}

		if ( !IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE.equals(zwavedevice.getDevicetype())&&(zwavedevice.getWarningstatuses() == null || zwavedevice.getWarningstatuses().length() == 0 ))
		{
			if ( IRemoteConstantDefine.DEVICE_TYPE_ALARM.equals(zwavedevice.getDevicetype()))
				unalarmdevice();
			return Action.SUCCESS;
		}
			
		JSONArray ja = null;
		//DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE
		ZWaveSubDevice zsd = null;
		if(IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE.equals(zwavedevice.getDevicetype())){
			ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
			zsd = zsds.queryByZwavedeviceidAndChannelid(zwavedeviceid, channelid);
			ja = JSON.parseArray(zsd.getWarningstatuses());
		}else{
			ja = JSON.parseArray(zwavedevice.getWarningstatuses());
		}

        if (zwavedevice.getStatus() != IRemoteConstantDefine.DEVICE_STATUS_MALFUNCTION) {
            if ( zwavedevice.getShadowstatus() != null )
                zwavedevice.setStatus(zwavedevice.getShadowstatus());
            else
                zwavedevice.setStatus(Utils.getDeviceDefaultStatus(zwavedevice.getDevicetype()));
        }

		for ( int i = 0 ; i < ja.size()	; i ++ )
		{
			String message = Utils.unalarmmessage(zwavedevice.getDevicetype() , ja.getInteger(i));
			
			if ( message != null ){
				Notification n = null;
				if(IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE.equals(zwavedevice.getDevicetype())){
					n = savenotification(message,zsd.getName());
				}else{
					n = savenotification(message);
				}
				NotificationHelper.pushmessage(n, phoneuser.getPhoneuserid(), NotificationHelper.catDevicename(remote, zwavedevice));

				JMSUtil.sendmessage(message, createUnalarmEventObject(message));
			}
		}
		
		unalarmdevice();
		if(IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE.equals(zwavedevice.getDevicetype())){
			zsd.setWarningstatuses(null);
			ZWaveDeviceEvent zde = createZWaveDeviceEvent(zsd);
			NotificationHelper.pushSubDeviceStatus(zde, new Date(), null);
		}else{
			zwavedevice.setWarningstatuses(null);
			NotificationHelper.pushDeviceStatus(zwavedevice , reporttime , null);
		}

		//NotificationHelper.pushDeviceStatus(zwavedevice , reporttime , null);
		
		return Action.SUCCESS;
	}
	private ZWaveDeviceEvent createZWaveDeviceEvent(ZWaveSubDevice zsd) {
		ZWaveDeviceEvent zde = new ZWaveDeviceEvent();
		zde.setEventtype(IRemoteConstantDefine.NOTIFICATION_SUB_DEVICE_STATUS);
		zde.setEventtime(new Date());
		zde.setDeviceid(zwavedevice.getDeviceid());
		zde.setZwavedeviceid(zwavedevice.getZwavedeviceid());
		zde.setName(zsd.getName());
		zde.setPhoneuserid(phoneuser.getPhoneuserid());
		zde.setApplianceid(zwavedevice.getApplianceid());
		zde.setStatus(zsd.getStatus());
		zde.setStatuses(zsd.getStatuses());
		zde.setWarningstatuses("[]");
		zde.setDevicetype(zwavedevice.getDevicetype());
		zde.setSubdeviceid(zsd.getZwavesubdeviceid());
		zde.setChannel(zsd.getChannelid());
		return zde;
	}

	protected ZWaveDeviceUnalarmEvent createUnalarmEventObject(String message)
	{
		ZWaveDeviceUnalarmEvent ue = new ZWaveDeviceUnalarmEvent(zwavedevice.getZwavedeviceid() , zwavedevice.getDeviceid() , zwavedevice.getNuid() ,message,reporttime);
		ue.setPhonenumber(phoneuser.getPhonenumber());
		ue.setCountrycode(phoneuser.getCountrycode());
		return ue ;
	}
	
	protected boolean checkprivilege()
	{
		if ( phoneuser == null )
			return false ;
		
		if ( phoneuser.getPhoneuserid() != remote.getPhoneuserid())
		{
			if ( PhoneUserHelper.checkPrivilege(phoneuser, zwavedevice) == false ) 
			{
				return false;
			}
		}
		return true;
	}
	
	private void unalarmdevice()
	{
		if (!needUnAlarmAlarm) {
			return;
		}
		List<Integer> pidl;
		if ( phoneuser.getFamilyid() != null && phoneuser.getFamilyid() != 0 )
			pidl = PhoneUserHelper.queryPhoneuseridbyfamilyid(phoneuser.getFamilyid());
		else 
		{
			pidl = new ArrayList<Integer>(1);
			pidl.add(remote.getPhoneuserid());
		}

		IremotepasswordService rs = new IremotepasswordService();
		List<String> didl = rs.queryDeviceidbyPhoneUserid(pidl);
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> lst = zds.querybydeviceid(didl);

		if ( remote.getPlatform() == IRemoteConstantDefine.PLATFORM_ASININFO)
			unalarmAsiainfoAlarmDevice(lst);
		else 
			unalarmAlarmDevice(lst);
	}
	
	protected PhoneUser getPhoneUser()
	{
		PhoneUser u = (PhoneUser) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		
		PhoneUserService pus = new PhoneUserService();
		u = pus.query(u.getPhoneuserid());
		return u ;
	}
	
	private void unalarmAlarmDevice(List<ZWaveDevice> lst)
	{
		IConnectionContext nbc = ConnectionManager.getConnection(deviceid);
		if ( nbc == null || nbc.getAttachment() == null )
		{
			log.info("remote offline");
			return;
		}

		for ( ZWaveDevice d : lst )
		{
			/*if ( !IRemoteConstantDefine.DEVICE_TYPE_ALARM.equals(d.getDevicetype()))
				continue;
			CommandTlv ct = CommandUtil.createUnalarmCommand(d.getNuid());
			SynchronizeRequestHelper.asynchronizeRequest(d.getDeviceid(), ct , 0);*/
			if ( IRemoteConstantDefine.DEVICE_TYPE_ALARM.equals(d.getDevicetype())){
				CommandTlv ct = CommandUtil.createUnalarmCommand(d.getNuid());
				SynchronizeRequestHelper.asynchronizeRequest(d.getDeviceid(), ct , 0);
			}
			if ( IRemoteConstantDefine.DEVICE_TYPE_ELECTRIC_FENCE.equals(d.getDevicetype())&&channelid==0){
				CommandTlv ct = CommandUtil.createEfance1UnalarmCommand(d.getNuid());
				SynchronizeRequestHelper.asynchronizeRequest(d.getDeviceid(), ct , 0);
			}
			if ( IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE.equals(d.getDevicetype())&&channelid!=0){
				CommandTlv ct = CommandUtil.createEfance2UnalarmCommand(d.getNuid(),(byte)channelid);
				SynchronizeRequestHelper.asynchronizeRequest(d.getDeviceid(), ct , 0);
			}
		}
	}
	
	private void unalarmAsiainfoAlarmDevice(List<ZWaveDevice> lst)
	{
		AsiainfoMessage message = AsiainfoMessageHelper.createMessageHead(IRemoteConstantDefine.ASIAINFO_MSG_ID_SET_COMMAND , deviceid);

		for ( ZWaveDevice d : lst )
		{
			if ( !IRemoteConstantDefine.DEVICE_TYPE_ALARM.equals(d.getDevicetype()))
				continue;
			CommandTlv ct = CommandUtil.createUnalarmCommand(d.getNuid());
			byte[] cmd = ct.getByte();
			
			message.setMessage(cmd);
			message.setMessagelength(cmd.length);
			
			CommandInfo ci = new CommandInfo();
			ci.setDeviceId( Utils.createZwaveDeviceid(deviceid , d.getZwavedeviceid(),d.getNuid()));
			ci.setOperUser(PhoneUserHelper.getUserId(phoneuser));
			ci.setUserType(PhoneUserHelper.getUserType(phoneuser));
			ci.setControlList(AsiainfoHttpHelper.parseCommand(d.getDevicetype() , cmd));
			message.setMessageinfo(JSON.toJSONString(ci));
			
			try {
				WebSocketWrap.writeMessage(message);
			} catch (TimeoutException e) {
				log.error(e.getMessage() , e);
			} catch (IOException e) {
				log.error(e.getMessage() , e);
			}
		}
	}

	private Notification savenotification(String message){
		Notification n = new Notification();
		n.setMessage(message);
		n.setDeviceid(deviceid);
		n.setMajortype(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE);
		n.setDevicetype(zwavedevice.getDevicetype());
		n.setNuid(zwavedevice.getNuid());
		n.setReporttime(reporttime);
		n.setName(zwavedevice.getName());
		n.setZwavedeviceid(zwavedevice.getZwavedeviceid());
		setUnalarmuser(n);

		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);
		return n ;
	}
	private Notification savenotification(String message,String name){
		Notification n = new Notification();
		n.setMessage(message);
		n.setDeviceid(deviceid);
		n.setMajortype(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE);
		n.setDevicetype(zwavedevice.getDevicetype());
		n.setReporttime(reporttime);
		n.setName(zwavedevice.getName());
		JSONObject json = new JSONObject();
        json.put("channelid", channelid);
        json.put("channelname", name);
		n.setAppendjson(json);
		setUnalarmuser(n);

		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);
		return n ;
	}
	
	protected void setUnalarmuser(Notification notification)
	{
		notification.setUnalarmphonenumber(phoneuser.getPhonenumber());
		notification.setUnalarmphoneuserid(phoneuser.getPhoneuserid());
		notification.setPhoneuserid(remote.getPhoneuserid());
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public void setNuid(int nuid) {
		this.nuid = nuid;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	protected String getDeviceid() {
		return deviceid;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setChannelid(int channelid) {
		this.channelid = channelid;
	}

	void setZwavedevice(ZWaveDevice zwavedevice) {
		this.zwavedevice = zwavedevice;
	}

	void setNeedUnAlarmAlarm(boolean needUnAlarmAlarm) {
		this.needUnAlarmAlarm = needUnAlarmAlarm;
	}
}
