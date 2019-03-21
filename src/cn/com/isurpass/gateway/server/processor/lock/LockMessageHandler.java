package cn.com.isurpass.gateway.server.processor.lock;

import com.iremote.infraredtrans.ReportProcessor;

import cn.com.isurpass.gateway.server.processor.BaseMessageHandler;

public class LockMessageHandler extends BaseMessageHandler 
{
	private ReportProcessor reportprocessor = new LockReportProcessor();

	@Override
	protected ReportProcessor getReportProcessor() 
	{
		return reportprocessor;
	}

}
