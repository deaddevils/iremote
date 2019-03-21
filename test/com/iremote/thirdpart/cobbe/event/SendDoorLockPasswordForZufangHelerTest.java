package com.iremote.thirdpart.cobbe.event;

import com.iremote.common.encrypt.AES;
import com.iremote.test.db.Db;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

import java.util.Date;
import java.util.List;

public class SendDoorLockPasswordForZufangHelerTest {
    public static void main(String[] args) throws Exception {
        /*String s = AES.encrypt2Str("123321");
        System.out.println(s);*/
        Db.init();

//        testProcess();
        DoorlockPasswordService service = new DoorlockPasswordService();
        List<Integer> list = service.listUnsynchronizedZwaveDoorLockPassword();


        Db.commit();
    }

    protected static void testProcess() {
        String deviceid = "iRemote8005000000041";
        Date date = new Date();
        long taskid = System.currentTimeMillis();

        SendDoorLockPasswordForZufangHelper sdlpfzh = new SendDoorLockPasswordForZufangHelper(deviceid, date, taskid);
        sdlpfzh.run();
    }
}
