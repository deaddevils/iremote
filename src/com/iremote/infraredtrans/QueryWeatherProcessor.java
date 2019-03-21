package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iremote.action.helper.PhoneUserBlueToothHelper;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JSONUtil;
import com.iremote.common.TagDefine;
import com.iremote.common.http.HttpUtil;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;

public class QueryWeatherProcessor implements IRemoteRequestProcessor
{
	private static Log log = LogFactory.getLog(QueryWeatherProcessor.class);
	
	private static Map<String , Integer> UV_INDEX_MAP = new HashMap<String , Integer>();
	private static Map<String , Integer> WEATHER_CODE_MAP = new HashMap<String , Integer>();
	
	private Integer pm2_5;
	private String weathercode;
	private Integer weather ;
	private Integer uvIndex ; 
	private String temperature;
	private Integer humidity;
	
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException 
	{
		if ( queryWeather(nbc) == false )
		{
			log.info("query weather failed or not set dress helper's address");
			return null ;
		}
		CommandTlv ct = new CommandTlv(TagDefine.COMMAND_CLASS_GATEWAY_103 , TagDefine.COMMAND_SUB_CLASS_GATEWAY_QUERY_WEATHER_RESPONSE);
		ct.addUnit(new TlvIntUnit(TagDefine.TAG_RESULT, ErrorCodeDefine.SUCCESS, TagDefine.TAG_LENGTH_2));
		
		if ( weather != null )
			ct.addUnit(new TlvIntUnit(TagDefine.TAG_WEATHER, weather, TagDefine.TAG_LENGTH_1));
		
		if ( humidity != null )
			ct.addUnit(new TlvIntUnit(TagDefine.TAG_RELATIVE_HUMIDITY, humidity, TagDefine.TAG_LENGTH_1));

		if ( pm2_5 != null )
			ct.addUnit(new TlvIntUnit(TagDefine.TAG_PM2_5, pm2_5, TagDefine.TAG_LENGTH_2));
		
		if ( uvIndex != null )
			ct.addUnit(new TlvIntUnit(TagDefine.TAG_UV_INDEX, uvIndex, TagDefine.TAG_LENGTH_1));

		if ( temperature != null )
			ct.addUnit(new TlvByteUnit(TagDefine.TAG_TEMPERATURE, temperature.getBytes()));
		
		return ct;
	}

	private boolean queryWeather(IConnectionContext nbc)
	{
		String areaId = getAreaId(nbc);
		return StringUtils.isBlank(areaId) ? queryWeather(nbc.getRemoteAddress()) : queryWeatherByArea(areaId);
	}

	private String getAreaId(IConnectionContext nbc) {
		String deviceid = nbc.getDeviceid();
		ZWaveDeviceService service = new ZWaveDeviceService();
		List<ZWaveDevice> zwaveDevices = service.querybydeviceid(deviceid);
		if (zwaveDevices.size() == 0) {
			return null;
		}
		ZWaveDevice zWaveDevice = zwaveDevices.get(0);
		DeviceCapability capability = PhoneUserBlueToothHelper.getCapability(
				zWaveDevice, IRemoteConstantDefine.DEVICE_CAPABILITY_DRESS_HELPER_AREA_ID);
		if (capability != null) {
			return capability.getCapabilityvalue();
		}
		return null;
	}

	private boolean queryWeatherByArea(String areaId) {
		String url = "http://ali-weather.showapi.com/area-to-weather";
		JSONObject json = new JSONObject();
		json.put("areaid", areaId);
		json.put("need3HourForcast", "0");
		json.put("needAlarm", "0");
		json.put("needHourData", "0");
		json.put("needIndex", "1");
		json.put("needMoreDay", "0");

		return query(json, url);
	}

	private boolean queryWeather(String ip)
	{
		String url = "http://ali-weather.showapi.com/ip-to-weather?needMoreDay=1";
		JSONObject json = new JSONObject();
		json.put("ip", ip);  

		return query(json, url);
	}

	private boolean query(JSONObject prams, String url) {
		JSONObject header = new JSONObject();
		header.put("Authorization", IRemoteConstantDefine.ALI_APPCODE);
		String rst = HttpUtil.httpGet(url, prams, header);
		return parseWeather(rst);
	}

	private boolean parseWeather(String rst) {
		JSONObject json;
		if ( StringUtils.isBlank(rst))
			return false;
		JSONObject jrst = JSON.parseObject(rst);
		json = jrst.getJSONObject("showapi_res_body");

		if ( ErrorCodeDefine.SUCCESS != json.getInteger("ret_code"))
			return false;

		String uv = JSONUtil.getString(json , "f1.ziwaixian");
		if ( StringUtils.isNotBlank(uv))
			uvIndex = UV_INDEX_MAP.get(uv);

		json = json.getJSONObject("now");
		pm2_5 = json.getJSONObject("aqiDetail").getInteger("pm2_5");

		weathercode = json.getString("weather_code");
		if ( StringUtils.isNotBlank(weathercode))
			weather = WEATHER_CODE_MAP.get(weathercode);

		temperature = json.getString("temperature");


		String sh = json.getString("sd");
		if ( StringUtils.isNotBlank(sh))
			humidity =  Integer.valueOf(sh.replaceAll("%", ""));

		return true ;
	}

	static 
	{
		UV_INDEX_MAP.put("\u6700\u5F31", 1);
		UV_INDEX_MAP.put("\u5F31", 2);
		UV_INDEX_MAP.put("\u4E2D\u7B49", 3);
		UV_INDEX_MAP.put("\u5F3A", 4);
		UV_INDEX_MAP.put("\u5F88\u5F3A", 5);
		
		WEATHER_CODE_MAP.put("00", 1);
		WEATHER_CODE_MAP.put("01", 2);
		WEATHER_CODE_MAP.put("02", 2);
		WEATHER_CODE_MAP.put("03", 3);
		WEATHER_CODE_MAP.put("04", 3);
		WEATHER_CODE_MAP.put("05", 3);
		WEATHER_CODE_MAP.put("06", 4);
		WEATHER_CODE_MAP.put("07", 3);
		WEATHER_CODE_MAP.put("08", 3);
		WEATHER_CODE_MAP.put("09", 3);
		WEATHER_CODE_MAP.put("10", 3);
		WEATHER_CODE_MAP.put("11", 3);
		WEATHER_CODE_MAP.put("12", 3);
		WEATHER_CODE_MAP.put("13", 4);
		WEATHER_CODE_MAP.put("14", 4);
		WEATHER_CODE_MAP.put("15", 4);
		WEATHER_CODE_MAP.put("16", 4);
		WEATHER_CODE_MAP.put("17", 4);
		WEATHER_CODE_MAP.put("18", 2);
		WEATHER_CODE_MAP.put("19", 3);
		WEATHER_CODE_MAP.put("20", 2);
		WEATHER_CODE_MAP.put("21", 3);
		WEATHER_CODE_MAP.put("22", 3);
		WEATHER_CODE_MAP.put("23", 3);
		WEATHER_CODE_MAP.put("24", 3);
		WEATHER_CODE_MAP.put("25", 3);
		WEATHER_CODE_MAP.put("26", 4);
		WEATHER_CODE_MAP.put("27", 4);
		WEATHER_CODE_MAP.put("28", 4);
		WEATHER_CODE_MAP.put("29", 2);
		WEATHER_CODE_MAP.put("30", 2);
		WEATHER_CODE_MAP.put("31", 2);
		WEATHER_CODE_MAP.put("53", 2);
		WEATHER_CODE_MAP.put("99", 2);
		WEATHER_CODE_MAP.put("301", 3);
		WEATHER_CODE_MAP.put("302", 4);

	}
	
	public static void main(String arg[])
	{
/*		QueryWeatherProcessor pro = new QueryWeatherProcessor();
		*//*pro.queryWeather("119.123.43.122");
		System.out.println(pro.uvIndex);
		System.out.println(pro.temperature);
		System.out.println(pro.weathercode);
		System.out.println(pro.humidity);
		System.out.println(pro.pm2_5);
		System.out.println(pro.weather);*//*
		pro.queryWeatherByArea("101210407");*/
	}
}
