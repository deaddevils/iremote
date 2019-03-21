package com.iremote.action.infraredcode2;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.InfraredKey;
import com.iremote.service.InfraredKeyService;
import com.opensymphony.xwork2.Action;

public class DeleteRemoteKeyCodeAction {
    private Integer infrareddeviceid;
    private Integer keyindex;


    private InfraredKey infraredKey;
    private int resultCode = ErrorCodeDefine.SUCCESS;


    public String execute()
    {
        if(infrareddeviceid == null || keyindex == null){
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
        }
        InfraredKeyService iks = new InfraredKeyService();
        infraredKey = iks.querybyinfrareddeviceidandkeyindex(infrareddeviceid,keyindex);
        if(infraredKey != null){
            iks.delete(infraredKey);
        }
        return Action.SUCCESS;
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
