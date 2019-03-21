package com.iremote.event.gateway;

import java.util.Date;
import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteEvent;
import com.iremote.common.jms.vo.RemoteNetworkChangedEvent;
import com.iremote.common.jms.vo.RemoteOnlineEvent;
import com.iremote.domain.GatewayCapability;
import com.iremote.domain.Remote;
import com.iremote.infraredtrans.GatewayReportHelper;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.GatewayCapabilityService;
import com.iremote.service.RemoteService;

import org.apache.commons.lang3.StringUtils;

public class UdpGatewayOnlineProcessor extends RemoteOnlineEvent implements ITextMessageProcessor
{
	private static final String defaultGatewayVersion = "1.1.2";
	private int oldNetwork;
	private int oldNetworkIntensity;
	private boolean isNew;
	
	@Override
	public void run()
	{
		Integer nwi = getNetworkintensity();
		
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(super.getDeviceid());
		copyParams(r);
		if (r == null) {
			isNew = true;
			r = GatewayReportHelper.createRemote(super.getDeviceid());

			r.setIversion(12);
			r.setLastupdatetime(new Date());
			r.setName(Utils.getGatewayDefaultName(super.getDeviceid()));
			r.setSsid("");
			r.setRemotetype(IRemoteConstantDefine.IREMOTE_TYPE_NORMAL);
		}

		r.setStatus(IRemoteConstantDefine.REMOTE_STATUS_ONLINE);
		if (super.isNbiotDevice()) {
			r.setNetwork(IRemoteConstantDefine.NETWORK_NBIOT);
			changeNBIotGatewayCapability(r);
		}
		if (nwi != null) {
			r.setNetworkintensity(nwi);
		}
		setVersion(r, r == null);
		rs.saveOrUpdate(r);

		pushMessageToThirdPart(r);
	}

	private void pushMessageToThirdPart(Remote remote) {
		if (!isNew && remote.getNetwork() == oldNetwork && remote.getNetworkintensity() == oldNetworkIntensity) {
			return;
		}
		JMSUtil.sendmessage(IRemoteConstantDefine.MESSAGE_TYPE_LOCK_NETWORK_INFO,
				new RemoteNetworkChangedEvent(remote.getDeviceid(), remote.getNetwork(), remote.getNetworkintensity()));
	}

	private void copyParams(Remote r) {
		if (r != null) {
			oldNetwork = r.getNetwork();
			oldNetworkIntensity = r.getNetworkintensity();
		}
	}


	private Integer getNetworkintensity()
	{
		if ( this.getReport() == null || this.getReport().length == 0 )
			return null ;
		return TlvWrap.readInteter(this.getReport(),TagDefine.TAG_NETWORK_INTENSITY , TagDefine.TAG_HEAD_LENGTH);
	}
	
	private void setVersion(Remote r , boolean force)
	{
		String version = null ;
		if ( this.getReport() != null && this.getReport().length > 0 )
			version = TlvWrap.readString(this.getReport(), TagDefine.TAG_STR_VERSION , TagDefine.TAG_HEAD_LENGTH);
		
		if ( StringUtils.isNotBlank(version))  // gateway report its current version 
			r.setVersion(version);
		else if ( force )  // set gateway version to default when we create a new gateway;
			r.setVersion(defaultGatewayVersion);
	}
	
	private void changeNBIotGatewayCapability(Remote r) {
		GatewayCapabilityService gcs = new GatewayCapabilityService();
		List<GatewayCapability> gatewayCapabilityList = gcs.querybydeviceid(super.getDeviceid());

		boolean hasCapabilityOfNotSupportForNetwork = false;

		for (GatewayCapability gatewayCapability : gatewayCapabilityList) {
			if (gatewayCapability.getCapabilitycode() == IRemoteConstantDefine.GATEWAY_CAPABILITY_AP_MODE) {
				gcs.delete(gatewayCapability);
			}
			if (gatewayCapability.getCapabilitycode() == IRemoteConstantDefine.GATEWAY_CAPABILITY_NOT_SUPPORT_FOR_NETWORK) {
				hasCapabilityOfNotSupportForNetwork = true;
			}
		}

		if (!hasCapabilityOfNotSupportForNetwork) {
			gcs.saveOrUpdate(new GatewayCapability(r, IRemoteConstantDefine.GATEWAY_CAPABILITY_NOT_SUPPORT_FOR_NETWORK));
		}
	}

	@Override
	public String getTaskKey()
	{
		return super.getDeviceid();
	}

}
