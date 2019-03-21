package com.iremote.device.operate.zwavedevice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.device.operate.OperationTranslatorBase;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TwoChannelElectricFenceOperationTranslator extends OperationTranslatorBase {
    @Override
    public String getDeviceStatus() {
        if ( StringUtils.isNoneBlank(this.devicestatus))
            return this.devicestatus;
        if ( this.status != null && this.status == IRemoteConstantDefine.DEVICE_STATUS_ELECTRIC_FENCE_ARM)
            this.devicestatus = IRemoteConstantDefine.DEVICE_OPERATION_E_FENCE_ARM;
        else if ( this.status != null && this.status == IRemoteConstantDefine.DEVICE_STATUS_ELECTRIC_FENCE_DIS_ARM)
            this.devicestatus = IRemoteConstantDefine.DEVICE_OPERATION_E_FENCE_DIS_ARM;
        return devicestatus;
    }

    @Override
    public String getCommandjson() {
        if ( StringUtils.isNotBlank(this.commandjson))
            return this.commandjson;
        return null;
    }

    @Override
    public Integer getValue() {
        if ( this.status != null )
            return this.status;
        if ( StringUtils.isNotBlank(this.devicestatus))
        {
            if ( IRemoteConstantDefine.DEVICE_OPERATION_E_FENCE_ARM.equals(this.devicestatus) )
                this.status = IRemoteConstantDefine.DEVICE_STATUS_ELECTRIC_FENCE_ARM ;
            else if ( IRemoteConstantDefine.DEVICE_OPERATION_E_FENCE_DIS_ARM.equals(this.devicestatus) )
                this.status = IRemoteConstantDefine.DEVICE_STATUS_ELECTRIC_FENCE_DIS_ARM ;
        }
        return this.status;
    }

    @Override
    public List<CommandTlv> getCommandTlv() {
        if (this.commandtlvlst != null) {
            return this.commandtlvlst;
        }

        if (this.zwavedevice != null && StringUtils.isNotBlank(this.commandjson)) {
            JSONArray jsonArray = super.parseJSONArray(commandjson);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int channel = jsonObject.getIntValue(IRemoteConstantDefine.CHANNELID);
                if (channel == 0) {
                    return commandtlvlst;
                }

                if (jsonObject.containsKey(IRemoteConstantDefine.OPERATION)) {
                    CommandTlv ct = null;
                    if (IRemoteConstantDefine.DEVICE_OPERATION_E_FENCE_DIS_ARM.equals(jsonObject.getString(IRemoteConstantDefine.OPERATION))) {
                        ct = CommandUtil.createElectricFenceCommand(super.zwavedevice.getNuid(), (byte) IRemoteConstantDefine.DEVICE_STATUS_ELECTRIC_FENCE_DIS_ARM, channel);
                    } else if (IRemoteConstantDefine.DEVICE_OPERATION_E_FENCE_ARM.equals(jsonObject.getString(IRemoteConstantDefine.OPERATION))) {
                        ct = CommandUtil.createElectricFenceCommand(super.zwavedevice.getNuid(), (byte) IRemoteConstantDefine.DEVICE_STATUS_ELECTRIC_FENCE_ARM, channel);
                    }

                    if (ct != null && this.operationtype != null) {
                        ct.addUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE, operationtype, TagDefine.TAG_LENGTH_1));
                    }

                    if (ct != null) {
                        if (commandtlvlst == null) {
                            commandtlvlst = new ArrayList<>();
                        }
                        commandtlvlst.add(ct);
                    }
                }
            }
        }

        return commandtlvlst;
    }
}
