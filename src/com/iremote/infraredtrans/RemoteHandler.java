package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IConnectionTimeoutHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IIdleTimeoutHandler;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.IDisconnectHandler;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.constant.GatewayConnectionStatus;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteEvent;
import com.iremote.domain.Notification;
import com.iremote.domain.Remote;
import com.iremote.service.IremotepasswordService;
import com.iremote.thirdpart.wcj.report.ReportManager;

public abstract class RemoteHandler implements IDataHandler, IConnectHandler,
		IIdleTimeoutHandler ,IConnectionTimeoutHandler,IDisconnectHandler{
	
	private static Log log = LogFactory.getLog(RemoteHandler.class);
	private static Logger loggatewaydata = Logger.getLogger("gatewaydataFile");
	
	@Override
	public boolean onData(INonBlockingConnection nbc) throws IOException,
			BufferUnderflowException, ClosedChannelException,
			MaxReadSizeExceededException 
	{
		if ( nbc == null || nbc.getAttachment() == null || nbc.available() <= 0  )
			return false;

		ByteBuffer bf = ByteBuffer.allocate(nbc.available());
		int length = nbc.read(bf);
		
		byte b[] = bf.array();
		
		if ( log.isInfoEnabled())
			Utils.print(String.format("Receive data from %s(%d)", getUuid(nbc) , nbc.hashCode()),b , length);
		if (loggatewaydata.isInfoEnabled() )
			Utils.printInfo(loggatewaydata ,getUuid(nbc) ,b , length);
		else 
			loggatewaydata.error(getUuid(nbc));
		
		try
		{
			byte[][] request = splitRequest(b , length);
			processRequest(request , nbc);
		}
		catch(Throwable t)
		{
			log.error("", t);
			return true;
		}

		return true;
	}
	
	protected abstract void processRequest(byte[][] request , INonBlockingConnection nbc) throws BufferOverflowException, IOException;
	
	protected abstract IConnectionContext createConnectionContext(INonBlockingConnection nbc);

	protected abstract byte[][] splitRequest(byte[] content , int length);

	public static String getUuid(INonBlockingConnection nbc)
	{
		if ( nbc == null || nbc.getAttachment() == null )
			return "";
		Remoter r = (Remoter)nbc.getAttachment();
		if ( r.isHaslogin() == true )
			return r.getUuid();
		return r.getToken();
	}

	@Override
	public boolean onConnect(INonBlockingConnection nbc) throws IOException,
			BufferUnderflowException, MaxReadSizeExceededException 
	{
		nbc.setAutoflush(true);
		
		Remoter r = new Remoter();
		r.setToken("");
		r.setSoftversion(getSoftversion());
		nbc.setAttachment(r);
		
		//to avoid sending a login command while remote is busying . 
		//waiting for remote sending a valid command , such as heart beat , and than send a login request to remote
		//sendLoginRequest(nbc);    
		
		return true;
	}
	
	protected abstract int getSoftversion();

	@Override
	public boolean onIdleTimeout(INonBlockingConnection nbc) throws IOException 
	{
		HibernateUtil.prepareSession(IRemoteConstantDefine.HIBERNATE_SESSION_REMOTE);
		HibernateUtil.beginTransaction();
		
		try
		{
			String uuid = getUuid(nbc);
			log.info(String.format("onIdleTimeout %s(%d)", uuid , nbc.hashCode()));
		
			onConnectClose(nbc , uuid);
			
			HibernateUtil.commit();
		}
		catch(Throwable t)
		{
			log.error(t.getMessage(), t);
			HibernateUtil.rollback();
		}
		finally
		{
			HibernateUtil.closeSession();
			JMSUtil.commitmessage();
		}
		return true;
	}
	
	public void onConnectClose(INonBlockingConnection nbc , String uuid)
	{
		if ( ConnectionManager.removeConnection(this.createConnectionContext(nbc)) == true )
		{
			sendOfflineNotification(uuid);
			IConnectionContext icc = this.createConnectionContext(nbc);
			icc.getAttachment().setStatus(GatewayConnectionStatus.hasDisconnected);
		}
	}

	public static void sendOfflineNotification(String deviceid)
	{
		sendOfflineNotification(deviceid, 0 );
	}
	
	public static void sendOfflineNotification(String deviceid , int second)
	{
		IremotepasswordService svr = new IremotepasswordService();
		Remote r = svr.getIremotepassword(deviceid);
		if ( r == null )
			return ;
		
		if ( GatewayUtils.isCobbeLock(r))
			return ;

		boolean pushed = checkConnection(deviceid, r);

		Notification n = new Notification();
		n.setDeviceid(deviceid);
		n.setMessage(pushed ? IRemoteConstantDefine.WARNING_TYPE_REMOTE_NET_WORK_CHANGED : IRemoteConstantDefine.WARNING_TYPE_REMOTE_OFFLINE);
		
		Calendar d = Calendar.getInstance();
		if ( second != 0 )
			d.add(Calendar.SECOND, -1 * second);
		n.setReporttime(d.getTime());
		n.setName(r.getName());
		n.setPhoneuserid(r.getPhoneuserid());
		if(IRemoteConstantDefine.REMOTE_STATUS_FORBIDDEN!=r.getStatus()){
			r.setStatus(IRemoteConstantDefine.REMOTE_STATUS_OFFLINE);
		}
		JSONObject json = new JSONObject();
		json.put("network", r.getNetwork());
		n.setAppendjson(json);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);

		if (!pushed) {
			NotificationHelper.pushWarningNotification(n, r.getName() , null , false, getMessage(IRemoteConstantDefine.WARNING_TYPE_REMOTE_OFFLINE, r.getNetwork()));
		}
		ReportManager.addReport(r,n);

		JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_REMOTE_OFFLINE, new RemoteEvent(r.getDeviceid() , n.getReporttime() , 0));
	}

	private static String getMessage(String type, int network) {
		if (network == IRemoteConstantDefine.NETWORK_WIFI) {
			return type + IRemoteConstantDefine.WARNING_TYPE_REMOTE_NET_WORK_CHANGED_SUFFIX_WIFI;
		} else if (IRemoteConstantDefine.NETWORK_3G.contains(network)) {
			return type + IRemoteConstantDefine.WARNING_TYPE_REMOTE_NET_WORK_CHANGED_SUFFIX_3G;
		}
		return null;
	}

	protected static boolean checkConnection(String deviceid, Remote r) {
		boolean pushed = false;
		List<IConnectionContext> allConnection = ConnectionManager.getAllConnection(deviceid);
		if (allConnection != null && allConnection.size() > 1) {
			pushed = true;
			Result result = get3GConnection(allConnection);
			r.setNetworkintensity(result.networkIntensity);
			if (!result.has3G) {
				r.setNetwork(IRemoteConstantDefine.NETWORK_WIFI);
				NotificationHelper.pushWarningNotification(r, IRemoteConstantDefine.WARNING_TYPE_REMOTE_NET_WORK_CHANGED,
						IRemoteConstantDefine.WARNING_TYPE_REMOTE_NET_WORK_CHANGED_SUFFIX_WIFI);
			}
		}
		return pushed;
	}

	private static Result get3GConnection(List<IConnectionContext> allConnection) {
		Result result = new Result();
		for (IConnectionContext iConnectionContext : allConnection) {
			if (IRemoteConstantDefine.NETWORK_3G.contains(iConnectionContext.getAttachment().getNetwork())) {
				result.has3G = true;
				result.networkIntensity = iConnectionContext.getAttachment().getNetworkIntensity();
				return result;
			}
			if (iConnectionContext.getAttachment().getNetwork() == IRemoteConstantDefine.NETWORK_WIFI) {
				result.networkIntensity = iConnectionContext.getAttachment().getNetworkIntensity();
			}
		}
		return result;
	}

	@Override
	public boolean onConnectionTimeout(INonBlockingConnection nbc) throws IOException 
	{
		log.info("onConnectionTimeout " + getUuid(nbc));
		return true;
	}

	@Override
	public boolean onDisconnect(INonBlockingConnection nbc) throws IOException 
	{
		IConnectionContext icc =this.createConnectionContext(nbc);
		
		//icc.getAttachment().setStatus(GatewayConnectionStatus.disconnectTimeWait);

		//String uuid = getUuid(nbc);
		log.info(String.format("onDisconnect %s(%d)" , icc.getDeviceid() , nbc.hashCode()));
		
		XSocketReportProcessor.getInstance().addTask(icc.getDeviceid(), new DisconnectionProcessor( icc , icc.getDeviceid()));
		
		return true;
	}

	static class Result{
		boolean has3G;
		int networkIntensity;
	}
}
