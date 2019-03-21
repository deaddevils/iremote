package com.iremote.infraredtrans.zwavecommand;

public class StandardZwaveBatteryReportProcessor extends BatteryReportProcessor{
    @Override
    protected void parseReport() {
        if ( zrb.getCmd() == null || zrb.getCmd().length <= 2 )
            return ;
        
        battery = zrb.getCmd()[3] & 0xff;
    }
}
