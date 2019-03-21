package com.iremote.device.operate;

import java.util.List;

import com.iremote.domain.InfraredDevice;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;

public interface IOperationTranslator
{
	void setZWavedevice(ZWaveDevice zd);
	void setInfrareddevice(InfraredDevice id);
	void setChannelid(int channelid);
	String getMajorType();
	String getDevicetype();
	void setDeviceStatus(String devicestatus);
	String getDeviceStatus();
	String getOperator();
	void setCommandjson(String json);
	String getCommandjson();
	Integer getValue();
	void setStatus(Integer status);
	String getStatus();
	void setCommand(byte[] command);
	byte[] getCommand();
	byte[] getCommands();
	void setOperationType(Integer operationtype);
	List<CommandTlv> getCommandTlv();
}
