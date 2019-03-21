package com.iremote.infraredtrans.zwavecommand;

import java.util.Date;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.ZwaveDeviceEventPushValues;
import com.iremote.service.ZwaveDeviceEventPushValuesService;

public class ElectricEnergyReportProcessor extends ZWaveReportBaseProcessor {

	private String message = null ;

	public ElectricEnergyReportProcessor() 
	{
		super();
		super.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus()
	{
		byte[] bv = new byte[4] ;
		System.arraycopy(zrb.getCmd(), 4, bv, 0, bv.length);
		int iv = Utils.readint(bv);
		float fv = iv * 1f / 100f;
		
		if ( zrb.getDevice().getFstatus() != null 
				&& ( Math.abs(zrb.getDevice().getFstatus() - fv ) < 0.000001 ))
			return ;
		
		zrb.getDevice().setFstatus(fv);
		
		saveZwaveDeviceEventPushValues();
	}
	
	private void saveZwaveDeviceEventPushValues()
	{
		ZwaveDeviceEventPushValuesService svr = new ZwaveDeviceEventPushValuesService();
		ZwaveDeviceEventPushValues pv = svr.querybyZwavedeviceid(zrb.getDevice().getZwavedeviceid());

		if ( pv == null )
		{
			pv = new ZwaveDeviceEventPushValues();
			pv.setZwavedeviceid(zrb.getDevice().getZwavedeviceid());
			pv.setMetervalue(zrb.getDevice().getFstatus());
			pv.setLastsendtime(new Date());
			svr.save(pv);
			message = IRemoteConstantDefine.WARNING_TYPE_METER_STATUS;
		}
		else if ( pv.getLastsendtime() == null 
				|| pv.getLastsendtime().getTime() < ( System.currentTimeMillis() - 30 * 60 * 1000 )
				|| Math.abs(zrb.getDevice().getFstatus() - pv.getMetervalue()) > 0.5)
		{
			pv.setMetervalue(zrb.getDevice().getFstatus());
			pv.setLastsendtime(new Date());
			message = IRemoteConstantDefine.WARNING_TYPE_METER_STATUS;
		}
	}


	@Override
	public String getMessagetype() {
		return message;
	}

}
