package com.iremote.infraredtrans.zwavecommand.doorlock;

import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import com.iremote.action.helper.DoorlockHelper;
import com.iremote.action.helper.GatewayHelper;
import com.iremote.action.helper.TimeZoneHelper;
import com.iremote.common.taskmanager.IMulitReportTask;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.zwavecommand.IZwaveReportProcessor;
import com.iremote.infraredtrans.zwavecommand.ZwaveReportBean;

public class DoorlockTimeRequestProcessor implements IZwaveReportProcessor
{
	private ZwaveReportBean zrb ;
	protected boolean finished = false ;
	protected boolean executed = false ;
	
	@Override
	public boolean merge(IMulitReportTask task)
	{
		return false;
	}

	@Override
	public boolean isReady()
	{
		return true;
	}

	@Override
	public boolean isExecuted()
	{
		return executed;
	}

	@Override
	public boolean isFinished()
	{
		return finished;
	}

	@Override
	public void run()
	{
		executed = true ;
			
		Date d = new Date();
		String tzid = GatewayHelper.getRemoteTimezoneId(zrb.getRemote());
		if ( StringUtils.isNotBlank(tzid))
			d = TimeZoneHelper.timezoneTranslate(new Date(), TimeZone.getDefault().getID(), tzid);
		DoorlockHelper.sendCurrentTime(zrb.getDeviceid(), zrb.getNuid()  , d);
		
		SynchronizeRequestHelper.asynchronizeRequest(zrb.getDeviceid(), createResult(zrb.getReportsequence()), 10);
		
		finished = true ;
	}
	
	protected CommandTlv createResult(int sequence)
	{
		CommandTlv rst = new CommandTlv(30 , 10);
		
		if (sequence != 0 && sequence != Integer.MIN_VALUE)
			rst.addOrReplaceUnit(new TlvIntUnit(31,sequence,2));
		return rst ;
	}

	protected CommandTlv createCommandTlv(byte[] command)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , command));
		int nuid = zrb.getNuid();
		if ( nuid <= 256 )
			ct.addUnit(new TlvIntUnit(71 , nuid , 1));
		else 
			ct.addUnit(new TlvIntUnit(71 , nuid , 4));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		return ct ;
	}
	
	@Override
	public void setReport(ZwaveReportBean zrb)
	{
		this.zrb = zrb;
	}

	@Override
	public void setNoSessionZwaveDevice(ZWaveDevice zwavedevice) {
		
	}

}
