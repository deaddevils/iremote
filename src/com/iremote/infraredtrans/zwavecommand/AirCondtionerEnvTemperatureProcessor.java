package com.iremote.infraredtrans.zwavecommand;

import com.alibaba.fastjson.JSONArray;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Temperature;
import com.iremote.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.transaction.NotSupportedException;

public class AirCondtionerEnvTemperatureProcessor  extends ZWaveReportBaseProcessor{
	private Log log = LogFactory.getLog(AirCondtionerEnvTemperatureProcessor.class);

	public AirCondtionerEnvTemperatureProcessor() {
		super();
		super.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus() 
	{
		Temperature temperature = new Temperature(zrb.getCmd()).calculate();
		JSONArray json = DeviceHelper.getZWaveDeviceStatusesJSONArray(zrb.getDevice().getStatuses());
		int valuePos;
		try {
			valuePos = getValuePos(zrb.getDevice().getDevicetype());
		} catch (NotSupportedException e) {
			log.error(e.getMessage(), e);
			return;
		}
		pushmessage = json.size() < valuePos || json.getInteger(valuePos - 1) != temperature.getC();
		JSONArray json0 = Utils.appendValueIntoJSONArray(json.toJSONString(), temperature.getC(), valuePos);
		JSONArray json1 = Utils.appendValueIntoJSONArray(json0.toJSONString(), temperature.getF(), valuePos + 1);
		zrb.getDevice().setStatuses(json1.toJSONString());

		NotificationHelper.pushTemperature(this.zrb.getDevice(), temperature.getC() ,temperature.getF() ,zrb.getReporttime());
	}

	private int getValuePos(String deviceType) throws NotSupportedException {
		//the return value is the statuses's index + 1
		switch (deviceType) {
			case IRemoteConstantDefine.DEVICE_TYPE_DEHUMIDIFY:
				return 10;
			case IRemoteConstantDefine.DEVICE_TYPE_FRESH_AIR_IN:
				return 4;
			case IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER:
				return 5;
			case IRemoteConstantDefine.DEVICE_TYPE_HEATING_CONTROLLER:
				return 3;
			case IRemoteConstantDefine.DEVICE_TYPE_DRESS_HELPER:
				return 2;
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
}
