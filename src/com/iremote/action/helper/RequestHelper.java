package com.iremote.action.helper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class RequestHelper
{
	private static Log log = LogFactory.getLog(RequestHelper.class);
	
	public static void setHeader(String name , String value	)
	{
//		if ( 1 == 1 )
//			return ;
		HttpServletResponse reponse = ServletActionContext.getResponse();
		reponse.setHeader(name,value);
	}
	
	public static String getRemoteIp()
	{
//		if ( 1 == 1 )
//			return "113.118.246.203";
		HttpServletRequest request = ServletActionContext.getRequest();
		return getIpAddr(request);//request.getRemoteAddr();
	}
	
	public static String getRootPath()
	{
//		if ( 1 == 1 )
//			return "D:/iremote/source/04_fr_branch_03_for_netty/WebContent/";
		ActionContext actionContext = ActionContext.getContext();  
	    ServletContext servletContext = (ServletContext)actionContext.get(ServletActionContext.SERVLET_CONTEXT);  
	    return servletContext.getRealPath("/");  
	}
	
	public static String readParameter()
	{
		try
        {
			HttpServletRequest request = ServletActionContext.getRequest(); 
	        int contentLength = request.getContentLength();
	        if(contentLength<0){
	            return null;
	        }
	        byte buffer[] = new byte[contentLength];
	        for (int i = 0; i < contentLength;) {
	            int len = request.getInputStream().read(buffer, i, contentLength - i);
	            if (len == -1) {
	                break;
	            }
	            i += len;
	        }
	        return new String(buffer, "utf-8");
        }
        catch(Throwable t )
        {
        	log.error(t.getMessage() , t);
        }
		return null ;
	}
	
    private static String getIpAddr(HttpServletRequest request)
    {  
        String ipAddress = request.getHeader("x-forwarded-for");  
        
        if(StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) 
            ipAddress = request.getHeader("Proxy-Client-IP");  
        
        if(StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress))  
            ipAddress = request.getHeader("WL-Proxy-Client-IP");  
        
        if(StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) 
        	ipAddress = request.getRemoteAddr();
        
        if( !StringUtils.isBlank(ipAddress) && ipAddress.indexOf(",")>0)  
            ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  

        if ( StringUtils.isBlank(ipAddress) )
        	ipAddress = "unknow";
        return ipAddress;   
    } 
    
}
