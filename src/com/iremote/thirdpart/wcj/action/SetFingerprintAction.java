package com.iremote.thirdpart.wcj.action;

import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = { "zwavedeviceid" })
public class SetFingerprintAction extends SetFingerprintBase{
	@Override
	protected boolean checkfingerprint() {
		activefinger = dpsvr.queryActivePassword2(zwavedeviceid);
		if (activefinger == null || activefinger.size() == 0)
			return true;
		for (DoorlockPassword dp : activefinger) {
			if (dp.getPassword().equals(fingerprint))
				return false;
		}
		return true;
	}
}
