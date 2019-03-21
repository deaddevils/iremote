package cn.com.isurpass.iremote.manage;

import com.alibaba.fastjson.JSONArray;
import com.google.common.io.Files;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.GatewayCapability;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.GatewayReportHelper;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class GenerateDeviceAction {
    private static Log log = LogFactory.getLog(GenerateDeviceAction.class);

    private int resultCode = ErrorCodeDefine.SUCCESS;
    private String message = "success";
    private String gateWayCapabilityCodeList;
    private String deviceCapabilityCodeList;
    private File deviceIdListFile;
    private String deviceid;
    private String devicetype;
    private int[] remoteTypeMapping;

    {
        remoteTypeMapping = new int[61];
        //IRemoteConstantDefine.DEVICE_TYPE_DRESS_HELPER = "60";
        remoteTypeMapping[60] = IRemoteConstantDefine.IREMOTE_TYPE_DRESS_HELPER;
    }

    public String execute() {
        if (devicetype == null) {
            message = "设备类型不能为空";
            return Action.SUCCESS;
        }

        Set<String> deviceIdList = getDataFromFile();
        if (StringUtils.isNotBlank(deviceid)) {
            deviceIdList.add(deviceid);
        }

        if (deviceIdList.size() == 0) {
            message = "网关 ID 不能为空";
            return Action.SUCCESS;
        }
        for (String deviceId : deviceIdList) {
            Remote remote = createGateway(deviceId);
            createZWaveDevice(remote);
        }
        return Action.SUCCESS;
    }

    private HashSet<String> getDataFromFile(){
        List<String> fileString;
        HashSet<String> list = new HashSet<>();

        if (deviceIdListFile == null) {
            return list;
        }

        try {
            fileString = readFile(deviceIdListFile);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return list;
        }

        if (fileString == null || fileString.size() == 0) {
            return list;
        }

        for (String line : fileString) {
            String[] columns = line.split(",");
            if (columns == null){
                continue;
            }
            for (String column : columns) {
                list.add(column);
            }
        }
        return list;
    }

    private List<String> readFile(File file) throws IOException {
        return Files.readLines(file, Charset.forName("utf-8"));
    }

    private void createZWaveDevice(Remote remote){
        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zWaveDevices = zds.querybydeviceidtype(remote.getDeviceid(), devicetype);
        ZWaveDevice zd;
        if (zWaveDevices == null || zWaveDevices.size() == 0) {
            zd = create(remote);
        } else {
            zd = zWaveDevices.get(0);
        }

        createDeviceCapability(zd);

        zds.saveOrUpdate(zd);
    }

    private void createDeviceCapability(ZWaveDevice zwavedevice) {
        if (zwavedevice.getCapability() != null) {
            zwavedevice.getCapability().clear();
        } else {
            zwavedevice.setCapability(new ArrayList<>());
        }

        for (Integer code : transformCapability(deviceCapabilityCodeList)) {
            zwavedevice.getCapability().add(new DeviceCapability(zwavedevice, code));
        }
    }

    private ZWaveDevice create(Remote remote) {
        ZWaveDevice zwavedevice = new ZWaveDevice();
        zwavedevice.setApplianceid(Utils.createtoken());
        zwavedevice.setBattery(100);
        zwavedevice.setDeviceid(remote.getDeviceid());
        zwavedevice.setNuid(IRemoteConstantDefine.DEVICE_NUID_WIFI_LOCK);
        zwavedevice.setDevicetype(devicetype);
        zwavedevice.setEnablestatus(IRemoteConstantDefine.DEVICE_ENABLE_STATUS_ENABLE);
        zwavedevice.setName(getZWaveDeviceName(remote));
        zwavedevice.setStatus(Utils.getDeviceDefaultStatus(zwavedevice.getDevicetype()));

        return zwavedevice;
    }

    private String getZWaveDeviceName(Remote remote) {
        String deviceName = MessageManager.getmessage("defaultname_" + devicetype, Utils.getRemotePlatform(remote.getDeviceid()), IRemoteConstantDefine.DEFAULT_LANGUAGE);
        if (StringUtils.isNotBlank(deviceName)) {
            return deviceName;
        }
        return StringUtils.isNotBlank(remote.getName())
                ? remote.getName()
                : remote.getDeviceid().substring(remote.getDeviceid().length() - 6);
    }

    private Remote createGateway(String deviceId){
        RemoteService rs = new RemoteService();
        Remote remote = rs.querybyDeviceid(deviceId);
        if (remote == null) {
            remote = GatewayReportHelper.createRemote(deviceId);
            remote.setVersion("1.1.2");
            remote.setIversion(12);
        }
        addGatewayCapability(remote);
        remote.setLastupdatetime(new Date());
        remote.setRemotetype(remoteTypeMapping[NumberUtils.toInt(devicetype)]);
        if (remote.getSsid() == null) {
            remote.setSsid("");
        }
        remote.setStatus(1);

        rs.saveOrUpdate(remote);
        return remote;
    }

    private void addGatewayCapability(Remote remote) {
        if (remote.getCapability() != null) {
            remote.getCapability().clear();
        } else {
            remote.setCapability(new ArrayList<>());
        }

        List<Integer> gatewayCapabilities = transformCapability(gateWayCapabilityCodeList);
        for (Integer code : gatewayCapabilities) {
            remote.getCapability().add(new GatewayCapability(remote, code));
        }
    }


    private List<Integer> transformCapability(String str) {
        return JSONArray.parseArray("[" + (StringUtils.isBlank(str) ? "" : str) + "]", Integer.class);
    }

    public void setGateWayCapabilityCodeList(String gateWayCapabilityCodeList) {
        this.gateWayCapabilityCodeList = gateWayCapabilityCodeList;
    }

    public void setDeviceCapabilityCodeList(String deviceCapabilityCodeList) {
        this.deviceCapabilityCodeList = deviceCapabilityCodeList;
    }

    public void setDeviceIdListFile(File deviceIdListFile) {
        this.deviceIdListFile = deviceIdListFile;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }
}
