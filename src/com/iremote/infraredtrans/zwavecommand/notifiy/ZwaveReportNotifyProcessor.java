package com.iremote.infraredtrans.zwavecommand.notifiy;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.taskmanager.IMulitReportTask;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.zwavecommand.IZwaveReportProcessor;
import com.iremote.infraredtrans.zwavecommand.ZwaveReportBean;

public class ZwaveReportNotifyProcessor implements IZwaveReportProcessor
{
	private static Log log = LogFactory.getLog(ZwaveReportNotifyProcessor.class);
	
	protected ZwaveReportBean zrb ;
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
		try {
			executed = true;
			
			List<IZwaveReportConsumer> lst = ZwaveReportNotifyManager.getInstance().getConsumerList(zrb.getDeviceid(),zrb.getNuid() , zrb.getCommandvalue().getCmd());
			
			if ( lst != null )
			{
				for ( IZwaveReportConsumer c : lst )
					c.reportArrive(zrb.getDeviceid(),zrb.getNuid() , zrb.getCommandvalue().getCmd());
			}
		} catch (Throwable e) {
			log.error(e.getMessage() , e);
		}
		finished = true;
	}

	@Override
	public void setReport(ZwaveReportBean zrb)
	{
		this.zrb = zrb ;
	}

	@Override
	public void setNoSessionZwaveDevice(ZWaveDevice zwavedevice) {
		
	}

}
