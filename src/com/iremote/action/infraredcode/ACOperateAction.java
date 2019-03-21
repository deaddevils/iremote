package com.iremote.action.infraredcode;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.InfraredDevice;
import com.iremote.infraredcode.CodeLiberayHelper;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.InfreredCodeLiberayService;
import com.opensymphony.xwork2.Action;

public class ACOperateAction
{
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	private int infrareddeviceid;
	private Integer codeid;
	private int power;
	private int temperature;
	private int mode;
	private int winddirection;
	private int fan;
	private int autodirection;
	
	public String execute()
	{
		InfraredDeviceService svr = new InfraredDeviceService();
		InfraredDevice id = svr.query(infrareddeviceid);
		if ( id == null )
		{
			this.resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		int[] liberay = null ;
		
		if ( codeid != null )
		{
			InfreredCodeLiberayService ils = new InfreredCodeLiberayService();
			String str = ils.queryByCodeid(codeid, IRemoteConstantDefine.DEVICE_TYPE_AC);
			if ( StringUtils.isNotBlank(str))
				liberay = Utils.jsontoIntArray(str);
		}
		else 
			liberay = id.getCodelibery();
		
		if ( liberay == null )
		{
			this.resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		byte[] command = createAcCommand(liberay);
		
		CommandTlv ct = new CommandTlv(4 , 1);
		ct.addUnit(new TlvByteUnit(40 , command));
		
		SynchronizeRequestHelper.synchronizeRequest(id.getDeviceid(), ct , IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND);
		
		return Action.SUCCESS;
	}
	
	protected byte[] createAcCommand(int[] liberay)
	{
		byte[] command = new byte[liberay.length + 1];
		for ( int i = 0 ; i < liberay.length ; i ++ )
		{
			if ( i < CodeLiberayHelper.acsecurity.length )
				command[i] = (byte)(liberay[i] ^ CodeLiberayHelper.acsecurity[i]);
			else
				command[i] = (byte)(liberay[i]);
		}
				
		command[4] = (byte)this.temperature;
		command[5] = (byte)this.fan;
		command[6] = (byte)this.winddirection;
		command[7] = (byte)this.autodirection;
		command[8] = (byte)this.power;
		command[9] = 1;
		command[10] = (byte)this.mode;
		
		for ( int i = 0 ; i < command.length - 1  ; i ++ )
			command[command.length -1] += command[i] ;
		
		return command ;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public void setInfrareddeviceid(int infrareddeviceid)
	{
		this.infrareddeviceid = infrareddeviceid;
	}
	public void setCodeid(int codeid)
	{
		this.codeid = codeid;
	}
	public void setPower(int power)
	{
		this.power = power;
	}
	public void setTemperature(int temperature)
	{
		this.temperature = temperature;
	}
	public void setMode(int mode)
	{
		this.mode = mode;
	}
	public void setWinddirection(int winddirection)
	{
		this.winddirection = winddirection;
	}
	public void setFan(int fan)
	{
		this.fan = fan;
	}
	public void setAutodirection(int autodirection)
	{
		this.autodirection = autodirection;
	}

}
