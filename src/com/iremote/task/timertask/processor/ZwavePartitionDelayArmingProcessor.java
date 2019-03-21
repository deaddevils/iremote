package com.iremote.task.timertask.processor;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.helper.PushEventToThirdpartHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.DSCEvent;
import com.iremote.domain.*;
import com.iremote.service.PartitionService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.TimerTaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

public class ZwavePartitionDelayArmingProcessor implements Runnable {
    private PhoneUser phoneuser;
    private TimerTask timerTask;
    private Integer timerTaskid;
    private int armStatus;
    private Partition partition;
    private String operator;
    private CheckDoorSensorHelper checkDoorSensorHelper;
    private static Log log = LogFactory.getLog(ZwavePartitionDelayArmingProcessor.class);


    public ZwavePartitionDelayArmingProcessor(Integer timerTaskid) {
        this.timerTaskid = timerTaskid;
    }

    private boolean init() {
        TimerTaskService tts = new TimerTaskService();
        this.timerTask = tts.query(timerTaskid);
        if (timerTask != null) {
            int partitionid = timerTask.getObjid();
            tts.delete(timerTask);
            PartitionService ps = new PartitionService();
            partition = ps.query(partitionid);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void run() {
        if (init()) {
            return;
        }
        setPhoneuser();

        log.info("armStatus: " + armStatus);

        partition.setArmstatus(armStatus);
        checkDoorSensorHelper = new CheckDoorSensorHelper(partition);

        pushArmMsg(checkDoorSensorHelper.checkDoorSensor(armStatus));
        PushEventToThirdpartHelper.pushEventToThirdpartFromZwavePartition(phoneuser.getPhoneuserid(), partition.getPartitionid(),
                armStatus, IRemoteConstantDefine.MESSAGE_PARTITION_ARM_USER_CODE);
    }

    private void pushArmMsg(boolean isSuccess) {
        if (phoneuser == null) {
            return;
        }

        pushMessage();
        writeLog();

        if (!isSuccess) {
            PushHelper.pushZwaveArmFailedMessage(phoneuser, checkDoorSensorHelper.getNotreadyappliance());
        }
    }

    private void pushMessage() {
        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_DSC_PARTITION_ARM_STATUS,
                new DSCEvent(partition.getPartitionid(), partition.getPhoneuser().getPhoneuserid(),
                        armStatus, new Date(), IRemoteConstantDefine.NOTIFICATION_DSC_ARM_STATUS_CHANGE, partition.getPhoneuser().getPlatform(), partition.getName()));
    }

    private void setPhoneuser() {
        String jsonpara = timerTask.getJsonpara();
        if (jsonpara == null) {
            return;
        }
        JSONObject json = JSONObject.parseObject(jsonpara);
        armStatus = json.getIntValue("armStatus");
        if (armStatus == 5 || armStatus == 4 ){
            armStatus = IRemoteConstantDefine.PARTITION_STATUS_OUT_HOME;
        }
        Integer phoneuserid = json.getInteger("phoneuserid");
        PhoneUserService pus = new PhoneUserService();
        phoneuser = pus.query(phoneuserid);

        operator = json.getString("operator");
    }

    private void writeLog() {
        String msg;
        if (armStatus == 1) {
            msg = IRemoteConstantDefine.MESSAGE_PARTITION_ARM;
        } else {
            msg = IRemoteConstantDefine.MESSAGE_PARTITION_HOME_ARM;
        }
        Notification notification = new Notification();

        notification.setDeviceid("");
        notification.setName("");
        notification.setAppendmessage(operator !=null ? operator : phoneuser.getPhonenumber());
        ZWaveDevice zd = partition.getZwavedevice();
        if (zd != null) {
            notification.setDeviceid(zd.getDeviceid());
            notification.setZwavedeviceid(zd.getZwavedeviceid());
            notification.setMajortype(zd.getMajortype());
            notification.setDevicetype(zd.getDevicetype());
            notification.setNuid(zd.getNuid());
            notification.setName(zd.getName());
        }
        notification.setReporttime(new Date());
        notification.setFamilyid(phoneuser.getFamilyid());
        notification.setMessage(msg);
        notification.setPhoneuserid(phoneuser.getPhoneuserid());
        JSONObject json = new JSONObject();
        json.put("dscpartitionid", null);
        json.put("partitionname", partition.getName());
        json.put("partitionid", partition.getPartitionid());
        notification.setAppendjsonstring(json.toJSONString());
        notification.setWarningstatus(armStatus);

        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

}
