package com.iremote.infraredtrans.serianet.dsc;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.Partition;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.task.timertask.processor.PushHelper;

import java.util.List;

public class DSCInvalidAccessCodeProcessor extends DSCExitDelayProcessor{
    boolean hasAssociation = false;
    String username;
    String partitionName;

    @Override
    protected void init() {
        status = IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PASSWORD_ERROR;
    }

    @Override
    protected void changeTaskStatus(int dscpartitionid, DeviceOperationSteps dos, DeviceOperationStepsService doss) {
        JSONObject jsonObject = JSONObject.parseObject(dos.getAppendmessage());
        if (jsonObject!=null && jsonObject.getIntValue("dscpartitionid") == dscpartitionid){
            dos.setFinished(true);
            dos.setStatus(status);
            doss.saveOrUpdate(dos);
        }
    }

    private String getDoorlockAssociationUsername() {
        List<DeviceOperationSteps> dosList = new DeviceOperationStepsService().queryDSCPartitionDisarmbyPartitionid(partitionid);
        for (DeviceOperationSteps dos : dosList) {
            if (!dos.isFinished() && dos.getExpiretime().getTime() > System.currentTimeMillis()){
                dos.setFinished(true);
                hasAssociation = true;
                return dos.getAppendmessage();
            }
        }
        return null;
    }

    @Override
    protected void changePartitionArmStatus(int dscpartitionid) {
        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zlist = zds.querybydeviceidtype(seriaNetReportBean.getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_DSC);
        if (zlist == null || zlist.size() == 0) {
            return;
        }
        ZWaveDevice zWaveDevice = zlist.get(0);
        List<Partition> partitions = zWaveDevice.getPartitions();
        if (partitions == null) {
            return;
        }
        for (Partition partition : partitions) {
            if (partition.getDscpartitionid() == dscpartitionid) {
                partitionid = partition.getPartitionid();
                partitionName = partition.getName();
                break;
            }
        }

        username = getDoorlockAssociationUsername();
    }

    @Override
    protected void pushMessage() {
        pushDoorLockAssociationNotification();

    }

    private void pushDoorLockAssociationNotification() {
        if (hasAssociation) {
            if (partitionid != 0) {
                PushHelper.pushNotificationbyType(getPhoneUser(), partitionName, username, IRemoteConstantDefine.NOTIFICATION_TYPE_PARTITION_PASSWORD_WRONG);
            }
        }
    }
}
