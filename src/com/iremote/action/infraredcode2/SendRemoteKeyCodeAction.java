package com.iremote.action.infraredcode2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Utils;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.InfraredKey;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.InfraredKeyService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;

public class SendRemoteKeyCodeAction {
    private Integer infrareddeviceid;
    private Integer keyindex;

    private int resultCode = ErrorCodeDefine.SUCCESS;
    private String deviceid;
    private InfraredDevice infraredDevice;

    public String execute()
    {
        getInfraredDevice();
        if(resultCode != ErrorCodeDefine.SUCCESS){
            return Action.SUCCESS;
        }
        InfraredKeyService iks = new InfraredKeyService();
        InfraredKey infraredKey = iks.querybyinfrareddeviceidandkeyindex(infrareddeviceid,keyindex);
        if(infraredKey == null || StringUtils.isEmpty(infraredKey.getKeycode())){
            resultCode = ErrorCodeDefine.INFRAREDKEY_NOT_EXIST;
            return Action.SUCCESS;
        }
        JSONArray ja = JSON.parseArray(infraredKey.getKeycode());

        byte[] b = new byte[ja.size()] ;
        for ( int i = 0 ; i < b.length ; i ++ )
            b[i] = ja.getByteValue(i);

        CommandTlv ct = new CommandTlv(4,1);
        ct.addUnit(new TlvByteUnit(40 , b));

        SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct, 1);
        return Action.SUCCESS;
    }

    private void getInfraredDevice(){
        if(infrareddeviceid == null || keyindex == null){
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return;
        }
        InfraredDeviceService idf = new InfraredDeviceService();
        infraredDevice = idf.query(infrareddeviceid);
        if(infraredDevice == null){
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return;
        }
        deviceid = infraredDevice.getDeviceid();
    }

    public void setInfrareddeviceid(Integer infrareddeviceid) {
        this.infrareddeviceid = infrareddeviceid;
    }

    public void setKeyindex(Integer keyindex) {
        this.keyindex = keyindex;
    }

    public int getResultCode() {
        return resultCode;
    }
}
