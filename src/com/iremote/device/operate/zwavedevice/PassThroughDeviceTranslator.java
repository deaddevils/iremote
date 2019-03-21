package com.iremote.device.operate.zwavedevice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PassThroughDeviceCmdHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.device.operate.OperationTranslatorBase;
import com.iremote.domain.DeviceRawCmd;
import com.iremote.domain.Remote;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.service.DeviceRawCmdService;
import com.iremote.service.RemoteService;
import com.iremote.task.devicecommand.CommandHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PassThroughDeviceTranslator extends OperationTranslatorBase {
    private static final String DEVICE_RAW_CMD_ID = "devicerawcmdid";

    @Override
    public String getDeviceStatus() {
        if (StringUtils.isNoneBlank(this.devicestatus))
            return this.devicestatus;
        if (this.status != null && this.status == IRemoteConstantDefine.DEVICE_STATUS_POWER_ON)
            this.devicestatus = IRemoteConstantDefine.DEVICE_OPERATION_OPEN;
        else if (this.status != null && this.status == IRemoteConstantDefine.DEVICE_STATUS_POWER_OFF)
            this.devicestatus = IRemoteConstantDefine.DEVICE_OPERATION_CLOSE;
        return devicestatus;
    }

    @Override
    public String getCommandjson() {
        if (StringUtils.isNotBlank(this.commandjson))
            return this.commandjson;
        return null;
    }

    @Override
    public Integer getValue() {
        if (this.status != null)
            return this.status;
        if (StringUtils.isNotBlank(this.devicestatus)) {
            if (IRemoteConstantDefine.DEVICE_OPERATION_OPEN.equals(this.devicestatus))
                this.status = IRemoteConstantDefine.DEVICE_STATUS_POWER_ON;
            else if (IRemoteConstantDefine.DEVICE_OPERATION_CLOSE.equals(this.devicestatus))
                this.status = IRemoteConstantDefine.DEVICE_STATUS_POWER_OFF;
        }
        return this.status;
    }

    @Override
    public List<CommandTlv> getCommandTlv() {
        if (this.commandtlvlst != null) {
            return this.commandtlvlst;
        }

        DeviceRawCmdService service = new DeviceRawCmdService();
        if (this.zwavedevice != null && StringUtils.isNotBlank(this.commandjson)) {
            JSONArray jsonArray = super.parseJSONArray(commandjson);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.containsKey(IRemoteConstantDefine.OPERATION)) {
                    List<CommandTlv> ctList = null;
                    String operation = jsonObject.getString(IRemoteConstantDefine.OPERATION);
                    if (IRemoteConstantDefine.DEVICE_OPERATION_CLOSE.equals(operation)) {
                        DeviceRawCmd cmd = service.queryUniquePowerCmdByCmdIndex(IRemoteConstantDefine.PASS_THROUGH_DEVICE_CMD_INDEX_POWER_OFF, zwavedevice.getZwavedeviceid());
                        if (cmd == null || StringUtils.isBlank(cmd.getRawcmd())) {
                            continue;
                        }
                        ctList = PassThroughDeviceCmdHelper.createCommandTlvList(cmd.getRawcmd(),
                                PassThroughDeviceCmdHelper.getCmdIndexByDeviceStatus(cmd.getCmdindex(), zwavedevice.getStatus()), zwavedevice.getNuid());

                    } else if (IRemoteConstantDefine.DEVICE_OPERATION_OPEN.equals(operation)) {
                        DeviceRawCmd cmd = service.queryUniquePowerCmdByCmdIndex(IRemoteConstantDefine.PASS_THROUGH_DEVICE_CMD_INDEX_POWER_ON, zwavedevice.getZwavedeviceid());
                        if (cmd == null || StringUtils.isBlank(cmd.getRawcmd())) {
                            continue;
                        }
                        ctList = PassThroughDeviceCmdHelper.createCommandTlvList(cmd.getRawcmd(),
                                PassThroughDeviceCmdHelper.getCmdIndexByDeviceStatus(cmd.getCmdindex(), zwavedevice.getStatus()), zwavedevice.getNuid());

                    } else if (IRemoteConstantDefine.DEVICE_OPERATION_SEND_CMD.equals(operation)){
                        int deviceRawCmdId = jsonObject.getIntValue(DEVICE_RAW_CMD_ID);
                        DeviceRawCmd cmd = service.query(deviceRawCmdId);
                        if (cmd == null || StringUtils.isBlank(cmd.getRawcmd())) {
                            continue;
                        }
                        ctList = PassThroughDeviceCmdHelper.createCommandTlvList(cmd.getRawcmd(),
                                PassThroughDeviceCmdHelper.getCmdIndexByDeviceStatus(cmd.getCmdindex(), zwavedevice.getStatus()), zwavedevice.getNuid());
                    }

                    if (ctList != null && ctList.size() != 0 && this.operationtype != null) {
                        for (CommandTlv commandTlv : ctList) {
                            commandTlv.addUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE, operationtype, TagDefine.TAG_LENGTH_1));
                            commandTlv.setDeviceid(zwavedevice.getDeviceid());
                        }
                    }

                    if (ctList != null && ctList.size() != 0) {
                        commandtlvlst = new ArrayList<>();
                        commandtlvlst.addAll(ctList);
                    }
                }
            }

            mergeCommands();
        }

        return commandtlvlst;
    }

    private void mergeCommands() {
        Remote remote = new RemoteService().querybyDeviceid(zwavedevice.getDeviceid());
        if (remote == null) {
            return;
        }
        commandtlvlst = CommandHelper.mergeCommand(remote, commandtlvlst);
    }

}
