package com.iremote.advert;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.geom.Line2D;
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

public class AmetaWeatherAction3 {
	private static Log log = LogFactory.getLog(AmetaWeatherAction3.class);
	private static final String cachefilepath = "/opt/tools/cachefiles";
	private static String key = "aV3UzkErYHEF1Khi34hrpfINLJGGuIsd";
	private static int[][] WEATHER_ICON_POSITION = new int[][] {{ 100, 50}, { 205, 50},{ 295, 50},{ 390, 50},{ 485, 50},{ 585, 50}};
	private static int[][] WEATHER_WEEK_POSITION = new int[][] {{ 140, 130}, { 235, 130},{ 325, 130},{ 420, 130},{ 515, 130},{ 615, 130}};
	private static int[][] WEATHER_MAX_POSITION = new int[][] {{ 140, 160}, { 235, 160},{ 325, 160},{ 420, 160},{ 515, 160},{ 615, 160}};
	private static int[][] WEATHER_MIN_POSITION = new int[][] {{ 140, 190}, { 235, 190},{ 325, 190},{ 420, 190},{ 515, 190},{ 615, 190}};
	private static int[] SHADOW_X_POSITION = new int[]{94,189,284,379,474,569};
	private static int[] SHADOW_Y_POSITION = new int[]{115,145,175};
	
	private ByteArrayInputStream inputStream;
	private BufferedImage buffImg;
	private Graphics2D g2d;

	private String[] weathercode = new String[1];
	private int[] color = new int[]{0x666666,0x333333,0x999999,0x6f7072};
	private String[] weekdaykey = new String[]{"monday","tuesday","wednesday","thursday","friday","saturday","sunday"}; 
	private String weatherstr;
	private String[] week = new String[6];
	private String[] maxvalue = new String[6];
	private String[] minvalue = new String[6];
	private String[] icon = new String[5];
	private String[] dayicon = new String[5];
	private String querycity;
	private int totalwidth;
	private String ip ;
	private PhoneUser phoneuser;

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
			int iw = Integer.valueOf(this.icon[0]);
			if (iw<45&&iw>0&&iw!=9&&iw!=10&&iw!=27&&iw!=28)
				createGraphics2D(RequestHelper.getRootPath() + "/images/weather/backgroud/ametabg.png");
			else {
				defaultImage();
				log.warn(String.format("unkown weather code : %s", this.weathercode[0]));
				savedefaultcacheimg();
				return Action.SUCCESS;
			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			for(int i=0;i<6;i++){
				if(i==0){
					drawRect(SHADOW_X_POSITION[i],SHADOW_Y_POSITION[0],88,16,new Color(0xf2f2f2));
				}else{
					drawRect(SHADOW_X_POSITION[i],SHADOW_Y_POSITION[0],87,15,new Color(0xf2f2f2));
				}
				drawRect(SHADOW_X_POSITION[i],SHADOW_Y_POSITION[1],87,15,new Color(0xf2f2f2));
				drawRect(SHADOW_X_POSITION[i],SHADOW_Y_POSITION[2],87,15,new Color(0xf2f2f2));
			}

			drawText(weatherstr, new Color(color[3]), 32, 40, fontstyle1,
					24);
			
			drawTextCenter(week[0], new Color(color[1]), WEATHER_WEEK_POSITION[0][0], WEATHER_WEEK_POSITION[0][1], fontstyle,18);
			drawTextCenter(String.valueOf(maxvalue[0]), new Color(color[1]), WEATHER_MAX_POSITION[0][0], WEATHER_MAX_POSITION[0][1], fontstyle,18);
			drawTextCenter(String.valueOf(minvalue[0]), new Color(color[1]), WEATHER_MIN_POSITION[0][0], WEATHER_MIN_POSITION[0][1], fontstyle,18);
			
			drawTextRight(querycity  , new Color(color[3]) , 15 , 40 , fontstyle,24);
			drawMyLine(185,50,185,200,new Color(color[2]));
			drawMyLine(280,50,280,200,new Color(color[2]));
			drawMyLine(375,50,375,200,new Color(color[2]));
			drawMyLine(470,50,470,200,new Color(color[2]));
			drawMyLine(565,50,565,200,new Color(color[2]));
			
			
			
			for(int i = 1 ;i < 6 ; i++){
				drawImage(new File(String.format("%s%s%s%s", RequestHelper.getRootPath(), "/images/weather/ametasmallicon/",
					this.icon[i-1], "-s.jpg")), WEATHER_ICON_POSITION[i][0], WEATHER_ICON_POSITION[i][1], 1);
				drawTextCenter(week[i], new Color(color[1]), WEATHER_WEEK_POSITION[i][0], WEATHER_WEEK_POSITION[i][1], fontstyle,18);
				drawTextCenter(String.valueOf(maxvalue[i])+"\u2103", new Color(color[1]), WEATHER_MAX_POSITION[i][0], WEATHER_MAX_POSITION[i][1], "Microsoft YaHei",18);
				drawTextCenter(String.valueOf(minvalue[i])+"\u2103", new Color(color[1]), WEATHER_MIN_POSITION[i][0], WEATHER_MIN_POSITION[i][1], "Microsoft YaHei",18);
			}
			int locationwidth =  getTextWidth(querycity,fontstyle, 24);
			drawImage(new File(String.format("%s%s%s%s", RequestHelper.getRootPath(), "/images/weather/",
					"locationicon", ".png")), 750-15-locationwidth-16-5, 16, 1);
/*			int textWidth = getTextWidth(str[1], color[1], WEATHER_TEXT_POSITION[1][0], WEATHER_TEXT_POSITION[1][1],
					fontstyle, WEATHER_TEXT_POSITION[1][2]);
			//F
			drawTextLeft("\u2109", color[2], WEATHER_TEXT_POSITION[3][0]+textWidth, WEATHER_TEXT_POSITION[3][1],
					"Microsoft YaHei", WEATHER_TEXT_POSITION[3][2]);*/
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
			return String.format("%s/%s_%s_%s.png", cachefilepath, Utils.formatTime(new Date(), "yyyyMMddHH"),phoneuser.getPlatform()+"_2", ip);
		}
		return String.format("%s/%s_%s_%s.png", cachefilepath, Utils.formatTime(new Date(), "yyyyMMddHH"),9+"_2", ip);
	}

	private void savedefaultcacheimg() {
		try {
			FileUtils.copyFile(new File(RequestHelper.getRootPath() + "/images/advert_oem/en_us/9/1.jpg"),
					new File(createcachefilename()));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	private int getTextWidth(String text, String fontType, int fontSize) {
		Font f = new Font(fontType, Font.PLAIN, fontSize);
		AttributedString ats = new AttributedString(text);
		ats.addAttribute(TextAttribute.FONT, f, 0, text.length());
		g2d.setFont(f);
		FontMetrics fm = g2d.getFontMetrics();
		return fm.stringWidth(text);
	}
	private void savecacheimg() {
		try {
			ImageIO.write(buffImg, "png", new File(createcachefilename()));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void drawMyLine(int startx,int starty,int endx,int endy, Color color) {
		g2d.setColor(color);
		Line2D line = new Line2D.Float(startx, starty, endx, endy);
        g2d.draw(line);
	}
	
	private void drawRect(int x,int y,int width,int height, Color color) {
		g2d.drawRect(x,y,width,height);
		g2d.setColor(color);
		g2d.fillRect(x,y,width,height);
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

	private void drawTextCenter(String text, Color markContentColor, int xmiddle, int y, String fontType,int fontSize) {
		g2d.setColor(markContentColor);
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

	private void drawTextRight(String text, Color markContentColor, int x , int y,String fontType, int fontSize)
    {
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
			String url3 = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/";
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

			url3 += keystr;
			String language = phoneuser.getLanguage().toLowerCase().replace("_", "-");// fr-ca en-us zh-cn
			JSONObject jsonparameter = new JSONObject();
			jsonparameter.put("apikey", key);
			jsonparameter.put("language", language);
			jsonparameter.put("details", true);
			jsonparameter.put("metric", true);
			String resultjson = HttpUtil.httpGet(url3, jsonparameter, null);

			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(resultjson);
			setValue(jsonObject,5);
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
			
			TimeZone rtz = TimeZone.getDefault();
			int systemzonenumber = rtz.getRawOffset()/60/1000/60;
			int zonenumber = Integer.parseInt(userzone.substring(0,3));
			long aparttime = (systemzonenumber-zonenumber)*60*60*1000;
			long systemcurrenttime = new Date().getTime();
			long localtime = systemcurrenttime-aparttime;
			boolean night = true;
			if (localtime < sunsetdate.getTime()) {
				night = false;
				icon[0] = dayicon[0];
				icon[1] = dayicon[1];
				icon[2] = dayicon[2];
				icon[3] = dayicon[3];
				icon[4] = dayicon[4];
			}
			Calendar cal=Calendar.getInstance();
			cal.setTime(new Date(localtime));
			int index = 1;
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
			ResourceBundle rb = null;
			if(phoneuser.getLanguage().equalsIgnoreCase("zh_cn")){	
				rb = ResourceBundle.getBundle("pageMessageResource", Locale.SIMPLIFIED_CHINESE);
			}else if(phoneuser.getLanguage().equalsIgnoreCase("fr_ca")){
				rb = ResourceBundle.getBundle("pageMessageResource", Locale.CANADA_FRENCH);
			}else{
				rb = ResourceBundle.getBundle("pageMessageResource", Locale.US);
			}
			week[1] = rb.getString(weekdaykey[index-1]);
			week[2] = rb.getString(weekdaykey[(index)%7]);
			week[3] = rb.getString(weekdaykey[(index+1)%7]);
			week[4] = rb.getString(weekdaykey[(index+2)%7]);
			week[5] = rb.getString(weekdaykey[(index+3)%7]);
			maxvalue[0] = rb.getString("weatherhigh");
			minvalue[0] = rb.getString("weatherlow");
			week[0] = night?rb.getString("weathernight"):rb.getString("weatherday");
			weatherstr = new String(rb.getString("weather").getBytes(),"utf-8");
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	private void setValue(net.sf.json.JSONObject jsonObject,int day){
		for(int i =0;i<day;i++){
			net.sf.json.JSONObject dailyforecastsjson = (net.sf.json.JSONObject) jsonObject
					.getJSONArray("DailyForecasts").get(i);
			String temperaturestring = dailyforecastsjson.getString("Temperature");
			net.sf.json.JSONObject temperaturejson = net.sf.json.JSONObject.fromObject(temperaturestring);
	
			String minimumstr = temperaturejson.getString("Minimum");
			String maximumstr = temperaturejson.getString("Maximum");
			net.sf.json.JSONObject minjson = net.sf.json.JSONObject.fromObject(minimumstr);
			net.sf.json.JSONObject maxjson = net.sf.json.JSONObject.fromObject(maximumstr);
			minvalue[i+1] = String.valueOf((int)minjson.getDouble("Value"));
			maxvalue[i+1] = String.valueOf((int)maxjson.getDouble("Value"));
	
			String daystr = dailyforecastsjson.getString("Day");
			String nightstr = dailyforecastsjson.getString("Night");
			net.sf.json.JSONObject dayjson = net.sf.json.JSONObject.fromObject(daystr);
			net.sf.json.JSONObject nightjson = net.sf.json.JSONObject.fromObject(nightstr);
			dayicon[i] = dayjson.getString("Icon");
			String nighticon = nightjson.getString("Icon");
			icon[i] = nighticon;
		}
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

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

}
