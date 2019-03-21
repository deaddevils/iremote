package com.iremote.infraredtrans.serianet.dsc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.DSCEvent;
import com.iremote.common.jms.vo.SubdeviceStatusEvent;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.*;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.serianet.SeriaNetReportBean;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DSCPartitionDisarmedProcessor extends DSCPartitionInAlarmProcessor {
    private static Log log = LogFactory.getLog(DSCPartitionDisarmedProcessor.class);

    @Override
    protected void updateDeviceStatus() {
        super.updateDeviceStatus();
        changeZwaveSubDeviceStatus();
//        DSCHelper.synArmStatus(seriaNetReportBean.getDeviceid(), dscpartitionid, IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM);
        disarmDelayTimerTask();

        unalarmAlarm();
    }

    private void unalarmAlarm() {
        ZWaveDeviceService zds = new ZWaveDeviceService();
        RemoteService rs = new RemoteService();
        List<String> devices = rs.queryDeviceidByPhoneuserid(getPhoneUser().getPhoneuserid());
        List<ZWaveDevice> zdList = zds.queryByDevicetypeAndPartitionid(devices, IRemoteConstantDefine.DEVICE_TYPE_ALARM, partitionid);

        for (ZWaveDevice d : zdList) {
            CommandTlv ct = CommandUtil.createUnalarmCommand(d.getNuid());
            SynchronizeRequestHelper.asynchronizeRequest(d.getDeviceid(), ct , 0);
        }
    }

    private void disarmDelayTimerTask() {
    	ZWaveDeviceService zds = new ZWaveDeviceService();
    	List<ZWaveDevice> zwavepartitionlist = zds.querybypartitionid(partitionid);
		TimerTaskService tts = new TimerTaskService();
		TimerTask timerTask = tts.queryByTypeAndObjid(IRemoteConstantDefine.TASK_ZWAVE_PARTITION_DELAY_ARM, partitionid);
		if (timerTask !=null){
            ScheduleManager.cancelJob(timerTask.getTimertaskid(), IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK);
            tts.delete(timerTask);
        }
		for(ZWaveDevice z:zwavepartitionlist){
			TimerTask timerTask1 = tts.queryByTypeAndObjid(IRemoteConstantDefine.TASK_SENSOR_DELAY_ALARM, z.getZwavedeviceid());
	        if (timerTask1 != null) {
	            ScheduleManager.cancelJob(timerTask1.getTimertaskid(), IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK);
	            tts.delete(timerTask1);
	        }
		}
	}
    protected void changeZwaveSubDeviceStatus() {
        Partition partition = zwaveDevice.getPartitions().stream().filter(p -> p.getDscpartitionid() == dscpartitionid).findFirst().get();
        List<ZWaveSubDevice> zWaveSubDeviceList = partition.getzWaveSubDevices();
        for (ZWaveSubDevice zsd : zWaveSubDeviceList) {
            restoreChannelEnableStatus(zsd);
            restoreChannelWarningStatuses(zsd);

            com.iremote.task.timertask.processor.PushHelper.pushZwaveSubDeviceStatusMessage(zsd);
        }
    }

    private void restoreChannelWarningStatuses(ZWaveSubDevice zsd) {
        if (zsd.getWarningstatuses() != null) {
            JSONArray jsonArray = JSONArray.parseArray(zsd.getWarningstatuses());
            jsonArray.removeIf(w -> w.equals(IRemoteConstantDefine.DSC_ZWAVE_SUB_DEIVCE_WARNING_STATUS_ARARM_DELAY));
            zsd.setWarningstatuses(jsonArray.toJSONString().replaceAll(" ",""));
        }
        if (!StringUtils.isBlank(zsd.getSubdevicetype()) && IRemoteConstantDefine.DSC_ZONE_WARNING_TYPES_NOT_SAFE.contains(zsd.getSubdevicetype())) {
            return;
        }
        zsd.setWarningstatuses(Collections.EMPTY_LIST.toString());

        removeTimerTask(zsd);
    }

    private void removeTimerTask(ZWaveSubDevice zsd) {
        TimerTaskService tts = new TimerTaskService();
        TimerTask timerTask = tts.queryByTypeAndObjid(IRemoteConstantDefine.TASK_DSC_CHANNEL_ALARM_RESTORE, zsd.getZwavesubdeviceid());
        if (timerTask != null) {
            ScheduleManager.cancelJob(timerTask.getTimertaskid(), IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK);
            tts.delete(timerTask);
        }
    }

    @Override
    protected void changePartitionArmStatus(Partition partition) {
        if (partition.getArmstatus() != IRemoteConstantDefine.DSC_TO_ARM_STATUS_DIS_ARM) {
            partition.setArmstatus(IRemoteConstantDefine.DSC_TO_ARM_STATUS_DIS_ARM);
            pushMessage = true;
        }
    }

    @Override
    protected void pushMessage() {
        if (pushMessage && partitionid != 0) {
            JMSUtil.sendmessage(IRemoteConstantDefine.EVENT_DSC_PARTITION_ARM_STATUS,
                    new DSCEvent(partitionid, seriaNetReportBean.getDeviceid(), zwavedeviceid, status, new Date(), IRemoteConstantDefine.NOTIFICATION_DSC_ARM_STATUS_CHANGE));
        }
    }

    @Override
    protected void writeLog() {
        if (!pushMessage || partitionid == 0) {
            return;
        }
        PhoneUser phoneUser = getPhoneUser();

        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        Notification notification = new Notification();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(seriaNetReportBean.getDeviceid(), DeviceOperationType.setdscarm, dscpartitionid);
        String username = getDoorlockAssociationUsername(doss);

        if (username != null){
            notification.setAppendmessage(username);
        }

        if (dos != null && dos.getAppendmessage() != null && (dos.getStarttime().getTime() + 270 * 1000) > System.currentTimeMillis()) {
            JSONObject jsonObject = JSONObject.parseObject(dos.getAppendmessage());
            int toarmstatus = jsonObject.getIntValue("toarmstatus");
            int dscpartitionid0 = jsonObject.getIntValue("dscpartitionid");
            if (jsonObject != null && dscpartitionid0 == dscpartitionid && toarmstatus == IRemoteConstantDefine.DSC_TO_ARM_STATUS_DIS_ARM) {
                notification.setAppendmessage(jsonObject.getString("phonenumber"));
            }
        }
        notification.setFamilyid(phoneUser.getFamilyid());
        notification.setDeviceid(seriaNetReportBean.getDeviceid());
        notification.setReporttime(new Date());
        notification.setZwavedeviceid(zwaveDevice.getZwavedeviceid());
        notification.setNuid(zwaveDevice.getNuid());
        notification.setMajortype(zwaveDevice.getMajortype());
        notification.setMessage(IRemoteConstantDefine.MESSAGE_PARTITION_DIS_ARM);
        notification.setDevicetype(zwaveDevice.getDevicetype());
        notification.setPhoneuserid(phoneUser.getPhoneuserid());
        notification.setName(zwaveDevice.getName());
        JSONObject json = new JSONObject();
        json.put("dscpartitionid", dscpartitionid);
        json.put("partitionid", partitionid);
        for (int i = 0; zwaveDevice.getPartitions() != null && i < zwaveDevice.getPartitions().size(); i++) {
            if (zwaveDevice.getPartitions().get(i).getDscpartitionid() == dscpartitionid) {
                json.put("partitionname", zwaveDevice.getPartitions().get(i).getName());
                break;
            }
        }
        notification.setAppendjsonstring(json.toJSONString());
        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

    private String getDoorlockAssociationUsername(DeviceOperationStepsService doss) {
        List<DeviceOperationSteps> dosList = doss.queryDSCPartitionDisarmbyPartitionid(partitionid);
        for (DeviceOperationSteps dos : dosList) {
            if (!dos.isFinished() && dos.getExpiretime().getTime() > System.currentTimeMillis()){
                dos.setFinished(true);
                return dos.getAppendmessage();
            }
        }
        return null;
    }

    @Override
    protected void disAramed() {
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(seriaNetReportBean.getDeviceid(), DeviceOperationType.setdscarm, dscpartitionid);
        if (dos == null || dos.getExpiretime().getTime() < System.currentTimeMillis()) {
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(dos.getAppendmessage());
        if (jsonObject != null && jsonObject.getIntValue("dscpartitionid") == dscpartitionid && jsonObject.getIntValue("toarmstatus") == IRemoteConstantDefine.DSC_TO_ARM_STATUS_DIS_ARM) {
            dos.setFinished(true);
            dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_SUCCESS);
            doss.saveOrUpdate(dos);
        }
    }

    @Override
    protected void changePartitionStatus() {
        PartitionService ps = new PartitionService();
        Partition partition = ps.query(partitionid);
        ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
        List<ZWaveSubDevice> zWaveSubDeviceList = zsds.queryWarningSubDevices(zwaveDevice.getZwavedeviceid());
        boolean noWarningDevice = true;

        for (ZWaveSubDevice zWaveSubDevice : zWaveSubDeviceList) {
            if (IRemoteConstantDefine.DSC_ZONE_WARNING_TYPES_NOT_SAFE.contains(zWaveSubDevice.getSubdevicetype())) {
                if (DSCHelper.isWarning(zWaveSubDevice.getWarningstatuses())) {
                    noWarningDevice = false;
                }
                continue;
            }
            zWaveSubDevice.setWarningstatuses(null);
            pushAndLogZoneAlarmRestore(partition, zWaveSubDevice.getChannelid());
        }

        if (noWarningDevice) {
            partition.setWarningstatus(IRemoteConstantDefine.DSC_PARTITION_WARNING_STATUS_NORMAL);
        }
        partition.setStatus(status);
        ps.saveOrUpdate(partition);
    }

    private void pushAndLogZoneAlarmRestore(Partition partition, Integer zoneId) {
        DSCZoneAlarmRestoreProcessor zonealarmrestore = new DSCZoneAlarmRestoreProcessor();
        SeriaNetReportBean seriaNetReportBean = new SeriaNetReportBean();

        seriaNetReportBean.setDeviceid(partition.getZwavedevice().getDeviceid());
        zonealarmrestore.setReport(seriaNetReportBean);
        zonealarmrestore.status = IRemoteConstantDefine.DSC_PARTITION_WARNING_STATUS_NORMAL;
        zonealarmrestore.pushMessage = true;
        zonealarmrestore.zwavedeviceid = partition.getZwavedevice().getZwavedeviceid();
        zonealarmrestore.zoneid = zoneId;
        zonealarmrestore.zd = partition.getZwavedevice();
        zonealarmrestore.dscpartitionid = partition.getDscpartitionid();
        zonealarmrestore.partitionid = partition.getPartitionid();

        zonealarmrestore.pushMessage();
        zonealarmrestore.writeLog();
    }


    protected void restoreChannelEnableStatus(ZWaveSubDevice zsd) {
        if (zsd.getEnablestatus() != 0) {
            zsd.setEnablestatus(0);
//            pushBypassResult(zsd);
            writeBypassMessage(zsd);
        }
    }

    private void writeBypassMessage(ZWaveSubDevice zsd) {
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(seriaNetReportBean.getDeviceid(), DeviceOperationType.setdscarm, dscpartitionid);
        if (dos == null || dos.getExpiretime().getTime() < System.currentTimeMillis()) {
            return;
        }
        JSONObject json = new JSONObject();
        json.put("channelid", zsd.getChannelid());
        json.put("channelname", zsd.getName());
        Notification notification = new Notification();
        String jsonmsg = dos.getAppendmessage();
        if (jsonmsg != null) {
            String phonenumber = JSONObject.parseObject(jsonmsg).getString("phonenumber");
            notification.setAppendmessage(phonenumber);
        }

        notification.setMessage(IRemoteConstantDefine.WARNING_TYPE_DEVICE_ENABLE);
        notification.setDeviceid(zsd.getZwavedevice().getDeviceid());
        notification.setReporttime(new Date());
        notification.setZwavedeviceid(zsd.getZwavedevice().getZwavedeviceid());
        notification.setNuid(zsd.getZwavedevice().getNuid());
        notification.setMajortype(zsd.getZwavedevice().getMajortype());
        notification.setDevicetype(zsd.getZwavedevice().getDevicetype());
        notification.setPhoneuserid(getPhoneUser().getPhoneuserid());
        notification.setName(zsd.getZwavedevice().getName());
        notification.setAppendjson(json);
        notification.setWarningstatus(status);

        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

    private void pushBypassResult(ZWaveSubDevice zsd) {
        SubdeviceStatusEvent sse = new SubdeviceStatusEvent();
        sse.setChannelid(zsd.getChannelid());
        sse.setDeviceid(zsd.getZwavedevice().getDeviceid());
        sse.setEnablestatus(0);
        sse.setEventtime(new Date());
        sse.setSubdeviceid(zsd.getZwavesubdeviceid());
        sse.setType(IRemoteConstantDefine.NOTIFICATION_SUB_DEVICE_STATUS);
        sse.setZwavedeviceid(zsd.getZwavedevice().getZwavedeviceid());

        JMSUtil.sendmessage(IRemoteConstantDefine.EVNET_PUSH_SUB_ZWAVEDEVICE_STATUS, sse);
    }

    @Override
    protected void init() {
        status = IRemoteConstantDefine.DSC_TO_ARM_STATUS_DIS_ARM;
    }
}
