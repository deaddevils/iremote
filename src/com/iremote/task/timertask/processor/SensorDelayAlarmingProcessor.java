package com.iremote.task.timertask.processor;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.Notification;
import com.iremote.domain.TimerTask;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.RemoteService;
import com.iremote.service.TimerTaskService;
import com.iremote.service.ZWaveDeviceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

public class SensorDelayAlarmingProcessor extends BaseZwaveDelayAlarmingProcessor {
    private int zwavedeivceid;
    private TimerTask timerTask;
    private String msg;
    private Integer todevicestatus;
    private Integer timerTaskId;
    private int armStatus;
    private Log log = LogFactory.getLog(SensorDelayAlarmingProcessor.class);

    public SensorDelayAlarmingProcessor(Integer timerTaskId) {
        this.timerTaskId = timerTaskId;
    }

    private void init(int zwavedeivceid, TimerTask timerTask) {
        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice zWaveDevice1 = zds.query(zwavedeivceid);
        setzWaveDevice(zWaveDevice1);
        if (timerTask.getJsonpara() != null) {
            JSONObject json = JSONObject.parseObject(timerTask.getJsonpara());
            if (json.containsKey("msg")) {
                msg = json.getString("msg");
                todevicestatus = json.getInteger("todevicestatus");
                armStatus = json.getIntValue("armstatus");
            }
        }
    }

    @Override
    protected boolean init() {
        TimerTaskService tts = new TimerTaskService();
        this.timerTask = tts.query(timerTaskId);
        if (timerTask != null) {
            this.zwavedeivceid = timerTask.getObjid();
            init(zwavedeivceid, timerTask);
            tts.delete(timerTask);
            return false;
        }
        log.info("timerTask "+ timerTaskId +" not found");
        return true;
    }

    @Override
    protected void pushAlarmMessage() {
        int reportid = 0;

        if (timerTask != null && timerTask.getJsonpara() != null) {
            JSONObject json = JSONObject.parseObject(timerTask.getJsonpara());
            if (json.containsKey("reportid")) {
                reportid = json.getIntValue("reportid");
            }
        }
        ZWaveDeviceEvent zde = new ZWaveDeviceEvent();
        zde.setNuid(zWaveDevice.getNuid());
        zde.setDeviceid(zWaveDevice.getDeviceid());
        zde.setApplianceid(zWaveDevice.getApplianceid());
        zde.setZwavedeviceid(zWaveDevice.getZwavedeviceid());
        if (Utils.isJsonArrayContaints(zWaveDevice.getWarningstatuses(), todevicestatus)) {
            zde.setWarningstatus(null);
            zde.setWarningstatuses(zWaveDevice.getWarningstatuses());
        }else{
            zde.setWarningstatus(todevicestatus);
            zde.setWarningstatuses(Utils.jsonArrayAppend(zWaveDevice.getWarningstatuses(), todevicestatus));
        }
        zde.setDevicetype(zWaveDevice.getDevicetype());
        zde.setStatus(todevicestatus);
        zde.setEventtype(msg);
        zde.setEventtime(new Date());
        zde.setTaskIndentify(reportid);

        JMSUtil.sendmessage(msg, zde);
    }

    @Override
    protected void setAlarmStatus() {
        if (todevicestatus != null) {
            super.appendWarningstatus(todevicestatus);
        } else {
            super.appendWarningstatus(255);
        }
    }

    @Override
    protected void writeLog() {
        RemoteService rs = new RemoteService();
        Integer phoneuserid = rs.queryOwnerId(zWaveDevice.getDeviceid());

        Notification notification = new Notification();
        notification.setPhoneuserid(phoneuserid);
        notification.setDeviceid(zWaveDevice.getDeviceid());
        notification.setNuid(zWaveDevice.getNuid());
        notification.setZwavedeviceid(zWaveDevice.getZwavedeviceid());
        notification.setApplianceid(zWaveDevice.getApplianceid());
        notification.setName(zWaveDevice.getName());
        notification.setMessage(msg);
        notification.setMajortype(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE);
        notification.setDevicetype(zWaveDevice.getDevicetype());
        notification.setReporttime(new Date());
        notification.setEclipseby(0);
        notification.setWarningstatus(todevicestatus);

        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

    @Override
    public void setzWaveDevice(ZWaveDevice zWaveDevice) {
        super.zWaveDevice = zWaveDevice;
    }
}
