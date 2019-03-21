package cn.com.isurpass.iremote.manage;

import com.alibaba.fastjson.JSONArray;
import com.google.common.io.Files;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.GatewayCapability;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.GatewayReportHelper;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class AddBlueToothLockInitSettingAction {
    private int resultCode;
    private File addBlueToothLockInitSettingFile;
    private String gateWayCapabilityCodeList;
    private String deviceCapabilityCodeList;
    private String productor;
    private String functionversion;
    private static final Integer TEMP_KEY_OF_DEVICE_ID = 99998;
    private static Log log = LogFactory.getLog(AddBlueToothLockInitSettingAction.class);

    public String execute(){
        if (addBlueToothLockInitSettingFile == null) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }

        ArrayList<Map<Integer, String>> data = getDataFromFile();

        Iterator<Map<Integer, String>> iterator = data.iterator();
        while (iterator.hasNext()) {
            Map<Integer, String> map = iterator.next();
            Remote gateway = createGateway(map.get(TEMP_KEY_OF_DEVICE_ID));
            createZwaveDevice(gateway, map);
        }
        return Action.SUCCESS;
    }

    private ArrayList<Map<Integer, String>> getDataFromFile(){
        List<String> fileString;
        ArrayList<Map<Integer, String>> list = new ArrayList<>();

        try {
            fileString = readFile(addBlueToothLockInitSettingFile);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return list;
        }

        if (fileString == null || fileString.size() == 0) {
            return list;
        }

        for (String line : fileString) {
            HashMap<Integer, String> map = new HashMap<>();
            String[] columns = line.split(",");
            if (columns == null || columns.length == 0 || columns.length < 6) {
                continue;
            }
            byte[] key1 = JWStringUtils.hexStringtobyteArray(columns[3]);
            byte[] key2 = JWStringUtils.hexStringtobyteArray(columns[4]);
            byte[] key3 = JWStringUtils.hexStringtobyteArray(columns[5]);
            String key1String = AES.encrypt(key1);
            String key2String = AES.encrypt(key2);
            String key3String = AES.encrypt(key3);

            map.put(TEMP_KEY_OF_DEVICE_ID, columns[0]);
            map.put(IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_BLUE_TOOTH_NAME, columns[1]);
            map.put(IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_MAC_ADDRESS, columns[2]);
            map.put(IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_1, key1String);
            map.put(IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_2, key2String);
            map.put(IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_3, key3String);
            list.add(map);
        }
        return list;
    }

    private void createZwaveDevice(Remote remote, Map<Integer, String> data){
        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zWaveDevices = zds.querybydeviceidtype(remote.getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK);
        ZWaveDevice zd;
        if (zWaveDevices == null || zWaveDevices.size() == 0) {
            zd = createBlueTooth(remote);
        } else {
            zd = zWaveDevices.get(0);
        }
        createDeviceCapability(zd, data);
        appendZwaveDeviceInfo(zd);

        zds.saveOrUpdate(zd);
    }

    private void appendZwaveDeviceInfo(ZWaveDevice zd) {
        if (StringUtils.isNotBlank(productor)) {
            zd.setProductor(productor);
        }
        if (StringUtils.isNotBlank(functionversion)) {
            zd.setFunctionversion(functionversion);
        }
    }

    private ZWaveDevice createBlueTooth(Remote remote) {
        ZWaveDevice zwavedevice = new ZWaveDevice();
        zwavedevice.setApplianceid(Utils.createtoken());
        zwavedevice.setBattery(100);
        zwavedevice.setDeviceid(remote.getDeviceid());
        zwavedevice.setNuid(IRemoteConstantDefine.DEVICE_NUID_WIFI_LOCK);
        zwavedevice.setDevicetype(IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK);
        zwavedevice.setEnablestatus(IRemoteConstantDefine.DEVICE_ENABLE_STATUS_ENABLE);

        if (remote != null && StringUtils.isNotBlank(remote.getName()))
            zwavedevice.setName(remote.getName());
        else
            zwavedevice.setName(remote.getDeviceid().substring(remote.getDeviceid().length() - 6));
        zwavedevice.setStatus(Utils.getDeviceDefaultStatus(zwavedevice.getDevicetype()));

        return zwavedevice;
    }

    private void createDeviceCapability(ZWaveDevice zwavedevice, Map<Integer, String> data) {
        if (zwavedevice.getCapability() != null) {
            zwavedevice.getCapability().clear();
        } else {
            zwavedevice.setCapability(new ArrayList<>());
        }

        for (Integer code : transform(deviceCapabilityCodeList)) {
            zwavedevice.getCapability().add(new DeviceCapability(zwavedevice, code));
        }
        
        for (Iterator<Map.Entry<Integer, String>> iterator = data.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<Integer, String> entry = iterator.next();
            if (!TEMP_KEY_OF_DEVICE_ID.equals(entry.getKey())) {
                zwavedevice.getCapability().add(new DeviceCapability(zwavedevice, entry.getKey(), entry.getValue()));

            }
        }
    }

    private Remote createGateway(String deviceId){
        RemoteService rs = new RemoteService();
        Remote remote = rs.querybyDeviceid(deviceId);
        if (remote == null) {
            remote = GatewayReportHelper.createRemote(deviceId);
            remote.setVersion("1.1.2");
            remote.setIversion(12);
        }
        addCapability(remote);
        remote.setLastupdatetime(new Date());
        remote.setRemotetype(IRemoteConstantDefine.IREMOTE_TYPE_COBBE_WIFI_LOCK);
        if (remote.getSsid() == null) {
            remote.setSsid("");
        }
        remote.setStatus(1);
        //GatewayReportHelper.ensureGatewayCapability(remote);

        rs.saveOrUpdate(remote);
        return remote;
    }

    private void addCapability(Remote remote) {
        if (remote.getCapability() != null) {
            remote.getCapability().clear();
        } else {
            remote.setCapability(new ArrayList<>());
        }

        List<Integer> codeList = transform(gateWayCapabilityCodeList);
        for (Integer code : codeList) {
            remote.getCapability().add(new GatewayCapability(remote, code));
        }
    }

    private List<Integer> transform(String capabilityCodeStrList) {
        if (StringUtils.isBlank(capabilityCodeStrList)) {
            capabilityCodeStrList = "";
        }
        return JSONArray.parseArray("[" + capabilityCodeStrList + "]", Integer.class);
    }

    public List<String> readFile(File file) throws IOException {
        return Files.readLines(file, Charset.forName("utf-8"));
    }

    public void setAddBlueToothLockInitSettingFile(File addBlueToothLockInitSettingFile) {
        this.addBlueToothLockInitSettingFile = addBlueToothLockInitSettingFile;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setGateWayCapabilityCodeList(String gateWayCapabilityCodeList) {
		this.gateWayCapabilityCodeList = gateWayCapabilityCodeList;
	}

    public void setDeviceCapabilityCodeList(String deviceCapabilityCodeList) {
        this.deviceCapabilityCodeList = deviceCapabilityCodeList;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public void setFunctionversion(String functionversion) {
        this.functionversion = functionversion;
    }

    public static void main(String[] args) {
  /*      Db.init();
//        AddBlueToothLockInitSettingAction action = new AddBlueToothLockInitSettingAction();
//        action.setAddBlueToothLockInitSettingFile(new File("D:\\123.txt"));
//        action.execute();
        ZWaveDevice zd = new ZWaveDeviceService().query(13830);
        List<DeviceCapability> list = zd.getCapability();
        list.clear();
//        zd.setCapability(new ArrayList<>());
        Db.commit();*/
    }
}
