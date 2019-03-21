package com.iremote.asiainfo.connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.asiainfo.task.DeviceReportTaskProcessor;
import com.iremote.asiainfo.task.RemoteOnlineTaskProcessor;
import com.iremote.asiainfo.task.RemoteResetTaskProcessor;
import com.iremote.asiainfo.vo.AsiainfoMessage;
import com.iremote.asiainfo.vo.CommonResponse;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.taskmanager.QueueTaskManager;
import com.iremote.common.thread.SynchronizeRequestManager;

public class MessageProcessThread implements Runnable{

	private static Log log = LogFactory.getLog(MessageProcessThread.class);
	private static QueueTaskManager<Runnable> taskmanager = new QueueTaskManager<Runnable>();
	private AsiainfoMessage message;

	public MessageProcessThread(AsiainfoMessage message) {
		super();
		this.message = message;
	}

	@Override
	public void run() 
	{
		try
		{
			if ( message.getMessageid() == IRemoteConstantDefine.ASIAINFO_MSG_ID_ASIAINFO_COMMON_RESPONSE )  
				// Unlike reports which are put into a list waiting for processing, responses should be process first , for some reports in processing are waiting for them. 
			{
				CommonResponse r = JSON.parseObject(message.getMessageinfo(), CommonResponse.class);
				if ( log.isInfoEnabled() )
					log.info(String.format("asiainfo reply %s=%d", r.getReplySeriNum() , r.getReplyResult()));
				if ( r.getReplyResult() != 0 )
					log.info(JSON.toJSONString(r));
				if ( r.getReplyResult() == 3 )
					ConnectThread.getAccessToken();
				SynchronizeRequestManager.response(r.getReplySeriNum() , "Asininfo_request" ,  r);
			}
			else if ( message.getMessageid() == IRemoteConstantDefine.ASIAINFO_MSG_ID_DEVICE_REPORT )
			{
				taskmanager.addTask(message.getClientid() , new DeviceReportTaskProcessor( message));
			}
			else if ( message.getMessageid() == IRemoteConstantDefine.ASIAINFO_MSG_ID_DEVICE_ONLINE_REPORT )
			{
				taskmanager.addTask(message.getClientid() , new RemoteOnlineTaskProcessor( message));
			}
			else if (message.getMessageid() == IRemoteConstantDefine.ASIAINFO_MSG_ID_DEVICE_RESET )
			{
				String str = message.getMessageinfo() ;
				log.info(str);
				JSONObject json = (JSONObject) JSON.parse(str);
				String deviceid = json.getString("deviceId");

				taskmanager.addTask(deviceid , new RemoteResetTaskProcessor(deviceid , message));
			}
		}
		catch(Throwable t)
		{
			log.error(t.getMessage(), t);
		}

	}

}
