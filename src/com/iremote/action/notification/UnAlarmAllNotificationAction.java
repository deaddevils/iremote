package com.iremote.action.notification;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.*;
import com.iremote.domain.TimerTask;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.*;
import com.iremote.task.timertask.processor.ChannelDelayAlarmingProcessor;
import com.iremote.task.timertask.processor.DSCKeyDelayUnAlarmProcessor;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class UnAlarmAllNotificationAction {
    private int resultCode;
    private PhoneUser phoneuser;
    private RemoteService remoteService;
    private ZWaveDeviceService zWaveDeviceService;
    private CameraService cameraService;

    public String execute(){
        if (phoneuser == null) {
            resultCode = ErrorCodeDefine.TOKEN_ERROR;
            return Action.SUCCESS;
        }

        ArrayList<ZWaveDevice> zWaveDevices = new ArrayList<>();
        ArrayList<Camera> cameras = new ArrayList<>();
        remoteService = new RemoteService();
        zWaveDeviceService = new ZWaveDeviceService();
        cameraService = new CameraService();
        PhoneUserService phoneUserService = new PhoneUserService();

        List<PhoneUser> families0 = phoneUserService.querybyfamiliyid(phoneuser.getFamilyid());
        HashSet<PhoneUser> families = new HashSet<>();
        families.addAll(families0);
        families.add(phoneuser);
        for (PhoneUser user : families) {
            List<String> deviceIds = remoteService.queryDeviceidByPhoneuserid(user.getPhoneuserid());
            zWaveDevices.addAll(getZWaveDevicesByDeviceIds(deviceIds));
            cameras.addAll(getCamerasByDeviceIds(deviceIds));
        }

        UserShareService userShareService = new UserShareService();
        List<UserShare> userShares = userShareService.querybytoUser(phoneuser.getPhoneuserid(), IRemoteConstantDefine.USER_SHARE_STATUS_NORMAL);
        for (UserShare userShare : userShares) {
            zWaveDevices.addAll(getZWaveDevicesFromPhoneUser(userShare.getShareuserid()));
            cameras.addAll(getCamerasFromPhoneUser(userShare.getShareuserid()));
        }

        ZWaveDeviceShareService zWaveDeviceShareService = new ZWaveDeviceShareService();
        List<ZWaveDeviceShare> zWaveDeviceShares = zWaveDeviceShareService.query(phoneuser.getPhoneuserid());

        setDeviceListFromShares(zWaveDeviceShares, zWaveDevices, cameras);

        unAlarmNotification(zWaveDevices);
        unAlarmCameraNotification(cameras);
        return Action.SUCCESS;
    }

    private void unAlarmCameraNotification(ArrayList<Camera> cameras) {
        for (Camera camera : cameras) {
            if (isNotWarning(camera.getWarningstatuses())) {
                continue;
            }
            UnalarmCameraNotificationAction action = new UnalarmCameraNotificationAction();
            action.setPhoneuser(phoneuser);
            action.setCamera(camera);
            action.execute();
        }
    }

    private void setDeviceListFromShares(List<ZWaveDeviceShare> zWaveDeviceShares, ArrayList<ZWaveDevice> zWaveDevices, ArrayList<Camera> cameras) {
        Set<Integer> zWaveDeviceIds = new HashSet<>();
        HashSet<Integer> cameraIds = new HashSet<>();

        for (ZWaveDeviceShare zWaveDeviceShare : zWaveDeviceShares) {
            if (zWaveDeviceShare.getZwavedeviceid() != null) {
                zWaveDeviceIds.add(zWaveDeviceShare.getZwavedeviceid());
            } else if (zWaveDeviceShare.getCameraid() != null){
                cameraIds.add(zWaveDeviceShare.getCameraid());
            }
        }

        zWaveDevices.addAll(zWaveDeviceService.query(zWaveDeviceIds));
        cameras.addAll(cameraService.query(cameraIds));
    }

    private UnalarmNotificationAction getUnAlarmNotification0() {
        UnalarmNotificationAction action = new UnalarmNotificationAction();
        action.setNeedUnAlarmAlarm(false);
        action.setPhoneuser(phoneuser);
        return action;
    }

    private void unAlarmNotification(List<ZWaveDevice> zWaveDevices) {
        for (ZWaveDevice zWaveDevice : zWaveDevices) {
            if (IRemoteConstantDefine.DEVICE_TYPE_ALARM.equals(zWaveDevice.getDevicetype())){
                unAlarmAlarm(zWaveDevice.getNuid(), zWaveDevice.getDeviceid());
            } else if (IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE.equals(zWaveDevice.getDevicetype())) {
                unAlarmTwoChannelNotification(zWaveDevice);
            } else if (IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(zWaveDevice.getDevicetype())){
                unAlarmDSCNotification(zWaveDevice);
            } else {
                if (isNotWarning(zWaveDevice.getWarningstatuses())) {
                    continue;
                }
                UnalarmNotificationAction action = getUnAlarmNotification0();
                action.setZwavedevice(zWaveDevice);
                action.execute();
            }
        }
    }

    private void unAlarmAlarm(int nuid, String deviceId) {
        CommandTlv ct = CommandUtil.createUnalarmCommand(nuid);
        SynchronizeRequestHelper.asynchronizeRequest(deviceId, ct , 0);
    }

    private void unAlarmDSCNotification(ZWaveDevice zWaveDevice) {
        if (!isNotWarning(zWaveDevice.getWarningstatuses())) {
            JSONArray jsonArray = JSONArray.parseArray(zWaveDevice.getWarningstatuses());
            for (Object o : jsonArray) {
                int delayWarningStatus = (int) o;
                if (IRemoteConstantDefine.DSC_WARNING_TYPE_3_ALARM_KEYS.contains(o)) {
                    DSCKeyDelayUnAlarmProcessor processor = new DSCKeyDelayUnAlarmProcessor(zWaveDevice, delayWarningStatus);
                    processor.run();
                    cancelTimerTask(zWaveDevice.getZwavedeviceid(), processor.getWarningStatus(delayWarningStatus));
                }
            }
        }

        List<Partition> partitions = zWaveDevice.getPartitions();
        if (partitions == null) {
            return;
        }
        for (Partition partition : partitions) {
            if (partition.getWarningstatus() != IRemoteConstantDefine.DSC_PARTITION_WARNING_STATUS_NORMAL) {
                List<ZWaveSubDevice> subDevices = partition.getzWaveSubDevices();
                for (ZWaveSubDevice subDevice : subDevices) {
                    if (Utils.isJsonArrayContaints(subDevice.getWarningstatuses(), IRemoteConstantDefine.DSC_ZWAVE_SUB_DEIVCE_WARNING_STATUS_ARARM_DELAY)) {
                        ChannelDelayAlarmingProcessor processor = new ChannelDelayAlarmingProcessor(subDevice);
                        processor.run();
                        cancelTimerTask(subDevice.getZwavesubdeviceid(), IRemoteConstantDefine.TASK_DSC_CHANNEL_ALARM_RESTORE);
                    }
                }
            }
        }
    }

    private void cancelTimerTask(int objId, int timerTaskType) {
        TimerTaskService tts = new TimerTaskService();
        TimerTask timerTask = tts.queryByTypeAndObjid(timerTaskType, objId);
        if (timerTask != null){
            ScheduleManager.cancelJob(timerTask.getTimertaskid(), IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK);
            tts.delete(timerTask);
        }
    }

    private boolean isNotWarning(String warningStatuses) {
        return StringUtils.isBlank(warningStatuses) || "[]".equals(warningStatuses);
    }

    private void unAlarmTwoChannelNotification(ZWaveDevice zWaveDevice) {
        List<ZWaveSubDevice> zWaveSubDeviceList = zWaveDevice.getzWaveSubDevices();
        if (zWaveSubDeviceList == null) {
            return;
        }
        for (ZWaveSubDevice zWaveSubDevice : zWaveSubDeviceList) {
            if (isNotWarning(zWaveSubDevice.getWarningstatuses())) {
                continue;
            }
            UnalarmNotificationAction action1 = getUnAlarmNotification0();
            action1.setZwavedevice(zWaveDevice);
            action1.setChannelid(zWaveSubDevice.getChannelid());
            action1.execute();
        }
    }

    private Collection<? extends Camera> getCamerasFromPhoneUser(int phoneUserId) {
        List<String> deviceIds = remoteService.queryDeviceidByPhoneuserid(phoneUserId);
        return getCamerasByDeviceIds(deviceIds);
    }

    private List<ZWaveDevice> getZWaveDevicesFromPhoneUser(int phoneUserId) {
        List<String> deviceIds = remoteService.queryDeviceidByPhoneuserid(phoneUserId);
        return getZWaveDevicesByDeviceIds(deviceIds);
    }

    private List<ZWaveDevice> getZWaveDevicesByDeviceIds(List<String> deviceIds) {
        return zWaveDeviceService.querybydeviceid(deviceIds);
    }

    private Collection<? extends Camera> getCamerasByDeviceIds(List<String> deviceIds) {
        return cameraService.querybydeviceid(deviceIds);
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public int getResultCode() {
        return resultCode;
    }
}
