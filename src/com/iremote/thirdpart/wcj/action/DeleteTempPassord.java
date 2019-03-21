package com.iremote.thirdpart.wcj.action;

import com.iremote.action.sms.RandCodeHelper;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid", "lockid"})
public class DeleteTempPassord extends SetTempPassword {

	@Override
	public String execute() 
	{
		this.setSuperpw(RandCodeHelper.createRandCode());
		this.setSuperpwtimes("2050-01-01 00:00:00");
		this.setSuperpwtimee("2050-01-01 00:00:01");

		return super.execute();
	}


}
