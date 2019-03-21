package com.iremote.action.infraredcode;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.device.operate.infrareddevice.TvStbOperateCommandBase;
import com.iremote.domain.InfraredDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.InfraredDeviceService;
import com.opensymphony.xwork2.Action;

public class TvStbOperationAction
{
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	private int infrareddeviceid;
	private Integer codeid;
	private String operation;
	private Integer value ;
	
	public String execute()
	{
		InfraredDeviceService svr = new InfraredDeviceService();
		InfraredDevice id = svr.query(infrareddeviceid);
		if ( id == null )
		{
			this.resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		if ( ConnectionManager.isOnline(id.getDeviceid()) == false ) 
		{
			this.resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return Action.SUCCESS;
		}
	
		TvStbOperateCommandBase toc = new TvStbOperateCommandBase();
		
		if ( value != null )
			operation = String.format("%s_%d", operation , value);
		
		if ( IRemoteConstantDefine.DEVICE_TYPE_STB.equals(id.getDevicetype()))
			toc.setStbOperation(operation);
		if ( IRemoteConstantDefine.DEVICE_TYPE_TV.equals(id.getDevicetype()))
			toc.setTvOperation(operation);
		toc.setDevicetype(id.getDevicetype());
		
		if ( codeid != null )
			toc.setCodeid(String.valueOf(codeid));
		else if ( StringUtils.isNotBlank(id.getCodeid()))
			toc.setCodeid(id.getCodeid());
		
		CommandTlv ct = toc.createCommand();
		
		if ( ct == null )
		{
			this.resultCode = ErrorCodeDefine.NOT_SUPPORT;
			return Action.SUCCESS;
		}
		
		byte[] rst = SynchronizeRequestHelper.synchronizeRequest(id.getDeviceid(), ct , IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND);
		
		if ( rst == null )
	   	{
	   		resultCode = ErrorCodeDefine.TIME_OUT;
	   	}
	   	else 
	   	{
	   		resultCode = TlvWrap.readInt(rst , 1 , TlvWrap.TAGLENGTH_LENGTH);
	   		if ( resultCode == Integer.MIN_VALUE )
	   			resultCode = ErrorCodeDefine.TIME_OUT;
	   	}
		
		return Action.SUCCESS;
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
	public void setOperation(String operation)
	{
		this.operation = operation;
	}
	public void setValue(int value)
	{
		this.value = value;
	}
	
} 
