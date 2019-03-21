package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.taskmanager.IMulitReportTask;
import com.iremote.domain.ZWaveDevice;

public interface IZwaveReportProcessor extends IMulitReportTask{

	void setReport(ZwaveReportBean zrb);
	void setNoSessionZwaveDevice(ZWaveDevice zwavedevice);
}
