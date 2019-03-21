package cn.com.isurpass.gateway.server.processor.gateway;

import com.iremote.infraredtrans.ReportProcessor;

import cn.com.isurpass.gateway.server.processor.BaseMessageHandler;

public class GatewayMessageHandler extends BaseMessageHandler 
{
	private ReportProcessor reportprocessor = new GatewayReportProcessor();

	@Override
	protected ReportProcessor getReportProcessor() 
	{
		return reportprocessor;
	}

}
