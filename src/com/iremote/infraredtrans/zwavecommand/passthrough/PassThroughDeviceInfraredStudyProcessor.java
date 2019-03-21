package com.iremote.infraredtrans.zwavecommand.passthrough;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.device.passthrough.StudyInfraredRawCmdAction;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.constant.GatewayInfrareddeviceStudySteps;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;
import com.iremote.service.DeviceOperationStepsService;
import org.apache.commons.lang3.StringUtils;
import org.xsocket.DataConverter;

import java.util.Calendar;

public class PassThroughDeviceInfraredStudyProcessor extends ZWaveReportBaseProcessor {
    @Override
    protected void parseReport() {
        this.dontpusmessage();
        this.dontsavenotification();
    }

    @Override
    protected void updateDeviceStatus() {
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(zrb.getDeviceid(), DeviceOperationType.remoteInfrareddeviceKey);

        int packageIndex = zrb.getCmd()[5] & 0xff;
        appendInfraredCode(dos,packageIndex);
        changeDeviceOperationStepsStatus(dos, packageIndex);
    }

    private void appendInfraredCode(DeviceOperationSteps dos, int packageIndex) {
        byte[] infraredCodePackage = getInfraredCodePackage();
        JSONObject json = dos.getAppendmessage() == null ? new JSONObject() : JSONObject.parseObject(dos.getAppendmessage());
        json.put(String.valueOf(packageIndex), DataConverter.toHexString(infraredCodePackage, infraredCodePackage.length));
        dos.setAppendmessage(json.toJSONString());
    }

    private byte[] getInfraredCodePackage() {
        byte[] infraredCodePackage = new byte[zrb.getCmd().length - 6];
        System.arraycopy(zrb.getCmd(), 6, infraredCodePackage,0, infraredCodePackage.length);
        return infraredCodePackage;
    }

    private void changeDeviceOperationStepsStatus(DeviceOperationSteps dos, int packageIndex) {
        if (packageIndex != 0 && packageIndex != 255) {
            dos.setExpiretime(StudyInfraredRawCmdAction.offsetDate(Calendar.SECOND, 10));
            return;
        }
        if (packageIndex == 0) {
            dos.setFinished(true);
            dos.setStatus(GatewayInfrareddeviceStudySteps.finished.ordinal());
            return;
        }
        dos.setFinished(true);
        dos.setStatus(checkPackage(dos)
                ? GatewayInfrareddeviceStudySteps.finished.ordinal()
                : GatewayInfrareddeviceStudySteps.failed.ordinal());
    }

    private boolean checkPackage(DeviceOperationSteps dos) {
        if (dos.getAppendmessage() == null) {
            return false;
        }
        JSONObject json = JSONObject.parseObject(dos.getAppendmessage());
        for (int i = 1; i < json.size(); i++) {
            if (StringUtils.isBlank(json.getString(String.valueOf(i)))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getMessagetype() {
        return null;
    }
}
