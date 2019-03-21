package com.iremote.common.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;


public class HttpBuilderBase 
{
	private static Log log = LogFactory.getLog(HttpBuilderBase.class);
	
	protected String url ;
	protected Map<String , String> header = new HashMap<String , String>();
	protected Map<String , String> parameter = new HashMap<String , String>();
	protected String cerfile ;
	protected String cerpassword;
	protected String truststorefile;
	protected String truststorepassword;
	
	public HttpBuilderBase setUrl(String url)
	{
		this.url = url ;
		return this;
	}
	
	public HttpBuilderBase appendHearder(String key , String value)
	{
		header.put(key, value);
		return this ;
	}
	
	public HttpBuilderBase appendParameter(String key , String value)
	{
		parameter.put(key, value);
		return this ;
	}
	
	public HttpBuilderBase setCerfile(String filename , String password)
	{
		this.cerfile = filename ;
		this.cerpassword = password;
		return this ;
	}
	
	public HttpBuilderBase setTruststore(String filename , String password)
	{
		this.truststorefile = filename ;
		this.truststorepassword = password;
		return this ;
	}

	protected void addRequestHeader(HttpUriRequest request)
    {
        if (this.header.size() == 0)
            return;

        for (String headerName : header.keySet())
            request.addHeader(headerName, header.get(headerName));
    }

    protected void addRequestParameter(HttpPost request)
    {
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		
		for ( String key : parameter.keySet())
		{
			nvps.add(new BasicNameValuePair(key, parameter.get(key)));
			if ( log.isInfoEnabled() )
				log.info(String.format("%s:%s", key ,parameter.get(key)));
		}
		
		try {
			request.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		} catch (UnsupportedEncodingException t) {
			log.error(t.getMessage() , t);
		}
    }

}
