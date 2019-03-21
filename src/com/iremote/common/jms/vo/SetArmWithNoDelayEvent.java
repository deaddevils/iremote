package com.iremote.common.jms.vo;

import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.Partition;
import com.iremote.service.DeviceOperationStepsService;

public class SetArmWithNoDelayEvent {
    protected String password;
    protected String deviceid;
    protected DeviceOperationSteps dos;
    protected Partition partition;

    public SetArmWithNoDelayEvent(String password, Partition partition, DeviceOperationSteps dos) {
        this.password = password;
        this.partition = partition;
        this.deviceid = partition.getZwavedevice().getDeviceid();
        this.dos = dos;
    }

    public SetArmWithNoDelayEvent() {
    }

    public String getPassword() {
        return password;
    }

    public Partition getPartition() {
        return partition;
    }

    public void setPartition(Partition partition) {
        this.partition = partition;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public DeviceOperationSteps getDos() {
        return dos;
    }

    public void setDos(DeviceOperationSteps dos) {
        this.dos = dos;
    }
}
