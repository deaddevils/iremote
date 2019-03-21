package com.iremote.action.helper;

import com.iremote.common.ByteUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.xsocket.DataConverter;

import java.util.ArrayList;
import java.util.List;

public class PassThroughDeviceCmdHelper {
    private static final int EACH_PACKAGE_MAX_LENGTH = 39;

    public static CommandTlv createCmdBytes(int nuid, byte[] cmds) {
        return createCmdBytes(nuid, cmds, null);
    }

    public static CommandTlv createCmdBytes(String deviceid, int nuid, byte[] cmds) {
        CommandTlv tlv = createCmdBytes(nuid, cmds);
        if (StringUtils.isNotBlank(deviceid)) {
            tlv.setDeviceid(deviceid);
        }
        return tlv;
    }

    public static CommandTlv createCmdBytes(int nuid, byte[] cmds, Integer cmdindex) {
        CommandTlv ct = new CommandTlv(30, 7);
        ct.addUnit(new TlvByteUnit(70, cmds));
        if (nuid <= 256) {
            ct.addUnit(new TlvIntUnit(71, nuid, 1));
        } else {
            ct.addUnit(new TlvIntUnit(71, nuid, 4));
        }
        if (cmdindex != null && (cmdindex == 1 || cmdindex == 0)) {
            ct.addUnit(new TlvByteUnit(74, new byte[]{(byte) 0x91, 0x02, 0x1c, 0x01, 0x03, (byte) cmdindex.intValue()}));
        }
        return ct;
    }

    public static byte[][] createCmdPackages(byte[] rawCmds, int cmdindex) {
        if (rawCmds == null || rawCmds.length == 0) {
            return null;
        }

        int s = rawCmds.length / EACH_PACKAGE_MAX_LENGTH;
        int y = rawCmds.length % EACH_PACKAGE_MAX_LENGTH;
        if (s >= 254) {
            return null;
        }

        if (y == 0) {
            s -= 1;
            y = EACH_PACKAGE_MAX_LENGTH;
        }
        cmdindex = (cmdindex == 0 || cmdindex == 1) ? cmdindex : 2;

        byte[][] bytes = new byte[s + 1][];
        int i;
        for (i = 0; i < s; i++) {
            byte[] headers = new byte[]{(byte) 0x91, 0x02, 0x1c, 0x01, 0x01, (byte) cmdindex, (byte) (i + 1)};
            byte[] bytes2 = new byte[EACH_PACKAGE_MAX_LENGTH];
            System.arraycopy(rawCmds, i * EACH_PACKAGE_MAX_LENGTH, bytes2, 0, EACH_PACKAGE_MAX_LENGTH);
            bytes[i] = ByteUtils.addBytes(headers, bytes2);
        }

        byte[] headers = new byte[]{(byte) 0x91, 0x02, 0x1c, 0x01, 0x01, (byte) cmdindex, (byte) (s == 0 ? 0x00 : 0xff)};
        byte[] bytes2 = new byte[y];
        System.arraycopy(rawCmds, i * EACH_PACKAGE_MAX_LENGTH, bytes2, 0, y);
        bytes[i] = ByteUtils.addBytes(headers, bytes2);

        return bytes;
    }

    public static CommandTlv createStudyInfraredCodeCommand(int nuid) {
        CommandTlv ct = new CommandTlv(30, 7);
        ct.addUnit(new TlvByteUnit(70, new byte[]{(byte) 0x91, 0x02, 0x1c, 0x01, 0x05}));
        if (nuid <= 256) {
            ct.addUnit(new TlvIntUnit(71, nuid, 1));
        } else {
            ct.addUnit(new TlvIntUnit(71, nuid, 4));
        }
        return ct;
    }

    public static List<CommandTlv> createCommandTlvList(byte[] rawCmds, int cmdindex, int nuid) {
        ArrayList<CommandTlv> list = new ArrayList<>();
        byte[][] cmdPackages = createCmdPackages(rawCmds, cmdindex);
        if (cmdPackages == null) {
            return list;
        }

        for (int i = 0; i < cmdPackages.length - 1; i++) {
            list.add(createCmdBytes(nuid, cmdPackages[i]));
        }
        list.add(createCmdBytes(nuid, cmdPackages[cmdPackages.length - 1], cmdindex));
        return list;
    }

    public static List<CommandTlv> createCommandTlvList(String rawCmdString, int cmdindex, int nuid) {
        return createCommandTlvList(JWStringUtils.hexStringtobyteArray(rawCmdString), cmdindex, nuid);
    }

    public static boolean checkCmdIndexIfPower(Integer cmdindex) {
        return (IRemoteConstantDefine.PASS_THROUGH_DEVICE_CMD_INDEX_POWER_ON == cmdindex
                || IRemoteConstantDefine.PASS_THROUGH_DEVICE_CMD_INDEX_POWER_OFF == cmdindex
                || IRemoteConstantDefine.PASS_THROUGH_DEVICE_CMD_INDEX_POWER_ON_AND_OFF == cmdindex);
    }

    public static int getCmdIndexByDeviceStatus(int cmdIndex, int status) {
        return cmdIndex != IRemoteConstantDefine.PASS_THROUGH_DEVICE_CMD_INDEX_POWER_ON_AND_OFF
                ? cmdIndex
                : (ifPassThroughDeviceON(status)
                ? IRemoteConstantDefine.PASS_THROUGH_DEVICE_CMD_INDEX_POWER_OFF
                : IRemoteConstantDefine.PASS_THROUGH_DEVICE_CMD_INDEX_POWER_ON);
    }

    public static boolean isPassThroughDevice(String deviceType) {
        return IRemoteConstantDefine.DEVICE_TYPE_PASS_THROUGH_DEVICE.equals(deviceType)
                || IRemoteConstantDefine.DEVICE_TYPE_INFRARED_PASS_THROUGH_DEVICE.equals(deviceType);
    }

    public static int getRawCmdTemplateType(String deviceType) {
        return isInfraredPassThroughDevice(deviceType)
                ? IRemoteConstantDefine.TYPE_INFRARED_OPERATE_CODE
                : IRemoteConstantDefine.TYPE_OTHER_OPERATE_CODE;
    }

    public static boolean isInfraredPassThroughDevice(String deviceType) {
        return IRemoteConstantDefine.DEVICE_TYPE_INFRARED_PASS_THROUGH_DEVICE.equals(deviceType);
    }

    public static boolean isOtherPassThroughDevice(String deviceType) {
        return IRemoteConstantDefine.DEVICE_TYPE_PASS_THROUGH_DEVICE.equals(deviceType);
    }

    private static boolean ifPassThroughDeviceON(int status) {
        return status == IRemoteConstantDefine.DEVICE_STATUS_POWER_ON;
    }

    public static void main(String[] args) {
        String s = "qwertyuiopsadfghjklmnbvcxzqwertyuioplkjhgfdfdsafasdfasdqwertyuiopsadfghjklmnbvcxzqwertyuioplkjhgfdfdsafasdfasd";

        List<CommandTlv> list = createCommandTlvList(convert(s), 3, 1000);
        System.out.println();
    }

    private static String convert(String s) {
        return DataConverter.toHexString(s.getBytes(), 1024).replace(" ", "");
    }
}
