package com.iremote.infraredtrans.serianet.dsc;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.*;
import com.iremote.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.List;

public class DSCPartitionInAlarmProcessor extends DSCReportBaseProcessor {
    protected ZWaveDevice zwaveDevice;
    boolean pushMessage = false;
    protected int dscpartitionid;
    private static Log log = LogFactory.getLog(DSCPartitionArmedProcessor.class);

    @Override
    protected void updateDeviceStatus() {
        log.info("zzzdisarm:");
        dscpartitionid = getDSCPartitionid(seriaNetReportBean.getCmd());
        if (dscpartitionid == 0) {
            return;
        }
        ZWaveDeviceService zds = new ZWaveDeviceService();
        zwaveDevice = getZwaveDevice(zds);
        List<Partition> partitionList = getPartitionList(zwaveDevice);
        boolean hasPartitionid = false;
        for (int i = 0; i < partitionList.size(); i++) {
            Partition partition = partitionList.get(i);
            if (dscpartitionid == partition.getDscpartitionid()) {
                hasPartitionid = true;
                changePartitionArmStatus(partition);
                super.partitionid = partition.getPartitionid();
                break;
            }
        }
        if (!hasPartitionid) {
            super.partitionid = addPartition(dscpartitionid, zwaveDevice, 0, 0);
        }
        super.zwavedeviceid = zwaveDevice.getZwavedeviceid();
        disAramed();
        changePartitionStatus();
        changeTaskStatus();
    }

    protected void changeTaskStatus() {

    }

    protected void changePartitionArmStatus(Partition partition) {
    }

    protected void changePartitionStatus() {
        PartitionService ps = new PartitionService();
        Partition query = ps.query(partitionid);
        query.setWarningstatus(status);
        ps.saveOrUpdate(query);
    }

    @Override
    protected void pushMessage() {
    }

    @Override
    protected void writeLog() {

    }

    @Override
    protected void init() {
        status = IRemoteConstantDefine.DSC_PARTITION_WARNING_STATUS_ARM;
    }

    private List<Partition> getPartitionList(ZWaveDevice zwaveDevice) {
        List<Partition> partitions = zwaveDevice.getPartitions();
        if (partitions == null || partitions.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        return partitions;
    }

    private ZWaveDevice getZwaveDevice(ZWaveDeviceService zds) {
        List<ZWaveDevice> lst = zds.querybydeviceidtype(seriaNetReportBean.getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_DSC);
        if (lst == null || lst.size() == 0) {
            return null;
        }
        return lst.get(0);
    }

    protected void disAramed() {

    }
}
