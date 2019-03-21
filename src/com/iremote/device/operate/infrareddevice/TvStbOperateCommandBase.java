package com.iremote.device.operate.infrareddevice;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.device.operate.IDeviceOperateCommand;
import com.iremote.infraredcode.CodeLiberayHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.service.InfreredCodeLiberayService;

import org.apache.commons.lang3.StringUtils;

public class TvStbOperateCommandBase implements IDeviceOperateCommand
{
	protected int index ;
	protected int[] codeliberay;
	protected String codeid ;
	protected String devicetype ;

	@Override
	public CommandTlv  createCommand()
	{
		CommandTlv ct = new CommandTlv(4 , 1);

		byte[] b = createTvStbCommand() ;
		if ( b == null )
			return null;
		ct.addUnit(new TlvByteUnit(40 , b));
		return ct ;
	}

	protected byte[] createTvStbCommand()
	{
		init();

		int[] cl = this.codeliberay;

		if (cl == null)
			return null;

		if (cl[0] == 0x04)
			cl[5] = cl[5] ^ 0x20;
		else if (cl[0] == 0x0a)
			cl[5] = cl[5] ^ 0x08;
		else if (cl[0] == 0x21)
			cl[5] = cl[5] ^ 0x10;

        byte[] b = new byte[]{0x30, 0x00,
                (byte) (cl[0] ^ CodeLiberayHelper.tvsecurity[0]),
                (byte) (cl[index] ^ CodeLiberayHelper.tvsecurity[index]),
                (byte) (cl[index + 1] ^ CodeLiberayHelper.tvsecurity[index + 1]),
                (byte) (cl[cl.length - 4] ^ CodeLiberayHelper.tvsecurity[cl.length - 4]),
                (byte) (cl[cl.length - 3] ^ CodeLiberayHelper.tvsecurity[cl.length - 3]),
                (byte) (cl[cl.length - 2] ^ CodeLiberayHelper.tvsecurity[cl.length - 2]),
                (byte) (cl[cl.length - 1] ^ CodeLiberayHelper.tvsecurity[cl.length - 1]),
                0};

		for (int i = 0; i < 9; i++)
			b[9] += b[i];

		return b;
	}

	private void init()
	{
		if ( StringUtils.isBlank(this.codeid) )
			return ;
		
		Integer cid = null ;
		if ( StringUtils.isNumeric(this.codeid))
			cid = Integer.valueOf(this.codeid);
		else 
		{
			String[] ca = this.codeid.split("_"); 
			if ( ca == null || ca.length < 2 )
				return ;
			cid = Integer.valueOf(ca[1]);
		}
		
		if ( IRemoteConstantDefine.DEVICE_TYPE_TV.equals(devicetype) // xiao mi TV 
				&& cid == 12936 
				&&  index == 11 )
			cid = 12937;
		
		String s = new InfreredCodeLiberayService().queryByCodeid(cid, devicetype);
		if (StringUtils.isNotBlank(s))
			this.codeliberay = Utils.jsontoIntArray(s);
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setTvOperation(String operation)
	{
		Integer i = TV_OPERATION_MAP.get(operation);
		if ( i == null )
			return ;
		this.index = i ;
	}

	public void setStbOperation(String operation)
	{
		Integer i = STB_OPERATION_MAP.get(operation);
		if ( i == null )
			return ;
		this.index = i ;
	}

	public JSONObject getCommandJson(String devicetype  , int index)
	{
		Map<String , Integer> m = null ;
		if ( IRemoteConstantDefine.DEVICE_TYPE_STB.equals(devicetype))
			m = STB_OPERATION_MAP;
		else
			m = TV_OPERATION_MAP;

		for ( String key : m.keySet())
		{
			if ( m.get(key) == index )
			{
				String[] s = key.split("_");
				JSONObject json = new JSONObject();
				json.put("operation", s[0]);
				if ( s.length == 2 )
					json.put("value", Integer.valueOf(s[1]));
				return json ;
			}
		}
		return null ;
	}
	
	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}


	private static Map<String , Integer> TV_OPERATION_MAP = new HashMap<String , Integer>();
	private static Map<String , Integer> STB_OPERATION_MAP = new HashMap<String , Integer>();

	static
	{
		TV_OPERATION_MAP.put("power", 11);
		TV_OPERATION_MAP.put("digital_0", 35);
		TV_OPERATION_MAP.put("digital_1", 15);
		TV_OPERATION_MAP.put("digital_2", 17);
		TV_OPERATION_MAP.put("digital_3", 19);
		TV_OPERATION_MAP.put("digital_4", 21);
		TV_OPERATION_MAP.put("digital_5", 23);
		TV_OPERATION_MAP.put("digital_6", 25);
		TV_OPERATION_MAP.put("digital_7", 27);
		TV_OPERATION_MAP.put("digital_8", 29);
		TV_OPERATION_MAP.put("digital_9", 31);
		TV_OPERATION_MAP.put("voice_-1", 1);
		TV_OPERATION_MAP.put("voice_0", 13);
		TV_OPERATION_MAP.put("voice_1", 9);
		TV_OPERATION_MAP.put("channel_1", 3);
		TV_OPERATION_MAP.put("channel_-1", 7);
		TV_OPERATION_MAP.put("up", 43);
		TV_OPERATION_MAP.put("down", 49);
		TV_OPERATION_MAP.put("left", 45);
		TV_OPERATION_MAP.put("right", 47);
		TV_OPERATION_MAP.put("back", 39);
		TV_OPERATION_MAP.put("ok", 41);
		TV_OPERATION_MAP.put("menu", 5);

		STB_OPERATION_MAP.put("power", 1);
		STB_OPERATION_MAP.put("digital_0", 23);
		STB_OPERATION_MAP.put("digital_1", 3);
		STB_OPERATION_MAP.put("digital_2", 5);
		STB_OPERATION_MAP.put("digital_3", 7);
		STB_OPERATION_MAP.put("digital_4", 9);
		STB_OPERATION_MAP.put("digital_5", 11);
		STB_OPERATION_MAP.put("digital_6", 13);
		STB_OPERATION_MAP.put("digital_7", 15);
		STB_OPERATION_MAP.put("digital_8", 17);
		STB_OPERATION_MAP.put("digital_9", 19);
		STB_OPERATION_MAP.put("voice_1", 37);
		//STB_OPERATION_MAP.put("voice_0", 0);
		STB_OPERATION_MAP.put("voice_-1", 39);
		STB_OPERATION_MAP.put("channel_1", 41);
		STB_OPERATION_MAP.put("channel_-1", 43);
		STB_OPERATION_MAP.put("up", 27);
		STB_OPERATION_MAP.put("down", 35);
		STB_OPERATION_MAP.put("left", 29);
		STB_OPERATION_MAP.put("right", 33);
		STB_OPERATION_MAP.put("back", 25);
		STB_OPERATION_MAP.put("ok", 31);
		STB_OPERATION_MAP.put("menu", 42);
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
}
