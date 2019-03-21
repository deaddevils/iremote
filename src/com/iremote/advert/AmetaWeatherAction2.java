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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.RequestHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.http.HttpUtil;
import com.iremote.domain.PhoneUser;
import com.opensymphony.xwork2.Action;

public class AmetaWeatherAction2 {
	private static Log log = LogFactory.getLog(AmetaWeatherAction2.class);
	private static final String cachefilepath = "/opt/tools/cachefiles";
	private static String key = "aV3UzkErYHEF1Khi34hrpfINLJGGuIsd";
	private static int[][] WEATHER_TEXT_POSITION = new int[][] {
			{ 200, 95, 24 }, // date
			{ 200, 147, 48 }, //temperature
			{ 200, 195, 24 },// weather
			{ 200, 131, 24 },//C
			{ 32, 40, 24 },//font weather
			};

	private ByteArrayInputStream inputStream;
	private BufferedImage buffImg;
	private Graphics2D g2d;

	private String[] weathercode = new String[1];
	private Double[] temperaturenight = new Double[1];
	private Double[] temperatureday = new Double[1];
	private String[] weather = new String[1];
	private int[] color = new int[]{0x666666,0x333333,0x999999,0x6f7072};
	private String ip ;
	private PhoneUser phoneuser;
	private String querycity;
	private int totalwidth;
	private String weatherstr;

	private String datestr = "WEN,07-19";
	private String fontstyle = "Arial MT";
	private String fontstyle1 = "Arial MT";
	
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public String execute() {
		HibernateUtil.commit();
		HibernateUtil.closeSession();
		RequestHelper.setHeader("Cache-control", "max-age=3600");

		if (StringUtils.isBlank(ip))
			ip = RequestHelper.getRemoteIp();
		//ip = "119.123.41.227";
		//ip = "47.254.33.167";
		log.info("The IP address of the user is:"+ip);
		if (phoneuser != null && (IRemoteConstantDefine.DEFAULT_LANGUAGE.equalsIgnoreCase(phoneuser.getLanguage())
				|| IRemoteConstantDefine.DEFAULT_ZHHK_LANGUAGE.equalsIgnoreCase(phoneuser.getLanguage()))) {
			fontstyle = "Microsoft YaHei";
		}
		if (phoneuser != null && (IRemoteConstantDefine.DEFAULT_LANGUAGE.equalsIgnoreCase(phoneuser.getLanguage())
				|| IRemoteConstantDefine.DEFAULT_FRCA_LANGUAGE.equalsIgnoreCase(phoneuser.getLanguage())
				|| IRemoteConstantDefine.DEFAULT_ZHHK_LANGUAGE.equalsIgnoreCase(phoneuser.getLanguage()))) {
			fontstyle1 = "Microsoft YaHei";
		}
		if (useCachedImg())
			return Action.SUCCESS;

		if (phoneuser==null||initWeather() == false||ip.startsWith("192.168")) {
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
			//drawMyRoundRect(30,20,180,44,new Color(0x09B9F0));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			//Weather icon on the right
			drawImage(new File(String.format("%s%s%s%s", RequestHelper.getRootPath(), "/images/weather/ametaicon/",
					this.weathercode[0], "-s.png")), 387, 61, 1);
			
			//0.date at the top left 1.temperature range on the left 2.weather describe on the left
			String[] str = new String[] { this.datestr ,String.format("%s/%s" ,
					getCount(this.temperatureday[0])==999?this.temperatureday[0]:getCount(this.temperatureday[0])+"",
					getCount(this.temperaturenight[0])==999?this.temperaturenight[0]:getCount(this.temperaturenight[0])+""),
					this.weather[0]};
			drawText(weatherstr, new Color(color[3]), WEATHER_TEXT_POSITION[4][0], WEATHER_TEXT_POSITION[4][1], fontstyle1,
					WEATHER_TEXT_POSITION[4][2]);
			drawText(str[0], new Color(color[0]), WEATHER_TEXT_POSITION[0][0], WEATHER_TEXT_POSITION[0][1], fontstyle,
					WEATHER_TEXT_POSITION[0][2]);
			drawTextRight(querycity  , new Color(color[3]) , 15 , 40 , fontstyle,24);
			for (int i = 1; i < 3 ; i++){
				drawTextLeft(str[i], color[i], WEATHER_TEXT_POSITION[i][0], WEATHER_TEXT_POSITION[i][1],
						fontstyle, WEATHER_TEXT_POSITION[i][2]);
			}
			int textWidth = getTextWidth(str[1], /*color[1], WEATHER_TEXT_POSITION[1][0], WEATHER_TEXT_POSITION[1][1],*/
					fontstyle, WEATHER_TEXT_POSITION[1][2]);
			
			int locationwidth =  getTextWidth(querycity,fontstyle, 24);
			drawImage(new File(String.format("%s%s%s%s", RequestHelper.getRootPath(), "/images/weather/",
					"locationicon", ".png")), 750-15-locationwidth-16-5, 16, 1);
			//F
			drawTextLeft("\u2103", color[2], WEATHER_TEXT_POSITION[3][0]+textWidth, WEATHER_TEXT_POSITION[3][1],
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
		totalwidth = buffImg.getWidth();
		g2d = buffImg.createGraphics();
	}

	private void outputImage(OutputStream os) throws IOException {
		g2d.dispose();
		ImageIO.write(buffImg, "png", os);
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
	private int getTextWidth(String text,String fontType, int fontSize) {
		Font f = new Font(fontType, Font.PLAIN, fontSize);

		AttributedString ats = new AttributedString(text);
		ats.addAttribute(TextAttribute.FONT, f, 0, text.length());
		g2d.setFont(f);
		FontMetrics fm = g2d.getFontMetrics();
		return fm.stringWidth(text);
	}
	private void drawTextRight(String text, Color markContentColor, int x , int y,String fontType, int fontSize){
    	g2d.setColor(markContentColor);
    	//g2d.setBackground(color1);
		Font f = new Font(fontType, Font.PLAIN, fontSize);
		
		AttributedString ats = new AttributedString(text);	
		ats.addAttribute(TextAttribute.FONT, f, 0, text.length());
		AttributedCharacterIterator iter = ats.getIterator();
		g2d.setFont(f);
		FontMetrics fm =g2d.getFontMetrics();
		
		g2d.drawString(iter, totalwidth - x - fm.stringWidth(text), y ); 
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
			String url3 = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/";
			String locationjson = HttpUtil.httprequest(url1, "");
			JSONObject parselocation = JSON.parseObject(locationjson);
			String countrycode = parselocation.getString("countryCode");
			String admincode = parselocation.getString("region");
			if(StringUtils.isBlank(admincode)){
				admincode = parselocation.getString("regionName").substring(0,2).toUpperCase();
			}
			querycity = parselocation.getString("city");
			if(querycity.contains("(")){
				querycity = querycity.substring(0,querycity.indexOf("("));
			}

			JSONObject jsonpar = new JSONObject();
			jsonpar.put("apikey", key);
			jsonpar.put("q", querycity);
			jsonpar.put("details", "true");
			String url2 = "http://dataservice.accuweather.com/locations/v1/cities/"+countrycode+"/"+admincode+"/search";
			String keyjson = HttpUtil.httpGet(url2, jsonpar, null);
			JSONArray arraykey = (JSONArray) JSONArray.parse(keyjson);
			JSONObject parsekey = (JSONObject) arraykey.get(0);
			String keystr = parsekey.getString("Key");
			
			/*JSONObject parsekey = JSON.parseObject(keyjson);
			String keystr = parsekey.getString("Key");*/

			url3 += keystr;
			String language = phoneuser.getLanguage().toLowerCase().replace("_", "-");// fr-ca en-us zh-cn
			JSONObject jsonparameter = new JSONObject();
			jsonparameter.put("apikey", key);
			jsonparameter.put("language", language);
			jsonparameter.put("details", true);
			jsonparameter.put("metric", true);
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
			cal.setTime(new Date(localtime));
			String week= "";
			SimpleDateFormat sd = new SimpleDateFormat("MM-dd");
			ResourceBundle rb = null;
			if(phoneuser.getLanguage().equalsIgnoreCase("zh_cn")){
				rb = ResourceBundle.getBundle("pageMessageResource", Locale.SIMPLIFIED_CHINESE);
			}else if(phoneuser.getLanguage().equalsIgnoreCase("fr_ca")){
				rb = ResourceBundle.getBundle("pageMessageResource", Locale.CANADA_FRENCH);
			}else{
				rb = ResourceBundle.getBundle("pageMessageResource", Locale.US);
			}
			switch(cal.get(Calendar.DAY_OF_WEEK)){
				case Calendar.SATURDAY:
					week=rb.getString("saturday");
					break;
				case Calendar.SUNDAY:
					week=rb.getString("sunday");
					break;
				case Calendar.MONDAY:
					week=rb.getString("monday");
					break;
				case Calendar.TUESDAY:
					week=rb.getString("tuesday");
					break;
				case Calendar.WEDNESDAY:
					week=rb.getString("wednesday");
					break;
				case Calendar.THURSDAY:
					week=rb.getString("thursday");
					break;
				case Calendar.FRIDAY:
					week=rb.getString("friday");
					break;
			}
			
			weatherstr = new String(rb.getString("weather").getBytes(),"utf-8");
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
		this.fileImage(RequestHelper.getRootPath() + "/images/advert_oem/en_us/9/1.jpg");
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
		AmetaWeatherAction2 wa = new AmetaWeatherAction2();
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
