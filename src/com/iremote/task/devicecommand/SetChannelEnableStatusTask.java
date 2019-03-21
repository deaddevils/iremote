package com.iremote.task.devicecommand;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.serianet.dsc.DSCHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.ZWaveSubDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class SetChannelEnableStatusTask implements Runnable {
    private Integer channelid;
    private String password;
    private int enablestatus;
    private ZWaveDevice zd;
    private int dscpartitionid;
    private static Log log = LogFactory.getLog(SetChannelEnableStatusTask.class);


    public SetChannelEnableStatusTask(Integer channelid, String password, int enablestatus, ZWaveDevice zd) {
        this.channelid = channelid;
        this.password = password;
        this.enablestatus = enablestatus;
        this.zd = zd;
    }

    @Override
    public void run() {
        log.info("Task execute");
        execute();
    }

    public void execute() {
        ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
        ZWaveSubDevice zWaveSubDevice = zsds.queryByZwavedeviceidAndChannelid(zd.getZwavedeviceid(), channelid);
        dscpartitionid = zWaveSubDevice.getPartition().getDscpartitionid();
        if (restorePartitionArmStatus(zWaveSubDevice)) {
            return;
        }
        log.info("Task 1");

        if (enableVirtualKeypad()) {
            return;
        }
        log.info("Task 2");

        String rs31 = sendKeyCode("070*");
        if (rs31 == null) {
            return;
        }
        log.info("Task 3");

        String rs41 = sendKeyCode("0701");
        if (rs41 == null) {
            return;
        }
        log.info("Task 5");

        String rs644 = bypassChannel();
        if (rs644 == null) {
            return;
        }
        log.info("Task 6");

        if (exitBypassMenu()) {
            return;
        }
        log.info("Task 7");

        int bypassResult = getBypassResult(rs644);

        changeZwaveSubDeviceEnableStatus(zsds, zWaveSubDevice, bypassResult);
        log.info("Task 8");

        changeTask(rs644);
        log.info("Task 9");

    }

    private boolean exitBypassMenu() {
        return sendCommands("070#");
    }

    private void changeZwaveSubDeviceEnableStatus(ZWaveSubDeviceService zsds, ZWaveSubDevice zWaveSubDevice, int bypassResult) {
        if (zWaveSubDevice.getEnablestatus() != bypassResult) {
            zWaveSubDevice.setEnablestatus(bypassResult);
        }
        zsds.save(zWaveSubDevice);
    }

    private String bypassChannel() {
        String channelChars = channelid < 10 ? "0" + channelid : String.valueOf(channelid);
        if (sendChannelChar0(channelChars)) {
            return null;
        }

        return sendChannelChar1(channelChars);
    }

    private boolean sendCommands(String cmd0) {
        ZwaveReportRequestWrap wrap61 = pushCommandAndGetReport(cmd0);

        if (checkResponse(wrap61)) {
            return true;
        }

        ZwaveReportRequestWrap wrap62 = pushCommandAndGetReport("070^");

        return checkResponse(wrap62);
    }

    private ZwaveReportRequestWrap pushCommandAndGetReport(String cmd0) {
        CommandTlv cmd61 = CommandUtil.createDscCommand(cmd0);
        return ZwaveReportRequestManager.sendRequest(zd.getDeviceid(), zd.getNuid(), cmd61, DSCHelper.getBypassComanndResponseKey(), IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND, 0);
    }

    private ZwaveReportRequestWrap pushCommandAndGetStatus(String cmd0) {
        CommandTlv cmd61 = CommandUtil.createDscCommand(cmd0);
        ZwaveReportRequestWrap wrap = ZwaveReportRequestManager.sendRequest(zd.getDeviceid(), zd.getNuid(), cmd61, DSCHelper.getBypassComanndResponseKey(), IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND, 0);
        ZwaveReportRequestWrap wrap65 = new ZwaveReportRequestWrap(zd.getDeviceid(), zd.getNuid(), new CommandTlv(0, 0),DSCHelper.getDscPartitionBusyOrNeedAccessCode(), 1, 0);
        byte[] rst = wrap.getResponse();
        byte[] rst1 = wrap65.getResponse();
        if (rst1 != null) {
            return wrap65;
        }
        if (rst == null) {
            return null;
        }
        String rs = new String(rst);
        if (StringUtils.isBlank(rs)) {
            return null;
        }
        return wrap;
    }

    private String sendChannelChar1(String channelChars) {
        ZwaveReportRequestWrap wrap63 = pushCommandAndGetReport("070" + channelChars.substring(1));

        if (checkResponse(wrap63)) {
            return null;
        }

        CommandTlv cmd64 = CommandUtil.createDscCommand("070^");
        ZwaveReportRequestWrap wrap64 = ZwaveReportRequestManager.sendRequest(zd.getDeviceid(), zd.getNuid(), cmd64, DSCHelper.getDscResponseKeyForBypassResult(), IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND, 0);

        byte[] rst644 = wrap64.getResponse();
        if (rst644 == null) {
            createResult(ErrorCodeDefine.TIME_OUT);
            return null;
        }
        String rs644 = new String(rst644);
        if (StringUtils.isBlank(rs644)) {
            createResult(ErrorCodeDefine.TIME_OUT);
            return null;
        }

        if (!rs644.startsWith("901")) {
            createResult(ErrorCodeDefine.TIME_OUT);
            return null;
        }
        return rs644;
    }

    private boolean checkResponse(ZwaveReportRequestWrap wrap63, String cmd) {
        if (wrap63 == null) {
            createResult(ErrorCodeDefine.TIME_OUT);
            return true;
        }
        byte[] rst63 = wrap63.getResponse();
        if (rst63 == null) {
            createResult(ErrorCodeDefine.TIME_OUT);
            return true;
        }
        String rs63 = new String(rst63);
        if (rs63.startsWith(cmd)) {
            createResult(ErrorCodeDefine.PARTITION_BUSY);
            return true;
        }
        if (rs63.startsWith("90100032Enter Your      Access Code")){
            sendPassword();
            return false;
        }
        if (StringUtils.isBlank(rs63) || !rs63.startsWith("500")) {
            createResult(ErrorCodeDefine.TIME_OUT);
            return true;
        }
        return false;
    }

    private boolean checkResponse(ZwaveReportRequestWrap wrap63) {
        return checkResponse(wrap63, "673");
    }

    private boolean sendChannelChar0(String channelChars) {
        String cmd0 = "070" + channelChars.substring(0, 1);
        return sendCommands(cmd0);
    }

    private String sendKeyCode(String cmd0) {
        ZwaveReportRequestWrap wrap40 = pushCommandAndGetReport(cmd0);

        if (checkResponse(wrap40)) {
            return null;
        }

        ZwaveReportRequestWrap wrap41 = pushCommandAndGetStatus("070^");

        return getResponse(wrap41);
    }


    private void sendPassword() {
            for (int i = 0; i < 6; i++) {
                sendCommand("070" + password.toCharArray()[i]);
                sendCommand("070^");
            }
    }

    private void sendCommand(String cmd0) {
        CommandTlv cmd61 = CommandUtil.createDscCommand(cmd0);
        try {
            SynchronizeRequestHelper.sendData(zd.getDeviceid(), cmd61);
            Thread.sleep(400);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getResponse(ZwaveReportRequestWrap wrap41, String cmd) {
        if (wrap41 == null) {
            createResult(ErrorCodeDefine.TIME_OUT);
            return null;
        }
        byte[] rst41 = wrap41.getResponse();
        if (rst41 == null) {
            createResult(ErrorCodeDefine.TIME_OUT);
            return null;
        }
        String rs41 = new String(rst41);
        if (StringUtils.isBlank(rs41)) {
            createResult(ErrorCodeDefine.TIME_OUT);
            return null;
        }
        if (rs41.startsWith(cmd)) {
            createResult(ErrorCodeDefine.TIME_OUT);
            return null;
        }
        if (rs41.startsWith("673")) {
            createResult(ErrorCodeDefine.PARTITION_BUSY);
            return null;
        }
        return rs41;
    }

    private String getResponse(ZwaveReportRequestWrap wrap){
        return getResponse(wrap, "673");
    }

    private boolean enableVirtualKeypad() {
        CommandTlv cmd21 = CommandUtil.createDscCommand("0581");
        ZwaveReportRequestWrap wrap21 = ZwaveReportRequestManager.sendRequest(zd.getDeviceid(), zd.getNuid(), cmd21, DSCHelper.getBypassComanndResponseKey(), 2, 0);

        byte[] rst21 = wrap21.getResponse();
        if (rst21 == null) {
            createResult(ErrorCodeDefine.TIME_OUT);
            return true;
        }
        String rs21 = new String(rst21);
        if (StringUtils.isBlank(rs21)) {
            createResult(ErrorCodeDefine.TIME_OUT);
            return true;
        }
        return false;
    }

    private boolean restorePartitionArmStatus(ZWaveSubDevice zWaveSubDevice) {
        if (zWaveSubDevice.getPartition().getArmstatus() != IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM) {
            StringBuilder sb = new StringBuilder();
            ZwaveReportRequestWrap wrap10 = ZwaveReportRequestManager.sendRequest(zd.getDeviceid(), zd.getNuid(),
                    CommandUtil.createDscCommand(sb.append("040").append(zWaveSubDevice.getPartition().getDscpartitionid()).append(password).toString()),
                    DSCHelper.getPartitionDisarmResponseKey(), IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND, 0);
            byte[] rst10 = wrap10.getResponse();

            if (rst10 == null) {
                createResult(ErrorCodeDefine.TIME_OUT);
                return true;
            }

            String rs10 = new String(rst10);
            if (StringUtils.isBlank(rs10)) {
                createResult(ErrorCodeDefine.TIME_OUT);
                return true;
            }

            if (!rs10.startsWith("655") && !rs10.startsWith("502023")) {
                createResult(ErrorCodeDefine.PARTITION_BUSY);
                return true;
            }
        }
        return false;
    }

    private void changeTask(String rs644) {
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(zd.getDeviceid(), DeviceOperationType.setbypass);
        if (dos == null || dos.getExpiretime().getTime() < System.currentTimeMillis()) {
            return;
        }
        dos.setStatus(getResult(rs644) ? IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_SUCCESS : IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_FAIL);
        dos.setFinished(true);
        doss.saveOrUpdate(dos);
    }

    private boolean getResult(String rs644) {
        int status = getBypassResult(rs644);
        return enablestatus == status;
    }

    private int getBypassResult(String rs644) {
        String endChar = rs644.substring(rs644.length() - 1);
        if ("B".equals(endChar)) {
            return 1;
        } else {
            return 0;
        }
    }

    private void createResult(int code) {
        log.info("result code is " + code);
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(zd.getDeviceid(), DeviceOperationType.setbypass);
        if (dos == null || dos.getExpiretime().getTime() < System.currentTimeMillis()) {
            return;
        }
        dos.setFinished(true);
        dos.setStatus(getStatus(code));
        doss.saveOrUpdate(dos);
    }

    private int getStatus(int code) {
        switch (code) {
            case ErrorCodeDefine.TIME_OUT:
                return 13;
            case ErrorCodeDefine.PARTITION_BUSY:
                return 11;
            case ErrorCodeDefine.PASSWORD_REQUIRED:
                return 14;
            case ErrorCodeDefine.DSC_PASSWORD_ERROR:
                return 10;
            case ErrorCodeDefine.SUCCESS:
                return 2;
            default:
                return 12;
        }
    }
}
