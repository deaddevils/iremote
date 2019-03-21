package com.iremote.device.operate;

import com.iremote.infraredtrans.tlv.CommandTlv;

public interface IDeviceOperateCommand 
{
	CommandTlv createCommand();
}
