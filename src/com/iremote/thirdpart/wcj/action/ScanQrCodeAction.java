package com.iremote.thirdpart.wcj.action;

import com.iremote.action.qrcode.AddGatewayAction;
import com.iremote.action.qrcode.ScanCodeAction;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.CommunityAdministratorService;
import com.iremote.service.PhoneUserService;
import com.iremote.vo.Appliance;
import com.iremote.vo.RemoteData;
import com.opensymphony.xwork2.Action;

import java.util.ArrayList;
import java.util.List;

public class ScanQrCodeAction {
    private String message;
    private String appendmessage;
    private String loginname;
    private ThirdPart thirdpart;
    private int resultCode;
    private boolean gatewayonline;
    private Remote remote;

    public String execute(){
        PhoneUser phoneUser = getPhoneUser(loginname);
        if (phoneUser == null) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }
        ScanCodeAction action = execute(phoneUser);
        resultCode = action.getResultCode();
        if (resultCode == ErrorCodeDefine.NOT_SUPPORT) {
            return Action.SUCCESS;
        }
        AddGatewayAction result = (AddGatewayAction) action.getAction();
        gatewayonline = result.isGatewayonline();
        remote = createRemote(result.getRemote());

        return Action.SUCCESS;
    }

    private Remote createRemote(RemoteData remote) {
        if (remote == null) {
            return null;
        }
        Remote r = new Remote();
        r.name = remote.getName();
        r.deviceid = remote.getDeviceid();
        r.loginname = remote.getPhonenumber();
        r.zwavedevice = getDevice(remote, new ZWaveDevice());
        r.infrareddevice = getDevice(remote, new InfraredDevice());
        return r;
    }

    private <T> List<T> getDevice(RemoteData remote, T t) {
        if (remote.getAppliancelist() == null || remote.getAppliancelist().size() == 0) {
            return null;
        }
        ArrayList<T> list = new ArrayList<>();
        for (Appliance appliance : remote.getAppliancelist()) {
            if (appliance.getZwavedeviceid() != null && t instanceof ZWaveDevice) {
                ZWaveDevice zd = new ZWaveDevice();
                zd.setZwavedeviceid(appliance.getZwavedeviceid());
                zd.setName(appliance.getName());
                zd.setDevicetype(appliance.getDevicetype());
                list.add((T)zd);
            } else if (appliance.getInfrareddeviceid() != null && t instanceof InfraredDevice) {
                InfraredDevice id = new InfraredDevice();
                id.setInfrareddeviceid(appliance.getInfrareddeviceid());
                id.setName(appliance.getName());
                id.setDevicetype(appliance.getDevicetype());
                list.add((T)id);
            }
        }
        return list;
    }

    protected ScanCodeAction execute(PhoneUser phoneUser) {
        ScanCodeAction action = new ScanCodeAction();
        action.setMessage(message);
        action.setAppendmessage(appendmessage);
        action.setPhoneuser(phoneUser);

        action.execute();
        return action;
    }

    private PhoneUser getPhoneUser(String loginname) {
        Integer phoneUserId = new CommunityAdministratorService().queryByThirdPartIdandLoginName(thirdpart.getThirdpartid(), loginname);
        return phoneUserId == null ? null : new PhoneUserService().query(phoneUserId);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAppendmessage(String appendmessage) {
        this.appendmessage = appendmessage;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public void setThirdpart(ThirdPart thirdpart) {
        this.thirdpart = thirdpart;
    }

    public int getResultCode() {
        return resultCode;
    }

    public boolean isGatewayonline() {
        return gatewayonline;
    }

    public Remote getRemote() {
        return remote;
    }

    public class Remote{
        private String deviceid;
        private String name;
        private String loginname;
        private List<ZWaveDevice> zwavedevice;
        private List<InfraredDevice> infrareddevice;

        public String getDeviceid() {
            return deviceid;
        }

        public String getName() {
            return name;
        }

        public String getLoginname() {
            return loginname;
        }

        public List<ZWaveDevice> getZwavedevice() {
            return zwavedevice;
        }

        public List<InfraredDevice> getInfrareddevice() {
            return infrareddevice;
        }
    }
}
