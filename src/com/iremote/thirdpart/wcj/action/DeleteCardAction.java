package com.iremote.thirdpart.wcj.action;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.DoorlockUser;
import com.iremote.service.DoorlockUserService;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = { "zwavedeviceid" })
public class DeleteCardAction extends DeleteLockUserBaseAction{

	private DeleteDoorlockCardUserInterface cardexecute = new ChuangjiaCardDelete();
	private DoorlockUser doorlockUser;
	
	@Override
	protected boolean deleteLockUser() {
		if(!checkDoorLockUser()){
			return false;
		}
		if(!checkIfChuangjia()){
			cardexecute = new JwzhCardDelete();
		}
		cardexecute.setValue(lock,doorlockUser);
		boolean sendresult = cardexecute.sendDeleteCommand();
		this.resultCode = cardexecute.getResultCode();
		return sendresult;
	}
	
	private boolean checkIfChuangjia(){
		if ( StringUtils.isNotBlank(lock.getProductor())
				&& lock.getProductor().toLowerCase().startsWith(IRemoteConstantDefine.JWZH_CHUANGJIA_PRODUCTOR_PREFIX.toLowerCase())
				&& !lock.getProductor().toLowerCase().startsWith(IRemoteConstantDefine.JWZH_CHUANGJIA_PRODUCTOR_PREFIX_2.toLowerCase())){
			return true;
		}
		return false;
	}
	@Override
	protected boolean checkDoorLockUser(){
		DoorlockUserService doorlockUserService = new DoorlockUserService();
		doorlockUser = doorlockUserService.query(zwavedeviceid,IRemoteConstantDefine.DOOR_LOCK_USERTYPE_CARD,usercode);
		if(doorlockUser == null){
			return false;
		}
		return true;
	}
	
	@Override
	public void setDoorlockpasswordusertype(int doorlockpasswordusertype) {
		this.doorlockpasswordusertype = IRemoteConstantDefine.DOORLOCKUSER_TYPE_CARD;
	}
}
