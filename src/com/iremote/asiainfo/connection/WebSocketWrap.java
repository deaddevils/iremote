package com.iremote.asiainfo.connection;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.iremote.asiainfo.helper.AsiainfoMessageHelper;
import com.iremote.asiainfo.helper.ByteWrap;
import com.iremote.asiainfo.vo.AsiainfoMessage;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.infraredtrans.tlv.CommandTlv;


public class WebSocketWrap {

	private static Log log = LogFactory.getLog(WebSocketWrap.class);
	
	private static final int DEFAULT_TIME_OUT = 300000 ;
	
	private static ExecutorService executor = Executors.newCachedThreadPool();
	
	private static WebSocketClientConnection conn;
	
	
	private static Object synObject = new Object();
	
	public static void writeMessage(String deviceid , CommandTlv ct , Object obj) throws TimeoutException, IOException
	{
		AsiainfoMessage msg = AsiainfoMessageHelper.createMessageHead(IRemoteConstantDefine.ASIAINFO_MSG_ID_DEVICE_REACTION , deviceid);
		byte[] cmd = ct.getByte();
		Utils.print("send message via asininfo" , cmd , cmd.length);
		msg.setMessage(cmd);
		msg.setMessageinfo(JSON.toJSONString(obj));
		writeMessage(msg);
	}
	
	public static void writeMessage(AsiainfoMessage message) throws TimeoutException, IOException
	{
//		if ( message.getMessageinfolength() == 0 )
//		{
//			byte[] cmd = message.getMessage();
//			if ( cmd != null && cmd.length > 2 && cmd[0] == 30 && cmd[1] == 7)
//			{
//				byte[] tb = TlvWrap.readTag(cmd , 70 , 4);
//				JSONArray ja = AsiainfoHttpHelper.parseCommand(tb);
//				
//			}
//		}
		
		log.info(JSON.toJSONString(message));
		ByteWrap bw = AsiainfoMessageHelper.encodeMessage(message);
		writeMessage(bw.getContent());
	}
	
	public static void asynWriteMessage(AsiainfoMessage message)
	{
		//log.info("send message");
		//log.info(JSON.toJSONString(message));
		ByteWrap bw = AsiainfoMessageHelper.encodeMessage(message);
		
		SendMessageThread st = new SendMessageThread(conn , bw.getContent());
		
		executor.execute(st);
	}
	
//	private static void writeMessage(ByteWrap bw) throws TimeoutException, IOException
//	{
//		writeMessage(bw.getContent());
//	}
	
	private static void writeMessage(byte[] message) throws TimeoutException, IOException
	{
		writeMessage(message , DEFAULT_TIME_OUT);
	}
	
	private static void writeMessage( byte[] message , long timeoutmillis) throws TimeoutException, IOException
	{
		SendMessageThread st = new SendMessageThread(conn , message);

		writeMessage(st , timeoutmillis);
	}
	
//	private static void writeMessage(String message) throws TimeoutException, IOException
//	{
//		writeMessage(message , DEFAULT_TIME_OUT);
//	}
	
//	private static void writeMessage( String message , long timeoutmillis) throws TimeoutException, IOException
//	{
//		SendMessageThread st = new SendMessageThread(conn , message);
//		
//		writeMessage(st , timeoutmillis);
//	}
	
	private static void writeMessage( SendMessageThread st , long timeoutmillis) throws TimeoutException, IOException
	{		
		Future<?> future = executor.submit(st);  
		try {  
            future.get(timeoutmillis, TimeUnit.MILLISECONDS);  
        } catch (TimeoutException e) {  
            future.cancel(true);
            throw e ;
        } catch (InterruptedException e) {
			log.error("", e);
		} catch (ExecutionException e) {
			log.error("", e);
		}  

		if ( st.getException() != null )
			throw st.getException();
	}
	
	public static void launchWebsocketServer() throws IOException, TimeoutException
	{
		synchronized(synObject)
		{
			ConnectThread ct = new ConnectThread();
			ct.setConn(conn);
			
			Future<?> future = executor.submit(ct);  
			try {  
	            future.get(DEFAULT_TIME_OUT * 5, TimeUnit.MILLISECONDS);  
	        } catch (TimeoutException e) {  
	            future.cancel(true);
	            log.error("", e);
	        } catch (InterruptedException e) {
				log.error("", e);
			} catch (ExecutionException e) {
				log.error("", e);
			}  
	
			if ( ct.getException() != null )
				throw ct.getException();
			
			conn = ct.getConn();
		}			
	}
	
	public static boolean isOpen()
	{
		return ( conn != null && conn.isOpen() );
	}
	

}
