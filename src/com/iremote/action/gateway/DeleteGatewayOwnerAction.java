package com.iremote.action.gateway;

import java.util.Date;
import java.util.List;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteEvent;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.Partition;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.BlueToothPasswordService;
import com.iremote.service.PartitionService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid")
public class DeleteGatewayOwnerAction
{
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	protected String deviceid ;
	protected PhoneUser phoneuser;
	
	public String execute()
	{
		if (!initPhoneUser()) {
			return Action.SUCCESS;
		}
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		
		if ( r == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.ERROR;
		}
		
		if ( r.getPhoneuserid() == null || r.getPhoneuserid() == 0  )
			return Action.SUCCESS;
		
		r.setPhonenumber(null);
		r.setPhoneuserid(null);
		r.setSsid(null);
		r.setName(Utils.getGatewayDefaultName(r.getDeviceid()));
		
		removeDeviceFromPartition();
		
		this.phoneuser.setLastupdatetime(new Date());
		
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_DELETE_REMOTE, new RemoteEvent(r.getDeviceid() , new Date() , phoneuser.getPhoneuserid() , 0));
		
		PhoneUserHelper.sendInfoChangeMessage(phoneuser , "deletegateway");
		
		return Action.SUCCESS;
	}

	protected boolean initPhoneUser() {
		return true;
	}

	private void removeDeviceFromPartition(){
		ZWaveDeviceService zds = new ZWaveDeviceService();
		PartitionService ps = new PartitionService();
		BlueToothPasswordService blueToothPasswordService = new BlueToothPasswordService();
		List<ZWaveDevice> zwavelist = zds.querybydeviceid(deviceid);

		for(ZWaveDevice z : zwavelist){
			blueToothPasswordService.deleteByZwaveDeviceId(z.getZwavedeviceid());

			if(z.getPartitionid()!=null&&z.getPartitionid()!=0){

				Partition partition = ps.query(z.getPartitionid());
				if(partition.getZwavedevice() == null){

					z.setPartitionid(null);
				}else{

					String dscDeviceid = partition.getZwavedevice().getDeviceid();
					if (!dscDeviceid.equals(z.getDeviceid())) {
						z.setPartitionid(null);
					}
				}

			} else if (z.getPartitions() != null && z.getPartitions().size() != 0) {

				for (Partition partition : z.getPartitions()) {
					List<ZWaveDevice> zWaveDeviceList = zds.querybypartitionid(partition.getPartitionid());

					for (ZWaveDevice zWaveDevice : zWaveDeviceList) {
						if (!deviceid.equals(zWaveDevice.getDeviceid())) {
							zWaveDevice.setPartitionid(null);
						}
					}
				}
			}
		}
	}
	
	public int getResultCode()
	{
		return resultCode;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
}
