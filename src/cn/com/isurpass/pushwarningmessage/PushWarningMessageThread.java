package cn.com.isurpass.pushwarningmessage;

import com.alibaba.fastjson.JSONArray;
import com.iremote.action.camera.lechange.LeChangeRequestManagerStore;
import com.iremote.common.GatewayUtils;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.push.PushMessageThread;
import com.iremote.common.sms.SMSInterface;
import com.iremote.domain.Camera;
import com.iremote.domain.OemProductor;
import com.iremote.domain.ZWaveDevice;
import com.iremote.event.camera.PushCameraWarningMessage;
import com.iremote.service.CameraService;
import com.iremote.service.OemProductorService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.tecus.event.PushMessageAccordingtoArmStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

public class PushWarningMessageThread implements Runnable {
    private static Log log = LogFactory.getLog(PushWarningMessageThread.class);
    public static void main(String[] args) {
        new Thread(new PushWarningMessageThread()).start();
    }

    @Override
    public void run() {
        HibernateUtil.prepareSession(IRemoteConstantDefine.HIBERNATE_SESSION_REMOTE);
        initOemProducotr();

        while(true){
            log.info("start push warning message");
            HibernateUtil.prepareSession(IRemoteConstantDefine.HIBERNATE_SESSION_REMOTE);

            pushZwaveWarning();
            pushCameraWarning();

            HibernateUtil.closeSession();
            try {
                Thread.sleep(30 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initOemProducotr()
    {
        OemProductorService ops = new OemProductorService();
        List<OemProductor> lst = ops.query();

        for ( OemProductor op : lst)
        {
            PushMessageThread.initPushClient(op.getPlatform(), op.getPushmasterkey(), op.getPushappkey());
            SMSInterface.initSmsSender(op.getPlatform(), op.getSmssign());
        }
    }

    private void pushZwaveWarning() {
        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zWaveDevices = zds.queryWarningDevice();
        for (ZWaveDevice zWaveDevice : zWaveDevices) {
            if (zWaveDevice.getDevicetype() == IRemoteConstantDefine.DEVICE_TYPE_DSC) {
                continue;
            }
            pushWarning(zWaveDevice);
        }
    }

    private void pushCameraWarning() {
        CameraService cs = new CameraService();
        List<Camera> cameras = cs.queryWarningDevice();
        for (Camera camera : cameras) {
            if (camera.getArmstatus() != IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM) {
                pushWarning(camera);
            }
//            System.out.println(camera.getName()+" " + camera.getDevicetype());
        }
    }

    private void pushWarning(ZWaveDevice zWaveDevice) {
        List<Integer> jsonList = JSONArray.parseArray(zWaveDevice.getWarningstatuses(), Integer.class);
        for (Integer warningstatus : jsonList) {
            PushMessageAccordingtoArmStatus pmaas = new PushMessageAccordingtoArmStatus();
            pmaas.setName(zWaveDevice.getName());
            pmaas.setApplianceid(zWaveDevice.getApplianceid());
            pmaas.setDeviceid(zWaveDevice.getDeviceid());
            pmaas.setZwavedeviceid(zWaveDevice.getZwavedeviceid());
            pmaas.setDevicetype(zWaveDevice.getDevicetype());
            pmaas.setWarningstatus(warningstatus);
            pmaas.setStatus(zWaveDevice.getStatus());
            pmaas.setStatuses(zWaveDevice.getStatuses());
            pmaas.setWarningstatuses(zWaveDevice.getWarningstatuses());
            pmaas.setEventtime(new Date());

            String warningstatustype = getWarningstatusType(zWaveDevice.getDevicetype(), zWaveDevice.getWarningstatus());
            if (warningstatustype == null) {
                continue;
            }
            pmaas.setEventtype(warningstatustype);

            pmaas.run();
        }
    }

    private void pushWarning(Camera camera) {
        List<Integer> jsonList = JSONArray.parseArray(camera.getWarningstatuses(), Integer.class);
        for (Integer warningstatus : jsonList) {
            PushCameraWarningMessage pcwm = new PushCameraWarningMessage();
            pcwm.setCameraid(camera.getCameraid());
            pcwm.setCamerauuid(camera.getApplianceid());
            pcwm.setEventtime(new Date());
            pcwm.setWarningstatus(warningstatus);
            pcwm.setDeviceid(camera.getDeviceid());

            String warningstatustype = getCameraWarningstatusType(warningstatus);
            if (warningstatustype == null) {
                continue;
            }
            pcwm.setEventtype(warningstatustype);

            pcwm.run();
        }
    }

    private String getWarningstatusType(String devicetype, Integer warningstatus) {
        if (devicetype == null || warningstatus == null) {
            return null;
        }

        String operation = String.format("%s_%d", devicetype, warningstatus);
        return IRemoteConstantDefine.DEVICE_WARNING_MESSAGE_MAP.get(operation);
    }

    private String getCameraWarningstatusType(Integer warningstatus) {
        return getWarningstatusType("camera", warningstatus);
    }
}
