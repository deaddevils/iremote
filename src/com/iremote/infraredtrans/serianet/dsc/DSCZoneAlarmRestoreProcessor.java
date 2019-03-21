package com.iremote.infraredtrans.serianet.dsc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.DSCEvent;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.*;
import com.iremote.service.TimerTaskService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveSubDeviceService;
import com.iremote.task.timertask.processor.PushHelper;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

public class DSCZoneAlarmRestoreProcessor extends DSCReportBaseProcessor {
    ZWaveDevice zd = new ZWaveDevice();
    boolean pushMessage = false;
    int zoneid;
    int dscpartitionid;
    ZWaveSubDevice zWaveSubDevice;
    boolean needPushZwaveSubDeviceStatus = false;

    @Override
    protected void updateDeviceStatus() {
        ZWaveDeviceService zds = new ZWaveDeviceService();

        zwavedeviceid = zd.getZwavedeviceid();
        changeZwaveSubdevice();

        List<Partition> partitions = zd.getPartitions();
        for (int i = 0; partitions != null && i < partitions.size(); i++) {
            if (partitions.get(i).getDscpartitionid() == dscpartitionid) {
                pushMessage = true;
                if (noWarningSubDevice(partitions.get(i))) {
                    partitions.get(i).setWarningstatus(status);
                }
                partitionid = partitions.get(i).getPartitionid();
                break;
            }
        }
        zds.saveOrUpdate(zd);
    }

    private boolean noWarningSubDevice(Partition partition) {
        List<ZWaveSubDevice> subDevices = partition.getzWaveSubDevices();
        if (subDevices == null) {
            return true;
        }
        for (ZWaveSubDevice subDevice : subDevices) {
            if (DSCHelper.isWarning(subDevice.getWarningstatuses())) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void pushMessage() {
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

            DSCHelper.pushDscUnalarmMessage(zd, zoneid, zoneid);
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
        notification.setMessage(IRemoteConstantDefine.NOTIFICATION_UN_DSC_ALRAM);
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
        notification.setAppendjsonstring(json.toJSONString());
        notification.setPhoneuserid(getPhoneUser().getPhoneuserid());
        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

    @Override
    protected void init() {
        status = IRemoteConstantDefine.DSC_PARTITION_WARNING_STATUS_NORMAL;
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

    private void changeZwaveSubdevice() {
        List<Partition> partitions = zd.getPartitions();
        Partition partition = partitions.stream().filter(f -> f.getDscpartitionid() == dscpartitionid).findFirst().orElse(null);
        if (partition == null) {
            return;
        }

        ZWaveSubDevice zsd = partition.getzWaveSubDevices().stream().filter(f -> f.getChannelid() == zoneid).findFirst().orElse(null);
        if (zsd == null) {
            return;
        }
        changeWarningstaus(zsd);
        createTask(zsd);
    }

    private void changeWarningstaus(ZWaveSubDevice zsd) {
        JSONArray jsonArray = new JSONArray();
        if (zsd.getWarningstatuses() != null) {
            jsonArray = JSONArray.parseArray(zsd.getWarningstatuses());
        }
        if (jsonArray.contains(IRemoteConstantDefine.DSC_ZWAVE_SUB_DEIVCE_WARNING_STATUS_NORMAL)) {
            return;
        }
        jsonArray.removeIf(z -> IRemoteConstantDefine.DSC_ZWAVE_SUB_DEVICE_WARNING_STATUS_ARARM_STATUS.contains(z));
        jsonArray.add(IRemoteConstantDefine.DSC_ZWAVE_SUB_DEIVCE_WARNING_STATUS_ARARM_DELAY);
        zsd.setWarningstatuses(jsonArray.toJSONString().replaceAll(" ", ""));

        needPushZwaveSubDeviceStatus = true;
        zWaveSubDevice = zsd;
    }

    private void createTask(ZWaveSubDevice zsd) {
        TimerTaskService tts = new TimerTaskService();
        TimerTask timerTask = tts.queryByTypeAndObjid(IRemoteConstantDefine.TASK_DSC_CHANNEL_ALARM_RESTORE, zsd.getZwavesubdeviceid());
        long date = System.currentTimeMillis() + 60 * 60 * 1000;
        if (timerTask == null) {
            timerTask = new TimerTask();

            timerTask.setObjid(zsd.getZwavesubdeviceid());
            timerTask.setDeviceid(zd.getDeviceid());
            timerTask.setExcutetime(new Date(date));
            timerTask.setExpiretime(new Date(date + 20 * 60 * 1000));
            timerTask.setCreatetime(new Date());
            timerTask.setType(IRemoteConstantDefine.TASK_DSC_CHANNEL_ALARM_RESTORE);

            tts.save(timerTask);
        } else {
            timerTask.setExcutetime(new Date(date));
            timerTask.setExpiretime(new Date(date + 20 * 60 * 1000));
            tts.update(timerTask);

            ScheduleManager.cancelJob(timerTask.getTimertaskid(), IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK);
        }
        ScheduleManager.excuteWithOutSaveInDB(IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK, timerTask);
    }
}
