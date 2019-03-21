package com.iremote.action.device;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class ReadEfanceConfigAction{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int channel;
	private int zwavedeviceid;
	private ZWaveDevice device;
	private Byte[] SINGLE_EFANCE_BATCH_REPORT = new Byte[] { (byte) 0x70, 0x09, 0x00,0x01, 0x07,0x00,0X02};
	private Byte[] SINGLE_EFANCE_LOW_VOLAGE_REPORT = new Byte[] { (byte) 0x70, 0x06, 0x08, 0x02};
	private Byte[] SINGLE_EFANCE_CURRENT_VOLAGE_REPORT = new Byte[] { (byte) 0x31, 0x05, (byte)0x0f, 0x02};
	
	private Byte[] DOUBLE_EFANCE_BATCH_REPORT = new Byte[] {(byte) 0x70, 0x09, 0x00,0x01, 0x07,0x00,0X02 };
	private Byte[] DOUBLE_EFANCE_LOW_VOLAGE_REPORT = new Byte[] {(byte) 0x80, 0x07, 0x00, (byte) 0x90, (byte) 0x10, 0x01,0x03 };
	private Byte[] DOUBLE_EFANCE_CURRENT_VOLAGE_REPORT = new Byte[] {(byte) 0x31, 0x05, (byte)0x0f, 0x02 };

	public String execute(){
		ZWaveDeviceService zds = new ZWaveDeviceService();
		if ( device == null ){
			if ( zwavedeviceid != 0 )
				device = zds.query(zwavedeviceid);
		}		
		if ( device == null ){
			resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
			return Action.SUCCESS;
		}
		if(channel==2){
			DOUBLE_EFANCE_BATCH_REPORT[2] = (byte) channel;
			DOUBLE_EFANCE_LOW_VOLAGE_REPORT[2] = (byte) channel;
			DOUBLE_EFANCE_CURRENT_VOLAGE_REPORT[2] = (byte) channel;
		}
		
		
		if(channel==0){
			ZwaveReportRequestManager.sendRequest(device.getDeviceid(),device.getNuid(), 
					createCommandTlv(3), SINGLE_EFANCE_CURRENT_VOLAGE_REPORT, 5, 0).getResponse();
			ZwaveReportRequestManager.sendRequest(device.getDeviceid(),device.getNuid(), 
					createCommandTlv(2), SINGLE_EFANCE_LOW_VOLAGE_REPORT, 5, 0).getResponse();
			ZwaveReportRequestManager.sendRequest(device.getDeviceid(),device.getNuid(), 
					createCommandTlv(1), SINGLE_EFANCE_BATCH_REPORT, 5, 0).getResponse();
		}else{
			ZwaveReportRequestManager.sendRequest(device.getDeviceid(),device.getNuid(), 
					createCommandTlv(3), DOUBLE_EFANCE_CURRENT_VOLAGE_REPORT, 5, 0).getResponse();
			ZwaveReportRequestManager.sendRequest(device.getDeviceid(),device.getNuid(), 
					createCommandTlv(2), DOUBLE_EFANCE_LOW_VOLAGE_REPORT, 5, 0).getResponse();
			ZwaveReportRequestManager.sendRequest(device.getDeviceid(),device.getNuid(), 
					createCommandTlv(1), DOUBLE_EFANCE_BATCH_REPORT, 5, 0).getResponse();
		}

		return Action.SUCCESS;
	}


	private CommandTlv createCommandTlv(int order) {
		if(order == 1){
			if(channel > 0){
				return CommandUtil.createBatchReadEfanceConfigCommand(device.getNuid(),channel);
			}else{
				return CommandUtil.createBatchReadEfanceConfigCommand(device.getNuid());
			}
		}else if(order == 2 ){
			if(channel > 0){
				return CommandUtil.createReadEfanceConfigCommand(device.getNuid(),channel);
			}else{
				return CommandUtil.createReadEfanceConfigCommand(device.getNuid());
			}
		}else if(order == 3){
			if(channel > 0){
				return CommandUtil.createReadEfanceVolageCommand(device.getNuid(),channel);
			}else{
				return CommandUtil.createReadEfanceVolageCommand(device.getNuid());
			}
		}
		return null;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	
}
