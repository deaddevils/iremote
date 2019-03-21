package com.iremote.thirdpart.wcj.action;

import java.util.List;
import org.apache.commons.lang3.StringUtils;

import com.iremote.action.helper.DoorlockHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

public class SetCardBase extends AddLockUserBaseAction {
	
	protected String cardinfo;
	protected int cardtype;
	protected List<DoorlockPassword> activecard;
    protected DoorlockPasswordService dpsvr = new DoorlockPasswordService();
    protected AddDoorlockCardUserInterface cardexecute = new ChuangjiaCard();
	
	@Override
	protected boolean sendUserToLock(DoorlockPassword doorlockpassword) {
		if(StringUtils.isBlank(username)&&StringUtils.isNotBlank(cardname)){
			username = cardname;
		}
		if(!checkIfChuangjia()){
			cardexecute = new JwzhCard();
			cardexecute.setMainValue(username,usercode,cardinfo,cardtype,validfrom,validthrough,zwavedeviceid,lock,weekday,starttime,endtime);
			return sendTwoConfig(doorlockpassword);
		}else{
			if(asynch==IRemoteConstantDefine.SEND_PASSWORD_TYPE_ASYNCH){
				resultCode = ErrorCodeDefine.NOT_SUPPORT;
				return false;
			}
			cardexecute.setMainValue(username,usercode,cardinfo,cardtype,validfrom,validthrough,zwavedeviceid,lock,weekday,starttime,endtime);
			resultCode = cardexecute.init();
			if(resultCode==ErrorCodeDefine.SUCCESS){
				usercode = cardexecute.getUsercode() & 0xff;
				return true;
			}else{
				resultCode = cardexecute.getResultCode();
				return false;
			}
		}
	}
	
	@Override
	protected boolean sendValidTimeToLock(DoorlockPassword doorlockpassword) {
		if(!checkIfChuangjia()){
			return true;
		}else{
			resultCode = DoorlockHelper.sendTimeConfigure(lock.getDeviceid(), lock.getNuid(),
					(byte) usertype, (byte) usercode, validfrom, validthrough);

			return (resultCode == ErrorCodeDefine.SUCCESS);
		}
	}
	
	private boolean sendTwoConfig(DoorlockPassword doorlockpassword){
		resultCode = cardexecute.init();
		if(resultCode==ErrorCodeDefine.SUCCESS){
			usercode = cardexecute.getUsercode() & 0xff;
			return true;
		}
		return false;
	}
		
/*	private boolean checkIfChuangjia(){
		if ( StringUtils.isNotBlank(lock.getProductor())
				&& lock.getProductor().toLowerCase().startsWith(IRemoteConstantDefine.JWZH_CHUANGJIA_PRODUCTOR_PREFIX.toLowerCase())
				&& !lock.getProductor().toLowerCase().startsWith(IRemoteConstantDefine.JWZH_CHUANGJIA_PRODUCTOR_PREFIX_2.toLowerCase())){
			return true;
		}
		return false;
	}*/

	@Override
	protected boolean check() {
		ZWaveDeviceService zWaveDeviceService = new ZWaveDeviceService();
		lock = zWaveDeviceService.query(zwavedeviceid);

		if (asynch != 0 && !ConnectionManager.contants(lock.getDeviceid())) {
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}
		if (checkcard() == false) {
			resultCode = ErrorCodeDefine.DOORLOCK_SETPASSWORD_CLASH;
			return false;
		}
		return true;

	}

    protected boolean checkcard() {
		return true;
	}

	@Override
	protected void uniqueMethod(DoorlockPassword doorlockPassword) {
		doorlockPassword.setPasswordtype(cardtype);
	}
	@Override
	protected String createpassword() {
		return cardinfo;
	}
	@Override
	public void setUsertype(int usertype) {
		this.usertype = IRemoteConstantDefine.DOORLOCKUSER_TYPE_CARD;
	}

	public void setCardinfo(String cardinfo) {
		this.cardinfo = cardinfo;
	}

	public void setCardtype(int cardtype) {
		this.cardtype = cardtype;
	}
	
}
