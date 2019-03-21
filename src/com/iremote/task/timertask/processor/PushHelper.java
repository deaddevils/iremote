package com.iremote.task.timertask.processor;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.NotificationHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.SubdeviceStatusEvent;
import com.iremote.common.message.MessageParser;
import com.iremote.common.push.PushMessage;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.infraredtrans.serianet.dsc.DSCHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PushHelper {

    /**
     * @param user
     * @param name It's the failed device's name
     */
    public static void pushArmFailedMessage(PhoneUser user, String name) {
        pushArmFailedNotification(user, name, IRemoteConstantDefine.WARNING_TYPE_ARM_FAILED2, IRemoteConstantDefine.WARNING_TYPE_ARM_SUCCESS_DEVICE_FAILED);
    }

    public static void pushArmFailedMessage(PhoneUser user, List<ZWaveDevice> zWaveDeviceList) {
        StringBuffer sb = getAllFailedDeviceName(zWaveDeviceList);

        pushArmFailedMessage(user, sb.toString());
    }

    public static void pushZwaveArmFailedMessage(PhoneUser user, List<ZWaveDevice> zWaveDeviceList) {
        StringBuffer sb = getAllFailedDeviceName(zWaveDeviceList);

        NotificationHelper.pushArmFailedMessage(user, sb.toString());
    }

    private static StringBuffer getAllFailedDeviceName(List<ZWaveDevice> zWaveDeviceList) {
        StringBuffer sb = new StringBuffer();
        for (ZWaveDevice zd : zWaveDeviceList) {
            if (sb.length() > 0)
                sb.append(",");
            sb.append(zd.getName());
        }
        return sb;
    }

    public static void pushNotificationbyType(PhoneUser user, String partitionname, String username, String type) {
        if (user == null) {
            return;
        }

        JSONObject json = new JSONObject();
        json.put("type", type);

        MessageParser m = Utils.createWarningMessage(type, user.getPlatform(), user.getLanguage(), partitionname, username);

        PushMessage.pushWarningNotification(Arrays.asList(new String[]{user.getAlias()}), user.getPlatform(), m.getMessage(), json);
    }

    public static void pushZwaveSubDeviceStatusMessage(ZWaveSubDevice zsd) {
        SubdeviceStatusEvent event = new SubdeviceStatusEvent();
        event.setZwavedeviceid(zsd.getZwavedevice().getZwavedeviceid());
        event.setType(IRemoteConstantDefine.NOTIFICATION_SUB_DEVICE_STATUS);
        event.setSubdeviceid(zsd.getZwavesubdeviceid());
        event.setEventtime(new Date());
        event.setEnablestatus(zsd.getEnablestatus());
        event.setWarningstatuses(zsd.getWarningstatuses());
        event.setStatus(zsd.getStatus());
        event.setDeviceid(zsd.getZwavedevice().getDeviceid());
        event.setChannelid(zsd.getChannelid());

        JMSUtil.sendmessage(IRemoteConstantDefine.EVNET_PUSH_SUB_ZWAVEDEVICE_STATUS, event);

//        DSCHelper.pushDscUnalarmMessage(zsd.getZwavedevice(), zsd.getChannelid(), zsd.getChannelid());
    }

    public static void pushArmFailedNotification(PhoneUser user, String name, String type1, String type2) {
        JSONObject json = new JSONObject();
        json.put("type", type1);

        MessageParser m = Utils.createWarningMessage(type2, user.getPlatform(), user.getLanguage(), name);

        PushMessage.pushWarningNotification(Arrays.asList(new String[]{user.getAlias()}), user.getPlatform(), m.getMessage(), json);
    }

    public static void pushArmFailedNotification(PhoneUser user, List<ZWaveDevice> zWaveDeviceList) {
        StringBuffer sb = getAllFailedDeviceName(zWaveDeviceList);

        pushArmFailedNotification(user, sb.toString(), IRemoteConstantDefine.WARNING_TYPE_ARM_FAILED, IRemoteConstantDefine.WARNING_TYPE_ARM_FAILED);
    }
}
