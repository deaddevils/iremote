package com.iremote.action.camera;

import java.util.Date;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.camera.lechange.LeChangeRequestManagerStore;
import com.iremote.action.camera.lechange.LeChangeInterface;
import com.iremote.action.camera.lechange.LeChangeUserTokenManager;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.constant.CameraProductor;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.Camera;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.service.CameraService;
import com.iremote.service.RemoteService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "camera", parameter = "cameraid")
public class DeleteCameraAction
{
	private static  Logger logdahua = Logger.getLogger("dahualechange");

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int cameraid;
	private String lechangecode;
	private String lechangemsg;
	protected PhoneUser phoneuser ;

	public String execute()
	{
		CameraService cs = new CameraService();
		Camera c = cs.query(cameraid);
		
		if ( c == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		if ( deletecamera(c) == false )
			return Action.SUCCESS;
		
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(c.getDeviceid());
		
		DeviceHelper.clearCamera(c);

		if ( r != null && r.getDeviceid().startsWith("V")) // .equals(CameraHelper.createVirtualDeviceId(phoneuser.getPlatform(), c.getProductorid(), c.getApplianceuuid()) ))
		{
			r.setPhoneuserid(null);
			r.setPhonenumber(null);
		}
		else 
			cs.delete(c);

		phoneuser.setLastupdatetime(new Date());
		PhoneUserHelper.sendInfoChangeMessage(phoneuser);
		
		return Action.SUCCESS;
	}
	
	private boolean deletecamera(Camera c)
	{
		if ( CameraProductor.dahualechange.getProductor().equals(c.getProductorid()))
			return deleteduhualechangecamera(c);
		
		return true ;
	}
	
	private boolean deleteduhualechangecamera(Camera c)
	{
		LeChangeUserTokenManager tm = new LeChangeUserTokenManager();
		tm.setDevicetype(c.getDevicetype());
		String token = tm.getToken(phoneuser);
		
		if ( token == null )
		{
			this.resultCode = tm.getResultCode();
			this.lechangecode = tm.getLechangecode();
			this.lechangemsg = tm.getLechangemsg();
			return false ;
		}

		LeChangeInterface lcr = LeChangeRequestManagerStore.getInstance().getProcessor(phoneuser.getPlatform(), c.getDevicetype());
		
		if ( lcr == null )
		{
			resultCode = ErrorCodeDefine.NOT_SUPPORT;
			return false;
		}
		
		JSONObject rst = lcr.unbindDevice(c.getApplianceuuid(), token);
		if ( rst == null )
		{
			resultCode = ErrorCodeDefine.THIRDPART_NETWORK_FAILED;
			return false ;
		}
		
		lechangecode = lcr.getResultCode(rst);
		lechangemsg = lcr.getResultMsg(rst);
		
		if ( !ErrorCodeDefine.SUCCESS_STR.equals(lechangecode)
				&& !"OP1009".equals(lechangecode))  // no privilege , this camera has deleted from the user account.
		{
			resultCode = ErrorCodeDefine.THIRDPART_CALL_FAILED;
			return false ;
		}
		
		logdahua.info(String.format("%s,%s(%d),unbind,result=%s,%s", c.getApplianceuuid(),phoneuser.getPhonenumber(),phoneuser.getPhoneuserid() , lechangecode,lechangemsg));
		
		return true;
	}
	
	public int getResultCode()
	{
		return resultCode;
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
	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
	
}
