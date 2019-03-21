package com.iremote.thirdpart.cobbe.event;

import com.iremote.common.TagDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteEvent;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class SendSleepCommand  extends RemoteEvent implements ITextMessageProcessor
{

	@Override
	public void run()
	{	
		CommandTlv ct = new CommandTlv(TagDefine.COMMAND_CLASS_GATEWAY , TagDefine.COMMAND_SUB_CLASS_GATEWAY_SLEEP);
		
		SynchronizeRequestHelper.asynchronizeRequest(super.getDeviceid(), ct , 1);
	}

	@Override
	public String getTaskKey()
	{
		return super.getDeviceid();
	}

}
