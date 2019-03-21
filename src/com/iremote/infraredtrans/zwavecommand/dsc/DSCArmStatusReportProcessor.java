package com.iremote.infraredtrans.zwavecommand.dsc;

import java.util.List;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.phoneuser.SetPhoneUserArmStatus;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;
import com.iremote.infraredtrans.zwavecommand.notifiy.IZwaveReportConsumer;
import com.iremote.infraredtrans.zwavecommand.notifiy.ZwaveReportNotifyManager;
import com.iremote.service.PhoneUserService;

@Deprecated
public class DSCArmStatusReportProcessor extends ZWaveReportBaseProcessor
{
	public DSCArmStatusReportProcessor() {
		super();
		this.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus()
	{
		if ( isReportofUserOperate())
			return ;
		
		int status = zrb.getCommandvalue().getValue();
		
		if ( zrb.getDevice().getStatus() != null && status == zrb.getDevice().getStatus())
			return ;
		
		zrb.getDevice().setStatus(status);

		SetPhoneUserArmStatus action = new SetPhoneUserArmStatus();
		if ( status == 0xff )
		{
			int armstatus =  PhoneUserHelper.getPhoneuserArmStatus(zrb.getPhoneuser());
			if ( armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM)
				action.setArmstatus(IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM);
			else 
				action.setArmstatus(armstatus);
		}
		else 
			action.setArmstatus(IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM);
		
		Integer puid = zrb.getRemote().getPhoneuserid();
		if ( puid == null )
			return ;
		
		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(puid);
		action.setPhoneuser(pu);
		
		action.execute();
		
		if ( action.getResultCode() != ErrorCodeDefine.SUCCESS )
		{
			NotificationHelper.pushArmFailedMessage(pu , "");
		}
	}

	private boolean isReportofUserOperate()
	{
		List<IZwaveReportConsumer> lst = ZwaveReportNotifyManager.getInstance().getConsumerList(zrb.getDeviceid() , zrb.getNuid(),new byte[]{0x25,0x03});
		return ( lst != null && lst.size() > 0 );
	}
	
	@Override
	public String getMessagetype()
	{
		if ( isReportofUserOperate())
			return null;
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}
