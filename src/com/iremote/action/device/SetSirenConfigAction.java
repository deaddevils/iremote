package com.iremote.action.device;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class SetSirenConfigAction extends DeviceOperationBaseAction{
	private String order;//1:sound; 2:time; 3:music
	private int config;//1:1-3; 2:0-3 255; 3:1-10
	private int doalarm;//1 alarm 2 cancle
	public String execute(){
		super.execute();
		if(resultCode == ErrorCodeDefine.SUCCESS){
			ZWaveDeviceService zds = new ZWaveDeviceService();
			ZWaveDevice zd = zds.query(zwavedeviceid);
			if("1".equals(order)){
				zd.setStatuses(Utils.setJsonArrayValue(zd.getStatuses(), 5, config));
			}else if("2".equals(order)){
				zd.setStatuses(Utils.setJsonArrayValue(zd.getStatuses(), 7, config));
			}else if("3".equals(order)){
				zd.setStatuses(Utils.setJsonArrayValue(zd.getStatuses(), 6, config));
			}
		}
		return Action.SUCCESS;
	}
	
	@Override
	protected CommandTlv[] createCommandTlv() {
		if("1".equals(order)){
			return new CommandTlv[]{CommandUtil.createSetSirenSoundCommand(device.getNuid(),(byte)config)};
		}else if("2".equals(order)){
			return new CommandTlv[]{CommandUtil.createSetSirenTimeCommand(device.getNuid(),(byte)config)};
		}else if("3".equals(order)){
			return new CommandTlv[]{CommandUtil.createSetSirenMusicCommand(device.getNuid(),(byte)config)};
		}
		if(doalarm==1){
			return new CommandTlv[]{CommandUtil.createAlarmCommand(device.getNuid(),(byte)0xff)};
		}else if(doalarm==2){
			return new CommandTlv[]{CommandUtil.createAlarmCommand(device.getNuid(),(byte)0x00)};
		}
		return null;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getConfig() {
		return config;
	}

	public void setConfig(int config) {
		this.config = config;
	}

	public void setDoalarm(int doalarm) {
		this.doalarm = doalarm;
	}


	
}
