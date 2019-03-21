package com.iremote.thirdpart.wcj.action;

import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class SetCardAction extends SetCardBase {
	@Override
	protected boolean checkcard() {
		activecard = dpsvr.queryActivePassword2(zwavedeviceid);
		if (activecard == null || activecard.size() == 0)
			return true;
		for (DoorlockPassword dp : activecard) {
			if (dp.getPassword().equals(cardinfo))
				return false;
		}
		return true;
	}
}
