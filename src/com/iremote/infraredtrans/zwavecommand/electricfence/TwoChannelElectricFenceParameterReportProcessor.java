package com.iremote.infraredtrans.zwavecommand.electricfence;

public class TwoChannelElectricFenceParameterReportProcessor extends TwoChannelElectricFenceVoltageReportProcessor {

    @Override
    protected void parseReport() {
        super.parseReport();
        typeIndex = zrb.getCmd()[6] & 0xff;
    }
}
