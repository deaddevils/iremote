package com.iremote.infraredtrans.serianet.dsc;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PushEventToThirdpartHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.DSCEvent;
import com.iremote.domain.*;
import com.iremote.service.*;
import com.iremote.task.timertask.processor.CheckDoorSensorHelper;
import com.iremote.task.timertask.processor.PushHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DSCPartitionArmedProcessor extends DSCReportBaseProcessor {
    boolean pushMessage = false;
    protected int dscpartitionid;
    private static Log log = LogFactory.getLog(DSCPartitionArmedProcessor.class);
    protected ZWaveDevice zd;
    private Integer operatorPhoneuserid;

    @Override
    protected void updateDeviceStatus() {
        dscpartitionid = getValue(seriaNetReportBean.getCmd(), 3, 4);
        int mode = getValue(seriaNetReportBean.getCmd(), 4, 5);
        if (mode == 0 || mode == 2) {
            status = 1;
        } else {
            status = 3;
        }

        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zdList = zds.querybydeviceidtype(seriaNetReportBean.getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_DSC);
        if (zdList == null || zdList.size() == 0) {
            return;
        }
        zd = zdList.get(0);

        changeTaskStatus();

        checkDoorSensor(zd);

        partitionid = setArmStatus(dscpartitionid, zdList);
        zwavedeviceid = zdList.get(0).getZwavedeviceid();

//        DSCHelper.synArmStatus(seriaNetReportBean.getDeviceid(), dscpartitionid, status);
    }

    private void changeTaskStatus() {
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(zd.getDeviceid(), DeviceOperationType.setdscarm, dscpartitionid);
        JSONObject jsonObject = JSONObject.parseObject(dos.getAppendmessage());
        if (jsonObject != null) {
            operatorPhoneuserid = jsonObject.getInteger("phoneuserid");
        }
        if (dos != null && (dos.getExpiretime().getTime() > System.currentTimeMillis())) {
            if (jsonObject != null) {
                int toarmstatus = jsonObject.getIntValue("toarmstatus");
                if (IRemoteConstantDefine.DSC_TO_ARM_STATUS_ARM == toarmstatus && jsonObject.getIntValue("dscpartitionid") == dscpartitionid) {
                    dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_SUCCESS);
                    dos.setFinished(true);
                    doss.saveOrUpdate(dos);
                }
            }
        }
    }

    @Override
    protected void pushMessage() {
        if (pushMessage && partitionid != 0) {
            RemoteService remoteService = new RemoteService();
            Integer phoneuserid = remoteService.queryOwnerId(seriaNetReportBean.getDeviceid());
            PhoneUserService pus = new PhoneUserService();
            PhoneUser phoneUser = pus.query(phoneuserid);
            /*if (phoneUser != null) {
                JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_DSC_PARTITION_ARM_STATUS,
                        new DSCEvent(partitionid, phoneUser.getPhoneuserid(), status, new Date(), IRemoteConstantDefine.NOTIFICATION_DSC_ARM_STATUS_CHANGE, phoneUser.getPlatform()));
            } else {*/
                JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_DSC_PARTITION_ARM_STATUS,
                        new DSCEvent(partitionid, seriaNetReportBean.getDeviceid(), zwavedeviceid, status, new Date(), IRemoteConstantDefine.NOTIFICATION_DSC_ARM_STATUS_CHANGE));
            /*}*/
        }
    }

    @Override
    protected void writeLog() {
        if (!pushMessage || partitionid == 0) {
            return;
        }
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(seriaNetReportBean.getDeviceid(), DeviceOperationType.setdscarm, dscpartitionid);
        Notification notification = new Notification();
        boolean needPushUsercode = true;

        if (dos != null && dos.getAppendmessage() != null && (dos.getStarttime().getTime() + 270 * 1000) > System.currentTimeMillis()) {
            JSONObject jsonObject = JSONObject.parseObject(dos.getAppendmessage());
            int toarmstatus = jsonObject.getIntValue("toarmstatus");
            int dscpartitionid0 = jsonObject.getIntValue("dscpartitionid");
            if (jsonObject != null && dscpartitionid0 == dscpartitionid && toarmstatus == IRemoteConstantDefine.DSC_TO_ARM_STATUS_ARM) {
                notification.setAppendmessage(jsonObject.getString("phonenumber"));

                boolean isPushed = jsonObject.getBooleanValue("isPushed");
                boolean hasPassword = jsonObject.containsKey("password");
                needPushUsercode = isPushed || !hasPassword || dos.isFinished();
                if (!isPushed) {
                    jsonObject.put("isPushed", true);
                    dos.setAppendmessage(jsonObject.toJSONString());
                }
            }
        }

        String msg;
        PhoneUser phoneUser = getPhoneUser();

        if (status == 1) {
            msg = IRemoteConstantDefine.MESSAGE_PARTITION_ARM;
        } else {
            msg = IRemoteConstantDefine.MESSAGE_PARTITION_HOME_ARM;
        }
        notification.setFamilyid(phoneUser.getFamilyid());
        notification.setDeviceid(seriaNetReportBean.getDeviceid());
        notification.setReporttime(new Date());
        notification.setZwavedeviceid(zd.getZwavedeviceid());
        notification.setNuid(zd.getNuid());
        notification.setMajortype(zd.getMajortype());
        notification.setMessage(msg);
        notification.setPhoneuserid(phoneUser.getPhoneuserid());
        notification.setName(zd.getName());
        notification.setDevicetype(zd.getDevicetype());
        notification.setWarningstatus(status);
        JSONObject json = new JSONObject();
        json.put("dscpartitionid", dscpartitionid);
        json.put("partitionid", partitionid);
        for (int i = 0; zd.getPartitions() != null && i < zd.getPartitions().size(); i++) {
            if (zd.getPartitions().get(i).getDscpartitionid() == dscpartitionid) {
                json.put("partitionname", zd.getPartitions().get(i).getName());
                break;
            }
        }
        notification.setAppendjsonstring(json.toJSONString());
        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);

        if (needPushUsercode){
            PushEventToThirdpartHelper.pushEventToThirdpart(seriaNetReportBean.getDeviceid(),
                    zwavedeviceid, partitionid, dscpartitionid, status, IRemoteConstantDefine.DEFAULT_USER_CODE, IRemoteConstantDefine.MESSAGE_PARTITION_ARM_USER_CODE);
        }

    }

    @Override
    protected void init() {

    }

    private int setArmStatus(int dscpartitionid, List<ZWaveDevice> zdList) {
        List<Partition> partitions = zdList.get(0).getPartitions();
        boolean hasDscPartition = false;
        for (int i = 0; partitions != null && i < partitions.size(); i++) {
            if (partitions.get(i).getDscpartitionid() == dscpartitionid) {
                hasDscPartition = true;
                if (partitions.get(i).getArmstatus() != status) {
                    partitions.get(i).setArmstatus(status);
                    pushMessage = true;
                    return partitions.get(i).getPartitionid();
                }
            }
        }
        if (!hasDscPartition) {
            pushMessage = true;
            partitionid = addPartition(dscpartitionid, zd, status, 0);
        }
        return partitionid;
    }

    protected void checkDoorSensor(ZWaveDevice zWaveDevice) {
        Partition partition = zWaveDevice.getPartitions().stream().filter(p -> p.getDscpartitionid() == dscpartitionid).findFirst().orElse(null);
        if (partition == null) {
            return;
        }
        ArrayList<ZWaveDevice> notreadyappliance = new ArrayList<>();
  
        CheckDoorSensorHelper cdsh = new CheckDoorSensorHelper(partition);
        if (!cdsh.checkDoorSensor(status)) {
            PhoneUser pu;
            if (operatorPhoneuserid == null) {
                pu = getPhoneUser();
            } else {
                PhoneUserService pus = new PhoneUserService();
                pu = pus.query(operatorPhoneuserid);
            }
            PushHelper.pushArmFailedMessage(pu, notreadyappliance);
        }
    }
}
