package com.iremote.infraredtrans.zwavecommand;


import java.util.Date;

import com.iremote.common.Utils;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.ZWaveDeviceService;

public class NEOSensitivityReportProcessor extends ZWaveReportBaseProcessor{
	protected int sensitivity;
	protected int zwavedeviceid;
	protected Date reporttime;
	
	@Override
	protected void updateDeviceStatus() {
		zwavedeviceid = zrb.getDevice().getZwavedeviceid();
		sensitivity = zrb.getCmd()[4] & 0xff;
		reporttime = zrb.getReporttime();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		DeviceOperationStepsService doss = new DeviceOperationStepsService();
		ZWaveDevice zd = zds.query(zwavedeviceid);
		zd.setStatuses(Utils.setJsonArrayValue(zrb.getDevice().getStatuses(), 5, sensitivity));
		zds.update(zd);
		DeviceOperationSteps qdos = doss.querybyzwavedeviceidandtype(zwavedeviceid, DeviceOperationType.getNEOSensitivity);
		if(qdos==null){
			DeviceOperationSteps dos = new DeviceOperationSteps();
			dos.setDeviceid(zrb.getDeviceid());
			dos.setStarttime(reporttime);
			dos.setExpiretime(reporttime);
			dos.setZwavedeviceid(zwavedeviceid);
			dos.setOptype(DeviceOperationType.getNEOSensitivity.ordinal());
			dos.setStatus(1);
			dos.setFinished(true);
			doss.saveOrUpdate(dos);
		}else{
			qdos.setStarttime(reporttime);
			qdos.setExpiretime(reporttime);
			qdos.setFinished(true);
			qdos.setStatus(1);
			//doss.update(qdos);
			doss.saveOrUpdate(qdos);
		}
	}

	@Override
	public String getMessagetype() {
		return null;
	}
	
	

	


}
