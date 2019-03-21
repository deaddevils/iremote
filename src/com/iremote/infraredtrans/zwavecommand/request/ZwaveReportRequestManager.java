package com.iremote.infraredtrans.zwavecommand.request;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class ZwaveReportRequestManager
{
	public static ZwaveReportRequestWrap sendRequest(String deviceid , int nuid, CommandTlv request , Byte[] reponsekey, int timeoutsecond , long taskid )
	{
		if ( timeoutsecond == 0 )
			timeoutsecond = IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND;
		
		request.setDeviceid(deviceid);
		ZwaveReportRequestWrap wrap = new ZwaveReportRequestWrap(deviceid, nuid, request ,reponsekey , timeoutsecond,taskid);

		
		SynchronizeRequestHelper.asynchronizeRequest(wrap);
		
		return wrap;
	}
	
	public static ZwaveReportRequestWrap sendRequest(String deviceid , int nuid, CommandTlv request , Byte[][] reponsekey, int timeoutsecond , long taskid )
	{
		if ( timeoutsecond == 0 )
			timeoutsecond = IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND;
		
		ZwaveReportRequestWrap wrap = new ZwaveReportRequestWrap(deviceid, nuid, request ,reponsekey , timeoutsecond,taskid);

		
		SynchronizeRequestHelper.asynchronizeRequest(wrap);
		
		return wrap;
	}
}
