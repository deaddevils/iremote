package com.iremote.action.device;

import java.util.Date;
import java.util.List;

import com.iremote.action.gateway.DeleteGatewayOwnerAction;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivileges;
import com.iremote.domain.Camera;
import com.iremote.domain.Partition;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.CameraService;
import com.iremote.service.DoorlockAssociationService;
import com.iremote.service.PartitionService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivileges( dataprivilege = {
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameter = "zwavedeviceid"),
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.ATTRIBUTE, domain = "devicemanage", usertype={"thirdpart"}),
})
public class DeleteZwaveDeviceAction
{
	private int resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
	private int zwavedeviceid ;
	private ZWaveDevice zwavedevice ;
	private PhoneUser phoneuser;
	private ThirdPart thirdpart;

	public String execute()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		if ( zwavedevice == null )
			zwavedevice = zds.query(zwavedeviceid);
		
		if ( zwavedevice == null )
			return Action.SUCCESS;
		
		DeviceHelper.clearZwaveDevice(zwavedevice);
		
		
		if ( isGatewayDevice(zwavedevice) && thirdpart != null ) // Third parter can't delete a device create automatically , such as a WIFI lock
		{
			this.resultCode = ErrorCodeDefine.NOT_SUPPORT;
			return Action.SUCCESS;
		}
		
		if ( isGatewayDevice(zwavedevice))
		{
			DeleteGatewayOwnerAction action = new DeleteGatewayOwnerAction();
			action.setDeviceid(zwavedevice.getDeviceid());
			action.setPhoneuser(phoneuser);
			action.execute();
			this.resultCode = action.getResultCode();
			return Action.SUCCESS;
		}
		else{ 			
			if(IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(zwavedevice.getDevicetype())){
				CameraService cs = new CameraService();
				PartitionService ps = new PartitionService();
				ZWaveDeviceService zds1 = new ZWaveDeviceService();
				List<Partition> partitionlist = ps.querypartitionbyzwavedeviceid(zwavedevice.getZwavedeviceid());
				for(Partition p : partitionlist){
					List<ZWaveDevice> zwavelist = zds1.querybypartitionid(p.getPartitionid());
					List<Camera> cameralist = cs.querybypartitionid(p.getPartitionid());
					for(Camera c: cameralist){
						c.setPartitionid(null);
					}
					for(ZWaveDevice z : zwavelist){
						z.setPartitionid(null);
					}
					
				}
				
			}
			DoorlockAssociationService das = new DoorlockAssociationService();
			das.deletebyzwavedeviceid(zwavedeviceid);
			
			zds.delete(zwavedevice);
		}
		
		if ( phoneuser != null )
		{
			phoneuser.setLastupdatetime(new Date());
			PhoneUserHelper.sendInfoChangeMessage(phoneuser);
		}
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, new InfoChange(zwavedevice.getDeviceid() , new Date() , 0) );

		this.resultCode = ErrorCodeDefine.SUCCESS;
		
		return Action.SUCCESS;
	}
	
	public void deleteZwaveDevice(String deviceid , int nuid)
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		zwavedevice  = zds.querybydeviceid(deviceid, nuid);
		
		if ( zwavedevice == null )
			return ;
		
		this.zwavedeviceid = zwavedevice.getZwavedeviceid();
		this.execute();
	}

	private boolean isGatewayDevice(ZWaveDevice zd)
	{
		if ( IRemoteConstantDefine.DEVICE_TYPE_AIR_QUALITY.equals(zd.getDevicetype()))
			return true;
		if ( IRemoteConstantDefine.DEVICE_TYPE_DOOR_CONTROL.equals(zd.getDevicetype()))
			return true;
		if ( IRemoteConstantDefine.DEVICE_TYPE_FINGERPRING.equals(zd.getDevicetype()))
			return true;
		if ( IRemoteConstantDefine.DEVICE_TYPE_DRESS_HELPER.equals(zd.getDevicetype()))
			return true;
		if ( GatewayUtils.isCobbeLock(zd.getDeviceid()) )
			return true;
		return false ;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}

	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setThirdpart(ThirdPart thirdpart) {
		this.thirdpart = thirdpart;
	}
	
}
