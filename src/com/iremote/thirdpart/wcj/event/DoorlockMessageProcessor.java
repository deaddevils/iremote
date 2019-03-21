package com.iremote.thirdpart.wcj.event;

import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.thirdpart.wcj.report.ReportManager;

public class DoorlockMessageProcessor extends ZWaveDeviceEvent implements ITextMessageProcessor {

	@Override
	public void run() 
	{
		ReportManager.addReport(this);
	}

	@Override
	public String getTaskKey() 
	{
		return getDeviceid();
	}

}
