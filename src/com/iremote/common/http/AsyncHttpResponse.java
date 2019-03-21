package com.iremote.common.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;

public class AsyncHttpResponse implements FutureCallback<HttpResponse>
{
	private static Log log = LogFactory.getLog(AsyncHttpResponse.class);
	private long indentity ;
	
	public AsyncHttpResponse() 
	{
		super();
		this.indentity = System.currentTimeMillis();
		log.info(indentity);
	}
	
	public AsyncHttpResponse(long indentity) 
	{
		super();
		this.indentity = indentity;
		log.info(indentity);
	}

	@Override
	public void cancelled() 
	{
		log.info(indentity);
		log.info("cancelled");
		
	}

	@Override
	public void completed(HttpResponse response) 
	{
		log.info(indentity);
		try
		{
			HttpEntity entity = response.getEntity();
			
			String rst = EntityUtils.toString(entity , "UTF-8");
			log.info(rst);
		}
		catch(Throwable t)
		{
			log.info(t.getMessage() , t);
		}
	}

	@Override
	public void failed(Exception e) 
	{
		log.info(indentity);
		log.info(e.getMessage() , e);
		
	}

}
