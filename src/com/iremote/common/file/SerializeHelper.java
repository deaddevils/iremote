package com.iremote.common.file;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.infraredcode.tv.TvCodeLiberay;

public class SerializeHelper {

	private static Log log = LogFactory.getLog(SerializeHelper.class);
	
	public static void saveObject(String file , Serializable obj)
	{
		try 
		{
		   FileOutputStream fs = new FileOutputStream(file);
		   ObjectOutputStream os = new ObjectOutputStream(fs);
		   os.writeObject(obj);
		   os.flush();
		   os.close();
		} 
		catch (Throwable ex) 
		{
			log.error(ex.getMessage(), ex);
		}
	}
	
	public static <T extends Serializable> T getObject(String file , T t)
	{
		try 
		{
			InputStream is = TvCodeLiberay.class.getClassLoader().getResourceAsStream(file);
			ObjectInputStream ois = new ObjectInputStream(is);
			T obj = (T)ois.readObject();
			ois.close();
			return obj;
		 } 
		 catch (Throwable ex) 
		 {
			 log.error(ex.getMessage(), ex);
		 }
		 return null;
	}
}
