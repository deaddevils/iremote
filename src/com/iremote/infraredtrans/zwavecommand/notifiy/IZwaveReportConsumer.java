package com.iremote.infraredtrans.zwavecommand.notifiy;

public interface IZwaveReportConsumer
{
	void reportArrive(String deviceid , int nuid , byte[] report);
	boolean isFinished();
}
