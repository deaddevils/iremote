package com.iremote.task.timertask.processor;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.camera.CameraHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.CameraEvent;
import com.iremote.domain.Camera;
import com.iremote.domain.Notification;
import com.iremote.domain.TimerTask;
import com.iremote.service.CameraService;
import com.iremote.service.TimerTaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

public class CameraDelayAlarmingProcessor extends BaseCameraDelayAlarmingProcessor {
    private int cameraid;
    private TimerTask timerTask;
    private String msg;
    private Integer todevicestatus;
    private Integer timerTaskId;
    private int armStatus;
    private Log log = LogFactory.getLog(CameraDelayAlarmingProcessor.class);

    public CameraDelayAlarmingProcessor(Integer timerTaskId) {
        this.timerTaskId = timerTaskId;
    }

    private void init(int cameraid, TimerTask timerTask) {
        CameraService cs = new CameraService();
        Camera camera = cs.query(cameraid);
        setCamera(camera);
        if (timerTask.getJsonpara() != null) {
            JSONObject json = JSONObject.parseObject(timerTask.getJsonpara());
            if (json.containsKey("msg")) {
                msg = json.getString("msg");
                todevicestatus = json.getIntValue("todevicestatus");
                armStatus = json.getIntValue("armstatus");
            }
        }
    }

    @Override
    protected boolean init() {
        TimerTaskService tts = new TimerTaskService();
        this.timerTask = tts.query(timerTaskId);
        if (timerTask != null) {
            this.cameraid = timerTask.getObjid();
            init(cameraid, timerTask);
            return false;
        }
        log.info("timerTask "+ timerTaskId +" not found");
        return true;
    }

    @Override
    protected void pushAlarmMessage() {
        CameraEvent ce = CameraHelper.createCameraEvent(camera);
        ce.setEventtime(new Date());
        ce.setPhoneuserid(cdah.phoneuserid);
        ce.setEventtype(msg);
        ce.setWarningstatus(todevicestatus);
        if (!Utils.isJsonArrayContaints(camera.getWarningstatuses(), todevicestatus)) {
            camera.setWarningstatuses(Utils.jsonArrayAppend(camera.getWarningstatuses(), todevicestatus));
        }
        ce.setWarningstatuses(camera.getWarningstatuses());


        JMSUtil.sendmessage(msg, ce);
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
        Notification notification = new Notification();
        notification.setDeviceid(camera.getDeviceid());
        notification.setMajortype(IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA);
        notification.setDevicetype(camera.getDevicetype());
        notification.setCameraid(cameraid);
        notification.setReporttime(new Date());
        notification.setName(camera.getName());
        notification.setPhoneuserid(cdah.phoneuserid);
        notification.setMessage(msg);
        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

    @Override
    public void setCamera(Camera camera) {
        super.camera = camera;
    }

}
