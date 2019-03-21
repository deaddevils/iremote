package cn.com.isurpass.nbiot.request;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.iremote.common.http.AsyncHttpBuilder;
import com.iremote.common.http.HttpBuilderBase;

public class NbiotHttpBuilder extends AsyncHttpBuilder
{
	private static Log log = LogFactory.getLog(NbiotHttpBuilder.class);
	
	public static String SELFCERTPATH = "/opt/tools/sslkey/nbiot/outgoing.CertwithKey.pkcs12";
	public static String SELFCERTPWD = "IoM@1234";
	public static String TRUSTCAPATH = "/opt/tools/sslkey/nbiot/ca.jks";
	public static String TRUSTCAPWD = "Huawei@123";
	public static final String BASE_URL = "https://device.api.ct10649.com:8743/";
	
	public NbiotHttpBuilder()
	{
		super();
		super.setCerfile(SELFCERTPATH, SELFCERTPWD);
		super.setTruststore(TRUSTCAPATH, TRUSTCAPWD);
		
	}

	@Override
	public HttpBuilderBase setUrl(String url)
	{
		return super.setUrl(BASE_URL + url);
	}
	
	@Override
	protected SSLContext createSSLContext()
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
			
			return sc;
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t);
		}
		return null ;
	}
	
}
