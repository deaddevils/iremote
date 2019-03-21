package com.iremote.action.device;

import com.iremote.common.commandclass.CommandUtil;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.opensymphony.xwork2.Action;

public class SetEfanceConfigAction extends DeviceOperationBaseAction{
	private int voltagechoose;//high:1 low:0
	private int frequency ;//1-4
	private int sensitivity ;//1-8
	private int baojingmenxian;//1-8
	private int delaytimes ; //0-5
	private int alarmtimes ; //5-999
	private int highvoltage ;//3800-6400
	private int lowvoltage ;//800-1800
	private int calibrate;
	public String execute(){
		super.execute();
		return Action.SUCCESS;
	}
	
	@Override
	protected CommandTlv[] createCommandTlv() {
		if(calibrate==1){//jiaozhun
			return new CommandTlv[]{CommandUtil.createCalibrateCommand(device.getNuid())};
		}else{
			if(lowvoltage!=0){
				return new CommandTlv[]{CommandUtil.createLowVoltageCommand(device.getNuid(),(byte)(lowvoltage/256),(byte)(lowvoltage%256))};
			}else{
				return new CommandTlv[] { CommandUtil.createBatchConfigCommand(device.getNuid(), voltagechoose,
						frequency, sensitivity, baojingmenxian, delaytimes, alarmtimes,highvoltage) };
			}
		}
	}

	public int getVoltagechoose() {
		return voltagechoose;
	}

	public void setVoltagechoose(int voltagechoose) {
		this.voltagechoose = voltagechoose;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}

	public int getBaojingmenxian() {
		return baojingmenxian;
	}

	public void setBaojingmenxian(int baojingmenxian) {
		this.baojingmenxian = baojingmenxian;
	}

	public int getDelaytimes() {
		return delaytimes;
	}

	public void setDelaytimes(int delaytimes) {
		this.delaytimes = delaytimes;
	}

	public int getAlarmtimes() {
		return alarmtimes;
	}

	public void setAlarmtimes(int alarmtimes) {
		this.alarmtimes = alarmtimes;
	}

	public int getHighvoltage() {
		return highvoltage;
	}

	public void setHighvoltage(int highvoltage) {
		this.highvoltage = highvoltage;
	}

	public int getLowvoltage() {
		return lowvoltage;
	}

	public void setLowvoltage(int lowvoltage) {
		this.lowvoltage = lowvoltage;
	}

	public int getCalibrate() {
		return calibrate;
	}

	public void setCalibrate(int calibrate) {
		this.calibrate = calibrate;
	}
	
}
