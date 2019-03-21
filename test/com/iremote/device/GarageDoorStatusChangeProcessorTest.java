package com.iremote.device;

import com.iremote.infraredtrans.IRemoteRequestProcessor;
import com.iremote.infraredtrans.ProcessorStore;
import com.iremote.mock.TestConnectionContext;
import com.iremote.test.db.Db;

import java.io.IOException;
import java.nio.BufferOverflowException;

public class GarageDoorStatusChangeProcessorTest {
    public static void main(String[] args) {
        Db.init();
        byte[] rq = new byte[]{30,9,0,39,0,71,0,1,41,0,70,0,3,102,3, (byte) 255,0,31,0,2,0,100,0,104,0,4,91, (byte) 200,47, (byte) 191,0,79,0,1,1,0,25,0,4,112, (byte) 220,1,40,65};

        IRemoteRequestProcessor pro = ProcessorStore.getInstance().getProcessor(rq , 0);
        try
        {
            pro.process(rq, new TestConnectionContext("iRemote6005000000005"));
        } catch (BufferOverflowException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        Db.commit();
    }
}
