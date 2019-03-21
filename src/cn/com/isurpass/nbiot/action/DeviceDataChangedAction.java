package cn.com.isurpass.nbiot.action;

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

public class DeviceDataChangedAction
{
	private static Log log = LogFactory.getLog(DeviceDataChangedAction.class);
	
	private ReportProcessor reportprocessor = new GatewayReportProcessor();
	
	public String execute()
	{
		String str = RequestHelper.readParameter();
		
		log.info(str);
		
		if ( StringUtils.isBlank(str))
			return Action.SUCCESS;
		
		JSONObject json = JSON.parseObject(str);
		
		String data = JSONUtil.getString(json, "service.data.rawData");
		
		if ( StringUtils.isBlank(data))
			return Action.SUCCESS;
		
		byte[] datab = JWStringUtils.hexStringtobyteArray(data);
		
		String deviceid = TlvWrap.readString(datab, TagDefine.TAG_GATEWAY_DEVICE_ID, TagDefine.TAG_HEAD_LENGTH);
		
        IConnectionContext cc = ConnectionManager.getConnection(deviceid); 
        
        NoiotConnectionWrap nbc = null ;
        if ( cc != null && cc instanceof NoiotConnectionWrap )
        {
        	nbc = ( NoiotConnectionWrap) cc ;
        	nbc.refresh();
        }
        else 
        {
        	nbc = new NoiotConnectionWrap(JSONUtil.getString(json, "deviceId"));
        
            Remoter r = new Remoter();
            r.setHaslogin(true);  // udp device need not login
            r.setUuid(deviceid);
            
            nbc.setAttachment(r);
            
            ConnectionManager.addConnection(deviceid, nbc);
    		JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE, new RemoteOnlineEvent(deviceid , new Date() ,false, 0));
			JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_UDP_GATEWAY_ONLINE, new RemoteOnlineEvent(deviceid, new Date(), true, false,datab, System.currentTimeMillis()));
    		JMSUtil.commitmessage();
        }
        
        if ( log.isInfoEnabled())
        	Utils.print(String.format("Receive nbiot data from %s", deviceid), datab);
        
        reportprocessor.processRequest(datab, nbc);
		
		return Action.SUCCESS;
	}
}
