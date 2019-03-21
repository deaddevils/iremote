package cn.com.isurpass.pushmessage.server;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SslContextFactory
{
	private static Log log = LogFactory.getLog(SslContextFactory.class);
	private static final String  PROTOCOL = "TLS"; 
	
	public static SSLContext getSSLContent(String keystore , String truststore , String password )
	{
		SSLContext context = null ;
		
		try  
	    {  
	        KeyStore ks = KeyStore.getInstance("JKS");  
	        ks.load( new FileInputStream(new File(keystore)), password.toCharArray());  
	
	        // Set up key manager factory to use our key store  
	        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());  
	        kmf.init(ks, password.toCharArray());  
	
	        // truststore  
	        KeyStore ts = KeyStore.getInstance("JKS");  
	        ts.load(new FileInputStream(new File(truststore)), password.toCharArray());  
	
	        // set up trust manager factory to use our trust store  
	        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());  
	        tmf.init(ts);  
	
	        // Initialize the SSLContext to work with our key managers.  
	        context = SSLContext.getInstance(PROTOCOL);  
	        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);  
	        return context;
	    } 
	    catch (Exception e)  
	    {  
	        log.error(e.getMessage() , e);
	    } 
		
		return null ;
	}
}
