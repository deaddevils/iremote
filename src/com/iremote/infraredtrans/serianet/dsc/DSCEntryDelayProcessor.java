package com.iremote.infraredtrans.serianet.dsc;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Partition;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class DSCEntryDelayProcessor extends  DSCExitDelayProcessor{
    private static Log log = LogFactory.getLog(DSCEntryDelayProcessor.class);

    @Override
    protected void updateDeviceStatus() {
        super.updateDeviceStatus();
    }

    @Override
    protected void pushMessage() {
        super.pushMessage();
    }

    @Override
    protected void writeLog() {
    }

    @Override
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
                if (partition.getArmstatus() != IRemoteConstantDefine.PARTITION_STATUS_OUT_HOME
                        && partition.getArmstatus() != IRemoteConstantDefine.PARTITION_STATUS_IN_HOME
                        &&partition.getArmstatus() != armstatus) {
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

    @Override
    protected void init() {
        armstatus = 5;
        status = IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_SUCCESS;
    }

}