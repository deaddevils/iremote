package com.iremote.infraredtrans.zwavecommand;

import com.alibaba.fastjson.JSONArray;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.transaction.NotSupportedException;

public class EnvHumidityReportProcessor  extends ZWaveReportBaseProcessor
{
	private Log log = LogFactory.getLog(EnvHumidityReportProcessor.class);

	public EnvHumidityReportProcessor() {
		super();
		super.dontsavenotification();
	}
	
	@Override
	protected void updateDeviceStatus()
	{
		int humidity = (int)processHumidity(zrb.getCmd());
		JSONArray json = DeviceHelper.getZWaveDeviceStatusesJSONArray(zrb.getDevice().getStatuses());
		int valuePos;
		try {
			valuePos = getValuePos(zrb.getDevice().getDevicetype());
		} catch (NotSupportedException e) {
			log.error(e.getMessage(), e);
			return;
		}
		pushmessage = json.size() < valuePos || json.getInteger(valuePos - 1) != humidity;
		JSONArray json0 = Utils.appendValueIntoJSONArray(json.toJSONString(), humidity, valuePos);
		zrb.getDevice().setStatuses(json0.toJSONString());

		NotificationHelper.pushHumidity(this.zrb.getDevice(), humidity ,zrb.getReporttime());
	}

	private int getValuePos(String deviceType) throws NotSupportedException {
		//the return value is the statuses's index + 1
		switch (deviceType) {
			case IRemoteConstantDefine.DEVICE_TYPE_DEHUMIDIFY:
				return 12;
			case IRemoteConstantDefine.DEVICE_TYPE_FRESH_AIR_IN:
				return 6;
			case IRemoteConstantDefine.DEVICE_TYPE_DRESS_HELPER:
				return 4;
			default:
				throw new NotSupportedException("Don't know the index by this device type");
		}
	}

	@Override
	public String getMessagetype() {
		if (pushmessage) {
			return IRemoteConstantDefine.WARNING_TYPE_DEVICE_ASSOCIATION;
		}
		return null;
	}

	private static float processHumidity(byte[] cmd){
		int tmp = cmd[3] & 0xff ;
		int size = tmp & 0x7 ;
//		int scale = ( tmp >> 3 ) & 0x3 ;
		int precision = ( tmp >> 5 ) & 0x7;

		float humidity = Utils.readsignedint(cmd, 4, 4 + size);
		humidity /= Math.pow(10, precision);

		return humidity;
	}
}
