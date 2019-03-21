package com.iremote.infraredtrans;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.domain.Remote;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.IremotepasswordService;
import com.iremote.vo.RemoteVO;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.BufferOverflowException;

public class TemperatureReportProcessor implements IRemoteRequestProcessor
{
	private static Log log = LogFactory.getLog(TemperatureReportProcessor.class);

	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException
	{
		String deviceid = nbc.getDeviceid();
		
		String tp =  TlvWrap.readString(request, 61, 4);
		
		IremotepasswordService svr = new IremotepasswordService();
		Remote remote = svr.getIremotepassword(deviceid);

		String gatewayTemperature = remote.getTemperature();
		remote.setTemperature(tp);

		sendTemperatureAssociation(gatewayTemperature, tp, remote);

		SynchronizeRequestHelper.onSynchronizeResponse(nbc, request);

		return null;
	}

	private void sendTemperatureAssociation(String gatewayTemperature, String tp, Remote remote) {
		RemoteVO remoteVO = new RemoteVO();
		try {
			PropertyUtils.copyProperties(remoteVO, remote);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return;
		}
		remoteVO.setLasttemperature(gatewayTemperature);
        if (!StringUtils.equals(gatewayTemperature, tp)) {
			JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_GATEWAY_TEMPERATUREA_ASSOCIATION,
					remoteVO);
		}
	}
}
