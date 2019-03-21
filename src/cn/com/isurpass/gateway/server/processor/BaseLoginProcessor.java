package cn.com.isurpass.gateway.server.processor;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.LoginProcessor;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.task.devicecommand.ExecuteDeviceCommand;

public abstract class BaseLoginProcessor extends LoginProcessor
{
	@Override
	protected void afterlogin(IConnectionContext nbc)
	{
		if ( StringUtils.isNotBlank(remote.getSecritykeybase64()))
		{
			nbc.getAttachment().setKey3(getSecurityKey().createkye3(deviceid, nbc.getAttachment().getToken(), remote.getSecritykey(), nbc.getAttachment().getTime1()));

			super.afterlogin(nbc);
			return ;
		}
		
		CommandTlv ct = new CommandTlv(TagDefine.COMMAND_CLASS_ENCRYPT , TagDefine.COMMAND_SUB_CLASS_SECURITYKEY_REQUEST);
		
		nbc.getAttachment().setToken2(Utils.createsecuritytoken(32));
		nbc.getAttachment().setTime2((int)(System.currentTimeMillis()/1000));
		nbc.getAttachment().setKey2(Utils.createsecuritykey(16));
		nbc.getAttachment().setHaslogin(true);
		
		ct.addUnit(new TlvByteUnit(100,nbc.getAttachment().getToken2().getBytes()));
		ct.addUnit(new TlvIntUnit(TagDefine.TAG_TIME , nbc.getAttachment().getTime2() , 4));
		ct.addUnit(new TlvIntUnit(31 , nbc.getAttachment().getSequence() , 1));
		ct.addUnit(new TlvByteUnit(24 ,getSecurityKey().encryptKey2(nbc.getAttachment().getKey2(), nbc.getAttachment().getToken2(), nbc.getAttachment().getTime2()) ));
		
		ScheduleManager.excutein(3, new ExecuteDeviceCommand(deviceid , ct , 1));     
	}
	
	protected abstract IGatewaySecurityKey getSecurityKey();
}
