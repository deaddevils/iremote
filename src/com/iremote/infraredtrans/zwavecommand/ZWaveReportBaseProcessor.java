package com.iremote.infraredtrans.zwavecommand;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.common.jms.vo.ZWaveDeviceStatusChange;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.common.taskmanager.IMulitReportTask;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.Notification;
import com.iremote.domain.TimerTask;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCacheManager;
import com.iremote.service.DeviceCapabilityService;
import com.iremote.service.TimerTaskService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.task.timertask.processor.ZwaveDelayAccordingHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Date;
import java.util.List;

public abstract class ZWaveReportBaseProcessor implements IZwaveReportProcessor {
	
	private static Log log = LogFactory.getLog(ZWaveReportBaseProcessor.class);

	protected Notification notification;

	protected ZwaveReportBean zrb ;
	protected boolean finished = false ;
	protected boolean executed = false ;
	
	protected Integer warningstatus = null ;
	
	protected boolean savenotificaton = true;
	protected boolean pushmessage = true;
	
	private Integer armstatus = null ;
	protected boolean eclipsed = false ;
	
	protected Integer oldstatus ;
	protected Integer oldshadowstatus;
	protected String oldstatuses;
	protected ZWaveDevice nosessionzwavedevice;
	
    protected ZwaveDelayAccordingHelper zdah;

	@Override
    public void setReport(ZwaveReportBean zrb) {
		this.zrb = zrb ;
	}
	
	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
    public void run() {
		try {
			executed = true;
			
			parseReport();
			IZwaveReportCache crrentcache = createCacheReport();
			
			if ( crrentcache == null )
				log.info("crrentcache is null ");
			else 
				log.info("crrentcache:" + crrentcache.getCacheKey() + " " + crrentcache.getValue());
			
			if ( (zrb.getReportsequence() == Integer.MIN_VALUE 
					||  zrb.getReportsequence() != zrb.getRemoter().getReportsequence())
                    && !isDuplicate(crrentcache)) {
				process();
				cacheReport(crrentcache);
            } else
				log.info("discard duplicate report");
			
			if ( zrb.getReportsequence() != Integer.MIN_VALUE )
				zrb.getRemoter().setReportsequence(zrb.getReportsequence());

			finished = true;
		} catch (Throwable e) {
			log.error(e.getMessage() , e);
		} 
	}
	
    protected void parseReport() {
		
	}
	
    protected boolean isDuplicate(IZwaveReportCache current) {
		if ( current == null)
			return false ;
		
		IZwaveReportCache old = this.getCahcedReport(current);
		
		if ( old == null )
			log.info("old cached report is null ");
		else 
			log.info("old:" + old.getCacheKey() + " " + old.getValue());
		
		if ( old == null )
			return false ;
		
		return old.getValue().equals(current.getValue()) ;
	}
	
    private void cacheReport(IZwaveReportCache current) {
		if ( current == null )
			return ;
		ZwaveReportCacheManager.getInstance().put(zrb.getDeviceid() , zrb.getNuid(), current);
	}
	
    private IZwaveReportCache getCahcedReport(IZwaveReportCache current) {
		if ( current == null )
			return null ;
		return ZwaveReportCacheManager.getInstance().get(zrb.getDeviceid() , zrb.getNuid(), current);
	}
	
    protected IZwaveReportCache createCacheReport() {
		return null ;
		//return new ZwaveReportCache(zrb.getCommandvalue());
	}
	
	@Override
	public boolean merge(IMulitReportTask task) {
		return false;
	}

	@Override
    public boolean isReady() {
		return true;
	}

	@Override
	public boolean isExecuted() {
		return executed;
	}

    public void process() throws BufferOverflowException, IOException {
        try {
            if (zrb.init() == false) {
				log.info("init failed.");
				return ;
			}
			
            zdah = new ZwaveDelayAccordingHelper(zrb.getDevice());

            setOldStatuses();
			
			recover();
			
			processZwaveReport();
        } catch (Throwable t) {
			log.error(t.getMessage() , t);
		}
		
	}

    protected void setOldStatuses() {
        this.oldstatus = zrb.getDevice().getStatus();
        this.oldshadowstatus = zrb.getDevice().getShadowstatus();
        this.oldstatuses = zrb.getDevice().getStatuses();
    }

    protected void processZwaveReport() {
		updateDeviceStatus();
		
		updatelastactivetime();
		
		savenotification();
		
		pushMessage();
		
		afterprocess();
		
		pushNotificationMessage();
	}
	
	protected abstract void updateDeviceStatus();

	public abstract String getMessagetype();
	
    protected void afterprocess() {
		
	}

    public long getTaskIndentify() {
		return zrb.getReportid();
	}
	
    protected void updatelastactivetime(){
    	int zwavedeviceid = zrb.getDevice().getZwavedeviceid();
    	ZWaveDeviceService zds = new ZWaveDeviceService();
    	ZWaveDevice query = zds.query(zwavedeviceid);
    	boolean flag = false;
    	if(this.oldstatuses==null && zrb.getDevice().getStatuses()==null){
    		flag = true;
    	}
    	if(this.oldstatuses!=null&&this.oldstatuses.equals(zrb.getDevice().getStatuses())){
    		flag = true;
    	}
		if(!(this.oldstatus).equals(zrb.getDevice().getStatus()) || !flag){
			query.setLastactivetime(new Date());
			if(query.getCreatetime()==null){
				query.setCreatetime(new Date());
			}
		}
    }
    
    protected void appendWarningstatus(int warningstatus) {
        if (Utils.isJsonArrayContaints(zrb.getDevice().getWarningstatuses(), warningstatus)) {
			eclipsed = true;
			return ;
		}
		/*if(zrb.getDevice().getArmstatus()==0){
			return;
		}*/
		this.warningstatus = warningstatus;
		zrb.getDevice().setWarningstatuses(Utils.jsonArrayAppend(zrb.getDevice().getWarningstatuses(), warningstatus));
	}
	
    protected void pushMessage() {
		if ( !this.pushmessage 
				|| zrb.getDevice().getEnablestatus() ==  IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE)
			return ;
		
		String mt = this.getMessagetype();
		if ( mt == null || mt.length() == 0 )
			return; 
		
		ZWaveDeviceStatusChange zde = new ZWaveDeviceStatusChange();
		try {
			PropertyUtils.copyProperties(zde, zrb.getDevice());
		} catch (Throwable t) {
			log.error(t.getMessage() , t);
		}
		
		zde.setWarningstatus(this.warningstatus);
		zde.setEventtime(zrb.getReporttime());

		zde.setEventtype(mt);
		zde.setReport(zrb.getReport());
		zde.setAppendmessage(getAppendMessage());
		zde.setDevicetype(zrb.getDevice().getDevicetype());
		zde.setTaskIndentify(zrb.getReportid());
		zde.setChannel(zrb.getCommandvalue().getChannelid());
		zde.setOldstatus(this.oldstatus);
		zde.setOldstatuses(this.oldstatuses);
		zde.setOldshadowstatus(oldshadowstatus);
		zde.setApptokenid(zrb.getApptokenid());
        processMessage(zde);
		JMSUtil.sendmessage(mt, zde);
	}

    protected void processMessage(ZWaveDeviceStatusChange zde) {
    }

    protected JSONObject getAppendMessage() {
		return null ;
	}
	
    protected void savenotification() {
		notification = new Notification();  //avoid null point exception in sub classes;
		if ( !this.savenotificaton || this.getMessagetype() == null )
			return ;
		
		notification.setMessage(this.getMessagetype());
		notification.setDeviceid(zrb.getDeviceid());
		notification.setMajortype(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE);
		notification.setDevicetype(zrb.getDevice().getDevicetype());
		notification.setNuid(zrb.getDevice().getNuid());
		notification.setZwavedeviceid(zrb.getDevice().getZwavedeviceid());
		notification.setReporttime(zrb.getReporttime());
		notification.setOrimessage(Utils.byteArraytoString(zrb.getCmd()));
		notification.setName(zrb.getDevice().getName());
		notification.setPhoneuserid(zrb.getPhoneuserid());
		notification.setWarningstatus(this.warningstatus);
		
		if ( !IRemoteConstantDefine.ASSOCIATION_SCENE_TASK_OPERATOR.equals(zrb.getOperator()))
			notification.setAppendmessage(zrb.getOperator());
		
		if ( eclipsed )
			notification.setEclipseby(1);
		
		if ( zrb.getDevice().getEnablestatus() == IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE)
			notification.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_DEVICE_DISABLE);
        else if (zrb.getPhoneuser() != null) {
			int armstatus = getArmstatus();
			if ( armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM )
				notification.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_USER_DISARM);
			else if ( armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_SLEEPING_ARM )
				notification.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_USER_INHOME_ARM);
			else 
				notification.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_USER_ARM);
        } else
			notification.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_NO_OWNER);
	}
	
    private void pushNotificationMessage() {
		if ( !this.savenotificaton || this.getMessagetype() == null )
			return ;
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
	}
	
    protected void recover() {
		if ( zrb.getDevice().getStatus() == null || zrb.getDevice().getStatus() != -1 )// not a malfunction device
			return ;
				
		Notification n = new Notification(zrb.getDevice() , IRemoteConstantDefine.WARNING_TYPE_RECOVER);
		n.setReporttime(new Date());
		n.setPhoneuserid(zrb.getPhoneuserid());
		
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);

		zrb.getDevice().setStatus(Utils.getDeviceDefaultStatus(zrb.getDevice().getDevicetype()));
		String ds = Utils.getDeviceDefaultStatuses(zrb.getDevice().getDevicetype());
		if ( StringUtils.isNotBlank(ds))
			zrb.getDevice().setStatuses(ds);
		
		JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_RECOVER, new ZWaveDeviceEvent(zrb.getDevice().getZwavedeviceid() , zrb.getDevice().getDeviceid() , zrb.getDevice().getNuid() ,n.getMessage(), n.getReporttime() , zrb.getReportid()));
	}
	
    protected void dontpusmessage() {
		this.pushmessage = false ;
	}
	
    protected void dontsavenotification() {
		this.savenotificaton = false ;
	}

    public Integer getArmstatus() {
        armstatus = PhoneUserHelper.getPhoneuserArmStatus(zrb.getPhoneuser());
		return armstatus;
	}
	
    protected boolean isUserSleepingArmed() {
		if ( !PhoneUserHelper.hasArmFunction(zrb.getPhoneuser()))
			return false ;
		Integer a = this.getArmstatus();
		return ( a != null && a == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_SLEEPING_ARM);
	}
	
    protected boolean isUserOuthomeArmed() {
		if ( !PhoneUserHelper.hasArmFunction(zrb.getPhoneuser()))
			return false ;
		Integer a = this.getArmstatus();
		return ( a != null && a == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM);
	}
	
    protected boolean isUserArmed() {
		return this.isUserOuthomeArmed() || this.isUserSleepingArmed();
	}

	protected boolean isStandardZwaveDoorLock(){

		if (zrb.getDevice() != null) {
			List<DeviceCapability> capability = zrb.getDevice().getCapability();
			for (DeviceCapability deviceCapability : capability) {
				if (deviceCapability.getCapabilitycode() == IRemoteConstantDefine.DEVICE_CAPABILITY_CODE_STANDARD_ZWAVE_DOOR_LOCK) {
					return true;
				}
			}
		} else {
			DeviceCapabilityService dcs = new DeviceCapabilityService();
			DeviceCapability deviceCapability = dcs.query(nosessionzwavedevice.getZwavedeviceid(), IRemoteConstantDefine.DEVICE_CAPABILITY_CODE_STANDARD_ZWAVE_DOOR_LOCK);
			return deviceCapability != null;
		}
		return false;
	}

	@Override
    public void setNoSessionZwaveDevice(ZWaveDevice zwavedevice) {
		nosessionzwavedevice = zwavedevice;
    }
		
    protected void createTimerTask(int type, ZwaveReportBean zrb, String msg, int todevicestatus) {
        TimerTaskService tts = new TimerTaskService();
        TimerTask timerTask = tts.queryByTypeAndObjid(IRemoteConstantDefine.TASK_SENSOR_DELAY_ALARM, zrb.getDevice().getZwavedeviceid());
        if (timerTask != null ){
            if (timerTask.getExcutetime().getTime() > System.currentTimeMillis()) {
                return;
            }
        }else{
            timerTask = new TimerTask();
        }

        timerTask.setCreatetime(new Date());
        long date = System.currentTimeMillis() + zdah.getDeviceDelayTime() * 1000;
        timerTask.setExcutetime(new Date(date));
        timerTask.setExpiretime(new Date(date + IRemoteConstantDefine.TIMER_TASK_EXPIRE_TIME));
        timerTask.setObjid(zrb.getDevice().getZwavedeviceid());
        timerTask.setType(type);
        timerTask.setDeviceid(zrb.getDeviceid());

        JSONObject json = new JSONObject();
        json.put("reportid", zrb.getReportid());
        json.put("msg", msg);
        json.put("todevicestatus", todevicestatus);
		json.put("armstatus", zdah.getArmStatus());
        timerTask.setJsonpara(json.toJSONString());

        ScheduleManager.excuteWithSaveInDB(IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK, timerTask);
}
}
