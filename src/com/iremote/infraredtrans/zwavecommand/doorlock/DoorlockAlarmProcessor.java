package com.iremote.infraredtrans.zwavecommand.doorlock;

import java.util.Date;

import com.iremote.common.IRemoteConstantDefine;
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
public class DoorlockAlarmProcessor extends ZWaveReportBaseProcessor_Deprecated implements IDoorlockopenProcessor{

	private long timeouttimemillis ;
	private int usercode;
	private String username ;
	
	@Override
	public void setReport(ZwaveReportBean zrb) 
	{
		super.setReport(zrb);
		
		if (isOpenreport())
		{
			timeouttimemillis = System.currentTimeMillis() + 2 * 1000;
			initusercode();
		}
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
		
		if ( usercode == 0 )
			return false ;
		
		return true; 
	}

	@Override
	protected void processZwaveReport() 
	{
		if ( ( zrb.getCmd()[5] & 0xff ) == 255 
				&& ( zrb.getCmd()[6] & 0xff ) == 6 
				&& ( zrb.getCmd()[7] & 0xff ) == 16 )
		{	
			savenotification(IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES);
			zrb.getDevice().setStatus(IRemoteConstantDefine.LOCK_PASSWORD_ERROR_5_TIMES);
			pushNotification();		
			triggeAlarm();
		}
		else if (isOpenreport())
		{
			savenotification(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN);
			
			this.notification.setAppendmessage(getOperateorName());
			
			zrb.getDevice().setShadowstatus(1);
			if ( !DoorlockReportHelper.isDoorlockWarning(zrb.getDevice()) )
				zrb.getDevice().setStatus(1);

			pushmessage();
			//if (notification != null && notification.getEclipseby() == 0  )
			//	JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN, new ZWaveDeviceStatusChange(zrb.getDevice().getDeviceid() , zrb.getDevice().getZwavedeviceid() , zrb.getDevice().getNuid(), usercode , null , IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,zrb.getReporttime()  , zrb.getReportid()));
			startAssociationScene(0);
		}
		else if ( ( zrb.getCmd()[5] & 0xff ) == 255 
				&& ( zrb.getCmd()[6] & 0xff ) == 6 
				&& ( ( zrb.getCmd()[7] & 0xff ) == 1 || ( zrb.getCmd()[7] & 0xff ) == 5 ))
		{
			savenotification(IRemoteConstantDefine.EVENT_TYPE_DOOR_LOCK_CLOSE);
			
			initUsername();
			this.notification.setAppendmessage(username);
			int status = 255 ;
			
			zrb.getDevice().setShadowstatus(status);
			if ( !DoorlockReportHelper.isDoorlockWarning(zrb.getDevice()) )
			{
				zrb.getDevice().setStatus(status);
				pushmessage();
			}

			//if (notification != null && notification.getEclipseby() == 0  )
			//	JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS, new ZWaveDeviceStatusChange(zrb.getDevice().getDeviceid() , zrb.getDevice().getZwavedeviceid() , zrb.getDevice().getNuid(), status , null , notification.getMessage(),zrb.getReporttime() , zrb.getReportid() ));
			startAssociationScene(status);
		}
	}
	
	private void initusercode()
	{
		usercode = 0 ;
		if( zrb.getCmd().length > 9 )
		{
			usercode = zrb.getCmd()[9] & 0xff ;
			
//			DoorlockUserService svr = new DoorlockUserService();
//			username = svr.query(zrb.getDevice().getZwavedeviceid(), usercode);
			
			if ( username != null && username.length() > 0 )
				return ;
			
			if ( usercode >= 0xc9 && usercode <= 0xD2 )
			{
				//username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_TEMP_USER, PhoneUserHelper.getLanguange(zrb.getPhoneuser()));
			}
			else if (usercode >= 0x01 && usercode <= 0x32 )
			{
				//username = String.format("%s%d",MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_USER, PhoneUserHelper.getLanguange(zrb.getPhoneuser())) , usercode);
			}
			else 
				usercode = 0 ;
		}
		else 
		{
			initUsername();
		}
	}
	
	private void initUsername()
	{
		if ( (zrb.getCmd()[7] & 0xff) == 1 || (zrb.getCmd()[7] & 0xff) == 2 )
			username = "manual";
		else if ( (zrb.getCmd()[7] & 0xff) == 5 || (zrb.getCmd()[7] & 0xff) == 6 )
			username = "panel";
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
		return ( ( zrb.getCmd()[6] & 0xff ) == 6 
				&& (( zrb.getCmd()[7] & 0xff ) == 2 
					|| ( zrb.getCmd()[7] & 0xff ) == 6));
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
