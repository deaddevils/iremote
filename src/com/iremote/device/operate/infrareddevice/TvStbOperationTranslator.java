package com.iremote.device.operate.infrareddevice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.device.operate.OperationTranslatorBase;
import com.iremote.infraredcode.CodeLiberayHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TvStbOperationTranslator  extends OperationTranslatorBase
{

	@Override
	public String getDeviceStatus()
	{
		return null;
	}

	@Override
	public String getCommandjson()
	{
		if ( StringUtils.isBlank(this.commandjson) && this.command != null )
		{
			int baseindex = 0 ;
			if ( this.command[0] == 4 && this.command[1] == 1 )
				baseindex = 4 ;
			if ( this.command[4] == 0 && this.command[5] == 40 )
				baseindex = 8 ;

			int c1 = this.command[baseindex+3] & 0xff ;
			int c2 = this.command[baseindex+4] & 0xff ;

			int[] cl = this.infrareddevice.getCodelibery();
			for ( int i = 1 ; i < cl.length - 1 ; i += 2  )
			{
				if ( ( cl[i] ^ CodeLiberayHelper.tvsecurity[i] ) == c1
						&& ( cl[i + 1] ^ CodeLiberayHelper.tvsecurity[i + 1] ) == c2 )
				{
					TvStbOperateCommandBase tso = new TvStbOperateCommandBase();
					JSONObject json = tso.getCommandJson(this.infrareddevice.getDevicetype(), i);
					if ( json != null ){
						JSONArray jsonarray = new JSONArray();
						jsonarray.add(json);
						this.commandjson = jsonarray.toJSONString();
					}
					break;
				}
			}
		}
		return this.commandjson;
	}

	@Override
	public Integer getValue()
	{
		return null;
	}

	@Override
	public String getStatus()
	{
		return null;
	}


	@Override
	public List<CommandTlv> getCommandTlv()
	{
		if ( this.commandtlvlst != null )
			return commandtlvlst;

		if ( this.commandtlvlst == null && this.infrareddevice != null && StringUtils.isNotBlank(this.commandjson))
		{
			JSONArray jsonarray = parseJSONArray(this.commandjson);
			for(int i=0; i < jsonarray.size(); i++){
				JSONObject json = jsonarray.getJSONObject(i);
				TvStbOperateCommandBase tso = new TvStbOperateCommandBase();
				//tso.setInfrareddevice(this.infrareddevice);
				tso.setCodeid(this.infrareddevice.getCodeid());
				tso.setDevicetype(this.infrareddevice.getDevicetype());

				String op = json.getString("operation");
				if ( json.containsKey("value"))
					op = String.format("%s_%d", op,json.getIntValue("value"));
				if ( IRemoteConstantDefine.DEVICE_TYPE_TV.equals(this.infrareddevice.getDevicetype()))
					tso.setTvOperation(op);
				else
					tso.setStbOperation(op);
				byte[] cmd = tso.createTvStbCommand();

				CommandTlv ct = new CommandTlv(4 , 1);
				ct.addUnit(new TlvByteUnit(40 , cmd));

				commandtlvlst = new ArrayList<CommandTlv>();
				commandtlvlst.add(ct);
			}
		}


		return commandtlvlst;
	}

}
