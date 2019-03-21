package com.iremote.action.gateway;

import java.util.ArrayList;
import java.util.List;

import com.iremote.common.ByteUtils;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.TagDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.DeviceExtendInfo;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.DeviceExtendInfoService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.vo.ZWaveDeviceExtendInfo;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid")
public class QueryGatewayandDeviceExtendInfo 
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String deviceid ;
	private String homeid ;
	private String zwavesecuritykey;
	private List<ZWaveDeviceExtendInfo> zwavedevice = new ArrayList<ZWaveDeviceExtendInfo>();
	
	public String execute()
	{
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		
		if ( r == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		if ( r.getHomeid() != null )
			homeid = Integer.toHexString(r.getHomeid());
		if ( r.getZwavescuritykey() != null )
			zwavesecuritykey = ByteUtils.toHexString(r.getZwavescuritykey());
		
//		ZWaveDeviceService zds = new ZWaveDeviceService();
//		List<ZWaveDevice> lst = zds.querybydeviceid(deviceid);
//		
//		DeviceExtendInfoService dis = new DeviceExtendInfoService();
//		for ( ZWaveDevice zd : lst )
//		{
//			String ei = null ;
//			List<DeviceExtendInfo> dil = dis.querybyzwavedeviceid(zd.getZwavedeviceid());
//			
//			for ( DeviceExtendInfo di : dil)
//			{
//				if ( di.getZwaveproductormessage() == null ) 
//					continue ;
//				String zpm = di.getZwaveproductormessage();
//				if ( zpm.startsWith("\""))
//					zpm = zpm.replaceAll("\"", "");
//				if ( !zpm.startsWith("["))
//					continue;
//				
//				byte[] bb = ByteUtils.jsonarraytobytearray(zpm);
//				ei = ByteUtils.toHexString(TlvWrap.readTag(bb, TagDefine.TAG_MODEL, 0));
//				break;
//			}
//			
//			zwavedevice.add(new ZWaveDeviceExtendInfo(zd.getZwavedeviceid() , zd.getNuid() , zd.getDevicetype() , ei));
//			
//		}
		
		return Action.SUCCESS;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public String getHomeid() {
		return homeid;
	}
	public String getZwavesecuritykey() {
		return zwavesecuritykey;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public List<ZWaveDeviceExtendInfo> getZwavedevice() {
		return zwavedevice;
	}

}
