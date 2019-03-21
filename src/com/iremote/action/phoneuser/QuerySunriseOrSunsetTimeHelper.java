package com.iremote.action.phoneuser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.http.HttpUtil;
import com.iremote.domain.City;
import com.iremote.domain.Province;
import com.iremote.domain.Region;
import com.iremote.service.CityService;
import com.iremote.service.ProvinceService;
import com.iremote.service.RegionService;

public class QuerySunriseOrSunsetTimeHelper {

	private static String key = "aV3UzkErYHEF1Khi34hrpfINLJGGuIsd";
	private static Log log = LogFactory.getLog(QuerySunriseOrSunsetTimeHelper.class);

	public static long querySunriseOrSunsetTime(int cityid, int timetype) {
		long l = querySunriseOrSunsetTime(timetype);
		return l==0?querylongtime(cityid,timetype):l;

	}
	public static long querylongtime(int cityid,int timetype){
		try {
			RegionService rs = new RegionService();
			ProvinceService ps = new ProvinceService();
			CityService cs = new CityService();
			City city = cs.queryByCityid(cityid);
			Province province = ps.queryByProvinceid(city.getProvinceid());
			Region region = rs.queryByRegionid(province.getRegionid());
			String locationkey ;
			if(StringUtils.isNotBlank(city.getLocationkey())){
				locationkey = city.getLocationkey();
			}else{
				String url = "http://dataservice.accuweather.com/locations/v1/cities/" + region.getCountrycode() + "/"
						+ province.getAdmincode() + "/search";
				JSONObject jsonparameter = new JSONObject();
				jsonparameter.put("apikey", key);
				jsonparameter.put("q", city.getName());
				jsonparameter.put("language", "en-us");
				jsonparameter.put("details", true);
				String keyjson;
				keyjson = HttpUtil.httpGet(url, jsonparameter, null);
				if("[]".equals(keyjson)){
					String capitalName = cs.queryByProvinceid(city.getProvinceid()).get(0).getName();
					jsonparameter.put("q", capitalName);
					keyjson = HttpUtil.httpGet(url, jsonparameter, null);
				}
				JSONArray arraykey = (JSONArray) JSONArray.parse(keyjson);
				JSONObject parsekey = (JSONObject) arraykey.get(0);
				locationkey = parsekey.getString("Key");
				city.setLocationkey(locationkey);
			}
			String url2 = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/" + locationkey;
			JSONObject jsonpar = new JSONObject();
			jsonpar.put("apikey", key);
			jsonpar.put("language", "en-us");
			jsonpar.put("details", true);
			String resultjson = HttpUtil.httpGet(url2, jsonpar, null);

			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(resultjson);
			net.sf.json.JSONObject dailyforecastsjson = (net.sf.json.JSONObject) jsonObject
					.getJSONArray("DailyForecasts").get(0);

			String sunstring = dailyforecastsjson.getString("Sun");
			net.sf.json.JSONObject sunjson = net.sf.json.JSONObject.fromObject(sunstring);
			String risestr = sunjson.getString("Rise");
			risestr = risestr.substring(0, 19).replace("T", " ");
			String setstr = sunjson.getString("Set");
			String userzone = setstr.substring(19);
			setstr = setstr.substring(0, 19).replace("T", " ");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date sunsetdate = sdf.parse(setstr);
			Date sunrisedate = sdf.parse(risestr);
			long risetime = sunrisedate.getTime();
			long settime = sunsetdate.getTime();

			TimeZone rtz = TimeZone.getDefault();
			int systemzonenumber = rtz.getRawOffset() / 60 / 1000 / 60;
			int zonenumber = Integer.parseInt(userzone.substring(0, 3));
			long aparttime = (systemzonenumber - zonenumber) * 60 * 60 * 1000;
			long localtime;
			if (timetype == 1) {
				localtime = risetime + aparttime;
			} else {
				localtime = settime + aparttime;
			}

			return localtime;
		} catch (Throwable e) {
			log.error("query weather data failed !",e);
			return 0;
		}
	}
	
	public static long querySunriseOrSunsetTime(int timetype) {
		try {
			
			String url2 = "https://dev.isurpass.com.cn/iremotestatic/sunrisevirtualdata.json";
			JSONObject jsonpar = new JSONObject();

			String resultjson = HttpUtil.httpGet(url2, jsonpar, null);

			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(resultjson);
			
			int switchresult = jsonObject.getInt("Switch");
			if(switchresult!=1){
				return 0;
			}
			
			net.sf.json.JSONObject dailyforecastsjson = (net.sf.json.JSONObject) jsonObject
					.getJSONArray("DailyForecasts").get(0);

			String sunstring = dailyforecastsjson.getString("Sun");
			net.sf.json.JSONObject sunjson = net.sf.json.JSONObject.fromObject(sunstring);
			String risestr = sunjson.getString("Rise");
			risestr = risestr.substring(0, 19).replace("T", " ");
			String setstr = sunjson.getString("Set");
			String userzone = setstr.substring(19);
			setstr = setstr.substring(0, 19).replace("T", " ");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date sunsetdate = sdf.parse(setstr);
			Date sunrisedate = sdf.parse(risestr);
			long risetime = sunrisedate.getTime();
			long settime = sunsetdate.getTime();

			TimeZone rtz = TimeZone.getDefault();
			int systemzonenumber = rtz.getRawOffset() / 60 / 1000 / 60;
			int zonenumber = Integer.parseInt(userzone.substring(0, 3));
			long aparttime = (systemzonenumber - zonenumber) * 60 * 60 * 1000;
			long localtime = risetime + aparttime;
			if (timetype == 1) {
				localtime = risetime + aparttime;
			} else {
				localtime = settime + aparttime;
			}

			return localtime;
		} catch (Throwable e) {
			log.error("virtual weather json data is missing !");
			return 0;
		}
	}

}
