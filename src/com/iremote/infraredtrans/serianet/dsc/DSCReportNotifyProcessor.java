package com.iremote.infraredtrans.serianet.dsc;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.taskmanager.IMulitReportTask;
import com.iremote.infraredtrans.serianet.ISeriaNetReportProcessor;
import com.iremote.infraredtrans.serianet.SeriaNetReportBean;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.infraredtrans.zwavecommand.notifiy.IZwaveReportConsumer;
import com.iremote.infraredtrans.zwavecommand.notifiy.ZwaveReportNotifyManager;

public class DSCReportNotifyProcessor implements ISeriaNetReportProcessor
{
	private static Log log = LogFactory.getLog(DSCReportNotifyProcessor.class);
	private SeriaNetReportBean zrb;
	protected boolean finished = false ;
	protected boolean executed = false ;
	protected int nuid = IRemoteConstantDefine.DEVICE_NUID_DSC;
	
	@Override
	public boolean merge(IMulitReportTask task) {
		return false;
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public boolean isExecuted() {
		return executed;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public void run() 
	{
		try {
			executed = true;
			
			byte[] cmd = TlvWrap.readTag(zrb.getCmd(), TagDefine.TAG_DSC, TagDefine.TAG_HEAD_LENGTH);
			List<IZwaveReportConsumer> lst = ZwaveReportNotifyManager.getInstance().getConsumerList(zrb.getDeviceid(),nuid , cmd);
			
			if ( lst != null )
			{
				for ( IZwaveReportConsumer c : lst )
					c.reportArrive(zrb.getDeviceid(),nuid , cmd);
			}
		} catch (Throwable e) {
			log.error(e.getMessage() , e);
		}
		finished = true;
	}

	@Override
	public void setReport(SeriaNetReportBean zrb) 
	{
		this.zrb = zrb;
	}

	public long getTaskIndentify()
	{
		return zrb.getReportid();
	}
}
