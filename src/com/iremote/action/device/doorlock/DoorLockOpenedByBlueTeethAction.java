package com.iremote.action.device.doorlock;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PhoneUserBlueToothHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.common.jms.vo.ZWaveDeviceStatusChange;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.*;
import com.iremote.infraredtrans.zwavecommand.ZwaveReportBean;
import com.iremote.service.RemoteService;
import com.iremote.service.TimerTaskService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.task.timertask.processor.ZwaveDelayAccordingHelper;
import com.opensymphony.xwork2.Action;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "device", parameter = "zwavedeviceid")
public class DoorLockOpenedByBlueTeethAction {
    private Log log = LogFactory.getLog(DoorLockOpenedByBlueTeethAction.class);
    private Integer zwavedeviceid;
    private Integer newsequence;
    private PhoneUser phoneuser;
    private Integer battery;
    private int resultCode;

    public String execute(){
        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice zd = zds.query(zwavedeviceid);
        if (zd == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return Action.SUCCESS;
        }

        if (!PhoneUserBlueToothHelper.isOnlyBlueToothLock(zd)) {
            return Action.SUCCESS;
        }

        Notification notification = new Notification();
        notification.setPhoneuserid(phoneuser.getPhoneuserid());
        notification.setDeviceid(zd.getDeviceid());
        notification.setZwavedeviceid(zd.getZwavedeviceid());
        notification.setReporttime(new Date());
        notification.setMessage(IRemoteConstantDefine.NOTIFICATION_TYPE_DOOR_LOCK_OPEN_BY_BLUE_TOOTH);
        notification.setName(zd.getName());
        notification.setMajortype(zd.getMajortype());
        notification.setDevicetype(zd.getDevicetype());
        notification.setAppendmessage(phoneuser.getPhonenumber());

        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);

        if (newsequence != null) {
            JSONObject json = new JSONObject();
            json.put("newsequence", newsequence);
            notification.setAppendjson(json);
            notification.setMessage(IRemoteConstantDefine.NOTIFICATION_TYPE_BLUE_TOOTH_SEQUENCE_CHANGED);

            JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
        }

        if (battery != null) {
            processorBattery(zd, battery);
        }
        
        triggerAssociation(zd);

        if (zd.getStatus() == IRemoteConstantDefine.DEVICE_STATUS_MALFUNCTION) {
            zd.setStatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_CLOSE);
        }
        return Action.SUCCESS;
    }

    private void processorBattery(ZWaveDevice zd, Integer battery) {
        if (battery > 100 && battery != 255) {
            return;
        }
        if (battery == 255) {
            battery = 10;
        }
        zd.setBattery(battery);

        sendMessage(zd, battery);
    }

    private void sendMessage(ZWaveDevice zd, Integer battery) {
        String message = battery <= 10 ? IRemoteConstantDefine.WARNING_TYPE_LOW_BATTERY : IRemoteConstantDefine.WARNING_TYPE_BATTERY;

        ZWaveDeviceEvent zde = new ZWaveDeviceEvent();
        try {
            PropertyUtils.copyProperties(zde, zd);
        } catch (Throwable t) {
            log.error(t.getMessage() , t);
        }
        zde.setEventtime(new Date());
        zde.setEventtype(message);
        zde.setDevicetype(zd.getDevicetype());

        JMSUtil.sendmessage(message, zde);
    }

    private void triggerAssociation(ZWaveDevice zd) {
        int warningstatus = 0;
        ZwaveDelayAccordingHelper zdah = new ZwaveDelayAccordingHelper(zd);
        if (zdah.hasArmedByUserSetting()) {
            warningstatus = IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN;
        }
        if (warningstatus != 0 && zdah.hasSetDelayAlarm()) {
            createTimerTask(IRemoteConstantDefine.TASK_SENSOR_DELAY_ALARM, zd,
                    IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN_DELAY_WARNING,
                    IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN, zdah.getDeviceDelayTime(), zdah.getArmStatus());
            return;
        }

        if (warningstatus != 0 && !Utils.isJsonArrayContaints(zd.getWarningstatuses(), warningstatus)) {
            zd.setWarningstatuses(Utils.jsonArrayAppend(zd.getWarningstatuses(), warningstatus));
        }

        ZWaveDeviceStatusChange zde = new ZWaveDeviceStatusChange();
        try {
            PropertyUtils.copyProperties(zde, zd);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        zde.setEventtime(new Date());
        zde.setWarningstatus(warningstatus);
        zde.setEventtype(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN);
        zde.setStatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
        JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN, zde);
    }

    protected void createTimerTask(int type, ZWaveDevice zd, String msg, int todevicestatus, int delayTime, int armstatus) {
        TimerTaskService tts = new TimerTaskService();
        TimerTask timerTask = tts.queryByTypeAndObjid(IRemoteConstantDefine.TASK_SENSOR_DELAY_ALARM, zd.getZwavedeviceid());
        if (timerTask != null ){
            if (timerTask.getExcutetime().getTime() > System.currentTimeMillis()) {
                return;
            }
        }else{
            timerTask = new TimerTask();
        }

        timerTask.setCreatetime(new Date());
        long date = System.currentTimeMillis() + delayTime * 1000;
        timerTask.setExcutetime(new Date(date));
        timerTask.setExpiretime(new Date(date + IRemoteConstantDefine.TIMER_TASK_EXPIRE_TIME));
        timerTask.setObjid(zd.getZwavedeviceid());
        timerTask.setType(type);
        timerTask.setDeviceid(zd.getDeviceid());

        JSONObject json = new JSONObject();
//        json.put("reportid", zrb.getReportid());
        json.put("msg", msg);
        json.put("todevicestatus", todevicestatus);
        json.put("armstatus", armstatus);
        timerTask.setJsonpara(json.toJSONString());

        ScheduleManager.excuteWithSaveInDB(IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK, timerTask);
    }

    public void setZwavedeviceid(Integer zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public void setNewsequence(Integer newsequence) {
        this.newsequence = newsequence;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public void setBattery(Integer battery) {
        this.battery = battery;
    }

    public int getResultCode() {
        return resultCode;
    }
}
