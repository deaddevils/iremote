package com.iremote.infraredtrans.zwavecommand.request;

import org.apache.struts2.json.annotations.JSON;

import com.alibaba.fastjson.annotation.JSONField;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.infraredtrans.RemoteSendData;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.infraredtrans.zwavecommand.notifiy.IZwaveReportConsumer;
import com.iremote.infraredtrans.zwavecommand.notifiy.ZwaveReportNotifyManager;

public class ZwaveReportRequestWrap extends RemoteSendData implements IZwaveReportConsumer
{
	
	private byte[] report ;
	
	private String deviceid ;
	private int nuid;
	private Byte[] reponsekey;
	private Byte[][] reponsekeys;

	public ZwaveReportRequestWrap(String deviceid, int nuid , CommandTlv ct,Byte[] reponsekey , int timeoutsecond, long taskid) 
	{
		super(deviceid, ct, timeoutsecond, taskid);
		this.deviceid = deviceid;
		this.nuid = nuid ;
		this.reponsekey = reponsekey ;
		ZwaveReportNotifyManager.getInstance().regist(deviceid, nuid, reponsekey, timeoutsecond,this);
	}

	public ZwaveReportRequestWrap(String deviceid, int nuid , CommandTlv ct,Byte[][] responsekeys , int timeoutsecond, long taskid)
	{
		super(deviceid, ct, timeoutsecond, taskid);
		this.deviceid = deviceid;
		this.nuid = nuid ;
		this.reponsekeys = responsekeys ;

		for ( int i = 0 ; i < this.reponsekeys.length ; i ++)
			ZwaveReportNotifyManager.getInstance().regist(deviceid, nuid, this.reponsekeys[i], this);
	}

	@Override
	public void reportArrive(String deviceid, int nuid, byte[] report)
	{
		Utils.print("report Arrive", report);
		synchronized(super.waitobj)
		{
			unregist();
			this.report = report ;
			super.finished = true;
			if ( super.ackresult == ErrorCodeDefine.EXECUTE_STATUS_NOT_START )
				super.ackresult = ErrorCodeDefine.SUCCESS;
			super.waitobj.notifyAll();
		}
		
	}
	
	@JSON(serialize=false)
	@JSONField(serialize = false)
	public byte[] getResponse()
	{
		return getResponse(timeoutsecond);
	}


	@JSON(serialize=false)
	@JSONField(serialize = false)
	public byte[] getResponse(int waitresponsesecond)
	{
		if ( this.report != null )
			return this.report;

		synchronized(super.waitobj)
		{
			if ( this.report != null )
				return this.report;
			try
			{
				if (waitresponsesecond != 0)
					super.waitobj.wait(waitresponsesecond * 1000);
				else
					super.waitobj.wait(super.timeoutsecond * 1000);
			}
			catch (InterruptedException e)
			{
			}
			if ( this.report == null && this.ackresult == ErrorCodeDefine.EXECUTE_STATUS_NOT_START )
				this.ackresult = ErrorCodeDefine.TIME_OUT;
			super.finished = true ;
			unregist();
		}
		return report ;
	}

	private void unregist()
	{
		if ( this.reponsekey != null)
			ZwaveReportNotifyManager.getInstance().unregist(deviceid, nuid, reponsekey, this);
		if ( this.reponsekeys != null )
			for ( int i = 0 ; i < this.reponsekeys.length ; i ++ )
				ZwaveReportNotifyManager.getInstance().unregist(deviceid, nuid, this.reponsekeys[i], this);

	}
	
	@Override
	public void onAckResponse(byte[] response) 
	{
		synchronized(super.waitobj)
		{
			super.response = response ;
			if ( super.response != null )
			{
				Integer rst = TlvWrap.readInteter(response, TagDefine.TAG_RESULT, 4);
				if ( rst != null )
					ackresult = rst ;
				else 
					ackresult = ErrorCodeDefine.UNKNOW_ERROR;
			}

			if ( super.ackresult != ErrorCodeDefine.SUCCESS )
				super.waitobj.notifyAll();
		}
	}
	
	
}
