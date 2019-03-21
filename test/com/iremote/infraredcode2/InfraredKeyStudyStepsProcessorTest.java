package com.iremote.infraredcode2;

import com.iremote.infraredtrans.IRemoteRequestProcessor;
import com.iremote.infraredtrans.ProcessorStore;
import com.iremote.mock.TestConnectionContext;
import com.iremote.test.db.Db;
import com.iremote.test.db.Env;

import java.io.IOException;
import java.nio.BufferOverflowException;

public class InfraredKeyStudyStepsProcessorTest {
    public static void main(String[] args) {
        Env.getInstance().need();
        Db.init();
        //126 2 2 0 12 0 1 0 2 0 0 0 31 0 2 0 5 23 126
        byte[] rq = new byte[]{126,2,2,0,12,0,1,0,2,0,0,0,31,0,2,0,5,23,126};

        IRemoteRequestProcessor pro = ProcessorStore.getInstance().getProcessor(rq , 0);
        try
        {
            pro.process(rq, new TestConnectionContext("iRemote2005000001337"));
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
