package com.iremote.task.timertask.processor;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.Partition;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DeviceCapabilityService;
import com.iremote.service.ZWaveDeviceService;

import java.util.ArrayList;
import java.util.List;

public class CheckDoorSensorHelper {
    protected Partition partition;
    protected List<ZWaveDevice> notreadyappliance ;

    public CheckDoorSensorHelper(Partition partition) {
        this.partition = partition;
    }

    public boolean checkDoorSensor()
    {
        if (partition == null ){
            return false;
        }
        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zwdl = zds.querybypartitionid(partition.getPartitionid());
        notreadyappliance = new ArrayList<>();

        for ( ZWaveDevice zd : zwdl)
        {
            if ( zd.getEnablestatus() == IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE)
                continue;
            if ( !IRemoteConstantDefine.DEVICE_TYPE_DOOR_SENSOR.equals(zd.getDevicetype())
                    && !IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zd.getDevicetype()))
                continue;
            if ( IRemoteConstantDefine.DEVICE_TYPE_DOOR_SENSOR.equals(zd.getDevicetype())
                    && zd.getStatus() != null
                    && zd.getStatus() != IRemoteConstantDefine.DEVICE_STATUS_DOOR_SENSOR_CLOSE )
                notreadyappliance.add(zd);
            if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zd.getDevicetype())
                    && zd.getStatus() != null
                    && zd.getStatus() != IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_CLOSE )
                notreadyappliance.add(zd);
        }

        if ( notreadyappliance.size() == 0 )
            return true ;

        return false;
    }

    public boolean checkDoorSensor(int armstatus)
    {
        if (partition == null ){
            return false;
        }
        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zwdl = zds.querybypartitionid(partition.getPartitionid());
        notreadyappliance = new ArrayList<>();
        DeviceCapabilityService dcs = new DeviceCapabilityService();
        for ( ZWaveDevice zd : zwdl)
        {
            if ( zd.getEnablestatus() == IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE)
                continue;

            if ( !IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_SENSOR.contains(zd.getDevicetype())
                    && !IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zd.getDevicetype()))
                continue;

            if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_SENSOR.contains(zd.getDevicetype())
                    && zd.getStatus() != null
                    && zd.getStatus() != IRemoteConstantDefine.DEVICE_STATUS_DOOR_SENSOR_CLOSE ){
                DeviceCapability deviceCapability = dcs.query(zd, IRemoteConstantDefine.DEVICE_CAPABILITY_ALARM_WHEN_HOME_ARMED);
                if (deviceCapability != null && armstatus == IRemoteConstantDefine.PARTITION_STATUS_IN_HOME){
                    continue;
                }
                notreadyappliance.add(zd);
            }

            if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zd.getDevicetype())
                    && zd.getStatus() != null
                    && zd.getStatus() != IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_CLOSE ){
                DeviceCapability deviceCapability = dcs.query(zd, IRemoteConstantDefine.DEVICE_CAPABILITY_ALARM_WHEN_HOME_ARMED);
                if (deviceCapability != null && armstatus == IRemoteConstantDefine.PARTITION_STATUS_IN_HOME){
                    continue;
                }
                notreadyappliance.add(zd);
            }
        }

        if ( notreadyappliance.size() == 0 )
            return true ;

        return false;
    }

    public List<ZWaveDevice> getNotreadyappliance() {
        return notreadyappliance;
    }

    public void clear() {
        this.notreadyappliance = null;
    }
}
