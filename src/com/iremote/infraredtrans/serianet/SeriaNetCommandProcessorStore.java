package com.iremote.infraredtrans.serianet;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.IRemoteRequestProcessor;
import com.iremote.infraredtrans.serianet.dsc.DSCReportProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeriaNetCommandProcessorStore {

    private static Log log = LogFactory.getLog(SeriaNetCommandProcessorStore.class);

    private static Map<String, List<Integer>> intmap = new HashMap<>();
    private static Map<Integer, Class<? extends IRemoteRequestProcessor>> clsmap = new HashMap<>();
    private final String DEFAULT_TYPE = "defaulttype";
    private static SeriaNetCommandProcessorStore instance = new SeriaNetCommandProcessorStore();
    public static SeriaNetCommandProcessorStore getInstance() {
        return instance;
    }

    protected SeriaNetCommandProcessorStore() {
        registProcessor(7, IRemoteConstantDefine.DEVICE_TYPE_DSC, DSCReportProcessor.class);
    }

    public void registProcessor(Integer capabilitycode, Class<? extends IRemoteRequestProcessor> cls) {
        registProcessor(capabilitycode, null, cls);
    }

    public void registProcessor(Integer capabilitycode, String type, Class<? extends IRemoteRequestProcessor> cls) {
        if (type == null || type.length() == 0) {
            type = DEFAULT_TYPE;
        }
        clsmap.put(capabilitycode, cls);
        List<Integer> list = intmap.get(type);
        if (list == null) {
            list = new ArrayList<>();
            intmap.put(type, list);
        }
        list.add(capabilitycode);
    }

    public IRemoteRequestProcessor getProcessor(Integer capabilitycode){
        return getProcessor(capabilitycode, DEFAULT_TYPE);
    }

    public IRemoteRequestProcessor getProcessor(Integer capabilitycode, String type) {
        List<Integer> lst = intmap.get(type);
        if (lst == null)
            return getProcessor(capabilitycode, DEFAULT_TYPE);
        for (Integer item : lst) {
            if (item.equals(capabilitycode)) {
                return createProcessor(clsmap.get(capabilitycode));
            }
        }
        if (!DEFAULT_TYPE.equals(type)) {
            return getProcessor(capabilitycode, DEFAULT_TYPE);
        } else {
            return null;
        }
    }

    private IRemoteRequestProcessor createProcessor(Class<? extends IRemoteRequestProcessor> aClass) {
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
