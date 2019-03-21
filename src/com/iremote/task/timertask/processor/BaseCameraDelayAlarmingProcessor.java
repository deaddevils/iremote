package com.iremote.task.timertask.processor;

import com.iremote.common.Utils;
import com.iremote.domain.Camera;

public abstract class BaseCameraDelayAlarmingProcessor implements Runnable {
    protected Camera camera;
    protected CameraDelayAccordingHelper cdah;
    @Override
    public void run() {

        if (init()) {
            return;
        }

        cdah = new CameraDelayAccordingHelper(camera);

        if (!cdah.hasArmed() || cdah.phoneuserid == null) {
            return;
        }
        pushAlarmMessage();
        setAlarmStatus();
//        writeLog();
    }

    protected abstract boolean init();

    protected void appendWarningstatus(int warningstatus) {
        if (Utils.isJsonArrayContaints(camera.getWarningstatuses(), warningstatus)) {
            return;
        }
        camera.setWarningstatuses(Utils.jsonArrayAppend(camera.getWarningstatuses(), warningstatus));
    }

    public abstract void setCamera(Camera camera);

    protected abstract void pushAlarmMessage();

    protected abstract void setAlarmStatus();

    protected abstract void writeLog();
}
