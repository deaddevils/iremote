package com.iremote.infraredtrans.zwavecommand.doorlock;

import java.util.Date;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.asynctosync.AsynctosyncManager;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceStatusChange;
import com.iremote.common.taskmanager.IMulitReportTask;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.zwavecommand.IDoorlockopenProcessor;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor_Deprecated;
import com.iremote.infraredtrans.zwavecommand.ZwaveReportBean;
import com.iremote.service.ThirdPartService;

@Deprecated
public class DoorlockSelfDefineReportProcessor extends ZWaveReportBaseProcessor_Deprecated implements IDoorlockopenProcessor
{
	private long timeouttimemillis ;
	private int usercode;
	private String username ;
	
	@Override
	public void setReport(ZwaveReportBean zrb) 
	{
		super.setReport(zrb);
		
		if (isOpenreport())
			timeouttimemillis = System.currentTimeMillis() + 2 * 1000;
		else 
			timeouttimemillis = System.currentTimeMillis();
	}
	
	@Override
	public boolean merge(IMulitReportTask task) 
	{
		if ( !( task instanceof IDoorlockopenProcessor))
			return false;
		
		IDoorlockopenProcessor src = (IDoorlockopenProcessor)task;
		
		if ( !src.isOpenreport() || !this.isOpenreport() || src.getNuid() != this.getNuid() 
				|| Math.abs(src.getReporttime().getTime() - this.getReporttime().getTime() ) > 2 * 1000)
			return false ;
		
		return true; 
	}

	protected void processZwaveReport()
	{
		String message = null;
		
		if ( ( zrb.getCmd()[3] & 0xff ) == 0xE0 )
		{
			int s = 0 ;
			if ( zrb.getCmd()[7] == 1 )
			{
				message = IRemoteConstantDefine.WARNING_TYPE_TAMPLE;
				s = IRemoteConstantDefine.DEVICE_STATUS_TAMPLE_ALARM;
			}
			else if ( zrb.getCmd()[8] == 1 )
			{
				message = IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR;;
				s = IRemoteConstantDefine.LOCK_KEY_ERROR;
			}
			else if ( zrb.getCmd()[9] == 1 )
			{
				message = IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES;;
				s = IRemoteConstantDefine.LOCK_PASSWORD_ERROR_5_TIMES;
			}
			if ( message == null )
				return ;
			
			savenotification(message);
			pushtothirdpart();
			zrb.getDevice().setStatus(s);
			pushNotification();
			triggeAlarm();
			pushJSMMessage();
		}
		else if ( ( zrb.getCmd()[3] & 0xff ) == 0xB0 && ( zrb.getCmd()[8] & 0xff ) == 0x15 ) // open lock with password
		{
			savenotification(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN);
			
			initusercode();
			this.notification.setAppendmessage(getOperateorName());
			
			zrb.getDevice().setShadowstatus(0);
			if ( !DoorlockReportHelper.isDoorlockWarning(zrb.getDevice()) )
				zrb.getDevice().setStatus(0);
			
			pushmessage();
			//if (notification != null && notification.getEclipseby() == 0  )
			//	JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN, new ZWaveDeviceStatusChange(zrb.getDevice().getDeviceid() , zrb.getDevice().getZwavedeviceid() , zrb.getDevice().getNuid(), usercode , null , IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,zrb.getReporttime()  , zrb.getReportid()));
			startAssociationScene(0);
		}
		else if ( ( zrb.getCmd()[3] & 0xff ) == 0x80 && ( zrb.getCmd()[6] & 0xff ) == 0x0A )
		{
			String key =  String.format("%s_%d", IRemoteConstantDefine.SYNC_KEY_DELETE_PASSWORD , zrb.getDevice().getZwavedeviceid() );
			AsynctosyncManager.getInstance().onResponse(key, zrb.getCmd());
		}
		else if ( ( zrb.getCmd()[3] & 0xff ) == 0x80 || ( zrb.getCmd()[3] & 0xff ) == 0xD0 )
		{
			String key =  String.format("%s_%d", IRemoteConstantDefine.SYNC_KEY_SETPASSWORD , zrb.getDevice().getZwavedeviceid() );
			AsynctosyncManager.getInstance().onResponse(key, zrb.getCmd());
		}


	}
	
	private void initusercode()
	{
		byte[] uc = new byte[8];
		System.arraycopy(zrb.getCmd(), 9, uc, 0, uc.length);
		String suc = new String(uc);
		
		if ( IRemoteConstantDefine.DOOR_LOCK_TEMP_USER.equals(suc) )
		{
			usercode = 0xF2;
			//username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_TEMP_USER, getLanguange());
		}
		else if ( IRemoteConstantDefine.DOOR_LOCK_ADMIN_USER.equals(suc) )
		{
			usercode = 0xF0;
			//username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_ADMIN_USER, getLanguange());
		}
		else if ( suc != null && suc.startsWith(IRemoteConstantDefine.DOOR_LOCK_USER))
		{
			usercode = Integer.valueOf(suc.replace(IRemoteConstantDefine.DOOR_LOCK_USER, "")) ;
			//username = String.format("%s%d",MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_USER, getLanguange()) , usercode);
		}
	}
	
	private String getOperateorName()
	{
		if ( usercode == 0 )
			return this.getOperateorName(this.zrb.getOperator());
		else 
			return username;
	}
	
	private String getOperateorName(String operator)
	{
		ThirdPartService tps = new ThirdPartService();
		
		if ( operator == null || operator.length() == 0 )
			return operator;
		ThirdPart tp = tps.query(operator);
		if ( tp == null || tp.getName() == null || tp.getName().length() == 0 )
			return operator;
		
		return tp.getName();
	}
	
	public String getOperator() {
		return zrb.getOperator();
	}

	public void setOperator(String operator) {
		zrb.setOperator(operator); 
	}
	
	public int getNuid() {
		return zrb.getNuid();
	}

	@Override
	public boolean isOpenreport() 
	{
		return ( ( zrb.getCmd()[3] & 0xff ) == 0xB0 && ( zrb.getCmd()[8] & 0xff ) == 0x15 );
	}

	@Override
	public Date getReporttime() {
		return zrb.getReporttime();
	}

	@Override
	public boolean isReady() 
	{
		return System.currentTimeMillis() > timeouttimemillis;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public int getUsercode()
	{
		return 0;
	}

	@Override
	public int getUsertype()
	{
		return 0;
	}

	@Override
	public int getIntReporttime()
	{
		return 0;
	}

	@Override
	public void setNoSessionZwaveDevice(ZWaveDevice zwavedevice) {
		
	}
}
