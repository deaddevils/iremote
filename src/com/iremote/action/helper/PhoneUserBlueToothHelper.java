package com.iremote.action.helper;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.NumberUtil;
import com.iremote.common.constant.DeviceShareSource;
import com.iremote.common.encrypt.AES;
import com.iremote.common.encrypt.Tea;
import com.iremote.domain.BlueToothPassword;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveDeviceShare;
import com.iremote.service.*;
import com.opensymphony.xwork2.conversion.impl.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.xsocket.DataConverter;

import java.time.LocalDateTime;
import java.util.*;

public class PhoneUserBlueToothHelper {
    public static List<ZWaveDevice> filterBlueToothLock(List<ZWaveDevice> zWaveDevices) {
        ArrayList<ZWaveDevice> list = new ArrayList<>();
        for (ZWaveDevice zWaveDevice : zWaveDevices) {
            if (isBlueToothLock(zWaveDevice)) {
                list.add(zWaveDevice);
            }
        }
        return list;
    }

    public static void plusBlueToothLockSequence(List<ZWaveDevice> zWaveDevices) {
        for (ZWaveDevice zWaveDevice : zWaveDevices) {
            List<DeviceCapability> capability = zWaveDevice.getCapability();
            for (int i = 0; capability != null && i < capability.size(); i++) {
                if (capability.get(i).getCapabilitycode() == IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_SEQUENCE) {
                    capability.get(i).setCapabilityvalue(String.valueOf(Integer.valueOf(capability.get(i).getCapabilityvalue()) + 1));
                    break;
                }
            }
        }
    }

    public static boolean isBlueToothLock(ZWaveDevice zWaveDevice) {
        return IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK.equals(zWaveDevice.getDevicetype())
                && hasCapability(zWaveDevice, IRemoteConstantDefine.DEVICE_CAPABILITY_LOCK_SUPPORT_BLUE_TOOTH);
    }

    public static boolean isOnlyBlueToothLock(ZWaveDevice zWaveDevice) {
        if (!IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK.equals(zWaveDevice.getDevicetype())) {
            return false;
        }
        if (hasCapability(zWaveDevice, IRemoteConstantDefine.DEVICE_CAPABILITY_LOCK_SUPPORT_BLUE_TOOTH)) {
            for (DeviceCapability deviceCapability : zWaveDevice.getCapability()) {
                if (IRemoteConstantDefine.DEVICE_CAPABILITY_LOCK_TYPE_EXCEPT_BLUE_TOOTH.contains(deviceCapability.getCapabilitycode())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean hasCapability(ZWaveDevice zWaveDevice, int capabilityCode) {
        return getCapability(zWaveDevice, capabilityCode) != null;
    }

    public static DeviceCapability getCapability(ZWaveDevice zWaveDevice, int capabilityCode) {
        for (DeviceCapability deviceCapability : zWaveDevice.getCapability()) {
            if (deviceCapability.getCapabilitycode() == capabilityCode) {
                return deviceCapability;
            }
        }
        return null;
    }

    public static void setDeviceCapability(ZWaveDevice zd, int code, String value) {
        DeviceCapability capability = getCapability(zd, code);
        if (capability == null) {
            zd.getCapability().add(new DeviceCapability(zd, code, value));
        } else {
            capability.setCapabilityvalue(value);
        }
    }

    public static byte[] createPasswordPacket(String macAddress, Integer sequence,
                                              Integer phoneUserId, Date validFrom, Date validThrough, Integer week) {
        byte[] passwordPacket = new byte[28];
        byte[] macAddress0 = NumberUtil.hexToBytes(macAddress.replace(":", ""));
        byte[] sequence0 = NumberUtil.intToByte4(sequence);
        byte[] phoneUserId0 = NumberUtil.intToByte4(phoneUserId);
        ValidTime fromTime = new ValidTime(validFrom);
        ValidTime throughTime = new ValidTime(validThrough);

        System.arraycopy(macAddress0, 0, passwordPacket, 0, 6);
        System.arraycopy(sequence0, 0, passwordPacket, 6, 4);
        System.arraycopy(phoneUserId0, 0, passwordPacket, 10, 4);
        System.arraycopy(fromTime.time, 0, passwordPacket, 14, 4);
        System.arraycopy(throughTime.time, 0, passwordPacket, 18, 4);
        passwordPacket[22] = week == null ? (byte)IRemoteConstantDefine.DIS_ABLE_WEEK : week.byteValue();
        passwordPacket[23] = fromTime.hour;
        passwordPacket[24] = fromTime.minute;
        passwordPacket[25] = throughTime.hour;
        passwordPacket[26] = throughTime.minute;
        addParityBit(passwordPacket);
        return passwordPacket;
    }

    private static class ValidTime{
        byte[] time = new byte[4];
        byte hour, minute;

        ValidTime(Date validateTime) {
            if (validateTime == null) {
                insert(time, (byte) 0);
            } else {
                time = NumberUtil.intToByte4((int) (validateTime.getTime() / 1000));
                LocalDateTime localDateTime = TimeZoneHelper.dateToLocalDateTime(validateTime);
                hour = (byte) localDateTime.getHour();
                minute = (byte) localDateTime.getMinute();
            }
        }
    }

    public static void addParityBit(byte[] passwordPacket) {
        if (passwordPacket == null || passwordPacket.length == 0) {
            return;
        }

        byte parityBit = passwordPacket[0];
        for (int i = 1; i < passwordPacket.length - 1; i++) {
            parityBit ^= passwordPacket[i];
        }
        passwordPacket[passwordPacket.length - 1] = parityBit;
    }

    private static void insert(byte[] bytes, byte value) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = value;
        }
    }

    public static Map<Integer, Map<Integer, String>> createPasswordPacket(
            List<ZWaveDevice> zWaveDeviceList, List<Integer> phoneUserIdList, List<Integer> blockUserList) {
        if (zWaveDeviceList == null || zWaveDeviceList.size() == 0) {
            return null;
        }
        HashMap<Integer, Map<Integer, String>> map = new HashMap<>();
        for (ZWaveDevice zWaveDevice : zWaveDeviceList) {
            Map<Integer, String> passwordPacket = createPasswordPacket(zWaveDevice, phoneUserIdList, blockUserList);
            if (passwordPacket == null) {
                continue;
            }
            map.put(zWaveDevice.getZwavedeviceid(), passwordPacket);
        }
        return map;
    }

    public static Map<Integer, String> createPasswordPacket(ZWaveDevice zd,
                                                            List<Integer> phoneUserIdList, List<Integer> blockUserList) {
        if (!PhoneUserBlueToothHelper.isBlueToothLock(zd)) {
            return null;
        }

        HashMap<Integer, String> map = new HashMap<>();

        DeviceCapability capabilityMac = getCapability(zd, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_MAC_ADDRESS);
        DeviceCapability capabilityKey2 = getCapability(zd, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_2);
        if (capabilityMac == null || capabilityKey2 == null
                || StringUtils.isBlank(capabilityMac.getCapabilityvalue())
                || StringUtils.isBlank(capabilityKey2.getCapabilityvalue())) {
            return null;
        }

        byte[] key2 = AES.decrypt(capabilityKey2.getCapabilityvalue());

        DeviceCapability capabilitySequence = getCapability(zd, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_SEQUENCE);
        if (capabilitySequence == null) {
            capabilitySequence = new DeviceCapability(zd, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_SEQUENCE,
                    String.valueOf(IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_DEFAULT_SEQUENCE + 1));
            new DeviceCapabilityService().save(capabilitySequence);
        }

        ZWaveDeviceShareService service = new ZWaveDeviceShareService();
        for (Integer id : phoneUserIdList) {
            if (blockUserList != null && blockUserList.contains(id)) {
                continue;
            }

            ShareValidateTime shareValidateTime = getShareValidateTime(zd, id, service);

            byte[] passwordPacket0 = PhoneUserBlueToothHelper.createPasswordPacket(capabilityMac.getCapabilityvalue(),
                    Integer.valueOf(capabilitySequence.getCapabilityvalue()),
                    id, shareValidateTime.validfrom, shareValidateTime.validthrough, IRemoteConstantDefine.DIS_ABLE_WEEK);

            byte[] passwordPacket = Tea.encryptByTea(passwordPacket0, key2, 16);
            String password0 = AES.encrypt(passwordPacket);

            map.put(id, password0);
        }

        return map;
    }

    private static ShareValidateTime getShareValidateTime(ZWaveDevice zd, Integer id, ZWaveDeviceShareService service) {
        ShareValidateTime shareValidateTime = new ShareValidateTime();
        ZWaveDeviceShare share = service.queryThirdPartShareByToUserId(zd.getZwavedeviceid(), id, DeviceShareSource.thirdpart);
        if (share != null) {
            shareValidateTime.validfrom = share.getValidfrom();
            shareValidateTime.validthrough = share.getValidthrough();
        }
        return shareValidateTime;
    }

    private static class ShareValidateTime {
        Date validfrom;
        Date validthrough;
    }

    /**
     * The phoneUserIdList will query all their blue tooth lock,
     * and create blue tooth password to the toUserIdList;
     * "create" means insert record into bluetoothpassword table
     *
     * @param phoneUserIdList
     * @param toUserIdList
     */
    public static void createBlueToothPassword(List<Integer> phoneUserIdList, List<Integer> toUserIdList) {
        RemoteService rs = new RemoteService();
        ZWaveDeviceService zds = new ZWaveDeviceService();

        List<Integer> list = remove(phoneUserIdList, toUserIdList);
        List<String> deviceList = rs.queryDeviceidListPhoneUserid(list);
        if (deviceList == null || deviceList.size() == 0) {
            return;
        }
        List<ZWaveDevice> zWaveDevices = zds.queryByDevicetype(deviceList, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK);
        createBlueToothPassword(zWaveDevices, toUserIdList, phoneUserIdList);
    }

    /**
     * The zWaveDevices will create blue tooth password to the toUserIdList;
     * "create" means insert record into bluetoothpassword table
     *
     * @param zWaveDevices
     * @param toUserIdList
     * @param blockUserList will don't create blue tooth password for their
     */
    public static void createBlueToothPassword(List<ZWaveDevice> zWaveDevices,
                                               List<Integer> toUserIdList, List<Integer> blockUserList) {
        if (zWaveDevices == null || zWaveDevices.size() == 0) {
            return;
        }
        List<ZWaveDevice> blueToothLock = filterBlueToothLock(zWaveDevices);
        Map<Integer, Map<Integer, String>> passwordPacket = PhoneUserBlueToothHelper
                .createPasswordPacket(blueToothLock, toUserIdList, blockUserList);
        if (passwordPacket == null || passwordPacket.size() == 0) {
            return;
        }
        BlueToothPasswordService service = new BlueToothPasswordService();
        for (Iterator<Map.Entry<Integer, Map<Integer, String>>> iterator = passwordPacket.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<Integer, Map<Integer, String>> entry = iterator.next();
            for (Iterator<Map.Entry<Integer, String>> iterator1 = entry.getValue().entrySet().iterator(); iterator1.hasNext(); ) {
                Map.Entry<Integer, String> entry1 = iterator1.next();
                service.save(new BlueToothPassword(entry.getKey(), entry1.getKey(), entry1.getValue()));
            }
        }
    }

    public static void createBlueToothPassword(Integer phoneUserId, List<Integer> phoneUserIdList) {
        createBlueToothPassword(Arrays.asList(phoneUserId), phoneUserIdList);
    }

    public static void createBlueToothPassword(Integer phoneUserId1, Integer phoneUserId2) {
        createBlueToothPassword(Arrays.asList(phoneUserId1), Arrays.asList(phoneUserId2));
    }

    public static void createBlueToothPassword(List<Integer> phoneUserId1, Integer phoneUserId2) {
        createBlueToothPassword(phoneUserId1, Arrays.asList(phoneUserId2));
    }

    /**
     * Change every blue tooth lock password package of the deviceList's for
     * who already log in bluetoothpassword with the device id except blockUserList.
     *
     * @param deviceList    The every device's type of deviceList should be Blue Tooth lock.
     * @param blockUserList
     */
    public static Set<Integer> modifyBlueToothDevicePassword(List<ZWaveDevice> deviceList, List<Integer> blockUserList) {
        Set<Integer> hadModifiedPhoneUserIdList = new HashSet<>();
        if (blockUserList != null) {
            hadModifiedPhoneUserIdList.addAll(blockUserList);
        }

        deviceList = filterBlueToothLock(deviceList);
        BlueToothPasswordService service = new BlueToothPasswordService();

        plusBlueToothLockSequence(deviceList);
        for (ZWaveDevice zWaveDevice : deviceList) {
            List<Integer> phoneUserId = service.findPhoneUserId(zWaveDevice.getZwavedeviceid());
            hadModifiedPhoneUserIdList.addAll(phoneUserId);
            if (blockUserList != null) {
                phoneUserId.removeAll(blockUserList);
            }
            service.deleteByZwaveDeviceId(zWaveDevice.getZwavedeviceid());
            createBlueToothPassword(Arrays.asList(zWaveDevice), phoneUserId, blockUserList);
        }

        return hadModifiedPhoneUserIdList;
    }

    /**
     * Find the phoneUserIdList's all blue tooth lock, and query all user who
     * already has blue tooth package in table bluetoothpassword for every lock.
     * and then, update blue tooth lock password  package for this except  blockUserList.
     *
     * @param phoneUserIdList
     * @param blockUserList
     */
    public static Set<Integer> modifyBlueToothPassword(List<Integer> phoneUserIdList, List<Integer> blockUserList) {
        RemoteService rs = new RemoteService();
        ZWaveDeviceService zds = new ZWaveDeviceService();

        List<Integer> list = remove(phoneUserIdList, blockUserList);
        List<String> deviceList = rs.queryDeviceidListPhoneUserid(list);
        if (deviceList == null || deviceList.size() == 0) {
            return new HashSet<>();
        }
        List<ZWaveDevice> zWaveDevices = zds.queryByDevicetype(deviceList, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK);

        return modifyBlueToothDevicePassword(zWaveDevices, blockUserList);
    }

    /**
     * Query all of the phoneUserIdList's blue tooth lock, and query all user who
     * already has blue tooth package in table bluetoothpassword for every lock.
     * and then, update blue tooth lock password  package for this except  blockUser.
     *
     * @param phoneUserIdList
     * @param blockUser
     */
    public static Set<Integer> modifyBlueToothPassword(List<Integer> phoneUserIdList, Integer blockUser) {
       return modifyBlueToothPassword(phoneUserIdList, Arrays.asList(blockUser));
    }

    public static Set<Integer> modifyFamilyShareBlueToothPassword(Integer phoneUserId, List<Integer> familyPhoneUserIdList) {
        List<Integer> list = remove(familyPhoneUserIdList, Arrays.asList(phoneUserId));
        return modifyBlueToothPassword(Arrays.asList(phoneUserId), list);
    }

    public static byte[] createBlueToothPasswordPackets(byte[] key2, byte[] key3, String macAddress, String zoneId) {
        byte[] macAddress0 = NumberUtil.hexToBytes(macAddress.replace(":", ""));

        Calendar c = Calendar.getInstance();
        TimeZoneHelper.timezoneTranslate(c, zoneId);
        int time = (int) (c.getTimeInMillis() / 1000 + 600);

        byte[] timeByte = NumberUtil.intToByte4(time);

        return createBlueToothPasswordPackets(key2, key3, macAddress0, timeByte);
    }

    public static byte[] createBlueToothPasswordPackets(byte[] key2, byte[] key3, byte[] mac, byte[] expireTime) {
        byte[] packets = new byte[43];
        System.arraycopy(key2, 0, packets, 0, 16);
        System.arraycopy(key3, 0, packets, 16, 16);
        System.arraycopy(mac, 0, packets, 32, 6);
        System.arraycopy(expireTime, 0, packets, 38, 4);
        addParityBit(packets);

        return packets;
    }

    private static<T> List<T> remove(List<T> list, List<T> tList){
        ArrayList<T> integers = new ArrayList<>();
        for (T t0 : list) {
            if (tList == null || !tList.contains(t0)) {
                integers.add(t0);
            }
        }
        return integers;
    }

    public static void main(String[] args) {
        System.out.println(TimeZoneHelper.dateToLocalDateTime(new Date()).getMinute());

        ValidTime time0 = new ValidTime(new Date());
        ValidTime time1 = new ValidTime(new Date(System.currentTimeMillis() + 61000));
        System.out.println(DataConverter.toHexString(time0.time, 8));
        System.out.println(time0.minute);
        System.out.println(DataConverter.toHexString(time1.time, 8));
        System.out.println(time1.minute);
    }
}
