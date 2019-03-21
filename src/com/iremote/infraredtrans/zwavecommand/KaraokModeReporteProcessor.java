package com.iremote.infraredtrans.zwavecommand;

import java.util.Arrays;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.service.ZWaveDeviceService;

public class KaraokModeReporteProcessor extends ZWaveReportBaseProcessor{

	public int mode;
	public ZWaveDevice zWaveDevice;
	
	public KaraokModeReporteProcessor() {
		super();
		super.dontsavenotification();
	}
	
	@Override
	public String getMessagetype() {
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}
	
	@Override
	protected void updateDeviceStatus() {
		ZWaveDeviceService zWaveDeviceService = new ZWaveDeviceService();
		zWaveDevice = zrb.getDevice();
		zWaveDevice.setStatuses(getStatuses());
		
		zWaveDeviceService.saveOrUpdate(zWaveDevice);
		
		sendVolum(IRemoteConstantDefine.DEVICE_KARAOK_VOLUME_MUSIC);
		sendVolum(IRemoteConstantDefine.DEVICE_KARAOK_VOLUME_MIC);
		sendVolum(IRemoteConstantDefine.DEVICE_KARAOK_VOLUME_EFFECTS);
		
	}
	
	public void sendVolum(int volumType){
		int nuid = zWaveDevice.getNuid();
		
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvIntUnit(71 , nuid , CommandUtil.getnuIdLenght(nuid)));
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x60,(byte)0x0d,0x00,(byte)volumType,0x26,0x02}));
		
		SynchronizeRequestHelper.asynchronizeRequest(zWaveDevice.getDeviceid(), ct , 8);	
	}
	
	public String getStatuses(){
		mode = zrb.getCommandvalue().getCmd()[2];
		String statuses = zWaveDevice.getStatuses();
		
		String[] statusess = null;
		if(statuses == null || statuses.length() < 1){
			statuses = IRemoteConstantDefine.DEVICE_KARAOK_DEFAULT_STATUES;
		}
		statuses = statuses.substring(1, statuses.length() - 1);	
		statusess = statuses.split(",");
		
		statusess[0] = String.valueOf(mode);
		
		return Arrays.toString(statusess).replace(" ", "");
	}
}
