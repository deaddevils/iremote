package com.iremote.event.dsc;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.SetArmWithNoDelayEvent;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.serianet.dsc.DSCHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.ZWaveDeviceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class SetArmWithNoDelay extends SetArmWithNoDelayEvent implements ITextMessageProcessor {
    private Log log = LogFactory.getLog(SetArmWithNoDelay.class);

    @Override
    public String getTaskKey() {
        return deviceid;
    }

    @Override
    public void run() {
        execute();
    }

    private void execute() {
        ZWaveDeviceService zds = new ZWaveDeviceService();
        if (dos.getZwavedeviceid() == null) {
            log.info("not found zwavedevice");
            return;
        }
        ZWaveDevice zd = zds.query(dos.getZwavedeviceid());
        if (zd == null){
            log.info("not found zwavedevice");
            return;
        }

        CommandTlv ct = new CommandTlv(0, 0);
        ZwaveReportRequestWrap wrap6 = new ZwaveReportRequestWrap(zd.getDeviceid(), zd.getNuid(), ct, DSCHelper.toByteArray("90100032Partition " + partition.getDscpartitionid() + "     is Unavailable"), 10, 0);
        ZwaveReportRequestWrap wrap3 = new ZwaveReportRequestWrap(zd.getDeviceid(), zd.getNuid(), ct, DSCHelper.toByteArray("90100032Invalid Access  Code"), 30, 0);

        if (sendArmWithNoDelay(wrap6)){
            exit();
            changeTask(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_PARTITION_NOT_EXSIT);
            return;
        }

        sendPassword();
        exit();

        changeTask(wrap3);
    }

    private boolean sendArmWithNoDelay(ZwaveReportRequestWrap wrap) {
        sendCommand("0581");
        switchKeypadPartition();
         if(checkPartition(wrap)){
             return true;
         }
        sendCommand("070*");
        sendCommand("070^");
        sendCommand("0709");
        sendCommand("070^");
        return false;
    }

    private boolean checkPartition(ZwaveReportRequestWrap wrap) {
        byte[] response = wrap.getResponse(5);
        return response != null;
    }

    private void switchKeypadPartition() {
        sendCommand("070#", 1600);
        sendCommand("070^");
        sendCommand("070" + partition.getDscpartitionid(), 1600);
        sendCommand("070^");
    }

    private void sendPassword() {
        for (int i = 0; i < password.length(); i++) {
            sendCommand("070" + password.toCharArray()[i]);
            sendCommand("070^");
        }
    }

    private void exit() {
        sendCommand("070#");
        sendCommand("070^");
    }


    private void sendCommand(String cmd0) {
        sendCommand(cmd0, 666);
    }

    private void sendCommand(String cmd, long sleeptime) {
        CommandTlv cmd61 = CommandUtil.createDscCommand(cmd);
        try {
            SynchronizeRequestHelper.sendData(deviceid, cmd61);
            Thread.sleep(sleeptime);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void changeTask(ZwaveReportRequestWrap wrap) {
        byte[] response = wrap.getResponse(2);
        changeTask(response == null
                ? IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_SUCCESS
                : IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PASSWORD_ERROR);
    }
    private void changeTask(int status) {
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        if (dos.getExpiretime().getTime() > System.currentTimeMillis()) {
            dos.setFinished(true);
            dos.setStatus(status);
            doss.saveOrUpdate(dos);
        }
    }
}
