package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.GatewayHelper;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.catalina.Server;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.ServerRuntime;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteOnlineEvent;
import com.iremote.common.jms.vo.RemoteOwnerChangeEvent;
import com.iremote.common.md5.MD5Util;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.GatewayCapability;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.IRemoteRequestProcessor;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;
import com.iremote.task.devicecommand.ExecuteDeviceCommand;
import com.iremote.thirdpart.wcj.report.ReportManager;

public class LoginProcessor implements IRemoteRequestProcessor 
{
	private static Log log = LogFactory.getLog(LoginProcessor.class);
	private static int[] CAPABILITY = new int[31];
	
	private String token;
	protected String deviceid;
	protected Remote remote;
	private Remoter remoter;
	private boolean haslogout = true;
	private Notification notification;
	
	public String getToken() {
		return token;
	}


	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException 
	{
		remoter = (Remoter)nbc.getAttachment();
		if ( remoter == null )
			return null ;
		if ( remoter.isHaslogin() == true )
			return createTime() ;
		if ( checklogin(request) == false )
		{
			log.info(deviceid + " login failed");
			nbc.close();
			return null;
		}
		
		remoter.setUuid(deviceid);
		
		IremotepasswordService svr = new IremotepasswordService();
		remote = svr.getIremotepassword(deviceid);
		
		if (remote != null && remote.getStatus() == IRemoteConstantDefine.REMOTE_STATUS_FORBIDDEN) {
			log.info(deviceid + " forbidden");
			nbc.close();
			return null;
		}
		
		if ( checkSecurityKey(request) == false )
		{
			log.info(deviceid + " login failed");
			nbc.close();
			return null ;
		}

		int network = remote.getNetwork();

		saveRemote(nbc , request);
		setGatewayCapability(request);

		if ( log.isInfoEnabled())
			log.info(String.format("%s login success from %s , %d" , deviceid , nbc.getRemoteAddress() , nbc.getConnectionHashCode()));
		if ( ConnectionManager.contants(deviceid) )
			haslogout = false ;
		
		ConnectionManager.addConnection(deviceid, nbc );
		
        saveOnlineNotification(checkConnection(network));
		
		//if (notification != null && notification.getEclipseby() == 0  )
			JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE, new RemoteOnlineEvent(deviceid , new Date() ,haslogout, 0));
		
		if ( GatewayUtils.isCobbeLock(remote))
		{
			nbc.setIdleTimeoutMillis( 20 * 1000);
		}
		else 
		{
			int timeout = TlvWrap.readInt(request, 105 , 4);
			if ( timeout <= 0 )
				timeout = 60 ;
			nbc.setIdleTimeoutMillis( Utils.calculateTimeout(timeout));
		}
		
		afterlogin(nbc);
		
		CommandTlv ct = createTime();
		
		ct.addUnit(new TlvIntUnit(1,0, 1 ));
		
		remoter.setHaslogin(true);

		changeGatewayHeartBeat(nbc);

	/*	if ("iRemote8005000001274".equals(deviceid))
			resetHeartbeat(nbc);*/

		return ct ;
	}

    protected boolean checkConnection(int oldNetwork) {
        boolean pushedSwitch = false;

	    remoter.setNetwork(remote.getNetwork());
        remoter.setNetworkIntensity(remote.getNetworkintensity());
        List<IConnectionContext> allConnection = ConnectionManager.getAllConnection(deviceid);
        if (allConnection != null && allConnection.size() > 1) {
	        if (IRemoteConstantDefine.NETWORK_3G.contains(remote.getNetwork()) && oldNetwork == IRemoteConstantDefine.NETWORK_WIFI) {
		        NotificationHelper.pushWarningNotification(remote, IRemoteConstantDefine.WARNING_TYPE_REMOTE_NET_WORK_CHANGED,
				        IRemoteConstantDefine.WARNING_TYPE_REMOTE_NET_WORK_CHANGED_SUFFIX_3G);
	        }
            pushedSwitch = true;
        }
        return pushedSwitch;
    }

    private void changeGatewayHeartBeat(IConnectionContext nbc) {
		GatewayCapability capability = GatewayHelper.getCapability(remote, IRemoteConstantDefine.GATEWAY_CAPABILITY_GATEWAY_HEART_BEAT);

		if (ServerRuntime.getInstance().getSystemcode() == IRemoteConstantDefine.PLATFORM_AMETA) {
			if (remote.getVersion().compareTo("1.2.47N") >= 0 && remote.getNetwork() != IRemoteConstantDefine.NETWORK_WIFI
					&& remote.getNetwork() != IRemoteConstantDefine.NETWORK_WIRED) {
				resetHeartbeat(nbc, ServerRuntime.getInstance().getDefaultheartbeatwithgsm());
			} else if (capability == null) {
				resetHeartbeat(nbc, ServerRuntime.getInstance().getDefaultheartbeatwithwifi());
			} else {
				setHeartBeatByCapability(nbc, capability);
			}
			return;
		}

		if (capability != null){
			setHeartBeatByCapability(nbc, capability);
		}
	}

	private void setHeartBeatByCapability(IConnectionContext nbc, GatewayCapability capability) {
		Integer second;
		try {
            second = Integer.valueOf(capability.getCapabilityvalue());
        } catch (Exception e) {
            return;
        }
		resetHeartbeat(nbc, second);
	}

	private void resetHeartbeat(IConnectionContext nbc)
	{
		resetHeartbeat(nbc, 300);
	}

	private void resetHeartbeat(IConnectionContext nbc, int heartbeat)
	{
		nbc.setIdleTimeoutMillis( Utils.calculateTimeout(heartbeat));
		CommandTlv ct = createLoginRquest();
		
		ct.addOrReplaceUnit(new TlvIntUnit(31 , nbc.getAttachment().getSequence() , 1));
		ct.addUnit(new TlvIntUnit(105 , heartbeat , 2));
		
		ScheduleManager.excutein(10, new ExecuteDeviceCommand(deviceid , ct , 1)); 
	}
	
	protected void afterlogin(IConnectionContext nbc)
	{
		ScheduleManager.excutein(3, new ExecuteDeviceCommand(deviceid , new CommandTlv(100 , 6) , 1));  // some gateway do not response this command   
	}
	
	private void setGatewayCapability(byte[] request)
	{
		if ( remote == null ) 
			return ;
		
		Integer c = TlvWrap.readInteter(request, TagDefine.TAG_REMOTE_CAPABILITY, TagDefine.TAG_HEAD_LENGTH);
		if ( c == null )
			return ;

		if ( remote.getCapability() != null )
		{
			List<GatewayCapability> rl = new ArrayList<GatewayCapability>();
			for ( GatewayCapability cc : remote.getCapability())
			{
				if ( cc.getCapabilitycode() > 32 )
					continue;
				if ( ( c & ( 1 << ( cc.getCapabilitycode() - 1 ) )) == 0 )
					rl.add(cc);
			}
			remote.getCapability().removeAll(rl);
		}
		
		for ( int i = 1 ; i < CAPABILITY.length ; i ++ )
		{
			if ( ( c & CAPABILITY[i] ) != 0 )
				GatewayReportHelper.ensureGatewayCapability(remote , i );
		}
		
	}
	
	protected boolean checkSecurityKey(byte[] request)
	{
		if ( remote == null )
			return true;
		byte[] sk = remote.getSecritykey();
		if ( sk == null || sk.length == 0 )
			return true;
		byte[] ck = TlvWrap.readTag(request, 101 , 4);
		if ( ck == null || ck.length == 0 )
		{
			log.error(String.format("%s should check security key" , remote.getDeviceid()));
			return true;
		}
		return MD5Util.checkRemote(deviceid, remoter.getToken(), remote.getSecritykey(), ck);
	}
	
	public CommandTlv createLoginRquest() 
	{
		token = UUID.randomUUID().toString();
		
		CommandTlv ct = new CommandTlv(100 , 1);
		
		ct.addUnit(new TlvByteUnit(100,token.getBytes()));
		
		return ct ;
	}
	
	private void saveOnlineNotification(boolean pushedSwitch)
	{
		if ( remote == null )
			return ;

		notification = new Notification();
		notification.setDeviceid(deviceid);
		notification.setMessage(pushedSwitch ? IRemoteConstantDefine.WARNING_TYPE_REMOTE_NET_WORK_CHANGED : IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE);
		notification.setReporttime(new Date());
		
		notification.setName(remote.getName());
		notification.setPhoneuserid(remote.getPhoneuserid());
		JSONObject json = new JSONObject();
		json.put("network", remote.getNetwork());
		notification.setAppendjson(json);
		if (!pushedSwitch) {
			if ( haslogout == false )
				notification.setEclipseby(1);
			else if ( new Date().getTime() - ServerRuntime.getInstance().getServerStartTime().getTime() < 5 * 60 * 1000)
				notification.setEclipseby(2);
		}

		if ( !GatewayUtils.isCobbeLock(remote))
		{
			//NotificationService ns = new NotificationService() ;
			//ns.save(notification);
			JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);

		}
		else 
			notification.setEclipseby(3);

		remote.setStatus(IRemoteConstantDefine.REMOTE_STATUS_ONLINE);
		
		notification.setNetwork(remote.getNetwork());
		notification.setNetworkintensity(remote.getNetworkintensity());
		
		if ( remote.getPhoneuserid() != null && !pushedSwitch)
			NotificationHelper.pushmessage(notification,remote.getPhoneuserid(), remote.getName());
		ReportManager.addReport(remote,notification);
	}
	
	private void saveRemote(IConnectionContext nbc , byte[] b)
	{
		if ( remote == null ) 
			remote = GatewayReportHelper.createRemote(deviceid);
		
		remote.setLastupdatetime(new Date());
		
		PhoneUser pu = null ;
		boolean sendinfochangedmessage = false ;
		if ( GatewayReportHelper.setRemote(b, remote) == true )
			sendinfochangedmessage = true;
		if ( GatewayReportHelper.checkhomeid(b, remote) == true )
		{
			GatewayReportHelper.clearRemote(remote);
			
			String phonenumber = null ;
			int phoneuserid = 0 ;
			
			if ( remote.getPhoneuserid() != null )
			{
				PhoneUserService pus = new PhoneUserService();
				pu = pus.query(remote.getPhoneuserid());
				
				if ( pu != null )
				{
					phonenumber = pu.getPhonenumber();
					phoneuserid = pu.getPhoneuserid();
				}
			}
			
			RemoteOwnerChangeEvent re = new RemoteOwnerChangeEvent(remote.getDeviceid() , new Date() , 0 , phoneuserid, null , phonenumber , 0);
			re.setRemote(remote);
			JMSUtil.sendmessage(IRemoteConstantDefine.MESSAGE_TYPE_REMOTE_RESET, re);
			
			sendinfochangedmessage = true;
		}
		if ( sendinfochangedmessage == true )
		{
			if ( pu == null && remote.getPhoneuserid() != null )
			{
				PhoneUserService pus = new PhoneUserService();
				pu = pus.query(remote.getPhoneuserid());
			}
			if ( pu != null )
			{
				pu.setLastupdatetime(new Date());
				PhoneUserHelper.sendInfoChangeMessage(pu);
			}
		}
		
		IremotepasswordService svr = new IremotepasswordService();
		svr.saveOrUpdate(remote);
	}
	
	private boolean checklogin(byte[] b)
	{
		if ( b == null )
			return false ;
		if ( b.length < 6 )
			return false ;
		if ( b[0] != 100 || b[1] != 2 )
			return false ;
		
		byte id[] = TlvWrap.readTag(b, 2 , 4);
		if ( id == null || id.length == 0 )
			return false ;
		
		deviceid = new String(id);
		
		return true;
	}

	public static CommandTlv createTime()
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		
		Calendar c = Calendar.getInstance() ;
		String str = sf.format(c.getTime());

		CommandTlv ct = new CommandTlv(100 , 3);
		ct.addUnit(new TlvByteUnit(102,str.getBytes()));
		ct.addUnit(new TlvIntUnit(103,( c.get(Calendar.DAY_OF_WEEK) - 1 ), 1 ));
		ct.addUnit(new TlvIntUnit(104,(int)(c.getTimeInMillis()/1000) , 4));
		ct.addUnit(new TlvIntUnit(211,ServerRuntime.getInstance().getTimezone(), 1));
		return ct;	
	}
	
	static
	{
		CAPABILITY[0] = 0 ;
		CAPABILITY[1] = 1 ;
		for ( int i = 2 ; i < CAPABILITY.length ; i ++ )
		{
			CAPABILITY[i] = CAPABILITY[i-1] * 2 ; 
		}
	}
}
