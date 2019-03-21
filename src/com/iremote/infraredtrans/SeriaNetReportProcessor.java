package com.iremote.infraredtrans;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.serianet.SeriaNetCommandProcessorStore;
import com.iremote.infraredtrans.serianet.dsc.DSCCommandProcessorStore;
import com.iremote.infraredtrans.tlv.CommandTlv;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.BufferOverflowException;

public class SeriaNetReportProcessor implements IRemoteRequestProcessor {
    private static Log log = LogFactory.getLog(SeriaNetReportProcessor.class);

    @Override
    public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException {
//        String deviceid = nbc.getDeviceid();
        log.info("Seria Net Report");
        IRemoteRequestProcessor processor = SeriaNetCommandProcessorStore.getInstance().getProcessor(7, IRemoteConstantDefine.DEVICE_TYPE_DSC);
        if (processor == null) {
            log.info("Processor is null ");
        }
        processor.process(request, nbc);
        return null;
    }
}
