package com.iremote.advert;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.RequestHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.http.HttpUtil;
import com.opensymphony.xwork2.Action;
import java.text.AttributedString;
import java.util.Date;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;

public class WeatherAction
{
	private static Log log = LogFactory.getLog(WeatherAction.class);
	private static final String cachefilepath = "/opt/tools/cachefiles";
	 
	private static int[][] WEATHER_IMANGE_POSITION = new int[][]{{140,115},{450,175},{680,175},{910,175}};
	private static int[][] WEATHER_TEXT_POSITION = new int[][]{{28,240,46},  // city
																{540,390,52}, //temperature
																{540,327,52}, //weather
																{540,517,40}, //wind
																{540,489,40},{500,111,26},{500,360,26},{500,420,26},{500,468,26},
																{730,74,26},{730,111,26},{730,360,26},{730,420,26},{730,468,26},
																{960,74,26},{960,111,26},{960,360,30},{960,420,26},{960,468,26}};
	private static String[] WEEKDAY = new String[]{"" , "周一", "周二", "周三", "周四", "周五", "周六", "周日" };
	private static String AQI = "空气指数:";
	private static String[] AQIRANK = new String[]{"优","良","轻度","中度","重度","严重"};
	private static Color[] AQIRANKCOLOR = new Color[]{Color.GREEN , Color.YELLOW , Color.ORANGE , Color.RED ,Color.MAGENTA , new Color( 150, 75, 0)} ;
	private static String LEVEL = "级";
	
	private ByteArrayInputStream inputStream;
    private BufferedImage buffImg ;
    private Graphics2D g2d ;

	private String city;
	private String cityen;
	private String[] weathercode = new String[4] ;
	private int[] temperaturenight = new int[4] ;
	private int[] temperatureday = new int[4];
	private String[] weather = new String[4] ;
	private String[] wind = new String[4] ;
	private String[] windpower = new String[4] ;
	private String[] date = new String[4] ;
	private int[] weekday = new int[4] ;
	private Color color = new Color(0x333333);
	private int totalwidth;

	private int pm2_5;
	private int aqi;
	private String ip ;
	private Integer longitude;
	private Integer latitude;
	
	public ByteArrayInputStream getInputStream() 
	{   
	    return inputStream;  
	}  
	
	public String execute()
	{
		RequestHelper.setHeader("Cache-control", "max-age=3600");
		
		if ( StringUtils.isBlank(ip))
			ip = RequestHelper.getRemoteIp();
		
//		ip = "183.38.246.222";
		
		if ( useCachedImg() )
			return Action.SUCCESS;
		
		if ( initWeather() == false )
		{
			defaultImage();
			return Action.SUCCESS;
		}
		
		try
		{
			int iw = Integer.valueOf(this.weathercode[0]);
			if ( iw < WEATHER_BACK_GROUD.length || iw == 53 || iw == 301 || iw == 302 )
				createGraphics2D(RequestHelper.getRootPath() + "/images/weather/backgroud/bg.png");// + WEATHER_BACK_GROUD[iw]);
//			else if ( iw == 53 )
//				createGraphics2D(RequestHelper.getRootPath() + "/images/weather/backgroud/bg_05.png");
//			else if ( iw == 301 )
//				createGraphics2D(RequestHelper.getRootPath() + "/images/weather/backgroud/bg_02.png");
//			else if ( iw == 302 )
//				createGraphics2D(RequestHelper.getRootPath() + "/images/weather/backgroud/bg_04.png");
			else 
			{
				defaultImage();
				log.warn(String.format("unkown weather code : %s", this.weathercode[0]));
				savedefaultcacheimg();
				return Action.SUCCESS;
			}
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
//			for ( int i = 0 ; i < this.weathercode.length ; i ++ )
//				drawImage(new File(String.format("%s%s%s%s", RequestHelper.getRootPath() , "/images/weather/icon/" , this.weathercode[i] , ".png")),
//						WEATHER_IMANGE_POSITION[i][0] , WEATHER_IMANGE_POSITION[i][1] , 1);
			drawImage(new File(String.format("%s%s%s%s", RequestHelper.getRootPath() , "/images/weather/icon/" , this.weathercode[0] , ".png")),
						516 , 206 , 1);
			String[] str = new String[]{this.city ,String.format("%s~%s℃" , this.temperaturenight[0] , this.temperatureday[0]) , this.weather[0],this.wind[0]+ this.windpower[1],
					WEEKDAY[this.weekday[1]] , this.date[1],String.format("%d~%d℃" , this.temperaturenight[1] , this.temperatureday[1]) ,this.weather[1] , this.wind[1] + this.windpower[1],
					WEEKDAY[this.weekday[2]] , this.date[2],String.format("%d~%d℃" , this.temperaturenight[2] , this.temperatureday[2]) ,this.weather[2] , this.wind[2] + this.windpower[2],
					WEEKDAY[this.weekday[3]] , this.date[3],String.format("%d~%d℃" , this.temperaturenight[3] , this.temperatureday[3]) ,this.weather[3] , this.wind[3] + this.windpower[3]};
			
			drawText(str[0] , color,WEATHER_TEXT_POSITION[0][0] , WEATHER_TEXT_POSITION[0][1] ,"Microsoft YaHei" ,WEATHER_TEXT_POSITION[0][2]);
			
			for ( int i = 1 ; i < 4 /*str.length*/ ; i ++ )
				drawTextCenter(str[i] , color,WEATHER_TEXT_POSITION[i][0] , WEATHER_TEXT_POSITION[i][1] ,"Microsoft YaHei" ,WEATHER_TEXT_POSITION[i][2]);
			
			drawAQI();
			outputImage(bos);
			inputStream = new ByteArrayInputStream(bos.toByteArray());

			savecacheimg();
		} 
		catch (IOException e)
		{
			defaultImage();
			log.error(e.getMessage() , e);
			return Action.SUCCESS;
		}

		return Action.SUCCESS;
	}
	
	private boolean useCachedImg()
	{
		String filename = createcachefilename();
		
		File f = new File(filename);
		if ( !f.exists())
			return false ;

		fileImage(filename);
		
		log.info("use cached image");
		return true;
	}
	
	private String createcachefilename()
	{
		return String.format("%s/%s_%s.png", cachefilepath,Utils.formatTime(new Date(), "yyyyMMddHH"),ip);
	}
	
	private void savedefaultcacheimg()
	{
		try
		{
			FileUtils.copyFile(new File(RequestHelper.getRootPath() + "/images/advert/title1.jpg"), new File(createcachefilename()));
		} 
		catch (IOException e)
		{
			log.error(e.getMessage() , e);
		}
	}
	
	private void savecacheimg()
	{
		try
		{
			ImageIO.write(buffImg, "png", new File(createcachefilename()));
		} 
		catch (IOException e)
		{
			log.error(e.getMessage(), e);
		}
	}
	
	private void drawAQI()
	{
		//drawText(AQI  , color, 28 , 489 , "Microsoft YaHei",40);
		int qr = this.aqi / 50 ;
		if ( qr >= AQIRANK.length)
			qr = AQIRANK.length - 1 ;
		drawText(String.format("%s %d%s", AQI , this.aqi , AQIRANK[qr])  , color , 28 , 517 , "Microsoft YaHei",40);
		
		//drawText("PM2.5:" , color , 10 , 489 , "Microsoft YaHei",40);
		qr = this.pm2_5 / 35 ;
		if ( qr >= AQIRANK.length)
			qr = AQIRANK.length - 1 ;
		
		drawTextRight(String.format("PM2.5: %d%s", this.pm2_5 , AQIRANK[qr])  , color , 28 , 517 , "Microsoft YaHei",40);
	}
	
    private void createGraphics2D(String filepath) throws IOException
    {
    	File file = new File(filepath);
    	buffImg = ImageIO.read(file);
    	totalwidth = buffImg.getWidth();
    	
    	g2d = buffImg.createGraphics();
    }
     
    private void outputImage(OutputStream os) throws IOException
    {
    	g2d.dispose();
    	ImageIO.write(buffImg, "png", os);
    }
    
    private void drawTextRight(String text, Color markContentColor, int x , int y,String fontType, int fontSize)
    {
    	g2d.setColor(markContentColor);
    	g2d.setBackground(color);
		Font f = new Font(fontType, Font.PLAIN, fontSize);
		
		AttributedString ats = new AttributedString(text);	
		ats.addAttribute(TextAttribute.FONT, f, 0, text.length());
		AttributedCharacterIterator iter = ats.getIterator();
		g2d.setFont(f);
		FontMetrics fm =g2d.getFontMetrics();
		
		g2d.drawString(iter, totalwidth - x - fm.stringWidth(text), y ); 
    }
    
    private void drawTextCenter(String text, Color markContentColor, int xmiddle , int y,String fontType, int fontSize)
    {
    	g2d.setColor(markContentColor);
    	g2d.setBackground(color);
		Font f = new Font(fontType, Font.PLAIN, fontSize);
		
		AttributedString ats = new AttributedString(text);	
		ats.addAttribute(TextAttribute.FONT, f, 0, text.length());
		AttributedCharacterIterator iter = ats.getIterator();
		g2d.setFont(f);
		FontMetrics fm =g2d.getFontMetrics();
		
		g2d.drawString(iter, xmiddle - fm.stringWidth(text)/2 , y ); 
    }
    
    private void drawText(String text, Color markContentColor, int x , int y,String fontType, int fontSize)
    {
    	g2d.setColor(markContentColor);
    	g2d.setBackground(color);
		Font f = new Font(fontType, Font.PLAIN, fontSize);
		
		AttributedString ats = new AttributedString(text);	
		ats.addAttribute(TextAttribute.FONT, f, 0, text.length());
		AttributedCharacterIterator iter = ats.getIterator();
		
		g2d.drawString(iter, x , y ); 
    }
    
    private void drawImage(File waterFile,int x, int y, float alpha) throws IOException
    {
    	BufferedImage waterImg = ImageIO.read(waterFile);
    	
    	int waterImgWidth = waterImg.getWidth();// 获取层图的宽度
        int waterImgHeight = waterImg.getHeight();// 获取层图的高度

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        
        g2d.drawImage(waterImg, x, y, waterImgWidth, waterImgHeight, null);
    }
     
	private boolean initWeather()
	{
		try
		{
			String url = "http://ali-weather.showapi.com/ip-to-weather?needMoreDay=1";
			JSONObject json = new JSONObject();
			if ( longitude != null && longitude != 0 && this.latitude != null && this.latitude != 0 )
			{
				url = "http://ali-weather.showapi.com/gps-to-weather?needMoreDay=1";
				json.put("from", 1);
				json.put("lat", this.latitude.floatValue()/1000000f);
				json.put("lng", this.longitude.floatValue()/1000000f);
			}
			else 
				json.put("ip", ip);  
			
			JSONObject header = new JSONObject();
			header.put("Authorization", IRemoteConstantDefine.ALI_APPCODE);
			String rst = HttpUtil.httpGet(url , json , header);
			
			if ( StringUtils.isBlank(rst))
				return false;
			
			JSONObject jrst = JSON.parseObject(rst);
			json = jrst.getJSONObject("showapi_res_body");
			
			if ( ErrorCodeDefine.SUCCESS != json.getInteger("ret_code"))
				return false;

			city = json.getJSONObject("cityInfo").getString("c3");
			cityen = json.getJSONObject("cityInfo").getString("c2");

			initWeather(json.getJSONObject("f1") , 0);
			initWeather(json.getJSONObject("f2") , 1);
			initWeather(json.getJSONObject("f3") , 2);
			initWeather(json.getJSONObject("f4") , 3);
			initNowWeather(json.getJSONObject("now"));
		}
		catch(Throwable e)
		{
			log.error(e.getMessage() , e);
			return false ;
		}
		return true;
	}
	
	private void initNowWeather(JSONObject json)
	{
		Integer p25 = json.getJSONObject("aqiDetail").getInteger("pm2_5");
		if ( p25 != null )
			pm2_5 = p25 ;
		Integer a = json.getInteger("aqi");
		if ( a != null )
			aqi = a ;
		
		this.weathercode[0] = json.getString("weather_code");
		this.weather[0] = json.getString("weather");
		this.wind[0] = json.getString("wind_direction");
		this.windpower[0] = json.getString("wind_power");
	}
	
	private void initWeather(JSONObject json , int index)
	{
		this.weathercode[index] = json.getString("day_weather_code");
		this.weather[index] = json.getString("day_weather");
		this.date[index] = json.getString("day");
		
		this.date[index] = this.date[index].substring(4, 6) + "/" + this.date[index].substring(6, 8);
		
		this.temperatureday[index] = json.getIntValue("day_air_temperature");
		this.temperaturenight[index] = json.getIntValue("night_air_temperature");
		this.weekday[index] =  json.getIntValue("weekday");
		this.wind[index] = json.getString("day_wind_direction");
		this.windpower[index] = json.getString("day_wind_power");
		String[] str = this.windpower[index].split(" ");
		if ( str != null && str.length > 0 && str[0].contains(LEVEL) )
			this.windpower[index] = str[0] ;
		else 
			this.windpower[index] = "";
	}
	
	private void defaultImage()
	{
		this.fileImage(RequestHelper.getRootPath() + "/images/advert/title1.jpg");
	}
	
	private void fileImage(String filename)
	{
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();  
	        InputStream input = new BufferedInputStream(new FileInputStream(filename));  
	        byte[] bt = new byte[1024];  
	        while (input.read(bt) > 0) {  
	            bos.write(bt);  
	        }  
	        this.inputStream = new ByteArrayInputStream(bos.toByteArray());  
	        bos.close();  
	        input.close();  
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t);
		}
	}
	
	private static String[] WEATHER_BACK_GROUD = new String[]{"bg_00.png",
			"bg_01.png",
			"bg_05.png",
			"bg_02.png",
			"bg_03.png",
			"bg_03.png",
			"bg_04.png",
			"bg_02.png",
			"bg_02.png",
			"bg_02.png",
			"bg_02.png",
			"bg_02.png",
			"bg_02.png",
			"bg_04.png",
			"bg_04.png",
			"bg_04.png",
			"bg_04.png",
			"bg_04.png",
			"bg_05.png",
			"bg_02.png",
			"bg_05.png",
			"bg_02.png",
			"bg_02.png",
			"bg_02.png",
			"bg_02.png",
			"bg_02.png",
			"bg_04.png",
			"bg_04.png",
			"bg_04.png",
			"bg_05.png",
			"bg_05.png",
			"bg_05.png"};
	
	public static void main(String arg[])
	{
		WeatherAction wa = new WeatherAction();
		wa.execute();

		try
		{
			ImageIO.write(wa.buffImg, "png", new File("E:/temp/4.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public void setLongitude(Integer longitude)
	{
		this.longitude = longitude;
	}

	public void setLatitude(Integer latitude)
	{
		this.latitude = latitude;
	}

}
