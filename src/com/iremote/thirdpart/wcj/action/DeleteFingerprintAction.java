package com.iremote.thirdpart.wcj.action;

import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid", "lockid"})
public class DeleteFingerprintAction extends DeleteUserPassord
{
	
	public DeleteFingerprintAction()
	{
		super();
		doorlockpasswordusertype = 3;
	}

	@Override
	protected byte[] createcommand()
	{
		return new byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x09,getUsercode(),0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
	}
	
	@Override
	protected Byte[] responseKey()
	{
		return new Byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x0A,getUsercode()} ;
	}
	
	@Override
	protected boolean checkResult(byte[] cmd)
	{
		if ( ((cmd[3] & 0xff) == 0xA0 ) && ( cmd[8] == 0x01) ) //delete fingerprint 
			return true;
		return false ;
	}
}
