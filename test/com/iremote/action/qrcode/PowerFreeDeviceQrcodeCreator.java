package com.iremote.action.qrcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.iremote.common.Utils;

public class PowerFreeDeviceQrcodeCreator
{
	private static final String format = "png";  
	private static final int BLACK = 0xFF000000;  
	private static final int WHITE = 0xFFFFFFFF;  
	private static final int ONE_KEY = 31 ;
	private static final int TWO_KEY = 32 ;
	private static final int THREE_KEY = 33 ;
	private static final int FOUR_KEY = 34 ;
	private static final int SIX_KEY = 35 ;
	
	public static void main(String arg[])
	{
	
		String dc[] = new String[]{"A1234567"};//,"C5CF0100","D0330100","D0230100","D02D0100"};
		int dt[] = new int[]{FOUR_KEY};//,SIX_KEY,SIX_KEY,SIX_KEY,SIX_KEY};
		for ( int i = 0 ; i < dc.length ; i ++ )
			//for ( int j = 0 ; j < dt.length ; j ++)
		{
			//System.out.println(String.format("%d", dt[j]));
			//for ( int i = 0 ; i < 6 ; i ++ )
			{
				JSONObject json = new JSONObject();
				json.put("type", "powerfreedevice");
				json.put("dt", String.valueOf(dt[i]));
				json.put("qid", Utils.createsecuritytoken(20));
				json.put("dc", "10");
				
				createCode(json.toJSONString() , String.format("e:\\qrcode\\powerfreedevice\\%d_%s.png", dt[0],dc[i]));
				//createCode(json.toJSONString() , String.format("e:\\qrcode\\powerfreedevice\\%d_%s.png",dt[0], json.getString("qid")));
				
				System.out.println(String.format("insert into deviceindentifyinfo (devicetype , devicecode , qrcodeid) values ( '%s' , '%s','%s');" , json.getString("dt") , dc[i] , json.getString("qid")));
				System.out.println(json.toJSONString());
			}
		}
	}
	
    public static void createCode(String content , String filename)
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
            ImageIO.write(bi, format, new File(filename));  
                      
        } catch (Exception e) {  
        	e.printStackTrace();
        }  
          
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
}
