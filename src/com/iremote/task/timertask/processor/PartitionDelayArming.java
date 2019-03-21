package com.iremote.task.timertask.processor;

import com.iremote.action.partition.SetPartitionArmStatus;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.DSCEvent;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.*;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ZWaveDeviceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PartitionDelayArming {
    private Partition partition;
    private int armstatus;
    private String password;
    private List<ZWaveDevice> notreadyappliance;
    private List<PhoneUser> familyuserlist = new ArrayList<>();
    private List<Integer> familyuseridlist = new ArrayList<>();
    private PhoneUser phoneuser;
    private ZWaveDevice partitionsfirstzwavedevice;

    public void start() {
        getPhoneUser();
        initFamilyUser();
        init();

        if (isDSCPartition()) {
            dscArm();
        } else {
            if (checkDoorSensor()) {
                if (isPartitionHasSetDelay()) {
                    createDelayTask();
                } else {
                    setPartitionArmStatus();
                    pushArmMsg();
                }
            } else {
                pushArmFailMsg();
            }
        }
    }

    private void pushArmFailMsg() {
        PushHelper.pushArmFailedMessage(phoneuser, notreadyappliance);
    }

    private void pushArmMsg() {
        JMSUtil.sendmessage(IRemoteConstantDefine.EVENT_DSC_PARTITION_ARM_STATUS,
                new DSCEvent(partition.getPartitionid(), partitionsfirstzwavedevice.getDeviceid(), partitionsfirstzwavedevice.getZwavedeviceid(), armstatus, new Date(), IRemoteConstantDefine.NOTIFICATION_DSC_ARM_STATUS_CHANGE));
    }

    private void createDelayTask() {
        TimerTask timerTask = new TimerTask();
        timerTask.setDeviceid(partitionsfirstzwavedevice.getDeviceid());
        timerTask.setType(IRemoteConstantDefine.TASK_ZWAVE_PARTITION_DELAY_ARM);
        long date = System.currentTimeMillis() + partition.getDelay() * 1000;
        timerTask.setExcutetime(new Date(date));
        timerTask.setExpiretime(new Date(date + IRemoteConstantDefine.TIMER_TASK_EXPIRE_TIME));
        timerTask.setCreatetime(new Date());
        timerTask.setObjid(partition.getPartitionid());

        ScheduleManager.excuteWithSaveInDB(IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK, timerTask);
    }

    private void init() {

        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> querybypartitionid = zds.querybypartitionid(partition.getPartitionid());
        if (querybypartitionid != null && querybypartitionid.size() != 0) {
            partitionsfirstzwavedevice = querybypartitionid.get(0);
        }
    }

    private void setPartitionArmStatus() {
        partition.setArmstatus(IRemoteConstantDefine.PARTITION_STATUS_IN_HOME);
    }

    private boolean isPartitionHasSetDelay() {
        return (partition.getDelay() != null && partition.getDelay() != 0);
    }

    private void dscArm() {

        SetPartitionArmStatus spas = new SetPartitionArmStatus();
        spas.setArmstatus(armstatus);
        spas.setPartitionid(partition.getPartitionid());
        spas.setPassword(password);
        spas.setPhoneuser(phoneuser);
        spas.execute();
    }

    private void getPhoneUser() {
        /*RemoteService rs = new RemoteService();
        Integer phoneuserid = rs.queryOwnerId(partition.getZwavedevice().getDeviceid());
        PhoneUserService ps = new PhoneUserService();
        phoneuser = ps.query();*/
        phoneuser = partition.getPhoneuser();
    }

    private boolean isDSCPartition() {
        return partition.getZwavedevice() != null;
    }

    private void initFamilyUser() {
        if (phoneuser.getFamilyid() != null) {
            PhoneUserService phoneuserservice = new PhoneUserService();
            familyuserlist = phoneuserservice.querybyfamiliyid(phoneuser.getFamilyid());
        } else
            familyuserlist.add(phoneuser);

        for (PhoneUser pu : familyuserlist)
            familyuseridlist.add(pu.getPhoneuserid());
    }

    private boolean checkDoorSensor() {
        List<Integer> lst = new ArrayList<>(familyuserlist.size());

        for (PhoneUser u : familyuserlist)
            lst.add(u.getPhoneuserid());

        IremotepasswordService rs = new IremotepasswordService();
        List<Remote> rlst = rs.querybyPhoneUserid(lst);

        List<String> didl = new ArrayList<>(rlst.size());
        for (Remote r : rlst)
            didl.add(r.getDeviceid());

        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zwdl = zds.querybydeviceid(didl);

        notreadyappliance = new ArrayList<>();

        for (ZWaveDevice zd : zwdl) {
            if (zd.getEnablestatus() == IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE)
                continue;
            if (!IRemoteConstantDefine.DEVICE_TYPE_DOOR_SENSOR.equals(zd.getDevicetype())
                    && !IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zd.getDevicetype()))
                continue;
            if (IRemoteConstantDefine.DEVICE_TYPE_DOOR_SENSOR.equals(zd.getDevicetype())
                    && zd.getStatus() != null
                    && zd.getStatus() != IRemoteConstantDefine.DEVICE_STATUS_DOOR_SENSOR_CLOSE)
                notreadyappliance.add(zd);
            if (IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zd.getDevicetype())
                    && zd.getStatus() != null
                    && zd.getStatus() != IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_CLOSE)
                notreadyappliance.add(zd);
        }
        if (notreadyappliance.size() == 0)
            return true;

        return false;

    }
}
