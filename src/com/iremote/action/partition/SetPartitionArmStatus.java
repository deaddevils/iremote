package com.iremote.action.partition;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PushEventToThirdpartHelper;
import com.iremote.action.notification.UnalarmNotificationAction;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.DSCEvent;
import com.iremote.common.jms.vo.SetArmWithNoDelayEvent;
import com.iremote.common.md5.MD5Util;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.*;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.service.*;
import com.iremote.task.timertask.processor.CheckDoorSensorHelper;
import com.iremote.task.timertask.processor.PushHelper;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;
 
import java.util.Date;
import java.util.List;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "partition", parameter = "partitionid")
public class SetPartitionArmStatus {
    protected int resultCode = ErrorCodeDefine.SUCCESS;
    private PhoneUser phoneuser;
    protected int partitionid;
    protected String password;
    protected int armstatus;
    protected String message;
    protected boolean isDSCPartition = true;
    protected CheckDoorSensorHelper checkDoorSensorHelper;
    private Partition partition;
    private String operator;
    protected boolean setDelay = false;
    private int eclipseby;

    public String execute() {
        if (partitionid == 0 || !validateArmStatus(armstatus)) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }
        if (!validatePassword()) {
            return Action.SUCCESS;
        }

        PartitionService ps = new PartitionService();
        partition = ps.query(partitionid);
        if (partition == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return Action.SUCCESS;
        }
         checkDoorSensorHelper = new CheckDoorSensorHelper(partition);
        if (partition.getZwavedevice() == null) {
            isDSCPartition = false;
        }

        String dscpartitionid = String.valueOf(partition.getDscpartitionid());
        DeviceOperationStepsService doss = new DeviceOperationStepsService();

        if (partition.getArmstatus() == armstatus || checkPartitionStatus()) {
            eclipseby = IRemoteConstantDefine.NOTIFICATION_ECLIPSE_BY_PARTITION_STATUS_DO_NOT_NEED_CHANGE;
            writeLog(String.valueOf(partition.getDscpartitionid()));
            return Action.SUCCESS;
        }
        
        if (armstatus == 0) {
            if (disArm(dscpartitionid, doss)){
                return Action.SUCCESS;
            }
        } else {
            if (arm(dscpartitionid, doss)){
                return Action.SUCCESS;
            }
        }
        return Action.SUCCESS;
    }

    private boolean checkPartitionStatus() {
        return armstatus != IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM
                && (partition.getArmstatus() == IRemoteConstantDefine.PARTITION_ARM_STATUS_EXIT_DELAY_IN_PROGRESS
                || partition.getArmstatus() == IRemoteConstantDefine.PARTITION_ARM_STATUS_ENTRY_DELAY_IN_PROGRESS);
    }

    private boolean arm(String dscpartitionid, DeviceOperationStepsService doss) {
        DeviceOperationSteps dos;
        if(!checkDoorSensorHelper.checkDoorSensor(armstatus) && !setDelay){
            resultCode = ErrorCodeDefine.PHONEUSER_ARM_SOME_DOORS_OR_WINDOWS_IS_OPENING;
            return true;
        }

        if (isDSCPartition) {
            dos = doss.querybydeviceidandtype(partition.getZwavedevice().getDeviceid(), DeviceOperationType.setdscarm, partition.getDscpartitionid());
        }else{
            dos = doss.queryByObjidAndType(partition.getPartitionid(), DeviceOperationType.setzWavePartitionArm);
        }

        if (dos != null && !isFinished(dos)) {
            resultCode = ErrorCodeDefine.GATEWAY_BUSSING;
            return true;
        }

        if (dos == null) {
            dos = new DeviceOperationSteps();
        }

        init(dos);

        if (!isDSCPartition) {
            createZwavePartitionArmTask(true, doss, dos);
            if (partition.getDelay() != null && partition.getDelay() > 0) {
                createTimerTask(partition);
                partition.setArmstatus(IRemoteConstantDefine.PARTITION_ARM_STATUS_EXIT_DELAY_IN_PROGRESS);
                pushPartitionArmStatusMessage(IRemoteConstantDefine.PARTITION_ARM_STATUS_EXIT_DELAY_IN_PROGRESS);
                pushEventToThirdpart(IRemoteConstantDefine.MESSAGE_PARTITION_ARM_USER_CODE,
                        IRemoteConstantDefine.PARTITION_ARM_STATUS_EXIT_DELAY_IN_PROGRESS);
            } else {
                partition.setArmstatus(armstatus);
                pushPartitionArmStatusNotification(armstatus);
                pushEventToThirdpart(IRemoteConstantDefine.MESSAGE_PARTITION_ARM_USER_CODE);
                writeLog(dscpartitionid);
                if (checkDoorSensorHelper.getNotreadyappliance() != null && checkDoorSensorHelper.getNotreadyappliance().size() != 0) {
                    PushHelper.pushZwaveArmFailedMessage(phoneuser, checkDoorSensorHelper.getNotreadyappliance());
                    checkDoorSensorHelper.clear();
                }
            }
            return true;
        } else {
            //dsc arm
            if (pushDSCCommand(partition, doss, dos)) {
                return true;
            }
        }
        return false;
    }

    private void pushEventToThirdpart(String type) {
        PushEventToThirdpartHelper.pushEventToThirdpartFromZwavePartition(phoneuser.getPhoneuserid(), partition.getPartitionid(), armstatus, type);
    }

    private void pushEventToThirdpart(String type, int armStatus) {
        PushEventToThirdpartHelper.pushEventToThirdpartFromZwavePartition(phoneuser.getPhoneuserid(), partition.getPartitionid(), armStatus, type);
    }

    private void pushPartitionArmStatusMessage() {
        pushPartitionArmStatusMessage(armstatus);
    }

    private void pushPartitionArmStatusMessage(int armStatus){
        JMSUtil.sendmessage(IRemoteConstantDefine.EVENT_DSC_PARTITION_ARM_STATUS,
                new DSCEvent(partitionid, partition.getPhoneuser().getPhoneuserid(), armStatus, new Date(),
                        IRemoteConstantDefine.NOTIFICATION_DSC_ARM_STATUS_CHANGE, partition.getPhoneuser().getPlatform()));
    }

    private void pushPartitionArmStatusNotification(int status) {
        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_DSC_PARTITION_ARM_STATUS,
                new DSCEvent(partitionid, partition.getPhoneuser().getPhoneuserid(), status, new Date(),
                        IRemoteConstantDefine.NOTIFICATION_DSC_ARM_STATUS_CHANGE, partition.getPhoneuser().getPlatform(), partition.getName()));
    }

    private boolean checkPassword() {
        if (partition.getPassword() == null) {
            return true;
        }
        if (StringUtils.isBlank(password)){
            resultCode = ErrorCodeDefine.PASSWORD_REQUIRED;
            return false;
        }
        String passwordEncrypted = MD5Util.MD5(String.valueOf(partitionid) + password);
        if (partition.getPassword().equals(passwordEncrypted)) {
            return true;
        } else {
            resultCode = ErrorCodeDefine.DSC_PASSWORD_ERROR;
            return false;
        }
    }

    private boolean disArm(String dscpartitionid, DeviceOperationStepsService doss) {
        if (isDSCPartition) {
            return disarmDSCPartition(dscpartitionid, doss);
        } else {
          return disarmZwavePartition();
        }
    }

    private boolean disarmZwavePartition() {
        if (!checkPassword()){
            return true;
        }
        partition.setArmstatus(IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM);
        pushPartitionArmStatusMessage();
        writeLog(String.valueOf(partition.getDscpartitionid()));
        createZwavePartitionArmTask(partition, true);

        ZWaveDeviceService zds = new ZWaveDeviceService();

        unalarmAlarm(zds);
        pushEventToThirdpart(IRemoteConstantDefine.MESSAGE_PARTITION_DIS_ARM_USER_CODE);


        List<ZWaveDevice> zwavepartitionlist = zds.querybypartitionid(partitionid);
        TimerTaskService tts = new TimerTaskService();
        TimerTask tt = tts.queryByTypeAndObjid(IRemoteConstantDefine.TASK_ZWAVE_PARTITION_DELAY_ARM, partitionid);
        if (tt != null) {
            ScheduleManager.cancelJob(tt.getTimertaskid(), IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK);
            tts.delete(tt);
        }

        for(ZWaveDevice z : zwavepartitionlist){
            TimerTask timerTask1 = tts.queryByTypeAndObjid(IRemoteConstantDefine.TASK_SENSOR_DELAY_ALARM, z.getZwavedeviceid());
            if (timerTask1 != null) {
                ScheduleManager.cancelJob(timerTask1.getTimertaskid(), IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK);
                tts.delete(timerTask1);
            }
        }
        return false;
    }

    private void unalarmAlarm(ZWaveDeviceService zds) {
        UnalarmNotificationAction una = new UnalarmNotificationAction();
        RemoteService rs = new RemoteService();
        List<String> devices = rs.queryDeviceidByPhoneuserid(partition.getPhoneuser().getPhoneuserid());
        if (devices == null || devices.size() == 0) {
            return ;
        }

        List<ZWaveDevice> zdList = zds.queryByDevicetypeAndPartitionid(devices, IRemoteConstantDefine.DEVICE_TYPE_ALARM, partitionid);

        for (ZWaveDevice zWaveDevice : zdList) {
            una.setZwavedeviceid(zWaveDevice.getZwavedeviceid());
            una.setPhoneuser(phoneuser);
            una.execute();
        }
    }

    private boolean disarmDSCPartition(String dscpartitionid, DeviceOperationStepsService doss) {
        DeviceOperationSteps dos = doss.querybydeviceidandtype(partition.getZwavedevice().getDeviceid(), DeviceOperationType.setdscarm, partition.getDscpartitionid());
        if (dos == null) {
            dos = new DeviceOperationSteps();
            dos.setFinished(true);
        }
        if (!isFinished(dos)) {
            resultCode = ErrorCodeDefine.GATEWAY_BUSSING;
            return true;
        }
        if (StringUtils.isBlank(password)) {
            resultCode = ErrorCodeDefine.PASSWORD_REQUIRED;
            return true;
        }

        message = IRemoteConstantDefine.MESSAGE_PARTITION_DIS_ARM;
        createTask(partition, doss, dos);

        String value = dscpartitionid + password;

        if (password.length() == 4) {
            value = dscpartitionid + password + "00";
        }

        pushCommand(partition, "040", value);
        return false;
    }

    private void writeLog(String dscpartitionid) {
        String msg;
        if (armstatus == 1) {
            msg = IRemoteConstantDefine.MESSAGE_PARTITION_ARM;
        } else if (armstatus == 0) {
            msg = IRemoteConstantDefine.MESSAGE_PARTITION_DIS_ARM;
        } else {
            msg = IRemoteConstantDefine.MESSAGE_PARTITION_HOME_ARM;
        }
        Notification notification = new Notification();

        ZWaveDevice zd = partition.getZwavedevice();
        notification.setDeviceid("");
        notification.setName(partition.getName());
        notification.setFamilyid(phoneuser.getFamilyid());

        if (operator != null) {
            notification.setAppendmessage(operator);
            if ("".equals(operator)){
                notification.setAppendmessage(null);
            }
        } else {
            notification.setAppendmessage(phoneuser.getPhonenumber());
        }

        if (zd != null){
            notification.setDeviceid(zd.getDeviceid());
            notification.setZwavedeviceid(zd.getZwavedeviceid());
            notification.setMajortype(zd.getMajortype());
            notification.setDevicetype(zd.getDevicetype());
            notification.setNuid(zd.getNuid());
            notification.setName(zd.getName());
        }
        if (eclipseby != IRemoteConstantDefine.NOTIFICATION_ECLIPSE_BY_NORMAL) {
            notification.setEclipseby(eclipseby);
        }
        notification.setReporttime(new Date());
        notification.setMessage(msg);
        notification.setPhoneuserid(phoneuser.getPhoneuserid());
        JSONObject json = new JSONObject();
        json.put("dscpartitionid", dscpartitionid);
        json.put("partitionname", partition.getName());
        json.put("partitionid", partition.getPartitionid());
        notification.setAppendjsonstring(json.toJSONString());
        notification.setWarningstatus(armstatus);

        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

    private void createTimerTask(Partition partition) {
        TimerTaskService tts = new TimerTaskService();
        TimerTask timerTask = tts.queryByTypeAndObjid(IRemoteConstantDefine.TASK_ZWAVE_PARTITION_DELAY_ARM, partitionid);
        if (timerTask != null && timerTask.getExcutetime().getTime() > System.currentTimeMillis()) {
            return;
        }
        if (timerTask == null) {
            timerTask = new TimerTask();
        }
        timerTask.setCreatetime(new Date());
        long date = System.currentTimeMillis() + partition.getDelay() * 1000;
        timerTask.setExcutetime(new Date(date));
        timerTask.setExpiretime(new Date(date + IRemoteConstantDefine.TIMER_TASK_EXPIRE_TIME));
        timerTask.setObjid(partitionid);
        timerTask.setType(IRemoteConstantDefine.TASK_ZWAVE_PARTITION_DELAY_ARM);
        JSONObject json = new JSONObject();
        json.put("phoneuserid", phoneuser.getPhoneuserid());
        json.put("armStatus", armstatus);
        json.put("operator", operator);
        timerTask.setJsonpara(json.toJSONString());
        ScheduleManager.excuteWithSaveInDB(IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK, timerTask);
    }

    private boolean pushDSCCommand(Partition partition, DeviceOperationStepsService doss, DeviceOperationSteps dos) {
        String cmd0;
            if (armstatus == 3) {
                cmd0 = "031";
                message = IRemoteConstantDefine.MESSAGE_PARTITION_HOME_ARM;
            } else if (armstatus == 4) {
                cmd0 = "032";
                message = IRemoteConstantDefine.MESSAGE_PARTITION_ARM_WITHOUT_CODE;
            } else if (armstatus == 5) {
                cmd0 = "033";
                message = IRemoteConstantDefine.MESSAGE_PARTITION_ARM;
            } else {
                message = IRemoteConstantDefine.MESSAGE_PARTITION_ARM;
                cmd0 = "030";
            }
            createTask(partition, doss, dos);
            if (armstatus ==4 ){
            JMSUtil.sendmessage(IRemoteConstantDefine.SET_ARM_WITH_NO_DELAY, new SetArmWithNoDelayEvent(password, partition, dos));
            return true;
            }
            pushCommand(partition, cmd0, String.valueOf(partition.getDscpartitionid()));
        return false;
        }

    private boolean validatePassword() {
        if (armstatus == 5 && StringUtils.isBlank(password)) {
            resultCode = ErrorCodeDefine.PASSWORD_REQUIRED;
            return false;
        }
     /*   if (!StringUtils.isBlank(password)) {
            if (password.length() == 4) {
                password = password + "00";
            } else if (password.length() != 6) {
                resultCode = ErrorCodeDefine.PARMETER_ERROR;
                return false;
            }
        }*/
        return true;
    }

    private void pushCommand(Partition partition, String command, String value) {
        CommandTlv commandTlv = new CommandTlv(107, 1);
        if (armstatus == 5) {
            if (password.length() == 4) {
                value = value + password + "00";
            } else {
                value = value + password;
            }
        }
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

    private void createTask(Partition partition, DeviceOperationStepsService doss, DeviceOperationSteps dos) {
        if (partition.getZwavedevice() != null) {
            dos.setZwavedeviceid(partition.getZwavedevice().getZwavedeviceid());
            dos.setDeviceid(partition.getZwavedevice().getDeviceid());
        }
        dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_IN_PROCESSOR);
        dos.setFinished(false);
        dos.setStarttime(new Date());
        dos.setExpiretime(new Date(System.currentTimeMillis() + 25 * 1000));
        dos.setOptype(DeviceOperationType.setdscarm.ordinal());
        if (!isDSCPartition) {
            dos.setOptype(DeviceOperationType.setzWavePartitionArm.ordinal());
            dos.setObjid(partition.getPartitionid());
        } else {
            dos.setObjid(partition.getDscpartitionid());
        }
        saveTask(partition, doss, dos);
    }

    private void createZwavePartitionArmTask(boolean isFinished, DeviceOperationStepsService doss, DeviceOperationSteps dos) {
        if (dos == null) {
            dos = new DeviceOperationSteps();
        }
        dos.setFinished(isFinished);
        if (isFinished) {
            dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_SUCCESS);
        } else {
            dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_IN_PROCESSOR);
        }

        if (partition.getZwavedevice() != null){
            dos.setZwavedeviceid(partition.getZwavedevice().getZwavedeviceid());
            dos.setDeviceid(partition.getZwavedevice().getDeviceid());
        }

        dos.setStarttime(new Date());
        dos.setExpiretime(new Date(System.currentTimeMillis() + 5 * 1000));
        dos.setOptype(DeviceOperationType.setzWavePartitionArm.ordinal());
        dos.setObjid(partition.getPartitionid());

        saveTask(partition, doss, dos);
    }

    private void createZwavePartitionArmTask(Partition partition, boolean isFinished) {
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.queryByObjidAndType(partitionid, DeviceOperationType.setzWavePartitionArm);
        if (dos == null) {
            dos = new DeviceOperationSteps();
        }
        if (isFinished) {
            dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_SUCCESS);
            dos.setFinished(true);
        } else {
            dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_IN_PROCESSOR);
            dos.setFinished(false);
        }
        if (partition.getZwavedevice() != null){
            dos.setZwavedeviceid(partition.getZwavedevice().getZwavedeviceid());
            dos.setDeviceid(partition.getZwavedevice().getDeviceid());
        }
        dos.setStarttime(new Date());
        dos.setExpiretime(new Date(System.currentTimeMillis() + 5 * 1000));
        dos.setOptype(DeviceOperationType.setzWavePartitionArm.ordinal());
        dos.setObjid(partition.getPartitionid());

        saveTask(partition, doss, dos);
        }

    private void saveTask(Partition partition, DeviceOperationStepsService doss, DeviceOperationSteps dos) {
        JSONObject json = new JSONObject();
        json.put("partitionid", partitionid);
        json.put("dscpartitionid", partition.getDscpartitionid());
        json.put("password", password);
        json.put("message", message);
        json.put("phoneuserid", phoneuser.getPhoneuserid());

        if (operator != null) {
            json.put("phonenumber", operator);
            if ("".equals(operator)){
                json.put("phonenumber", null);
            }
        } else {
            json.put("phonenumber", phoneuser.getPhonenumber());
        }

        if (armstatus == 0) {
            json.put("toarmstatus", IRemoteConstantDefine.DSC_TO_ARM_STATUS_DIS_ARM);
        } else {
            json.put("toarmstatus", IRemoteConstantDefine.DSC_TO_ARM_STATUS_ARM);
        }
        dos.setAppendmessage(json.toJSONString());
        doss.saveOrUpdate(dos);
    }

    private boolean isFinished(DeviceOperationSteps dos) {
        return dos.isFinished() || System.currentTimeMillis() > dos.getExpiretime().getTime();
    }

    private void init(DeviceOperationSteps dos) {
        dos.setAppendmessage(null);
        dos.setFinished(false);
        dos.setStarttime(null);
//        dos.setOptype(DeviceOperationType.setdscarm.ordinal());
        dos.setExpiretime(new Date(System.currentTimeMillis() - 1000));
        dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_NOT_IN_PROCESSOR);
    }

    private boolean validateArmStatus(int armstatus) {
        if (armstatus >= 0 && armstatus <= 5) {
            return true;
        }
        return false;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setPartitionid(int partitionid) {
        this.partitionid = partitionid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setArmstatus(int armstatus) {
        this.armstatus = armstatus;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public int getResultCode() {
        return resultCode;
    }

    public List<ZWaveDevice> getNotreadyappliance() {
        if (checkDoorSensorHelper == null || checkDoorSensorHelper.getNotreadyappliance() == null
                || checkDoorSensorHelper.getNotreadyappliance().size() == 0) {
            return null;
        }
        return checkDoorSensorHelper.getNotreadyappliance();
    }

    public void setSetDelay(boolean setDelay) {
        this.setDelay = setDelay;
    }
}
