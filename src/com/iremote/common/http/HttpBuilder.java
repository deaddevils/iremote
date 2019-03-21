package com.iremote.common.http;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class HttpBuilder
{
	private static Log log = LogFactory.getLog(HttpBuilder.class);
	
	private String url ;
	private Map<String , String> header = new HashMap<String , String>();
	private Map<String , String> parameter = new HashMap<String , String>();
	private String cerfile ;
	private String cerpassword;
	private String truststorefile;
	private String truststorepassword;
	
	public HttpBuilder setUrl(String url)
	{
		this.url = url ;
		return this;
	}
	
	public HttpBuilder appendHearder(String key , String value)
	{
		header.put(key, value);
		return this ;
	}
	
	public HttpBuilder appendParameter(String key , String value)
	{
		parameter.put(key, value);
		return this ;
	}
	
	public HttpBuilder setCerfile(String filename , String password)
	{
		this.cerfile = filename ;
		this.cerpassword = password;
		return this ;
	}
	
	public HttpBuilder setTruststore(String filename , String password)
	{
		this.truststorefile = filename ;
		this.truststorepassword = password;
		return this ;
	}
	
	public String post()
	{
		log.info(url);
		HttpPost httpost = new HttpPost(url);
		
		addRequestHeader(httpost);
		addRequestParameter(httpost);
		return executeHttpRequest(httpost);
	}
	
	public String postJson(String json)
	{
        HttpPost httpost = new HttpPost(url);
        addRequestHeader(httpost);
        
        log.info(json);
        httpost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        
        return executeHttpRequest(httpost);
	}
	
	public String postJson()
	{
        return this.postJson(JSON.toJSONString(parameter));
	}
	
	private String executeHttpRequest(HttpUriRequest request)
	{
		try
		{			
			HttpClient httpclient = createHttpClient(this.url) ; 
			HttpResponse response = httpclient.execute(request);
			
			HttpEntity entity = response.getEntity();
	
			String rst = EntityUtils.toString(entity , "UTF-8");
			log.info(rst);
			
			return rst ;
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t);
		}
		return "";
	}
	
    private void addRequestHeader(HttpUriRequest request)
    {
        if (this.header.size() == 0)
            return;

        for (String headerName : header.keySet())
            request.addHeader(headerName, header.get(headerName));
    }

    private void addRequestParameter(HttpPost request)
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
	
	private HttpClient createHttpClient(String url)
	{
		if ( url.startsWith("https"))
		{
			try
			{
				KeyManager[] km = null ;
				TrustManager[] tm = null ;
				
				if ( this.cerfile != null )
				{
					KeyStore keystore = KeyStore.getInstance("pkcs12");
					keystore.load(new FileInputStream(this.cerfile), this.cerpassword.toCharArray());
					KeyManagerFactory kmf = KeyManagerFactory.getInstance("sunx509");
					kmf.init(keystore, this.cerpassword.toCharArray());
					km = kmf.getKeyManagers();
				}
				
				if ( this.truststorefile != null )
				{
					KeyStore caCert = KeyStore.getInstance("jks");
					caCert.load(new FileInputStream(this.truststorefile), this.truststorepassword.toCharArray());
					TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
					tmf.init(caCert);
					tm = tmf.getTrustManagers();
				}
				
				SSLContext sc = SSLContext.getInstance("TLS");
				sc.init(km, tm , null);
			
				final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sc,NoopHostnameVerifier.INSTANCE);
	
				final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				        .register("http", new PlainConnectionSocketFactory())
				        .register("https", sslsf)
				        .build();
	
				final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
				cm.setMaxTotal(100);
				return HttpClients.custom()
				        .setSSLSocketFactory(sslsf)
				        .setConnectionManager(cm)
				        .build();
			}
			catch(Throwable t)
			{
				log.error(t.getMessage() , t);
				return null ;
			}
		}
		else 
			return HttpClientBuilder.create().build();
	}
}

