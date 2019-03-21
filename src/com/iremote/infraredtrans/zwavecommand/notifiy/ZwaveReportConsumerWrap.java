package com.iremote.infraredtrans.zwavecommand.notifiy;

import com.iremote.common.IRemoteConstantDefine;

public class ZwaveReportConsumerWrap
{
	private IZwaveReportConsumer comsumer ;
	private Byte[] report ;
	private long expiretime = System.currentTimeMillis() + IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND * 1000;

	public ZwaveReportConsumerWrap(IZwaveReportConsumer comsumer, Byte[] report, int timeoutsecond)
	{
		super();
		this.comsumer = comsumer;
		this.report = report;
		if ( timeoutsecond != 0 )
			expiretime = System.currentTimeMillis() + timeoutsecond * 1000;
	}
	
	public ZwaveReportConsumerWrap(IZwaveReportConsumer comsumer, Byte[] report)
	{
		super();
		this.comsumer = comsumer;
		this.report = report;
	}
	public IZwaveReportConsumer getComsumer()
	{
		return comsumer;
	}
	public void setComsumer(IZwaveReportConsumer comsumer)
	{
		this.comsumer = comsumer;
	}
	public Byte[] getReport()
	{
		return report;
	}
	public void setReport(Byte[] report)
	{
		this.report = report;
	}
	
	public boolean isExpired()
	{
		return System.currentTimeMillis() > expiretime;
	}
	
}
