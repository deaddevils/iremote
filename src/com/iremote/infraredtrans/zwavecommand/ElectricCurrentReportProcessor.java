package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.Utils;
import com.iremote.domain.ZwaveDeviceEventPushValues;
import com.iremote.service.ZwaveDeviceEventPushValuesService;

public class ElectricCurrentReportProcessor extends ZWaveReportBaseProcessor {

	public ElectricCurrentReportProcessor() {
		super();
		super.dontsavenotification();
		super.dontpusmessage();
	}

	@Override
	protected void updateDeviceStatus()
	{
		if ( zrb.getCmd() == null || zrb.getCmd().length < 8)
			return ;

		byte[] bv = new byte[4] ;
		System.arraycopy(zrb.getCmd(), 4, bv, 0, bv.length);
		int iv = Utils.readint(bv);

		float fv = iv * 1f / 100f;
		
		ZwaveDeviceEventPushValuesService svr = new ZwaveDeviceEventPushValuesService();
		ZwaveDeviceEventPushValues pv = svr.querybyZwavedeviceid(zrb.getDevice().getZwavedeviceid());
		if ( pv == null )
			return ;
		pv.setFloatappendparam(fv);
	}

	@Override
	public String getMessagetype() {
		return null;
	}
}
