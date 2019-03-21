package com.iremote.infraredtrans.zwavecommand;

import com.iremote.domain.ZwaveDeviceEventPushValues;
import com.iremote.service.ZwaveDeviceEventPushValuesService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

import java.util.Date;

public class MeterReportProcessor  extends ZWaveReportBaseProcessor 
{
	private static Log log = LogFactory.getLog(MeterReportProcessor.class);
			
	protected int metertype ;
	protected float metervalue ;
	protected int scale ;

	public MeterReportProcessor()
	{
		super();
		this.dontsavenotification();
	}
	
	@Override
	protected void updateDeviceStatus()
	{
		metertype = zrb.getCmd()[2] & 0x1f ;
		int t1 = zrb.getCmd()[2] & 0xff;
		int t2 = zrb.getCmd()[3] & 0xff ;
		int size = t2 & 0x7 ;
		scale = ( t2 >> 3 ) & 0x3 ;
		if ( (t1 & 0x80) == 0x80 )
			scale = scale | 0x04;
		
		int precision = ( t2 >> 5 ) & 0x7;
		
		metervalue = Utils.readsignedint(zrb.getCmd(), 4, 4 + size);
		metervalue /= Math.pow(10, precision);
		
		if ( log.isInfoEnabled())
			log.info(String.format("metertype:%d,scale:%d,metervalue:%f", metertype,scale,metervalue));
		
		updateStatus();

		checkIfPushMessage();
	}

	private void checkIfPushMessage() {
		if (metertype != 1 || scale != 0) {
			return;
		}

		ZwaveDeviceEventPushValuesService svr = new ZwaveDeviceEventPushValuesService();
		ZwaveDeviceEventPushValues pv = svr.querybyZwavedeviceid(zrb.getDevice().getZwavedeviceid());

		if ( pv == null ){
			pv = new ZwaveDeviceEventPushValues();
			pv.setZwavedeviceid(zrb.getDevice().getZwavedeviceid());
			pv.setMetervalue(metervalue);
			pv.setLastsendtime(new Date());
			svr.save(pv);
		} else if (pv.getLastsendtime() == null
				|| pv.getLastsendtime().getTime() < (System.currentTimeMillis() - 30 * 60 * 1000)
				|| Math.abs(metervalue - pv.getMetervalue()) > 0.5) {
			pv.setMetervalue(metervalue);
			pv.setLastsendtime(new Date());
		} else {
			this.dontpusmessage();
		}
	}

	protected void updateStatus()
	{
		String ss = zrb.getDevice().getStatuses();
		if ( StringUtils.isBlank(ss))
			ss = Utils.getDeviceDefaultStatuses(zrb.getDevice().getDevicetype());
		
		if ( metertype == 1 )  //Electric meter
		{
			if ( scale == 0 ) //kWh
				ss = Utils.setJsonArrayValue(ss, 0, metervalue);
			else 
				this.dontpusmessage();
		}
		else 
			this.dontpusmessage();
		zrb.getDevice().setStatuses(ss);

	}
	
	@Override
	public String getMessagetype()
	{
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}
