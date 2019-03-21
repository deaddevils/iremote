package com.iremote.event.association;

import com.iremote.common.IRemoteConstantDefine;

public class DoorLockBellRingAssociation extends DeviceReportAssociation {
    @Override
    protected void initAssocationStatus() {
        this.associationstatus = IRemoteConstantDefine.DOOR_LOCK_BELL_RING;
        this.associationoldstatus = super.getOldstatus();
    }
}
