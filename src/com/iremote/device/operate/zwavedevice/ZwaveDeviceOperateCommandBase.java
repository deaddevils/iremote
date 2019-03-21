package com.iremote.device.operate.zwavedevice;

import com.iremote.device.operate.IDeviceOperateCommand;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;

public abstract class ZwaveDeviceOperateCommandBase implements IDeviceOperateCommand {

	protected ZWaveDevice zwavedevice;
	protected byte channel ;
	protected byte status ;

	public abstract CommandTlv createCommand();

	public void setZwavedevice(ZWaveDevice zwavedevice) {
		this.zwavedevice = zwavedevice;
	}

	public void setChannel(byte channel) {
		this.channel = channel;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
	
	
}
