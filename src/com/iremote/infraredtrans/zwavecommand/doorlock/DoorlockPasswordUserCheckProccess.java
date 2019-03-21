package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.action.device.doorlock.DoorlockOperationStore;
import com.iremote.action.device.doorlock.IDoorlockOperationProcessor;
import com.iremote.action.device.doorlock.ZwaveDoorlockPasswordSetter;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.message.MessageManager;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;
import com.iremote.infraredtrans.zwavecommand.ZwaveCommandProcessorStore;
import com.iremote.service.DoorlockUserService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class DoorlockPasswordUserCheckProccess extends ZWaveReportBaseProcessor{
    private static Log log = LogFactory.getLog(DoorlockPasswordUserCheckProccess.class);

    @Override
    protected void updateDeviceStatus() {
        byte[] report = zrb.getCmd();
        int usercode = report[2] & 0xff;
        log.info("usercode is " + usercode);

        IDoorlockOperationProcessor processor = DoorlockOperationStore.getInstance().get(String.valueOf(zrb.getDevice().getZwavedeviceid()));
        if ((processor instanceof ZwaveDoorlockPasswordSetter) && !((ZwaveDoorlockPasswordSetter) processor).isSented()) {
            DoorlockUserService dus = new DoorlockUserService();
            List<Integer> list = dus.queryUsercode(zrb.getDevice().getZwavedeviceid());

            if (!list.contains(usercode)){
                String userName = getUserName(usercode);
                ScheduleManager.excutein(5, new CreateDoorlockUserinneedTask(zrb.getDevice().getZwavedeviceid(), 21, usercode , userName));
            }
        }
    }

    private String getUserName(int usercode){
        String username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_PASSWORD_USER, 0, getLanguange());
        return username + usercode;
    }

    private String getLanguange() {
        RemoteService rs = new RemoteService();
        PhoneUserService pus = new PhoneUserService();

        Integer phoneuserid = rs.queryOwnerId(zrb.getDeviceid());

        return PhoneUserHelper.getLanguange(pus.query(phoneuserid));

    }

    @Override
    public String getMessagetype() {
        return null;
    }
}
