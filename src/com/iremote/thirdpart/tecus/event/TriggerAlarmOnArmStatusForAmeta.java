package com.iremote.thirdpart.tecus.event;

public class TriggerAlarmOnArmStatusForAmeta extends TriggerAlarmForAmetaProcessor {

    @Override
    public void run() {

        if ( getWarningstatus() != null && getWarningstatus() != 0 )
            super.run();
    }
}
