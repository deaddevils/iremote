package com.iremote.task.timertask.processor;

import com.iremote.common.Utils;
import com.iremote.domain.ZWaveDevice;

public abstract class BaseZwaveDelayAlarmingProcessor implements Runnable {
    protected ZWaveDevice zWaveDevice;

    @Override
    public void run() {

        if (init()) {
            return;
        }

        ZwaveDelayAccordingHelper zdah = new ZwaveDelayAccordingHelper(zWaveDevice);

        if (!zdah.hasArmed() || zdah.phoneuserid == null) {
            return;
        }
        pushAlarmMessage();
        setAlarmStatus();
        writeLog();
    }

    protected abstract boolean init();

    protected void appendWarningstatus(int warningstatus) {
        if (Utils.isJsonArrayContaints(zWaveDevice.getWarningstatuses(), warningstatus)) {
            return;
        }
        zWaveDevice.setWarningstatuses(Utils.jsonArrayAppend(zWaveDevice.getWarningstatuses(), warningstatus));
    }

    public abstract void setzWaveDevice(ZWaveDevice zWaveDevice);

    protected abstract void pushAlarmMessage();

    protected abstract void setAlarmStatus();

    protected abstract void writeLog();
}