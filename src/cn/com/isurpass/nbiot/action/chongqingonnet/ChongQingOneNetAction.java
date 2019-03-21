package cn.com.isurpass.nbiot.action.chongqingonnet;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.RequestHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JSONUtil;
import com.iremote.common.JWStringUtils;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteOnlineEvent;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.ReportProcessor;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.opensymphony.xwork2.Action;

import cn.com.isurpass.gateway.server.processor.gateway.GatewayReportProcessor;


public class ChongQingOneNetAction 
{
	private static Log log = LogFactory.getLog(ChongQingOneNetAction.class);
	
	private ReportProcessor reportprocessor = new GatewayReportProcessor();
	private String msg;
    private InputStream inputStream; 

	public String execute()
	{
		if ( StringUtils.isNotBlank(msg))
		{
			log.info(msg);
			try {
				inputStream = new ByteArrayInputStream(msg.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage() , e);
			}
			return "inittest";
		}
		
		String content = RequestHelper.readParameter();
		log.info(content);

		if ( StringUtils.isBlank(content))
			return Action.SUCCESS;

		JSONObject json = JSON.parseObject(content);
		
		String data = JSONUtil.getString(json, "msg.value");
		
		if ( StringUtils.isBlank(data))
			return Action.SUCCESS;
		
		byte[] datab = JWStringUtils.hexStringtobyteArray(data);
		
		String deviceid = TlvWrap.readString(datab, TagDefine.TAG_GATEWAY_DEVICE_ID, TagDefine.TAG_HEAD_LENGTH);
		
		if ( StringUtils.isBlank(deviceid))
		{
			log.error("deviceid is null");
			return Action.SUCCESS;
		}
		
        IConnectionContext cc = ConnectionManager.getConnection(deviceid); 
        
        ChongQingOneNetWrap nbc = null ;
        if ( cc != null && cc instanceof ChongQingOneNetWrap )
        {
        	nbc = ( ChongQingOneNetWrap) cc ;
        	nbc.refresh();
        }
        else 
        {
        	nbc = new ChongQingOneNetWrap(JSONUtil.getString(json, "msg.imei"));
        
            Remoter r = new Remoter();
            r.setHaslogin(true);  // udp device need not login
            r.setUuid(deviceid);
            
            nbc.setAttachment(r);
            
            ConnectionManager.addConnection(deviceid, nbc);
    		JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE, new RemoteOnlineEvent(deviceid , new Date() ,false, 0));
    		JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_UDP_GATEWAY_ONLINE, new RemoteOnlineEvent(deviceid , new Date() , true, false,datab, 0));
    		JMSUtil.commitmessage();
        }
        
        if ( log.isInfoEnabled())
        	Utils.print(String.format("Receive nbiot data from %s", deviceid), datab);
        
        reportprocessor.processRequest(datab, nbc);
		
		return Action.SUCCESS;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
    
    public InputStream getInputStream() {  
        return inputStream;  
    } 
}
