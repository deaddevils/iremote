package com.iremote.thirdpart.wcj.action;

import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY , domain = "device", parameters = {"zwavedeviceid"})
public class SetPasswordAction extends SetPasswordBase{
	@Override
	protected boolean checkpassword() {
		activepassword = dpsvr.queryActivePassword2(zwavedeviceid);
		if (activepassword == null || activepassword.size() == 0)
			return true;
		for (DoorlockPassword dp : activepassword) {
			if (dp.getPassword().equals(encryptedpassword))
				return false;
		}
		return true;
	}
}
