package cn.com.isurpass.iremote.opt.qrcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.opensymphony.xwork2.Action;

public class CreateOneQrcodeImageAction
{
	private static Log log = LogFactory.getLog(CreateOneQrcodeImageAction.class);

	
	private static final String format = "png";  
	private static final int BLACK = 0xFF000000;  
	private static final int WHITE = 0xFFFFFFFF;  
	
	private String qrcodestring ;
	private ByteArrayInputStream inputStream;
	private String filename ;

	public String execute()
	{
		if ( qrcodestring == null)
			qrcodestring = "" ;
				
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		createCode(qrcodestring ,bos);
		inputStream = new ByteArrayInputStream(bos.toByteArray());
		
		return Action.SUCCESS;
	}
	
    private void createCode(String content , OutputStream os)
    {  
        int width = 300;  
        int height = 300;  

        HashMap<EncodeHintType , String> hints = new HashMap<EncodeHintType , String>();  

        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
        try 
        {  
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE,width,height,hints);  
            
            BufferedImage bi = toBufferedImage(bitMatrix);
            
            ByteArrayOutputStream bs = new ByteArrayOutputStream();  
            ImageIO.createImageOutputStream(bs);  
            ImageIO.write(bi, format, os);      
        } 
        catch (Exception e) 
        {  
        	log.error(e.getMessage() , e );
        }  
    }
    
    public static BufferedImage toBufferedImage(BitMatrix matrix) 
    {  
        int width = matrix.getWidth();  
        int height = matrix.getHeight();  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        for (int x = 0; x < width; x++) 
          for (int y = 0; y < height; y++)  
            image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);  
        return image;  
     }
	
	public ByteArrayInputStream getInputStream()
	{
		return inputStream;
	}

	public void setQrcodestring(String qrcodestring)
	{
		this.qrcodestring = qrcodestring;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}
	
	
}
