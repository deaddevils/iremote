package com.iremote.infraredtrans.serianet.dsc;

import com.iremote.common.TagDefine;
import com.iremote.infraredtrans.serianet.ISeriaNetReportProcessor;
import com.iremote.infraredtrans.serianet.dsc.alarm.*;
import com.iremote.infraredtrans.tlv.TlvWrap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class DSCCommandProcessorStore {
    private static Log log = LogFactory.getLog(DSCCommandProcessorStore.class);
    private static Map<String, Class<? extends ISeriaNetReportProcessor>> clsmap = new HashMap<>();
    private static DSCCommandProcessorStore instance = new DSCCommandProcessorStore();

    public static DSCCommandProcessorStore getInstance() {
        return instance;
    }

    protected DSCCommandProcessorStore() {

        registProcessor("609", DSCZoneOpenProcessor.class);
        registProcessor("610", DSCZoneRestoredProcessor.class);

        registProcessor("621", DSCFireAlarmProcessor.class);
        registProcessor("622", DSCFireAlarmRestoralProcessor.class);
        registProcessor("623", DSCAuxiliaryAlarmProcessor.class);
        registProcessor("624", DSCAuxiliaryAlarmRestoralProcessor.class);
        registProcessor("625", DSCPanicAlarmProcessor.class);
        registProcessor("626", DSCPanicAlarmRestoralProcessor.class);

        registProcessor("650", DSCPartitionReadyProcessor.class);
        registProcessor("651", DSCPartitionNotReadyProcessor.class);
        registProcessor("654", DSCPartitionInAlarmProcessor.class);

        registProcessor("656", DSCExitDelayProcessor.class);
        registProcessor("657", DSCEntryDelayProcessor.class);
        registProcessor("670", DSCInvalidAccessCodeProcessor.class);

        registProcessor("502", DSCSystemErrorProcessor.class);
        registProcessor("652", DSCPartitionArmedProcessor.class);
        registProcessor("655", DSCPartitionDisarmedProcessor.class);
        registProcessor("601", DSCZoneAlarmProcessor.class);
        registProcessor("602", DSCZoneAlarmRestoreProcessor.class);
        registProcessor("700", DSCUserClosingProcessor.class);
        registProcessor("750", DSCUserOpeningProcessor.class);
        registProcessor("900", DSCCodeRequiredProcessor.class);
    }

    public void registProcessor(String cmd, Class<? extends ISeriaNetReportProcessor> cls) {
        clsmap.put(cmd, cls);
    }

    public ISeriaNetReportProcessor getProcessor(byte[] request) {
        String cmd = getCmdHead(request);
        if (cmd != null) {
            return createProcessor(clsmap.get(cmd));
        }
        return null;
    }

    private String getCmdHead(byte[] request) {
        String cmd = TlvWrap.readString(request, TagDefine.TAG_DSC, TagDefine.TAG_HEAD_LENGTH);
        if (cmd == null || cmd.length() < 3) {
            return null;
        }
        return cmd.substring(0, 3);
    }

    private ISeriaNetReportProcessor createProcessor(Class<? extends ISeriaNetReportProcessor> aClass) {
        if (aClass == null) {
            return null;
        }
        try {
            return aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
