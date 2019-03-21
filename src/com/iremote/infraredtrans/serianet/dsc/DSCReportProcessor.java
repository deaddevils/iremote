package com.iremote.infraredtrans.serianet.dsc;

import com.iremote.common.TagDefine;
import com.iremote.common.taskmanager.MultiReportTaskManager;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.IRemoteRequestProcessor;
import com.iremote.infraredtrans.serianet.ISeriaNetReportProcessor;
import com.iremote.infraredtrans.serianet.SeriaNetReportBean;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.BufferOverflowException;

public class DSCReportProcessor implements IRemoteRequestProcessor {
    private static Log log = LogFactory.getLog(DSCReportProcessor.class);
    private static MultiReportTaskManager<ISeriaNetReportProcessor> taskmgr = new MultiReportTaskManager<>();

    @Override
    public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException {
        if (request.length < 3 || !isValidated(request)) {
            log.info("DSC report valid fail");
            return null;
        }

        SeriaNetReportBean snrb = new SeriaNetReportBean();
        log.info(snrb.getReportid());
        String cmd = TlvWrap.readString(request, TagDefine.TAG_DSC, TagDefine.TAG_HEAD_LENGTH);
        log.info("Receive command from "+nbc.getDeviceid()+ " : " +cmd);
        ISeriaNetReportProcessor processor = DSCCommandProcessorStore.getInstance().getProcessor(request);
        if (processor == null) {
            notifyReport(nbc.getDeviceid() , request);
            log.info("Processor is null ");
            return null;
        }
        log.info("processorClassName: "+processor.getClass().getName());
        snrb.setCmd(request);
        snrb.setDeviceid(nbc.getDeviceid());
        processor.setReport(snrb);
        taskmgr.addTask(nbc.getDeviceid(), processor);
        
        notifyReport(nbc.getDeviceid() , request);
        return null;
    }
    
	private void notifyReport(String deviceid ,byte[] request)
	{
		SeriaNetReportBean zrb = new SeriaNetReportBean();

		zrb.setCmd(request);
		zrb.setDeviceid(deviceid);

		DSCReportNotifyProcessor pro = new DSCReportNotifyProcessor();
		pro.setReport(zrb);
		log.info(zrb.getReportid());
		log.info(pro.getClass().getName());
		
		taskmgr.addTask(deviceid, pro);
	}

    private boolean isValidated(byte[] request) {
        String value = TlvWrap.readString(request, TagDefine.TAG_DSC, TagDefine.TAG_HEAD_LENGTH);
        if (value.length() < 2) {
            return false;
        }
        String valueString = value.substring(0, value.length() - 4);
        String validString = value.substring(value.length()-4,value.length() - 2);
        char[] chars = valueString.toCharArray();
        int total = 0;
        for (int i = 0; i < chars.length; i++) {
            total += (int) chars[i];
        }
        String string = Integer.toHexString(total);
        if (string.length() != 2) {
            string = string.substring(string.length() - 2);
        }
        if (string.equalsIgnoreCase(validString)) {
            return true;
        } else {
            return false;
        }
    }
}
