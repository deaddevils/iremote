package com.iremote.thirdpart.cobbe.event;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteEvent;
import com.iremote.common.jms.vo.RemoteOnlineEvent;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.action.SetPasswordThread;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

public class SendPassword extends RemoteOnlineEvent implements ITextMessageProcessor
{
	private boolean haswaited = false ;
	
	@Override
	public void run()
	{		
		if ( !GatewayUtils.isCobbeLock(super.getDeviceid()) )
			return ;
		
		RemoteService rs = new RemoteService();
		Remote remote = rs.getIremotepassword(super.getDeviceid());
		Integer oid = remote.getPhoneuserid();
		if ( oid == null || oid == 0 )
		{
			this.sendSleepCommand();
			return ;
		}
		
		ZWaveDeviceService svr = new ZWaveDeviceService() ;
		ZWaveDevice zd = svr.querybydeviceid(super.getDeviceid(), IRemoteConstantDefine.DEVICE_NUID_WIFI_LOCK);

		if ( zd == null )
			return ;
		
		DoorlockPasswordService dpsvr = new DoorlockPasswordService();
		List<DoorlockPassword> elst = dpsvr.queryCobbePassword(zd.getZwavedeviceid());
		
		Random r = new Random(System.currentTimeMillis());
		Set<Integer> usercodeset = new HashSet<Integer>();
		for ( DoorlockPassword dp: elst)
		{
			if ( dp.getValidthrough().getTime() > System.currentTimeMillis())
			{
				usercodeset.add(dp.getUsercode());
				continue;
			}
			dp.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ECLIPSED);
			
			//DoorlockPassword doorlockpassword = DoorLockPasswordHelper.createLockTempPassword(dp.getZwavedeviceid(), dp.getUsercode(), String.format("%06d", r.nextInt(1000000)));
			//dpsvr.save(doorlockpassword);
		}
		
		int passwordcont = 10 ;
		if ( remote.getRemotetype() == IRemoteConstantDefine.IREMOTE_TEYP_TONGXING )
			passwordcont = 1 ;
		for ( int i = 0 ; i < passwordcont ; i ++ )
		{
			int usercode = 0xf3 + i ;
			if ( usercodeset.contains(usercode))
				continue;
			
			DoorlockPassword doorlockpassword = DoorLockPasswordHelper.createLockTempPassword(zd.getZwavedeviceid(),usercode , String.format("%06d", r.nextInt(1000000)));

			dpsvr.save(doorlockpassword);
		}
		
		List<DoorlockPassword> lst = dpsvr.queryWaitingPassword(zd.getZwavedeviceid());
		
		SetPasswordThread sp = null ;
		for ( DoorlockPassword dp : lst )
		{
			sp = new SetPasswordThread(dp);
			sp.sendpassword(); 
			if ( sp.getResultCode() == ErrorCodeDefine.GATEWAY_ERROR_CODE_BUSSING)
			{
				if ( haswaited == false )
				{
					haswaited = true;
					ScheduleManager.excutein(3, this);
				}
				else 
					sendSleepCommand();
				return;
			}
		}
		
		if ( sp != null )
			sp.sendCurrentTime();
		
		sendSleepCommand();
	}

	@Override
	public String getTaskKey()
	{
		return super.getDeviceid();
	}

	private void sendSleepCommand()
	{
		RemoteEvent re = new RemoteEvent(super.getDeviceid(),super.getEventtime() , super.getTaskIndentify());	
		JMSUtil.sendmessage(IRemoteConstantDefine.DOOR_LOCK_SLEEP, re);
	}
}
