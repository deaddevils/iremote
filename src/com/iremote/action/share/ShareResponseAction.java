package com.iremote.action.share;

import java.util.*;
import java.util.stream.Collectors;

import com.iremote.action.helper.PhoneUserBlueToothHelper;
import com.iremote.domain.*;
import com.iremote.service.*;
import org.apache.commons.lang3.StringUtils;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.push.PushMessage;
import com.opensymphony.xwork2.Action;

public class ShareResponseAction {
	
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int shareid;
	private int response;
	private Integer armstatus;
	private PhoneUser phoneuser;
	private PhoneUser touser;

	public String execute()
	{		
		UserShareService svr = new UserShareService();
		UserShare su = svr.query(shareid) ;
		
		if ( su == null || phoneuser.getPhoneuserid() != su.getTouserid() )
		{
			resultCode = ErrorCodeDefine.SHARE_INVITATION_EXPIRED ;
			return Action.SUCCESS;
		}
	
		PhoneUserService pus = new PhoneUserService();
		PhoneUser shareuser = pus.query(su.getShareuserid());
		touser = pus.query(su.getTouserid());
		
		if ( shareuser == null )
		{
			resultCode = ErrorCodeDefine.SHARE_INVITATION_EXPIRED ;
			return Action.SUCCESS;
		}
		
		if ( su.getStatus() != IRemoteConstantDefine.USER_SHARE_STATUS_WAIT_FOR_RESPONSE)
		{
			resultCode = ErrorCodeDefine.SHARE_INVITATION_EXPIRED ;
			return Action.SUCCESS;
		}
		
		if ( response == 1 )
		{
			List<UserShare> userShareList = null;
			if ( su.getSharetype()  == IRemoteConstantDefine.USER_SHARE_TYPE_FAMILY )
			{
				resultCode = ShareHelper.checkfamilyshare(su.getSharetype(), shareuser, touser);
				if ( resultCode != ErrorCodeDefine.SUCCESS )
					return Action.SUCCESS;
				FamilyService fs = new FamilyService();
				Family f ;
				if ( shareuser.getFamilyid() == null )
				{
					f = new Family();
					f.setPhoneuserid(shareuser.getPhoneuserid());
					f.setArmstatus(shareuser.getArmstatus());

					shareuser.setFamilyid(fs.save(f));
				}
				else 
					f = fs.query(shareuser.getFamilyid());

				createFamilyBlueToothPassword(f.getFamilyid());
				touser.setFamilyid(shareuser.getFamilyid());
				this.armstatus = f.getArmstatus();
			}else if (su.getSharetype() == IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL
					&& su.getSharedevicetype() == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_SPECIFY) {
				createBlueToothPassword(su);

				userShareList = svr.queryShared(su.getShareuserid(), su.getTouserid(),
						IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL, IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_SPECIFY);
				if (userShareList != null && userShareList.size() > 1) {
					mergeUserShareDevices(userShareList);
				}
			} else if (su.getSharetype() == IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL
					&& su.getSharedevicetype() == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_ALL) {
				createFriendBlueToothPassword(shareuser.getPhoneuserid());
			}
			
			su.setStatus(IRemoteConstantDefine.USER_SHARE_STATUS_NORMAL);
			shareuser.setLastupdatetime(new Date());

			if (userShareList != null && su.getSharetype() == IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL
					&& su.getSharedevicetype() == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_SPECIFY) {
				initZwaveDeviceShare(userShareList.get(0));
			}else
				initZwaveDeviceShare(su);
			
			if ( shareuser.getFamilyid() == null )
				PushMessage.pushShareRequestAcceptedMessage(shareuser.getAlias(), phoneuser.getPlatform(), su.getShareid());
			else 
			{
				List<Integer> sharetophoneuserid = PhoneUserHelper.queryPhoneuseridbyfamilyid(shareuser.getFamilyid());

				sharetophoneuserid.add(shareuser.getPhoneuserid());
				sharetophoneuserid.add(touser.getPhoneuserid());
				
				List<String> al = pus.queryAlias(sharetophoneuserid); 
				PushMessage.pushInfoChangedMessage(al.toArray(new String[0]) , phoneuser.getPlatform());
			}
		}
		else 
		{
			svr.delete(su);
			PushMessage.pushShareRequestRejectedMessage(shareuser.getAlias(), phoneuser.getPlatform(),su.getShareid());
		}
		
		return Action.SUCCESS;
	}

	private void createFamilyBlueToothPassword(Integer familyId) {
		List<Integer> phoneUserIdList = new PhoneUserService().queryldListByFamilyId(familyId);

		PhoneUserBlueToothHelper.createBlueToothPassword(touser.getPhoneuserid(), phoneUserIdList);
		PhoneUserBlueToothHelper.createBlueToothPassword(phoneUserIdList, touser.getPhoneuserid());
	}

	private void createFriendBlueToothPassword(Integer shareUserId) {
		PhoneUserBlueToothHelper.createBlueToothPassword(shareUserId, touser.getPhoneuserid());
	}

	private void createBlueToothPassword(UserShare su){
		List<Integer> zwaveDeviceIds = su.getUserShareDevices()
                .stream()
                .filter(s -> s.getZwavedeviceid() != null)
                .map(s -> s.getZwavedeviceid()).collect(Collectors.toList());
		List<ZWaveDevice> devices = new ZWaveDeviceService().query(zwaveDeviceIds, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK);

		PhoneUserBlueToothHelper.createBlueToothPassword(devices, Arrays.asList(touser.getPhoneuserid()), null);
	}

	private void mergeUserShareDevices(List<UserShare> userShareList) {
		List<UserShareDevice> usd = userShareList.get(0).getUserShareDevices();
		UserShareService uss = new UserShareService();
		UserShareDeviceService usds = new UserShareDeviceService();
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		for (int i = 1; userShareList != null && i < userShareList.size(); i++) {
			List<UserShareDevice> userShareDevices = userShareList.get(i).getUserShareDevices();
			for (UserShareDevice userShareDevice : userShareDevices) {
				boolean exsit = usd.stream().anyMatch(d -> equals(d, userShareDevice));
				if (!exsit) {
					usd.add(new UserShareDevice(usd.get(0).getUserShare(), userShareDevice.getZwavedeviceid(),
							userShareDevice.getInfrareddeviceid(), userShareDevice.getCameraid()));
				}
				usds.delete(userShareDevice);
				zdss.delete(userShareDevice.getZwavedeviceshare());
			}
			uss.delete(userShareList.get(i));
		}

		for (UserShareDevice userShareDevice : userShareList.get(0).getUserShareDevices()) {
			zdss.delete(userShareDevice.getZwavedeviceshare());
			userShareDevice.setZwavedeviceshare(null);
		}
	}

	private boolean equals(UserShareDevice d, UserShareDevice userShareDevice) {
		boolean zwavedeviceisNull = false;
		boolean infrareddeviceisNull = false;
		boolean camaraisNull = false;
		if (d.getZwavedeviceid() == null || d.getZwavedeviceid() == 0) {
			if (userShareDevice.getZwavedeviceid() != null && userShareDevice.getZwavedeviceid() != 0) {
				return false;
			} else {
				zwavedeviceisNull = true;
			}
		}
		if (d.getInfrareddeviceid() == null || d.getInfrareddeviceid() == 0) {
			if (userShareDevice.getInfrareddeviceid() != null && userShareDevice.getInfrareddeviceid() != 0) {
				return false;
			} else {
				infrareddeviceisNull = true;
			}
		}
		if (d.getCameraid() == null || d.getCameraid() == 0) {
			if (userShareDevice.getCameraid() != null && userShareDevice.getCameraid() != 0) {
				return false;
			} else {
				camaraisNull = true;
			}
		}

		return (zwavedeviceisNull || d.getZwavedeviceid().equals(userShareDevice.getZwavedeviceid()))
				&& (infrareddeviceisNull || d.getInfrareddeviceid().equals(userShareDevice.getInfrareddeviceid()))
				&& (camaraisNull || d.getCameraid().equals(userShareDevice.getCameraid()));
	}

	private void initZwaveDeviceShare(UserShare su)
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		InfraredDeviceService ids = new InfraredDeviceService();
		CameraService cs = new CameraService();
		
		if ( su.getUserShareDevices() != null )
		{
			for ( UserShareDevice usd : su.getUserShareDevices())
			{
				String deviceid = null ;
				if ( usd.getZwavedeviceid() != null && usd.getZwavedeviceid() != 0 )
				{
					ZWaveDevice zd = zds.query(usd.getZwavedeviceid());
					if ( zd != null )
						deviceid = zd.getDeviceid();
				}
				if ( usd.getInfrareddeviceid() != null && usd.getInfrareddeviceid() != 0 )
				{
					InfraredDevice id = ids.query(usd.getInfrareddeviceid());
					if ( id != null )
						deviceid  = id.getDeviceid();
				}
				if ( usd.getCameraid() != null && usd.getCameraid() != 0 )
				{
					Camera c = cs.query(usd.getCameraid());
					if ( c != null )
						deviceid  = c.getDeviceid();
				}
				if ( StringUtils.isNotBlank(deviceid))
					usd.setZwavedeviceshare(new ZWaveDeviceShare(deviceid , su , usd));
			}
		}
	}
	
	
	public int getResultCode() {
		return resultCode;
	}
	public void setShareid(int shareid) {
		this.shareid = shareid;
	}
	public void setResponse(int response) {
		this.response = response;
	}

	public Integer getArmstatus() {
		return armstatus;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
}
