package com.iremote.common.http;

import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.sf.json.JSONArray;



public class HttpUtil {

	private static Log log = LogFactory.getLog(HttpUtil.class);
	
	public static String httpGet(String url , JSONObject parameter , JSONObject header)
	{
		try 
		{
			log.info(url);
			if ( log.isInfoEnabled() && parameter != null )
				log.info(parameter.toJSONString());
			
			StringBuffer sb = new StringBuffer();
			if ( parameter != null )
				for ( String key : parameter.keySet())
				{
					if ( sb.length() != 0 )
						sb.append("&");
					sb.append(key).append("=").append(URLEncoder.encode(parameter.getString(key) , "UTF-8"));
				}
			
			if ( url.contains("?"))
				url += "&" + sb.toString();
			else 
				url += "?" + sb.toString();
			
			HttpClient httpclient = createHttpClient(url);
			HttpGet httpget = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
			httpget.setConfig(requestConfig);
			
			if ( header != null )
				for ( String key : header.keySet())
					httpget.addHeader(key, header.getString(key));
						
			HttpResponse response = httpclient.execute(httpget);
						
			HttpEntity entity = response.getEntity();

			String rst = EntityUtils.toString(entity , "UTF-8");
			log.info(rst);
			
			return rst;
		}
		catch(SocketTimeoutException e)
		{
			log.warn(e.getMessage());
		}
		catch(Throwable t)
		{
			log.error(t.getMessage(), t);
		}
		return "";
	}
	
	public static String httprequest(String url , String parameter)
	{
		HttpClient httpclient = createHttpClient(url);
		try 
		{
			log.info(url);
			if ( log.isInfoEnabled() )
				log.info(parameter);

			HttpPost httpost = new HttpPost(url);
						
			httpost.setEntity(new StringEntity(parameter, "UTF-8"));
			
			HttpResponse response = httpclient.execute(httpost);
						
			HttpEntity entity = response.getEntity();

			String rst = EntityUtils.toString(entity , "UTF-8");
			log.info(rst);
			
			return rst;
		}
		catch(Throwable t)
		{
			log.error(t.getMessage(), t);
		}
		finally 
		{
			
		}
		return "";
	}
	
	public static String httprequest(String url , JSONObject parameter)
	{
		HttpClient httpclient = createHttpClient(url);
		try 
		{
			log.info(url);
			if ( log.isInfoEnabled() )
				log.info(parameter.toJSONString());

			HttpPost httpost = new HttpPost(url);
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			
			for ( String key : parameter.keySet())
				nvps.add(new BasicNameValuePair(key, parameter.getString(key)));
						
			httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			
			HttpResponse response = httpclient.execute(httpost);
						
			HttpEntity entity = response.getEntity();

			String rst = EntityUtils.toString(entity , "UTF-8");
			log.info(rst);
			
			return rst;
		}
		catch(Throwable t)
		{
			log.error(t.getMessage(), t);
		}
		finally 
		{
			
		}
		return "";
	}
	
	public static HttpClient createHttpClient(String url)
	{
		if ( url.startsWith("https"))
		{
			RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();  
			try {  
  			    SSLContext sslContext = SSLContext.getInstance("TLS"); 
  			    sslContext.init(null, new TrustManager[] { new AnyTrustManager() }, null);
			    LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext);  
			    registryBuilder.register("https", sslSF);  
			} catch (NoSuchAlgorithmException e) {  
			    throw new RuntimeException(e);  
			} catch (KeyManagementException e) {
				 throw new RuntimeException(e);  
			}  
			Registry<ConnectionSocketFactory> registry = registryBuilder.build();  
			
			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);  

			return HttpClientBuilder.create().setConnectionManager(connManager).build();
		}
		else 
			return HttpClientBuilder.create().build();
	}
	
	public static void main(String arg[])
	{
		/*JSONObject json = new JSONObject();
		json.put("ip", "113.118.246.193");
		
		JSONObject header = new JSONObject();
		header.put("Authorization", "APPCODE 78ad21d98ef6429291a0f22417375520");
		String rst = HttpUtil.httpGet("http://ali-weather.showapi.com/ip-to-weather", json , header);*/
		String key = "7xTGA4jWlKSVs3NGjrV9tC0W30TqbJjw";
		String url1 = "http://www.ip-api.com/json/47.254.33.167";
		String url2 = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search";
		String url3 = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/";
		String rst = HttpUtil.httprequest(url1, "");
		System.out.println(rst);
		JSONObject parselocation = JSON.parseObject(rst);
		String lon = parselocation.getString("lon");
		String lat = parselocation.getString("lat");
		System.out.println(lat+":"+lon);
		JSONObject jsonpar = new JSONObject();
		jsonpar.put("apikey", key);
		jsonpar.put("q", lat+","+lon);
		//url2 += "?apikey="+key+"&q="+ lat+","+lon;
		String keyjson = HttpUtil.httpGet(url2, jsonpar, null);
		JSONObject parsekey = JSON.parseObject(keyjson);
		String keystr = parsekey.getString("Key");
		url3 += keystr;
		String language = "en-us";// fr-ca en-us zh-cn
		JSONObject jsonparameter = new JSONObject();
		jsonparameter.put("apikey", key);
		jsonparameter.put("language", language);
		jsonparameter.put("details", true);
		String resultjson = /*HttpUtil.httpGet(url3, jsonparameter,null)*/"";
		//TODO The allowed number of requests has been exceeded
		resultjson = "{'Headline':{'Category':'cold','EndEpochDate':1539313200,'EffectiveEpochDate':1539097200,'Severity':7,'Text':'Cool from tomorrow to Thursday','EndDate':'2018-10-11T20:00:00-07:00','Link':'http://www.accuweather.com/en/us/highlands-baywood-park-ca/94402/daily-weather-forecast/2621905?lang=en-us','EffectiveDate':'2018-10-09T08:00:00-07:00','MobileLink':'http://m.accuweather.com/en/us/highlands-baywood-park-ca/94402/extended-weather-forecast/2621905?lang=en-us'},'DailyForecasts':[{'Temperature':{'Minimum':{'UnitType':18,'Value':55.0,'Unit':'F'},'Maximum':{'UnitType':18,'Value':76.0,'Unit':'F'}},'Night':{'RainProbability':0,'Wind':{'Speed':{'UnitType':9,'Value':4.6,'Unit':'mi/h'},'Direction':{'English':'SW','Degrees':234,'Localized':'SW'}},'SnowProbability':0,'Snow':{'UnitType':1,'Value':0.0,'Unit':'in'},'TotalLiquid':{'UnitType':1,'Value':0.0,'Unit':'in'},'ShortPhrase':'Clear','Ice':{'UnitType':1,'Value':0.0,'Unit':'in'},'HoursOfRain':0.0,'HoursOfIce':0.0,'Rain':{'UnitType':1,'Value':0.0,'Unit':'in'},'PrecipitationProbability':0,'ThunderstormProbability':0,'IceProbability':0,'IconPhrase':'Clear','CloudCover':7,'LongPhrase':'Clear','Icon':33,'WindGust':{'Speed':{'UnitType':9,'Value':9.2,'Unit':'mi/h'},'Direction':{'English':'W','Degrees':275,'Localized':'W'}},'HoursOfSnow':0.0,'HoursOfPrecipitation':0.0},'EpochDate':1539007200,'Moon':{'EpochSet':1539050100,'Set':'2018-10-08T18:55:00-07:00','Phase':'WaningCrescent','EpochRise':1539005520,'Age':29,'Rise':'2018-10-08T06:32:00-07:00'},'DegreeDaySummary':{'Cooling':{'UnitType':18,'Value':0.0,'Unit':'F'},'Heating':{'UnitType':18,'Value':0.0,'Unit':'F'}},'RealFeelTemperatureShade':{'Minimum':{'UnitType':18,'Value':55.0,'Unit':'F'},'Maximum':{'UnitType':18,'Value':73.0,'Unit':'F'}},'AirAndPollen':[{'Type':'Ozone','Category':'Moderate','Value':51,'CategoryValue':2,'Name':'AirQuality'},{'Category':'Low','Value':0,'CategoryValue':1,'Name':'Grass'},{'Category':'Low','Value':0,'CategoryValue':1,'Name':'Mold'},{'Category':'Low','Value':0,'CategoryValue':1,'Name':'Ragweed'},{'Category':'Low','Value':7,'CategoryValue':1,'Name':'Tree'},{'Category':'Moderate','Value':4,'CategoryValue':2,'Name':'UVIndex'}],'HoursOfSun':11.0,'Sun':{'EpochSet':1539049320,'Set':'2018-10-08T18:42:00-07:00','EpochRise':1539007860,'Rise':'2018-10-08T07:11:00-07:00'},'Sources':['AccuWeather'],'Date':'2018-10-08T07:00:00-07:00','RealFeelTemperature':{'Minimum':{'UnitType':18,'Value':55.0,'Unit':'F'},'Maximum':{'UnitType':18,'Value':78.0,'Unit':'F'}},'Day':{'RainProbability':0,'Wind':{'Speed':{'UnitType':9,'Value':6.9,'Unit':'mi/h'},'Direction':{'English':'NNW','Degrees':334,'Localized':'NNW'}},'SnowProbability':0,'Snow':{'UnitType':1,'Value':0.0,'Unit':'in'},'TotalLiquid':{'UnitType':1,'Value':0.0,'Unit':'in'},'ShortPhrase':'Sunshine','Ice':{'UnitType':1,'Value':0.0,'Unit':'in'},'HoursOfRain':0.0,'HoursOfIce':0.0,'Rain':{'UnitType':1,'Value':0.0,'Unit':'in'},'PrecipitationProbability':0,'ThunderstormProbability':2,'IceProbability':0,'IconPhrase':'Sunny','CloudCover':4,'LongPhrase':'Sunshine','Icon':1,'WindGust':{'Speed':{'UnitType':9,'Value':10.4,'Unit':'mi/h'},'Direction':{'English':'NNW','Degrees':343,'Localized':'NNW'}},'HoursOfSnow':0.0,'HoursOfPrecipitation':0.0},'Link':'http://www.accuweather.com/en/us/highlands-baywood-park-ca/94402/daily-weather-forecast/2621905?day=1&lang=en-us','MobileLink':'http://m.accuweather.com/en/us/highlands-baywood-park-ca/94402/daily-weather-forecast/2621905?day=1&lang=en-us'}]}";
		JSONObject parseresult = JSON.parseObject(resultjson);
		//net.sf.json.JSONObject parseresult = net.sf.json.JSONObject.fromObject(resultjson);
		String headline = parseresult.getString("Headline");
		JSONObject headlinejson = JSON.parseObject(headline);
		
		String text = headlinejson.getString("Text");
		
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(resultjson);
		net.sf.json.JSONObject dailyforecastsjson = (net.sf.json.JSONObject) jsonObject.getJSONArray("DailyForecasts").get(0);
		String temperaturestring = dailyforecastsjson.getString("Temperature");
		net.sf.json.JSONObject temperaturejson = net.sf.json.JSONObject.fromObject(temperaturestring);
		
		String minimumstr = temperaturejson.getString("Minimum");
		String maximumstr = temperaturejson.getString("Maximum");
		net.sf.json.JSONObject minjson = net.sf.json.JSONObject.fromObject(minimumstr);
		net.sf.json.JSONObject maxjson = net.sf.json.JSONObject.fromObject(maximumstr);
		double minvalue = minjson.getDouble("Value");
		double maxvalue = maxjson.getDouble("Value");
		String daystr = dailyforecastsjson.getString("Day");
		String nightstr = dailyforecastsjson.getString("Night");
		net.sf.json.JSONObject dayjson = net.sf.json.JSONObject.fromObject(daystr);
		net.sf.json.JSONObject nightjson = net.sf.json.JSONObject.fromObject(nightstr);
		String dayicon = dayjson.getString("Icon");
		String nighticon = nightjson.getString("Icon");
		String icon = dayicon;
		
		String sunstring = dailyforecastsjson.getString("Sun");
		net.sf.json.JSONObject sunjson = net.sf.json.JSONObject.fromObject(sunstring);
		String risestr = sunjson.getString("Rise");
		String setstr = sunjson.getString("Set");
		
		risestr = risestr.substring(0,risestr.lastIndexOf("-")).replace("T", " ");
		setstr = setstr.substring(0,setstr.lastIndexOf("-")).replace("T", " ");
		System.out.println(risestr+"----fengefu----"+setstr);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date sunsetdate = new Date();
		try {
			sunsetdate = sdf.parse(setstr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(new Date().getTime()>sunsetdate.getTime()){
			icon = nighticon;
		}
		String tz = System.getProperty("user.timezone");
		TimeZone rtz = TimeZone.getDefault();
		System.out.println(tz);
		System.out.println(rtz.getRawOffset()/60/1000/60);
	}
}
