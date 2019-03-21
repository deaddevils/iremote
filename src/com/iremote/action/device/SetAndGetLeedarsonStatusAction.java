package com.iremote.action.device;

import com.iremote.common.commandclass.CommandUtil;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.opensymphony.xwork2.Action;

public class SetAndGetLeedarsonStatusAction extends DeviceOperationBaseAction{
	private String hope;//set get
	private String order;//1:sensitivity; 2:silentseconds
	private int sensitivity;//0-10  0 means turnoff
	private int silentseconds;//5-255*60   actual 5-600
	public String execute(){
		super.execute();
		return Action.SUCCESS;
	}
	
	@Override
	protected CommandTlv[] createCommandTlv() {
		if("set".equals(hope)&&"1".equals(order)){
			return new CommandTlv[]{CommandUtil.createSetLeedarsonSensitivityCommand(device.getNuid(),(byte)sensitivity)};
		}else if("set".equals(hope)&&"2".equals(order)){
			return new CommandTlv[]{CommandUtil.createSetLeedarsonSilentsecondsCommand(device.getNuid(),silentseconds)};
		}else if("get".equals(hope)&&"1".equals(order)){
			return new CommandTlv[]{CommandUtil.createGetLeedarsonSensitivityCommand(device.getNuid())};
		}else if("get".equals(hope)&&"2".equals(order)){
			return new CommandTlv[]{CommandUtil.createGetLeedarsonSilentsecondsCommand(device.getNuid())};
		}
		return null;
	}

	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}
	public void setSilentseconds(int silentseconds) {
		this.silentseconds = silentseconds;
	}

	public void setHope(String hope) {
		this.hope = hope;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
}
