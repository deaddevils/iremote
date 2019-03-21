package com.iremote.action.camera.lechange;

import java.util.Date;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Utils;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.Camera;
import com.iremote.domain.Remote;
import com.iremote.service.CameraService;
import com.iremote.service.RemoteService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "camera", parameter = "cameraid")
public class QueryTokenAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int cameraid;
	private String token;
	private Date validthrough;
	private String lechangecode;
	private String lechangemsg;
	
	public String execute()
	{
		CameraService cs = new CameraService();
		Camera c = cs.query(cameraid);
		
		if ( c == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(c.getDeviceid());
		
		if ( r == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		if ( r.getPhoneuserid() == null )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}
		
		LeChangeUserTokenManager tm = new LeChangeUserTokenManager();
//		token = tm.getToken(r.getPlatform() , r.getPhonenumber());
		tm.setDevicetype(c.getDevicetype());
		token = tm.getToken(r);
		this.resultCode = tm.getResultCode();
		this.lechangecode = tm.getLechangecode();
		this.lechangemsg = tm.getLechangemsg();
			
		return Action.SUCCESS;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public String getToken()
	{
		return token;
	}
	public Date getValidthrough()
	{
		return validthrough;
	}
	public String getLechangecode()
	{
		return lechangecode;
	}
	public String getLechangemsg()
	{
		return lechangemsg;
	}
	public void setCameraid(int cameraid)
	{
		this.cameraid = cameraid;
	}

	public static void main(String arg[])
	{
		LeChangeUserTokenManager tm = new LeChangeUserTokenManager();
		System.out.println(tm.getUserToken(0 , "15603068107"));
		System.out.println(tm.getUserToken(0 , "15603068107"));
		
		Utils.sleep(5000);
		System.out.println(tm.getUserToken(0 , "15603068107"));
		System.out.println(tm.getUserToken(0 , "15603068107"));
	}
}
