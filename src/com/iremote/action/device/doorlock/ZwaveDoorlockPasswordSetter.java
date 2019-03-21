package com.iremote.action.device.doorlock;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.common.message.MessageManager;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.doorlock.CreateDoorlockUserinneedTask;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.DoorlockUserService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

public class ZwaveDoorlockPasswordSetter implements IDoorlockOperationProcessor, Runnable {
    private static Log log = LogFactory.getLog(ZwaveDoorlockPasswordSetter.class);

    private static String[] STATUS = new String[]{"", "init", "gatewayoffline", "sendingpassword", "failed", "success", "devicebussing"};
    public static int STATUS_INIT = 1;
    private static int STATUS_OFFLINE = 2;
    public static int STATUS_SENDING_PASSWORD = 3;
    public static int STATUS_FAILED = 4;
    public static int STATUS_SUCCESS = 5;
    private static int STATUS_BUSY = 6;
    private String deviceid;
    private int nuid;
    private int zwavedeviceid;
    private String password;
    private boolean sented = false;
    private String username;

    private long taskid = System.currentTimeMillis();
    private long expiretime = System.currentTimeMillis() + 30 * 1000;
    private int status = STATUS_INIT;
    private byte usercode;

    public static byte generateUsercode(int zwavedeviceid, int index) {
        DoorlockUserService dlus = new DoorlockUserService();
        List<Integer> usercodeList = dlus.queryUsercode(zwavedeviceid);

        for (; index < usercodeList.size(); index++) {
            if (usercodeList.get(index) != index + 1) {
                return (byte) (index + 1);
            }
        }
        return (byte) (index + 1);
    }

    @Override
    public void run() {
        if (!StringUtils.isNumeric(password)) {
            status = STATUS_FAILED;
            return;
        }
        if (deviceid == null || !ConnectionManager.contants(deviceid)) {
            status = STATUS_OFFLINE;
            return;
        }

        usercode = generateUsercode(zwavedeviceid, 0);
        while ((usercode & 0xff) < 251) {
            ZwaveReportRequestWrap wrap = getZwaveReportRequestWrap(taskid, usercode);
            if (wrap == null) {
                return;
            }
            Utils.print("responseValue:", wrap.getResponse());
            byte[] tagrst = wrap.getResponse();
            if (tagrst[3] == 0xfe) {
                status = STATUS_FAILED;
                return;
            } else if (tagrst[3] == 0x01) {
                log.info("usercode" + usercode + " exist, find next");
                usercode = generateUsercode(zwavedeviceid, usercode);
            } else {
                break;
            }
        }

        if (usercode > 0xff) {
            status = STATUS_FAILED;
        }

        byte[] setLockUserPasswordCommnad = DoorlockPasswordHelper.createZwaveSetLockUserPasswordCommnad(password, usercode);
        CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(setLockUserPasswordCommnad, nuid);
        byte[] bytes = SynchronizeRequestHelper.synchronizeRequest(deviceid, ct, 10);
        if (bytes == null) {
            status = STATUS_FAILED;
            return;
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sented = true;
        ZwaveReportRequestWrap wrap = getZwaveReportRequestWrap(taskid, usercode);
        if (wrap == null) {
            return;
        }
        byte[] tagrst = wrap.getResponse();

        if (tagrst[3] == 0x01) {
            byte[] bytes1 = new byte[tagrst.length - 4];
            for (int i = 4; i < tagrst.length; i++) {
                bytes1[i - 4] = tagrst[i];
            }
            String password0 = new String(bytes1);
            if (password0.equals(password)) {
                status = STATUS_SUCCESS;
                addDoorLockUser(usercode);
                sendToThirdPart();
                return;
            }
        }
        status = STATUS_FAILED;
    }
    private void sendToThirdPart() {
		JMSUtil.sendmessage(IRemoteConstantDefine.ADD_LOCK_USER_RESULT, createZwaveDeviceEvent());
	}

	private ZWaveDeviceEvent createZwaveDeviceEvent() {
		ZWaveDeviceEvent zde = new ZWaveDeviceEvent();
		zde.setZwavedeviceid(zwavedeviceid);
		zde.setDeviceid(deviceid);
		zde.setEventtime(new Date());
		zde.setEventtype(IRemoteConstantDefine.ADD_LOCK_USER_RESULT);
		zde.setAppendmessage(createAppendMessage());
		zde.setName(username);
		zde.setUsertype(IRemoteConstantDefine.DOOR_LOCK_USER_PASSWORD);
		return zde;
	}

	private JSONObject createAppendMessage() {
		JSONObject json = new JSONObject();
		json.put("resultCode", IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS);
		if (status == STATUS_SUCCESS) {
			json.put("userCode", (usercode & 0xff));
		}
		return json;
	}
    private void addDoorLockUser(byte usercode) {
        int intusercode = usercode & 0xff;
        if (username == null) {
            username = getUserName(intusercode);
        }

        ScheduleManager.excutein(0, new CreateDoorlockUserinneedTask(zwavedeviceid, 21, usercode , username));
    }

    private ZwaveReportRequestWrap getZwaveReportRequestWrap(long taskid, byte usercode) {
        byte[] readPasswordCommand = new byte[]{0x63, 0x02, usercode};
        Byte[] readPasswordResponse = new Byte[]{0x63, 0x03, usercode};
        CommandTlv readPasswordCTlv = DoorlockPasswordHelper.createCommandTlv(readPasswordCommand, nuid);

        ZwaveReportRequestWrap wrap = ZwaveReportRequestManager.sendRequest(deviceid, nuid, readPasswordCTlv, readPasswordResponse, 10, taskid);
        if (wrap == null) {
            status = STATUS_FAILED;
            return null;
        }

        if (wrap.getResponse() == null) {
            status = STATUS_FAILED;
            return null;
        }
        return wrap;
    }

    @Override
    public int getStatus() {
        if (status == STATUS_SENDING_PASSWORD) {
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
        return (status == STATUS_OFFLINE || status == STATUS_FAILED || status == STATUS_SUCCESS || status == STATUS_BUSY);
    }

    @Override
    public void init() {
        if (deviceid == null || !ConnectionManager.contants(deviceid)) {
            status = STATUS_OFFLINE;
            return;
        }

        DoorlockOperationStore.getInstance().put(String.valueOf(zwavedeviceid), this);
    }

    @Override
    public void sendcommand() {
        status = STATUS_SENDING_PASSWORD;
//        new Thread(this).start();
        ScheduleManager.excutein(0,this);
    }

    private String getUserName(int usercode){
        String username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_PASSWORD_USER, 0, getLanguange());
        return username + usercode;
    }

    private String getLanguange() {
        RemoteService rs = new RemoteService();
        PhoneUserService pus = new PhoneUserService();

        Integer phoneuserid = rs.queryOwnerId(deviceid);

        return PhoneUserHelper.getLanguange(pus.query(phoneuserid));
    }

    @Override
    public void onGatewayOnline() {

    }

    @Override
    public void onGatewayOffline() {

    }

    @Override
    public long getExpireTime() {
        return expiretime;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public int getNuid() {
        return nuid;
    }

    public void setNuid(int nuid) {
        this.nuid = nuid;
    }

    public int getZwavedeviceid() {
        return zwavedeviceid;
    }

    public void setZwavedeviceid(int zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public boolean isSented() {
        return sented;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
