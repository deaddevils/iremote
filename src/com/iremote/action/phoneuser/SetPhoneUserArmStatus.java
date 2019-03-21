package com.iremote.action.phoneuser;

import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.device.DeviceOperationAction;
import com.iremote.action.helper.PushEventToThirdpartHelper;
import com.iremote.domain.*;
import com.iremote.domain.TimerTask;
import com.iremote.service.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.UserArmEvent;
import com.iremote.common.jms.vo.ZWaveDeviceStatusChange;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.serianet.dsc.DSCHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.zwavecommand.notifiy.ZwaveReportNotifyManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.opensymphony.xwork2.Action;

import static java.util.stream.Collectors.toList;

public class SetPhoneUserArmStatus {
	
	private static Log log = LogFactory.getLog(SetPhoneUserArmStatus.class);
	protected int resultCode = ErrorCodeDefine.SUCCESS ;
	protected int armstatus;
	protected PhoneUserService phoneuserservice = new PhoneUserService();
	protected List<PhoneUser> familyuserlist = new ArrayList<PhoneUser>();
	protected List<Integer> familyuseridlist = new ArrayList<Integer>();
	protected List<ZWaveDevice> notreadyappliance ;
	protected PhoneUser phoneuser ;
	protected String devicename ;
	private String password = "" ;
	private String message ;
	private boolean savenotification = true;
	
	public String execute()
	{
		initFamilyUser();
		
		if ( (armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM || armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_SLEEPING_ARM )
				&& checkDoorSensor() == false )
			return Action.SUCCESS;
		
		List<ZWaveDevice> zdlst = queryZWaveDevices();

		if ( setDSCArmStatus(zdlst ) == false )
		{
			unregistlocksign(zdlst);
			return Action.SUCCESS;
		}
		
		Family f = null ;
		if ( phoneuser.getFamilyid() != null )
		{
			FamilyService fs = new FamilyService();
			f = fs.query(phoneuser.getFamilyid());
		}
		
		if ( f != null && f.getArmstatus() == armstatus )
		{
			unregistlocksign(zdlst);
			return Action.SUCCESS;
		}
		if ( f == null && phoneuser.getArmstatus() == armstatus )
		{
			unregistlocksign(zdlst);
			return Action.SUCCESS;
		}

		associationElectricfence();
		
		savenotification();
		
		if ( f != null )
			f.setArmstatus(armstatus);
		else 
			phoneuser.setArmstatus(armstatus);
		
		NotificationHelper.pushArmstatusChangeEvent(familyuserlist, phoneuser.getPlatform(), armstatus);

		if (armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM )
		{
			unalarmalldevice(zdlst);
			disarmDelayTimerTask();
		}
		
		sendarmmessage();
		pushEventToThirdpart();
		
		unregistlocksign(zdlst);
		
		return Action.SUCCESS;
	}

	private void associationElectricfence() {
		if (phoneuser.getArmstatus() == armstatus) {
			return;
		}

		RemoteService remoteService = new RemoteService();
		ZWaveDeviceService zWaveDeviceService = new ZWaveDeviceService();

		List<String> devices = remoteService.queryDeviceidByPhoneuserid(phoneuser.getPhoneuserid());
		List<ZWaveDevice> zWaveDevices = zWaveDeviceService.queryByDevicetype(devices,
				Arrays.asList(IRemoteConstantDefine.DEVICE_TYPE_ELECTRIC_FENCE, IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE));

		if (zWaveDevices == null || zWaveDevices.size() == 0) {
			return;
		}

		String operation;
		if (armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM) {
			operation = IRemoteConstantDefine.DEVICE_OPERATION_E_FENCE_DIS_ARM;
		} else {
			operation = IRemoteConstantDefine.DEVICE_OPERATION_E_FENCE_ARM;
		}

		JSONObject json = new JSONObject();
		json.put(IRemoteConstantDefine.OPERATION, operation);

		for (ZWaveDevice zWaveDevice : zWaveDevices) {
			if (IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE.equals(zWaveDevice.getDevicetype())
					&& zWaveDevice.getzWaveSubDevices() != null && zWaveDevice.getzWaveSubDevices().size() != 0) {
				for (ZWaveSubDevice zWaveSubDevice : zWaveDevice.getzWaveSubDevices()) {
					if ((armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM
							&& zWaveSubDevice.getStatus() != IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM)
							|| (armstatus != IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM
							&& zWaveSubDevice.getStatus() != IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM)) {
						continue;
					}
					DeviceOperationAction doa = new DeviceOperationAction();
					doa.setPhoneuser(phoneuser);
					doa.setZwavedeviceid(zWaveDevice.getZwavedeviceid());
					doa.setCommand(json.toJSONString());
					doa.setChannel(zWaveSubDevice.getChannelid());

					doa.execute();
				}
				continue;
			}

			if ((armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM
					&& zWaveDevice.getStatus() != IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM)
					|| (armstatus != IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM
					&& zWaveDevice.getStatus() != IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM)) {
				continue;
			}
			DeviceOperationAction doa = new DeviceOperationAction();
			doa.setPhoneuser(phoneuser);
			doa.setZwavedeviceid(zWaveDevice.getZwavedeviceid());
			doa.setCommand(json.toJSONString());

			doa.execute();
		}
	}

	private void pushEventToThirdpart() {
		String type = (armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM)?
				IRemoteConstantDefine.MESSAGE_PARTITION_DIS_ARM_USER_CODE : IRemoteConstantDefine.MESSAGE_PARTITION_ARM_USER_CODE;
		PushEventToThirdpartHelper.pushEventToThirdpartFromZwavePartition(phoneuser.getPhoneuserid(), null, armstatus, type);
	}


	public void executeonDeviceReport(String devicename)
	{
		this.devicename = devicename;
		this.execute();
	}
	
	private void unregistlocksign(List<ZWaveDevice> lst)
	{
		for ( ZWaveDevice d : lst )
		{
			if (IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(d.getDevicetype())  )
			{
				ZwaveReportNotifyManager.getInstance().unregist(d.getDeviceid(), IRemoteConstantDefine.DEVICE_NUID_DSC, DSCHelper.getLockKeyforPartion1(), null);
			}
		}
	}
	
	private void sendarmmessage()
	{
		UserArmEvent ua = new UserArmEvent();
		ua.setArmstatus(armstatus);
		ua.setCountrycode(phoneuser.getCountrycode());
		ua.setPhonenumber(phoneuser.getPhonenumber());
		ua.setPhoneuserid(phoneuser.getPhoneuserid());
		ua.setPlatform(phoneuser.getPlatform());
		ua.setArmtype(message);
		JMSUtil.sendmessage(message, ua);
	}
	
	private List<ZWaveDevice> queryZWaveDevices()
	{
		IremotepasswordService rs = new IremotepasswordService();
		List<String> didl = rs.queryDeviceidbyPhoneUserid(familyuseridlist);
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> lst = zds.querybydeviceid(didl);
		
		return lst ;		
	}
	
	private boolean setDSCArmStatus(List<ZWaveDevice> lst)
	{
		String command = "" ; 
		if (armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM )
		{
			if ( StringUtils.isNotBlank(password) && password.length() == 4)
				password += "00";
			else 
				password = "";
			command = "0401" + password ;
			
		}
		else if (armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM ) 
			command = "0301";
		else if ( armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_SLEEPING_ARM)
			command = "0311";

		for ( ZWaveDevice d : lst )
		{
			if (IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(d.getDevicetype())  )
			{
				ZwaveReportNotifyManager.getInstance().regist(d.getDeviceid(), IRemoteConstantDefine.DEVICE_NUID_DSC, DSCHelper.getLockKeyforPartion1(), null);
				
				Partition p1 = findPartition(d.getPartitions() , 1);
				if ( p1 == null )
					continue ;
				if ( p1.getArmstatus() == armstatus )
					continue;

				int oldstatus = d.getStatus();
				String oldStatuses = d.getStatuses();

				CommandTlv ct = CommandUtil.createDscCommand(command);
				
				ZwaveReportRequestWrap wrap = ZwaveReportRequestManager.sendRequest(d.getDeviceid(), d.getNuid(), ct, DSCHelper.getArmCommandResponseKeyforPartion1(), IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND, 0);				

				byte[] rst = wrap.getResponse();
				
				if ( rst == null )
				{
					this.resultCode = ErrorCodeDefine.TIME_OUT;
					return false;
				}
				
				String rs = new String(rst);
				if ( StringUtils.isBlank(rs))
				{
					this.resultCode = ErrorCodeDefine.TIME_OUT;
					return false;
				}
				
				if ( rs.startsWith("9001") )
				{
					if ( StringUtils.isBlank(password))
					{
						this.resultCode = ErrorCodeDefine.PASSWORD_REQUIRED;
						return false;
					}
					
					if ( rs.length() >= 5 && Integer.valueOf(rs.substring(5,6)) == 6 && password.length() == 4 )
						password += "00";
					
					ct = CommandUtil.createDscCommand("200" + password);
					
					wrap = ZwaveReportRequestManager.sendRequest(d.getDeviceid(), d.getNuid(), ct, DSCHelper.getArmCommandResponseKeyforPartion1(), IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND, 0);				
				}
				
				//arm or disarm success
				if ( rs.startsWith("6561") || rs.startsWith("6571") || rs.startsWith("6541") || rs.startsWith("6551") || rs.startsWith("6521") )
				{
					if (armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM )
						d.setStatus(0);
					else 
						d.setStatus(255);
					pushMessage(d , oldstatus , oldStatuses);
					continue;
				}
			
				if ( rs.startsWith("6701") ) //password error
					this.resultCode = ErrorCodeDefine.DSC_PASSWORD_ERROR;
				else if ( rs.startsWith("6511"))
					this.resultCode = ErrorCodeDefine.DEVICE_NOT_READY;
				else if ( rs.startsWith("502"))
					this.resultCode = ErrorCodeDefine.UNKNOW_ERROR;
				else if ( rs.startsWith("6721"))
					this.resultCode = ErrorCodeDefine.UNKNOW_ERROR;
				else if ( rs.startsWith("6731"))
					this.resultCode = ErrorCodeDefine.PARTITION_BUSY;

				return false ;
			}
		}
		
		for ( ZWaveDevice d : lst )
		{
			if (IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(d.getDevicetype())  )
			{
				if (  armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM )
                {
                    changeZwavedeviceWarningstatuses(d.getZwavedeviceid());
                }
			}
		}
		return true ;
	}

    private void changeZwavedeviceWarningstatuses(int zWaveDeviceId) {
        ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();

	    List<ZWaveSubDevice> subDeviceList = zsds.querySafeWarningSubDevices(zWaveDeviceId);
	    if (subDeviceList == null) {
	    	return;
	    }
	    for (ZWaveSubDevice zWaveSubDevice : subDeviceList) {
		    zWaveSubDevice.setWarningstatuses(null);
	    }
    }

    protected void pushMessage(ZWaveDevice zd , int oldstatus , String oldStatues )
	{
		ZWaveDeviceStatusChange zde = new ZWaveDeviceStatusChange();
		try {
			PropertyUtils.copyProperties(zde, zd);
		} catch (Throwable t) {
			log.error(t.getMessage() , t);
		}
		
		zde.setEventtime(new Date());
		zde.setEventtype(IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS);
		zde.setDevicetype(zd.getDevicetype());
		zde.setOldstatus(oldstatus);
		zde.setOldstatuses(oldStatues);
		zde.setApptokenid(PhoneUserHelper.getTokenid());
		
		JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS, zde);
	}
	
	private void addOperator(CommandTlv ct )
	{
		ct.addUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE ,IRemoteConstantDefine.OPERATOR_TYPE_APP_USER , 1));
		ct.addUnit(new TlvByteUnit(TagDefine.TAG_OPERATOR ,phoneuser.getPhonenumber().getBytes()));
	}
	
	private String queryPassword(int zwavedeviceid)
	{
		DeviceExtendInfoService dis = new DeviceExtendInfoService();
		List<DeviceExtendInfo> dil = dis.querybyzwavedeviceid(zwavedeviceid);
		
		if ( dil == null )
			return null ;
		
		for ( DeviceExtendInfo dei : dil)
			if ( StringUtils.isNotBlank(dei.getDevicepassword()))
				return dei.getDevicepassword();

		return null ;
	}
	
	private Partition findPartition(List<Partition> lst , int dscpartitionid)
	{
		if ( lst == null || lst.size() == 0 )
			return null ;
		
		for ( Partition p : lst )
			if ( p.getDscpartitionid() == dscpartitionid )
				return p ;
		return null ;
	}
	
	private void unalarmalldevice(List<ZWaveDevice> lst)
	{
		for ( ZWaveDevice d : lst )
		{
			if (IRemoteConstantDefine.DEVICE_TYPE_ALARM.equals(d.getDevicetype())  )
			{
				CommandTlv ct = CommandUtil.createUnalarmCommand(d.getNuid());
				SynchronizeRequestHelper.synchronizeRequest(d.getDeviceid(), ct , 0);
			}
		}
		
	}
	private void disarmDelayTimerTask() {
		RemoteService rs = new RemoteService();
		TimerTaskService tts = new TimerTaskService();
		List<Remote> remotelist = rs.querybyPhoneUserid(familyuseridlist);
		for(Remote r :remotelist){
			List<TimerTask> ttlist = tts.queryByDeviceid(r.getDeviceid());
			for(TimerTask t:ttlist){
				ScheduleManager.cancelJob(t.getTimertaskid(), IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK);
	            tts.delete(t);
			}
		}
		for(int f:familyuseridlist){
			TimerTask timerTask = tts.queryByTypeAndObjid(IRemoteConstantDefine.TASK_PHONEUSER_DELAY_ARM, f);
			if (timerTask !=null){
	            ScheduleManager.cancelJob(timerTask.getTimertaskid(), IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK);
	            tts.delete(timerTask);
	        }
		}
	}

	private void initFamilyUser()
	{
		if( phoneuser.getFamilyid() != null )
		{
			familyuserlist = phoneuserservice.querybyfamiliyid(phoneuser.getFamilyid());
		}
		else 
			familyuserlist.add(phoneuser);
		
		for (PhoneUser pu :familyuserlist)
			familyuseridlist.add(pu.getPhoneuserid());
	}
	
	private boolean checkDoorSensor()
	{
		List<Integer> lst = new ArrayList<Integer>(familyuserlist.size());
		
		for ( PhoneUser u : familyuserlist)
			lst.add(u.getPhoneuserid());
		
		IremotepasswordService rs = new IremotepasswordService();
		List<Remote> rlst = rs.querybyPhoneUserid(lst);
		
		List<String> didl = new ArrayList<String>(rlst.size());
		for ( Remote r : rlst )
			didl.add(r.getDeviceid());
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> zwdl = zds.querybydeviceid(didl);
		
		notreadyappliance = new ArrayList<ZWaveDevice>();
		
		for ( ZWaveDevice zd : zwdl)
		{
			if ( zd.getEnablestatus() == IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE)
				continue;
			if ( !IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_SENSOR.contains(zd.getDevicetype())
					&& !IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zd.getDevicetype()))
				continue;
			if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_SENSOR.contains(zd.getDevicetype())
					&& zd.getStatus() != null 
					&& zd.getStatus() != IRemoteConstantDefine.DEVICE_STATUS_DOOR_SENSOR_CLOSE )
				notreadyappliance.add(zd);
			if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zd.getDevicetype())
					&& zd.getStatus() != null 
					&& zd.getStatus() != IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_CLOSE )
				notreadyappliance.add(zd);
		}
		
		if ( notreadyappliance.size() == 0 )
			return true ;
		
		resultCode = ErrorCodeDefine.PHONEUSER_ARM_SOME_DOORS_OR_WINDOWS_IS_OPENING;
		
		return false;
	}
	
	private void savenotification()
	{
		if ( armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM )
			message = IRemoteConstantDefine.WARNING_TYPE_USER_DISARM;
		else if ( armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_SLEEPING_ARM)
			message = IRemoteConstantDefine.WARNING_TYPE_USER_INHOME_ARM;
		else 
			message = IRemoteConstantDefine.WARNING_TYPE_USER_ARM;
		
		if ( savenotification == false )
			return ;
 
		Notification n = new Notification();
		n.setPhoneuserid(phoneuser.getPhoneuserid());
		n.setFamilyid(phoneuser.getFamilyid());
		n.setMessage(message);
		n.setReporttime(new Date());
		if ( StringUtils.isBlank(devicename)){
			n.setName(phoneuser.getPhonenumber());
			n.setAppendmessage(phoneuser.getPhonenumber());
		}
		else {
			n.setName(devicename);
			n.setAppendmessage(devicename);
		}
		n.setDeviceid("");
		n.setWarningstatus(armstatus);

		JSONObject json = new JSONObject();
		json.put("usercode", IRemoteConstantDefine.DEFAULT_USER_CODE);
		n.setAppendjsonstring(json.toJSONString());
//		NotificationService ns = new NotificationService();
//		ns.save(n);	
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);

	}
	
	public void setArmstatus(int armstatus) {
		this.armstatus = armstatus;
	}

	public int getResultCode() {
		return resultCode;
	}

	public List<ZWaveDevice> getNotreadyappliance() {
		return notreadyappliance;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSavenotification(boolean savenotification) {
		this.savenotification = savenotification;
	}



}
