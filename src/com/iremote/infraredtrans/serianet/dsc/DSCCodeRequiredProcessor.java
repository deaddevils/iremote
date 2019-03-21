package com.iremote.infraredtrans.serianet.dsc;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.service.DeviceOperationStepsService;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class DSCCodeRequiredProcessor extends DSCReportBaseProcessor {
    @Override
    protected void updateDeviceStatus() {
        int dscpartitionid = getValue(seriaNetReportBean.getCmd(), 3, 4);
        int codelength = getValue(seriaNetReportBean.getCmd(), 4, 5);
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(seriaNetReportBean.getDeviceid(), DeviceOperationType.setdscarm, dscpartitionid);
        if (dos == null || dos.getExpiretime().getTime() < new Date().getTime()) {
            return;
        }
        JSONObject json = JSONObject.parseObject(dos.getAppendmessage());
        if (json == null || (json.getIntValue("dscpartition") == dscpartitionid && StringUtils.isBlank(json.getString("password")))) {
            //password empty
            dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_NEED_PASSWORD);
            doss.saveOrUpdate(dos);
            return;
        }
        String password = changePassword(json, codelength);
        if (password.equals("")){
            dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PASSWORD_ERROR);
            doss.saveOrUpdate(dos);
            return;
        }
        CommandTlv commandTlv = CommandUtil.createDscCommand("200" + password);
        SynchronizeRequestHelper.asynchronizeRequest(seriaNetReportBean.getDeviceid(), commandTlv, 1);
    }

    private String changePassword(JSONObject json, int codelength) {
        String password = json.getString("password");
        if (codelength == password.length()) {
            return password;
        }
        if (codelength < password.length()){
            return "";
        }
        if (codelength > 4) {
            for (int i = codelength - 4; i > 0; i--) {
                password += "0";
            }
        }
        return password;
    }

    @Override
    protected void pushMessage() {

    }

    @Override
    protected void writeLog() {

    }

    @Override
    protected void init() {

    }

    private byte[] createCommand(String cmd, String value) {
        if (value == null) {
            value = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(cmd).append(value);
        char[] chars = sb.toString().toCharArray();
        int total = 0;
        for (int i = 0; i < chars.length; i++) {
            total += (int) chars[i];
        }
        String string = Integer.toHexString(total);
        if (string.length() > 2) {
            string = string.substring(string.length() - 2);
        }
        sb.append(string.toUpperCase());

        byte[] bytes = sb.toString().getBytes();
        byte[] endString = new byte[]{0x0D, 0x0A};
        byte[] newBytes = new byte[bytes.length + endString.length];
        System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
        System.arraycopy(endString, 0, newBytes, bytes.length, endString.length);
        return newBytes;
    }
}
