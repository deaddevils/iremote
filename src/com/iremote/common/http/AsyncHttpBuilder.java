package com.iremote.common.http;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

public class AsyncHttpBuilder extends HttpBuilderBase
{
	private static Log log = LogFactory.getLog(AsyncHttpBuilder.class);
	protected static  CloseableHttpAsyncClient client;
	
	private void init() 
	{
		RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(50000)
                .setSocketTimeout(50000)
                .setConnectionRequestTimeout(10000)
                .build();
		IOReactorConfig ioReactorConfig = IOReactorConfig.custom().
                setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .setSoKeepAlive(true)
                .build();
		
		
		ConnectingIOReactor ioReactor=null;
        try {
            ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
        
        SSLContext sc = this.createSSLContext();
        PoolingNHttpClientConnectionManager connManager ;
        if ( sc != null )
        {
            SSLIOSessionStrategy sslioSessionStrategy = new SSLIOSessionStrategy(sc, SSLIOSessionStrategy.ALLOW_ALL_HOSTNAME_VERIFIER);

			Registry<SchemeIOSessionStrategy> registry = RegistryBuilder.<SchemeIOSessionStrategy>create()
			        .register("http", NoopIOSessionStrategy.INSTANCE)
			        .register("https", sslioSessionStrategy)
			        .build();
	        connManager = new PoolingNHttpClientConnectionManager(ioReactor,registry);
        }
        else 
        	connManager = new PoolingNHttpClientConnectionManager(ioReactor);
        	
        connManager.setMaxTotal(100);
        connManager.setDefaultMaxPerRoute(100);

        HttpAsyncClientBuilder bld =HttpAsyncClients.custom().
                setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig);        
        client = bld.build();
        client.start();
	}
	
	public void post(AsyncHttpResponse ahr)
	{
		log.info(url);
		HttpPost httpost = new HttpPost(url);
		
		addRequestHeader(httpost);
		addRequestParameter(httpost);
		getClient().execute(httpost , ahr);
	}
	
	public void postJson(String json , AsyncHttpResponse ahr)
	{
		log.info(url);
        HttpPost httpost = new HttpPost(url);
        addRequestHeader(httpost);
        
        log.info(json);
        httpost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        
        getClient().execute(httpost , ahr);
	}
	
	private CloseableHttpAsyncClient getClient()
	{
		if ( client != null )
			return client ;
		synchronized(log)
		{
			if ( client != null )
				return client ;
			init();
		}
		return client ;
	}
	
	protected SSLContext createSSLContext()
	{
		return null ;
	}
}
