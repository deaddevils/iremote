package com.iremote.device.operate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.constant.ArithmeticOperator;
import com.iremote.device.operate.zwavedevice.OperationTranslatorHelper;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;


public abstract class OperationTranslatorBase implements IOperationTranslator
{
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(OperationTranslatorBase.class);
	protected ZWaveDevice zwavedevice;
	protected InfraredDevice infrareddevice;
	protected Integer channelid ;
	protected String commandjson;
	protected Integer status ;
	protected String devicestatus;
	protected byte[] command ;
	protected Integer operationtype;
	protected List<CommandTlv> commandtlvlst ;

	public void setOperationType(Integer operationtype)
	{
		this.operationtype = operationtype;
	}
	
	public void setCommand(byte[] command)
	{
		this.command = command ;
	}
	
	@Override
	public void setZWavedevice(ZWaveDevice zd)
	{
		this.zwavedevice = zd ;
	}

	@Override
	public void setInfrareddevice(InfraredDevice id)
	{
		this.infrareddevice = id ;
	}

	@Override
	public void setChannelid(int channelid)
	{
		this.channelid = channelid;
	}
	
	@Override
	public String getStatus()
	{
		Integer s = this.getValue();
		if ( s != null )
			return String.valueOf(s);
		return null;
	}

	@Override
	public String getOperator()
	{
		return ArithmeticOperator.eq.name();
	}
	
	@Override
	public String getMajorType()
	{
		if ( zwavedevice != null )
			return zwavedevice.getMajortype();
		else if ( infrareddevice != null )
			return infrareddevice.getMajortype();
		return null;
	}

	@Override
	public String getDevicetype()
	{
		if ( zwavedevice != null )
			return zwavedevice.getDevicetype();
		else if ( infrareddevice != null )
			return infrareddevice.getDevicetype();
		return null;
	}

	@Override
	public void setDeviceStatus(String devicestatus)
	{
		this.devicestatus = devicestatus;
	}

	@Override
	public void setCommandjson(String json)
	{
		this.commandjson = json;
	}

	@Override
	public void setStatus(Integer status)
	{
		this.status = status ;
	}

	@Override
	public List<CommandTlv> getCommandTlv()
	{
		if ( commandtlvlst != null )
			return commandtlvlst;

		if ( this.zwavedevice != null &&  StringUtils.isNotBlank(this.commandjson))
		{
			JSONArray ja = parseJSONArray(this.commandjson);
			
			for ( int i = 0 ; i < ja.size() ; i ++ )
			{
				JSONObject json = ja.getJSONObject(i);
				
				if ( !json.containsKey(IRemoteConstantDefine.OPERATION))
					continue;

				this.setStatus(null);
				this.setDeviceStatus(json.getString(IRemoteConstantDefine.OPERATION));
				if ( !json.containsKey(IRemoteConstantDefine.VALUE) && this.getValue() != null )
					json.put(IRemoteConstantDefine.VALUE, this.getValue());

				CommandTlv ct = createCommandTlv( json);
				if ( ct != null )
				{
					if ( this.operationtype != null )
						ct.addUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE ,operationtype ,TagDefine.TAG_LENGTH_1 ));
					ct.setDeviceid(zwavedevice.getDeviceid());
					commandtlvlst = new ArrayList<CommandTlv>();
					commandtlvlst.add(ct);
				}
			}
		}
		
		return commandtlvlst;
	}

	protected JSONArray parseJSONArray(String json)
	{
		Object jt= JSON.parse(json);
		
		JSONArray ja = null ;  
		
		if ( jt instanceof JSONArray )
			ja = (JSONArray)jt ;
		else if ( jt instanceof JSON )
		{
			ja = new JSONArray();
			ja.add(jt);
		}
		return ja;
	}
	
	@Override
	public byte[] getCommands()
	{
		if ( this.command != null )
			return this.command;
		
		getCommandTlv();
		
		this.command = OperationTranslatorHelper.createCommand(commandtlvlst);
		
		return this.command;
	}
	
	@Override
	public byte[] getCommand()
	{
		return getCommands();
	}
	
	protected CommandTlv createCommandTlv(JSONObject json)
	{
		return null ;
	}

}
