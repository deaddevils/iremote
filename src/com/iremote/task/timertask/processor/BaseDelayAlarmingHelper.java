package com.iremote.task.timertask.processor;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Family;
import com.iremote.domain.Partition;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.*;

import java.util.List;

public abstract class BaseDelayAlarmingHelper {
    protected Partition partition;
    protected PhoneUser phoneUser;
    protected Integer deviceDelayTime;
    protected Integer phoneuserid;
    protected boolean hasPhoneuser = false;
    protected int armStatus;

    protected void init(String deviceid){
        setPhoneUser(deviceid);
    }

    public boolean hasArmed() {
        if (!hasPhoneuser) {
            return false;
        }

        if (!deviceHasPartition()) {
            if (hasPartition()) {
                return false;
            } else {
                if (!isPhoneuserArmed()) {
                    return false;
                }
            }
        } else {
            if (!isParitionArmed()) {
                return false;
            }
        }
        return true;
    }

    protected abstract boolean deviceHasPartition();

    protected void setPhoneUser(String deviceid){
        RemoteService remoteService = new RemoteService();
        phoneuserid = remoteService.queryOwnerId(deviceid);
        if (phoneuserid == null) {
            return;
        }
        PhoneUserService pus = new PhoneUserService();
        phoneUser = pus.query(phoneuserid);

        if (phoneUser != null) {
            hasPhoneuser = true;
        }
    }

    public boolean hasPartition(){
        ZWaveDeviceService zds = new ZWaveDeviceService();
        PartitionService ps = new PartitionService();
        RemoteService rs = new RemoteService();
        PhoneUserService pus = new PhoneUserService();

        if (userHasPartition(phoneUser.getPhoneuserid(), rs, zds, ps)) {
            return true;
        }
        if (phoneUser.getFamilyid() == null || phoneUser.getFamilyid() == 0) {
            return false;
        }
        List<PhoneUser> userList = pus.querybyfamiliyid(phoneUser.getFamilyid());
        for (PhoneUser user : userList) {
            if (user.getPhoneuserid() != phoneUser.getPhoneuserid()
                    && userHasPartition(user.getPhoneuserid(), rs, zds, ps)) {
                return true;
            }
        }
        return false;
    }

    private boolean userHasPartition(Integer phoneuserid, RemoteService rs, ZWaveDeviceService zds, PartitionService ps) {
        List<String> list = rs.queryDeviceidByPhoneuserid(phoneuserid);
        if (list == null || list.size() == 0) {
            return false;
        }
        List<ZWaveDevice> zWaveDevices = zds.querybydeviceidandtype(list, IRemoteConstantDefine.DEVICE_TYPE_DSC);
        for (ZWaveDevice zWaveDevice : zWaveDevices) {
            if (zWaveDevice.getPartitions().size() != 0) {
                return true;
            }
        }
        List<Partition> partitions = ps.queryParitionsByPhoneuserid(phoneuserid);
        return partitions != null && partitions.size() != 0;
    }

    protected boolean isOutHomeArm() {
        if (hasArmed()) {
            if (partition == null) {
                if (phoneUser.getFamilyid() != null) {
                    FamilyService fs = new FamilyService();
                    Family family = fs.query(phoneUser.getFamilyid());
                    if (family != null) {
                        return family.getArmstatus() == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM;
                    }
                }
                return phoneUser.getArmstatus() == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM;
            }
            return partition.getArmstatus() == IRemoteConstantDefine.PARTITION_STATUS_OUT_HOME;
        }
        return false;
    }

    private boolean isPhoneuserArmed() {
        if (phoneUser == null) {
            return false;
        }

        if (phoneUser.getFamilyid() != null) {
            FamilyService fs = new FamilyService();
            Family family = fs.query(phoneUser.getFamilyid());
            if (family != null) {
                armStatus = family.getArmstatus();
                return family.getArmstatus() != IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM;
            }
        }

        armStatus = phoneUser.getArmstatus();
        if (phoneUser.getArmstatus() != IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM
                && phoneUser.getArmstatus() != IRemoteConstantDefine.PHONEUSER_ARM_STATUS_SLEEPING_ARM) {
            return false;
        }
        return true;
    }

    private boolean isParitionArmed() {
        return this.partition.getArmstatus() != IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM
                && this.partition.getArmstatus() != IRemoteConstantDefine.PARTITION_ARM_STATUS_EXIT_DELAY_IN_PROGRESS
                && this.partition.getArmstatus() != IRemoteConstantDefine.PARTITION_ARM_STATUS_ENTRY_DELAY_IN_PROGRESS;
    }

    protected void setPartitionArmStatus(Integer partitionid) {
        PartitionService ps = new PartitionService();
        partition = ps.query(partitionid);
        if (partition != null) {
            armStatus = partition.getArmstatus();
        }
    }

    public Integer getDeviceDelayTime() {
        return deviceDelayTime;
    }

    public int getArmStatus() {
        return armStatus;
    }
}
