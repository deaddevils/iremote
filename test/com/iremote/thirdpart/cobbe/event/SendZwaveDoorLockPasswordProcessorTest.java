package com.iremote.thirdpart.cobbe.event;

import com.iremote.test.db.Db;

public class SendZwaveDoorLockPasswordProcessorTest {
    public static void main(String[] args) {
        Db.init();

        SendZwaveDoorLockPasswordProcessor szdlpp = new SendZwaveDoorLockPasswordProcessor();
        szdlpp.run();

        Db.commit();
    }
}
