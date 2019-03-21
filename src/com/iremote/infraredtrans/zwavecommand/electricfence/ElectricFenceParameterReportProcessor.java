package com.iremote.infraredtrans.zwavecommand.electricfence;

public class ElectricFenceParameterReportProcessor extends ElectricFenceVoltageReportProcessor {

      @Override
    protected void parseReport() {
          super.parseReport();
          typeIndex = zrb.getCmd()[2] & 0xff;
    }
}
