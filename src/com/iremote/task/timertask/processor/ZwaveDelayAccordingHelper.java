package com.iremote.task.timertask.processor;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.helper.PushEventToThirdpartHelper;
import com.iremote.action.partition.SetPartitionArmStatus;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.encrypt.AES;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.DSCEvent;
import com.iremote.common.md5.MD5Util;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.*;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.scene.SceneExecutor;
import com.iremote.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

public class ZwaveDelayAccordingHelper extends BaseDelayAlarmingHelper{
    private Log log = LogFactory.getLog(ZwaveDelayAccordingHelper.class);
    protected ZWaveDevice zWaveDevice;
    protected String username;

    public ZwaveDelayAccordingHelper(ZWaveDevice zWaveDevice) {
        this.zWaveDevice = zWaveDevice;
        super.init(zWaveDevice.getDeviceid());
    }

    public boolean hasArmed() {
        return zWaveDevice != null && super.hasArmed();
    }

    public boolean hasArmedByUserSetting() {
        if (!hasPhoneuser || !PhoneUserHelper.hasArmFunction(phoneUser)) {
            return false;
        }

        return hasCapabilityAlarmWhenHomeArmed() ? isOutHomeArm() : hasArmed();
    }

    public boolean hasSetDelayAlarm() {
        if (!hasPhoneuser) {
            return false;
        }

        DeviceCapabilityService dsc = new DeviceCapabilityService();
        DeviceCapability dc = dsc.query(zWaveDevice, IRemoteConstantDefine.DEVICE_CAPABILITY_DELAY_TIME_CAPABILITCODE);
        if (dc == null || Integer.valueOf(dc.getCapabilityvalue()) == 0) {
            return false;
        }
        deviceDelayTime = Integer.valueOf(dc.getCapabilityvalue());
        return true;
    }

    public void executeDoorlockAssociation(int zwavedeviceid, int usercode, String deviceName) {
        if (!hasPhoneuser) {
            return;
        }

        DoorlockAssociationService das = new DoorlockAssociationService();
        List<DoorlockAssociation> doorlockAssociations = das.queryByZwavedeviceidAndUsercode(zwavedeviceid, usercode);
        if (doorlockAssociations == null) {
            return;
        }
        for (DoorlockAssociation da : doorlockAssociations) {
            if (da.getObjtype() == IRemoteConstantDefine.DOOR_LOCK_ASSOCIATION_OBJ_TYPE_EXECUTE_SCENE) {
                executeScene(da.getObjid());
            } else if (da.getObjtype() == IRemoteConstantDefine.DOOR_LOCK_ASSOCIATION_OBJ_TYPE_DISARM_PARTITION) {
                disarmPartition(da.getObjid(), AES.decrypt2Str(da.getAppendmessage()), usercode);
            } else if (da.getObjtype() == IRemoteConstantDefine.DOOR_LOCK_ASSOCIATION_OBJ_TYPE_IN_HOME_ARM_PARTITION) {
                armPartition(da.getObjid(), IRemoteConstantDefine.PARTITION_STATUS_IN_HOME, usercode, deviceName);
            } else if (da.getObjtype() == IRemoteConstantDefine.DOOR_LOCK_ASSOCIATION_OBJ_TYPE_OUT_HOME_ARM_PARTITION) {
                armPartition(da.getObjid(), IRemoteConstantDefine.PARTITION_STATUS_OUT_HOME, usercode, deviceName);
            }
        }
    }

    private void armPartition(Integer partitionid, int armstatus, int usercode, String deviceName) {
        if (usercode != 0) {
            DoorlockUserService dlus = new DoorlockUserService();
            DoorlockUser doorlockUser = dlus.querybyZwavedeviceidAndUsercode(zWaveDevice.getZwavedeviceid(), usercode);
            if (doorlockUser != null) {
                username = doorlockUser.getUsername();
            }
        }

        PartitionService ps = new PartitionService();
        Partition p = ps.query(partitionid);

        if (p == null) {
            return;
        }
        if (p.getArmstatus() == armstatus) {
            log.info("partition already in this arm status "+armstatus);
        }

        SetPartitionArmStatus spas = new SetPartitionArmStatus();
        spas.setPartitionid(partitionid);
        spas.setPhoneuser(phoneUser);
        spas.setArmstatus(armstatus);
        spas.setOperator(username);
        spas.setSetDelay(true);
        spas.execute();
        int resultCode = spas.getResultCode();
        if (spas.getResultCode() == 0) {
            return;
        } else {
            PushHelper.pushNotificationbyType(phoneUser, p.getName(), deviceName, IRemoteConstantDefine.NOTIFICATION_TYPE_ASSOCIATE_PARTITION_ARM_FAIED);
            log.info("Partitionid:"+ partitionid+ " arm failed, resultCode is "+ resultCode);
        }
    }

    private boolean hasCapabilityAlarmWhenHomeArmed() {
        DeviceCapabilityService dcs = new DeviceCapabilityService();
        DeviceCapability dc = dcs.query(zWaveDevice, IRemoteConstantDefine.DEVICE_CAPABILITY_ALARM_WHEN_HOME_ARMED);
        return dc != null;
    }

    private void executeScene(Integer scenedbid) {
        SceneService ss = new SceneService();
        Scene s = ss.query(scenedbid);

        if (s == null) {
            return;
        }

        SceneExecutor se = new SceneExecutor(scenedbid, phoneUser, null, phoneUser.getPhonenumber(),
                IRemoteConstantDefine.OPERATOR_TYPE_SCENE,
                IRemoteConstantDefine.SCENE_EXECUTE_TYPE_USER_TRIGGER);
        se.run();
    }

    private boolean isParitionArmed() {
        return partition.getArmstatus() != IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM
                && this.partition.getArmstatus() != IRemoteConstantDefine.PARTITION_ARM_STATUS_EXIT_DELAY_IN_PROGRESS
                && this.partition.getArmstatus() != IRemoteConstantDefine.PARTITION_ARM_STATUS_ENTRY_DELAY_IN_PROGRESS;
    }

    protected boolean deviceHasPartition() {
        boolean hasPartition = zWaveDevice.getPartitionid() != null;
        if (hasPartition) {
            super.setPartitionArmStatus(zWaveDevice.getPartitionid());
        }
        return hasPartition;
    }

    private void disarmPartition(int partitionid, String password, int usercode) {
        if (usercode != 0) {
            DoorlockUserService dlus = new DoorlockUserService();
            DoorlockUser doorlockUser = dlus.querybyZwavedeviceidAndUsercode(zWaveDevice.getZwavedeviceid(), usercode);
            username = doorlockUser.getUsername();
        }

        PartitionService ps = new PartitionService();
        Partition p = ps.query(partitionid);

        if (p == null || p.getArmstatus() == IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM) {
            log.info("partition is null or partition don't need to disarm");
            return;
        }

        if (p.getZwavedevice() != null) {
            //dsc partition
            disarmDSCPartition(p, password);
        } else {
            disarmZwavePartition(p, password);
        }
    }

    private void disarmDSCPartition(Partition p, String password) {
        if (password.length() == 4) {
            password = password + "00";
        }

        createDSCPartitionDisarm(p);

        if (p.getArmstatus() != IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM) {
            String value = p.getDscpartitionid() + password;
            pushCommand(p, "040", value);
        }
    }

    private void createDSCPartitionDisarm(Partition p) {
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.queryByZwavedeviceidAndObjidAndType(zWaveDevice.getZwavedeviceid(), p.getPartitionid(), DeviceOperationType.setdscparititiondisarm);

        if (dos == null || dos.isFinished() || dos.getExpiretime().getTime() < System.currentTimeMillis()) {

            if (dos == null) {
                dos = new DeviceOperationSteps();
            }

            dos.setZwavedeviceid(zWaveDevice.getZwavedeviceid());
            dos.setFinished(false);
            dos.setOptype(DeviceOperationType.setdscparititiondisarm.ordinal());
            dos.setObjid(p.getPartitionid());
            dos.setDeviceid(zWaveDevice.getDeviceid());
            dos.setStarttime(new Date());
            dos.setExpiretime(new Date(System.currentTimeMillis() + 5 * 1000));
        }
        dos.setAppendmessage(username);
        doss.saveOrUpdate(dos);
    }

    private void disarmZwavePartition(Partition partition, String password) {
        if (partition.getPassword() != null) {
            String s = MD5Util.MD5(partition.getPartitionid() + password);
            if (!partition.getPassword().equals(s)) {
                PushHelper.pushNotificationbyType(phoneUser, partition.getName(), username, IRemoteConstantDefine.NOTIFICATION_TYPE_PARTITION_PASSWORD_WRONG);
                log.info("disarm zwave partition by doorlock association, but password is wrong");
                return;
            }
        }

        partition.setArmstatus(IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM);

        unalarmAlarm(partition);
        cancelTimerTask(partition);

        PushEventToThirdpartHelper.pushEventToThirdpart(zWaveDevice.getDeviceid(),zWaveDevice.getZwavedeviceid(), partition.getPartitionid(), null,
                IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM, 0,IRemoteConstantDefine.MESSAGE_PARTITION_DIS_ARM_USER_CODE);

        JMSUtil.sendmessage(IRemoteConstantDefine.EVENT_DSC_PARTITION_ARM_STATUS,
                new DSCEvent(partition.getPartitionid(), partition.getPhoneuser().getPhoneuserid(), IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM, new Date(),
                        IRemoteConstantDefine.NOTIFICATION_DSC_ARM_STATUS_CHANGE, partition.getPhoneuser().getPlatform()));
        writeLog(partition, IRemoteConstantDefine.MESSAGE_PARTITION_DIS_ARM);
    }

    private void cancelTimerTask(Partition partition) {
        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zwavepartitionlist = zds.querybypartitionid(partition.getPartitionid());
        TimerTaskService tts = new TimerTaskService();
        TimerTask tt = tts.queryByTypeAndObjid(IRemoteConstantDefine.TASK_ZWAVE_PARTITION_DELAY_ARM, partition.getPartitionid());
        if (tt != null) {
            ScheduleManager.cancelJob(tt.getTimertaskid(), IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK);
            tts.delete(tt);
        }

        for (ZWaveDevice z : zwavepartitionlist) {
            TimerTask timerTask1 = tts.queryByTypeAndObjid(IRemoteConstantDefine.TASK_SENSOR_DELAY_ALARM, z.getZwavedeviceid());
            if (timerTask1 != null) {
                ScheduleManager.cancelJob(timerTask1.getTimertaskid(), IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK);
                tts.delete(timerTask1);
            }
        }
    }

    private void unalarmAlarm(Partition partition) {
        RemoteService rs = new RemoteService();
        List<String> devices = rs.queryDeviceidByPhoneuserid(partition.getPhoneuser().getPhoneuserid());
        List<ZWaveDevice> zdList = new ZWaveDeviceService().queryByDevicetypeAndPartitionid(devices, IRemoteConstantDefine.DEVICE_TYPE_ALARM, partition.getPartitionid());

        for (ZWaveDevice d : zdList) {
            CommandTlv ct = CommandUtil.createUnalarmCommand(d.getNuid());
            SynchronizeRequestHelper.asynchronizeRequest(d.getDeviceid(), ct , 0);
        }
    }

    private void pushCommand(Partition partition, String command, String value) {
        CommandTlv commandTlv = new CommandTlv(107, 1);
        byte[] cmd = createCommand(command, value);
        commandTlv.addUnit(new TlvByteUnit(93, cmd));

        SynchronizeRequestHelper.asynchronizeRequest(partition.getZwavedevice().getDeviceid(), commandTlv, 1);
    }

    private byte[] createCommand(String cmd, String value) {
        if (value == null) {
            value = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(cmd).append(value);

        char[] chars = sb.toString().toCharArray();
        int total = 0;
        for (int i = 0; i < chars.length; i++) {
            total += (int) chars[i];
        }
        String string = Integer.toHexString(total);
        if (string.length() > 2) {
            string = string.substring(string.length() - 2);
        }
        sb.append(string.toUpperCase());
        byte[] bytes = sb.toString().getBytes();
        byte[] endString = new byte[]{0x0D, 0x0A};
        byte[] newBytes = new byte[bytes.length + endString.length];
        System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
        System.arraycopy(endString, 0, newBytes, bytes.length, endString.length);
        Utils.print("zzzlll: ", newBytes);
        return newBytes;
    }

    private void writeLog(Partition partition, String msg) {
        Notification notification = new Notification();

        notification.setDeviceid("");
        notification.setName("");
        notification.setAppendmessage(username != null ? username : phoneUser.getPhonenumber());
        ZWaveDevice zd = partition.getZwavedevice();
        if (zd != null) {
            notification.setDeviceid(zd.getDeviceid());
            notification.setZwavedeviceid(zd.getZwavedeviceid());
            notification.setMajortype(zd.getMajortype());
            notification.setDevicetype(zd.getDevicetype());
            notification.setNuid(zd.getNuid());
            notification.setName(zd.getName());
        }
        notification.setFamilyid(phoneUser.getFamilyid());
        notification.setReporttime(new Date());
        notification.setMessage(msg);
        notification.setPhoneuserid(phoneUser.getPhoneuserid());
        JSONObject json = new JSONObject();
        json.put("dscpartitionid", null);
        json.put("partitionname", partition.getName());
        json.put("partitionid", partition.getPartitionid());
//        json.put("usercode", PhoneUserHelper.getUserCode(phoneUser.getPhoneuserid()));
        notification.setAppendjsonstring(json.toJSONString());
        notification.setWarningstatus(IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM);

        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

    public ZWaveDevice getzWaveDevice() {
        return zWaveDevice;
    }

    public Integer getPhoneuserid() {
        return phoneuserid;
    }

    public Partition getPartition() {
        return partition;
    }
}
