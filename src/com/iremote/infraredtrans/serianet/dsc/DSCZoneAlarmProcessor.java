package com.iremote.infraredtrans.serianet.dsc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.DSCEvent;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.*;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.TimerTaskService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveSubDeviceService;
import com.iremote.task.timertask.processor.PushHelper;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

public class DSCZoneAlarmProcessor extends DSCReportBaseProcessor {
    ZWaveDevice zd = new ZWaveDevice();
    int dscpartitionid;
    int zoneid;
    boolean pushMessage = false;
    ZWaveSubDevice zWaveSubDevice;
    boolean needPushZwaveSubDeviceStatus = false;

    private Log log = LogFactory.getLog(DSCZoneAlarmProcessor.class);

    @Override
    protected void updateDeviceStatus() {
        ZWaveDeviceService zds = new ZWaveDeviceService();

        changeZwaveSubDeviceWarningStatus();

        boolean hasDscPartitionid = false;
        for (int i = 0; zd.getPartitions() != null && i < zd.getPartitions().size(); i++) {
            if (zd.getPartitions().get(i).getDscpartitionid() == dscpartitionid) {
                hasDscPartitionid = true;
                partitionid = zd.getPartitions().get(i).getPartitionid();
                if (zd.getPartitions().get(i).getWarningstatus() != status) {
                    pushMessage = true;
                    zd.getPartitions().get(i).setWarningstatus(status);
                }
                break;
            }
        }
        if (!hasDscPartitionid) {
            partitionid = addPartition(dscpartitionid, zd, 0, status);
            pushMessage = true;
        }
        if (partitionid == 0) {
            return;
        }
        zds.saveOrUpdate(zd);
        zwavedeviceid = zd.getZwavedeviceid();
    }

    private void changeZwaveSubDeviceWarningStatus() {
        ZWaveSubDevice zsd = zd.getzWaveSubDevices().stream().filter(s -> s.getChannelid() == zoneid).findFirst().orElse(null);
        if (zsd == null) {
            return;
        }
        JSONArray jsonArray = zsd.getWarningstatuses() == null ? new JSONArray() : JSONArray.parseArray(zsd.getWarningstatuses());
        if (jsonArray.contains(IRemoteConstantDefine.DSC_ZWAVE_SUB_DEIVCE_WARNING_STATUS_ARARM_DELAY)){
            TimerTaskService tts = new TimerTaskService();
            TimerTask timerTask = tts.queryByTypeAndObjid(IRemoteConstantDefine.TASK_DSC_CHANNEL_ALARM_RESTORE, zsd.getZwavesubdeviceid());
            if (timerTask !=null){
                ScheduleManager.cancelJob(timerTask.getTimertaskid(), IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK);
                tts.delete(timerTask);
            }
        }
        jsonArray.removeIf(z -> IRemoteConstantDefine.DSC_ZWAVE_SUB_DEVICE_WARNING_STATUS_ARARM_STATUS.contains(z));
        jsonArray.add(IRemoteConstantDefine.DSC_ZWAVE_SUB_DEIVCE_WARNING_STATUS_ALARM);
        if (!DSCHelper.isWarning(zsd.getWarningstatuses())) {
            pushMessage = true;
        }
        zsd.setWarningstatuses(jsonArray.toJSONString().replaceAll(" ",""));

        needPushZwaveSubDeviceStatus = true;
        zWaveSubDevice = zsd;
    }

    @Override
    protected void pushMessage() {
        log.info(pushMessage + " " + partitionid);
        if (pushMessage && partitionid != 0) {
            DSCEvent event = new DSCEvent();
            event.setPartitionid(partitionid);
            event.setDeviceid(seriaNetReportBean.getDeviceid());
            event.setZwavedevceid(zwavedeviceid);
            event.setWarningstatus(status);
            event.setChannelid(zoneid);
            event.setType(IRemoteConstantDefine.NOTIFICATION_DSC_PARTITION_WARNING_STATUS);
            event.setDscparitionid(dscpartitionid);

            JMSUtil.sendmessage(IRemoteConstantDefine.EVENT_DSC_PARTITION_WARNING_STATUS, event);

            DSCHelper.pushDscAlarmMessage(zd, zoneid, zoneid);
        }

        if (needPushZwaveSubDeviceStatus) {
            PushHelper.pushZwaveSubDeviceStatusMessage(zWaveSubDevice);
        }
    }

    @Override
    protected void writeLog() {
        if (!pushMessage || partitionid == 0) {
            return;
        }
        ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
        ZWaveSubDevice zWaveSubDevice = zsds.queryByZwavedeviceidAndChannelid(zd.getZwavedeviceid(), zoneid);
        Notification notification = new Notification();
        notification.setDeviceid(seriaNetReportBean.getDeviceid());
        notification.setReporttime(new Date());
        notification.setZwavedeviceid(zd.getZwavedeviceid());
        notification.setNuid(zd.getNuid());
        notification.setMajortype(zd.getMajortype());
//        notification.setApplianceid(zd.getApplianceid());
        notification.setName(zd.getName());
        notification.setMessage(IRemoteConstantDefine.NOTIFICATION_DSC_ALRAM);
        notification.setDevicetype(zd.getDevicetype());
        notification.setWarningstatus(status);
        JSONObject json = new JSONObject();
        json.put("dscpartitionid", dscpartitionid);
        json.put("channelid", zoneid);
        for (int i = 0; zd.getPartitions() != null && i < zd.getPartitions().size(); i++) {
            if (zd.getPartitions().get(i).getDscpartitionid() == dscpartitionid) {
//                json.put("partitionname", zd.getPartitions().get(i).getName());
                if (zWaveSubDevice != null) {
                    notification.setName(zd.getName() + " " + zWaveSubDevice.getName());
                }
                break;
            }
        }
        notification.setPhoneuserid(getPhoneUser().getPhoneuserid());
        notification.setAppendjsonstring(json.toJSONString());
        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

    @Override
    protected void init() {
        status = IRemoteConstantDefine.DSC_PARTITION_WARNING_STATUS_ARM;
        dscpartitionid = getValue(seriaNetReportBean.getCmd(), 3, 4);
        zoneid = getValue(seriaNetReportBean.getCmd(), 4, 7);

        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zWaveDeviceList = zds.querybydeviceidtype(seriaNetReportBean.getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_DSC);
        if (zWaveDeviceList == null || zWaveDeviceList.size() == 0){
            initSucceed = false;
            return;
        }
        zd = zWaveDeviceList.get(0);

        ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
        ZWaveSubDevice zsd = zsds.queryByZwavedeviceidAndChannelid(zd.getZwavedeviceid(), zoneid);

        if (zsd != null && StringUtils.isBlank(zsd.getSubdevicetype())) {
            initSucceed = false;
        }
    }
}
