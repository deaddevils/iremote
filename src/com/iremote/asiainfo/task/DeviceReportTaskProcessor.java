package com.iremote.asiainfo.task;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.iremote.asiainfo.connection.WebSocketWrap;
import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.asiainfo.helper.AsiainfoMessageHelper;
import com.iremote.asiainfo.processor.AsiaInfoProcessorStore;
import com.iremote.asiainfo.request.AsiainfoConnectionConext;
import com.iremote.asiainfo.vo.AsiainfoMessage;
import com.iremote.asiainfo.vo.ReportParse;
import com.iremote.asiainfo.vo.StatusTranslate;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.thread.ISynchronizeRequest;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.IRemoteRequestProcessor;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class DeviceReportTaskProcessor  implements Runnable, ISynchronizeRequest{

	private static Log log = LogFactory.getLog(DeviceReportTaskProcessor.class);
	
	private AsiainfoMessage message;
	private AsiainfoMessage result;

	public DeviceReportTaskProcessor(AsiainfoMessage message) {
		super();
		this.message = message;
	}

	@Override
	public void run()
	{
		process(message.getClientid() , message.getMessage());
		AsiainfoHttpHelper.CommonResponse(message);
	}
	
	private void process(String deviceid , byte[] report)
	{
		IRemoteRequestProcessor pro = AsiaInfoProcessorStore.getInstance().getProcessor(report , 0);
		if ( pro == null )
			return ;
		try {
			AsiainfoConnectionConext ac = new AsiainfoConnectionConext(deviceid);
			if ( log.isInfoEnabled() )
				Utils.print(String.format("receive report from %s" , deviceid) , report, report.length);
			CommandTlv ct = pro.process(report, ac );
			if ( ct != null )
				;//ac.sendData(ct); 
//			if ( pro instanceof ZWaveReportProcessor )
//				reportDeviceStatus(((ZWaveReportProcessor)pro).getDevice());
		} catch (BufferOverflowException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} 
//		catch (TimeoutException e) {
//			log.error(e.getMessage(), e);
//		}
		
	}
		
	@SuppressWarnings("unused")
	private void reportDeviceStatus(ZWaveDevice device)
	{
		if ( device == null )
			return ;
		AsiainfoMessage am = AsiainfoMessageHelper.createMessageHead(IRemoteConstantDefine.ASIAINFO_MSG_ID_REPORT_PARSE,device.getDeviceid());
		
		ReportParse ci = new ReportParse();
		ci.setDeviceId( Utils.createZwaveDeviceid(device.getDeviceid() , device.getZwavedeviceid(),device.getNuid()));
		ci.setReplyResult(0);
		ci.setReplySeriNum(message.getSequence());
		ci.setStateList(translateDeviceStatus(device));

		am.setMessageinfo(JSON.toJSONString(ci));
		
		try {
			WebSocketWrap.writeMessage(am);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private JSONObject translateDeviceStatus(ZWaveDevice device)
	{
		JSONObject s = new JSONObject();
		
		if ( device == null )
			return null ;

		//s.put("deviceid", device.getDeviceid());
		//s.put("nuid", device.getNuid());
		s.put("name", device.getName());
		
		if ( device.getBattery() < 20 )
			s.put("lowPower", 1);
		if ( device.getBattery() > 40 )
			s.put("lowPower", 0);
		
		int status = - 1 ;
		if ( device.getStatus() != null )
			status = device.getStatus();
		int shadowstatus = -1 ;
		if ( device.getShadowstatus() != null )
			shadowstatus = device.getShadowstatus();
		
		if (  251 == status  )
			s.put("breakAlarm", 1);
		else 
			s.put("breakAlarm", 0);

		StatusTranslate st = null;
		
		
		if ( IRemoteConstantDefine.DEVICE_TYPE_SMOKE.equals(device.getDevicetype())
			 || IRemoteConstantDefine.DEVICE_TYPE_WATER.equals(device.getDevicetype())
			 || IRemoteConstantDefine.DEVICE_TYPE_GAS.equals(device.getDevicetype()))
		{
			st = AsiainfoHttpHelper.getReportTranslate(device.getDevicetype(), status);				
		}
		else 
			st = AsiainfoHttpHelper.getReportTranslate(device.getDevicetype(), shadowstatus);
		
		if ( st != null )
			s.put(st.getName(), st.getValue());

		return s ;
	}
	
	@Override
	public void sendRequest() throws IOException, TimeoutException 
	{
		WebSocketWrap.writeMessage(result);
	}

	@Override
	public String getkey() {
		return message.getSequence();
	}

}
