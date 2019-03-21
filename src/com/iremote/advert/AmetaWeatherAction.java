package com.iremote.advert;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.RequestHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.http.HttpUtil;
import com.iremote.domain.PhoneUser;
import com.opensymphony.xwork2.Action;

public class AmetaWeatherAction {
	private static Log log = LogFactory.getLog(AmetaWeatherAction.class);
	private static final String cachefilepath = "/opt/tools/cachefiles";
	private static String key = "aV3UzkErYHEF1Khi34hrpfINLJGGuIsd";
	private static int[][] WEATHER_TEXT_POSITION = new int[][] {
			{ 58, 51, 24 }, // date
			{ 130, 128, 47 }, //temperature
			{ 130, 159, 24 },// weather
			{ 130, 113, 24 },//F
	};

	private ByteArrayInputStream inputStream;
	private BufferedImage buffImg;
	private Graphics2D g2d;

	private String[] weathercode = new String[1];
	private Double[] temperaturenight = new Double[1];
	private Double[] temperatureday = new Double[1];
	private String[] weather = new String[1];
	private int[] color = new int[]{0x666666,0x333333,0x999999};
	private String[] weekdayzh = new String[]{"\u5468\u4E00", "\u5468\u4E8C", "\u5468\u4E09", "\u5468\u56DB", "\u5468\u4E94", "\u5468\u516D","\u5468\u65E5"};
	private String[] weekdayfr = new String[]{"LUN", "MAR", "MER", "JEU", "VEN", "SAM","DIM"};
	private String[] weekdayen = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT","SUN"};
	private String ip ;
	private PhoneUser phoneuser;

	private String datestr = "WEN,07-19";
	private String fontstyle = "Arial MT";
	
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public String execute() {
		RequestHelper.setHeader("Cache-control", "max-age=3600");

		if (StringUtils.isBlank(ip))
			ip = RequestHelper.getRemoteIp();

		ip = "119.123.41.227";
		//ip = "47.254.33.167";
		//ip="128.171.125.25";
		//ip="24.237.132.5";
		log.info("The IP address of the user is:"+ip);
		if (phoneuser != null && (IRemoteConstantDefine.DEFAULT_LANGUAGE.equalsIgnoreCase(phoneuser.getLanguage())
				|| IRemoteConstantDefine.DEFAULT_ZHHK_LANGUAGE.equalsIgnoreCase(phoneuser.getLanguage()))) {
			fontstyle = "Microsoft YaHei";
		}
		if (useCachedImg())
			return Action.SUCCESS;

		if (phoneuser==null||initWeather() == false) {
			defaultImage();
			return Action.SUCCESS;
		}

		try {
			int iw = Integer.valueOf(this.weathercode[0]);
			if (iw<45&&iw>0&&iw!=9&&iw!=10&&iw!=27&&iw!=28)
				createGraphics2D(RequestHelper.getRootPath() + "/images/weather/backgroud/ametabg.png");
			else {
				defaultImage();
				log.warn(String.format("unkown weather code : %s", this.weathercode[0]));
				savedefaultcacheimg();
				return Action.SUCCESS;
			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			//A rounded rectangle at the top left
			drawMyRoundRect(30,20,180,44,new Color(0x09B9F0));
			
			//Weather icon on the right
			drawImage(new File(String.format("%s%s%s%s", RequestHelper.getRootPath(), "/images/weather/ametaicon/",
					this.weathercode[0], "-s.png")), 467, 61, 1);
			
			//0.date at the top left 1.temperature range on the left 2.weather describe on the left
			String[] str = new String[] { this.datestr ,String.format("%s/%s" ,
					getCount(this.temperatureday[0])==999?this.temperatureday[0]:getCount(this.temperatureday[0])+"",
					getCount(this.temperaturenight[0])==999?this.temperaturenight[0]:getCount(this.temperaturenight[0])+""),
					this.weather[0]};

			drawText(str[0], new Color(color[0]), WEATHER_TEXT_POSITION[0][0], WEATHER_TEXT_POSITION[0][1], fontstyle,
					WEATHER_TEXT_POSITION[0][2]);
			for (int i = 1; i < 3 ; i++){
				drawTextLeft(str[i], color[i], WEATHER_TEXT_POSITION[i][0], WEATHER_TEXT_POSITION[i][1],
						fontstyle, WEATHER_TEXT_POSITION[i][2]);
			}
			int textWidth = getTextWidth(str[1], color[1], WEATHER_TEXT_POSITION[1][0], WEATHER_TEXT_POSITION[1][1],
					fontstyle, WEATHER_TEXT_POSITION[1][2]);
			//F
			drawTextLeft("\u2109", color[2], WEATHER_TEXT_POSITION[3][0]+textWidth, WEATHER_TEXT_POSITION[3][1],
					"Microsoft YaHei", WEATHER_TEXT_POSITION[3][2]);
			outputImage(bos);
			inputStream = new ByteArrayInputStream(bos.toByteArray());

			savecacheimg();
		} catch (IOException e) {
			defaultImage();
			log.error(e.getMessage(), e);
			return Action.SUCCESS;
		}

		return Action.SUCCESS;
	}
	
	private boolean useCachedImg() {
		String filename = createcachefilename();

		File f = new File(filename);
		if (!f.exists())
			return false;

		fileImage(filename);

		log.info("use cached image");
		return true;
	}

	private String createcachefilename() {
		if(phoneuser!=null){
			return String.format("%s/%s_%s_%s.png", cachefilepath, Utils.formatTime(new Date(), "yyyyMMddHH"),phoneuser.getPlatform(), ip);
		}
		return String.format("%s/%s_%s_%s.png", cachefilepath, Utils.formatTime(new Date(), "yyyyMMddHH"),9, ip);
	}

	private void savedefaultcacheimg() {
		try {
			FileUtils.copyFile(new File(RequestHelper.getRootPath() + "/images/advert_oem/en_us/9/1.jpg"),
					new File(createcachefilename()));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void savecacheimg() {
		try {
			ImageIO.write(buffImg, "png", new File(createcachefilename()));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public static int getCount(double d) {
		int i = String.valueOf(d).indexOf(".");
		if (d % Integer.parseInt(String.valueOf(d).substring(0, i)) == 0) {
			return Integer.parseInt(String.valueOf(d).substring(0, i));
		}
		return 999;
	}

	private void createGraphics2D(String filepath) throws IOException {
		File file = new File(filepath);
		buffImg = ImageIO.read(file);
		g2d = buffImg.createGraphics();
	}

	private void outputImage(OutputStream os) throws IOException {
		g2d.dispose();
		ImageIO.write(buffImg, "png", os);
	}
	
	private void drawMyRoundRect(int x,int y,int width,int height, Color color) {
		g2d.setColor(color);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawRoundRect(x,y,width,height,height,height);
	}
	
	private void drawTextLeft(String text, int color2, int x, int y, String fontType, int fontSize) {
		g2d.setColor(new Color(color2));
		g2d.setBackground(new Color(color2));
		Font f = new Font(fontType, Font.PLAIN, fontSize);

		AttributedString ats = new AttributedString(text);
		ats.addAttribute(TextAttribute.FONT, f, 0, text.length());
		AttributedCharacterIterator iter = ats.getIterator();
		g2d.setFont(f);

		g2d.drawString(iter, x, y);
	}
	private int getTextWidth(String text, int color2, int x, int y, String fontType, int fontSize) {
		g2d.setColor(new Color(color2));
		g2d.setBackground(new Color(color2));
		Font f = new Font(fontType, Font.PLAIN, fontSize);

		AttributedString ats = new AttributedString(text);
		ats.addAttribute(TextAttribute.FONT, f, 0, text.length());
		g2d.setFont(f);
		FontMetrics fm = g2d.getFontMetrics();
		return fm.stringWidth(text);
	}

	private void drawTextCenter(String text, Color markContentColor, int xmiddle, int y, String fontType,int fontSize) {
		g2d.setColor(markContentColor);
		//g2d.setBackground(color);
		Font f = new Font(fontType, Font.PLAIN, fontSize);

		AttributedString ats = new AttributedString(text);
		ats.addAttribute(TextAttribute.FONT, f, 0, text.length());
		AttributedCharacterIterator iter = ats.getIterator();
		g2d.setFont(f);
		FontMetrics fm = g2d.getFontMetrics();

		g2d.drawString(iter, xmiddle - fm.stringWidth(text) / 2, y);
	}

	private void drawText(String text, Color markContentColor, int x, int y, String fontType, int fontSize) {
		g2d.setColor(markContentColor);
		//g2d.setBackground(color);
		Font f = new Font(fontType, Font.PLAIN, fontSize);

		AttributedString ats = new AttributedString(text);
		ats.addAttribute(TextAttribute.FONT, f, 0, text.length());
		AttributedCharacterIterator iter = ats.getIterator();

		g2d.drawString(iter, x, y);
	}

	private void drawImage(File waterFile, int x, int y, float alpha) throws IOException {
		BufferedImage waterImg = ImageIO.read(waterFile);

		int waterImgWidth = waterImg.getWidth();
		int waterImgHeight = waterImg.getHeight();

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		g2d.drawImage(waterImg, x, y, waterImgWidth, waterImgHeight, null);
	}

	private boolean initWeather() {
		try {
			String url1 = "http://www.ip-api.com/json/" + ip;
			String url2 = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search";
			String url3 = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/";
			String locationjson = HttpUtil.httprequest(url1, "");
			JSONObject parselocation = JSON.parseObject(locationjson);
			String lon = parselocation.getString("lon");
			String lat = parselocation.getString("lat");

			JSONObject jsonpar = new JSONObject();
			jsonpar.put("apikey", key);
			jsonpar.put("q", lat + "," + lon);
			String keyjson = HttpUtil.httpGet(url2, jsonpar, null);
			JSONObject parsekey = JSON.parseObject(keyjson);
			String keystr = parsekey.getString("Key");

			url3 += keystr;
			String language = phoneuser.getLanguage().toLowerCase().replace("_", "-");// fr-ca en-us zh-cn
			JSONObject jsonparameter = new JSONObject();
			jsonparameter.put("apikey", key);
			jsonparameter.put("language", language);
			jsonparameter.put("details", true);
			String resultjson = HttpUtil.httpGet(url3, jsonparameter, null);

			JSONObject parseresult = JSON.parseObject(resultjson);
			String headline = parseresult.getString("Headline");
			JSONObject headlinejson = JSON.parseObject(headline);
			String text = headlinejson.getString("Text");
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(resultjson);
			net.sf.json.JSONObject dailyforecastsjson = (net.sf.json.JSONObject) jsonObject
					.getJSONArray("DailyForecasts").get(0);
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
			String icon = nighticon;
			text = nightjson.getString("IconPhrase");

			String sunstring = dailyforecastsjson.getString("Sun");
			net.sf.json.JSONObject sunjson = net.sf.json.JSONObject.fromObject(sunstring);
			String risestr = sunjson.getString("Rise");
			risestr = risestr.substring(0, 19).replace("T", " ");
			String setstr = sunjson.getString("Set");
			String userzone = setstr.substring(19);
			setstr = setstr.substring(0, 19).replace("T", " ");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date sunsetdate = sdf.parse(setstr);
			
			//String tz = System.getProperty("user.timezone");
			TimeZone rtz = TimeZone.getDefault();
			int systemzonenumber = rtz.getRawOffset()/60/1000/60;
			int zonenumber = Integer.parseInt(userzone.substring(0,3));
			long aparttime = (systemzonenumber-zonenumber)*60*60*1000;
			long systemcurrenttime = new Date().getTime();
			long localtime = systemcurrenttime-aparttime;

			if (localtime < sunsetdate.getTime()) {
				icon = dayicon;
				text = dayjson.getString("IconPhrase");
			}
			Calendar cal=Calendar.getInstance();
//			cal.setTime(new Date(sunjson.getLong("EpochRise")*1000));
			cal.setTime(new Date(localtime));
			int index = 1;
			String week= "";
			SimpleDateFormat sd = new SimpleDateFormat("MM-dd");
			switch(cal.get(Calendar.DAY_OF_WEEK)){
				case Calendar.SATURDAY:
					index = 6;
					break;
				case Calendar.SUNDAY:
					index = 7;
					break;
				case Calendar.MONDAY:
					index = 1;
					break;
				case Calendar.TUESDAY:
					index = 2;
					break;
				case Calendar.WEDNESDAY:
					index = 3;
					break;
				case Calendar.THURSDAY:
					index = 4;
					break;
				case Calendar.FRIDAY:
					index = 5;
					break;
			}
			if(phoneuser.getLanguage().equalsIgnoreCase("zh_cn")){
				week = weekdayzh[index-1];
			}else if(phoneuser.getLanguage().equalsIgnoreCase("fr_ca")){
				week = weekdayfr[index-1];
			}else{
				week = weekdayen[index-1];
			}
			String format = sd.format(new Date(localtime));
			datestr = week+","+format;
			JSONObject json0 = new JSONObject();
			json0.put("weather_code", icon);
			json0.put("weather", text);
			json0.put("day_air_temperature", maxvalue);
			json0.put("night_air_temperature", minvalue);
			initWeather(json0, 0);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	private void initWeather(JSONObject json, int index) {
		this.weathercode[index] = json.getString("weather_code");
		this.weather[index] = json.getString("weather");
		this.temperatureday[index] = json.getDoubleValue("day_air_temperature");
		this.temperaturenight[index] = json.getDoubleValue("night_air_temperature");
	}

	private void defaultImage() {
		this.fileImage(RequestHelper.getRootPath() + "/images/advert_oem/9/1.jpg");
	}

	private void fileImage(String filename) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			InputStream input = new BufferedInputStream(new FileInputStream(filename));
			byte[] bt = new byte[1024];
			while (input.read(bt) > 0) {
				bos.write(bt);
			}
			this.inputStream = new ByteArrayInputStream(bos.toByteArray());
			bos.close();
			input.close();
		} catch (Throwable t) {
			log.error(t.getMessage(), t);
		}
	}

	public static void main(String arg[]) {
		AmetaWeatherAction wa = new AmetaWeatherAction();
		wa.execute();

		try {
			ImageIO.write(wa.buffImg, "png", new File("E:/temp/4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

}
