package com.iremote.infraredtrans.zwavecommand.bgm;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;

public class BGMReportProcessor  extends ZWaveReportBaseProcessor
{
	private static Log log = LogFactory.getLog(BGMReportProcessor.class);
	private static Map<Integer , Integer> map = new HashMap<Integer , Integer>();
	
	static 
	{
		map.put(1, 2);
		map.put(2, 1);
		map.put(4, 0);
		map.put(5, 4);
	}
	
	public BGMReportProcessor() 
	{
		super();
		super.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus() 
	{
		if ( zrb.getCmd() == null || zrb.getCmd().length < 6)
			return ;
		
		int t = zrb.getCmd()[3] & 0xff;
		
		Integer p = map.get(t);
		if ( p != null )
		{
			int v = zrb.getCmd()[5] & 0xff;
			zrb.getDevice().setStatuses(Utils.setJsonArrayValue(zrb.getDevice().getStatuses(), p, v));
		}
		else if ( t == 3)
		{
			byte[] b = new byte[zrb.getCmd().length - 5];
			System.arraycopy(zrb.getCmd(), 5, b, 0, b.length);
			try {
				String s = new String(b , "UTF-8");
				zrb.getDevice().setStatuses(Utils.setJsonArrayValue(zrb.getDevice().getStatuses(), 10, s));
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage() , e);
			}
			
		}
	}

	@Override
	public String getMessagetype() 
	{
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}
