package com.iremote.action.share;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.iremote.action.helper.PhoneUserBlueToothHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.push.PushMessage;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.UserShareDevice;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.PhoneUserService;
import com.iremote.service.UserShareDeviceService;
import com.iremote.service.UserShareService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class DeleteShareDeviceAction {
	
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int usersharedeviceid;
	private PhoneUser phoneuser ;
	
	public String execute(){
		UserShareDeviceService userShareDeviceService = new UserShareDeviceService();
		UserShareDevice userShareDevice = userShareDeviceService.query(usersharedeviceid);
		
		if ( userShareDevice == null )
		{
			resultCode = ErrorCodeDefine.SHARE_INVITATION_EXPIRED ;
			return Action.SUCCESS;
		}

		changeBlueToothPassword(userShareDevice);

		userShareDeviceService.delete(userShareDevice);
		
		if(userShareDeviceService.query(userShareDevice.getUserShare()) == null){
			new UserShareService().delete(userShareDevice.getUserShare());
		};
		
		
		List<Integer> sharetophoneuserid = new ArrayList<Integer>();

		sharetophoneuserid.add(userShareDevice.getUserShare().getTouserid());
		sharetophoneuserid.add(userShareDevice.getUserShare().getShareuserid());
		
		PhoneUserService pus = new PhoneUserService();
		PhoneUser shareuser = pus.query(userShareDevice.getUserShare().getShareuserid());
		shareuser.setLastupdatetime(new Date());
		
		List<String> al = pus.queryAlias(sharetophoneuserid); 
		PushMessage.pushInfoChangedMessage(al.toArray(new String[0]) , phoneuser.getPlatform());
				
		return Action.SUCCESS;
	}

	protected void changeBlueToothPassword(UserShareDevice userShareDevice) {
		if (userShareDevice.getZwavedeviceid() != null) {
			ZWaveDevice zd = new ZWaveDeviceService().query(userShareDevice.getZwavedeviceid());
			if (userShareDevice.getUserShare() != null) {
				if (PhoneUserBlueToothHelper.isBlueToothLock(zd)) {
					PhoneUserBlueToothHelper.modifyBlueToothDevicePassword(Arrays.asList(zd),
							Arrays.asList(userShareDevice.getUserShare().getTouserid()));
				}
			}
		}
	}

	public void setUsersharedeviceid(int usersharedeviceid) {
		this.usersharedeviceid = usersharedeviceid;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
}
