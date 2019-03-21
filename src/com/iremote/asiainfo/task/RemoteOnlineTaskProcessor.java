package com.iremote.asiainfo.task;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.NotificationHelper;
import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.asiainfo.vo.AsiainfoMessage;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.domain.Notification;
import com.iremote.domain.Remote;
import com.iremote.infraredtrans.RemoteHandler;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.NotificationService;

public class RemoteOnlineTaskProcessor implements Runnable {

	private static Log log = LogFactory.getLog(RemoteOnlineTaskProcessor.class);
	
	private AsiainfoMessage message;

	public RemoteOnlineTaskProcessor(AsiainfoMessage message) {
		super();
		this.message = message;
	}

	@Override
	public void run() 
	{
		String str = message.getMessageinfo() ;
		log.info(str);
		JSONObject json = (JSONObject) JSON.parse(str);
		if ( json.containsKey("status"))
		{
			int status = json.getIntValue("status");
			if ( status == 0 || status == 2  )
			{
				RemoteHandler.sendOfflineNotification(message.getClientid());
			}
			else if ( status == 1 || status == 3 )
			{
				sendOnlineNotification(message.getClientid());
			}
		}
		AsiainfoHttpHelper.CommonResponse(message);
	}

	private void sendOnlineNotification(String deviceid)
	{
		IremotepasswordService svr = new IremotepasswordService();
		Remote remote = svr.getIremotepassword(deviceid);

		if ( remote == null )
			return ;

		Notification n = new Notification();
		n.setDeviceid(remote.getDeviceid());
		n.setMessage(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE);
		n.setReporttime(new Date());
		
		n.setName(remote.getName());
		n.setPhoneuserid(remote.getPhoneuserid());
		
//		NotificationService ns = new NotificationService() ;
//		ns.save(n);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);
		remote.setStatus(IRemoteConstantDefine.REMOTE_STATUS_ONLINE);

		
		if ( remote.getPhoneuserid() != null )
			NotificationHelper.pushmessage(n,remote.getPhoneuserid(), remote.getName());
		
	}
}
