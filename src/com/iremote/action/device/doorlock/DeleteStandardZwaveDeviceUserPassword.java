package com.iremote.action.device.doorlock;

import com.iremote.common.ByteUtils;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.DoorlockUserService;

public class DeleteStandardZwaveDeviceUserPassword implements IDoorlockOperationProcessor, Runnable {
    public static String[] STATUS = new String[]{"", "init", "gatewayoffline", "sendingcommand", "deletefailed", "deletesuccess"};
    public static int STATUS_INIT = 1;
    public static int STATUS_OFFLINE = 2;
    public static int STATUS_SENDING_COMMAN = 3;
    public static int STATUS_FAILED = 4;
    public static int STATUS_SUCCESS = 5;
    private int status = STATUS_INIT;

    private int zwavedeviceid;
    private int usercode;
    private PhoneUser phoneUser;
    private int doorlockuserid;
    private ZWaveDevice zWaveDevice;
    private long taskid = System.currentTimeMillis();
    private long expiretime = System.currentTimeMillis() + 30 * 1000;

    @Override
    public void run() {

        byte[] readPassword = createReadPasswordCommand();
        Byte[] readPasswordResponse = createReadPasswordResponseCommand();
        CommandTlv readPasswordCommand = DoorlockPasswordHelper.createCommandTlv(readPassword, zWaveDevice.getNuid());
        ZwaveReportRequestWrap wrap = ZwaveReportRequestManager.sendRequest(zWaveDevice.getDeviceid(), zWaveDevice.getNuid(), readPasswordCommand, readPasswordResponse, 10, taskid);
        if (wrap == null || wrap.getResponse() == null) {
            status = STATUS_FAILED;
            return;
        }
        byte[] tagrst = wrap.getResponse();

        if (tagrst[3] == 0x01) {
            byte[] bytePassword = new byte[tagrst.length - 4];
            for (int i = 4; i < tagrst.length; i++) {
                bytePassword[i - 4] = tagrst[i];
            }
            byte[] deletePassword = createDeletePasswordCommand(bytePassword);
            CommandTlv deletePasswordCommand = DoorlockPasswordHelper.createCommandTlv(deletePassword, zWaveDevice.getNuid());
            byte[] bytes = SynchronizeRequestHelper.synchronizeRequest(zWaveDevice.getDeviceid(), deletePasswordCommand, 10);
            if (bytes == null) {
                status = STATUS_FAILED;
                return;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ZwaveReportRequestWrap wrap0 = ZwaveReportRequestManager.sendRequest(zWaveDevice.getDeviceid(), zWaveDevice.getNuid(), readPasswordCommand, readPasswordResponse, 10, taskid);
            if (wrap0 == null || wrap0.getResponse() == null) {
                status = STATUS_FAILED;
                return;
            }
            byte[] rst = wrap0.getResponse();
            if (rst[3] == 0x00) {
                status = STATUS_SUCCESS;
                deleteDoorLockUserFromDB();
                return;
            }
        } else if (tagrst[3] == 0x00) {
            status = STATUS_SUCCESS;
            deleteDoorLockUserFromDB();
            return;
        }
        status = STATUS_FAILED;
    }

    private void deleteDoorLockUserFromDB() {
        DoorlockUserService dus = new DoorlockUserService();
        DoorlockUser du = dus.query(doorlockuserid);
        if (du != null) {
            dus.delete(du);
        }
    }

    @Override
    public int getStatus() {
        if (status == STATUS_SENDING_COMMAN) {
            if (System.currentTimeMillis() > expiretime) {
                return STATUS_FAILED;
            }
        }
        return status;
    }

    @Override
    public String getMessage() {
        return STATUS[getStatus()];
    }

    @Override
    public void reportArrive(String deviceid, int nuid, byte[] report) {

    }

    @Override
    public boolean isFinished() {
        return (status == STATUS_OFFLINE || status == STATUS_FAILED || status == STATUS_SUCCESS);
    }

    @Override
    public void init() {
        if (zWaveDevice.getDeviceid() == null || !ConnectionManager.contants(zWaveDevice.getDeviceid())) {
            status = STATUS_OFFLINE;
            return;
        }

        DoorlockOperationStore.getInstance().put(String.valueOf(zwavedeviceid), this);
    }

    @Override
    public void sendcommand() {
        status = STATUS_SENDING_COMMAN;
        ScheduleManager.excutein(0, this);
    }

    @Override
    public void onGatewayOnline() {

    }

    @Override
    public void onGatewayOffline() {

    }

    @Override
    public long getExpireTime() {
        return 0;
    }

    private byte[] createReadPasswordCommand() {
        byte[] bytes = {0x63, 0x02, (byte) usercode};
        return bytes;
    }

    private Byte[] createReadPasswordResponseCommand() {
        Byte[] bytes = {0x63, 0x03, (byte) usercode};
        return bytes;
    }

    private byte[] createDeletePasswordCommand(byte[] bytePassword) {
        byte[] bytes = {0x63, 0x01, (byte) usercode, 0x00};
        byte[] bytes1 = ByteUtils.addBytes(bytes, bytePassword);
        return bytes1;
    }

    public void setZwavedeviceid(int zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public void setUsercode(int usercode) {
        this.usercode = usercode;
    }

    public void setPhoneUser(PhoneUser phoneUser) {
        this.phoneUser = phoneUser;
    }

    public void setzWaveDevice(ZWaveDevice zWaveDevice) {
        this.zWaveDevice = zWaveDevice;
    }

    public long getExpiretime() {
        return expiretime;
    }

    public void setDoorlockuserid(int doorlockuserid) {
        this.doorlockuserid = doorlockuserid;
    }
}
