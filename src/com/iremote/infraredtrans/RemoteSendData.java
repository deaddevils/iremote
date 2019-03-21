package com.iremote.infraredtrans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.json.annotations.JSON;

import com.alibaba.fastjson.annotation.JSONField;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.asycresponse.IAsyncResponse;
import com.iremote.common.taskmanager.IStatusfulTask;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;

public class RemoteSendData implements IStatusfulTask, IAsyncResponse 
{
	private static Log log = LogFactory.getLog(RemoteSendData.class);
	
	private String deviceid ;
	private CommandTlv command;
	protected byte[] response ;
	protected boolean finished = false ;
	protected Object waitobj = new Object();
	private long timeouttime = 0 ;
	protected int timeoutsecond;
	private long taskid ;
	protected int ackresult = ErrorCodeDefine.EXECUTE_STATUS_NOT_START;
	private int ackcount = 1 ;
	
	public RemoteSendData(String deviceid, CommandTlv ct , int timeoutsecond , long taskid) {
		super();
		this.deviceid = deviceid;
		this.command = ct;
		this.timeoutsecond = timeoutsecond ;
		this.taskid = taskid ;
		this.ackcount = ct.getAckcount();
	}
	
	@JSON(serialize=false)
	@JSONField(serialize = false)
	@Override
	public boolean isFinished() 
	{
		if ( finished )  // finished 
			return true;
		else if ( this.timeouttime == 0 )  // not execute
			return false ;
		else if ( System.currentTimeMillis() > this.timeouttime )  // time out .
		{
			this.ackresult = ErrorCodeDefine.TIME_OUT;
			log.info("Time out , discard task");
			if ( log.isInfoEnabled())
				log.info(com.alibaba.fastjson.JSON.toJSONString(this));
			return true ;
		}
		else 
			return false ;
	}

	@Override
	public void run() 
	{
		try 
		{
			this.timeouttime = System.currentTimeMillis() + timeoutsecond * 1000L ;
			SynchronizeRequestHelper.sendData(this.getDeviceid(), this.getCommand());
		} 
		catch (Throwable e) 
		{
			log.error(e.getMessage(), e);
		} 
	}

	@Override
	public void onAckResponse(byte[] response) 
	{
		this.response = response ;
		ackcount -- ;
		if ( ackcount <= 0 )
			finished = true ;
		else  
			this.timeouttime = System.currentTimeMillis() + timeoutsecond * 1000L ;
		
		if ( this.response != null )
		{
			Integer rst = TlvWrap.readInteter(response, TagDefine.TAG_RESULT, 4);
			if ( rst != null )
			{
				ackresult = rst ;
				this.command.onAckArrive(ackresult, response);
			}
			else 
				ackresult = ErrorCodeDefine.UNKNOW_ERROR;
		}
		synchronized(waitobj)
		{
			waitobj.notify();
		}
	}

	@Override
	public byte[] getAckResponse(int timeoutsecond) 
	{
		synchronized(waitobj)
		{
			if ( response != null )
				return response;
			try {
				if ( timeoutsecond >= 0 )
				{
					waitobj.wait(timeoutsecond * 1000L);
					finished = true;    //Requests are finished if we get a response or timeout .
				}
			} catch (InterruptedException e) {
				log.error(e.getMessage() , e);
			}				
			return response ;
		}
	}
	
	public int getAckresult() 
	{
		return ackresult;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public CommandTlv getCommand() {
		return command;
	}

	public long getTaskIndentify() {
		return taskid;
	}

	public void setAckresult(Integer ackresult) {
		this.ackresult = ackresult;
	}

	public void setTaskIndentify(long taskid) {
		this.taskid = taskid;
	}

}