package com.iremote.action.camera.lechange;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.camera.CameraHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.*;
import com.iremote.infraredtrans.GatewayReportHelper;
import com.iremote.service.AuthQrCodeService;
import com.iremote.service.CameraService;
import com.iremote.service.RemoteService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class AuthorizeCameraAction {
    private String applianceuuid;
    private String message;
    private PhoneUser phoneuser;
    private String devicetype;
    private String productorid;
    private int resultCode = ErrorCodeDefine.SUCCESS;
    private JSONObject messageJson;

    public String execute() {
        if (!PhoneUserHelper.isAdminUser(phoneuser.getPhoneuserid())) {
            resultCode = ErrorCodeDefine.NO_PRIVILEGE;
            return Action.SUCCESS;
        }
        if (!checkParameters()) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }
        String qid = messageJson.getString("qid");
        AuthQrCodeService service = new AuthQrCodeService();
        AuthQrCode code = service.findByApplianceUuid(applianceuuid);
        if (code != null) {
            resultCode = ErrorCodeDefine.APPLIANCE_HAD_ADDED;
            return Action.SUCCESS;
        }
        AuthQrCode authQrCode = service.findByQid(qid);
        if (authQrCode == null
                || !authQrCode.getAuthtype().equals(messageJson.getString("type"))
                || authQrCode.getStatus() != IRemoteConstantDefine.AUTH_QR_CODE_STATUS_VALID) {
            resultCode = ErrorCodeDefine.QR_CODE_INVALID;
        } else if (authQrCode.getStatus() == IRemoteConstantDefine.AUTH_QR_CODE_STATUS_USED) {
            resultCode = ErrorCodeDefine.QR_CODE_HAD_USED;
        } else if (authQrCode.getStatus() == IRemoteConstantDefine.AUTH_QR_CODE_STATUS_VALID) {
            setAuthQrCode(service, authQrCode);
            createDevice();
        }

        return Action.SUCCESS;
    }

    private void createDevice() {
        CameraService cameraService = new CameraService();
        Camera camera = cameraService.querybyapplianceuuid(applianceuuid);
        if (camera == null) {
            String deviceId = createDeviceId(camera);
            createRemote(deviceId);
            createCamera(deviceId);
        }
    }

    private void createCamera(String deviceId) {
        Camera c = new Camera();
        c.setDeviceid(deviceId);
        c.setApplianceid(Utils.createtoken());
        c.setApplianceuuid(applianceuuid);
        c.setName(applianceuuid);
        c.setProductorid(productorid);
        c.setDevicetype(devicetype);
        new CameraService().saveOrUpdate(c);
    }

    private String createDeviceId(Camera camera) {
        if (camera != null) {
            return camera.getDeviceid();
        }
        return CameraHelper.createVirtualDeviceId(phoneuser.getPlatform(), productorid, applianceuuid);
    }

    private void createRemote(String deviceId) {
        RemoteService service = new RemoteService();
        Remote remote = service.querybyDeviceid(deviceId);
        if (remote == null) {
            service.save(createRemoteObj(deviceId));
        }
    }

    private Remote createRemoteObj(String deviceId) {
        Remote r = GatewayReportHelper.createRemote(deviceId);
        r.setSsid("nossid");
        r.setMac("nomac");
        r.getCapability().add(new GatewayCapability(r, 1));
        r.getCapability().add(new GatewayCapability(r, 2));
        r.setStatus(1);
        r.setName(applianceuuid);
        return r;
    }

    protected void setAuthQrCode(AuthQrCodeService service, AuthQrCode authQrCode) {
        authQrCode.setApplianceuuid(applianceuuid);
        authQrCode.setStatus(IRemoteConstantDefine.AUTH_QR_CODE_STATUS_USED);
        authQrCode.setAuthtime(new Date());
        authQrCode.setOperator(phoneuser.getPhonenumber());
        service.update(authQrCode);
    }

    private boolean checkParameters() {
        try {
            messageJson = JSONObject.parseObject(message);
        } catch (Exception e) {
            return false;
        }
        if (messageJson == null || !IRemoteConstantDefine.AUTH_TYPE_LECHANGE_AUTHORIZE.equals(messageJson.getString("type"))
                || StringUtils.isBlank(messageJson.getString("qid"))) {
            return false;
        }
        return StringUtils.isNotBlank(applianceuuid);
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public void setProductorid(String productorid) {
        this.productorid = productorid;
    }

    public void setApplianceuuid(String applianceuuid) {
        this.applianceuuid = applianceuuid;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public int getResultCode() {
        return resultCode;
    }
}
