package com.iremote.event.dsc;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ChannelEnableEvent;
import com.iremote.common.jms.vo.SubdeviceStatusEvent;
import com.iremote.domain.*;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.serianet.dsc.DSCHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Date;

public class SetChannelEnableStatusTask extends ChannelEnableEvent implements ITextMessageProcessor {

    private int count = 0;
    private static Log log = LogFactory.getLog(SetChannelEnableStatusTask.class);

    @Override
    public String getTaskKey() {
        return super.getDeviceid();
    }

    @Override
    public void run() {
        execute();
    }

    public void execute() {
        String channelString = getChannelString();

        ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
        ZWaveSubDevice zWaveSubDevice = zsds.queryByZwavedeviceidAndChannelid(zd.getZwavedeviceid(), channelid);
        CommandTlv ct = new CommandTlv(0, 0);

//        if (restorePartitionArmStatus(zWaveSubDevice)) {
//            return;
//        }
        if (isPartitionArm(zWaveSubDevice)) {
            createResult(ErrorCodeDefine.PARTITION_BUSY);
            return;
        }

        enableVirtualKeypad();

        ZwaveReportRequestWrap wrap6 = new ZwaveReportRequestWrap(zd.getDeviceid(), zd.getNuid(), ct, DSCHelper.toByteArray("90100032Partition " + zWaveSubDevice.getPartition().getDscpartitionid() + "     is Unavailable"), 8, 0);

        switchKeypadPartition(zWaveSubDevice);

        if (isPartitionUnavailable(wrap6)){
            return;
        }

        ZwaveReportRequestWrap wrap1 = new ZwaveReportRequestWrap(zd.getDeviceid(), zd.getNuid(), ct, DSCHelper.toByteArray("90100032Enter Your      Access Code"), 12, 0);
        ZwaveReportRequestWrap wrap2 = new ZwaveReportRequestWrap(zd.getDeviceid(), zd.getNuid(), ct, DSCHelper.toByteArray("90100032Zone    "), 60, 0);
        ZwaveReportRequestWrap wrap3 = new ZwaveReportRequestWrap(zd.getDeviceid(), zd.getNuid(), ct, DSCHelper.toByteArray("90100032Invalid Access  Code"), 30, 0);
        ZwaveReportRequestWrap wrap4 = new ZwaveReportRequestWrap(zd.getDeviceid(), zd.getNuid(), ct, DSCHelper.toByteArray("90134"), 60, 0);
        ZwaveReportRequestWrap wrap5 = new ZwaveReportRequestWrap(zd.getDeviceid(), zd.getNuid(), ct, DSCHelper.toByteArray("670" + zWaveSubDevice.getPartition().getDscpartitionid()), 30, 0);

        enterBypassMenu();

        sendPassword(wrap1);
        sendChannel(channelString);
        byte[] response = wrap2.getResponse(5);
        if (response == null) {
            if (wrap4.getResponse(3) != null && wrap4.getResponse(3)[5] != 50) {
                String rst903 = new String(wrap4.getResponse());
                Integer status = Integer.valueOf(rst903.substring(4, 5));
                response = new byte[]{57, 48, 51, 52, 48, 66, 48, 48, 48, 48};
                if (status == 1) {
                    response[5] = 32;
                }
            } else {
                byte[] passwordwrong = wrap3.getResponse(3);
                byte[] passwordwrong1 = wrap5.getResponse(1);
                if (passwordwrong != null || passwordwrong1 != null) {
                    createResult(ErrorCodeDefine.DSC_PASSWORD_ERROR);
                    return;
                }
                createResult(ErrorCodeDefine.TIME_OUT);
                return;
            }

        }
        String rs = new String(response);
        if (StringUtils.isBlank(rs)) {
            createResult(ErrorCodeDefine.TIME_OUT);
            return;
        }
        String statutChar = rs.substring(rs.length() - 5, rs.length() - 4);
        int nowstatus;
        if ("B".equals(statutChar)) {
            log.info("unbypass");
            nowstatus = 0;
        } else {
            log.info("bypass");
            nowstatus = 1;
        }
        if (nowstatus != enablestatus && count < 1) {
            count++;
            exit();
            delayTaskExpiretime(50 * 1000);
            execute();
        } else if (nowstatus != enablestatus && count >= 1) {
            createResult(12);
        } else {
            DeviceOperationSteps dos = createSuccess(zWaveSubDevice);
            pushResult(zWaveSubDevice);
            writeLog(zWaveSubDevice, dos);
        }
    }

    private boolean isPartitionUnavailable(ZwaveReportRequestWrap wrap6) {
        byte[] rsp6 = wrap6.getResponse(1);
        if (rsp6 != null){
            createResult(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_PARTITION_NOT_EXSIT);
            return true;
        }
        return false;
    }

    private void switchKeypadPartition(ZWaveSubDevice zWaveSubDevice) {
        int dscpartitionid = zWaveSubDevice.getPartition().getDscpartitionid();
        sendCommand("070#", 2000);
        sendCommand("070^");
        sendCommand("070" + dscpartitionid, 4000);
        sendCommand("070^");
    }

    private void writeLog(ZWaveSubDevice zsd, DeviceOperationSteps dos) {
        JSONObject json = new JSONObject();
        json.put("channelid", zsd.getChannelid());
        json.put("channelname", zsd.getName());
        Notification notification = new Notification();
        String jsonmsg = dos.getAppendmessage();
        if (jsonmsg != null) {
            String phonenumber = JSONObject.parseObject(jsonmsg).getString("phonenumber");
            notification.setAppendmessage(phonenumber);
        }
        notification.setMessage(getMessageType());
        notification.setDeviceid(deviceid);
        notification.setReporttime(new Date());
        notification.setZwavedeviceid(zd.getZwavedeviceid());
        notification.setNuid(zd.getNuid());
        notification.setMajortype(zd.getMajortype());
        notification.setDevicetype(zd.getDevicetype());
        notification.setPhoneuserid(getPhoneUser().getPhoneuserid());
        notification.setName(zd.getName());
        notification.setAppendjson(json);

        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

    private String getMessageType() {
        return enablestatus == IRemoteConstantDefine.DSC_CHANNEL_READY ?
                IRemoteConstantDefine.WARNING_TYPE_DEVICE_ENABLE : IRemoteConstantDefine.WARNING_TYPE_DEVICE_DISABLE;
    }

    private void pushResult(ZWaveSubDevice zsd) {
        SubdeviceStatusEvent sse = new SubdeviceStatusEvent();
        sse.setChannelid(channelid);
        sse.setDeviceid(deviceid);
        sse.setEnablestatus(enablestatus);
        sse.setEventtime(new Date());
        sse.setSubdeviceid(zsd.getZwavesubdeviceid());
        sse.setType(IRemoteConstantDefine.NOTIFICATION_SUB_DEVICE_STATUS);
        sse.setZwavedeviceid(zsd.getZwavedevice().getZwavedeviceid());

        JMSUtil.sendmessage(IRemoteConstantDefine.EVNET_PUSH_SUB_ZWAVEDEVICE_STATUS, sse);
    }

    private boolean isPartitionArm(ZWaveSubDevice zWaveSubDevice) {
        return zWaveSubDevice.getPartition().getArmstatus() != IRemoteConstantDefine.DSC_TO_ARM_STATUS_DIS_ARM;
    }

    private void sendPassword(ZwaveReportRequestWrap wrap1) {
        byte[] needCodeCmd = wrap1.getResponse(5);
        if (needCodeCmd != null) {
            for (int i = 0; i < password.length(); i++) {
                sendCommand("070" + password.toCharArray()[i]);
                sendCommand("070^");
            }
        }
    }

    private void enableVirtualKeypad() {
        sendCommand("0581");
    }

    private void enterBypassMenu() {
        sendCommand("070*");
        sendCommand("070^");
        sendCommand("0701");
        sendCommand("070^");
    }

    private void sendChannel(String channelString) {
        sendCommand("070" + channelString.substring(0, 1));
        sendCommand("070^");
        sendCommand("070" + channelString.substring(1));
        sendCommand("070^");
    }

    private void exit() {
        sendCommand("070#");
        sendCommand("070^");
    }

    private String getChannelString() {
        return channelid < 10 ? "0" + channelid : String.valueOf(channelid);
    }

    private void sendCommand(String cmd0) {
        sendCommand(cmd0, 666);
    }

    private void sendCommand(String cmd0, long sleeptime) {
        CommandTlv cmd61 = CommandUtil.createDscCommand(cmd0);
        try {
            SynchronizeRequestHelper.sendData(zd.getDeviceid(), cmd61);
            Thread.sleep(sleeptime);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean restorePartitionArmStatus(ZWaveSubDevice zWaveSubDevice) {
        if (zWaveSubDevice.getPartition().getArmstatus() != IRemoteConstantDefine.DSC_TO_ARM_STATUS_DIS_ARM) {
            String password0;
            password0 = (password.length() == 4) ? (password + "00") : password;
            StringBuilder sb = new StringBuilder();
            ZwaveReportRequestWrap wrap10 = ZwaveReportRequestManager.sendRequest(zd.getDeviceid(), zd.getNuid(),
                    CommandUtil.createDscCommand(sb.append("040").append(zWaveSubDevice.getPartition().getDscpartitionid()).append(password0).toString()),
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

    private DeviceOperationSteps createSuccess(ZWaveSubDevice zWaveSubDevice) {
        DeviceOperationSteps result = createResult(ErrorCodeDefine.SUCCESS);
        zWaveSubDevice.setEnablestatus(enablestatus);
        return result;
    }

    private DeviceOperationSteps createResult(int code) {
        log.info("result code is " + code);
        exit();
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(zd.getDeviceid(), DeviceOperationType.setbypass);
        if (dos == null || dos.getExpiretime().getTime() < System.currentTimeMillis()) {
            return null;
        }
        dos.setStatus(getStatus(code));
        dos.setFinished(true);
        return dos;
    }

    private DeviceOperationSteps delayTaskExpiretime(long time) {
        log.info("delay Task Expiretime");
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(zd.getDeviceid(), DeviceOperationType.setbypass);
        if (dos == null || dos.getExpiretime().getTime() < System.currentTimeMillis()) {
            return null;
        }
        dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_IN_PROCESSOR);
        long time0 = dos.getExpiretime().getTime() + time;
        dos.setExpiretime(new Date(time0));
        dos.setFinished(true);
        return dos;
    }

    private int getStatus(int code) {
        switch (code) {
            case ErrorCodeDefine.TIME_OUT:
                return 13;
            case ErrorCodeDefine.PARTITION_BUSY:
                return 15;
            case ErrorCodeDefine.PASSWORD_REQUIRED:
                return 14;
            case IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PARTITION_NOT_READY:
                return 11;
            case ErrorCodeDefine.DSC_PASSWORD_ERROR:
                return 10;
            case ErrorCodeDefine.SUCCESS:
                return 2;
            case IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_PARTITION_NOT_EXSIT:
                return 16;
            default:
                return 12;
        }
    }

    protected PhoneUser getPhoneUser() {
        RemoteService rs = new RemoteService();
        Integer phoneuserid = rs.queryOwnerId(deviceid);
        if (phoneuserid == null) {
            return null;
        }
        PhoneUserService pus = new PhoneUserService();
        PhoneUser phoneuser = pus.query(phoneuserid);
        if (phoneuser == null) {
            return null;
        }
        return phoneuser;
    }
}
