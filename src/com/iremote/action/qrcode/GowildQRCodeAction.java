package com.iremote.action.qrcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.UserToken;
import com.iremote.service.PhoneUserService;
import com.iremote.service.UserService;
import com.iremote.service.UserTokenService;
import com.opensymphony.xwork2.Action;

import net.sf.json.JSONObject;

public class GowildQRCodeAction {

	private static Log log = LogFactory.getLog(GowildQRCodeAction.class);
	
	private String phonenumber;
	private int platform = 0;
	private String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
	private String password;
	private String wifissid ;
	private String wifipassword;
	private String token;
	private PhoneUser phoneuser ;
	private InputStream imageStream;  
	
	private static final int BLACK = 0xFF000000;  
	private static final int WHITE = 0xFFFFFFFF;  
	private static final String format = "png";  
	
	public String execute()
	{
		PhoneUserService us = new PhoneUserService();
		UserService svr = new UserService();
		
		phoneuser = us.query(countrycode , phonenumber , platform);
		if ( phoneuser == null || password == null 
				|| !svr.checkPassword(phoneuser.getPhonenumber() , password, phoneuser.getPassword()))
		{
			onLoginfailed();
			return Action.SUCCESS;
		}

		createToken();
		JSONObject json = createJson();
		createCode(json.toString());

		return Action.SUCCESS;
	}
	
    public void createCode(String content)
    {  
        int width = 300;  
        int height = 300;  
        

        HashMap<EncodeHintType , String> hints = new HashMap<EncodeHintType , String>();  

        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
        try {  
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE,width,height,hints);  
            // Éú³É¶þÎ¬Âë  
            
            BufferedImage bi = toBufferedImage(bitMatrix);
            
            ByteArrayOutputStream bs = new ByteArrayOutputStream();  
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);  
            ImageIO.write(bi, format, imOut);  
            imageStream = new ByteArrayInputStream(bs.toByteArray());              
        } catch (Exception e) {  
            log.error(e.getMessage() , e);
        }  
          
    }
	
    public void onLoginfailed()
    {
    	imageStream = this.getClass().getClassLoader().getResourceAsStream("resource/loginfailed.png");      
    }
    
    public static BufferedImage toBufferedImage(BitMatrix matrix) {  
        int width = matrix.getWidth();  
        int height = matrix.getHeight();  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        for (int x = 0; x < width; x++) {  
          for (int y = 0; y < height; y++) {  
            image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);  
          }  
        }  
        return image;  
      } 
    
	private void createToken()
	{
		token = Utils.createtoken();

		UserToken ut = new UserToken();
		ut.setPhoneuserid(phoneuser.getPhoneuserid());
		ut.setToken(token);
		
		UserTokenService uts = new UserTokenService();
		uts.save(ut);
	}
	
	private JSONObject createJson()
	{
		JSONObject json = new JSONObject();
		json.put("company", "iSurpass");
		json.put("ssid", wifissid);
		json.put("psw", wifipassword);
		
		JSONObject data = new JSONObject();
		data.put("token", token);
		
		json.put("data", data);
		
		return json ;
	}
	
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setWifissid(String wifissid) {
		this.wifissid = wifissid;
	}
	public void setWifipassword(String wifipassword) {
		this.wifipassword = wifipassword;
	}

	public InputStream getImageStream() {
		return imageStream;
	}

	public static void main(String arg[])
	{
		GowildQRCodeAction a = new GowildQRCodeAction();
		
		System.out.println(a.getClass().getClassLoader().getResourceAsStream("resource/loginfailed.png"));
	}
	
}
