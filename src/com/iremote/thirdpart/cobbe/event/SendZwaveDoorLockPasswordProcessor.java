package com.iremote.thirdpart.cobbe.event;

import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

import java.util.Calendar;
import java.util.List;

public class SendZwaveDoorLockPasswordProcessor implements Runnable {
    @Override
    public void run() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour >= 20 || hour <= 8) {
            return;
        }

        List<Integer> doorlockPasswordIdList = new DoorlockPasswordService().listUnsynchronizedZwaveDoorLockPassword();

        if (doorlockPasswordIdList == null || doorlockPasswordIdList.size() == 0) {
            return;
        }
        
        HibernateUtil.commit();

        SendDoorLockPasswordForZufangHelper zdlpfzh = new SendDoorLockPasswordForZufangHelper(true);
        zdlpfzh.sendPasswordForZwaveDoorLock(doorlockPasswordIdList);
    }
}
