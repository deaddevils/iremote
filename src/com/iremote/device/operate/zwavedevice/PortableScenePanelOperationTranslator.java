package com.iremote.device.operate.zwavedevice;

import java.util.List;

import com.iremote.device.operate.OperationTranslatorBase;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class PortableScenePanelOperationTranslator extends OperationTranslatorBase
{

	@Override
	public String getDeviceStatus()
	{
		return devicestatus;
	}

	@Override
	public String getCommandjson()
	{
		return null;
	}

	@Override
	public Integer getValue()
	{
		return Integer.valueOf(devicestatus);
	}

	@Override
	public List<CommandTlv> getCommandTlv()
	{
		return null;
	}

	@Override
	public byte[] getCommands()
	{
		return null;
	}

	@Override
	public byte[] getCommand()
	{
		return null;
	}

}
