package com.iremote.action.camera;

import java.util.Date;
import java.util.List;

import com.iremote.action.helper.GatewayHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.constant.CameraProductor;
import com.iremote.domain.*;
import com.iremote.infraredtrans.GatewayReportHelper;
import com.iremote.service.CameraService;
import com.iremote.service.DeviceCapabilityService;
import com.iremote.service.RemoteService;
import com.iremote.vo.RemoteData;
import com.opensymphony.xwork2.Action;

public class AddCameraAction {
    protected int resultCode = ErrorCodeDefine.SUCCESS;
    protected String name;
    protected String productorid;
    protected String applianceuuid;
    protected String code;
    protected String devicetype = "1";
    protected RemoteData remote;
    protected PhoneUser phoneuser;
    protected int status;
    protected String lechangecode;
    protected String lechangemsg;
    protected int needauthcode = IRemoteConstantDefine.CAMERA_NEED_NOT_AUTH_CODE;

    public String execute() {
        String deviceid = createdeviceid();
        if (deviceid == null) {
            return Action.SUCCESS;
        }

        RemoteService rs = new RemoteService();
        Remote r = rs.getIremotepassword(deviceid);

        if (r != null && r.getPhoneuserid() != null && !r.getPhoneuserid().equals(phoneuser.getPhoneuserid())) {
            this.resultCode = ErrorCodeDefine.NO_PRIVILEGE;
            return Action.SUCCESS;
        }

        if (r != null && r.getPhoneuserid() != null && r.getPhoneuserid().equals(phoneuser.getPhoneuserid())) {
            r.setName(name);

            CameraService cs = new CameraService();
            List<Camera> lst = cs.querybydeviceid(deviceid);
            if (lst != null && lst.size() > 0) {
                Camera c = lst.get(0);
                c.setName(name);
                c.setDevicetype(getDevicetype());
                c.setProductorid(productorid);
                phoneuser.setLastupdatetime(new Date());
                PhoneUserHelper.sendInfoChangeMessage(phoneuser);

                return Action.SUCCESS;
            }
        }

        if (addcamera() == false)
            return Action.SUCCESS;

        if (r == null) {
            r = GatewayReportHelper.createRemote(deviceid);
            r.setSsid("nossid");
            r.setMac("nomac");
            r.getCapability().add(new GatewayCapability(r, 1));
            r.getCapability().add(new GatewayCapability(r, 2));
            r.setStatus(1);
        }
        r.setPlatform(phoneuser.getPlatform());
        r.setPhonenumber(phoneuser.getPhonenumber());
        r.setPhoneuserid(phoneuser.getPhoneuserid());
        r.setName(name);

        rs.saveOrUpdate(r);

        CameraService cs = new CameraService();
        List<Camera> lst = cs.querybydeviceid(deviceid);

        Camera c = null;
        if (lst != null && lst.size() > 0)
            c = lst.get(0);
        else {
            c = new Camera();
            c.setDeviceid(r.getDeviceid());
            c.setApplianceid(Utils.createtoken());
            c.setApplianceuuid(applianceuuid);
        }
        c.setName(name);
        c.setProductorid(productorid);
        c.setDevicetype(getDevicetype());
        setCameraInfo(c);
        cs.saveOrUpdate(c);

        if (needauthcode == IRemoteConstantDefine.CAMERA_NEED_AUTH_CODE) {
            addDeviceCapability(c);
        }

        remote = GatewayHelper.createRemoteData(r);
        remote.getAppliancelist().add(GatewayHelper.createAppliance(c));

        phoneuser.setLastupdatetime(new Date());
        PhoneUserHelper.sendInfoChangeMessage(phoneuser);

        return Action.SUCCESS;
    }

    private void addDeviceCapability(Camera camera) {
        DeviceCapabilityService dcs = new DeviceCapabilityService();

        if (dcs.queryByCamera(camera, IRemoteConstantDefine.CAMERA_NEED_AUTH_CODE) == null) {
            DeviceCapability capability = new DeviceCapability(camera, IRemoteConstantDefine.CAMERA_NEED_AUTH_CODE);
            dcs.save(capability);
        }
    }

    protected void setCameraInfo(Camera c)
    {
    	
    }
    
    protected String getDevicetype() {
        return this.devicetype;
    }

    protected String createdeviceid() {
        CameraService cs = new CameraService();
        Camera c = cs.querybyapplianceuuid(applianceuuid);
        if (c != null) {
            return c.getDeviceid();
        }
        if (CameraProductor.lechangeabroad.getDevicetype().equals(devicetype)
                || CameraProductor.dahualechange.getDevicetype().equals(devicetype)) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_AUTHORIZED;
            return null;
        }
        return CameraHelper.createVirtualDeviceId(phoneuser.getPlatform(), productorid, applianceuuid);
    }

    protected boolean addcamera() {
        return true;
    }


    public int getResultCode() {
        return resultCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductorid(String productorid) {
        this.productorid = productorid;
    }

    public void setApplianceuuid(String applianceuuid) {
        this.applianceuuid = applianceuuid;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public RemoteData getRemote() {
        return remote;
    }

    public int getStatus() {
        return status;
    }

    public String getLechangecode() {
        return lechangecode;
    }

    public String getLechangemsg() {
        return lechangemsg;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public void setNeedauthcode(int needauthcode) {
        this.needauthcode = needauthcode;
    }
}
