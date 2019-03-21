package com.iremote.thirdpart.jwzh;

import java.util.Date;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Utils;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.Remote;
import com.iremote.service.IremotepasswordService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "device", parameters = {"deviceid"})
public class UpdateRemoteSecurityKey {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String deviceid ;
	private String securitykey;
	
	public String execute()
	{
		if ( deviceid == null || securitykey == null )
		{
			resultCode = ErrorCodeDefine.RAND_CODE_ERROR;
			return Action.SUCCESS;
		}
		
		IremotepasswordService svr = new IremotepasswordService();
		Remote remote = svr.getIremotepassword(deviceid);
		
		if ( remote == null )
		{
			remote = new Remote();
			remote.setDeviceid(deviceid);
			remote.setCreatetime(new Date());
			remote.setPlatform(Utils.getRemotePlatform(deviceid));
		}
		
		byte[] sk = new byte[ securitykey.length() / 2 ];
		for ( int i = 0 ; i < securitykey.length() / 2 ; i ++ )
			sk[i] = Integer.valueOf(securitykey.substring(i * 2 , i * 2 + 2), 16).byteValue();
		remote.setSecritykey(sk);
		
		svr.saveOrUpdate(remote);
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setSecuritykey(String securitykey) {
		this.securitykey = securitykey;
	}
	
}
