package com.iremote.infraredtrans.serianet.dsc;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.DSCEvent;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.Partition;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.ZWaveDeviceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

public class DSCExitDelayProcessor extends DSCReportBaseProcessor {
    private static Log log = LogFactory.getLog(DSCExitDelayProcessor.class);
    protected int status;
    protected int armstatus;
    protected boolean pushMessage = false;

    @Override
    protected void updateDeviceStatus() {
        int dscpartitionid = getValue(seriaNetReportBean.getCmd(), 3, 4);
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(seriaNetReportBean.getDeviceid(), DeviceOperationType.setdscarm, dscpartitionid);
        if (dos != null && dos.getExpiretime().getTime() > System.currentTimeMillis()) {
            changeTaskStatus(dscpartitionid, dos, doss);
        }
        changePartitionArmStatus(dscpartitionid);
    }

    protected void changePartitionArmStatus(int dscpartitionid) {
        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zlist = zds.querybydeviceidtype(seriaNetReportBean.getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_DSC);
        if (zlist == null || zlist.size() == 0) {
            return;
        }
        ZWaveDevice zWaveDevice = zlist.get(0);
        zwavedeviceid = zWaveDevice.getZwavedeviceid();
        boolean hasPartition = false;
        for (int i = 0; zWaveDevice.getPartitions() != null && i < zWaveDevice.getPartitions().size(); i++) {
            Partition partition = zWaveDevice.getPartitions().get(i);
            if (partition.getDscpartitionid() == dscpartitionid) {
                hasPartition = true;
                if (partition.getArmstatus() != armstatus) {
                    partition.setArmstatus(armstatus);
                    partitionid = partition.getPartitionid();
                    pushMessage = true;
                }
                break;
            }
        }
        if (!hasPartition) {
            partitionid = addPartition(dscpartitionid, zWaveDevice, armstatus, 0);
        }
    }

    protected void changeTaskStatus(int dscpartitionid, DeviceOperationSteps dos, DeviceOperationStepsService doss) {
        JSONObject jsonObject = JSONObject.parseObject(dos.getAppendmessage());
        if (jsonObject!=null && jsonObject.getIntValue("dscpartitionid") == dscpartitionid && jsonObject.getIntValue("toarmstatus") == IRemoteConstantDefine.DSC_TO_ARM_STATUS_ARM ){
            dos.setFinished(true);
            dos.setStatus(status);
            doss.saveOrUpdate(dos);
        }
    }

    @Override
    protected void pushMessage() {
        if (pushMessage && partitionid != 0) {
            JMSUtil.sendmessage(IRemoteConstantDefine.EVENT_DSC_PARTITION_ARM_STATUS,
                    new DSCEvent(partitionid, seriaNetReportBean.getDeviceid(), zwavedeviceid, armstatus, new Date(), IRemoteConstantDefine.NOTIFICATION_DSC_ARM_STATUS_CHANGE));
        }
    }

    @Override
    protected void writeLog() {
    }

    @Override
    protected void init() {
        armstatus = 4;
        status = IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_SUCCESS;
    }

}
